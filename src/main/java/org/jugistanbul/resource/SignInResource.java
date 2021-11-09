package org.jugistanbul.resource;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jugistanbul.dto.UserDTO;
import org.jugistanbul.entity.User;
import org.jboss.logging.Logger;
import org.wildfly.security.password.PasswordFactory;
import org.wildfly.security.password.WildFlyElytronPasswordProvider;
import org.wildfly.security.password.interfaces.BCryptPassword;
import org.wildfly.security.password.util.ModularCrypt;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 18.10.2021
 */
@Path("/api")
@RequestScoped
public class SignInResource
{
    @Inject
    Logger log;

    @ConfigProperty(name = "mp.jwt.verify.issuer", defaultValue = "https://jugistanbul.org/issuer")
    String issuer;

    @POST
    @Path("/signIn")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response signIn(final UserDTO userDTO, @Context UriInfo uriInfo) {

        var user = User.findByUsername(userDTO.username());
        return user != null && verifyBCryptPassword(user.password, userDTO.password())
                ? createSuccessLoginResponse(user, uriInfo) : createFailedLoginResponse();

    }

    public boolean verifyBCryptPassword(String bCryptPasswordHash, String passwordToVerify) {

        try {
            var provider = new WildFlyElytronPasswordProvider();
            var passwordFactory = PasswordFactory.getInstance(BCryptPassword.ALGORITHM_BCRYPT, provider);
            var userPasswordDecoded = ModularCrypt.decode(bCryptPasswordHash);
            var userPasswordRestored = passwordFactory.translate(userPasswordDecoded);
            return passwordFactory.verify(userPasswordRestored, passwordToVerify.toCharArray());

        } catch (NoSuchAlgorithmException | InvalidKeyException | InvalidKeySpecException e) {
            log.error("Verification failed!", e);
        }
        return false;
    }

    private Response createSuccessLoginResponse(final User user, UriInfo uriInfo) {
        var token = Jwt.issuer(issuer)
                .upn(user.username)
                .claim("userId", user.id)
                .groups(user.role)
                .expiresIn(Duration.ofMinutes(30))
                .sign();

        return Response.ok(token, MediaType.TEXT_PLAIN_TYPE)
                .header(HttpHeaders.AUTHORIZATION, token)
                .expires(Date.from(Instant.now().plus(Duration.ofMinutes(30))))
                .cookie(new NewCookie("auth-token", token, "/", uriInfo.getBaseUri().getHost(),
                        null, 60 * 30, false)).build();

    }

    private Response createFailedLoginResponse() {
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
