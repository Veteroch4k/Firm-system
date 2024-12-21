package course_project.firm_system.firm.repositories;

import course_project.firm_system.firm.models.Drawing;
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

  List<Factory> getAllFactories() throws IOException;

  List<Operation> getAllOperations() throws IOException;

  List<Material> getAllMaterials() throws IOException;

  List<ToolType> getAllToolsTypes() throws IOException;

  List<Tool> getAllTools() throws IOException;

  List<ToolType> getUsedTools();

  void saveOperation(Operation operation) throws IOException;

  void saveTool(Tool tool) throws IOException;

  List<Product> getAllProducts() throws IOException;

  List<OpMaterials> getOpMaterials() throws IOException;
  List<OpTools> getOpTools() throws IOException;

  List<FactoryMaterials> getFactoryMaterials() throws IOException;
  List<FactoryTools> getFactoryTools() throws IOException;

  List<Drawing> getAllDrawings() throws IOException;

}


/**
 * Потенциально можно добавить, если будет хватать времени
 * 1 - getOperationById
 * 2- getToolById
 * 3- getMaterialById
 * etc
 */