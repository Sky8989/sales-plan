package com.leaderment.sales.service;


import com.leaderment.sales.pojo.vo.ShowItemKeyAndSalesVolumeRuleAllVO;
import com.leaderment.sales.util.entity.ResultBean;

public interface SalesVolumeRuleItemKeyRelService {


    ShowItemKeyAndSalesVolumeRuleAllVO getShowItemKeyAndSalesVolumeRuleAllVO(int userId);
    ShowItemKeyAndSalesVolumeRuleAllVO getShowItemKeyAndSalesVolumeRuleAllVOByBusinessUnitId(int businessUnitId);


}
