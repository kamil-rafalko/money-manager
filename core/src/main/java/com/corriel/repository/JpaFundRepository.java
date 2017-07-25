package com.corriel.repository;

import com.corriel.application.core.entity.Fund;
import com.corriel.application.core.repository.FundRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaFundRepository extends JpaGenericRepository<Fund, Long> implements FundRepository {
    JpaFundRepository() {
        super(Fund.class);
    }
}