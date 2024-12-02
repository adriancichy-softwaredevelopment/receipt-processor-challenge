package com.adriancichy.receiptprocessor.infrastructure.persist;

import com.adriancichy.receiptprocessor.domain.Receipt;
import com.adriancichy.receiptprocessor.domain.ReceiptRepository;
import com.adriancichy.receiptprocessor.domain.exception.ReceiptNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
class ReceiptDbRepository implements ReceiptRepository {

    private final ReceiptJpaRepository receiptJpaRepository;

    @Transactional
    @Override
    public UUID create(Receipt receipt) {
        ReceiptEntity receiptEntity = ReceiptEntity.createFrom(receipt);
        receiptJpaRepository.save(receiptEntity);

        return receiptEntity.getId();
    }

    @Transactional
    @Override
    public Receipt findById(UUID id) {
        return receiptJpaRepository.findById(id)
                .map(ReceiptEntity::toDomain)
                .orElseThrow(() -> new ReceiptNotFoundException("Receipt not found for ID: " + id));
    }
}
