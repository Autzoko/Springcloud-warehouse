package org.longman.microservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.longman.exception.IdConflictException;
import org.longman.exception.JsonDataError;
import org.longman.exception.MissingFieldException;
import org.longman.entity.TransactionEntity;
import org.longman.entity.dto.TransactionDto;
import org.longman.microservice.service.TransactionService;
import org.longman.utils.BaseController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
public class TransactionController extends BaseController {

    private final TransactionService transactionService;

    @PostMapping("/new")
    public ResponseEntity<Object> createTransaction(@RequestBody TransactionDto transactionDto) {
        try {
            validateTransactionInfo(transactionDto);
            TransactionEntity transaction = new TransactionEntity();

            transaction.setId(UUID.randomUUID().toString());
            transaction.setProvider_id(transactionDto.getProvider_id());
            transaction.setConsumer_id(transactionDto.getConsumer_id());
            transaction.setCommodity_id(transactionDto.getCommodity_id());
            transaction.setNum_transaction(transactionDto.getNum_transaction());

            transactionService.createTransaction(transaction);

            return success("transaction created");
        } catch (JsonDataError e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("json data error");
        } catch (IdConflictException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return createTransaction(transactionDto);
        } catch (MissingFieldException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail("missing field error");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

    @GetMapping("/fetch-transaction")
    public ResponseEntity<Object> fetchTransactionByConsumerId(@RequestParam(name = "id") Long id) {
        // here need to check this user whether is existed, function not implemented, currently skipped
        try {
            List<TransactionEntity> transactions = transactionService.getTransactionsByConsumerId(id);

            List<TransactionDto> transactionDtoList = getTransactionDtoList(transactions);

            return success(transactionDtoList);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteTransaction(@RequestParam(name = "id") String id) {
        try {
            transactionService.deleteTransaction(id);
            return success("delete success");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

    @GetMapping("/fetch-provider-transaction")
    public ResponseEntity<Object> fetchTransactionByProviderId(@RequestParam(name = "id") Long id) {
        // here need to check this user whether is existed, function not implemented, currently skipped
        try {
            List<TransactionEntity> transactions = transactionService.getTransactionsByProviderId(id);
            List<TransactionDto> transactionDtoList = getTransactionDtoList(transactions);
            return success(transactionDtoList);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
            return fail(e.getMessage());
        }
    }

    private static List<TransactionDto> getTransactionDtoList(List<TransactionEntity> transactions) {
        List<TransactionDto> transactionDtoList = new ArrayList<>();

        for (TransactionEntity transaction : transactions) {
            TransactionDto transactionDto = new TransactionDto();
            transactionDto.setProvider_id(transaction.getProvider_id());
            transactionDto.setConsumer_id(transaction.getConsumer_id());
            transactionDto.setCommodity_id(transaction.getCommodity_id());
            transactionDto.setNum_transaction(transaction.getNum_transaction());
            transactionDtoList.add(transactionDto);
        }
        return transactionDtoList;
    }

    private static void validateTransactionInfo(TransactionDto transactionDto) {
        if (transactionDto == null) {
            throw new JsonDataError("transaction data not found");
        }
        if (Objects.isNull(transactionDto.getProvider_id())) {
            throw new JsonDataError("provider info empty");
        }
        if (Objects.isNull(transactionDto.getConsumer_id())) {
            throw new JsonDataError("consumer info empty");
        }
        if (Objects.isNull(transactionDto.getCommodity_id())) {
            throw new JsonDataError("commodity info empty");
        }
        if (Objects.isNull(transactionDto.getNum_transaction())) {
            throw new JsonDataError("transaction number not found");
        }
    }
}