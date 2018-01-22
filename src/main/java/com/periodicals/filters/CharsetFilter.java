package com.periodicals.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author Daniel Volnitsky
 * <p>
 * Filter that converts incoming data in given encoding format
 */
@WebFilter(urlPatterns = {"/*"})
public class CharsetFilter implements Filter {
    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        if (null == req.getCharacterEncoding()) {
            req.setCharacterEncoding(ENCODING);
        }
        // Set the default response content type and ENCODING
        resp.setContentType("text/html; charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        filterChain.doFilter(req, resp);
    }

    public void destroy() {
    }
}