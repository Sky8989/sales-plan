package com.leaderment.sales.service.impl;

import com.leaderment.sales.mapper.jpa.UserMapper;
import com.leaderment.sales.mapper.mybatis.ItemKeyMapperEx;
import com.leaderment.sales.mapper.mybatis.SalesVolumeRuleItemKeyRelMapperEx;
import com.leaderment.sales.mapper.mybatis.SalesVolumeRuleMapperEx;
import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;
import com.leaderment.sales.pojo.vo.ShowSalesVolumeRuleVO;
import com.leaderment.sales.service.SalesVolumeRuleItemKeyRelService;
import com.leaderment.sales.service.SalesVolumeRuleService;
import com.leaderment.sales.util.entity.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SalesVolumeRuleServiceImpl implements SalesVolumeRuleService {



    @Autowired
    UserMapper userMapper;
    @Autowired
    ItemKeyMapperEx itemKeyMapperEx;
    @Autowired
    SalesVolumeRuleMapperEx salesVolumeRuleMapperEx;

    @Autowired
    SalesVolumeRuleItemKeyRelMapperEx salesVolumeRuleItemKeyRelMapperEx;
    @Autowired
    SalesVolumeRuleItemKeyRelService salesVolumeRuleItemKeyRelService;


    @Transactional
    @Override
    public ResultBean addSalesVolumeRule(SalesVolumeRule salesVolumeRule) {
        ResultBean resultBean = new ResultBean();

        resultBean = checkSalesVolumeRule(salesVolumeRule);
        if(resultBean.getCode().equals(500)){
            return resultBean;
        }
        //新增销量规则
        //通过 itemKeyId 查询出对应自定义列
        ItemKey itemKey = itemKeyMapperEx.findByItemKeyId(salesVolumeRule.getItemKeyId());

        if(itemKey == null){
            resultBean.setCode(500);
            resultBean.setMsg("不存在自定义列");
            return resultBean;
        }
        SalesVolumeRule salesVolumeRuleDb = new SalesVolumeRule();
        salesVolumeRuleDb.setItemKeyId(itemKey.getItemKeyId());
        salesVolumeRuleDb.setMaxSalesVolume(salesVolumeRule.getMaxSalesVolume());
        salesVolumeRuleDb.setMinSalesVolume(salesVolumeRule.getMinSalesVolume());
        //1: 新增销量规则 并以指定 itemKeyId为参考 id
        salesVolumeRuleMapperEx.save(salesVolumeRuleDb);
        salesVolumeRuleDb.setSalesVolumeRuleId(salesVolumeRuleDb.getSalesVolumeRuleId());
        //2: 通过查询出来的自定义列 获取 businessUnitId 查询当前部门下的所有 自定义列
        List<ItemKey> itemKeyList = itemKeyMapperEx.findByBusinessUnitId(itemKey.getBusinessUnitId());

        //3:将新增销量规则 返回的规则id 与 当前部门下所有自定列 去新增关联关系 新增默认比率 默认为0
        if(itemKeyList != null && itemKeyList.size() > 0){
            for(ItemKey itemKey1 : itemKeyList){
                SalesVolumeRuleItemKeyRel  salesVolumeRuleItemKeyRel = new SalesVolumeRuleItemKeyRel();
                salesVolumeRuleItemKeyRel.setSalesVolumeRuleId(salesVolumeRuleDb.getSalesVolumeRuleId());
                salesVolumeRuleItemKeyRel.setItemKeyId(itemKey1.getItemKeyId());

                //新增  比率默认为0
                salesVolumeRuleItemKeyRelMapperEx.saveAndGetId(salesVolumeRuleItemKeyRel);

            }
        }
        /**
         * 最后返回所有数据 一个itemKeyList 一个ShowSalesVolumeRuleVO
         */

      /*  ShowItemKeyAndSalesVolumeRuleAllVO showItemKeyAndSalesVolumeRuleAllVO =
                salesVolumeRuleItemKeyRelService.getShowItemKeyAndSalesVolumeRuleAllVO(salesVolumeRule.getUserId());
        System.out.println("showItemKeyAndSalesVolumeRuleAllVO = " + showItemKeyAndSalesVolumeRuleAllVO);*/

        ShowSalesVolumeRuleVO showSalesVolumeRuleVO  = new ShowSalesVolumeRuleVO();

        showSalesVolumeRuleVO.setItemKeyId(itemKey.getItemKeyId());
        showSalesVolumeRuleVO.setItemKey(itemKey.getItemKey());
        showSalesVolumeRuleVO.setMinSalesVolume(salesVolumeRuleDb.getMinSalesVolume());
        showSalesVolumeRuleVO.setMaxSalesVolume(salesVolumeRuleDb.getMaxSalesVolume());
        showSalesVolumeRuleVO.setType(itemKey.getType());
        showSalesVolumeRuleVO.setStatus(itemKey.getStatus());
        showSalesVolumeRuleVO.setSalesVolumeRuleId(salesVolumeRuleDb.getSalesVolumeRuleId());

        /**
         * 返回 当前规则 以及对应的一行比率
         */
        List<SalesVolumeRuleItemKeyRel> salesVolumeRuleItemKeyRelList =  salesVolumeRuleItemKeyRelMapperEx.findBySalesVolumeRuleId(salesVolumeRuleDb.getSalesVolumeRuleId());

        showSalesVolumeRuleVO.setSalesVolumeRuleRatiolList(salesVolumeRuleItemKeyRelList);


        resultBean.setData(showSalesVolumeRuleVO);




        return resultBean;
    }

    private ResultBean checkSalesVolumeRule(SalesVolumeRule salesVolumeRule) {
        ResultBean resultBean = new ResultBean();

        if(salesVolumeRule == null){
            resultBean.setCode(500);
            resultBean.setMsg("传入的数据为空,新增销量规则失败!");
            return resultBean;
        }
        if(salesVolumeRule.getItemKeyId() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("没有选择自定义参考列,新增失败!");
            return resultBean;
        }if(salesVolumeRule.getMinSalesVolume() < 0 || salesVolumeRule.getMaxSalesVolume() < 0){
            resultBean.setCode(500);
            resultBean.setMsg("最大销量和最小销量小于0");
            return resultBean;
        }if(salesVolumeRule.getMinSalesVolume() >= salesVolumeRule.getMaxSalesVolume()){
            resultBean.setCode(500);
            resultBean.setMsg("最小销量大于最大销量");
            return resultBean;
        }

        return resultBean;
    }
}
