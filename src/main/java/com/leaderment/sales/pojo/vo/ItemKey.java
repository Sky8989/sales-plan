package com.leaderment.sales.pojo.vo;

public class ItemKey {
    private int itmeKeyId;
    private String itmeKey;

    public int getItmeKeyId() {
        return itmeKeyId;
    }

    public void setItmeKeyId(int itmeKeyId) {
        this.itmeKeyId = itmeKeyId;
    }

    public String getItmeKey() {
        return itmeKey;
    }

    public void setItmeKey(String itmeKey) {
        this.itmeKey = itmeKey;
    }

    @Override
    public String toString() {
        return "ItemKey{" +
                "itmeKeyId=" + itmeKeyId +
                ", itmeKey='" + itmeKey + '\'' +
                '}';
    }
}
