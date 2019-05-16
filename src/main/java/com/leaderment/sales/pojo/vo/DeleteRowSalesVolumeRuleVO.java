package com.leaderment.sales.pojo.vo;


/**
 * @author leaderment
 * 删除一个 规则 以及对应比率
 */
public class DeleteRowSalesVolumeRuleVO {


    private int salesVolumeRuleId;
    private int itemKeyId;

    public int getSalesVolumeRuleId() {
        return salesVolumeRuleId;
    }

    public void setSalesVolumeRuleId(int salesVolumeRuleId) {
        this.salesVolumeRuleId = salesVolumeRuleId;
    }

    public int getItemKeyId() {
        return itemKeyId;
    }

    public void setItemKeyId(int itemKeyId) {
        this.itemKeyId = itemKeyId;
    }

    @Override
    public String toString() {
        return "DeleteRowSalesVolumeRuleVO{" +
                "salesVolumeRuleId=" + salesVolumeRuleId +
                ", itemKeyId=" + itemKeyId +
                '}';
    }
}

