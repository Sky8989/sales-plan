package com.leaderment.sales.pojo.dto;

import java.util.List;

/**
 * 添加安全天数 参数DTO
 */
public class AddTotalSafetyDayDTO {


    /**
     * 销售计划item Id
     */
    private int  salePlanItemId;

    /**
     * 安全天数
     */
    private int  totalSafetyDay;


    public int getSalePlanItemId() {
        return salePlanItemId;
    }

    public void setSalePlanItemId(int salePlanItemId) {
        this.salePlanItemId = salePlanItemId;
    }

    public int getTotalSafetyDay() {
        return totalSafetyDay;
    }

    public void setTotalSafetyDay(int totalSafetyDay) {
        this.totalSafetyDay = totalSafetyDay;
    }

    @Override
    public String toString() {
        return "AddTotalSafetyDayDTO{" +
                "salePlanItemId=" + salePlanItemId +
                ", totalSafetyDay=" + totalSafetyDay +
                '}';
    }
}
