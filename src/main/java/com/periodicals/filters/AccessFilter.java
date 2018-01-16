package com.periodicals.filters;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.security.SecurityConfiguration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;

import static com.periodicals.utils.AttributesHolder.PAGE_SUFFIX;
import static com.periodicals.utils.PagesHolder.ERROR_PAGE;

@WebFilter(urlPatterns = {"/*"})
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        SecurityConfiguration config = SecurityConfiguration.getInstance();

        String path = getCorrectPath(request);
        String command = getStringCommand(path, config.getEndPoints());
        String securityType = config.getSecurityType(command);

        if ("ALL".equals(securityType)) {
            request.setAttribute("command", command);
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        if ("AUTH".equals(securityType)) {
            boolean loggedIn = AuthenticationHelper.isUserLoggedIn(request.getSession());
            if (loggedIn) {
                request.setAttribute("command", command);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendRedirect(ERROR_PAGE);
            return;

        }
        if ("ADMIN".equals(securityType)) {
            boolean isAdmin = AuthenticationHelper.isAdmin(request.getSession());
            if (isAdmin) {
                request.setAttribute("command", command);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.sendRedirect(ERROR_PAGE);
            return;
        }
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.sendRedirect(ERROR_PAGE);
    }

    private String getCorrectPath(HttpServletRequest request) {
        String path = request.getRequestURI();
        if (path == null) {
            path = "/";
        } else {
            path = path.toLowerCase();
            int index = path.lastIndexOf(PAGE_SUFFIX);
            if (index != -1 && index + PAGE_SUFFIX.length() == path.length()) {
                path = path.substring(0, index);
            }
        }
        return path;
    }

    /**TODO ЗАМЕНИТЬ НА ПРОВЕРКУ ОКРУЖЕНИЯ ЭНДПОИНТА СЛЕШАМИ*/
    private String getStringCommand(String path, Set<String> endPoints) {
        for (String endPoint : endPoints) {
            if (path.contains(endPoint))
                return endPoint;
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
