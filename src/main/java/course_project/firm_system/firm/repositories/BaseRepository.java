package course_project.firm_system.firm.repositories;

import course_project.firm_system.firm.models.Drawing;
import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.reports.Employer;
import course_project.firm_system.firm.models.reports.FreeTools;
import course_project.firm_system.firm.models.reports.MaterialsAccounting;
import course_project.firm_system.firm.models.reports.OrdersAccounting;
import course_project.firm_system.firm.models.reports.ToolAccounting;
import course_project.firm_system.firm.models.factories.Factory;
import course_project.firm_system.firm.models.factories.FactoryMaterials;
import course_project.firm_system.firm.models.factories.FactoryTools;
import course_project.firm_system.firm.models.operations.OpMaterials;
import course_project.firm_system.firm.models.operations.OpTools;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BaseRepository {


  // Получение списка работников склада
  List<Employer> getAllEmployers() throws IOException;

  List<Product> getAllProducts() throws IOException;

  List<Drawing> getAllDrawings() throws IOException;

  // Работа с нарядами
  List<Order> getAllOrders() throws IOException;

  // Получение наряда по id
  Order getOrder(int order_id) throws IOException;

  // Сохранение наряда
  void saveOrder(Order order) throws IOException;

  // Сохранение отчетности нарядов
  void saveOrderAccounting(List<OrdersAccounting> accountings) throws IOException;

  // Получение отчености нарядов
  List<OrdersAccounting> getOrderAccounting() throws IOException;


  // Получение всех цехов
  List<Factory> getAllFactories() throws IOException;


  // Получение всех операций
  List<Operation> getAllOperations() throws IOException;


  // Сохранение новой операции
  void saveOperation(Operation operation) throws IOException;

  // Получение экземпляра отдельной операции
  Operation getCertainOp(int id) throws IOException;

  // Получение операции, предоставляемой цехом
  Operation getFactoryOperation(int factory_id) throws IOException;

  Drawing getDrawingByProductId(int product_id) throws IOException;

}
