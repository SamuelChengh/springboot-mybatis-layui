package com.ch.common;

import com.ch.entity.sys.User;

import javax.servlet.http.HttpServletRequest;

public class ConstantsCMP {

    public final static String USER_SESSION_INFO = "userSessionInfo";

    // 从session中取出user
    public static User getSessionUser(HttpServletRequest request) {
        return (User) (request.getSession().getAttribute(ConstantsCMP.USER_SESSION_INFO));
    }
}
