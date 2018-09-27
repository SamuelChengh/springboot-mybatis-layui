package com.ch.config.shiro;

import com.ch.dao.sys.UserDao;
import com.ch.entity.sys.Role;
import com.ch.entity.sys.User;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     *
     * 权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        String loginName = (String) principalCollection.fromRealm(getName()).iterator().next();
        User user = userDao.findByLoginName(loginName);

        Set<String> roles = new HashSet();
        List<Role> roleList = user.getRoles();
        if(roleList != null && roleList.size() > 0){
            for(Role role : roleList){
                roles.add(role.getName());
            }
        }
        info.setRoles(roles);

        return info;
    }

    /**
     * 认证回调函数,登录时调用
     *
     * 登录验证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userDao.findByLoginName(token.getUsername());
        if(user == null){
            throw new AuthenticationException();
        }

        return new SimpleAuthenticationInfo(user.getLoginName(), user.getPassword(), getName());
    }
}
