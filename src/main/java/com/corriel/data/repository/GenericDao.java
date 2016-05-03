package com.corriel.data.repository;

import java.io.Serializable;
import java.util.List;

public interface GenericDao<T, PK extends Serializable> {

    void create(T entity);
    T find(PK id);
    void update(T entity);
    void remove(T entity);
    List<T> getAll();
}
