package com.leaderment.sales.mapper.jpa;


import com.leaderment.sales.pojo.UserProductAmzAsinRel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProductAmzAsinRelMapper extends JpaRepository<UserProductAmzAsinRel,Integer> {

    UserProductAmzAsinRel findByUserIdAndProductIdAndCountryIdAndAsinId(Integer userId, Integer productId, Integer countryId, Integer asinId);


}