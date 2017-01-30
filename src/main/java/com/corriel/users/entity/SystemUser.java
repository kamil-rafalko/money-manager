package com.corriel.users.entity;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.entity.fund.Fund;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "system_user")
@Getter
@Setter
public class SystemUser {

    @Id
    @Column(unique = true, nullable = false, length = 45)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @ManyToMany
    @JoinTable(name = "user_fund", joinColumns = @JoinColumn(name = "system_user"), inverseJoinColumns = @JoinColumn(name = "fund"))
    private Set<Fund> funds;

    @OneToMany(mappedBy = "user")
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToOne
    private Budget budget;
}
