package com.corriel.web;

import com.corriel.budget.service.TransactionService;
import com.corriel.web.dto.TransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @Inject
    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insert(@RequestBody final TransactionDto transaction) {
        transactionService.create(transaction);
        return ResponseEntity.ok().build();
    }
}