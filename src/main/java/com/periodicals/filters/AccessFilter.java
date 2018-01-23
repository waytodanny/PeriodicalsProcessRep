package com.periodicals.filters;

import com.periodicals.authentification.AuthenticationHelper;
import com.periodicals.security.SecurityConfiguration;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static com.periodicals.utils.resourceHolders.AttributesHolder.COMMAND;
import static com.periodicals.utils.resourceHolders.AttributesHolder.PAGE_SUFFIX;
import static com.periodicals.utils.resourceHolders.PagesHolder.ERROR_PAGE;

/**
 * @author Daniel Volnitsky
 * <p>
 * Filter that:
 * 1) checks incoming request;
 * 2) pulls out command value;
 * 3) due to command access level decides what to do with request.
 * Uses SecurityConfiguration class to get commands access levels
 * @see SecurityConfiguration
 * @see com.periodicals.command.util.Command
 */
@WebFilter(urlPatterns = {"/*"})
public class AccessFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        SecurityConfiguration securityConfiguration = SecurityConfiguration.getInstance();

        String requestURI = this.getCorrectedRequestURI(request);
        String command = this.getCommandFromRequestURI(requestURI, securityConfiguration);
        String securityType = securityConfiguration.getCommandSecurityType(command);

        switch (securityType) {
            case "ALL":
                request.setAttribute(COMMAND, command);
                filterChain.doFilter(servletRequest, servletResponse);
                break;

            case "AUTH":
                if (AuthenticationHelper.isUserLoggedIn(request.getSession())) {
                    request.setAttribute(COMMAND, command);
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.sendRedirect(ERROR_PAGE);
                }
                break;

            case "ADMIN":
                if (AuthenticationHelper.isSessionUserAdmin(request.getSession())) {
                    request.setAttribute(COMMAND, command);
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

    /**
     * Method reorganize incoming URI from request in
     * understandable for Command seeking method state
     *
     * @see this.getCommandFromRequestURI
     */
    private String getCorrectedRequestURI(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        if (requestURI != null && !requestURI.isEmpty()) {
            requestURI = requestURI.toLowerCase();

            /*gets rid of pages suffix*/
            if (requestURI.endsWith(PAGE_SUFFIX)) {
                requestURI = requestURI.substring(0, requestURI.lastIndexOf(PAGE_SUFFIX));
            }
            /*gets rid of pages last '/' character*/
            if (requestURI.endsWith("/")) {
                requestURI = requestURI.substring(0, requestURI.lastIndexOf("/"));
            }
        } else {
            requestURI = "/";
        }
        return requestURI;
    }

    /**
     * Finds command in requested path in possible command list
     *
     * @see SecurityConfiguration
     */
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
