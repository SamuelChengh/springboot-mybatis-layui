package com.ch.controller.account;

import com.ch.common.ConstantsCMP;
import com.ch.dao.sys.UserDao;
import com.ch.entity.sys.User;
import com.ch.response.ResponseEnum;
import com.ch.utils.EncryptUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;

@Controller
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    /**
     * 验证码生成工具
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/captchaImage")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        response.setDateHeader("Expires", 0);
        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate");
        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");
        // return a jpeg
        response.setContentType("image/jpeg");
        // create the text for the image
        String capText = defaultKaptcha.createText();
        // store the text in the session
        request.getSession().setAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY, capText);
        // create the image with the text
        BufferedImage bi = defaultKaptcha.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        // write the data out
        ImageIO.write(bi, "jpg", out);
        try {
            out.flush();
        } finally {
            out.close();
        }
        return null;
    }

    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    /**
     * 登录
     *
     * @param username   用户名
     * @param password   密码
     * @param randomCode 验证码
     * @return
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.POST)
    public String login(String username, String password, String randomCode,
                        HttpSession session, RedirectAttributes ra) {

        // 验证码
        String checkCode = (String) session.getAttribute(com.google.code.kaptcha.Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isEmpty(randomCode) || !StringUtils.equalsIgnoreCase(randomCode, checkCode)) {
            ra.addFlashAttribute("msg", ResponseEnum.VERIFICATION_CODE_ERROR.getMessage());
            return "redirect:/";
        }

        Subject currentUser = SecurityUtils.getSubject();
        try {
            UsernamePasswordToken token = new UsernamePasswordToken(username, EncryptUtil.encryptMD5(password));
            token.setRememberMe(false);
            currentUser.login(token);
        } catch (Exception e) {
            ra.addFlashAttribute("msg", e.getMessage());
            return "redirect:/";
        }

        boolean validUser = currentUser.isAuthenticated();
        if (!validUser) {
            ra.addFlashAttribute("msg", ResponseEnum.AUTHORITY_NOT.getMessage());
            return "redirect:/";
        }

        User user = userDao.findByUserName(username);
        session.setAttribute(ConstantsCMP.USER_SESSION_INFO, user);
        return "redirect:/index";
    }

    /**
     * 主界面
     */
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request, Model model) {

        User user = ConstantsCMP.getSessionUser(request);
        model.addAttribute("user", user);

        return "user/index";
    }

    /**
     * 退出
     */
    @RequestMapping(value = {"/logout"})
    public String logout() {
        Subject currentUser = SecurityUtils.getSubject();
        try {
            currentUser.logout();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return InternalResourceViewResolver.REDIRECT_URL_PREFIX + "/";
    }

    @RequestMapping(value = {"/nopermission"})
    public String noPermission() {
        return "common/noPermission";
    }
}
