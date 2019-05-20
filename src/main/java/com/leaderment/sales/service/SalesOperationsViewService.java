package com.leaderment.sales.service;



import com.leaderment.sales.pojo.dto.AddRemarkDTO;
import com.leaderment.sales.pojo.dto.AddTotalSafetyDayDTO;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.SalePlanItemListByOperationsVO;
import com.leaderment.sales.util.entity.ResultBean;

/**
 * 运营 Service
 */

public interface SalesOperationsViewService {


    ResultBean findSalePlanItemList(FindSalesPalnListDTO findSalesPalnListDTO);

    ResultBean addTotalSafetyDay(AddTotalSafetyDayDTO addTotalSafetyDayDTO);

    ResultBean addRemark(AddRemarkDTO addRemarkDTO);
}
