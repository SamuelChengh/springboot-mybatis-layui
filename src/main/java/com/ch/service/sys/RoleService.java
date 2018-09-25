package com.ch.service.sys;

import com.ch.dao.sys.RoleDao;
import com.ch.dto.sys.RoleDto;
import com.ch.entity.sys.Role;
import com.ch.response.ResponseEnum;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.response.RestResultGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public ResponsePageResult list(RoleDto dto) {
        Integer page = dto.getPage();
        Integer limit = dto.getLimit();

        Page<Role> pages = PageHelper.startPage(page, limit).doSelectPage(()-> roleDao.findAll());

        return RestResultGenerator.createSuccessPageResult(pages);
    }


    public ResponseResult add(RoleDto dto) {

        if(roleDao.findByName(dto.getName()) != null){
            return RestResultGenerator.createErrorResult(ResponseEnum.NAME_EXIST);
        }

        Role role = new Role();
        role.setName(dto.getName());
        role.setRemark(dto.getRemark());
        roleDao.insert(role);

        Map<String, Object> map = new HashMap();
        map.put("roleId", role.getId());
        map.put("authIds", dto.getAuthIds());
        roleDao.insertRoleAuth(map);

        return RestResultGenerator.createSuccessResult();
    }


    public ResponseResult update(RoleDto dto) {

        Role role = roleDao.findById(dto.getId());
        if(!role.getName().equals(dto.getName())){
            if(roleDao.findByName(dto.getName()) != null){
                return RestResultGenerator.createErrorResult(ResponseEnum.NAME_EXIST);
            }
        }

        role.setName(dto.getName());
        role.setRemark(dto.getRemark());
        roleDao.update(role);

        roleDao.deleteByRoleId(dto.getId());

        Map<String, Object> map = new HashMap();
        map.put("roleId", dto.getId());
        map.put("authIds", dto.getAuthIds());
        roleDao.insertRoleAuth(map);

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult delete(RoleDto dto) {

        Role role = roleDao.findById(dto.getId());
        if(role == null){
            return RestResultGenerator.createErrorResult(ResponseEnum.USER_NOT_EXIST);
        }

        roleDao.delete(dto.getId());
        roleDao.deleteByRoleId(dto.getId());

        return RestResultGenerator.createSuccessResult();
    }
}
