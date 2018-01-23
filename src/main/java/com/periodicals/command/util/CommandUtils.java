package com.periodicals.command.util;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

import static com.periodicals.utils.resourceHolders.AttributesHolder.*;

/*TODO divide not to let class have lot of responsibilities*/
public class CommandUtils {

    public static boolean isGetMethod(HttpServletRequest request){
        return request.getMethod().equals(GET);
    }

    public static boolean isPostMethod(HttpServletRequest request){
        return request.getMethod().equals(POST);
    }

    public static boolean requiredFieldsNotEmpty(Object[] fields) {
        for (Object field : fields) {
            if (stringIsNullOrEmpty(field)) {
                return false;
            }
        }
        return true;
    }

    public static boolean stringIsNullOrEmpty(Object object) {
        return Objects.nonNull(object) && !Objects.equals(object, "");
    }

    public static boolean paramClarifiedInQuery(HttpServletRequest request, String paramName) {
        String param = request.getParameter(paramName);
        return !stringIsNullOrEmpty(param);
    }

    public static String getRefererWithoutServletPath(HttpServletRequest request) {
        String referer = DEFAULT;

        String header = request.getHeader("referer");
        if(header != null && !header.isEmpty()) {
            try {
                String path = new URI(header).getPath();
                String query = new URI(header).getQuery();
                referer = Objects.isNull(query) ? path : path + "?" + query;
            } catch (URISyntaxException e) {
                /*TODO log*/
            }
        }

        if(referer.startsWith(SERVLET_ROOT)) {
            referer = referer.substring(SERVLET_ROOT.length());
        }

        return referer;
    }
}
