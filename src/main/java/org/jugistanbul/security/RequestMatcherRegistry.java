package org.jugistanbul.security;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 23.02.2022
 ***/
@ApplicationScoped
public class RequestMatcherRegistry
{
    private List<Resource> resources;

    public RequestMatcherRegistry addResource(final String path, final String[] roles,
                                              final boolean permitAll) {
        resources().add(new Resource(path, roles, permitAll));
        return this;
    }

    public List<Resource> resources() {
        if(null == resources){
            resources = new ArrayList<>();
        }
        return resources;
    }
}
