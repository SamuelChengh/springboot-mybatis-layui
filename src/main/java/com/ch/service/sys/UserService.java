package com.ch.service.sys;

import com.ch.dao.sys.UserDao;
import com.ch.entity.sys.User;
import com.ch.response.PageResponse;
import com.ch.response.ResponseEnum;
import com.ch.response.ResponseResult;
import com.ch.response.RestResultGenerator;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserDao userDao;

    public ResponseResult list(Integer pageNumber, Integer pageSize) {

        Page<User> page = PageHelper.startPage(pageNumber, pageSize).doSelectPage(()-> userDao.findAll());

        PageResponse<User> pageResponse = new PageResponse<>();
        pageResponse.setCount(page.getTotal());
        pageResponse.setPages(page.getPages());
        pageResponse.setRows(page);

        return RestResultGenerator.createSuccessResult(pageResponse);
    }
}
