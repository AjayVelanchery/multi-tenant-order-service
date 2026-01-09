package com.neork.order_service.order.query;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class GetOrderQuery {

    private final Long orderId;
}
