package com.leaderment.sales.mapper.mybatis;


import com.leaderment.sales.pojo.SalePlan;
import com.leaderment.sales.pojo.vo.ItemKey;
import com.leaderment.sales.pojo.vo.ItemValVO;
import com.leaderment.sales.pojo.vo.SalePlanVO;

import java.util.List;

public interface SalePalnMapperEx {

    List<SalePlan> findAll();

    List<ItemKey> findItemTableHead();

    List<SalePlanVO> findSalePlanLit();

    List<ItemValVO> findSalePlanItemLit(int salePlanItemId);
}
