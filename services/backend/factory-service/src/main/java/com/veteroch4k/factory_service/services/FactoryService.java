package com.veteroch4k.factory_service.services;

import com.veteroch4k.factory_service.dto.factory.FactoryResponse;
import com.veteroch4k.factory_service.exceptions.ResourceNotFoundException;
import com.veteroch4k.factory_service.mappers.FactoryMapper;
import com.veteroch4k.factory_service.models.Factory;
import com.veteroch4k.factory_service.repository.FactoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FactoryService {
    
    private final FactoryRepository factoryRepository;
    private final FactoryMapper factoryMapper;

    @Transactional(readOnly = true)
    public Page<FactoryResponse> findAllFactories(PageRequest of) {

        Page<Long> ids = factoryRepository.findFactoryIds(of);

        List<Factory> factories = factoryRepository.findFactoryByIdIn(ids.getContent());

        return new PageImpl<>(factories.stream().map(factoryMapper::toFactoryResponse).toList()
                , of, ids.getTotalElements());
    }

    public FactoryResponse findFactoryById(Long id) {

        Factory factory = factoryRepository.findFactoryById(id).orElseThrow(() -> {
            log.warn("Фабрика с ID: {} не найдена при запросе фабрики по ID", id);
            return new ResourceNotFoundException("Фабрика с ID: " + id + " не найдена");
        });

        return factoryMapper.toFactoryResponse(factory);
    }
}
