package com.leaderment.sales.service;


import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.dto.UpdateRowSalesVolumeRuleDTO;
import com.leaderment.sales.pojo.vo.AddSalesVolumeRuleAllVO;
import com.leaderment.sales.pojo.vo.DeleteRowSalesVolumeRuleVO;
import com.leaderment.sales.pojo.vo.UpdateRowSalesVolumeRuleVO;
import com.leaderment.sales.util.entity.ResultBean;

/**
 * 运营负责人 Service
 */

public interface SalesPalnSalesChargeViewService {


    ResultBean addSalesVolumeRule(AddSalesVolumeRuleAllVO addSalesVolumeRuleAllVO);

    ResultBean getAllSalesVolumeRuleByUserId(int userId);

    ResultBean updateRowSalesVolumeRule(UpdateRowSalesVolumeRuleDTO updateRowSalesVolumeRuleDTO);

    ResultBean deleteRowSalesVolumeRule(int  salesVolumeRuleId);

    ResultBean getAll(int userId);

    ResultBean findSalePlanItemList(FindSalesPalnListDTO findSalesPalnListDTO);
}
