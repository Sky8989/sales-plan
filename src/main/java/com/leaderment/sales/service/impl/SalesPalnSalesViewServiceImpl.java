package com.leaderment.sales.service.impl;

import com.leaderment.sales.mapper.jpa.SalePlanMapper;
import com.leaderment.sales.mapper.jpa.SalesVolumeRuleItemKeyRelMapper;
import com.leaderment.sales.mapper.jpa.UserMapper;
import com.leaderment.sales.mapper.mybatis.ProductSubscriptionMapperEx;
import com.leaderment.sales.mapper.mybatis.SalePalnMapperEx;
import com.leaderment.sales.mapper.mybatis.SalesVolumeMapperEx;
import com.leaderment.sales.mapper.mybatis.SalesVolumeRuleItemKeyRelMapperEx;
import com.leaderment.sales.pojo.*;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.ItemValVO;
import com.leaderment.sales.pojo.vo.SalePlanItemListVO;
import com.leaderment.sales.service.SalesPalnSalesViewService;
import com.leaderment.sales.util.entity.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class SalesPalnSalesViewServiceImpl implements SalesPalnSalesViewService {


    @Autowired
    SalePlanMapper salePlanMapper;

    @Autowired
    SalesVolumeRuleItemKeyRelMapper salesVolumeRuleItemKeyRelMapper;
    @Autowired
    UserMapper userMapper;

    @Autowired
    SalePalnMapperEx salePalnMapperEx;
    @Autowired
    SalesVolumeMapperEx salesVolumeMapperEx;
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
}
