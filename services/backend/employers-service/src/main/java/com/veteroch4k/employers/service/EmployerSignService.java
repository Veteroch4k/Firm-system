package com.veteroch4k.employers.service;

import com.veteroch4k.employers.dto.EmployerResponse;
import com.veteroch4k.employers.model.EmployerSign;
import com.veteroch4k.employers.model.commands.SignOrderCommand;
import com.veteroch4k.employers.repository.EmployerRepository;
import com.veteroch4k.employers.repository.EmployerSignRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class EmployerSignService {

    private final EmployerService employerService;
    private final EmployerSignRepository employerSignRepository;
    private final EmployerRepository employerRepository;

    @Transactional
    public void processSign(SignOrderCommand command) {

        EmployerResponse employer = employerService.getRandomEmployer();

        EmployerSign sign = new EmployerSign();
        sign.setEmployer(employerRepository.getReferenceById(employer.id()));
        sign.setOrderId(command.orderId());

        employerSignRepository.save(sign);

    }
}
