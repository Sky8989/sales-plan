package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.SalesVolumeRule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalesVolumeMapperEx {


    List<SalesVolumeRule> findByItemKeyId(int itemKeyId);

    SalesVolumeRule findByItemKeyIdAndSales(@Param("itemKeyId") int itemKeyId,@Param("lastUnitsOrderSum") Integer lastUnitsOrderSum);
}
