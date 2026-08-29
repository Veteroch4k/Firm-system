package com.veteroch4k.product.product.services;

import com.veteroch4k.product.exceptions.ResourceNotFoundException;
import com.veteroch4k.product.repositories.ProductRepository;
import com.veteroch4k.product.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    @Test
    void shouldThrowResourceNotFoundExceptionWhenProductIsEmpty() {

        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        ResourceNotFoundException ex = assertThrows(
                ResourceNotFoundException.class,
                () -> productService.findProductById(productId)
        );

    }
}
