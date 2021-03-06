package com.leaderment.sales.pojo.vo;



/**
 * @author leaderment
 */
public class AddSalesVolumeRuleVO {


    private String itemKey;
    private int type;
    private int minSalesVolume;
    private int maxSalesVolume;

    /**
     * 合理性
     */
    private int rationality;

    public int getRationality() {
        return rationality;
    }

    public void setRationality(int rationality) {
        this.rationality = rationality;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

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
                ", type=" + type +
                ", minSalesVolume=" + minSalesVolume +
                ", maxSalesVolume=" + maxSalesVolume +
                ", rationality=" + rationality +
                '}';
    }
}

