package com.leaderment.sales.mapper.jpa;

import com.leaderment.sales.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMapper extends JpaRepository<User, Integer> {

    User findByUserId(int userId);


}
