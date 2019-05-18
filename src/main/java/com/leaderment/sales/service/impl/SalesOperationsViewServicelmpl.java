package com.leaderment.sales.service.impl;


import com.leaderment.sales.mapper.jpa.SalePlanItemMapper;
import com.leaderment.sales.mapper.jpa.UserMapper;
import com.leaderment.sales.mapper.mybatis.SalePlanItemMapperEx;
import com.leaderment.sales.pojo.SalePlanItem;
import com.leaderment.sales.pojo.User;
import com.leaderment.sales.pojo.dto.FindSalesPalnListDTO;
import com.leaderment.sales.pojo.vo.SalePlanItemListByOperationsVO;
import com.leaderment.sales.service.SalesOperationsViewService;
import com.leaderment.sales.util.entity.ResultBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 运营 Service
 */
@Service
public class SalesOperationsViewServicelmpl implements SalesOperationsViewService {


    @Autowired
    SalePlanItemMapperEx salePlanItemMapperEx;

    @Autowired
    UserMapper userMapper;



    @Override
    public ResultBean findSalePlanItemList(FindSalesPalnListDTO findSalesPalnListDTO) {
        ResultBean resultBean = new ResultBean();

        resultBean = checkSalePlanItemListByOperationsVO(findSalesPalnListDTO);
        if(resultBean.getCode().equals(500)){
            return resultBean;
        }

        User user =  userMapper.findByUserId(findSalesPalnListDTO.getUserId());


        /**
         * 显示结果 list
         */
        List<SalePlanItemListByOperationsVO> salePlanItemListByOperationsVOList =  salePlanItemMapperEx.findSalePlanItemListByOperationsVOByBusinessUnitId(user.getBusinessUnitId());



        //1: 遍历list 查询亚马逊仓的总数  (可买，清点中，在途中)

        //2: 深圳仓 U8   入库-出库

        //3：供应商 (可发数量 未清数量)


        //4：获取对应的 可变列 以及对应的值 计算 历史日均 和 预测日均

        resultBean.setData(salePlanItemListByOperationsVOList);

        return resultBean;
    }

    private ResultBean checkSalePlanItemListByOperationsVO(FindSalesPalnListDTO findSalesPalnListDTO) {
        ResultBean resultBean = new ResultBean();

        if(findSalesPalnListDTO == null){
            resultBean.setMsg("查询失败");
            resultBean.setCode(500);
            return resultBean;
        }
        if(findSalesPalnListDTO.getUserId() == 0){
            resultBean.setMsg("用户Id为空请重新登录！");
            resultBean.setCode(500);
            return resultBean;
        }






        return resultBean;
    }
}
