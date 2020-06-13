package com.ch.service.sys;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ch.dao.sys.MenuDao;
import com.ch.entity.sys.Menu;
import com.ch.response.ResponseResult;
import com.ch.response.RestResultGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class MenuService extends ServiceImpl<MenuDao, Menu> {

    @Autowired
    private MenuDao menuDao;

    public ResponseResult list() {

        List<Menu> list = menuDao.findAll(null);

        return RestResultGenerator.createSuccessResult(list);
    }

    public ResponseResult add(Menu form) {

        if (form.getDisplayType().equals(1)) {
            form.setParentId(0);
        }
        if (form.getDisplaySort() == null) {
            form.setDisplaySort(0);
        }
        form.setCreatedDate(Timestamp.valueOf(LocalDateTime.now()));
        menuDao.insert(form);

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult update(Menu form) {

        form.setUpdatedDate(Timestamp.valueOf(LocalDateTime.now()));
        menuDao.updateById(form);

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult delete(Menu form) {

        menuDao.deleteById(form.getId());

        return RestResultGenerator.createSuccessResult();
    }

    public ResponseResult getParent(Integer type) {

        List<Menu> list = menuDao.findByDisplayType(type);

        return RestResultGenerator.createSuccessResult(list);
    }
}
