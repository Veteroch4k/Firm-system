package com.veteroch4k.product.product.services.drawing;

import com.veteroch4k.product.exceptions.ResourceNotFoundException;
import com.veteroch4k.product.repositories.DrawingRepository;
import com.veteroch4k.product.services.DrawingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DrawingServiceTest {

    @Mock
    private DrawingRepository drawingRepository;

    @InjectMocks
    private DrawingService drawingService;

    @Test
    void shouldThrowResourceNotFoundExceptionWhenFindDrawingById() {

        Long id = 1L;

        when(drawingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> drawingService.findDrawingById(id));
    }


}
