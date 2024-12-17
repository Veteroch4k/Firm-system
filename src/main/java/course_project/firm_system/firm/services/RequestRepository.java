package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Tool;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository  {

  List<Operation> getFactoryOperations(int factory_id);

  List<Object> getOperationMaterials(int operation_id);

  List<Tool> getUsedTools();

  List<Operation> getAllOperations();

  void saveOperation(Operation operation);

  List<Product> getAllProducts();

  /**
   * Потенциально можно добавить, если будет хватать времени
   * 1 - getOperationById
   * 2- getToolById
   * 3- getMaterialById
   * etc
   */

  // Object getFirmReportStatistics();

}
