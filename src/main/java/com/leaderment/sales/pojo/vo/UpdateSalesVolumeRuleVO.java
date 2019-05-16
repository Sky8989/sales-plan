package com.leaderment.sales.pojo.vo;



/**
 * @author leaderment
 */
public class UpdateSalesVolumeRuleVO {

    private int type;
    private int itemKeyId;
    private String itemKey;

    private int minSalesVolume;
    private int maxSalesVolume;



    private int salesVolumeRuleId;

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

    public int getSalesVolumeRuleId() {
        return salesVolumeRuleId;
    }

    public void setSalesVolumeRuleId(int salesVolumeRuleId) {
        this.salesVolumeRuleId = salesVolumeRuleId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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
        return "UpdateSalesVolumeRuleVO{" +
                "type=" + type +
                ", itemKeyId=" + itemKeyId +
                ", itemKey='" + itemKey + '\'' +
                ", minSalesVolume=" + minSalesVolume +
                ", maxSalesVolume=" + maxSalesVolume +
                ", salesVolumeRuleId=" + salesVolumeRuleId +
                ", rationality=" + rationality +
                '}';
    }
}

