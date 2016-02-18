package com.corriel.users.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"role", "username"}))
public class UserRole {

    private Integer userRoleId;
    private User user;
    private String role;

    public UserRole() {
    }

    public UserRole(User user, String role) {
        this.user = user;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(unique = true, nullable = false)
    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    @ManyToOne
    @JoinColumn(name = "username", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(nullable = false, length = 45)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
