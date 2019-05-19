package com.leaderment.sales.service.impl;


import com.leaderment.sales.mapper.jpa.SalePlanItemMapper;
import com.leaderment.sales.mapper.jpa.UserMapper;
import com.leaderment.sales.mapper.mybatis.*;
import com.leaderment.sales.pojo.SalePlanItem;
import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;
import com.leaderment.sales.pojo.User;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.ItemValVO;
import com.leaderment.sales.pojo.vo.SalePlanItemListByOperationsVO;
import com.leaderment.sales.pojo.vo.SalePlanItemListVO;
import com.leaderment.sales.service.SalesOperationsViewService;
import com.leaderment.sales.util.entity.ResultBean;
import com.leaderment.sales.util.excel.ImportExcelUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 运营 Service
 */
@Service
public class SalesOperationsViewServicelmpl implements SalesOperationsViewService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(ImportExcelUtil.class);


    @Autowired
    SalePlanItemMapperEx salePlanItemMapperEx;

    @Autowired
    UserMapper userMapper;

    @Autowired
    InventoryMapperEx inventoryMapperEx;

    @Autowired
    SalePalnMapperEx salePalnMapperEx;

    @Autowired
    SalesVolumeRuleMapperEx salesVolumeRuleMapperEx;

    @Autowired
    SalesVolumeRuleItemKeyRelMapperEx salesVolumeRuleItemKeyRelMapperEx;



    @Override
    public ResultBean findSalePlanItemList(FindSalesPalnListDTO findSalesPalnListDTO) {
        ResultBean resultBean = new ResultBean();

        resultBean = checkSalePlanItemListByOperationsVO(findSalesPalnListDTO);
        if(resultBean.getCode().equals(500)){
            return resultBean;
        }

        User user =  userMapper.findByUserId(findSalesPalnListDTO.getUserId());


        /**
         * 显示结果 list
         */
        List<SalePlanItemListByOperationsVO> salePlanItemListByOperationsVOList =  salePlanItemMapperEx.findSalePlanItemListByOperationsVOByBusinessUnitId(findSalesPalnListDTO);



        //第一次遍历 计算出 亚马逊仓总数 深圳仓总数 供应商总数 以及历史销量 和 相关规则
        for(SalePlanItemListByOperationsVO salePlanItem : salePlanItemListByOperationsVOList){

            int salePlanItemId = salePlanItem.getSalePlanItemId();
            int productId = salePlanItem.getProductId();
            int asinId = salePlanItem.getAsinId();
            //自定义的列 和对应值
            List<ItemValVO>  itemValVOList =  salePalnMapperEx.findItemValBySalePlanItemId(salePlanItemId);
            System.out.println("itemValVOList === " + itemValVOList);


            //1: 遍历list 查询亚马逊仓的总数  (可买，清点中，在途中)
            int amzProductNumberSum = getAmzProductNumberSum(asinId);
            salePlanItem.setAmzProductNumberSum(amzProductNumberSum);
            //2: 深圳仓 U8   入库-出库

            int localProductNumberSum = getLocalProductNumberSum(productId);
            salePlanItem.setLocalProductNumberSum(localProductNumberSum);

            //3：供应商 (可发数量 未清数量)
            int supplierProductNumberSum = getSupplierProductNumberSum(productId);
            salePlanItem.setSupplierProductNumberSum(supplierProductNumberSum);


            //1:获取规则
            for(ItemValVO  itemValVO : itemValVOList){
                //历史销量
                System.out.println("============历史销量");
                if(itemValVO.getType() == 1){
                    int lastDayVal = itemValVO.getLastDayVal();
                    //计算历史销量
                    System.out.println("-------------==计算历史销量");
                    Integer lastUnitsOrderSum = salePalnMapperEx.getlastUnitsOrderedSum(lastDayVal,salePlanItem.getAsinId(),salePlanItem.getCountryId());
                    lastUnitsOrderSum = lastUnitsOrderSum == null ? 0 : lastUnitsOrderSum;
                    System.out.println("历史销量 ==" + lastUnitsOrderSum);
                    itemValVO.setItemVal(lastUnitsOrderSum.toString());
//                            lastSaleVolumeSum += lastUnitsOrderSum;

                    //通过销量去查询对应规则 sales_volume_rule 一次只能匹配一个规则
                    SalesVolumeRule salesVolumeRule = salesVolumeRuleMapperEx.findByItemKeyIdAndSales(itemValVO.getItemKeyId(),lastUnitsOrderSum);

                    //拿到对应规则 放入到当前 salePlanItem中
                    if(salesVolumeRule != null){
                        salePlanItem.setSalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());
                    }
                }
            }

            if(salePlanItem.getSalesVolumeRuleId() != 0){
                //通过规则id 获取一组比率
                List<SalesVolumeRuleItemKeyRel>  salesVolumeRuleItemKeyRel =  salesVolumeRuleItemKeyRelMapperEx.findBySalesVolumeRuleId(salePlanItem.getSalesVolumeRuleId());
                System.out.println("salesVolumeRuleItemKeyRel ====" + salesVolumeRuleItemKeyRel);
                //2:添加比率
                for(ItemValVO  itemValVO2 : itemValVOList){
                    for (SalesVolumeRuleItemKeyRel rel : salesVolumeRuleItemKeyRel){
                        if(rel.getItemKeyId() == itemValVO2.getItemKeyId()){
                            System.out.println("rel.getItemKeyRatio() ====" + rel.getItemKeyRatio());
                            itemValVO2.setItemKeyRatio(rel.getItemKeyRatio());
                            break;
                        }
                    }
                }
                //一个 salePlanItem 对应一组(list) itemVal 数据
                salePlanItem.setItemValVOList(itemValVOList);
            }
        }

        //第二次 计算比率
        for(SalePlanItemListByOperationsVO salePlanItem2 : salePlanItemListByOperationsVOList){
            //自定义的列 和对应值
            List<ItemValVO>  itemValVOList =  salePlanItem2.getItemValVOList();
            System.out.println("itemValVOList === " + itemValVOList);

            /**
             * 历史销量*比率 总和
             */
            double lastSaleVolumeRatioSum = 0.0;

            /**
             * 预测销量*比率 总和
             */
            double estSaleVolumeRatioSum = 0.0;
            //历史销量求和 预测销量求和
            for(ItemValVO  itemValVO : itemValVOList){
                if(itemValVO.getType() == 1){
                    int lastSales = Integer.parseInt(itemValVO.getItemVal());
                    // lastSaleVolumeSum += lastSales;
                    lastSaleVolumeRatioSum += lastSales * itemValVO.getItemKeyRatio();
                }else if(itemValVO.getType() == 2){
                    int estSales = Integer.parseInt(itemValVO.getItemVal());
                    //  estSaleVolumeSum += estSales;
                    estSaleVolumeRatioSum += estSales * itemValVO.getItemKeyRatio();
                }
            }

            System.out.println("========计算加权后历史日均");
            System.out.println("========lastSaleVolumeRatioSum = " + lastSaleVolumeRatioSum);
            //计算加权后历史日均
            salePlanItem2.setLastUnitsAvgDay((int)lastSaleVolumeRatioSum);

            System.out.println("========计算加权后预测日均");
            System.out.println("========estSaleVolumeRatioSum = " + estSaleVolumeRatioSum);
            //计算加权后预测日均
            salePlanItem2.setEstUnitsAvgDay((int)estSaleVolumeRatioSum);


        }

        resultBean.setData(salePlanItemListByOperationsVOList);

        return resultBean;
    }


    /**
     * 通过productId 获取 供应商仓库当前产品的 总数
     * @param productId
     * @return
     */
    private int getSupplierProductNumberSum(int productId) {
        int resultNum = 0;

        //入库
        Integer storageNum = inventoryMapperEx.findSupplierStorageNumByProductId(productId);
        //出库
        Integer storageOutNum = inventoryMapperEx.findSupplierStorageNumOutByProductId(productId);

        resultNum = (storageNum == null ? 0 : storageNum) - (storageOutNum == null ? 0 : storageOutNum);

        return resultNum;
    }

    /**
     * 通过productId 获取 深圳仓库当前产品的 总数
     * @param productId
     * @return
     */
    private int getLocalProductNumberSum(int productId) {
        int resultNum = 0;


        Integer storageNum = inventoryMapperEx.findStorageNumByProductId(productId);
        logger.info("u8接收 storageNum  == " + storageNum);

        Integer storageOutNum = inventoryMapperEx.findStorageNumOutByProductId(productId);
        logger.info("u8发出 storageOutNum  == " + storageOutNum);

        resultNum = (storageNum == null ? 0 : storageNum) - (storageOutNum == null ? 0 : storageOutNum);



        return resultNum;
    }

    /**
     * 通过 asinId 获取 亚马逊仓库当前产品的 总数
     * @param asinId
     * @return
     */
    private int getAmzProductNumberSum(int asinId) {
        int resultNum = 0;

        //可卖
        Integer afnFulfillableQuantity = inventoryMapperEx.findAfnFulfillableQuantityByAsinId(asinId);
        logger.info("可卖 afnFulfillableQuantity  == " + afnFulfillableQuantity);

        //在途
        Integer shippedNumber = inventoryMapperEx.findShippedNumberByAsinId(asinId);
        logger.info("在途 afnFulfillableQuantity  == " + shippedNumber);


            resultNum = (afnFulfillableQuantity == null ? 0 : afnFulfillableQuantity) + (shippedNumber == null ? 0 : shippedNumber);


        logger.info("亚马逊总数 resultNum  == " + resultNum);
        return resultNum;

    }

    private ResultBean checkSalePlanItemListByOperationsVO(FindSalesPalnListDTO findSalesPalnListDTO) {
        ResultBean resultBean = new ResultBean();

        if(findSalesPalnListDTO == null){
            resultBean.setMsg("查询失败");
            resultBean.setCode(500);
            return resultBean;
        }
        if(findSalesPalnListDTO.getUserId() == 0){
            resultBean.setMsg("用户Id为空请重新登录！");
            resultBean.setCode(500);
            return resultBean;
        }






        return resultBean;
    }
}
