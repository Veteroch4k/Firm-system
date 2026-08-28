package com.veteroch4k.product.services;

import com.veteroch4k.product.dto.drawing.DrawingResponse;
import com.veteroch4k.product.exceptions.ResourceNotFoundException;
import com.veteroch4k.product.models.Drawing;
import com.veteroch4k.product.repositories.DrawingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DrawingService {

    private final DrawingRepository drawingRepository;


    public Page<DrawingResponse> findAllDrawings(PageRequest of) {

        Page<Drawing> drawings = drawingRepository.findAll(of);

        return drawings.map(this::getDrawingResponse);
    }

    public DrawingResponse findDrawingById(Long id) {

        Drawing drawing = drawingRepository.findById(id).orElseGet(() -> {

            log.warn("Чертёж с ID: {} не найжен при запросе по ID", id);

            throw new ResourceNotFoundException("Чертеж с заданным id: " + id + " не найден.");
        });

        return getDrawingResponse(drawing);
    }

    private DrawingResponse getDrawingResponse(Drawing drawing) {
        return new DrawingResponse(
                drawing.getId(),
                drawing.getOperationId(),
                drawing.getFactoryId()
        );
    }
}
