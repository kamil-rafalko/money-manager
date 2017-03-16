package com.corriel.budget.repository;

import com.corriel.budget.entity.Transaction;
import com.corriel.data.repository.GenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionDao extends GenericDao<Transaction, Long> {
    public TransactionDao() {
        super(Transaction.class);
    }
}