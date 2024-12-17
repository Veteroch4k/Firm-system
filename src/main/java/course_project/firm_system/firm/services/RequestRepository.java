package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.Operation;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.ToolType;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository  {

  List<Operation> getFactoryOperations(int factory_id) throws IOException;

  Map<Material,Integer> getOperationMaterials(int operation_id) throws IOException;
  Map<ToolType,Integer> getOperationTools(int operation_id) throws IOException;

  List<ToolType> getUsedTools();

  List<Operation> getAllOperations() throws IOException;

  void saveOperation(Operation operation);

  List<Product> getAllProducts() throws IOException;

  /**
   * Потенциально можно добавить, если будет хватать времени
   * 1 - getOperationById
   * 2- getToolById
   * 3- getMaterialById
   * etc
   */

  // Object getFirmReportStatistics();

}
