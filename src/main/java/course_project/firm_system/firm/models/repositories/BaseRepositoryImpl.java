package course_project.firm_system.firm.models.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.firm_system.firm.models.Factory;
import course_project.firm_system.firm.models.Operation;
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


  private final File factoryFilePath = new File("src/main/resources/db/factory.json");
  private final File operationFilePath = new File( "src/main/resources/db/operations.json");
  private final File materialFilePath = new File("src/main/resources/db/materials.json");
  private final File toolTypesFilePath = new File("src/main/resources/db/toolTypes.json");
  private final File toolFilePath = new File("src/main/resources/db/tools.json");
  private final File productsFilePath = new File( "src/main/resources/db/warehouse.json");


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
  public void saveOperation(Operation operation) {

  }

  @Override
  public List<Product> getAllProducts() throws IOException {
    return List.of(objectMapper.readValue(productsFilePath, new TypeReference<>(){}));
  }



}
