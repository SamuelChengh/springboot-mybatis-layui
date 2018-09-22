package com.ch.service.sys;

import com.ch.dao.sys.UserDao;
import com.ch.entity.sys.User;
import com.ch.response.*;
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

    public ResponsePageResult list(User user) {
        Integer page = user.getPage();
        Integer limit = user.getLimit();

        Page<User> pages = PageHelper.startPage(page, limit).doSelectPage(()-> userDao.findAll());

        return RestResultGenerator.createSuccessPageResult(pages);
    }
}
