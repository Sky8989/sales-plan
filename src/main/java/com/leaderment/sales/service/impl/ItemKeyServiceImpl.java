package com.leaderment.sales.service.impl;

import com.leaderment.sales.mapper.jpa.ItemKeyMapper;
import com.leaderment.sales.mapper.jpa.SalesVolumeRuleMapper;
import com.leaderment.sales.mapper.jpa.UserMapper;
import com.leaderment.sales.mapper.mybatis.ItemKeyMapperEx;
import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.User;
import com.leaderment.sales.pojo.dto.ItemKeyDTO;
import com.leaderment.sales.service.ItemKeyService;
import com.leaderment.sales.util.entity.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemKeyServiceImpl implements ItemKeyService {

    @Autowired
    ItemKeyMapper itemKeyMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    ItemKeyMapperEx itemKeyMapperEx;

    @Autowired
    SalesVolumeRuleMapper salesVolumeRuleMapper;

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

    @Override
    public ResultBean addItemKey(ItemKey itemKey) {
        ResultBean resultBean = new ResultBean();
        if(itemKey == null){
            resultBean.setCode(500);
            resultBean.setMsg("传入的对象为空");
            return resultBean;
        }

       User user =  userMapper.findByUserId(itemKey.getUserId());
        if(user == null){
            resultBean.setCode(500);
            resultBean.setMsg("用户不存在");
            return resultBean;
        }

        ItemKey itemKeyDb =   itemKeyMapper.getByItemKeyAndBusinessUnitId(itemKey.getItemKey(),user.getBusinessUnitId());

        if(itemKeyDb != null){
            resultBean.setCode(500);
            resultBean.setMsg("已经存在当前自定义列名  " + itemKey.getItemKey());
            return resultBean;
        }else{
            //新增
            ItemKey result =  itemKeyMapper.save(itemKey);
            if(result != null){
                resultBean.setData(result);
            }
        }

        return resultBean;
    }

    @Override
    public ResultBean findItemKeyAndInfoList() {
        ResultBean resultBean = new ResultBean();

        List<ItemKeyDTO>  itemKeyDTOList = itemKeyMapperEx.findAll();
        List<SalesVolumeRule> salesVolumeRuleList =  salesVolumeRuleMapper.findAll();

        for (ItemKeyDTO itemKeyDTO : itemKeyDTOList){



        }

        return resultBean;
    }
}
