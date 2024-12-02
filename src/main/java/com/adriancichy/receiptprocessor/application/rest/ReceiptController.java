package com.adriancichy.receiptprocessor.application.rest;

import com.adriancichy.receiptprocessor.domain.Receipt;
import com.adriancichy.receiptprocessor.domain.ReceiptRepository;
import com.adriancichy.receiptprocessor.domain.exception.ReceiptNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("/receipts")
@RequiredArgsConstructor
@Slf4j
class ReceiptController {

    private final ReceiptRepository repository;

    @PostMapping("/process")
    public ResponseEntity<ProcessResponse> processReceipt(
            @Valid @RequestBody ReceiptDto receipt) {

        UUID id = repository.create(ReceiptDto.toDomain(receipt));

        return ResponseEntity.ok(ProcessResponse.from(id));
    }

    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPoints(
            @PathVariable("id") UUID id) {

        Receipt receipt = repository.findById(id);

        int points = receipt.calculatePoints();

        return ResponseEntity.ok(new PointsResponse(points));
    }

    @ExceptionHandler(ReceiptNotFoundException.class)
    public ResponseEntity<UserErrorDto> handleReceiptNotFoundException(ReceiptNotFoundException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .status(NOT_FOUND)
                .body(new UserErrorDto("No receipt found for that id"));
    }

    record ProcessResponse(
            String id
    ) {

        static ProcessResponse from(UUID id) {
            return new ProcessResponse(id.toString());
        }
    }

    record PointsResponse(
            int points
    ) { }

}
