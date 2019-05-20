package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.SalePlanItem;
import com.leaderment.sales.pojo.dto.AddTotalSafetyDayDTO;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.SalePlanItemListByOperationsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalePlanItemMapperEx {


    int findNumBySalePlanId(Integer salePlanId);

    List<SalePlanItemListByOperationsVO> findSalePlanItemListByOperationsVOByBusinessUnitId(FindSalesPalnListDTO findSalesPalnListDTO);

    int updateStatusBySalePlanItemId(@Param("salePlanItemId") int salePlanItemId, @Param("status")int status);

    Integer addTotalSafetyDayBySalePlanItemId(AddTotalSafetyDayDTO addTotalSafetyDayDTO);

    SalePlanItem findBySalePlanItemId(int salePlanItemId);

    int save(SalePlanItem salePlanItem);
}
