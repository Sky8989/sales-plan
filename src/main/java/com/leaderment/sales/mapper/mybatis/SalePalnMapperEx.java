package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.SalePlan;
import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.ItemValVO;
import com.leaderment.sales.pojo.vo.SalePlanItemListVO;
import com.leaderment.sales.pojo.vo.SalePlanVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalePalnMapperEx {

    List<SalePlan> findAll();

    List<ItemKey> findItemTableHead();

    List<SalePlanVO> findSalePlanLit();

    List<ItemValVO> findSalePlanItemLit(int salePlanItemId);

    int isExistSalePalnItem(int salePlanId);

    List<SalePlanItemListVO> findSalePlanItemList(FindSalesPalnListDTO findSalesPalnListDTO);

    List<ItemValVO> findItemValBySalePlanItemId(int salePlanItemId);

    Integer getlastUnitsOrderedSum(@Param("lastDayVal") int lastDayVal, @Param("asinId")int asinId,  @Param("countryId")int countryId);

    List<ItemValVO> findItemValByUserId(int userId);

    int isExistSalePaln(@Param("userId") int userId, @Param("time") String format);

    List<SalePlanItemListVO> findSalePlanItemListByBusinessUnitId(FindSalesPalnListDTO findSalesPalnListDTO);
}
