package com.veteroch4k.toolwarehouse.service;

import com.veteroch4k.toolwarehouse.model.FactoryTools;
import com.veteroch4k.toolwarehouse.repository.FactoryToolsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FactoryToolsService {

    private final FactoryToolsRepository factoryToolsRepository;


    public List<FactoryTools> getFactoryTools(Long factoryId) {

        return factoryToolsRepository.findAllByFactoryId(factoryId);

    }
}
