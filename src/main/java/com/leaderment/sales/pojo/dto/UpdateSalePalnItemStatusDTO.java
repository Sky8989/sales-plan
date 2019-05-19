package com.leaderment.sales.pojo.dto;


import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;

import java.util.List;

/**
 * @author leaderment
 * 修改一个 规则 对应的一行 比率  及合理性
 */
public class UpdateRowSalesVolumeRuleDTO {

    private int salesVolumeRuleId;
    private int itemKeyId;
    private String itemKey;
    private int type;
    private int minSalesVolume;
    private int maxSalesVolume;



    /**
     * 合理性
     */
    private int rationality;

    /**
     * 一组比率
     */
    List<SalesVolumeRuleItemKeyRel> salesVolumeRuleRatioList;

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

    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getRationality() {
        return rationality;
    }

    public void setRationality(int rationality) {
        this.rationality = rationality;
    }

    public List<SalesVolumeRuleItemKeyRel> getSalesVolumeRuleRatioList() {
        return salesVolumeRuleRatioList;
    }

    public void setSalesVolumeRuleRatioList(List<SalesVolumeRuleItemKeyRel> salesVolumeRuleRatioList) {
        this.salesVolumeRuleRatioList = salesVolumeRuleRatioList;
    }

    @Override
    public String toString() {
        return "UpdateRowSalesVolumeRuleDTO{" +
                "salesVolumeRuleId=" + salesVolumeRuleId +
                ", itemKeyId=" + itemKeyId +
                ", itemKey='" + itemKey + '\'' +
                ", type=" + type +
                ", minSalesVolume=" + minSalesVolume +
                ", maxSalesVolume=" + maxSalesVolume +
                ", rationality=" + rationality +
                ", salesVolumeRuleRatioList=" + salesVolumeRuleRatioList +
                '}';
    }
}

