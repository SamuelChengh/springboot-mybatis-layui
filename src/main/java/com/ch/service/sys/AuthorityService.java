package com.ch.service.sys;

import com.ch.dao.sys.AuthorityDao;
import com.ch.entity.sys.Authority;
import com.ch.response.ResponsePageResult;
import com.ch.response.ResponseResult;
import com.ch.response.RestResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AuthorityService {

    @Autowired
    private AuthorityDao authorityDao;

    public ResponseResult list() {

        List<Authority> list = authorityDao.findAll();

        return RestResultGenerator.createSuccessResult(list);
    }

    public ResponseResult add(Authority authority) {

        authorityDao.insert(authority);

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult update(Authority authority) {

        authorityDao.update(authority);

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult delete(Authority authority) {

        authorityDao.delete(authority.getId());

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult getParent() {

        List<Authority> list = authorityDao.findByParent(0);

        return RestResultGenerator.createSuccessResult(list);
    }
}
