package com.ch.dao.sys;

import com.ch.entity.sys.Authority;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface AuthorityDao {

    Integer insert(Authority authority);

    Integer update(Authority authority);

    Integer delete(@Param("id") Integer id);

    Authority findById(@Param("id") Integer id);

    List<Authority> findAll();

    List<Authority> findByParent(@Param("parent") Integer parent);
}
