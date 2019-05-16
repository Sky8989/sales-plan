package com.leaderment.sales.pojo.vo;


import java.util.ArrayList;
import java.util.List;

public class UpdateItemKeyVO {
    private int type;
    private int itemKeyId;
    private String itemKey;


    List<Object> ratioList = new ArrayList<>();

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

    public List<Object> getRatioList() {
        return ratioList;
    }

    public void setRatioList(List<Object> ratioList) {
        this.ratioList = ratioList;
    }

    @Override
    public String toString() {
        return "UpdateItemKeyVO{" +
                "type=" + type +
                ", itemKeyId=" + itemKeyId +
                ", itemKey='" + itemKey + '\'' +
                ", ratioList=" + ratioList +
                '}';
    }
}
