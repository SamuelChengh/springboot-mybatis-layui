package com.ch.dao.sys;

import com.ch.entity.sys.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao {
    Integer insert(User user);

    Integer update(User user);

    Integer deleteById(Long id);

    User findById(Long id);

    List<User> findAll();
}
