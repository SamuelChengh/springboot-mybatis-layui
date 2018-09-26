package com.ch.service.sys;

import com.ch.dao.sys.UserDao;
import com.ch.dto.sys.UserDto;
import com.ch.entity.sys.User;
import com.ch.response.*;
import com.ch.utils.EncryptUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public ResponsePageResult list(UserDto dto) {
        Integer page = dto.getPage();
        Integer limit = dto.getLimit();

        Page<User> pages = PageHelper.startPage(page, limit).doSelectPage(()-> userDao.findAll());

        return RestResultGenerator.createSuccessPageResult(pages);
    }

    public ResponseResult add(UserDto dto) {

        if(userDao.findByLoginName(dto.getLoginName()) != null){
            return RestResultGenerator.createErrorResult(ResponseEnum.ACCOUNT_EXIST);
        }

        User user = new User();
        user.setLoginName(dto.getLoginName());
        user.setNickName(dto.getNickName());
        user.setPassword(EncryptUtil.encryptMD5(dto.getPassword()));
        user.setStatus(dto.getStatus());
        user.setRemark(dto.getRemark());
        userDao.insert(user);

        Map<String, Object> map = new HashMap();
        map.put("userId", user.getId());
        map.put("roleIds", dto.getRoleIds());
        userDao.insertUserRole(map);

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult update(UserDto dto) {

        User user = userDao.findById(dto.getId());
        if(!user.getLoginName().equals(dto.getLoginName())){
            if(userDao.findByLoginName(dto.getLoginName()) != null){
                return RestResultGenerator.createErrorResult(ResponseEnum.ACCOUNT_EXIST);
            }
        }

        user.setLoginName(dto.getLoginName());
        user.setNickName(dto.getNickName());
        user.setPassword(EncryptUtil.encryptMD5(dto.getPassword()));
        user.setStatus(dto.getStatus());
        user.setRemark(dto.getRemark());
        userDao.update(user);

        userDao.deleteByUserId(dto.getId());

        Map<String, Object> map = new HashMap();
        map.put("userId", dto.getId());
        map.put("roleIds", dto.getRoleIds());
        userDao.insertUserRole(map);

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult delete(UserDto dto) {

        User user = userDao.findById(dto.getId());
        if(user == null){
            return RestResultGenerator.createErrorResult(ResponseEnum.USER_NOT_EXIST);
        }

        userDao.delete(dto.getId());
        userDao.deleteByUserId(dto.getId());

        return RestResultGenerator.createSuccessResult();
    }
}
