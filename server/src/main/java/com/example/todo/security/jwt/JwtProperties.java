package com.example.todo.security.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String secret;
    private Long secs_to_expire_access;
    private Long secs_to_expire_refresh;


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getSecs_to_expire_access() {
        return secs_to_expire_access;
    }

    public void setSecs_to_expire_access(Long secs_to_expire_access) {
        this.secs_to_expire_access = secs_to_expire_access;
    }

    public Long getSecs_to_expire_refresh() {
        return secs_to_expire_refresh;
    }

    public void setSecs_to_expire_refresh(Long secs_to_expire_refresh) {
        this.secs_to_expire_refresh = secs_to_expire_refresh;
    }
}
