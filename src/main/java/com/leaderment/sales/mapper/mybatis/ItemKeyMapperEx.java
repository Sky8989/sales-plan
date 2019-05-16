package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.ItemKey;

import com.leaderment.sales.pojo.dto.ItemKeyDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ItemKeyMapperEx {


    List<ItemKeyDTO> findAll();


    int getByItemKeyAndTypeAndBusinessUnitId(ItemKey itemKey);

    List<ItemKey> findByBusinessUnitId(int businessUnitId);

    int updateStatusByItemKeyId(@Param("status") int status, @Param("itemKeyId")int itemKeyId);

    int updateItemKeyAndTypeByItemId(ItemKey itemKey);
}
