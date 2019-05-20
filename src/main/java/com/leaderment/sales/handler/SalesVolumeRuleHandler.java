package com.leaderment.sales.handler;


import com.leaderment.sales.mapper.mybatis.SalePlanMapperEx;
import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.service.ItemKeyService;
import com.leaderment.sales.service.SalesVolumeRuleService;
import com.leaderment.sales.util.entity.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 *
 *
 * @author Lee
 * @date 2019-5-06
 */
@Api(description = "SalesVolumeRuleHandler 销量规则控制器", tags = "SalesVolumeRuleHandler")
@RestController
@RequestMapping("/salesVolumeRuleHandler")
public class SalesVolumeRuleHandler {

    @Autowired
    ItemKeyService itemKeyService;

    @Autowired
    SalesVolumeRuleService salesVolumeRuleService;

    @Autowired
    SalePlanMapperEx salePlanMapperEx;

    @ApiOperation(value = "添加销量规则")
    @PostMapping("/addSalesVolumeRule")
    public ResultBean addSalesVolumeRule(@RequestBody SalesVolumeRule salesVolumeRule) {
        System.out.println("salesVolumeRule = " +salesVolumeRule);
        ResultBean result = salesVolumeRuleService.addSalesVolumeRule(salesVolumeRule);
        return result;
    }











}
