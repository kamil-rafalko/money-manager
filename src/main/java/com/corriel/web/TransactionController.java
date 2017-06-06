package com.corriel.web;

import com.corriel.application.core.budget.TransactionService;
import com.corriel.application.dto.TransactionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/transaction")
class TransactionController {

    private final TransactionService transactionService;

    @Inject
    public TransactionController(final TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<TransactionDto> list() {
        return transactionService.findAllForCurrentUser();
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> insert(@RequestBody final TransactionDto transaction) {
        transactionService.create(transaction);
        return ResponseEntity.ok().build();
    }
}