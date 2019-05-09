package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.User;
import com.leaderment.sales.pojo.dto.ItemKeyDTO;

import java.util.List;

public interface ItemKeyMapperEx {


    List<ItemKeyDTO> findAll();

    ItemKey getByItemKeyAndBusinessUnitId(String itemKey, User user);
}
