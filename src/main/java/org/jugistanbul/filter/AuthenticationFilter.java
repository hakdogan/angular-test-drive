package org.jugistanbul.filter;

import io.vertx.core.http.HttpHeaders;
import io.vertx.core.http.HttpServerRequest;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 23.10.2021
 */
@Provider
@Secured
public class AuthenticationFilter implements ContainerRequestFilter
{
    @Inject
    Logger log;

    @Context
    HttpServerRequest httpServerRequest;

    @Override
    public void filter(ContainerRequestContext context) throws IOException {

        var method = context.getMethod();
        var uriInfo = context.getUriInfo();

        var path = uriInfo.getPath();
        var remoteAddress = httpServerRequest.remoteAddress().toString();
        var auth = null != context.getHeaderString((String) HttpHeaders.AUTHORIZATION);

        if (!auth) {
            context.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
            return;
        }

        log.info(String.format("Request %s %s from IP %s User %s", method, path, remoteAddress,
                context.getSecurityContext().getUserPrincipal().getName()));
    }
}
