package com.receipt.processor.challenge.controller;

import com.receipt.processor.challenge.model.Receipt;
import com.receipt.processor.challenge.service.ReceiptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/receipts")
public class ReceiptController {

    private final ReceiptService receiptService;

    public ReceiptController(final ReceiptService receiptService) {
        this.receiptService = receiptService;
    }

    @PostMapping("/process")
    public ResponseEntity<Map<String, String>> processReceipt(@RequestBody final Receipt receipt) {
        log.info("Received request to process Receipt: {}", receipt);
        final String id = receiptService.processReceipt(receipt);
        return ResponseEntity.ok(Map.of("id", id));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<Map<String, Integer>> getPoints(@PathVariable final String id) {
        log.info("Received request to get points for id: {}", id);
        final Integer points = receiptService.getPoints(id);
        if (points == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(Map.of("points", points));
    }
}
