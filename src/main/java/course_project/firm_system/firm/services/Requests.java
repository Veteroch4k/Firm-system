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


  Map<Material,Integer> getOperationMaterials(int operation_id) throws IOException;
  Map<ToolType,Integer> getOperationTools(int operation_id) throws IOException;

  Map<Tool,ToolType> getToolsWithTypes() throws IOException;

  Map<Product, Map<ToolType, Integer>> getProductsWithTools() throws IOException;

  Employer getRandomEmployer() throws IOException;

  List<Order> getDateOrders(LocalDate start, LocalDate end) throws IOException;
  Map<Material, Integer> getOrderMaterials(Order order) throws IOException;

  Operation getFactoryOperation(int factory_id) throws IOException;
  Map<Material, Integer> getFactoryMaterials(int factory_id) throws IOException;
  Map<Material, Integer> getFactoryTools(int factory_id) throws IOException;
  Map<Material, Integer> checkFactoryRequiredMaterials(int factory_id) throws IOException; // возвращает, сколько недостает материалов


}
