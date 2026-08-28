package com.veteroch4k.product.services;

import com.veteroch4k.product.dto.ProductManufacturingInfoResponse;
import com.veteroch4k.product.dto.drawing.DrawingResponse;
import com.veteroch4k.product.dto.product.ProductResponse;
import com.veteroch4k.product.exceptions.ResourceNotFoundException;
import com.veteroch4k.product.models.Product;
import com.veteroch4k.product.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

    private final ProductRepository productRepository;

    public ProductManufacturingInfoResponse getProductInfo(Long id) {

        log.debug("Получение производственной информации о продукте по его id: {}", id);

        Product product = productRepository.findById(id).
                orElseThrow(() -> {
                    log.warn("Продукт с ID: {} не найден во время получения производственной инфы о продукте.", id);
                    return new ResourceNotFoundException("Продукта по заданному id: " + id + " не найдено.");
                });

        return new ProductManufacturingInfoResponse(
                product.getId(),
                product.getDescription(),
                product.getDrawing().getId(),
                product.getDrawing().getFactoryId(),
                product.getDrawing().getOperationId());
    }

    public Page<ProductResponse> findAllProducts(PageRequest of) {

        Page<Product> product = productRepository.findAll(of);


        return product.map(this::getProductResponse);
    }

    public ProductResponse findProductById(Long id) {

        Product product = productRepository.findById(id).orElseThrow(() -> {
            log.warn("Продукт с ID: {} не найден при запросе по ID", id);
            return new ResourceNotFoundException("Продукта по заданному id: " + id + " не найдено.");
        });

        return getProductResponse(product);

    }

    private ProductResponse getProductResponse(Product product) {

        return new ProductResponse(product.getId(),
                product.getDescription(),
                new DrawingResponse(product.getDrawing().getId(),
                        product.getDrawing().getOperationId(),
                        product.getDrawing().getFactoryId())

        );

    }
}
