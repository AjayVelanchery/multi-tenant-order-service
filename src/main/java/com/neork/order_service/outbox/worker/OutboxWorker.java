package com.neork.order_service.outbox.worker;

import com.neork.order_service.outbox.enums.OutboxStatus;
import com.neork.order_service.outbox.model.OutboxEvent;
import com.neork.order_service.outbox.repository.OutboxEventRepository;
import com.neork.order_service.saga.OrderSagaProcessor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class OutboxWorker {

    private final OutboxEventRepository outboxEventRepository;
    private final OrderSagaProcessor orderSagaProcessor;

    public OutboxWorker(OutboxEventRepository outboxEventRepository,
                        OrderSagaProcessor orderSagaProcessor) {
        this.outboxEventRepository = outboxEventRepository;
        this.orderSagaProcessor = orderSagaProcessor;
    }

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void processOutboxEvents() {

        List<OutboxEvent> events =outboxEventRepository
                                  .findByStatusIn(List.of(OutboxStatus.PENDING, OutboxStatus.IN_PROGRESS));

        for (OutboxEvent event : events) {


            event.setStatus(OutboxStatus.IN_PROGRESS);


            orderSagaProcessor.process(event.getAggregateId());


            event.setStatus(OutboxStatus.PROCESSED);
        }
    }
}
