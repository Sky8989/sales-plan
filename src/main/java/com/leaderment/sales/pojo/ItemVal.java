package com.leaderment.sales.pojo;


import javax.persistence.*;

@Entity
@Table(name = "item_val", schema = "bison")
public class ItemVal {

    @Id
    @Column(name = "item_val_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int itemValId;

    @Column(name = "item_key_id", nullable = false)
    private int ItemKeyId;

    @Column(name = "item_val")
    private String itemVal;

    @Column(name = "sale_plan_item_id", nullable = false)
    private int salePlanItemId;


    public int getItemValId() {
        return itemValId;
    }

    public void setItemValId(int itemValId) {
        this.itemValId = itemValId;
    }

    public int getItemKeyId() {
        return ItemKeyId;
    }

    public void setItemKeyId(int itemKeyId) {
        ItemKeyId = itemKeyId;
    }

    public String getItemVal() {
        return itemVal;
    }

    public void setItemVal(String itemVal) {
        this.itemVal = itemVal;
    }

    public int getSalePlanItemId() {
        return salePlanItemId;
    }

    public void setSalePlanItemId(int salePlanItemId) {
        this.salePlanItemId = salePlanItemId;
    }

    @Override
    public String toString() {
        return "ItemVal{" +
                "itemValId=" + itemValId +
                ", ItemKeyId=" + ItemKeyId +
                ", itemVal='" + itemVal + '\'' +
                ", salePlanItemId='" + salePlanItemId + '\'' +
                '}';
    }
}
