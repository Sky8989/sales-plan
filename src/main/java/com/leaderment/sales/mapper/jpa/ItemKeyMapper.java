package com.leaderment.sales.mapper.jpa;

import com.leaderment.sales.pojo.ItemKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemKeyMapper extends JpaRepository<ItemKey, Integer> {

    ItemKey getByItemKeyAndBusinessUnitId(String itemKey, int userId);
}
