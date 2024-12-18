package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface Requests{

  List<Operation> getFactoryOperations(int factory_id) throws IOException;

  Map<Material,Integer> getOperationMaterials(int operation_id) throws IOException;

  Map<ToolType,Integer> getOperationTools(int operation_id) throws IOException;

  Map<Tool,ToolType> getToolsWithTypes() throws IOException;

  // Object getFirmReportStatistics();

}
