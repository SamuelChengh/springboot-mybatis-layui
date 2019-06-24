package com.ch.dao.sys;

import com.ch.entity.sys.Role;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public interface RoleDao {

    Integer insert(Role role);

    Integer update(Role role);

    Integer delete(@Param("id") Integer id);

    List<Role> findAll(Role role);

    List<Role> findPageList(Role role);

    Role findById(@Param("id") Integer id);

    Role findByName(@Param("name") String name);

    void insertRoleAuth(Map<String, Object> map);

    void deleteByRoleId(@Param("roleId") Integer roleId);
}
