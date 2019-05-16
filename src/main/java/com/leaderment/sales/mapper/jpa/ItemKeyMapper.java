package com.leaderment.sales.mapper.jpa;

import com.leaderment.sales.pojo.ItemKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemKeyMapper extends JpaRepository<ItemKey, Integer> {

    List<ItemKey> getByBusinessUnitIdAndStatus(int BusinessUnitId,int status);


}
