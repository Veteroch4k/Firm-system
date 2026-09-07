package com.veteroch4k.order.service;

import com.veteroch4k.order.exceptions.ResourceNotFoundException;
import com.veteroch4k.order.model.Order;
import com.veteroch4k.order.model.OrderStatus;
import com.veteroch4k.order.model.event.OrderReadyEvent;
import com.veteroch4k.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaConsumerService {

    private final OrderRepository orderRepository;

    @KafkaListener(topics = "order-ready-events", groupId = "order-ready-group")
    @Transactional
    public void listenOnOrderEvents(OrderReadyEvent command) {

        log.info("Заказ с ID: {} принят на подтверждение", command.orderId());

        Order order = orderRepository.findById(command.orderId())
                .orElseThrow(() -> {
                    log.error("Произошла ошибка утверждения заказа ! ID был изменён либо передан несуществующий! OrderID: {}", command.orderId());
                    return new ResourceNotFoundException("Заказ с ID: " + command.orderId() + " не был найден при попытке утвердить заказ!");
                });
        order.setOrderStatus(OrderStatus.COMPLETED);

        log.info("Заказ с ID: {} успешно подтвержден!", command.orderId());


    }
}
