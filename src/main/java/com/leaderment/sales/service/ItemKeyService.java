package com.leaderment.sales.service;


import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.util.entity.ResultBean;

public interface ItemKeyService {

    ResultBean findAll();

    ResultBean addItemKey(ItemKey itemKey);

    ResultBean findItemKeyAndInfoList();

}
