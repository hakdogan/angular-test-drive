package org.jugistanbul.filter;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 24.10.2021
 */
@Provider
@Admin
public class AdminAuthenticationFilter implements ContainerRequestFilter
{
    private static final String ADMIN_ROLE = "admin";

    @Override
    public void filter(ContainerRequestContext context) throws IOException {

        if(!context.getSecurityContext().isUserInRole(ADMIN_ROLE)){
            context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}
