package com.corriel.budget.repository.implementation;

import com.corriel.budget.entity.Transaction;
import com.corriel.budget.repository.TransactionDao;
import com.corriel.data.repository.JpaGenericDao;
import org.springframework.stereotype.Repository;

@Repository
public class JpaTransactionDao extends JpaGenericDao<Transaction, Long> implements TransactionDao {
    public JpaTransactionDao() {
        super(Transaction.class);
    }
}