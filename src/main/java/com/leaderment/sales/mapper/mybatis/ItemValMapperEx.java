package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.ItemVal;
import org.apache.ibatis.annotations.Param;

public interface ItemValMapperEx {


    int save(ItemVal itemVal);

    int updateItemValById(@Param("itemValId") int itemValId, @Param("itemVal")String itemVal);
}
