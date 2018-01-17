package com.periodicals.command.util;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.periodicals.utils.AttributesHolder.DEFAULT;
import static com.periodicals.utils.AttributesHolder.SERVLET_ROOT;

/*TODO divide not to let class have lot of responsibilities*/
public class CommandHelper {
    public static boolean requiredFieldsNotEmpty(String[] fields) {
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

    public static String getRefererWithoutServletPath(HttpServletRequest req) {
        String referer = DEFAULT;
        try {
            String path = new URI(req.getHeader("referer")).getPath();
            String query = new URI(req.getHeader("referer")).getQuery();
            referer = Objects.isNull(query) ? path : path + "?" + query;
            if(referer.contains(SERVLET_ROOT)){
                referer = referer.substring(SERVLET_ROOT.length());
            }
        } catch (URISyntaxException e) {
            /*TODO log*/
        }
        return referer;
    }
}
