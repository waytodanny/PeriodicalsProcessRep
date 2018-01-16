package com.periodicals.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"})
public class CharsetFilter implements Filter {
    private static final String ENCODING = "UTF-8";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        // Respect the client-specified character ENCODING
        // (see HTTP specification section 3.4.1)
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