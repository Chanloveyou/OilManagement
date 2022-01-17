package com.chan.oilmanagement.interceptor;

import com.chan.oilmanagement.exception.UserLoginException;
import com.chan.oilmanagement.pojo.Employee;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.chan.oilmanagement.consts.OilConst.CURRENT_USER;

@Slf4j
public class UserLoginInterceptor implements HandlerInterceptor {

    /**
     * true表示继续流程
     * false表示中断
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("preHandle...");
        Employee employee = (Employee) request.getSession().getAttribute(CURRENT_USER);
        if (employee == null) {
            log.info("user==null");
            throw new UserLoginException();
            //return false;
            //return ResponseVo.error(ResponseEnum.NEED_LOGIN);
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
