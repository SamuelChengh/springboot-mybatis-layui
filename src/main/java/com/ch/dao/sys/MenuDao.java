package com.ch.dao.sys;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ch.entity.sys.Menu;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface MenuDao extends BaseMapper<Menu> {

    List<Menu> findAll(Menu menu);

    List<Menu> findByDisplayType(@Param("displayType") Integer displayType);

    List<Menu> findByUserName(@Param("userName") String userName);
}
