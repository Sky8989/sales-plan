package com.leaderment.sales.mapper.jpa;

import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalesVolumeRuleItemKeyRelMapper extends JpaRepository<SalesVolumeRuleItemKeyRel, Integer> {


    List<SalesVolumeRuleItemKeyRel> findBySalesVolumeRuleId(int salesVolumeRuleId);

}
