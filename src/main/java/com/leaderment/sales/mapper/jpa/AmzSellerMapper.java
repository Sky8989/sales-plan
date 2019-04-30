package com.leaderment.sales.mapper.jpa;

import com.leaderment.sales.pojo.AmzSeller;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmzSellerMapper extends JpaRepository<AmzSeller, Integer> {
}
