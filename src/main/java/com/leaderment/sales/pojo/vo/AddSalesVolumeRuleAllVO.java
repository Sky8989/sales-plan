package com.leaderment.sales.pojo.vo;

import java.util.List;

/**
 * @author
 */
public class AddSalesVolumeRuleAllVO {



    private int userId;

    /**
     * 销售规则list  min-max-itemKey-type   itemKey参考 自定义列
     */
    private List<AddSalesVolumeRuleVO> salesVolumeList;

    /**
     * 销售规则和itemKey 关系List 结构 min-max-itemKey-type=ratio
     */
    private List<Object> salesVolumeItemKeyRelList;


    /**
     * itemKey 和 type
     */
    private List<AddItemKeyVO> itemKeyVOList;



    public List<AddItemKeyVO> getItemKeyVOList() {
        return itemKeyVOList;
    }

    public void setItemKeyVOList(List<AddItemKeyVO> itemKeyVOList) {
        this.itemKeyVOList = itemKeyVOList;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public List<Object> getSalesVolumeItemKeyRelList() {
        return salesVolumeItemKeyRelList;
    }

    public void setSalesVolumeItemKeyRelList(List<Object> salesVolumeItemKeyRelList) {
        this.salesVolumeItemKeyRelList = salesVolumeItemKeyRelList;
    }

    public List<AddSalesVolumeRuleVO> getSalesVolumeList() {
        return salesVolumeList;
    }

    public void setSalesVolumeList(List<AddSalesVolumeRuleVO> salesVolumeList) {
        this.salesVolumeList = salesVolumeList;
    }

    @Override
    public String toString() {
        return "AddSalesVolumeRuleAllVO{" +
                "userId=" + userId +
                ", salesVolumeList=" + salesVolumeList +
                ", salesVolumeItemKeyRelList=" + salesVolumeItemKeyRelList +
                ", itemKeyVOList=" + itemKeyVOList +
                '}';
    }
}
