package com.leaderment.sales.pojo.vo;

public class ItemValVO {
    private int itmeKeyId;
    private int itmeValId;
    private String itmeKey;
    private double itmeKeyRatio;
    private String itmeVal;


    public int getItmeKeyId() {
        return itmeKeyId;
    }

    public void setItmeKeyId(int itmeKeyId) {
        this.itmeKeyId = itmeKeyId;
    }

    public int getItmeValId() {
        return itmeValId;
    }

    public void setItmeValId(int itmeValId) {
        this.itmeValId = itmeValId;
    }

    public String getItmeKey() {
        return itmeKey;
    }

    public void setItmeKey(String itmeKey) {
        this.itmeKey = itmeKey;
    }

    public double getItmeKeyRatio() {
        return itmeKeyRatio;
    }

    public void setItmeKeyRatio(double itmeKeyRatio) {
        this.itmeKeyRatio = itmeKeyRatio;
    }

    public String getItmeVal() {
        return itmeVal;
    }

    public void setItmeVal(String itmeVal) {
        this.itmeVal = itmeVal;
    }

    @Override
    public String toString() {
        return "ItemValVO{" +
                "itmeKeyId=" + itmeKeyId +
                ", itmeValId=" + itmeValId +
                ", itmeKey='" + itmeKey + '\'' +
                ", itmeKeyRatio=" + itmeKeyRatio +
                ", itmeVal='" + itmeVal + '\'' +
                '}';
    }
}
