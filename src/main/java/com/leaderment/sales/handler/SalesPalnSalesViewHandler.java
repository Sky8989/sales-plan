package com.leaderment.sales.handler;


import com.leaderment.sales.mapper.jpa.SalePlanMapper;
import com.leaderment.sales.mapper.mybatis.SalePalnMapperEx;
import com.leaderment.sales.pojo.SalePlan;
import com.leaderment.sales.pojo.vo.ItemKey;
import com.leaderment.sales.pojo.vo.ItemValVO;
import com.leaderment.sales.pojo.vo.SalePlanVO;
import com.leaderment.sales.service.SalesPalnSalesViewService;
import com.leaderment.sales.util.entity.ResultBean;
import io.swagger.annotations.Api;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
    SalePlanMapper salePlanMapper;
    @Autowired
    SalePalnMapperEx salePalnMapperEx;

    @ApiOperation(value = "创建销售计划")
    @PostMapping("/addSalesPlan")
    public ResultBean addSalesPlan(HttpServletResponse response, HttpServletRequest request) {
        ResultBean result = new ResultBean();
        return result;


    }

    @ApiOperation(value = "查询销售计划列表")
    @GetMapping("/findSalesPlanList")
    public ResultBean findSalesPlanList() {

        ResultBean result = new ResultBean();
        return result;
    }


    @ApiOperation(value = "查询所有 销售计划")
    @GetMapping("/findAllSalesPlan")
    public ResultBean findAllSalesPlan() {

        ResultBean result = new ResultBean();
        // List<SalePlan> list =  salePlanMapper.findAll();
        List<SalePlan> list = salePalnMapperEx.findAll();
        System.out.println("list = " + list);
        result.setData(list);
        return result;
    }

    @ApiOperation(value = "查询所有 销售计划")
    @GetMapping("/getTableHead")
    public ResultBean getTableHead(int userId,String time) {

        userId = 1;
        time = "2018-04";

        ResultBean result = new ResultBean();
        List<ItemKey> tableHeadList =  salePalnMapperEx.findItemTableHead();

        List<SalePlanVO> salePalnList  =  salePalnMapperEx.findSalePlanLit();

        for (SalePlanVO salePaln :  salePalnList) {
            List<ItemValVO> salePalnitemList  =  salePalnMapperEx.findSalePlanItemLit(salePaln.getSalePlanItemId());
            salePaln.setItemValList(salePalnitemList);
        }




        Map<String,Object> map = new HashMap<String,Object>();
        map.put("tableHeadList",tableHeadList);
        map.put("salePalnList",salePalnList);
        result.setData(map);


        return result;
    }


}
