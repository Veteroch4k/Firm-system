package com.veteroch4k.factory_service.services;

import com.veteroch4k.factory_service.dto.operation.OperationRequest;
import com.veteroch4k.factory_service.dto.operation.OperationResponse;
import com.veteroch4k.factory_service.exceptions.ResourceNotFoundException;
import com.veteroch4k.factory_service.mappers.OperationMapper;
import com.veteroch4k.factory_service.models.Factory;
import com.veteroch4k.factory_service.models.Operation;
import com.veteroch4k.factory_service.repository.FactoryRepository;
import com.veteroch4k.factory_service.repository.OperationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OperationService {

    private final OperationRepository operationRepository;
    private final FactoryRepository factoryRepository;
    private final OperationMapper operationMapper;


    public Page<OperationResponse> findAllOperations(PageRequest of) {

        Page<Operation> operations = operationRepository.findAllWithFactory(of);

        return operations.map(operationMapper::toOperationResponse);

    }

    public OperationResponse findOperationById(Long id) {

        Operation operation = operationRepository.findWithFactoryById(id).orElseThrow(() -> {
            log.warn("Операция с ID: {} не найдена при запросе операции по ID", id);
            return new ResourceNotFoundException("Операция с ID: " + id + " не найдена.");
        });

        return operationMapper.toOperationResponse(operation);

    }

    @Transactional
    public OperationResponse createOperation(OperationRequest op) {

        Factory factory = factoryRepository.findById(op.factoryId()).orElseThrow(() -> {
            log.warn("(FK) Фабрика с ID: {} не найдена при создании операции", op.factoryId());
            return new ResourceNotFoundException("Ошибка внешнего ключа: Фабрики с ID: " + op.factoryId() + " не найдено!");
        });

        Operation operation = operationMapper.toEntity(op);
        operation.setFactory(factory);


        return operationMapper.toOperationResponse(operationRepository.save(operation));

    }

    @Transactional
    public void updateOperation(Long id, OperationRequest op) {

        Operation operation = operationRepository.findById(id).orElseThrow(() -> {
            log.warn("Операция с ID: {} не найдена при обновлении операции", id);
            return new ResourceNotFoundException("Операция с ID: " + id + " не найдена.");
        });

        if (!factoryRepository.existsById(op.factoryId())) {
            log.warn("(FK) Фабрика с ID: {} не найдена при обновлении операции", op.factoryId());
            throw new ResourceNotFoundException("Ошибка внешнего ключа: Фабрики с ID: " + op.factoryId() + " не найдено!");
        }

        operationMapper.updateEntityFromRequest(op, operation);
        operation.setFactory(factoryRepository.getReferenceById(op.factoryId()));


    }

    @Transactional
    public void deleteOperation(Long id) {

        log.debug("Запрос на удаление операции ID: {}", id);

        Operation operation = operationRepository.findById(id).orElseThrow(() -> {
            log.warn("Операция с ID: {} не найдена при попытке удаления операции", id);
            return new ResourceNotFoundException("Операция с ID: " + id + " не найдена.");
        });

        operationRepository.delete(operation);

        log.debug("Успешна удалена операция ID: {}", id);



    }
}
