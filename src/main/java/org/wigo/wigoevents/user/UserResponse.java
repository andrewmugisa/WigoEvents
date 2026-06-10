package org.wigo.wigoevents.user;

import lombok.Getter;
import java.time.Instant;

@Getter
public class UserResponse {
    private Integer userId;
    private String name;
    private String email;
    private String username;
    private boolean enabled;
    private Instant createdAt;

    public UserResponse(UserEntity user) {
        this.userId    = user.getUserId();
        this.name      = user.getName();
        this.email     = user.getEmail();
        this.username  = user.getUserDisplayName();
        this.enabled   = user.isEnabled();
        this.createdAt = user.getCreatedAt();
    }
}
