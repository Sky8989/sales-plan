package com.leaderment.sales.pojo.vo;


import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;

import java.util.List;

/**
 * @author leaderment
 * 修改一个 规则 对应的一行 比率  及合理性
 */
public class UpdateRowSalesVolumeRuleVO {

   private SalesVolumeRule salesVolumeRule;
   private List<SalesVolumeRuleItemKeyRel> updateRowSalesVolumeRuleList;


    public SalesVolumeRule getSalesVolumeRule() {
        return salesVolumeRule;
    }

    public void setSalesVolumeRule(SalesVolumeRule salesVolumeRule) {
        this.salesVolumeRule = salesVolumeRule;
    }

    public List<SalesVolumeRuleItemKeyRel> getUpdateRowSalesVolumeRuleList() {
        return updateRowSalesVolumeRuleList;
    }

    public void setUpdateRowSalesVolumeRuleList(List<SalesVolumeRuleItemKeyRel> updateRowSalesVolumeRuleList) {
        this.updateRowSalesVolumeRuleList = updateRowSalesVolumeRuleList;
    }

    @Override
    public String toString() {
        return "UpdateRowSalesVolumeRuleVO{" +
                "salesVolumeRule=" + salesVolumeRule +
                ", updateRowSalesVolumeRuleList=" + updateRowSalesVolumeRuleList +
                '}';
    }
}

