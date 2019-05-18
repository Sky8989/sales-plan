package com.leaderment.sales.handler;



import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.dto.UpdateRowSalesVolumeRuleDTO;
import com.leaderment.sales.pojo.vo.AddSalesVolumeRuleAllVO;

import com.leaderment.sales.pojo.vo.DeleteRowSalesVolumeRuleVO;
import com.leaderment.sales.pojo.vo.UpdateRowSalesVolumeRuleVO;
import com.leaderment.sales.service.SalesPalnSalesChargeViewService;

import com.leaderment.sales.service.SalesVolumeRuleItemKeyRelService;
import com.leaderment.sales.util.entity.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;



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

        ResultBean result =  new ResultBean();
//        ResultBean result =  salesPalnSalesChargeViewService.addSalesVolumeRule(addSalesVolumeRuleAllVO);
        return result;
    }

    @ApiOperation(value = "修改数据前 获取所有销量规则以及比率")
    @GetMapping("/getAllSalesVolumeRuleByUserId/{userId}")
    public ResultBean getAllSalesVolumeRuleByUserId(@PathVariable int userId) {
        System.out.println("userId = " + userId);
        //ResultBean result =  salesPalnSalesChargeViewService.getAllSalesVolumeRuleByUserId(userId);
        ResultBean result =  salesPalnSalesChargeViewService.getAll(userId);
        return result;
    }


    @ApiOperation(value = "修改 某规则 对应的一行比率 以及合理性")
    @PostMapping("/updateRowSalesVolumeRule")
    public ResultBean updateRowSalesVolumeRule(@RequestBody UpdateRowSalesVolumeRuleDTO updateRowSalesVolumeRuleDTO) {
        System.out.println("======updateRowSalesVolumeRuleDTO == " + updateRowSalesVolumeRuleDTO);
        System.out.println("======updateRowSalesVolumeRuleDTO.list == " +
                updateRowSalesVolumeRuleDTO.getSalesVolumeRuleRatioList() );

      //  ResultBean result =  new ResultBean();
        ResultBean result =  salesPalnSalesChargeViewService.updateRowSalesVolumeRule(updateRowSalesVolumeRuleDTO);
        return result;
    }

    @ApiOperation(value = "删除一个 规则 以及对应比率 ")
    @DeleteMapping("/deleteRowSalesVolumeRule/{salesVolumeRuleId}")
    public ResultBean deleteRowSalesVolumeRule(@PathVariable int salesVolumeRuleId) {
        System.out.println("======salesVolumeRuleId == " + salesVolumeRuleId);


        //  ResultBean result =  new ResultBean();
        ResultBean result =  salesPalnSalesChargeViewService.deleteRowSalesVolumeRule(salesVolumeRuleId);
        return result;
    }


    @ApiOperation(value = "查询 当前BU下的所有用户 的销售计划子项List")
    @PostMapping("/findSalePlanItemList")
    public ResultBean findSalePlanItemList(@RequestBody FindSalesPalnListDTO findSalesPalnListDTO) {
        System.out.println("findSalesPalnListDTO = " + findSalesPalnListDTO);
        //ResultBean result =  salesPalnSalesChargeViewService.getAllSalesVolumeRuleByUserId(userId);
        ResultBean result =  salesPalnSalesChargeViewService.findSalePlanItemList(findSalesPalnListDTO);
        return result;
    }


    @ApiOperation(value = " 通过主管id 查询bUId  查询在salePlanItem中存在的user列表 ")
    @GetMapping("/findUserList/{userId}")
    public ResultBean findUserList(@PathVariable int userId) {
        System.out.println("======userId == " + userId);
        ResultBean result =  salesPalnSalesChargeViewService.findUserList(userId);
        return result;
    }




}
