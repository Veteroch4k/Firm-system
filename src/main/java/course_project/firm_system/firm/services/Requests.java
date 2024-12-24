package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.reports.Employer;
import course_project.firm_system.firm.models.consumables.reports.MaterialsAccounting;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface Requests {

  // Получение необходимых материалов для данной операции
  Map<Material,Integer> getOperationMaterials(int operation_id) throws IOException;

  // Получение необходимых инструментов для данной операции
  Map<ToolType,Integer> getOperationTools(int operation_id) throws IOException;

  // Получение всех инструментов и указанием их типа
  Map<Tool,ToolType> getToolsWithTypes() throws IOException;

  // Получение продуктов с соответствующими инструментами и их кол-вом, необходимым для данного продукта
  Map<Product, Map<ToolType, Integer>> getProductsWithTools() throws IOException;

  // Получение случайного рабочего
  Employer getRandomEmployer() throws IOException;

  // Получение нарядов в заданном интервале дат
  List<Order> getDateOrders(LocalDate start, LocalDate end) throws IOException;

  // Получение материалов, необходимых для выполнения данного наряда
  Map<Material, Integer> getOrderMaterials(Order order) throws IOException;

  // Получение операции, предоставляемой цехом
  Operation getFactoryOperation(int factory_id) throws IOException;

  // Получение материалов, хранящихся в цеху
  Map<Material, Integer> getFactoryMaterials(int factory_id) throws IOException;

  // Получение типов инструментов, хранящихся в цеху
  Map<ToolType, Integer> getFactoryTools(int factory_id) throws IOException;

  // Получение материалов, необходимых цеху для выполнения операции (создания 1 продукта)
  Map<Material, Integer> checkFactoryRequiredMaterials(int factory_id, int quantity) throws IOException; // возвращает, сколько недостает материалов

  // Получение типов инструментов, необходимых цеху для выполнения операции (создания 1 продукта)
  Map<ToolType, Integer> checkFactoryRequiredTools(int factory_id, int quantity) throws IOException;

  Tool getRandomTool(int toolType_id) throws IOException;

}
