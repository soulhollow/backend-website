package com.example.backend.model;

import java.io.Serializable;

public class UserRoleId implements Serializable {

    private Long user;
    private Long role;

    // equals() und hashCode() implementieren


    public UserRoleId(Long user, Long role) {
        this.user = user;
        this.role = role;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }

    public Long getRole() {
        return role;
    }

    public void setRole(Long role) {
        this.role = role;
    }
}
