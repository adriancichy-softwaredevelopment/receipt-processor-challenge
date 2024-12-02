package com.adriancichy.receiptprocessor.infrastructure.persist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface ReceiptJpaRepository extends JpaRepository<ReceiptEntity, UUID> {
}
