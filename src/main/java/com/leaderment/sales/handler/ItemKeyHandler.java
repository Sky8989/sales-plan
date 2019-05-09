package com.leaderment.sales.handler;


import com.leaderment.sales.mapper.jpa.SalePlanMapper;
import com.leaderment.sales.mapper.mybatis.SalePalnMapperEx;
import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.SalePlan;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.service.ItemKeyService;
import com.leaderment.sales.service.SalesPalnSalesViewService;
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
@Api(description = "ItemKeyHandler 自定义列名", tags = "ItemKeyHandler")
@RestController
@RequestMapping("/itemKeyHandler")
public class ItemKeyHandler {

    @Autowired
    SalesPalnSalesViewService salesPalnSalesViewService;

    @Autowired
    ItemKeyService itemKeyService;
    @Autowired
    SalePlanMapper salePlanMapper;
    @Autowired
    SalePalnMapperEx salePalnMapperEx;

    @ApiOperation(value = "添加自定义列")
    @PostMapping("/addItemKey")
    public ResultBean addItemKey(@RequestBody ItemKey itemKey) {
       // System.out.println("======salePlan == " + salePlan);
        ResultBean result = itemKeyService.addItemKey(itemKey);
        return result;
    }

    @ApiOperation(value = "添加自定义列")
    @GetMapping("/findItemKeyAndInfoList")
    public ResultBean findItemKeyAndInfoList() {
        // System.out.println("======salePlan == " + salePlan);
        ResultBean result = itemKeyService.findItemKeyAndInfoList();

        return result;
    }







}
