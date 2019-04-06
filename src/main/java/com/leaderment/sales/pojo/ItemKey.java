package com.leaderment.sales.pojo;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "item_key", schema = "bison")
public class ItemKey {

    @Id
    @Column(name = "item_key_id", nullable = false)
    private int itemKeyId;

    @Column(name = "item_key", nullable = false)
    private String itemKey;

    @Column(name = "type", nullable = false)
    private int type;

    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "business_unit_id", nullable = false)
    private int businessUnitId;

    @Column(name = "last_day_val")
    private Integer lastDayVal;

    public Integer getLastDayVal() {
        return lastDayVal;
    }

    public void setLastDayVal(Integer lastDayVal) {
        this.lastDayVal = lastDayVal;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBusinessUnitId() {
        return businessUnitId;
    }

    public void setBusinessUnitId(int businessUnitId) {
        this.businessUnitId = businessUnitId;
    }

    @Override
    public String toString() {
        return "ItemKey{" +
                "itemKeyId=" + itemKeyId +
                ", itemKey='" + itemKey + '\'' +
                ", type=" + type +
                ", status=" + status +
                ", businessUnitId=" + businessUnitId +
                ", lastDayVal=" + lastDayVal +
                '}';
    }
}
