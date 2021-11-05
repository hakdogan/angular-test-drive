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
import java.util.Arrays;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 5.11.2021
 */
@WebFilter(urlPatterns = "/*")
public class RouteFilter extends HttpFilter {

    private static final String[] ROUTES = {"/login", "/user-list"};

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {

        var request = (HttpServletRequest) req;
        var response = (HttpServletResponse) res;
        chain.doFilter(request, response);

        if (response.getStatus() == 404) {
            var path = request.getRequestURI();
            var angularRoute = Arrays.stream(ROUTES).filter(path::equals).findAny();
            if (angularRoute.isPresent()) {
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
