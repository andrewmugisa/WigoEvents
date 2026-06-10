package org.wigo.myday.adapter;

import org.springframework.stereotype.Component;
import org.wigo.auth.model.AuthUser;
import org.wigo.auth.model.AuthUserFactory;
import org.wigo.myday.model.UserEntity;

@Component
public class UserEntityFactory implements AuthUserFactory {

    @Override
    public AuthUser create(String username, String email, String password, String name) {
        return new UserEntity(username, email, password, name);
    }
}
