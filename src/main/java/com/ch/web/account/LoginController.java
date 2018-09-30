package com.ch.web.account;

import com.ch.common.ConstantsCMP;
import com.ch.dao.sys.UserDao;
import com.ch.entity.sys.User;
import com.ch.response.ResponseEnum;
import com.ch.utils.EncryptUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = { "/" })
    public String login() {
        return "login";
    }

    /**
    * 登录
    */
    @RequestMapping(value = { "/login" }, method = RequestMethod.POST)
    public String login(String loginName, String password
            , HttpSession session, RedirectAttributes ra) {

        Subject currentUser = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, EncryptUtil.encryptMD5(password));
        token.setRememberMe(false);
        try {
            currentUser.login(token);
        } catch (AuthenticationException e) {
            logger.info(e.getMessage(), e);
            ra.addFlashAttribute("msg", ResponseEnum.USER_WRONG_AUT.getMessage());
            return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
        }
        boolean validUser = currentUser.isAuthenticated();
        if (validUser) {
            User user = userDao.findByLoginName(loginName);
            session.setAttribute(ConstantsCMP.USER_SESSION_INFO, user);
            return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/index";
        }else{
            ra.addFlashAttribute("msg", ResponseEnum.AUTHORITY_NOT.getMessage());
            return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
        }
    }

    /**
     * 首页
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {

        User user = ConstantsCMP.getSessionUser(request);
        model.addAttribute("user", user);

        return "index";
    }

    /**
     * 退出
     */
    @RequestMapping(value = { "/logout" })
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (AuthenticationException e) {
            logger.error(e.getMessage(), e);
        }
        return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
    }
}
