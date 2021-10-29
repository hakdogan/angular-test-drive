package org.jugistanbul.security.filter;

import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.Logger;
import org.jugistanbul.security.PermissionProvider;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.PreMatching;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 23.10.2021
 */
@Provider
@PreMatching
public class WebSecurityFilter implements ContainerRequestFilter
{

    @Inject
    Logger log;

    @Inject
    PermissionProvider permissionProvider;

    @Context
    HttpServerRequest httpServerRequest;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {

        var uriInfo = context.getUriInfo();
        var path = uriInfo.getPath();

        if(!permissionProvider.checkAuthentication(path, context)){
            try {
                context.abortWith(Response.seeOther(new URI("/")).build());
            } catch (URISyntaxException e) {
                log.error("URISyntaxException was thrown in AuthenticationFilter", e);
            }
            return;
        }

        var permission = permissionProvider.checkPermission(path, context.getSecurityContext());
        if (!permission) {
            context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        var principal = Optional.ofNullable(context.getSecurityContext().getUserPrincipal());
        log.info(String.format("Request %s %s from IP %s User %s", context.getMethod(), path,
                httpServerRequest.remoteAddress().toString(),
                principal.isPresent()?principal.get().getName():"Anonymous"));
    }
}
