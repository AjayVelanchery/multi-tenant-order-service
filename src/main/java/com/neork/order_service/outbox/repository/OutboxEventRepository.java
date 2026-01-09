package com.neork.order_service.outbox.repository;

import com.neork.order_service.outbox.model.OutboxEvent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent,Long> {


}
