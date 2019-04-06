package com.leaderment.sales.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;


/**
 * @ClassName SalePlanItem
 * @Description TODO
 * @Author Lee
 * @Date 19-4-26 下午4:03
 * @Version 1.0
 **/
@Entity
@Table(name = "sale_plan_Item", schema = "bison")
public class SalePlanItem {
    @Id
    @Column(name = "sale_plan_item_id", nullable = false)
    private int salePlanItemId;

    @Column(name = "sale_plan_id", nullable = false)
    private int salePlanId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "country", nullable = false)
    private int country;

    @Column(name = "last_units_avg_day", nullable = false)
    private int lastUnitsAvgDay;

    @Column(name = "est_units_avg_day", nullable = false)
    private int estUnitsAvgDay;

    @Column(name = "est_units_promotion", nullable = false)
    private int estUnitsPromotion;


    @Column(name = "rationality", nullable = false)
    private int rationality;



    @Column(name = "status", nullable = false)
    private int status;

    @Column(name = "remark", nullable = false)
    private String remark;


    public int getSalePlanItemId() {
        return salePlanItemId;
    }

    public void setSalePlanItemId(int salePlanItemId) {
        this.salePlanItemId = salePlanItemId;
    }

    public int getSalePlanId() {
        return salePlanId;
    }

    public void setSalePlanId(int salePlanId) {
        this.salePlanId = salePlanId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getCountry() {
        return country;
    }

    public void setCountry(int country) {
        this.country = country;
    }

    public int getLastUnitsAvgDay() {
        return lastUnitsAvgDay;
    }

    public void setLastUnitsAvgDay(int lastUnitsAvgDay) {
        this.lastUnitsAvgDay = lastUnitsAvgDay;
    }

    public int getEstUnitsAvgDay() {
        return estUnitsAvgDay;
    }

    public void setEstUnitsAvgDay(int estUnitsAvgDay) {
        this.estUnitsAvgDay = estUnitsAvgDay;
    }

    public int getEstUnitsPromotion() {
        return estUnitsPromotion;
    }

    public void setEstUnitsPromotion(int estUnitsPromotion) {
        this.estUnitsPromotion = estUnitsPromotion;
    }

    public int getRationality() {
        return rationality;
    }

    public void setRationality(int rationality) {
        this.rationality = rationality;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "SalePlanItem{" +
                "salePlanItemId=" + salePlanItemId +
                ", salePlanId=" + salePlanId +
                ", userId=" + userId +
                ", country=" + country +
                ", lastUnitsAvgDay=" + lastUnitsAvgDay +
                ", estUnitsAvgDay=" + estUnitsAvgDay +
                ", estUnitsPromotion=" + estUnitsPromotion +
                ", rationality=" + rationality +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                '}';
    }
}