package com.leaderment.sales.mapper.jpa;

import com.leaderment.sales.pojo.Brand;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BrandMapper extends JpaRepository<Brand, Integer> {
}
