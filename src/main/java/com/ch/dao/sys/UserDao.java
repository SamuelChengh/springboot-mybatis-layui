package com.ch.dao.sys;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ch.entity.sys.User;
import com.ch.entity.sys.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserDao extends BaseMapper<User> {

    List<User> findAll(User user);

    User findById(@Param("id") Integer id);

    User findByUserName(@Param("userName") String userName);

    void batchInsertUserRole(@Param("urs") List<UserRole> urs);

    void deleteByUserId(@Param("userId") Integer userId);
}
