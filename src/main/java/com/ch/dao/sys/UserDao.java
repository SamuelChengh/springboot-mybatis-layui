package com.ch.dao.sys;

import com.ch.dto.sys.UserDto;
import com.ch.entity.sys.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface UserDao {
    Integer insert(User user);

    Integer update(User user);

    Integer delete(@Param("id") Integer id);

    List<User> findAll(User user);

    List<User> findPageList(User user);

    User findById(@Param("id") Integer id);

    User findByLoginName(@Param("loginName") String loginName);

    void insertUserRole(Map<String, Object> map);

    void deleteByUserId(@Param("userId") Integer userId);
}
