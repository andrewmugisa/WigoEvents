// UserResponse.java
package org.wigo.myday.response;

import lombok.Getter;
import org.wigo.myday.model.UserEntity;

import java.time.Instant;

@Getter
public class UserResponse {
    private final Integer userId;
    private final String username;
    private final String name;
    private final String email;
    private final boolean enabled;
    private final Instant createdAt;

    public UserResponse(UserEntity user) {
        this.userId    = user.getUserId();
        this.username  = user.getUsername();
        this.name      = user.getName();
        this.email     = user.getEmail();
        this.enabled   = user.isEnabled();
        this.createdAt = user.getCreatedAt();
    }
}