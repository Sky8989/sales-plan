package com.leaderment.sales.pojo.vo;

public class AddItemKeyVO {
    private int type;
    private String itemKey;

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
                ", itemKey='" + itemKey + '\'' +
                '}';
    }
}
