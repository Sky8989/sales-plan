package com.leaderment.sales.service.impl;

import com.leaderment.sales.mapper.jpa.ItemKeyMapper;
import com.leaderment.sales.mapper.jpa.SalesVolumeRuleMapper;
import com.leaderment.sales.mapper.jpa.UserMapper;
import com.leaderment.sales.mapper.mybatis.ItemKeyMapperEx;
import com.leaderment.sales.mapper.mybatis.SalesVolumeRuleItemKeyRelMapperEx;
import com.leaderment.sales.mapper.mybatis.SalesVolumeRuleMapperEx;
import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;
import com.leaderment.sales.pojo.User;
import com.leaderment.sales.pojo.vo.ShowItemKeyAndSalesVolumeRuleAllVO;
import com.leaderment.sales.pojo.vo.ShowItemKeyVO;
import com.leaderment.sales.pojo.vo.ShowSalesVolumeRuleVO;
import com.leaderment.sales.service.SalesVolumeRuleItemKeyRelService;
import com.leaderment.sales.service.SalesVolumeRuleService;
import com.leaderment.sales.util.entity.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SalesVolumeRuleItemKeyServiceImpl implements SalesVolumeRuleItemKeyRelService {

    @Autowired
    ItemKeyMapper itemKeyMapper;

    @Autowired
    UserMapper userMapper;
    @Autowired
    ItemKeyMapperEx itemKeyMapperEx;

    @Autowired
    SalesVolumeRuleMapperEx salesVolumeRuleMapperEx;
    @Autowired
    SalesVolumeRuleItemKeyRelMapperEx salesVolumeRuleItemKeyRelMapperEx;


    @Override
    public ShowItemKeyAndSalesVolumeRuleAllVO getShowItemKeyAndSalesVolumeRuleAllVO(int userId) {
        ShowItemKeyAndSalesVolumeRuleAllVO showItemKeyAndSalesVolumeRuleAllVO = new ShowItemKeyAndSalesVolumeRuleAllVO();

        User user = userMapper.findByUserId(userId);

        System.out.println("user =" + user);

       List<ItemKey> itemKeyList =  itemKeyMapperEx.findByBusinessUnitId(user.getBusinessUnitId());

        //通过itemKeyList 查询所有规则 存在一个销售规则列表
        List<SalesVolumeRule> salesVolumeRuleList = new ArrayList<>();
        for (ItemKey itemKey1 : itemKeyList){
            List<SalesVolumeRule> list = salesVolumeRuleMapperEx.findSalesVolumeRuleByItemKeyId(itemKey1.getItemKeyId());
            if(list != null && list.size() > 0){
                salesVolumeRuleList.addAll(list);
            }
        }

        //itemKey list
        List<ShowItemKeyVO>  showItemKeyVOList =  itemKeyListToShowItemKeyVOList(itemKeyList);
        //规则list
        List<ShowSalesVolumeRuleVO> showSalesVolumeRuleVOList = itemKeyListAndSalesVolumeRuleListToShowSalesVolumeRuleVOList(itemKeyList,salesVolumeRuleList);


        showItemKeyAndSalesVolumeRuleAllVO.setShowItemKeyVOList(showItemKeyVOList);
        showItemKeyAndSalesVolumeRuleAllVO.setShowSalesVolumeRuleVOList(showSalesVolumeRuleVOList);

        return showItemKeyAndSalesVolumeRuleAllVO;
    }

    @Override
    public ShowItemKeyAndSalesVolumeRuleAllVO getShowItemKeyAndSalesVolumeRuleAllVOByBusinessUnitId(int businessUnitId) {
        ShowItemKeyAndSalesVolumeRuleAllVO showItemKeyAndSalesVolumeRuleAllVO = new ShowItemKeyAndSalesVolumeRuleAllVO();

        List<ItemKey> itemKeyList =  itemKeyMapperEx.findByBusinessUnitId(businessUnitId);

        //通过itemKeyList 查询所有规则 存在一个销售规则列表
        List<SalesVolumeRule> salesVolumeRuleList = new ArrayList<>();
        for (ItemKey itemKey1 : itemKeyList){
            List<SalesVolumeRule> list = salesVolumeRuleMapperEx.findSalesVolumeRuleByItemKeyId(itemKey1.getItemKeyId());
            if(list != null && list.size() > 0){
                salesVolumeRuleList.addAll(list);
            }
        }

        //itemKey list
        List<ShowItemKeyVO>  showItemKeyVOList =  itemKeyListToShowItemKeyVOList(itemKeyList);
        //规则list
        List<ShowSalesVolumeRuleVO> showSalesVolumeRuleVOList = itemKeyListAndSalesVolumeRuleListToShowSalesVolumeRuleVOList(itemKeyList,salesVolumeRuleList);


        showItemKeyAndSalesVolumeRuleAllVO.setShowItemKeyVOList(showItemKeyVOList);
        showItemKeyAndSalesVolumeRuleAllVO.setShowSalesVolumeRuleVOList(showSalesVolumeRuleVOList);

        return showItemKeyAndSalesVolumeRuleAllVO;

    }

    private List<ShowSalesVolumeRuleVO> itemKeyListAndSalesVolumeRuleListToShowSalesVolumeRuleVOList(List<ItemKey> itemKeyList,
                                                                                                     List<SalesVolumeRule> salesVolumeRuleList) {
        List<ShowSalesVolumeRuleVO> showSalesVolumeRuleVOList = new ArrayList<>();

        if(salesVolumeRuleList != null && salesVolumeRuleList.size() > 0){
            for(SalesVolumeRule salesVolumeRule : salesVolumeRuleList){
                //一个规则
                ShowSalesVolumeRuleVO showSalesVolumeRuleVO = new ShowSalesVolumeRuleVO();
                int salesVolumeRuleId =  salesVolumeRule.getSalesVolumeRuleId();
                int itemKeyId = salesVolumeRule.getItemKeyId();

                showSalesVolumeRuleVO.setSalesVolumeRuleId(salesVolumeRuleId);
                showSalesVolumeRuleVO.setMaxSalesVolume(salesVolumeRule.getMaxSalesVolume());
                showSalesVolumeRuleVO.setMinSalesVolume(salesVolumeRule.getMinSalesVolume());
                showSalesVolumeRuleVO.setRationality(salesVolumeRule.getRationality());

                /**
                 * 获取itemKey
                 */

                ItemKey itemKeyDb = itemKeyMapper.findOne(itemKeyId);

                showSalesVolumeRuleVO.setItemKeyId(itemKeyDb.getItemKeyId());
                showSalesVolumeRuleVO.setItemKey(itemKeyDb.getItemKey());
                showSalesVolumeRuleVO.setType(itemKeyDb.getType());
                showSalesVolumeRuleVO.setStatus(itemKeyDb.getStatus());


                /**
                 * 一组 比率
                 */
                List<SalesVolumeRuleItemKeyRel> salesVolumeRuleItemKeyRelList = new ArrayList<>();

                if(itemKeyList != null && itemKeyList.size() > 0){
                    for(ItemKey itemKey : itemKeyList){
                        SalesVolumeRuleItemKeyRel  salesVolumeRuleItemKeyRel = new SalesVolumeRuleItemKeyRel();
                        salesVolumeRuleItemKeyRel.setSalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());
                        salesVolumeRuleItemKeyRel.setItemKeyId(itemKey.getItemKeyId());


                        SalesVolumeRuleItemKeyRel  salesVolumeRuleItemKeyRelDb =  salesVolumeRuleItemKeyRelMapperEx.findBySalesVolumeRuleIdAndItemKeyId(salesVolumeRuleItemKeyRel);
                        salesVolumeRuleItemKeyRelList.add(salesVolumeRuleItemKeyRelDb);
                    }
                    showSalesVolumeRuleVO.setSalesVolumeRuleRatiolList(salesVolumeRuleItemKeyRelList);
                }

                //最后添加
                showSalesVolumeRuleVOList.add(showSalesVolumeRuleVO);

            }
        }



        System.out.println("showSalesVolumeRuleVOList = " + showSalesVolumeRuleVOList);

        return showSalesVolumeRuleVOList;
    }

    List<ShowItemKeyVO> itemKeyListToShowItemKeyVOList(List<ItemKey> itemKeyList) {
        List<ShowItemKeyVO> showItemKeyVOList = new ArrayList<>();

        for (ItemKey itemKey : itemKeyList){
            ShowItemKeyVO showItemKeyVO = new ShowItemKeyVO();
            showItemKeyVO.setItemKeyId(itemKey.getItemKeyId());
            showItemKeyVO.setItemKey(itemKey.getItemKey());
            showItemKeyVO.setBusinessUnitId(itemKey.getBusinessUnitId());
            showItemKeyVO.setType(itemKey.getType());
            showItemKeyVO.setLastDayVal(itemKey.getLastDayVal());
            showItemKeyVO.setStatus(itemKey.getStatus());
            showItemKeyVOList.add(showItemKeyVO);
        }
        return showItemKeyVOList;

    }
}
