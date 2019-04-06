package com.leaderment.sales.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;


/**
 * @ClassName SalesPlan
 * @Description TODO
 * @Author Lee
 * @Date 19-4-26 下午4:03
 * @Version 1.0
 **/
@Entity
@Table(name = "sale_plan", schema = "bison")
public class SalePlan {
    @Id
    @Column(name = "sale_plan_id", nullable = false)
    private int salePlanId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "plan_date", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss") //前端入参
    private String planDate;

    @Column(name = "status", nullable = false)
    private String status;


    @Transient
    private String showSalePlan;

    public String getShowSalePlan() {
        return showSalePlan;
    }

    public void setShowSalePlan(String showSalePlan) {
        this.showSalePlan = showSalePlan;
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

    public String getPlanDate() {
        return planDate;
    }

    public void setPlanDate(String planDate) {
        this.planDate = planDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SalePlan{" +
                "salePlanId=" + salePlanId +
                ", userId=" + userId +
                ", planDate='" + planDate + '\'' +
                ", status='" + status + '\'' +
                ", showSalePlan='" + showSalePlan + '\'' +
                '}';
    }
}
