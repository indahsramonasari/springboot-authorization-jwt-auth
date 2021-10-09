package com.id.userproductservice.utils;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

public class HttpRequestUtil {

    private HttpRequestUtil() {
    }

    private static final String HEADER_AUTHORIZATION = "Authorization";
    private static final String HEADER_BEARER = "Bearer";


    public static String getToken(HttpServletRequest request) {
        return Objects.requireNonNullElse(request.getHeader(HEADER_AUTHORIZATION), "")
                .replace(HEADER_BEARER, "")
                .trim();
    }
}
