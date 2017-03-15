package com.corriel.budget.repository.implementation;

import com.corriel.budget.entity.Fund;
import com.corriel.budget.repository.FundDao;
import com.corriel.data.repository.JpaGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaFundDao extends JpaGenericDao<Fund, Long> implements FundDao {
    public JpaFundDao() {
        super(Fund.class);
    }
}