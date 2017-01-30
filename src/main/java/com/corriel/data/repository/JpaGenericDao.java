package com.corriel.data.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.List;

public class JpaGenericDao<T, PK extends Serializable> implements GenericDao<T, PK> {

    private Class<T> type;

    @PersistenceContext
    protected EntityManager entityManager;

    public JpaGenericDao(Class<T> type) {
        this.type = type;
    }

    @Override
    public void create(T entity) {
        entityManager.persist(entity);
    }

    @Override
    public T find(PK id) {
        return entityManager.find(type, id);
    }

    @Override
    public void remove(T entity) {
        entityManager.remove(entity);
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(type);
        Root<T> rootEntry = criteriaQuery.from(type);
        CriteriaQuery<T> all = criteriaQuery.select(rootEntry);
        TypedQuery<T> allQuery = entityManager.createQuery(all);
        return allQuery.getResultList();
    }
}
