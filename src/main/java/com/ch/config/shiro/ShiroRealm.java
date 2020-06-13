package com.ch.config.shiro;

import com.ch.dao.sys.MenuDao;
import com.ch.dao.sys.UserDao;
import com.ch.entity.sys.Menu;
import com.ch.entity.sys.Role;
import com.ch.entity.sys.User;
import com.ch.response.ResponseEnum;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private MenuDao menuDao;

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     *
     * 权限验证
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();

        String userName = (String) principalCollection.fromRealm(getName()).iterator().next();
        User user = userDao.findByUserName(userName);

        // 角色权限标识
        Set<String> roles = new HashSet<>();
        List<Role> roleList = user.getRoles();
        if(roleList != null && roleList.size() > 0){
            for(Role role : roleList){
                roles.add(role.getRoles());
            }
        }
        info.setRoles(roles);

        // 菜单权限标识
        Set<String> stringPermissions = new HashSet<>();
        List<Menu> menuList = menuDao.findByUserName(userName);
        if(menuList != null && menuList.size() > 0){
            for(Menu menu : menuList){
                if(StringUtils.isBlank(menu.getPermission())){
                    continue;
                }
                stringPermissions.add(menu.getPermission());
            }
        }
        info.setStringPermissions(stringPermissions);

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

        User user = userDao.findByUserName(token.getUsername());
        // 账号不存在
        if(user == null){
            throw new UnknownAccountException(ResponseEnum.USER_NOT_EXIST.getMessage());
        }

        // 密码错误
        if(!new String(token.getPassword()).equals(user.getPassword())) {
            throw new IncorrectCredentialsException(ResponseEnum.PASSWORD_ERROR.getMessage());
        }

        // 账号被禁用
        if(user.getStatus().equals(0)){
            throw new LockedAccountException(ResponseEnum.USER_DISABLED.getMessage());
        }

        return new SimpleAuthenticationInfo(user.getUserName(), user.getPassword(), getName());
    }
}
