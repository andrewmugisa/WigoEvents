package org.wigo.myday.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name="users")
public class UserEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    private boolean enabled;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "verification_expiration")
    private Instant verificationCodeExpiration;

    private String name;

    @Column(name = "created_at", updatable = false, insertable = false)
    private Instant createdAt;


    public UserEntity(String username, String email, String password, String name, Instant createdAt) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.name = name;
        this.createdAt = createdAt;
    }

    public UserEntity(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserEntity() {}

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(); //because we are not doing role_based authentication
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", createdDate=" + createdAt +
                '}';
    }
}
