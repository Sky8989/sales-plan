package com.leaderment.sales.service.impl;

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
import com.leaderment.sales.service.ItemKeyService;
import com.leaderment.sales.service.SalesVolumeRuleItemKeyRelService;
import com.leaderment.sales.util.entity.ResultBean;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ItemKeyServiceImpl implements ItemKeyService {



    @Autowired
    UserMapper userMapper;
    @Autowired
    ItemKeyMapperEx itemKeyMapperEx;

    @Autowired
    SalesVolumeRuleMapperEx salesVolumeRuleMapperEx;
    @Autowired
    SalesVolumeRuleItemKeyRelMapperEx salesVolumeRuleItemKeyRelMapperEx;
    @Autowired
    SalesVolumeRuleItemKeyRelService salesVolumeRuleItemKeyRelService;
    @Autowired
    SalesVolumeRuleItemKeyServiceImpl salesVolumeRuleItemKeyServiceImpl;



    @Override
    public ResultBean findAll(int userId) {
        ResultBean resultBean = new ResultBean();

        User user = userMapper.findByUserId(userId);

        if(user == null){
            resultBean.setCode(500);
            resultBean.setMsg("不存在当前用户!");
            return resultBean;
        }
        /**
         * 没有被禁用 itemKey
         */
        List<ItemKey> itemKeyList =   itemKeyMapperEx.getByBusinessUnitIdAndStatus(user.getBusinessUnitId(),1);

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

        resultBean =  checkItemKey(itemKey);
        if(resultBean.getCode().equals("500")){
            return resultBean;
        }

       User user =  userMapper.findByUserId(itemKey.getUserId());
        System.out.println("user = " + user);
        if(user == null){
            resultBean.setCode(500);
            resultBean.setMsg("数据库不存在当前用户");
            return resultBean;
        }
        itemKey.setBusinessUnitId(user.getBusinessUnitId());

        //先判断当前部门 下指定类型 的自定义列名是否重复
        int resultNum = itemKeyMapperEx.getByItemKeyAndTypeAndBusinessUnitId(itemKey);

        if(resultNum > 0){
            resultBean.setCode(500);
            resultBean.setMsg("已经存在当前自定义列名,请更换其他名称");
            return resultBean;
        }else{
            //新增
            itemKey.setStatus(1);
            itemKey.setLastDayVal(Integer.parseInt(itemKey.getItemKey()));
            itemKeyMapperEx.save(itemKey);
            itemKey.setItemKeyId(itemKey.getItemKeyId());
            System.out.printf("新增== itemKey = " + itemKey);
            if(itemKey.getItemKeyId() > 0){
                //查询当前部门下的所有itemKey
               List<ItemKey> itemKeyList = itemKeyMapperEx.findByBusinessUnitId(itemKey.getBusinessUnitId());


               //通过itemKeyList 查询所有规则 存在一个销售规则列表
                List<SalesVolumeRule> salesVolumeRuleList = new ArrayList<>();
                for (ItemKey itemKey1 : itemKeyList){
                    List<SalesVolumeRule> list = salesVolumeRuleMapperEx.findSalesVolumeRuleByItemKeyId(itemKey1.getItemKeyId());
                    if(list != null && list.size() > 0){
                        salesVolumeRuleList.addAll(list);
                    }
                }

                System.out.println("salesVolumeRuleList  = " + salesVolumeRuleList);
                //给 当前自定义列  对应每一个规则 添加初始化比率
                for(SalesVolumeRule salesVolumeRule : salesVolumeRuleList){
                    SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRel = new SalesVolumeRuleItemKeyRel();
                    salesVolumeRuleItemKeyRel.setItemKeyId(itemKey.getItemKeyId());
                    salesVolumeRuleItemKeyRel.setSalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());
                    //新增  比率默认为0
                    salesVolumeRuleItemKeyRelMapperEx.saveAndGetId(salesVolumeRuleItemKeyRel);
                }

                /**
                 * 最后查询 需要显示的所有内容
                 *
                 */
                ShowItemKeyAndSalesVolumeRuleAllVO showItemKeyAndSalesVolumeRuleAllVO =  salesVolumeRuleItemKeyRelService.getShowItemKeyAndSalesVolumeRuleAllVO(user.getUserId());



               //遍历 ItemKey 查询关联相关的 规则 和 比率
                resultBean.setMsg("新增自定义列成功!");
                resultBean.setData(showItemKeyAndSalesVolumeRuleAllVO);
            }
        }

        return resultBean;
    }

    private ResultBean checkItemKey(ItemKey itemKey) {
        ResultBean resultBean = new ResultBean();

        if(itemKey.getUserId() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("不存在用户请重新登录");
            return resultBean;
        }

        if(itemKey == null){
            resultBean.setCode(500);
            resultBean.setMsg("传入的对象为空");
            return resultBean;
        }
        if(StringUtils.isEmpty(itemKey.getItemKey())){
            resultBean.setCode(500);
            resultBean.setMsg("传入的自定义列名为空");
            return resultBean;
        }
        if(itemKey.getType() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("传入列名类型为空");
            return resultBean;
        }

        return resultBean;

    }



    @Override
    public ResultBean deleteItemKey(int itemKeyId) {
        ResultBean resultBean = new ResultBean();
        if(itemKeyId == 0){
            resultBean.setCode(500);
            resultBean.setMsg("不存在 Id 删除自定义列失败");
        }


        //1:通过itemKeyId去查销量规则表 是否存在对应销量规则

            //1.1存在对应规则 通过遍历销量规则 拿销量规则id 去查 销售规则与ItemKey的关系表 查是否存在 对应比率

                //1.1.1存在对应比率 删除比率

                //1.1.2不存在对应比率 跳过

            //1.2 不存在对应销量规则 跳过

        //2:最后通过ItemKeyId 去删除对应的自定义列


        return resultBean;
    }

    /**
     * 禁用当前自定义列
     * @param itemKeyId
     * @return
     */
    @Override
    public ResultBean UpdateItemKeyStatus(int itemKeyId) {
        ResultBean resultBean = new ResultBean();

        if(itemKeyId == 0){
            resultBean.setMsg("传入的id为空,禁用失败");
            resultBean.setCode(500);
            return resultBean;
        }

        ItemKey itemKey = itemKeyMapperEx.findByItemKeyId(itemKeyId);
        if(itemKey == null){
            resultBean.setMsg("不存在当前自定义列,禁用失败");
            resultBean.setCode(500);
            return resultBean;
        }
        //禁用状态
        int num = itemKeyMapperEx.updateStatusByItemKeyId(-itemKey.getStatus(),itemKeyId);

        if(num > 0){
            if(itemKey.getStatus() == 1){
                resultBean.setMsg("禁用成功!!");
            }else{
                resultBean.setMsg("启用成功!!");
            }

        }else {
            if(itemKey.getStatus() == 1){
                resultBean.setMsg("禁用失败!!");
            }else{
                resultBean.setMsg("启用失败!!");
            }

            resultBean.setCode(500);
        }

        //查询所有数据
        //最后查最新的数据
            resultBean.setData(salesVolumeRuleItemKeyRelService.getShowItemKeyAndSalesVolumeRuleAllVOByBusinessUnitId(itemKey.getBusinessUnitId()));
        return resultBean;
    }

    @Override
    public ResultBean updateItemKey(ItemKey itemKey) {
        ResultBean resultBean = new ResultBean();

        resultBean = checkItemKey(itemKey);
        if(resultBean.getCode().equals(500)){
            return resultBean;
        }

        //进行修改
      int num =  itemKeyMapperEx.updateItemKeyAndTypeByItemId(itemKey);
      if(num > 0){
          resultBean.setMsg("保存成功!");
      }else{
          resultBean.setMsg("保存失败!");
      }



        ShowItemKeyAndSalesVolumeRuleAllVO showItemKeyAndSalesVolumeRuleAllVO = new ShowItemKeyAndSalesVolumeRuleAllVO();

        List<ShowItemKeyVO> showItemKeyVOList = getShowItemKeyVOListByUserId(itemKey.getUserId());

        //默认返回 itemKeyList
        List<SalesVolumeRule> salesVolumeRuleList  = salesVolumeRuleMapperEx.findByItemKeyId(itemKey.getItemKeyId());
        if(salesVolumeRuleList == null){
            showItemKeyAndSalesVolumeRuleAllVO.setShowItemKeyVOList(showItemKeyVOList);
            resultBean.setData(showItemKeyAndSalesVolumeRuleAllVO);
        }else {
            //如果是 销量规则的参考列需要 查询所有显示数据
            showItemKeyAndSalesVolumeRuleAllVO = salesVolumeRuleItemKeyRelService.getShowItemKeyAndSalesVolumeRuleAllVO(itemKey.getUserId());
            resultBean.setData(showItemKeyAndSalesVolumeRuleAllVO);
        }

        return resultBean;
    }

    @Override
    public List<ShowItemKeyVO> getShowItemKeyVOListByUserId(int userId) {


        User user = userMapper.findByUserId(userId);

        System.out.println("user =" + user);

        List<ItemKey> itemKeyList =  itemKeyMapperEx.findByBusinessUnitId(user.getBusinessUnitId());

        List<ShowItemKeyVO>  showItemKeyVOList =  salesVolumeRuleItemKeyServiceImpl.itemKeyListToShowItemKeyVOList(itemKeyList);



        return showItemKeyVOList;
    }
}
