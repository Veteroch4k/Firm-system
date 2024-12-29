package course_project.firm_system.firm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import course_project.firm_system.firm.models.Drawing;
import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.factories.Factory;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.reports.Employer;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.repositories.BaseRepositoryImpl;
import course_project.firm_system.firm.services.RequestDAO;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestsServiceTests {


  @Mock
  private BaseRepository repository;

  @InjectMocks
  private RequestDAO requestDAO;

  private Factory factory;
  private Operation operation;


  @BeforeEach
  void setUp() {
    // Инициализация базовых «заглушек» данных
    factory = new Factory();
    factory.setId(1);
    factory.setOperation_id(10);

    operation = new Operation();
    operation.setId(10);
    operation.setDuration(8);

  }

  @Test
  void testGetOrderDeadLine() throws IOException {
    // Создаём заказ (Order)
    Order order = new Order();
    order.setFinish_date(LocalDate.of(2024, 1, 1));
    order.setProduct_quantity(6);
    order.setProduct_id(10);

    // Имитируем связь Product -> Drawing -> Operation
    Product product = new Product();
    product.setId(10);
    product.setDrawing_id(500);

    Drawing drawing = new Drawing();
    drawing.setId(500);
    drawing.setOperation_id(operation.getId());

    // Настраиваем моки
    when(repository.getAllProducts()).thenReturn(List.of(product));
    when(repository.getAllDrawings()).thenReturn(List.of(drawing));
    when(repository.getAllOperations()).thenReturn(List.of(operation));
    when(repository.getDrawingByProductId(order.getProduct_id())).thenReturn(drawing);
    LocalDate date = requestDAO.getOrderDeadLine(order);

    // Проверяем — код в dao: hours = operation.duration * order.product_quantity
    // у нас duration=8, product_quantity=6 => hours=48
    // 48 часов = 48 / 24 = 2 дня
    // Это добавляет 2 дня
    assertEquals(LocalDate.of(2024, 1, 3), date,
        "Согласно текущей реализации (без присваивания) дата не изменяется");
  }

  @Test
  void testGetRandomEmployer() throws IOException {
    Employer e1 = new Employer();
    e1.setId(1);
    e1.setName("John");

    Employer e2 = new Employer();
    e2.setId(2);
    e2.setName("Jane");

    when(repository.getAllEmployers()).thenReturn(List.of(e1, e2));

    Employer randomEmp = requestDAO.getRandomEmployer();
    assertNotNull(randomEmp);
    // Проверять что-то конкретное в "случайных" методах не всегда удобно,
    // можно проверить хотя бы, что он из заданного списка
    assertTrue(randomEmp.getId() == 1 || randomEmp.getId() == 2);
  }

  @Test
  void testGetDateOrders() throws IOException {
    Order o1 = new Order();
    o1.setId(1);
    o1.setOrder_date(LocalDate.of(2024, 1, 10));
    o1.setFinish_date(LocalDate.of(2024, 1, 15));

    Order o2 = new Order();
    o2.setId(2);
    o2.setOrder_date(LocalDate.of(2024, 1, 20));
    o2.setFinish_date(LocalDate.of(2024, 1, 25));

    Order o3 = new Order();
    o3.setId(3);
    o3.setOrder_date(LocalDate.of(2024, 2, 1));
    o3.setFinish_date(LocalDate.of(2024, 2, 5));

    when(repository.getAllOrders()).thenReturn(List.of(o1, o2, o3));

    // Ищем наряды в интервале 2024-01-01 по 2024-01-31
    List<Order> result = requestDAO.getDateOrders(
        LocalDate.of(2024, 1, 1),
        LocalDate.of(2024, 1, 31)
    );
    // Ожидаем, что попадут только o1 и o2
    assertEquals(2, result.size());
    assertTrue(result.contains(o1));
    assertTrue(result.contains(o2));
  }


}
