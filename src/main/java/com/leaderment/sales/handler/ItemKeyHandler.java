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
        System.out.println("ItemKey = " +itemKey);
        ResultBean result = itemKeyService.addItemKey(itemKey);
        return result;
    }

    @ApiOperation(value = "通过 itemKeyId 更改 自定义列 的状态")
    @GetMapping("/updateItemKeyStatus/{itemKeyId}")
    public ResultBean deleteItemKey(@PathVariable  int itemKeyId) {
        System.out.println("itemKeyId = " +itemKeyId);
        ResultBean result = itemKeyService.UpdateItemKeyStatus(itemKeyId);
        return result;
    }

    @ApiOperation(value = "修改自定义列")
    @PostMapping("/updateItemKey")
    public ResultBean updateItemKey(@RequestBody ItemKey itemKey) {
        System.out.println("ItemKey = " +itemKey);
        ResultBean result = itemKeyService.updateItemKey(itemKey);
        return result;
    }









}
