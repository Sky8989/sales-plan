package com.leaderment.sales.pojo.vo;

import java.util.List;
import java.util.Map;

/**
 * @author leaderment
 */
public class AddSalesVolumeRuleItemKeyRelVO {

    private String itemKey;

    private Map<String,Integer> ratioList;


    public String getItemKey() {
        return itemKey;
    }

    public void setItemKey(String itemKey) {
        this.itemKey = itemKey;
    }

//    public List<Map<String, Integer>> getRatioList() {
//        return ratioList;
//    }
//
//    public void setRatioList(List<Map<String, Integer>> ratioList) {
//        this.ratioList = ratioList;
//    }


    public Map<String, Integer> getRatioList() {
        return ratioList;
    }

    public void setRatioList(Map<String, Integer> ratioList) {
        this.ratioList = ratioList;
    }

    @Override
    public String toString() {
        return "AddSalesVolumeRuleItemKeyRelVO{" +
                "itemKey='" + itemKey + '\'' +
                ", ratioList=" + ratioList +
                '}';
    }
}
