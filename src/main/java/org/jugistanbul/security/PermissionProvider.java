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
    Map<String, List<String>> pathAndRoleMapper;

    public PermissionProvider() {
        pathAndRoleMapper = Map.of("/api/user/all", Collections.singletonList("admin"),
                "/user-list", Collections.singletonList("admin"),
                "/api/signIn", Collections.singletonList("all"),
                "/app/guest", List.of("admin", "guest"));
    }

    public boolean checkAuthentication(final String path, final ContainerRequestContext requestContext){

        if(isPermittedRequest(path)){
            return true;
        }

        var auth = null != requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
        return auth;
    }

    public boolean checkPermission(final String path, final SecurityContext securityContext){

        if(isPermittedRequest(path)){
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

    private boolean isPermittedRequest(final String path){
        Optional<String> permit = pathAndRoleMapper
                .entrySet()
                .stream()
                .filter(entrySet -> path.equals(entrySet.getKey()) && entrySet.getValue().contains("all"))
                .map(Map.Entry::getKey).findAny();

        return permit.isPresent();
    }
}
