package com.leaderment.sales.pojo.vo;

public class AddItemKeyVO {
    private int type;
    private int itemKeyId;
    private String itemKey;


    public int getItemKeyId() {
        return itemKeyId;
    }

    public void setItemKeyId(int itemKeyId) {
        this.itemKeyId = itemKeyId;
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

    @Override
    public String toString() {
        return "AddItemKeyVO{" +
                "type=" + type +
                ", itemKeyId=" + itemKeyId +
                ", itemKey='" + itemKey + '\'' +
                '}';
    }
}
