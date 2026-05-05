package com.veteroch4k.employers.services;

import com.veteroch4k.employers.models.Employer;
import com.veteroch4k.employers.repositories.EmployerRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class EmployerSevice {

  private EmployerRepository repository;

  private static final Logger logger = LoggerFactory.getLogger(EmployerSevice.class);

  public EmployerSevice(EmployerRepository repository) {
    this.repository = repository;
  }

  public Optional<Employer> getRandomEmployer() {
    long count = repository.count();
    if (count == 0) {
      return Optional.empty();
    }

    long idx = (long)(Math.random() * count);
    return repository.findById(idx);
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
        logger.error(e.getMessage());
      }

    }
    return new ResponseEntity<>("result skibiti toilet", HttpStatus.OK);

  }

  public ResponseEntity<String> testBackup(Throwable t) {
    logger.error("Employer service недоступен: {}", t.getMessage());
    return new ResponseEntity<>("разрыватель цепи", HttpStatus.ACCEPTED);
  }

  public ResponseEntity<String> testRetry(Throwable t) {
    return new ResponseEntity<>("Повторная попытка отправки", HttpStatus.ACCEPTED);
  }

  public ResponseEntity<String> testBulk(Throwable t) {
    return new ResponseEntity<>("Герметичный сука отсек", HttpStatus.ACCEPTED);
  }

}
