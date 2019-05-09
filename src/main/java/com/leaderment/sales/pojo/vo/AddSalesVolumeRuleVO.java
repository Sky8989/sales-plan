package com.leaderment.sales.pojo.vo;

import java.util.List;

/**
 * @author leaderment
 */
public class AddSalesVolumeRuleVO {


    private String itemKey;
    private int minSalesVolume;
    private int maxSalesVolume;


    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
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

    @Override
    public String toString() {
        return "AddSalesVolumeRuleVO{" +
                "itemKey='" + itemKey + '\'' +
                ", minSalesVolume=" + minSalesVolume +
                ", maxSalesVolume=" + maxSalesVolume +
                '}';
    }
}
