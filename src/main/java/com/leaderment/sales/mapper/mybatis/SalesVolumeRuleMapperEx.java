package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.vo.UpdateSalesVolumeRuleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SalesVolumeRuleMapperEx {


    List<SalesVolumeRule> findByItemKeyId(int itemKeyId);

    SalesVolumeRule findByItemKeyIdAndSales(@Param("itemKeyId") int itemKeyId,@Param("lastUnitsOrderSum") Integer lastUnitsOrderSum);

    List<UpdateSalesVolumeRuleVO> findByUpdateSalesVolumeRuleVOItemKeyId(int itemKeyId);

    int updateRationalityById(SalesVolumeRule salesVolumeRule);

    int deleteBySalesVolumeRuleIdAndItemKeyId(int salesVolumeRuleId);

    List<SalesVolumeRule> findSalesVolumeRuleByItemKeyId(int itemKeyId);
}
