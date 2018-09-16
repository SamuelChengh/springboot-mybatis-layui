package com.ch.dao.sys;

import com.ch.entity.sys.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {
    Integer insert(User user);

    Integer update(User user);

    Integer deleteById(@Param("id") Long id);

    User findById(@Param("id") Long id);

    List<User> findAll();
}
