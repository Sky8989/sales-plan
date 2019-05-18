package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.vo.SalePlanItemListByOperationsVO;

import java.util.List;

public interface SalePlanItemMapperEx {


    int findNumBySalePlanId(Integer salePlanId);

    List<SalePlanItemListByOperationsVO> findSalePlanItemListByOperationsVOByBusinessUnitId(int businessUnitId);
}
