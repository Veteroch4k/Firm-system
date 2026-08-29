package com.veteroch4k.employers.services;

import com.veteroch4k.employers.dto.EmployerResponse;
import com.veteroch4k.employers.models.Employer;
import com.veteroch4k.employers.repositories.EmployerRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeoutException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.errors.ResourceNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployerSevice {

  private final EmployerRepository repository;

  public Page<EmployerResponse> findAllEmployers(PageRequest of) {

    Page<Employer> page = repository.findAll(of);

    return page.map(this::getEmployerResponse);

  }

  public EmployerResponse findEmployerById(Long id) {

    Employer employer = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Сотрудика с ID: " + id + " не существует"));

    return getEmployerResponse(employer);
  }


  public EmployerResponse getRandomEmployer() {
    long count = repository.count();

    if (count == 0) {
      throw new ResourceNotFoundException("Таблица сотрудников пуста!");
    }

    long idx = (long)(Math.random() * count);
    Employer employer = repository.findById(idx).orElseThrow(() -> new ResourceNotFoundException("Непредвиденная на данном эатпе ошибка )"));
    return getEmployerResponse(employer);
  }

  @CircuitBreaker(name = "test", fallbackMethod = "testBackup")
  @Retry(name = "retryTest", fallbackMethod = "testRetry")
  @Bulkhead(name = "bulkheadTest", fallbackMethod = "testBulk")
  public ResponseEntity<String> immitateSomeWork() throws TimeoutException {

    Random r = new Random();

    int rNum = r.nextInt(3) + 1;



    if (rNum == 3) {
      try {
        System.out.println("Sleep");
        Thread.sleep(5000);
        throw new java.util.concurrent.TimeoutException();
      } catch (InterruptedException e) {
        log.error(e.getMessage());
      }

    }
    return new ResponseEntity<>("result skibiti toilet", HttpStatus.OK);

  }

  public ResponseEntity<String> testBackup(Throwable t) {
    log.error("Employer service недоступен: {}", t.getMessage());
    return new ResponseEntity<>("разрыватель цепи", HttpStatus.ACCEPTED);
  }

  public ResponseEntity<String> testRetry(Throwable t) {
    return new ResponseEntity<>("Повторная попытка отправки", HttpStatus.ACCEPTED);
  }

  public ResponseEntity<String> testBulk(Throwable t) {
    return new ResponseEntity<>("Герметичный сука отсек", HttpStatus.ACCEPTED);
  }

  private  EmployerResponse getEmployerResponse(Employer employer) {

    return new EmployerResponse(
            employer.getId(),
            employer.getName()
    );

  }


}
