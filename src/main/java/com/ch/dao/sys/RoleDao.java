package com.ch.dao.sys;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ch.entity.sys.Role;
import com.ch.entity.sys.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface RoleDao extends BaseMapper<Role> {

    List<Role> findAll(Role role);

    Role findById(@Param("id") Integer id);

    Role findByRoleName(@Param("roleName") String roleName);

    void batchInsertRoleMenu(@Param("rms") List<RoleMenu> rms);

    void deleteByRoleId(@Param("roleId") Integer roleId);

}
