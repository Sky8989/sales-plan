package com.leaderment.sales.service.impl;

import com.leaderment.sales.mapper.jpa.*;
import com.leaderment.sales.mapper.mybatis.*;
import com.leaderment.sales.pojo.*;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.ItemValVO;
import com.leaderment.sales.pojo.vo.SalePlanItemListVO;
import com.leaderment.sales.service.SalesPalnSalesViewService;
import com.leaderment.sales.util.entity.ResultBean;
import com.leaderment.sales.util.excel.ImportExcelUtil;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.beans.Transient;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SalesPalnSalesViewServiceImpl implements SalesPalnSalesViewService {


    @Autowired
    SalePlanMapper salePlanMapper;

    @Autowired
    ItemValMapper itemValMapper;

    @Autowired
    SalePlanItemMapper salePlanItemMapper;
    @Autowired
    SalePlanItemMapperEx salePlanItemMapperEx;

    @Autowired
    SalesVolumeRuleItemKeyRelMapper salesVolumeRuleItemKeyRelMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    ItemKeyMapper itemKeyMapper;

    @Autowired
    ItemKeyMapperEx itemKeyMapperEx;

    @Autowired
    SalePalnMapperEx salePalnMapperEx;
    @Autowired
    SalesVolumeRuleMapperEx salesVolumeMapperEx;
    @Autowired
    SalesVolumeRuleItemKeyRelMapperEx salesVolumeRuleItemKeyRelMapperEx;

    @Autowired
    ProductSubscriptionMapperEx productSubscriptionMapperEx;




    @Override
    public ResultBean findSalesPlanByUserId(int userId) {
        ResultBean resultBean = new ResultBean();

        User user = userMapper.findByUserId(userId);
        if(user == null){
            resultBean.setCode(500);
            resultBean.setMsg("不存在当前用户");
            return resultBean;
        }

        List<SalePlan> salePlanList = salePlanMapper.findByUserId(userId);

        DateFormat  df2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        for (SalePlan saleplan :salePlanList) {
            String str = df2.format(saleplan.getPlanDate());
            saleplan.setShowSalePlan(str.split(" ")[0].concat(" " + user.getUserName() + "销售计划"));
        }
        resultBean.setData(salePlanList);

        return resultBean;
    }

    @Override
    public ResultBean findSalePlanItemList(FindSalesPalnListDTO findSalesPalnListDTO) {
        ResultBean resultBean = new ResultBean();

        if(findSalesPalnListDTO != null){
           int salePlanId =  findSalesPalnListDTO.getSalePlanId();

           if(salePlanId != 0){
               //1:查询当前salePlanId下的item是否存在
               int flagNum = salePalnMapperEx.isExistSalePalnItem(salePlanId);
               if(flagNum > 0){
                   //1.1存在直接进行查询
                   System.out.println("findSalesPalnListDTO ========== "  + findSalesPalnListDTO);
                   List<SalePlanItemListVO> salePlanItemListVOList  = salePalnMapperEx.findSalePlanItemList(findSalesPalnListDTO);
                   System.out.println("salePlanItemListVOList ========== "  + salePlanItemListVOList);

                   //第一次 存参考库存
                for(SalePlanItemListVO salePlanItem : salePlanItemListVOList){
                       int salePlanItemId = salePlanItem.getSalePlanItemId();
                       //自定义的列 和对应值
                       List<ItemValVO>  itemValVOList =  salePalnMapperEx.findItemValBySalePlanItemId(salePlanItemId);
                       System.out.println("itemValVOList === " + itemValVOList);


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

                               //通过销量去查询对应规则 sales_volume_rule
                               SalesVolumeRule  salesVolumeRule = salesVolumeMapperEx.findByItemKeyIdAndSales(itemValVO.getItemKeyId(),lastUnitsOrderSum);

                               //拿到对应规则 放入到当前 salePlanItem中
                               if(salesVolumeRule != null){
                                   salePlanItem.setSalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());
                               }
                           }
                       }
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
                       salePlanItem.setItemValVOList(itemValVOList);
                   }

                //第二次 计算比率
                for(SalePlanItemListVO salePlanItem2 : salePlanItemListVOList){
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

                       System.out.println("========合理性计算");
                       //合理性计算
                       System.out.println("========salePlanItem.getLastUnitsAvgDay() = " + salePlanItem2.getLastUnitsAvgDay());
                       System.out.println("========salePlanItem.getEstUnitsAvgDay()" + salePlanItem2.getEstUnitsAvgDay());
                       salePlanItem2.setRationality(salePlanItem2.getLastUnitsAvgDay() == 0  ? 0 : (salePlanItem2.getEstUnitsAvgDay() - salePlanItem2.getLastUnitsAvgDay()) / salePlanItem2.getLastUnitsAvgDay());
                   }
                resultBean.setData(salePlanItemListVOList);

               }else{
                   //1.2不存在拿userId去查询当前负责的产品
                   int userId = findSalesPalnListDTO.getUserId();
                   List<SalePlanItemListVO> salePlanItemList  = productSubscriptionMapperEx.findByUserChargeId(userId);
                    //计算历史销量 并通过对应规则id 拿到对应比率
                   for (SalePlanItemListVO salePlanItem: salePlanItemList){

                       List<ItemValVO>  itemValVOList =  salePalnMapperEx.findItemValByUserId(userId);
                        //1:计算历史销量
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

                               //通过销量去查询对应规则 sales_volume_rule
                               SalesVolumeRule  salesVolumeRule = salesVolumeMapperEx.findByItemKeyIdAndSales(itemValVO.getItemKeyId(),lastUnitsOrderSum);

                               //拿到对应规则 放入到当前 salePlanItem中
                               if(salesVolumeRule != null){
                                   salePlanItem.setSalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());
                               }
                           }
                       }


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


                       salePlanItem.setItemValVOList(itemValVOList);
                   }
                   //第二次计算比率
                   for(SalePlanItemListVO salePlanItem2 : salePlanItemList){
                       //自定义的列 和对应值
                       List<ItemValVO>  itemValVOList =  salePlanItem2.getItemValVOList();
                       System.out.println("itemValVOList === " + itemValVOList);

                       /**
                        * 历史销量*比率 总和
                        */
                       double lastSaleVolumeRatioSum = 0.0;
                       //历史销量求和 预测销量求和
                       for(ItemValVO  itemValVO : itemValVOList){
                           if(itemValVO.getType() == 1){
                               int lastSales = Integer.parseInt(itemValVO.getItemVal());
                               // lastSaleVolumeSum += lastSales;
                               lastSaleVolumeRatioSum += lastSales * itemValVO.getItemKeyRatio();
                           }
                       }
                       System.out.println("========计算加权后历史日均");
                       System.out.println("========lastSaleVolumeRatioSum = " + lastSaleVolumeRatioSum);
                       //计算加权后历史日均
                       salePlanItem2.setLastUnitsAvgDay((int)lastSaleVolumeRatioSum);


                   }

                   resultBean.setData(salePlanItemList);
               }

           }else {
               resultBean.setMsg("不存在销售计划Id! 请先选择销售计划");
               resultBean.setCode(500);
           }
        }

        return resultBean;
    }

    @Transactional
    @Override
    public ResultBean addSalesPlan(SalePlan salePlan) {
        ResultBean resultBean = new ResultBean();
        if(salePlan == null){
            resultBean.setMsg("新增失败,保存的对象为空");
            resultBean.setCode(500);
            return resultBean;
        }
        if(salePlan.getUserId() == 0){
            resultBean.setMsg("新增失败,用户为空");
            resultBean.setCode(500);
            return resultBean;
        }
        //判断当前月是否存在销售计划 存在提示不能添加
        DateFormat  df = new SimpleDateFormat("yyyy-MM");

        String time  = df.format(salePlan.getPlanDate());
        Integer num = salePalnMapperEx.isExistSalePaln(salePlan.getUserId(),time);

        if(num != null && num > 0){
            resultBean.setMsg("新增失败, " + df.format(salePlan.getPlanDate()) + "月已存在销售计划,请勿重复添加!!!");
            resultBean.setCode(500);
            return resultBean;
        }

        salePlan.setStatus("1");
        //新增销售计划
        SalePlan  resultSalePlan = salePlanMapper.save(salePlan);
        if(resultSalePlan != null){
            resultBean = findSalesPlanByUserId(resultSalePlan.getUserId());
            resultBean.setMsg("新增销售计划成功");
        }




        return resultBean;
    }


    @Transactional
    @Override
    public ResultBean uplaodSalePlanFile(MultipartFile file, Integer userId, Integer salePlanId)throws IOException {
        ResultBean resultBean = new ResultBean();

        if(userId == 0){
            resultBean.setCode(500);
            resultBean.setMsg("请登录！");
            return resultBean;
        }if(salePlanId == 0){
            resultBean.setCode(500);
            resultBean.setMsg("请选择销售计划！");
            return resultBean;
        }
            ImportExcelUtil.checkFile(file);


        //判断是否已经上传过当月销售计划
        int num = salePlanItemMapperEx.findNumBySalePlanId(salePlanId);
        if(num > 0){
            resultBean.setCode(500);
            resultBean.setMsg("已经上传过当月计划，请不要重复上传！");
            return resultBean;
        }

        //通过userId 获取到相关bu 下的所有预测字段
        List<ItemKey> itemKeyList = itemKeyMapperEx.findByUserIdAndType(userId,2,1);

        //解析 excel

            Workbook workbook = ImportExcelUtil.getWorkBook(file);

            //数据的行数
            int numberOfSheets = workbook.getNumberOfSheets();
            System.out.println("numberOfSheets = " + numberOfSheets);


            int estUnitsPromotionIndex = 0;
            int remarkIndex = 0;
            int countryNameIndex = 0;
            int productModelNumberIndex = 0;

            //预测itemKey的索引 key:itemKey value index
            Map<String,Integer> map = new HashMap<>(itemKeyList.size());

            //后缀
            String  itemKeyNameEst = "天预测日均";


            //1：遍历获取属性名对应的下标
            for (int i = 0; i < numberOfSheets; i++) {
                System.out.println("i = " + i);
                Sheet sheet = workbook.getSheetAt(i);
                Iterator<Row> rowIterator = sheet.iterator();
                //
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                  //  System.out.println("row ===" + row);
                    Cell cell = null;

                    //列名的长度
                    int length = row.getPhysicalNumberOfCells();

                    //遍历获取列名 以及下标
                    for(int j = 0; j < length; j++){
                        cell = row.getCell(j);
                        String cellName = ImportExcelUtil.getCellValue(cell);

                       //预测的名称获取
                       if(itemKeyList != null && itemKeyList.size() > 0 && map.size() != itemKeyList.size()){
                           for(ItemKey itemKey :itemKeyList){
                           String   itemKeyName =   itemKey.getItemKey() + itemKeyNameEst;
                           if(cellName.equals(itemKeyName)){
                               map.put(cellName,j);
                               break;
                           }
                           }
                       }
                        //获取索引
                        switch (cellName){
                            case"productModelNumber":   //国家名
                                productModelNumberIndex = j;
                                break;
                            case"countryName":   //国家名
                                countryNameIndex = j;
                                    break;
                            case"estUnitsPromotion":   //预测活动量
                                estUnitsPromotionIndex = j;
                                    break;
                            case"remark":   //备注
                                remarkIndex = j;
                            break;
                        }
                    }
                }
            }



        List<ItemKey> itemKeyLastList = itemKeyMapperEx.findByUserIdAndType(userId,1,1);
            List<String[]> valuesList = ImportExcelUtil.readExcel(file);

            //循环获取值 并保存到数据库
            for(String[] strings :valuesList){
                System.out.println("strings " + Arrays.toString(strings));

                SalePlanItem salePlanItem = new SalePlanItem(salePlanId,userId);
                salePlanItem.setRemark(strings[strings.length-1]);
                salePlanItem.setRemark(strings[strings.length-3]);
               // salePlanItem.setCountry();
                String countryName =  strings[0];
                String productModelNumber =  strings[1];
                int countryId = salePalnMapperEx.findCountryIdByCountryName(countryName);
                int productId = salePalnMapperEx.findProductIdByModelNumber(productModelNumber);
                salePlanItem.setCountry(countryId);
                salePlanItem.setProductId(productId);



                resultBean =  checkSalePlanItem(salePlanItem);
                if(resultBean.getCode().equals(500)){
                    return resultBean;
                }

                //保存到数据库
                 salePlanItemMapper.save(salePlanItem);
                System.out.println("salePlanItem = " + salePlanItem);


                //保存预测相关数据
                for(ItemKey itemKey : itemKeyList){
                    String key = itemKey.getItemKey() + itemKeyNameEst;
                    int itemKeyId = itemKey.getItemKeyId();
                    int index = map.get(key);
                    if(index != 0){
                       String itemValName = strings[index];
                        ItemVal itemVal = new ItemVal();
                        itemVal.setItemKeyId(itemKeyId);
                        itemVal.setSalePlanItemId(salePlanItem.getSalePlanItemId());
                        itemVal.setItemVal(itemValName);

                        itemValMapper.save(itemVal);

                    }
                }

                //保存 type=1 itemKey 对应val
                for(ItemKey itemKey : itemKeyLastList){
                    ItemVal itemVal = new ItemVal();
                    int itemKeyId = itemKey.getItemKeyId();
                    itemVal.setItemKeyId(itemKeyId);
                    itemVal.setSalePlanItemId(salePlanItem.getSalePlanItemId());
                    itemValMapper.save(itemVal);
                }

            }


        resultBean.setMsg("上传成功，保存成功！！");

        return resultBean;
    }



    private ResultBean checkSalePlanItem(SalePlanItem salePlanItem) {
        ResultBean resultBean = new ResultBean();

        if(salePlanItem == null){
            resultBean.setCode(500);
            resultBean.setMsg("保存失败！");
        }
        if(salePlanItem.getSalePlanId() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("缺少销售计划id！");
            return resultBean;
        }
        if(salePlanItem.getUserId() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("缺少用户id！");
            return resultBean;
        }
        if(salePlanItem.getCountry() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("缺少国家名称！");
            return resultBean;
        }
        if(salePlanItem.getProductId() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("缺少ModelNumber！");
            return resultBean;
        }
        if(salePlanItem.getProductId() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("缺少ModelNumber！");
            return resultBean;
        }

        return resultBean;
    }
}
