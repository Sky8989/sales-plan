package com.leaderment.sales.handler;


import com.leaderment.sales.mapper.mybatis.SalePlanMapperEx;
import com.leaderment.sales.pojo.SalePlan;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;

import com.leaderment.sales.pojo.dto.UpdateSalePalnItemStatusDTO;
import com.leaderment.sales.service.ItemKeyService;
import com.leaderment.sales.service.SalesPalnSalesViewService;
import com.leaderment.sales.util.entity.ResultBean;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;



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
    SalePlanMapperEx salePlanMapperEx;

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


    @ApiOperation(value = "通过 userId 查询对应ItemKey")
    @GetMapping("/findItemKeyAll/{userId}")
    public ResultBean findItemKeyAll(@PathVariable int userId) {

        ResultBean resultBean = itemKeyService.findAll(userId);
        return resultBean;
    }


    @ApiOperation(value = "上传销售计划 ")
    @PostMapping("/uplaodSalePlanFile")
    public ResultBean uplaodSalePlanFile(@RequestParam(value = "uplaodSalePlanFile")
                                                     MultipartFile file,
                                         @RequestParam("userId") Integer userId,
                                         @RequestParam("salePlanId")Integer salePlanId) throws IOException {
        System.out.println("=====uplaodSalePlanFile====== =");
        System.out.println("=====file====== ="  + file);
        System.out.println("=====salePlanId====== ="  + salePlanId);
        System.out.println("=====userId====== ="  + userId);

     //   ResultBean result = new ResultBean();
        ResultBean result = salesPalnSalesViewService.uplaodSalePlanFile(file,userId,salePlanId);

        return result;
    }

    @ApiOperation(value = "修改销售计划 子项的状态 ")
    @PutMapping("/updateStatusBySalePlanItemId/{salePlanItemId}/{status}")
    public ResultBean updateStatusBySalePlanItemId(@PathVariable("salePlanItemId") int salePlanItemId,@PathVariable("status") int status){
        System.out.println("=====salePlanItemId====== ="  + salePlanItemId);
        System.out.println("=====status====== ="  + status);

        ResultBean result = salesPalnSalesViewService.updateStatusBySalePlanItemId(salePlanItemId,status);

        return result;
    }

    @ApiOperation(value = "通过 salePlanItemIdList 批量 修改状态 ")
    @PostMapping("/batchUpdateStatusBySalePlanItemIdList")
    public ResultBean batchUpdateStatusBySalePlanItemIdList(@RequestBody UpdateSalePalnItemStatusDTO updateSalePalnItemStatusDTO){
        System.out.println("=====updateSalePalnItemStatusDTO====== ="  + updateSalePalnItemStatusDTO);


        ResultBean result = salesPalnSalesViewService.batchUpdateStatusBySalePlanItemIdList(updateSalePalnItemStatusDTO);

        return result;
    }





}
