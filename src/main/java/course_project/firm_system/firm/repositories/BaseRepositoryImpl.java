package course_project.firm_system.firm.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRepositoryImpl implements BaseRepository{

  private final ObjectMapper objectMapper;


  private final File factoryFilePath = new File("src/main/resources/db/factory/factories.json");
  private final File factoryToolsFilePath = new File("src/main/resources/db/factory/fctrMaterials.json");
  private final File factoryMaterialsFilePath = new File("src/main/resources/db/factory/fctrTools.json");

  private final File operationFilePath = new File( "src/main/resources/db/op/operations.json");
  private final File opMaterialsFilePath = new File( "src/main/resources/db/op/opMaterials.json");
  private final File opToolsFilePath = new File( "src/main/resources/db/op/opTools.json");

  private final File materialFilePath = new File("src/main/resources/db/materials.json");

  private final File toolTypesFilePath = new File("src/main/resources/db/toolTypes.json");
  private final File toolFilePath = new File("src/main/resources/db/tools.json");

  private final File productsFilePath = new File( "src/main/resources/db/products.json");

  private final File drawingsFilePath = new File( "src/main/resources/db/drawings.json");


  public BaseRepositoryImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public List<Factory> getAllFactories() throws IOException {
    if (factoryFilePath.exists()) {
      return objectMapper.readValue(factoryFilePath, new TypeReference<>(){});
    }
    return new ArrayList<>();
  }

  @Override
  public List<Operation> getAllOperations() throws IOException {
    if (operationFilePath.exists()) {
      return objectMapper.readValue(operationFilePath, new TypeReference<>(){});

    }
    return new ArrayList<>();
  }

  @Override
  public List<Material> getAllMaterials() throws IOException {

    if (materialFilePath.exists()) {
      return objectMapper.readValue(materialFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<ToolType> getAllToolsTypes() throws IOException {

    if (toolTypesFilePath.exists()) {
      return objectMapper.readValue(toolTypesFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<Tool> getAllTools() throws IOException {
    if (toolFilePath.exists()) {
      return objectMapper.readValue(toolFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<ToolType> getUsedTools() {
    return List.of();
  }

  @Override
  public void saveOperation(Operation operation) throws IOException {

    List<Operation> operations = getAllOperations();

    operations.add(operation);

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(operationFilePath, operations);

  }

  @Override
  public void saveTool(Tool tool) throws IOException {

    List<Tool> tools = getAllTools();

    tools.add(tool);

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(toolFilePath, tools);


  }

  @Override
  public List<Product> getAllProducts() throws IOException {
    return objectMapper.readValue(productsFilePath, new TypeReference<>(){});
  }

  @Override
  public List<OpMaterials> getOpMaterials() throws IOException {

    if (opMaterialsFilePath.exists()) {
      return objectMapper.readValue(opMaterialsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<OpTools> getOpTools() throws IOException {
    if (opToolsFilePath.exists()) {
      return objectMapper.readValue(opToolsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<FactoryMaterials> getFactoryMaterials() throws IOException {
    if (factoryMaterialsFilePath.exists()) {
      return objectMapper.readValue(factoryMaterialsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();  }

  @Override
  public List<FactoryTools> getFactoryTools() throws IOException {
    if (factoryToolsFilePath.exists()) {
      return objectMapper.readValue(factoryToolsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<Drawing> getAllDrawings() throws IOException {
    if (drawingsFilePath.exists()) {
      return objectMapper.readValue(drawingsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

}
