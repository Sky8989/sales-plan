package com.leaderment.sales.service;


import com.leaderment.sales.pojo.SalePlan;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.util.entity.ResultBean;

public interface SalesPalnSalesViewService {

    ResultBean findSalesPlanByUserId(int userId);

    ResultBean findSalePlanItemList(FindSalesPalnListDTO findSalesPalnListDTO);

    ResultBean addSalesPlan(SalePlan salePlan);
}
