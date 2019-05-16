package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;

import java.util.List;

public interface SalesVolumeRuleItemKeyRelMapperEx {




    List<SalesVolumeRuleItemKeyRel> findBySalesVolumeRuleId(int salesVolumeRuleId);

    int saveAndGetId(SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRel);

    SalesVolumeRuleItemKeyRel findBySalesVolumeRuleIdAndItemKeyId(SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRel);


    int updateItemKeyRatioBySalesVolumeRuleIdAndItemKeyId(SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRel);

    int deleteBySalesVolumeRuleId(int salesVolumeRuleId);

    List<SalesVolumeRuleItemKeyRel> findByItemKeyId(int itemKeyId);
}
