package org.jugistanbul.security;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.util.*;
import java.util.function.Predicate;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 29.10.2021
 */
@ApplicationScoped
public class PermissionProvider
{
    private final RequestMatcherRegistry requestMatcherRegistry;

    public PermissionProvider(final RequestMatcherRegistry requestMatcherRegistry){
        this.requestMatcherRegistry = requestMatcherRegistry;
    }

    public boolean checkAuthentication(final ContainerRequestContext requestContext){
        return null != requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
    }

    public boolean checkPermission(final String path, final SecurityContext securityContext){

        return requestMatcherRegistry
                .resources().stream().anyMatch(isRoleAllowed(path, securityContext));
    }

    public boolean isPermitted(final String path){
        return requestMatcherRegistry
                .resources().stream().anyMatch(isPathAllowed(path));
    }

    private Predicate<Resource> isPathAllowed(final String path){
        return resource -> resource.permitAll() && resource.path().equals(path);
    }

    private Predicate<Resource> isRoleAllowed(final String path, final SecurityContext securityContext){
        return resource -> resource.path().equals(path)
                && Arrays.stream(resource.roles()).anyMatch(securityContext::isUserInRole);
    }
}
