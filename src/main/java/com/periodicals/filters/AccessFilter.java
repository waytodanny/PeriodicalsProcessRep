package com.periodicals.filters;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.security.SecurityConfiguration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.PAGE_SUFFIX;
import static com.periodicals.utils.ResourceHolders.PagesHolder.ERROR_PAGE;

@WebFilter(urlPatterns = {"/*"})
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        SecurityConfiguration securityConfiguration = SecurityConfiguration.getInstance();

        String requestURI = this.getCorrectedRequestURI(request);
        String command = this.getCommandFromRequestURI(requestURI, securityConfiguration);
        String securityType = securityConfiguration.getCommandSecurityType(command);

        switch (securityType) {
            case "ALL":
                request.setAttribute("command", command);
                filterChain.doFilter(servletRequest, servletResponse);
                break;

            case "AUTH":
                if (AuthenticationHelper.isUserLoggedIn(request.getSession())) {
                    request.setAttribute("command", command);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.sendRedirect(ERROR_PAGE);
                }
                break;

            case "ADMIN":
                if (AuthenticationHelper.isCurrentUserAdmin(request.getSession())) {
                    request.setAttribute("command", command);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.sendRedirect(ERROR_PAGE);
                }
                break;

            default:
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.sendRedirect(ERROR_PAGE);
        }
    }

    private String getCorrectedRequestURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI != null && !requestURI.isEmpty()) {
            requestURI = requestURI.toLowerCase();
            if (requestURI.endsWith(PAGE_SUFFIX)) {
                requestURI = requestURI.substring(0, requestURI.lastIndexOf(PAGE_SUFFIX));
            }
            if(requestURI.endsWith("/")) {
                requestURI = requestURI.substring(0, requestURI.lastIndexOf("/"));
            }
        } else {
            requestURI = "/";
        }
        return requestURI;
    }

    private String getCommandFromRequestURI(String path, SecurityConfiguration securityConfiguration) {
        Set<String> endPoints = securityConfiguration.getEndPoints();
        for (String endPoint : endPoints) {
            if (path.endsWith(endPoint))
                return endPoint;
        }
        return null;
    }

    @Override
    public void destroy() {

    }
}
