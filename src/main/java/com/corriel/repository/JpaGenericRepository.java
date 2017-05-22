package com.corriel.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

class JpaGenericRepository<T, PK extends Serializable> {

    private Class<T> type;

    @PersistenceContext
    private EntityManager entityManager;

    JpaGenericRepository(Class<T> type) {
        this.type = type;
    }

    public void create(T entity) {
        entityManager.persist(entity);
    }

    public T find(PK id) {
        return entityManager.find(type, id);
    }

    public void remove(T entity) {
        entityManager.remove(entity);
    }

    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> rootEntry = criteriaQuery.from(type);
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
}
