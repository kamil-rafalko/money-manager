package com.corriel.repository;

import com.corriel.application.core.entity.Transaction;
import com.corriel.application.core.repository.TransactionRepository;
import org.springframework.stereotype.Repository;

@Repository
class JpaTransactionRepository extends JpaGenericRepository<Transaction, Long> implements TransactionRepository {
    public JpaTransactionRepository() {
        super(Transaction.class);
    }
}