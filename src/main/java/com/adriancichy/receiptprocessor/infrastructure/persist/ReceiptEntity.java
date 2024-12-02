package com.adriancichy.receiptprocessor.infrastructure.persist;

import com.adriancichy.receiptprocessor.domain.Item;
import com.adriancichy.receiptprocessor.domain.Receipt;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter(AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE, setterPrefix = "with")
class ReceiptEntity {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "retailer", nullable = false)
    private String retailer;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @Column(name = "purchase_time", nullable = false)
    private LocalTime purchaseTime;

    @OneToMany(mappedBy = "receipt", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> items;

    @Column(name = "total", nullable = false)
    private Float total;

    public static ReceiptEntity createFrom(Receipt receipt) {

        ReceiptEntity receiptEntity = ReceiptEntity.builder()
                .withRetailer(receipt.getRetailer())
                .withPurchaseDate(receipt.getPurchaseDate())
                .withPurchaseTime(receipt.getPurchaseTime())
                .withTotal(receipt.getTotal())
                .build();

        List<ItemEntity> itemEntities = receipt.getItems().stream()
                .map(item -> ItemEntity.createFrom(item, receiptEntity))
                .toList();

        receiptEntity.setItems(itemEntities);
        return receiptEntity;
    }

    public static Receipt toDomain(ReceiptEntity entity) {
        List<Item> domainItems = entity.getItems().stream()
                .map(ItemEntity::toDomain)
                .toList();

        return new Receipt(
                entity.getRetailer(),
                entity.getPurchaseDate(),
                entity.getPurchaseTime(),
                domainItems,
                entity.getTotal()
        );
    }
}