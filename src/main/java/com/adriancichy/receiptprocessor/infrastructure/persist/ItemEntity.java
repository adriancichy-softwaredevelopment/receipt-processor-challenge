package com.adriancichy.receiptprocessor.infrastructure.persist;

import com.adriancichy.receiptprocessor.domain.Item;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE, setterPrefix = "with")
class ItemEntity {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    UUID id;

    @Column(name = "short_description", nullable = false)
    String shortDescription;

    @Column(name = "price", nullable = false)
    Float price;

    @ManyToOne
    @JoinColumn(name = "receipt_id", nullable = false)
    private ReceiptEntity receipt;

    public static ItemEntity createFrom(Item item, ReceiptEntity receipt) {

        return ItemEntity.builder()
                .withShortDescription(item.getShortDescription())
                .withPrice(item.getPrice())
                .withReceipt(receipt)
                .build();
    }

    public static Item toDomain(ItemEntity entity) {
        return new Item(
                entity.shortDescription,
                entity.price);
    }

}