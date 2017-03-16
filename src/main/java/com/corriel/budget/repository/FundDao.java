package com.corriel.budget.repository;

import com.corriel.budget.entity.Fund;
import com.corriel.data.repository.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class FundDao extends GenericDao<Fund, Long> {
    public FundDao() {
        super(Fund.class);
    }
}