package com.leaderment.sales.service;


import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.vo.ShowItemKeyVO;
import com.leaderment.sales.util.entity.ResultBean;

import java.util.List;

public interface ItemKeyService {

    ResultBean findAll(int userId);

    ResultBean addItemKey(ItemKey itemKey);

    ResultBean deleteItemKey(int itemKeyId);


    ResultBean UpdateItemKeyStatus(int itemKeyId);

    ResultBean updateItemKey(ItemKey itemKey);


    List<ShowItemKeyVO> getShowItemKeyVOListByUserId(int userId);
}
