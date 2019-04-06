package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;

import java.util.List;

public interface SalesVolumeRuleItemKeyRelMapperEx {


    List<SalesVolumeRuleItemKeyRel> findBySalesVolumeRuleId(int salesVolumeRuleId);
}
