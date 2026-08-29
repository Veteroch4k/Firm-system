package com.veteroch4k.employers.controllers;

import com.veteroch4k.employers.dto.EmployerResponse;
import com.veteroch4k.employers.exceptions.ErrorResponse;
import com.veteroch4k.employers.models.Employer;
import com.veteroch4k.employers.repositories.EmployerRepository;
import com.veteroch4k.employers.services.EmployerSevice;
import java.util.List;
import java.util.concurrent.TimeoutException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employers")
@Tag(name  = "Employers API", description = "API для управления сотрудниками")
@Validated
public class EmployerController {

  private final EmployerSevice service;


  @Operation(summary = "Получить всех сотрудников", description = "Возвращает пагинированный список")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Сотрудники получены"),
          @ApiResponse(responseCode = "400", description = "Переданы некорректные данные",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @GetMapping("/all")
  public Page<EmployerResponse> getAllEmployers(
          @Parameter(description = "Номер страницы")
      @RequestParam(defaultValue = "0") @Min(0) int page,
      @Parameter(description = "Размер страницы (максимум 100)")
      @RequestParam(defaultValue = "20") @Min(1) @Max(100) int size
  ) {
    return service.findAllEmployers(PageRequest.of(page, size));
  }

  @Operation(summary = "Получение информации о конкректном сотруднике")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Сотрудник успешно получен"),
          @ApiResponse(responseCode = "400", description = "Передан невалидный ID",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "Сотрудника с заданным ID не существует",
          content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @GetMapping("/{id}")
  public EmployerResponse getEmployerById(
          @Parameter(description = "Внутренний ID сотрудника")
          @PathVariable @PositiveOrZero Long id) {
    return service.findEmployerById(id);
  }

  @Operation(summary = "Получить случайного сотрудника")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Сотрудник успешно получен"),
          @ApiResponse(responseCode = "404", description = "Таблица сотрудников пуста",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @GetMapping("/random")
  public EmployerResponse getRandomEmployer() {
    return service.getRandomEmployer();
  }

  @Operation(summary = "Получить случайного сотрудника")
  @ApiResponses(value = {
          @ApiResponse(responseCode = "200", description = "Тестовая работа успешно проделана :)"),
          @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
  })
  @GetMapping("/test")
  public ResponseEntity<String> test() throws TimeoutException {
    return service.immitateSomeWork();
  }
}