package com.neork.order_service.order.model;

import com.neork.order_service.order.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String tenantId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(name = "failure_message")
    private String failureMessage;

    public void markProcessed() {
        this.status = OrderStatus.PROCESSED;
        this.failureMessage = null;
    }

    public void markFailed(String message) {
        this.status = OrderStatus.FAILED;
        this.failureMessage = message;
    }

}
