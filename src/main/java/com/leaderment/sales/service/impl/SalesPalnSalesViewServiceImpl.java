package com.leaderment.sales.service.impl;

import com.leaderment.sales.mapper.jpa.SalePlanMapper;
import com.leaderment.sales.mapper.jpa.SalesVolumeRuleItemKeyRelMapper;
import com.leaderment.sales.mapper.jpa.UserMapper;
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

        for (SalePlan saleplan :salePlanList) {
            saleplan.setShowSalePlan(saleplan.getPlanDate().split(" ")[0].concat(" " + user.getUserName() + "销售计划"));
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

                    /**
                     * 历史销量 总和
                     *//*
                    int lastSaleVolumeSum = 0;
                    *//**
                     * 历史销量*比率 总和
                     *//*
                    double lastSaleVolumeRatioSum = 0.0;
                    *//**
                     * 预测销量 总和
                     *//*
                    int estSaleVolumeSum = 0;
                    *//**
                     * 预测销量*比率 总和
                     *//*
                    double estSaleVolumeRatioSum = 0.0;*/
                    // 历史7天 历史30天 预测7天 预测14天 预测30天
                    System.out.println("=====历史7天 历史30天 预测7天 预测14天 预测30天");
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

                            //拿到当前一组数据的比率
                            List<SalesVolumeRuleItemKeyRel>  salesVolumeRuleItemKeyRel =  salesVolumeRuleItemKeyRelMapper.findBySalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());

                            //添加比率
                            for (SalesVolumeRuleItemKeyRel rel : salesVolumeRuleItemKeyRel){
                                if(rel.getItemKeyId() == itemValVO.getItemKeyId()){
                                    itemValVO.setItemKeyRatio(rel.getItemKeyRatio());
                                }
                            }

                            }
                        }


                    salePlanItem.setItemValVOList(itemValVOList);
                }

                //第二次 计算比率
               for(SalePlanItemListVO salePlanItem2 : salePlanItemListVOList){
                       int salePlanItemId = salePlanItem2.getSalePlanItemId();
                       //自定义的列 和对应值
                       List<ItemValVO>  itemValVOList =  salePalnMapperEx.findItemValBySalePlanItemId(salePlanItemId);
                       System.out.println("itemValVOList === " + itemValVOList);

                       /**
                        * 历史销量 总和
                        */
                       int lastSaleVolumeSum = 0;
                       /**
                        * 历史销量*比率 总和
                        */
                       double lastSaleVolumeRatioSum = 0.0;
                       /**
                        * 预测销量 总和
                        */
                       int estSaleVolumeSum = 0;
                       /**
                        * 预测销量*比率 总和
                        */
                       double estSaleVolumeRatioSum = 0.0;
                       // 历史7天 历史30天 预测7天 预测14天 预测30天
                       System.out.println("=====历史7天 历史30天 预测7天 预测14天 预测30天");
                       for(ItemValVO  itemValVO : itemValVOList){
                           //历史销量
                           System.out.println("============历史销量");
                           if(itemValVO.getType() == 1){
                               int lastDayVal = itemValVO.getLastDayVal();
                               //计算历史销量
                               System.out.println("-------------==计算历史销量");
                               Integer lastUnitsOrderSum = salePalnMapperEx.getlastUnitsOrderedSum(lastDayVal,salePlanItem2.getAsinId(),salePlanItem2.getCountryId());
                               lastUnitsOrderSum = lastUnitsOrderSum == null ? 0 : lastUnitsOrderSum;
                               System.out.println("历史销量 ==" + lastUnitsOrderSum);
                               itemValVO.setItemVal(lastUnitsOrderSum.toString());
                               lastSaleVolumeSum += lastUnitsOrderSum;
                           }else if(itemValVO.getType() == 2){
                               estSaleVolumeSum += Integer.parseInt(itemValVO.getItemVal());
                           }

                           List<SalesVolumeRule>  salesVolumeRuleList  = salesVolumeMapperEx.findByItemKeyId(itemValVO.getItemKeyId());
                           /**
                            * 销量规则Id 对应整个 itemValVOList
                            */
                           int salesVolumeRuleId = 0;
                           //指定某一个历史7天 对应的所有规则 0-10 10-30 30-60 60-100 ....
                           System.out.println("===========指定某一个历史7天 对应的所有规则 0-10 10-30 30-60 60-100 ....");
                           for (SalesVolumeRule salesVolumeRule : salesVolumeRuleList) {
                               int min = salesVolumeRule.getMinSalesVolume();
                               int max = salesVolumeRule.getMaxSalesVolume();
                               System.out.println("=== " + salesVolumeRule.getSalesVolumeRuleId());
                               System.out.println("==== min ==" + min);
                               System.out.println("==== max ==" + max);

                               //找到参考历史日均
                               if(itemValVO.getItemKeyId() == salesVolumeRule.getItemKeyId()){
                                   System.out.println("=========找到参考历史日均");
                                   if(itemValVO.getType() == 1) {
                                       int lastSaleVolume = Integer.parseInt(itemValVO.getItemVal());
                                       System.out.println("====lastSaleVolume ===" + lastSaleVolume);
                                       //判断历史日均对应那个 sales_volume_rule_id
                                       System.out.println("====判断历史日均对应那个 sales_volume_rule_id");
                                       if(lastSaleVolume >= min && lastSaleVolume <= max){
                                           salesVolumeRuleId =  salesVolumeRule.getSalesVolumeRuleId();
                                       }
                                   }
                                   break;
                               }
                           }

                           System.out.println("====salesVolumeRuleId = " + salesVolumeRuleId);
                           System.out.println("======通过 salesVolumeRuleId 查询出当前itemValVOList 下各自的比率");
                           //通过 salesVolumeRuleId 查询出当前itemValVOList 下各自的比率
                           List<SalesVolumeRuleItemKeyRel> SalesVolumeRuleItemKeyRelList = salesVolumeRuleItemKeyRelMapperEx.findBySalesVolumeRuleId(salesVolumeRuleId);
                           System.out.println("==========SalesVolumeRuleItemKeyRelList = " + SalesVolumeRuleItemKeyRelList);
                           //计算出比率
                           for(SalesVolumeRuleItemKeyRel  salesVolumeRuleItemKeyRel: SalesVolumeRuleItemKeyRelList){
                               System.out.println("===========计算出比率");
                               System.out.println("salesVolumeRuleItemKeyRel ==" + salesVolumeRuleItemKeyRel);
                               if(salesVolumeRuleItemKeyRel.getItemKeyId() == itemValVO.getItemKeyId()){
                                   if(itemValVO.getType() == 1){
                                       lastSaleVolumeRatioSum += salesVolumeRuleItemKeyRel.getItemKeyRatio() * Integer.parseInt(itemValVO.getItemVal());
                                       System.out.println("lastSaleVolumeRatioSum==" + lastSaleVolumeRatioSum);
                                   }else if(itemValVO.getType() == 2){
                                       estSaleVolumeRatioSum += salesVolumeRuleItemKeyRel.getItemKeyRatio() * Integer.parseInt(itemValVO.getItemVal());
                                       System.out.println("estSaleVolumeRatioSum == " + estSaleVolumeRatioSum);
                                   }
                               }
                           }
                       }

                       System.out.println("========计算加权后历史日均");
                       System.out.println("========lastSaleVolumeSum = " + lastSaleVolumeSum);
                       System.out.println("========lastSaleVolumeSum = " + lastSaleVolumeRatioSum);
                       //计算加权后历史日均
                       salePlanItem2.setLastUnitsAvgDay(lastSaleVolumeSum == 0 ? 0:(int)lastSaleVolumeRatioSum/lastSaleVolumeSum);

                       System.out.println("========计算加权后预测日均");
                       System.out.println("========lastSaleVolumeSum = " + estSaleVolumeSum);
                       System.out.println("========lastSaleVolumeSum = " + estSaleVolumeRatioSum);
                       //计算加权后预测日均
                       salePlanItem2.setLastUnitsAvgDay(estSaleVolumeSum == 0 ? 0 : (int)estSaleVolumeRatioSum/estSaleVolumeSum);

                       System.out.println("========合理性计算");
                       //合理性计算
                       System.out.println("========salePlanItem.getLastUnitsAvgDay() = " + salePlanItem2.getLastUnitsAvgDay());
                       System.out.println("========salePlanItem.getEstUnitsAvgDay()" + salePlanItem2.getEstUnitsAvgDay());
                       salePlanItem2.setRationality(salePlanItem2.getLastUnitsAvgDay() == 0  ? 0 : (salePlanItem2.getEstUnitsAvgDay() - salePlanItem2.getLastUnitsAvgDay()) / salePlanItem.getLastUnitsAvgDay());

                       salePlanItem2.setItemValVOList(itemValVOList);
                   }

                   resultBean.setData(salePlanItemListVOList);

               }else{
                   //1.2不存在拿userId去查询当前负责的产品
               }

           }else {
               resultBean.setMsg("不存在销售计划Id! 请先选择销售计划");
               resultBean.setCode(500);
           }
        }

        return resultBean;
    }
}
