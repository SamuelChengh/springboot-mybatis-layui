package com.ch.dao.sys;

import com.ch.entity.sys.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserDao {
    Integer insert(User user);

    Integer update(User user);

    Integer deleteById(Integer id);

    User findById(Integer id);

    User findByLoginName(String loginName);

    List<User> findAll();

    void insertUserRole(Map<String, Object> map);

    void deleteByUserId(Integer userId);
}
