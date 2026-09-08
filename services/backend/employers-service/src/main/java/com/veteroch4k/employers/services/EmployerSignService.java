package com.veteroch4k.employers.services;

import com.veteroch4k.employers.dto.EmployerResponse;
import com.veteroch4k.employers.models.EmployerSign;
import com.veteroch4k.employers.models.commands.SignOrderCommand;
import com.veteroch4k.employers.repositories.EmployerRepository;
import com.veteroch4k.employers.repositories.EmployerSignRepository;
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
