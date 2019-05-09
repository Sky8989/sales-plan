package com.leaderment.sales.handler;


import com.leaderment.sales.mapper.jpa.SalePlanMapper;
import com.leaderment.sales.mapper.mybatis.SalePalnMapperEx;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.AddSalesVolumeRuleAllVO;
import com.leaderment.sales.service.ItemKeyService;
import com.leaderment.sales.service.SalesPalnSalesChargeViewService;
import com.leaderment.sales.service.SalesPalnSalesViewService;
import com.leaderment.sales.util.entity.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * 销售报表 销售主管视图
 *
 * @author Lee
 * @date 2019-4-26
 */
@Api(description = "SalesPalnSalesChargeViewHandler 销售计划 销售主管视图", tags = "SalesPalnSalesChargeViewHandler")
@RestController
@RequestMapping("/salesPalnSalesChargeViewHandler")
public class SalesPalnSalesChargeViewHandler {

    @Autowired
    SalesPalnSalesChargeViewService salesPalnSalesChargeViewService;

    @ApiOperation(value = "创建销量规则表")
    @PostMapping("/addSalesVolumeRule")
    public ResultBean addSalesVolumeRule(@RequestBody AddSalesVolumeRuleAllVO addSalesVolumeRuleAllVO) {
        System.out.println("======addSalesVolumeRuleAllVO == " + addSalesVolumeRuleAllVO);
        System.out.println("======addSalesVolumeRuleAllVO.getRatioList == " +
                addSalesVolumeRuleAllVO.getSalesVolumeItemKeyRelList());

        ResultBean result =  salesPalnSalesChargeViewService.addSalesVolumeRule(addSalesVolumeRuleAllVO);
        return result;
    }





}
