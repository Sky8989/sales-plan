package com.leaderment.sales.service.impl;


import com.leaderment.sales.mapper.jpa.ItemKeyMapper;
import com.leaderment.sales.mapper.jpa.SalesVolumeRuleItemKeyRelMapper;
import com.leaderment.sales.mapper.jpa.SalesVolumeRuleMapper;
import com.leaderment.sales.mapper.jpa.UserMapper;
import com.leaderment.sales.mapper.mybatis.ItemKeyMapperEx;
import com.leaderment.sales.mapper.mybatis.SalesVolumeRuleItemKeyRelMapperEx;
import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;
import com.leaderment.sales.pojo.User;
import com.leaderment.sales.pojo.vo.AddItemKeyVO;
import com.leaderment.sales.pojo.vo.AddSalesVolumeRuleAllVO;
import com.leaderment.sales.pojo.vo.AddSalesVolumeRuleVO;
import com.leaderment.sales.service.SalesPalnSalesChargeViewService;
import com.leaderment.sales.util.entity.ResultBean;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SalesPalnChargeViewServiceImpl  implements SalesPalnSalesChargeViewService {


    @Autowired
    ItemKeyMapperEx itemKeyMapperEx;
    @Autowired
    ItemKeyMapper itemKeyMapper;

    @Autowired
    UserMapper userMapper;


    @Autowired
    SalesVolumeRuleMapper salesVolumeRuleMapper;
    @Autowired
    SalesVolumeRuleItemKeyRelMapper salesVolumeRuleItemKeyRelMapper;
    @Autowired
    SalesVolumeRuleItemKeyRelMapperEx salesVolumeRuleItemKeyRelMapperEx;


    @Transactional
    @Override
    public ResultBean addSalesVolumeRule(AddSalesVolumeRuleAllVO addSalesVolumeRuleAllVO) {

        ResultBean resultBean = new ResultBean();

        resultBean = checkAddSalesVolumeRuleAllVO(addSalesVolumeRuleAllVO);

        if(resultBean.getCode().equals(500)){
            return resultBean;
        }

        //1:通过userId 获取 businessUnitId

        User user = userMapper.findByUserId(addSalesVolumeRuleAllVO.getUserId());

        if(user == null){
            resultBean.setMsg("当前用户不存在,请重新登录!!!");
            resultBean.setCode(500);
            return resultBean;
        }

        //2:通过User 获取BusinessUnitId 在添加自定义列 获取到ItemKeyId
        List<AddItemKeyVO> addItemKeyVOList =  addSalesVolumeRuleAllVO.getItemKeyVOList();
        /**
         *新增 ItemKey后返回 列表
         */
        List<ItemKey> ItemKeyDb = new ArrayList<ItemKey>(addItemKeyVOList.size());

        /**
         * key itemKey  val itemKeyId
         */
        Map<String,Integer> itemKeyDbMap = new HashedMap(addItemKeyVOList.size());
        for(AddItemKeyVO addItemKeyVO : addItemKeyVOList){
            ItemKey itemKey = new ItemKey();
            itemKey.setItemKey(addItemKeyVO.getItemKey());
            itemKey.setStatus(1);
            itemKey.setType(addItemKeyVO.getType());
            itemKey.setLastDayVal(Integer.parseInt(addItemKeyVO.getItemKey()));
            itemKey.setBusinessUnitId(user.getBusinessUnitId());

            //判断数据是否存在,存在不添加
           itemKeyMapper.save(itemKey);
           ItemKeyDb.add(itemKey);
           itemKeyDbMap.put(itemKey.getItemKey(),itemKey.getItemKeyId());
        }
        //3: 拿到itemKeyId  在添加销售规则  返回规则Id salesVolumeRuleId
        List<AddSalesVolumeRuleVO> addSalesVolumeRuleVOList =  addSalesVolumeRuleAllVO.getSalesVolumeList();

        /**
         * key  min-max  val salesVolumeRuleId
         */
        Map<String,Integer> SalesVolumeRuleDbMap = new HashMap<>();
        for (AddSalesVolumeRuleVO addSalesVolumeRuleVO: addSalesVolumeRuleVOList){
            SalesVolumeRule salesVolumeRule = new SalesVolumeRule();
            salesVolumeRule.setMinSalesVolume(addSalesVolumeRuleVO.getMinSalesVolume());
            salesVolumeRule.setMaxSalesVolume(addSalesVolumeRuleVO.getMaxSalesVolume());
            for(ItemKey itemKeyDb : ItemKeyDb){
                //通过itemKey 找到id
                if(itemKeyDb.getItemKey().equals(addSalesVolumeRuleVO.getItemKey())){
                    salesVolumeRule.setItemKeyId(itemKeyDb.getItemKeyId());
                    break;
                }
            }
            //新增销量规则
            salesVolumeRuleMapper.save(salesVolumeRule);
            String key = salesVolumeRule.getMinSalesVolume() +"-"+ salesVolumeRule.getMaxSalesVolume();
            SalesVolumeRuleDbMap.put(key,salesVolumeRule.getSalesVolumeRuleId());

        }

        //4:通过 销量关系(min,max) 获得对应的 salesVolumeRuleId 去新增 销售规则与iteemKeyId的关系 存下比率
        /**
         *  min-max-itemKey=ratio
         */
        List<Object> strList  = addSalesVolumeRuleAllVO.getSalesVolumeItemKeyRelList();
      //  Map<String,Double> map = new HashMap<>();
        for (Object str : strList){
            SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRel = new SalesVolumeRuleItemKeyRel();
            String[] strArray = str.toString().split("=");
            for(int i = 0; i < strArray.length; i++){
                int k = 0;
                int v = 0;
                // 0 和双数进去
                if( i == 0 || i % 2 == 0){
                    //  0,2,4
                    k = i;
                    v = i + 1;
                if(v <= strArray.length - 1){
                    String[] strArr = strArray[k].split("-");
                    double ratio = Double.parseDouble(strArray[v]);
                    System.out.println("ratio = " + ratio);
                    salesVolumeRuleItemKeyRel.setItemKeyRatio(ratio);

                    if(strArr != null && strArr.length == 3){
                        int salesVolumeRuleId =  SalesVolumeRuleDbMap.get(strArr[0]+"-"+strArr[1]);
                        int itemKeyId  = itemKeyDbMap.get(strArr[2]);
                        salesVolumeRuleItemKeyRel.setSalesVolumeRuleId(salesVolumeRuleId);
                        salesVolumeRuleItemKeyRel.setItemKeyId(itemKeyId);
                        System.out.println("itemKeyId =" + itemKeyId);
                        System.out.println("salesVolumeRuleId =" + salesVolumeRuleId);
                        break;
                    }
              //      map.put(strArray[k],Double.parseDouble(strArray[v]));
                }
                }
            }
            //检查
            resultBean =  checkSalesVolumeRuleItemKeyRel(salesVolumeRuleItemKeyRel);
            if(resultBean.getCode().equals(500)){
                return resultBean;
            }
            //新增
            System.out.println("salesVolumeRuleItemKeyRel ==" + salesVolumeRuleItemKeyRel);
           int id =  salesVolumeRuleItemKeyRelMapperEx.saveAndGetId(salesVolumeRuleItemKeyRel);
           if(id > 0){
               System.out.println("新增销量规则比率成功!");
           }
        }

        resultBean.setMsg("新增规则成功");
         //   System.out.println("map ==" + map);
         //   Set<String> set =  map.keySet();

//            for(String key : set){
//                System.out.println("key ==" + key);
//                System.out.println("val ==" + map.get(key));
//            }

        return resultBean;
    }

    private ResultBean checkSalesVolumeRuleItemKeyRel(SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRel) {
        ResultBean resultBean = new ResultBean();
        if(salesVolumeRuleItemKeyRel.getSalesVolumeRuleId() == 0){
            resultBean.setMsg("销售规则与为空,添加销售规则失败!!!");
            resultBean.setCode(500);
            return resultBean;
        }
        if(salesVolumeRuleItemKeyRel.getItemKeyId() == 0){
            resultBean.setMsg("自定义列Id为空,添加销售规则失败!!!");
            resultBean.setCode(500);
            return resultBean;
        }
        if(salesVolumeRuleItemKeyRel.getItemKeyRatio() == 0.0){
            resultBean.setMsg("销售规则与itemKey的比率为空,添加销售规则失败!!!");
            resultBean.setCode(500);
            return resultBean;
        }
        return resultBean;

    }

    private ResultBean checkAddSalesVolumeRuleAllVO(AddSalesVolumeRuleAllVO addSalesVolumeRuleAllVO) {
        ResultBean resultBean = new ResultBean();

        if(addSalesVolumeRuleAllVO == null){
            resultBean.setMsg("上传的数据为空,添加销售规则失败!!!");
            resultBean.setCode(500);
            return resultBean;
        }
        if(addSalesVolumeRuleAllVO.getUserId() <= 0){
            resultBean.setMsg("当前用户不存在,请重新登录!!!");
            resultBean.setCode(500);
            return resultBean;
        }
        if(addSalesVolumeRuleAllVO.getItemKeyVOList() == null || addSalesVolumeRuleAllVO.getItemKeyVOList().size() == 0){
            resultBean.setMsg("上传的自定义列为空,添加销售规则失败");
            resultBean.setCode(500);
            return resultBean;
        }
        if(addSalesVolumeRuleAllVO.getSalesVolumeList() == null || addSalesVolumeRuleAllVO.getSalesVolumeList().size() == 0){
            resultBean.setMsg("上传的销售规则数据为空,添加销售规则失败");
            resultBean.setCode(500);
            return resultBean;
        }
        if(addSalesVolumeRuleAllVO.getSalesVolumeItemKeyRelList() == null || addSalesVolumeRuleAllVO.getSalesVolumeItemKeyRelList().size() == 0){
            resultBean.setMsg("上传的销售规则数据对应比率为空,添加销售规则失败");
            resultBean.setCode(500);
            return resultBean;
        }

        return resultBean;

    }
}
