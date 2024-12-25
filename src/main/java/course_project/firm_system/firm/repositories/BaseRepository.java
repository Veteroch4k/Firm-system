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

  // Получение всех цехов
  List<Factory> getAllFactories() throws IOException;

  // Сохранение информации о материалах в цеху
  void saveFactoryMaterials(List<FactoryMaterials> list) throws IOException;

  // Сохранение информации об инструментах в цеху
  void saveFactoryTools(List<FactoryTools> list) throws IOException;

  // Получение данных о материалах в цехах
  List<FactoryMaterials> getFactoryMaterials() throws IOException;

  // Получение данных об инструментах в цехах
  List<FactoryTools> getFactoryTools() throws IOException;

  /***/

  // Получение всех операций
  List<Operation> getAllOperations() throws IOException;

  // Сохранение новой операции
  void saveOperation(Operation operation) throws IOException;

  // Получение материалов, необходимых для выполнения операций
  List<OpMaterials> getOpMaterials() throws IOException;

  // Получение инструментов, необходимых для выполнения операций
  List<OpTools> getOpTools() throws IOException;

  // Получение экземпляра отдельной операции
  Operation getCertainOp(int id) throws IOException;


  // Получение отчетности о выданных складом материалах
  List<MaterialsAccounting> getMaterialAccountings() throws IOException;

  // Получение отчетности о выданных инструментальной инструментов
  List<ToolAccounting> getToolAccounting() throws IOException;


  // Сохранение отчетности
  void saveMaterialAccounting(List<MaterialsAccounting> list) throws IOException;
  void saveToolAccounting(List<ToolAccounting> list) throws IOException;

  /***/

  // Получение списка работников склада
  List<Employer> getAllEmployers() throws IOException;

  // Работа с нарядами
  List<Order> getAllOrders() throws IOException;
  Order getOrders(int order_id) throws IOException;
  void saveOrder(Order order) throws IOException;

  /***/

  // Список всех существующих материалов
  List<Material> getAllMaterials() throws IOException;

  // Список всех существующих типов инструментов
  List<ToolType> getAllToolsTypes() throws IOException;

  // Список всех существующих экземпляров инструментов
  List<Tool> getAllTools() throws IOException;

  // Список инструментов, которые отсутствуют в инструментальной
  List<ToolType> getUsedTools();

  // Сохранение нового инструмента
  void saveTool(Tool tool) throws IOException;

  // Получение списка инструментов, находящихся в инструментальной
  List<FreeTools> getFreeTools() throws IOException;

  /***/

  List<Product> getAllProducts() throws IOException;

  List<Drawing> getAllDrawings() throws IOException;

  void saveFreeTools(List<FreeTools> freeTools) throws IOException;

  /***/

  List<OrdersAccounting> getOrderAccounting() throws IOException;

  void saveOrderAccounting(List<OrdersAccounting> accountings) throws IOException;

}
