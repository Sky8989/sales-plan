package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.SalePlan;

import com.leaderment.sales.pojo.User;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.ItemValVO;
import com.leaderment.sales.pojo.vo.SalePlanItemListVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalePlanMapperEx {



    int isExistSalePalnItem(int salePlanId);

    List<SalePlanItemListVO> findSalePlanItemList(FindSalesPalnListDTO findSalesPalnListDTO);

    List<ItemValVO> findItemValBySalePlanItemId(int salePlanItemId);

    Integer getlastUnitsOrderedSum(@Param("lastDayVal") int lastDayVal, @Param("asinId")int asinId,  @Param("countryId")int countryId);

    List<ItemValVO> findItemValByUserId(int userId);

    int isExistSalePaln(@Param("userId") int userId, @Param("time") String format);

    List<SalePlanItemListVO> findSalePlanItemListByBusinessUnitId(FindSalesPalnListDTO findSalesPalnListDTO);

    List<User> findUserListByBusinessUnitIdAndStatus(@Param("businessUnitId") int businessUnitId, @Param("status") int status);

    int findCountryIdByCountryName(String countryName);

    int findProductIdByModelNumber(String productModelNumber);

    List<SalePlan> findByUserId(int userId);

    int save(SalePlan salePlan);

    SalePlan findBySalePlanItemId(int salePlanId);
}
