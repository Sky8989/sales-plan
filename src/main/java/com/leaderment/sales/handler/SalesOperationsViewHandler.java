package com.leaderment.sales.handler;



import com.leaderment.sales.service.SalesOperationsViewService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 销售报表 运营视图
 *
 * @author Lee
 * @date 2019-4-26
 */
@Api(description = "SalesOperationsViewHandler 销售计划 运营视图", tags = "SalesOperationsViewHandler")
@RestController
@RequestMapping("/salesOperationsViewHandler")
public class SalesOperationsViewHandler {

    @Autowired
    SalesOperationsViewService salesOperationsViewService;








}
