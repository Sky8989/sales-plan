package com.leaderment.sales.pojo.dto;


import java.util.List;

/**
 * @author leaderment
 * 批量修改 salePlanItem 的状态
 */
public class UpdateSalePalnItemStatusDTO {
    private List<Integer> salePlanItemIdList;
    private Integer status;

    public List<Integer> getSalePlanItemIdList() {
        return salePlanItemIdList;
    }

    public void setSalePlanItemIdList(List<Integer> salePlanItemIdList) {
        this.salePlanItemIdList = salePlanItemIdList;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "UpdateSalePalnItemStatusDTO{" +
                "salePlanItemIdList=" + salePlanItemIdList +
                ", status=" + status +
                '}';
    }
}

