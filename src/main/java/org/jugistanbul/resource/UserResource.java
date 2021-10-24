package org.jugistanbul.resource;

import org.jboss.resteasy.annotations.jaxrs.PathParam;
import org.jugistanbul.entity.User;
import org.jugistanbul.filter.Admin;
import org.jugistanbul.filter.Secured;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 24.10.2021
 */
@Secured
@Path("/api/user")
@Produces(MediaType.APPLICATION_JSON)
public class UserResource
{
    @Admin
    @GET
    @Path("/all")
    public List<User> fetchAllUsers(){
        return User.listAll();
    }

    @GET
    @Path("/info/{id}")
    public User info(@PathParam Long id){
        return User.findById(id);
    }
}
