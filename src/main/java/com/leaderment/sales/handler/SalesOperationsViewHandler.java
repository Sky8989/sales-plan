package com.leaderment.sales.handler;



import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.SalePlanItemListByOperationsVO;
import com.leaderment.sales.service.SalesOperationsViewService;
import com.leaderment.sales.util.entity.ResultBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 销售报表 采购视图
 *
 * @author Lee
 * @date 2019-4-26
 */
@Api(description = "SalesOperationsViewHandler 销售计划 采购视图", tags = "SalesOperationsViewHandler")
@RestController
@RequestMapping("/salesOperationsViewHandler")
public class SalesOperationsViewHandler {

    @Autowired
    SalesOperationsViewService salesOperationsViewService;

    @ApiOperation(value = "查询 当前BU下的所有产品 的销售计划子项List")
    @PostMapping("/findSalePlanItemList")
    public ResultBean findSalePlanItemList(@RequestBody FindSalesPalnListDTO findSalesPalnListDTO) {
        System.out.println("findSalesPalnListDTO = " + findSalesPalnListDTO);
f
        ResultBean result =  salesOperationsViewService.findSalePlanItemList(indSalesPalnListDTO);
        return result;
    }









}
