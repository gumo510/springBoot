package com.gumo.demo.filter;

import com.gumo.demo.constants.GlobalConstants;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Objects;

/**
 * Description:
 *
 * @author: ChenWenlong
 * @date: 2021/10/22 15:31
 * @version: 1.0
 */
@WebFilter(filterName = "operateLogFilter", urlPatterns = {
        GlobalConstants.PASS_RECORD_PERSON_SELECT_URI,
        GlobalConstants.PASS_RECORD_STRANGER_SELECT_URI,
        GlobalConstants.PASS_RECORD_DELETE_URI})
@ServletComponentScan
@Component
public class OperateFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletRequest myRequest = null;
        if (servletRequest instanceof HttpServletRequest) {
            myRequest = new RequestWrapper((HttpServletRequest) servletRequest);
        }
        if (Objects.isNull(myRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            filterChain.doFilter(myRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
