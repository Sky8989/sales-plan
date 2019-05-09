package com.leaderment.sales.mapper.jpa;

import com.leaderment.sales.pojo.SalePlan;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
@Mapper
public interface SalePlanMapper extends JpaRepository<SalePlan, Integer> {


    List<SalePlan> findByUserId(int userId);

}
