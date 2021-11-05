package org.jugistanbul.security.filter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.regex.Pattern;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 5.11.2021
 */
@WebFilter(urlPatterns = "/*")
public class RouteFilter extends HttpFilter {

    private static final Pattern FILE_NAME_PATTERN = Pattern.compile(".*[.][a-zA-Z\\d]+");

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        var request = (HttpServletRequest) req;
        var response = (HttpServletResponse) res;
        chain.doFilter(request, response);

        if (response.getStatus() == 404) {
            var length = request.getContextPath().length();
            var path = request.getRequestURI().substring(length).replaceAll("[/]+$", "");
            if (!FILE_NAME_PATTERN.matcher(path).matches()) {
                try {
                    response.setStatus(200);
                    request.getRequestDispatcher("/").forward(request, response);
                } finally {
                    response.getOutputStream().close();
                }
            }
        }
    }
}
