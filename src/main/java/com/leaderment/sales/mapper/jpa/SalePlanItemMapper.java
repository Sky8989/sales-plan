package com.leaderment.sales.mapper.jpa;


import com.leaderment.sales.pojo.SalePlanItem;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;



@Mapper
public interface SalePlanItemMapper extends JpaRepository<SalePlanItem, Integer> {



}
