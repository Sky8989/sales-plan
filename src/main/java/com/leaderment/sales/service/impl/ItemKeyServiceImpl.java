package com.leaderment.sales.service.impl;

import com.leaderment.sales.mapper.jpa.ItemKeyMapper;
import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.service.ItemKeyService;
import com.leaderment.sales.util.entity.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemKeyServiceImpl implements ItemKeyService {

    @Autowired
    ItemKeyMapper itemKeyMapper;

    @Override
    public ResultBean findAll() {
        ResultBean resultBean = new ResultBean();
        List<ItemKey> itemKeyList =   itemKeyMapper.findAll();

        if(itemKeyList != null){
            resultBean.setData(itemKeyList);
        }else {
            resultBean.setCode(500);
            resultBean.setMsg("数据库中不存在表头数据");
        }

        return resultBean;
    }
}
