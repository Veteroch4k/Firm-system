package course_project.firm_system.firm.services.toolsService;

import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ToolsRequests {

  // Получение необходимых инструментов для данной операции
  Map<ToolType,Integer> getOperationTools(int operation_id) throws IOException;

  // Получение типов инструментов, хранящихся в цеху
  Map<ToolType, Integer> getFactoryTools(int factory_id) throws IOException;

  // Получение типов инструментов, необходимых цеху для выполнения операции (создания 1 продукта)
  Map<ToolType, Integer> checkFactoryRequiredTools(int factory_id, int quantity) throws IOException;

  Tool getRandomTool(int toolType_id) throws IOException;

  Tool generateNewTool(int toolType_id) throws IOException;

  void checkEnoughFreeTools(ToolType type, int quantity) throws IOException;

  void generateRequiredTools(ToolType type, int quantity) throws IOException;

  // Получение всех инструментов и указанием их типа
  Map<Tool,ToolType> getToolsWithTypes() throws IOException;

  // Получение продуктов с соответствующими инструментами и их кол-вом, необходимым для данного продукта
  Map<Product, Map<ToolType, Integer>> getProductsWithTools() throws IOException;

  // Получение используемых инструментов
  List<Tool> getUsedTools() throws IOException;


}
