package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.vo.SalePlanItemListVO;


import java.util.List;


public interface ProductSubscriptionMapperEx {


    List<SalePlanItemListVO> findByUserChargeId(int userId);
}
