package com.corriel.users.entity;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.entity.fund.Fund;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity(name = "system_user")
public class SystemUser {

    private String username;
    private String password;
    private boolean enabled;
    private Set<Fund> funds;
    private Set<UserRole> userRoles = new HashSet<>();
    private Set<Budget> budgets;

    public SystemUser() {}

    public SystemUser(String username, String password, boolean enabled ) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
    }

    public SystemUser(String username, String password, boolean enabled, Set<UserRole> userRoles) {
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.userRoles = userRoles;
    }

    @Id
    @Column(unique = true, nullable = false, length = 45)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(nullable = false, length = 60)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @ManyToMany
    @JoinTable(name = "user_fund", joinColumns = @JoinColumn(name = "system_user"), inverseJoinColumns = @JoinColumn(name = "fund"))
    public Set<Fund> getFunds() {
        return funds;
    }

    public void setFunds(Set<Fund> funds) {
        this.funds = funds;
    }

    @ManyToMany
    @JoinTable(name = "user_budget", joinColumns = @JoinColumn(name = "system_user"), inverseJoinColumns =
    @JoinColumn(name = "budget"))
    public Set<Budget> getBudgets() {
        return budgets;
    }

    public void setBudgets(Set<Budget> budgets) {
        this.budgets = budgets;
    }

    @OneToMany(mappedBy = "user")
    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
