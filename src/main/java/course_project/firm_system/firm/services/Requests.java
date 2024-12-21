package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public interface Requests {


  Map<Material,Integer> getOperationMaterials(int operation_id) throws IOException;
  Map<ToolType,Integer> getOperationTools(int operation_id) throws IOException;

  Map<Tool,ToolType> getToolsWithTypes() throws IOException;

  Map<Product, Map<ToolType, Integer>> getProductsWithTools() throws IOException;

  Operation getFactoryOperation(int factory_id) throws IOException;
  Map<Material, Integer> getFactoryMaterials(int factory_id) throws IOException;
  Map<Material, Integer> getFactoryTools(int factory_id) throws IOException;
  Map<Material, Integer> checkFactoryRequiredMaterials(int factory_id) throws IOException; // возвращает, сколько недостает материалов


}
