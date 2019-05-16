package com.leaderment.sales.handler;


import com.leaderment.sales.mapper.jpa.SalePlanMapper;
import com.leaderment.sales.mapper.mybatis.SalePalnMapperEx;
import com.leaderment.sales.pojo.SalePlan;
import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.AddSalesVolumeRuleAllVO;
import com.leaderment.sales.pojo.vo.AddSalesVolumeRuleVO;
import com.leaderment.sales.pojo.vo.ItemValVO;
import com.leaderment.sales.pojo.vo.SalePlanVO;
import com.leaderment.sales.service.ItemKeyService;
import com.leaderment.sales.service.SalesPalnSalesViewService;
import com.leaderment.sales.util.entity.ResultBean;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;




/**
 * 销售报表 销售视图
 *
 * @author Lee
 * @date 2019-4-26
 */
@Api(description = "SalesPalnSalesViewHandler 销售计划 销售视图", tags = "SalesPalnSalesViewHandler")
@RestController
@RequestMapping("/salesPalnSalesViewHandler")
public class SalesPalnSalesViewHandler {

    @Autowired
    SalesPalnSalesViewService salesPalnSalesViewService;

    @Autowired
    ItemKeyService itemKeyService;
    @Autowired
    SalePlanMapper salePlanMapper;
    @Autowired
    SalePalnMapperEx salePalnMapperEx;

    @ApiOperation(value = "创建销售计划")
    @PostMapping("/addSalesPlan")
    public ResultBean addSalesPlan(@RequestBody SalePlan salePlan) {
        ResultBean result = salesPalnSalesViewService.addSalesPlan(salePlan);
        return result;
    }

    @ApiOperation(value = "查询销售计划列表")
    @GetMapping("/findSalesPlanList")
    public ResultBean findSalesPlanList() {

        ResultBean result = new ResultBean();
        return result;
    }


    @ApiOperation(value = "查询指定用户的销售计划")
    @GetMapping("/findSalesPlanByUserId/{userId}")
    public ResultBean findSalesPlanByUserId(@PathVariable int userId) {

        ResultBean result = salesPalnSalesViewService.findSalesPlanByUserId(userId);
        return result;
    }

    @ApiOperation(value = "查询销售计划Item相关List")
    @PostMapping("/findSalePlanItemList")
    public ResultBean findSalePlanItemList(@RequestBody FindSalesPalnListDTO findSalesPalnListDTO) {
        System.out.println("=====findSalePlanItemList====== =");
        System.out.println("=====findSalesPalnListDTO====== ="  + findSalesPalnListDTO);

        ResultBean result = salesPalnSalesViewService.findSalePlanItemList(findSalesPalnListDTO);

        return result;
    }


    @ApiOperation(value = "查询所有ItemKey")
    @GetMapping("/findItemKeyAll")
    public ResultBean findItemKeyAll() {

        ResultBean resultBean = itemKeyService.findAll();
        return resultBean;
    }



}
