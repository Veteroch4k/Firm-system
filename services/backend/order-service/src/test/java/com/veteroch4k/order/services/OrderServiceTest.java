package com.veteroch4k.order.services;

import com.veteroch4k.order.exceptions.ResourceNotFoundException;
import com.veteroch4k.order.repository.OrderRepository;
import com.veteroch4k.order.service.KafkaProducerService;
import com.veteroch4k.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private KafkaProducerService kafkaProducerService;

    @InjectMocks
    private OrderService orderService;

    @Test
    void shouldThrowExceptionWhenOrderIsEmpty() {

        Long orderId = 1L;

        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> orderService.findOrderById(orderId)
        );

    }

}
