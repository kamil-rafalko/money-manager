package com.corriel.budget.repository.implementation;

import com.corriel.budget.entity.Budget;
import com.corriel.budget.entity.Budget_;
import com.corriel.budget.repository.BudgetDao;
import com.corriel.data.repository.JpaGenericDao;
import com.corriel.users.entity.SystemUser;
import com.corriel.users.entity.SystemUser_;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class JpaBudgetDao extends JpaGenericDao<Budget, Long> implements BudgetDao {

    public JpaBudgetDao() {
        super(Budget.class);
    }

    @Override
    public List<Budget> findUsersBudgets(String username) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Budget> criteriaQuery = criteriaBuilder.createQuery(Budget.class);
        Root<Budget> budget = criteriaQuery.from(Budget.class);
        Join<Budget, SystemUser> user = budget.join(Budget_.users);
        criteriaQuery.where(criteriaBuilder.equal(user.get(SystemUser_.username), username));
        TypedQuery<Budget> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
    }
}
