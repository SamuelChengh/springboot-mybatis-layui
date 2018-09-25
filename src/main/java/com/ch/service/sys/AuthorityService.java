package com.ch.service.sys;

import com.ch.dao.sys.AuthorityDao;
import com.ch.dto.sys.AuthorityDto;
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

    public ResponsePageResult list() {

        List<Authority> list = authorityDao.findAll();

        return RestResultGenerator.createSuccessPageResult(list);
    }

    public ResponseResult add(AuthorityDto dto) {
        return null;
    }

    public ResponseResult update(AuthorityDto dto) {
        return null;
    }

    public ResponseResult delete(AuthorityDto dto) {
        return null;
    }
}
