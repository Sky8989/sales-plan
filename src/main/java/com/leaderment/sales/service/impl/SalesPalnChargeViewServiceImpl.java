package com.leaderment.sales.service.impl;


import com.leaderment.sales.mapper.jpa.UserMapper;
import com.leaderment.sales.mapper.mybatis.ItemKeyMapperEx;
import com.leaderment.sales.mapper.mybatis.SalePlanMapperEx;
import com.leaderment.sales.mapper.mybatis.SalesVolumeRuleMapperEx;
import com.leaderment.sales.mapper.mybatis.SalesVolumeRuleItemKeyRelMapperEx;
import com.leaderment.sales.pojo.ItemKey;
import com.leaderment.sales.pojo.SalesVolumeRule;
import com.leaderment.sales.pojo.SalesVolumeRuleItemKeyRel;
import com.leaderment.sales.pojo.User;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.dto.UpdateRowSalesVolumeRuleDTO;
import com.leaderment.sales.pojo.vo.*;
import com.leaderment.sales.service.SalesPalnSalesChargeViewService;
import com.leaderment.sales.service.SalesVolumeRuleItemKeyRelService;
import com.leaderment.sales.util.entity.ResultBean;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class SalesPalnChargeViewServiceImpl  implements SalesPalnSalesChargeViewService {


    @Autowired
    ItemKeyMapperEx itemKeyMapperEx;
    @Autowired
    SalesVolumeRuleMapperEx salesVolumeRuleMapperEx;

    @Autowired
    UserMapper userMapper;

    @Autowired
    SalePlanMapperEx salePlanMapperEx;

    @Autowired
    SalesVolumeRuleItemKeyRelMapperEx salesVolumeRuleItemKeyRelMapperEx;

    @Autowired
    SalesVolumeRuleItemKeyRelService salesVolumeRuleItemKeyRelService;

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
        List<ItemKey> itemKeyDbList = new ArrayList<ItemKey>(addItemKeyVOList.size());

        /**
         * key itemKey-type  val itemKeyId
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
            itemKeyMapperEx.save(itemKey);
            itemKey.setItemKeyId(itemKey.getItemKeyId());
            itemKeyDbList.add(itemKey);
           itemKeyDbMap.put(itemKey.getItemKey()+"-"+itemKey.getType(),itemKey.getItemKeyId());
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
            for(ItemKey itemKeyDb : itemKeyDbList){
                //通过itemKey 找到id
                if(itemKeyDb.getItemKey().equals(addSalesVolumeRuleVO.getItemKey())){
                    salesVolumeRule.setItemKeyId(itemKeyDb.getItemKeyId());
                    break;
                }
            }
            //新增销量规则
            salesVolumeRuleMapperEx.save(salesVolumeRule);
            salesVolumeRule.setSalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());
            String key = salesVolumeRule.getMinSalesVolume() +"-"+ salesVolumeRule.getMaxSalesVolume();
            SalesVolumeRuleDbMap.put(key,salesVolumeRule.getSalesVolumeRuleId());

        }
        //System.out.println("SalesVolumeRuleDbMap = " + SalesVolumeRuleDbMap);

        //4:通过 销量关系(min,max) 获得对应的 salesVolumeRuleId 去新增 销售规则与iteemKeyId的关系 存下比率
        /**
         *  min-max-itemKey-type=ratio
         */
        List<Object> strList  = addSalesVolumeRuleAllVO.getSalesVolumeItemKeyRelList();
      //  Map<String,Double> map = new HashMap<>();
        //System.out.println("strList ==" + strList);
        for (Object str : strList){
            //System.out.println("str ==" + str);
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
                    //System.out.println("ratio = " + ratio);
                    salesVolumeRuleItemKeyRel.setItemKeyRatio(ratio);

                    if(strArr != null && strArr.length == 4){
                        int salesVolumeRuleId =  SalesVolumeRuleDbMap.get(strArr[0]+"-"+strArr[1]);
                        int itemKeyId  = itemKeyDbMap.get(strArr[2]+"-"+strArr[3]);
                        salesVolumeRuleItemKeyRel.setSalesVolumeRuleId(salesVolumeRuleId);
                        salesVolumeRuleItemKeyRel.setItemKeyId(itemKeyId);
                        //System.out.println("itemKeyId =" + itemKeyId);
                        //System.out.println("salesVolumeRuleId =" + salesVolumeRuleId);
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
            //System.out.println("salesVolumeRuleItemKeyRel ==" + salesVolumeRuleItemKeyRel);
           int id =  salesVolumeRuleItemKeyRelMapperEx.saveAndGetId(salesVolumeRuleItemKeyRel);
           if(id > 0){
               //System.out.println("新增销量规则比率成功!");
           }
        }

        resultBean.setMsg("新增规则成功");
        return resultBean;
    }

    @Override
    public ResultBean getAllSalesVolumeRuleByUserId(int userId) {
        ResultBean resultBean = new ResultBean();
       User user = userMapper.findByUserId(userId);
        //System.out.println("user = " + user);

        Map<String,Object> resultMap = new HashMap<>(3);

       if(user == null){
           resultBean.setMsg("不存在当前用户,请重新登录");
           resultBean.setCode(500);
           return resultBean;
       }
        /**
         * 自定义列名 list
         */
       List<ItemKey> itemKeyList = itemKeyMapperEx.getByBusinessUnitIdAndStatus(user.getBusinessUnitId(),1);

       if(itemKeyList == null || itemKeyList.size() == 0){
           resultBean.setMsg("新增");
           resultBean.setCode(500);
           return resultBean;
       }



        //System.out.println("itemKeyList = " + itemKeyList);
        //resultMap.put("itemKeyList",itemKeyList);

        /**
         * 用来 显示自定义列名
         *
         */
        List<UpdateItemKeyVO> updateItemKeyVOList = new ArrayList<>(itemKeyList.size());

        /**
         * 规则 list
         */
       List<UpdateSalesVolumeRuleVO> updateSalesVolumeRuleVOList = new ArrayList<UpdateSalesVolumeRuleVO>();

       for(ItemKey itemKey : itemKeyList){
           UpdateItemKeyVO updateItemKeyVO = new UpdateItemKeyVO();
           updateItemKeyVO.setType(itemKey.getType());
           updateItemKeyVO.setItemKeyId(itemKey.getItemKeyId());
           updateItemKeyVO.setItemKey(itemKey.getItemKey());
           if(itemKey.getType() == 1){
               List<UpdateSalesVolumeRuleVO> dbSalesVolumeRuleList =  salesVolumeRuleMapperEx.findByUpdateSalesVolumeRuleVOItemKeyId(itemKey.getItemKeyId());
               //System.out.println("dbSalesVolumeRuleList ==" + dbSalesVolumeRuleList);
               if(dbSalesVolumeRuleList != null && dbSalesVolumeRuleList.size() > 0){
                   updateSalesVolumeRuleVOList.addAll(dbSalesVolumeRuleList);
               }
           }
           updateItemKeyVOList.add(updateItemKeyVO);
       }


       // //System.out.println("salesVolumeRuleList = " + salesVolumeRuleList);
        resultMap.put("salesVolumeRuleList",updateSalesVolumeRuleVOList);
        /**
         * 销量规则 与 itemKey 关系实体 存比率
         */
        List<SalesVolumeRuleItemKeyRel> salesVolumeRuleItemKeyRelList = new ArrayList<>();
        for(UpdateSalesVolumeRuleVO salesVolumeRule : updateSalesVolumeRuleVOList){
            List<SalesVolumeRuleItemKeyRel> dbSalesVolumeRuleItemKeyRelList = salesVolumeRuleItemKeyRelMapperEx.findBySalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());
            //System.out.println("dbSalesVolumeRuleItemKeyRelList ==" + dbSalesVolumeRuleItemKeyRelList);
            if(dbSalesVolumeRuleItemKeyRelList != null && dbSalesVolumeRuleItemKeyRelList.size() > 0){
                salesVolumeRuleItemKeyRelList.addAll(dbSalesVolumeRuleItemKeyRelList);
            }
        }
        //System.out.println("salesVolumeRuleItemKeyRelList ===" + salesVolumeRuleItemKeyRelList);

        //存比率
        for (SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRel : salesVolumeRuleItemKeyRelList){
            for (UpdateItemKeyVO itemKey : updateItemKeyVOList){
                if(itemKey.getItemKeyId() == salesVolumeRuleItemKeyRel.getItemKeyId()){
                    SalesVolumeRule salesVolumeRule =  salesVolumeRuleMapperEx.findBySalesVolumeRuleId(salesVolumeRuleItemKeyRel.getSalesVolumeRuleId());
                    //System.out.println("salesVolumeRule == " + salesVolumeRule);
                    if(salesVolumeRule != null){
                        int min =  salesVolumeRule.getMinSalesVolume();
                        int max =  salesVolumeRule.getMaxSalesVolume();
                        String key = min + "-" + max + "-" + itemKey.getItemKey() + "-" + itemKey.getType();
                        double ratio = salesVolumeRuleItemKeyRel.getItemKeyRatio();
                        itemKey.getRatioList().add(key+":"+ratio);
                        //System.out.println("key ==" + key);
                        //System.out.println("value ==" + ratio);
                        //System.out.println("itemKey ===" + itemKey);
                        break;
                    }
                }
            }
        }
        //System.out.println("updateItemKeyVOList ===" + updateItemKeyVOList);
        resultMap.put("itemKeyList",updateItemKeyVOList);

        //resultMap.put("salesVolumeRuleItemKeyRelList",updateItemKeyVOList);

        resultBean.setData(resultMap);
        return resultBean;
    }

    @Transactional
    @Override
    public ResultBean updateRowSalesVolumeRule(UpdateRowSalesVolumeRuleDTO updateRowSalesVolumeRuleDTO) {

        ResultBean resultBean = new ResultBean();

        resultBean = checkUpdateRowSalesVolumeRuleVO(updateRowSalesVolumeRuleDTO);
        if(resultBean.getCode() == 500){
            return resultBean;
        }
        SalesVolumeRule salesVolumeRule =  new SalesVolumeRule(updateRowSalesVolumeRuleDTO.getSalesVolumeRuleId(),updateRowSalesVolumeRuleDTO.getRationality());
        SalesVolumeRule salesVolumeRuleDb = salesVolumeRuleMapperEx.findBySalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());

        //不相等进行修改
        if(salesVolumeRule.getRationality() != salesVolumeRuleDb.getRationality()){
            int num = salesVolumeRuleMapperEx.updateRationalityById(salesVolumeRule);
            if(num < 0){
                resultBean.setCode(500);
                resultBean.setMsg("修改失败,修改合理性失败");
                //System.out.println("修改合理性失败!!");
            }else{
                //System.out.println("修改合理性成功!!");
                resultBean.setMsg("修改成功!");
            }
        }

        //和数据库中的 比率 进行对比是否修改
        List<SalesVolumeRuleItemKeyRel> salesVolumeRuleItemKeyRelList =  updateRowSalesVolumeRuleDTO.getSalesVolumeRuleRatioList();
        for(SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRel : salesVolumeRuleItemKeyRelList){
            int salesVolumeRuleId = salesVolumeRuleItemKeyRel.getSalesVolumeRuleId();
            int itemKeyId = salesVolumeRuleItemKeyRel.getItemKeyId();
            SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRelDb = salesVolumeRuleItemKeyRelMapperEx.findBySalesVolumeRuleIdAndItemKeyId(salesVolumeRuleItemKeyRel);
            if(salesVolumeRuleItemKeyRelDb != null){
                //同一个id下比率对比
                if(salesVolumeRuleId == salesVolumeRuleItemKeyRelDb.getSalesVolumeRuleId() && itemKeyId == salesVolumeRuleItemKeyRelDb.getItemKeyId()){
                    //不相等进行修改
                    if(salesVolumeRuleItemKeyRel.getItemKeyRatio() != salesVolumeRuleItemKeyRelDb.getItemKeyRatio()){
                        //System.out.println("修改比率  salesVolumeRuleItemKeyRel== " + salesVolumeRuleItemKeyRel);
                        int num = salesVolumeRuleItemKeyRelMapperEx.updateItemKeyRatioBySalesVolumeRuleIdAndItemKeyId(salesVolumeRuleItemKeyRel);
                        if(num > 0){
                            //System.out.println("修改比率成功 === ");
                            resultBean.setMsg("修改成功!");
                        }else{
                            //修改失败
                            resultBean.setCode(500);
                            //System.out.println("修改比率失败 === ");
                            resultBean.setMsg("修改失败,修改比率失败");
                        }
                    }
                }

            }
        }

        ItemKey itemKeyDb = itemKeyMapperEx.findByItemKeyId( updateRowSalesVolumeRuleDTO.getItemKeyId());
        //最后查最新的数据
        if(itemKeyDb != null){
            resultBean.setData(salesVolumeRuleItemKeyRelService.getShowItemKeyAndSalesVolumeRuleAllVOByBusinessUnitId(itemKeyDb.getBusinessUnitId()));
        }



        return resultBean;
    }

    @Transactional
    @Override
    public ResultBean deleteRowSalesVolumeRule(int salesVolumeRuleId) {
        ResultBean resultBean  = new ResultBean();

        if(salesVolumeRuleId == 0 ){
            resultBean.setCode(500);
            resultBean.setMsg("上传的id为空,删除失败!!");
        }
        //先删除比率 在删除规则
            //通过salesVolumeRuleId 删除一组比率
           int num = salesVolumeRuleItemKeyRelMapperEx.deleteBySalesVolumeRuleId(salesVolumeRuleId);

           if(num > 0){

                int num1  =   salesVolumeRuleMapperEx.deleteBySalesVolumeRuleIdAndItemKeyId(salesVolumeRuleId);
                if(num1 > 0){
                    resultBean.setMsg("删除成功!!");
                }else{
                    resultBean.setCode(500);
                    resultBean.setMsg("删除一行规则失败!!");
                }
           }else{
               resultBean.setCode(500);
               resultBean.setMsg("删除一组比率失败!!");
           }

        return resultBean;
    }

    @Override
    public ResultBean getAll(int userId) {
        ResultBean resultBean  = new ResultBean();

        resultBean.setData(salesVolumeRuleItemKeyRelService.getShowItemKeyAndSalesVolumeRuleAllVO(userId));

        return resultBean;
    }

    @Override
    public ResultBean findSalePlanItemList(FindSalesPalnListDTO findSalesPalnListDTO) {
        int userId = findSalesPalnListDTO.getUserId();
        ResultBean resultBean = new ResultBean();

        if(userId == 0){
            resultBean.setCode(500);
            resultBean.setMsg("传入的用户id为空!");
            return resultBean;
        }
        User user = userMapper.findByUserId(userId);
        if(user == null){
            resultBean.setCode(500);
            resultBean.setMsg("不存在当前用户!");
            return resultBean;
        }

        int businessUnitId = user.getBusinessUnitId();

        if(businessUnitId == 0){
            resultBean.setCode(500);
            resultBean.setMsg("当前用户不属于某一个BU!");
            return resultBean;
        }


        //直接通过BUId 查询 salePlanItem List
        findSalesPalnListDTO.setBusinessUnitId(businessUnitId);
        List<SalePlanItemListVO> salePlanItemVOList =  salePlanMapperEx.findSalePlanItemListByBusinessUnitId(findSalesPalnListDTO);

        //System.out.printf("salePlanItemVOList ==" + salePlanItemVOList);
        //第一次 存参考库存
        for(SalePlanItemListVO salePlanItem : salePlanItemVOList){
            int salePlanItemId = salePlanItem.getSalePlanItemId();
            //自定义的列 和对应值
            List<ItemValVO>  itemValVOList =  salePlanMapperEx.findItemValBySalePlanItemId(salePlanItemId);
            //System.out.println("itemValVOList === " + itemValVOList);


            //1:获取规则
            for(ItemValVO  itemValVO : itemValVOList){
                //历史销量
                //System.out.println("============历史销量");
                if(itemValVO.getType() == 1){
                    int lastDayVal = itemValVO.getLastDayVal();
                    //计算历史销量
                    //System.out.println("-------------==计算历史销量");
                    Integer lastUnitsOrderSum = salePlanMapperEx.getlastUnitsOrderedSum(lastDayVal,salePlanItem.getAsinId(),salePlanItem.getCountryId());
                    lastUnitsOrderSum = lastUnitsOrderSum == null ? 0 : lastUnitsOrderSum;
                    //System.out.println("历史销量 ==" + lastUnitsOrderSum);
                    itemValVO.setItemVal(lastUnitsOrderSum.toString());
//                            lastSaleVolumeSum += lastUnitsOrderSum;

                    //通过销量去查询对应规则 sales_volume_rule 一次只能匹配一个规则
//                    SalesVolumeRule  salesVolumeRule = salesVolumeRuleMapperEx.findByItemKeyIdAndSales(itemValVO.getItemKeyId(),lastUnitsOrderSum);
                    SalesVolumeRule  salesVolumeRule = salesVolumeRuleMapperEx.findBySales(lastUnitsOrderSum);

                    //拿到对应规则 放入到当前 salePlanItem中
                    if(salesVolumeRule != null){
                        salePlanItem.setSalesVolumeRuleId(salesVolumeRule.getSalesVolumeRuleId());
                        salePlanItem.setRationality(salesVolumeRule.getRationality());
                    }
                }
            }

            if(salePlanItem.getSalesVolumeRuleId() != 0){
                //通过规则id 获取一组比率
                List<SalesVolumeRuleItemKeyRel>  salesVolumeRuleItemKeyRel =  salesVolumeRuleItemKeyRelMapperEx.findBySalesVolumeRuleId(salePlanItem.getSalesVolumeRuleId());
                //System.out.println("salesVolumeRuleItemKeyRel ====" + salesVolumeRuleItemKeyRel);
                //2:添加比率
                for(ItemValVO  itemValVO2 : itemValVOList){
                    for (SalesVolumeRuleItemKeyRel rel : salesVolumeRuleItemKeyRel){
                        if(rel.getItemKeyId() == itemValVO2.getItemKeyId()){
                            //System.out.println("rel.getItemKeyRatio() ====" + rel.getItemKeyRatio());
                            itemValVO2.setItemKeyRatio(rel.getItemKeyRatio());
                            break;
                        }
                    }
                }
                //一个 salePlanItem 对应一组(list) itemVal 数据
                salePlanItem.setItemValVOList(itemValVOList);
            }
        }


        //第二次 计算比率
        for(SalePlanItemListVO salePlanItem2 : salePlanItemVOList){
            //自定义的列 和对应值
            List<ItemValVO>  itemValVOList =  salePlanItem2.getItemValVOList();
            //System.out.println("itemValVOList === " + itemValVOList);

            /**
             * 历史销量*比率 总和
             */
            double lastSaleVolumeRatioSum = 0.0;

            /**
             * 预测销量*比率 总和
             */
            double estSaleVolumeRatioSum = 0.0;
            //历史销量求和 预测销量求和
            if(itemValVOList != null && itemValVOList.size() > 0){
                for(ItemValVO  itemValVO : itemValVOList){
                    if(itemValVO.getType() == 1){
                        int lastSales = Integer.parseInt(StringUtils.isNotEmpty(itemValVO.getItemVal()) == false ? "0": itemValVO.getItemVal());
                        // lastSaleVolumeSum += lastSales;
                        lastSaleVolumeRatioSum += lastSales * itemValVO.getItemKeyRatio();
                    }else if(itemValVO.getType() == 2){
                        int estSales = Integer.parseInt(StringUtils.isNotEmpty(itemValVO.getItemVal()) == false ? "0" : itemValVO.getItemVal());
                        //  estSaleVolumeSum += estSales;
                        estSaleVolumeRatioSum += estSales * itemValVO.getItemKeyRatio();
                    }
                }
            }


            //System.out.println("========计算加权后历史日均");
            //System.out.println("========lastSaleVolumeRatioSum = " + lastSaleVolumeRatioSum);
            //计算加权后历史日均
            salePlanItem2.setLastUnitsAvgDay((int)lastSaleVolumeRatioSum);

            //System.out.println("========计算加权后预测日均");
            //System.out.println("========estSaleVolumeRatioSum = " + estSaleVolumeRatioSum);
            //计算加权后预测日均
            salePlanItem2.setEstUnitsAvgDay((int)estSaleVolumeRatioSum);

            //System.out.println("========合理性计算");
            //合理性计算
            //System.out.println("========salePlanItem.getLastUnitsAvgDay() = " + salePlanItem2.getLastUnitsAvgDay());
            //System.out.println("========salePlanItem.getEstUnitsAvgDay()" + salePlanItem2.getEstUnitsAvgDay());
            int rationallty = salePlanItem2.getLastUnitsAvgDay() == 0  ? 0 : (salePlanItem2.getEstUnitsAvgDay() - salePlanItem2.getLastUnitsAvgDay()) / salePlanItem2.getLastUnitsAvgDay();
            //System.out.println("==rationallty=== " + rationallty);
            salePlanItem2.setRationality(rationallty);
        }

        //返回一个 salePlanItem 列表
        resultBean.setData(salePlanItemVOList);

        return resultBean;
    }

    @Override
    public ResultBean findUserList(int userId) {

        ResultBean resultBean = new ResultBean();

        if(userId == 0){
            resultBean.setCode(500);
            resultBean.setMsg("传入的用户id为空!");
            return resultBean;
        }
        User user = userMapper.findByUserId(userId);
        if(user == null){
            resultBean.setCode(500);
            resultBean.setMsg("不存在当前用户!");
            return resultBean;
        }

        List<User> userList = salePlanMapperEx.findUserListByBusinessUnitIdAndStatus(user.getBusinessUnitId(),1);
        resultBean.setData(userList);
        return resultBean;
    }

    private ResultBean checkUpdateRowSalesVolumeRuleVO(UpdateRowSalesVolumeRuleDTO updateRowSalesVolumeRuleDTO) {
        ResultBean resultBean = new ResultBean();

        if(updateRowSalesVolumeRuleDTO == null || updateRowSalesVolumeRuleDTO.getSalesVolumeRuleRatioList() == null ||
               updateRowSalesVolumeRuleDTO.getSalesVolumeRuleRatioList().size() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("上传的数据为空,修改失败!");
            return resultBean;
        } if( updateRowSalesVolumeRuleDTO.getRationality() == 0  ){
            resultBean.setCode(500);
            resultBean.setMsg("合理性为空,修改失败!");
            return resultBean;
        }if( updateRowSalesVolumeRuleDTO.getSalesVolumeRuleId() == 0){
            resultBean.setCode(500);
            resultBean.setMsg("销量规则id为空,修改失败!");
            return resultBean;
        }
        //判断规则对应ratio 数据是否正常
        for (SalesVolumeRuleItemKeyRel salesVolumeRuleItemKeyRel: updateRowSalesVolumeRuleDTO.getSalesVolumeRuleRatioList()){
            if(salesVolumeRuleItemKeyRel == null){
                resultBean.setCode(500);
                resultBean.setMsg("上传的比率数据为空,修改失败!");
                return resultBean;
            }
        }

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
        if(salesVolumeRuleItemKeyRel.getItemKeyRatio() < 0.0){
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
