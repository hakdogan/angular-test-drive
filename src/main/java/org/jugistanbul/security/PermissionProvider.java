package org.jugistanbul.security;


import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.SecurityContext;
import java.util.*;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 29.10.2021
 */
@ApplicationScoped
public class PermissionProvider
{
    private static final String SIGIN_PAGE = "/api/signIn";

    Map<String, List<String>> pathAndRoleMapper;

    public PermissionProvider() {
        pathAndRoleMapper = Map.of("/app/admin", Collections.singletonList("admin"),
                "/api/user/all", Collections.singletonList("admin"),
                "/app/guest", List.of("admin", "guest"));
    }

    public boolean checkAuthentication(final String path, final ContainerRequestContext requestContext){

        if(checkSignInRequest(path)){
            return true;
        }

        var auth = null != requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        return auth;
    }

    public boolean checkPermission(final String path, final SecurityContext securityContext){

        if(checkSignInRequest(path)){
            return true;
        }

        Optional<Map.Entry<String, List<String>>> matchedPath =
                pathAndRoleMapper
                    .entrySet()
                    .stream()
                    .filter(k -> k.getKey().startsWith(path))
                    .findAny();

        if(matchedPath.isPresent()){
            var role = matchedPath.get().getValue().stream().filter(securityContext::isUserInRole).findAny();
            return role.isPresent();
        }

        return false;
    }

    private boolean checkSignInRequest(final String path){
        return path.startsWith(SIGIN_PAGE);
    }
}
