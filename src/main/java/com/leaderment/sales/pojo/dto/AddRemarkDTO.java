package com.leaderment.sales.pojo.dto;

/**
 * 添加备注 参数DTO
 */
public class AddRemarkDTO {


    /**
     * 销售计划item Id
     */
    private int  salePlanItemId;


    private int  userId;

    private String  remark;


    public int getSalePlanItemId() {
        return salePlanItemId;
    }

    public void setSalePlanItemId(int salePlanItemId) {
        this.salePlanItemId = salePlanItemId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "AddRemarkDTO{" +
                "salePlanItemId=" + salePlanItemId +
                ", userId=" + userId +
                ", remark='" + remark + '\'' +
                '}';
    }
}
