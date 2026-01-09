package com.neork.order_service.outbox.repository;

import com.neork.order_service.enums.OutboxStatus;
import com.neork.order_service.outbox.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent,Long> {
    List<OutboxEvent> findByStatusIn(List<OutboxStatus> statuses);

}
