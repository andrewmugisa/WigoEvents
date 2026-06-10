package org.wigo.wigoevents.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import org.wigo.auth.model.AuthUser;

@Entity
@Table(name = "users")
public class UserEntity extends AuthUser {

    // Add wigoEvents-specific fields here

    public UserEntity(String username, String email, String password, String name) {
        super(username, email, password, name);
    }

    public UserEntity() { super(); }
}
