package com.leaderment.sales.mapper.jpa;

import com.leaderment.sales.pojo.UserCountryProductRel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCountryProductRelMapper extends JpaRepository<UserCountryProductRel,Integer> {

    UserCountryProductRel findByUserIdAndCountryIdAndProductId(Integer userId, Integer countryId, Integer productId);
}
