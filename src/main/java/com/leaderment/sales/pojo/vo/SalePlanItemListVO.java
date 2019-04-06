package com.leaderment.sales.pojo.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;


/**
 * @ClassName SalePlanItemListVO
 * @Description TODO
 * @Author Lee
 * @Date 19-4-26 下午4:03
 * @Version 1.0
 **/

public class SalePlanItemListVO {

    private int salePlanItemId;


    private int salePlanId;


    private int productId;


    private int userId;

    private String  countryName;

    private int  countryId;

    private int  asinId;

    private String  asin;


    /**
     * 加权后 历史日均
     */
    private int lastUnitsAvgDay;

    /**
     * 加权预测日均
     */
    private int estUnitsAvgDay;


    /**
     * 运营预估活动量
     */
    private int estUnitsPromotion;


    /**
     * 合理性
     */
    private int rationality;


    private int status;

    private String remark;

    private String productModelNumber;

    private List<ItemValVO> ItemValVOList;


    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getAsinId() {
        return asinId;
    }

    public void setAsinId(int asinId) {
        this.asinId = asinId;
    }

    public String getProductModelNumber() {
        return productModelNumber;
    }

    public void setProductModelNumber(String productModelNumber) {
        this.productModelNumber = productModelNumber;
    }

    public List<ItemValVO> getItemValVOList() {
        return ItemValVOList;
    }

    public void setItemValVOList(List<ItemValVO> itemValVOList) {
        ItemValVOList = itemValVOList;
    }

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

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    @Override
    public String toString() {
        return "SalePlanItemListVO{" +
                "salePlanItemId=" + salePlanItemId +
                ", salePlanId=" + salePlanId +
                ", productId=" + productId +
                ", userId=" + userId +
                ", countryName='" + countryName + '\'' +
                ", asin='" + asin + '\'' +
                ", lastUnitsAvgDay=" + lastUnitsAvgDay +
                ", estUnitsAvgDay=" + estUnitsAvgDay +
                ", estUnitsPromotion=" + estUnitsPromotion +
                ", rationality=" + rationality +
                ", status=" + status +
                ", remark='" + remark + '\'' +
                ", productModelNumber='" + productModelNumber + '\'' +
                ", ItemValVOList=" + ItemValVOList +
                '}';
    }
}
