package com.veteroch4k.toolwarehouse.services;

import com.veteroch4k.toolwarehouse.models.FactoryTools;
import com.veteroch4k.toolwarehouse.repositories.FactoryToolsRepository;
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
