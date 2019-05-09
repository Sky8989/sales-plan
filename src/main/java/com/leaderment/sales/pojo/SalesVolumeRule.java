package com.leaderment.sales.pojo;


import javax.persistence.*;

/**
 * @ClassName SalesVolumeRule
 * @Description TODO
 * @Author Lee
 * @Date 19-4-29 上午11:49
 * @Version 1.0
 **/

@Entity
@Table(name = "sales_volume_rule", schema = "bison")
public class SalesVolumeRule {


    @Id
    @Column(name = "sales_volume_rule_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int salesVolumeRuleId;

    /**
     * 参考当前 历史日均的值 计算
     */

    @Column(name = "item_key_id", nullable = false)
    private int itemKeyId;


    /**
     * 销量范围最小值
     */
    @Column(name = "min_sales_volume", nullable = false)
    private int minSalesVolume;
    /**
     * 销量范围最大值
     */
    @Column(name = "max_sales_volume", nullable = false)
    private int maxSalesVolume;

    /**
     * 比率
     */
    @Transient
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


    public int getMinSalesVolume() {
        return minSalesVolume;
    }

    public void setMinSalesVolume(int minSalesVolume) {
        this.minSalesVolume = minSalesVolume;
    }

    public int getMaxSalesVolume() {
        return maxSalesVolume;
    }

    public void setMaxSalesVolume(int maxSalesVolume) {
        this.maxSalesVolume = maxSalesVolume;
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
                ", minSalesVolume=" + minSalesVolume +
                ", maxSalesVolume=" + maxSalesVolume +
                ", itemKeyRatio=" + itemKeyRatio +
                '}';
    }
}
