package com.periodicals.command.util;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.periodicals.utils.ResourceHolders.AttributesHolder.DEFAULT;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.GET;
import static com.periodicals.utils.ResourceHolders.AttributesHolder.SERVLET_ROOT;

/*TODO divide not to let class have lot of responsibilities*/
public class CommandHelper {

    public static boolean isGetMethod(HttpServletRequest request){
        return request.getMethod().equals(GET);
    }

    public static boolean requiredFieldsNotEmpty(String... fields) {
        for (String field : fields) {
            if (!paramIsNotEmpty(field)) {
                return false;
            }
        }
        return true;
    }

    public static boolean paramIsNotEmpty(String param) {
        return Objects.nonNull(param) && !Objects.equals(param, "");
    }

    public static boolean paramClarifiedInQuery(HttpServletRequest req, String paramName) {
        String param = req.getParameter(paramName);
        return Objects.nonNull(param) && !Objects.equals(param, "");
    }

    public static int getPagesCount(long recordsCount, int recordsPerPage) {
        return (int) Math.ceil(recordsCount * 1.0 / recordsPerPage);
    }

    /*TODO test*/
    public static String getRefererWithoutServletPath(HttpServletRequest req) {
        String referer = DEFAULT;
        try {
            String header = req.getHeader("referer");
            String path = new URI(header).getPath();
            String query = new URI(header).getQuery();
            referer = Objects.isNull(query) ? path : path + "?" + query;
            if(referer.startsWith(SERVLET_ROOT)){
                referer = referer.substring(SERVLET_ROOT.length());
            }
        } catch (Exception e) {
            /*TODO log*/
        }
        return referer;
    }


    /**
     * Gets page from request query or returns default if page param is not found
     */
    public static int getPageFromRequest(HttpServletRequest req) {
        int page = 1;
        if (paramClarifiedInQuery(req, "page")) {
            page = java.lang.Integer.parseInt(req.getParameter("page"));
        }
        return page;
    }
}
