package com.corriel.users.entity;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.*;

@Entity
@Table(name="user_role", uniqueConstraints = @UniqueConstraint(columnNames = {"role", "system_user"}))
public class UserRole {

    private Integer userRoleId;
    private SystemUser user;
    private String role;

    public UserRole() {
    }

    public UserRole(SystemUser user, String role) {
        this.user = user;
        this.role = role;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    @ManyToOne
    @JoinColumn(name = "system_user", nullable = false)
    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }

    @Column(name = "role", nullable = false, length = 45)
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
