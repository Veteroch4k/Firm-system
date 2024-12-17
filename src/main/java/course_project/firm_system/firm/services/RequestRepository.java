package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Tool;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository  {

  List<Operation> getFactoryOperations(int factory_id) throws IOException;

  Set<Integer> getOperationMaterials(int operation_id) throws IOException;

  List<Tool> getUsedTools();

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
