package cn.edu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : bingo
 * @category : TODO
 * @Date : 2018/7/28 16:10
 **/
@Component
@WebFilter(urlPatterns = "/demo",filterName = "filter1")
public class FilterDemo implements Filter {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info(">>Filter init<<");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info(">>Filter doFilter<< start");
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        filterChain.doFilter(request,response);
        logger.info(">>Filter doFilter<< end");
    }

    @Override
    public void destroy() {
        logger.info(">>Filter doFilter<< destroy");
    }
}
