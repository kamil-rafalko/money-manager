package com.corriel.users.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="user_role", uniqueConstraints = @UniqueConstraint(columnNames = {"role", "system_user"}))
@Getter
@Setter
@NoArgsConstructor
public class UserRole {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name="id", unique = true, nullable = false)
    private Integer userRoleId;

    @ManyToOne
    @JoinColumn(name = "system_user", nullable = false)
    private SystemUser user;

    @Column(name = "role", nullable = false, length = 45)
    private String role;

    public UserRole(SystemUser user, String role) {
        this.user = user;
        this.role = role;
    }
}
