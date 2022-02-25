package org.jugistanbul.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

/*
 * @author hakdogan (huseyin.akdogan@patikaglobal.com)
 * Created on 18.10.2021
 */
@Entity
@Table(name = "user_t")
@UserDefinition
public class User extends PanacheEntity
{
    @Username
    public String username;

    @Password
    public String password;

    @Roles
    public String role;

    public static User findByUsername(final String username){
        return find("username", username).firstResult();
    }

    public static List<User> findByRole(final String role){
        return find("role", role).list();
    }

}
