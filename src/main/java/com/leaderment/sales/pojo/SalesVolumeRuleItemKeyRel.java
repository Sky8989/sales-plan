package com.leaderment.sales.pojo;


import javax.persistence.*;

/**
 * @ClassName SalesVolumeRuleItemKeyRel
 * @Description TODO
 * @Author Lee
 * @Date 19-4-29 上午11:49
 * @Version 1.0
 **/
@Entity
@Table(name = "sales_volume_rule_item_key_rel", schema = "bison")
public class SalesVolumeRuleItemKeyRel {

    @Id
    @Column(name = "sales_volume_rule_id", nullable = false)
    private int salesVolumeRuleId;

    /**
     * 参考当前 历史日均的值 计算
     */

    @Column(name = "item_key_id", nullable = false)
    private int itemKeyId;



    /**
     * 比率
     */
    @Column(name = "item_key_ratio", nullable = false)
    private double itemKeyRatio;

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



    public double getItemKeyRatio() {
        return itemKeyRatio;
    }

    public void setItemKeyRatio(double itemKeyRatio) {
        this.itemKeyRatio = itemKeyRatio;
    }

    @Override
    public String toString() {
        return "SalesVolumeRule{" +
                "salesVolumeRuleId=" + salesVolumeRuleId +
                ", itemKeyId=" + itemKeyId +
                ", itemKeyRatio=" + itemKeyRatio +
                '}';
    }
}
