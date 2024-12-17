package course_project.firm_system.firm.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.firm_system.firm.models.Factory;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.operations.Operation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public class RequestDAO implements RequestRepository {

  private final ObjectMapper objectMapper;

  private final File factoryFilePath = new File("src/main/resources/db/factory.json");
  private final File operationFilePath = new File( "src/main/resources/db/operations.json");
  private final File materialFilePath = new File("src/main/resources/db/materials.json");
  private final File toolFilePath = new File("src/main/resources/db/tools.json");
  private final File productsFilePath = new File( "src/main/resources/db/warehouse.json");

  public RequestDAO(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  public List<Factory> getFactories() throws IOException {
    if (factoryFilePath.exists()) {
      return objectMapper.readValue(factoryFilePath, new TypeReference<>(){});
    }
    return new ArrayList<>();
  }

  public List<Operation> getOperations() throws IOException {
    if (operationFilePath.exists()) {
      return objectMapper.readValue(operationFilePath, new TypeReference<>(){});

    }
    return new ArrayList<>();
  }

  public List<Material> getMaterials() throws IOException {

    if (materialFilePath.exists()) {
      return objectMapper.readValue(materialFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<Operation> getFactoryOperations(int factory_id) throws IOException {
    List<Factory> factoryList = getFactories();
    return factoryList.get(factory_id).getOperations();
  }

  // Goida
  @Override
  public Set<Integer> getOperationMaterials(int operation_id) throws IOException {
    List<Operation> allMaterials = getOperations();
    return null;

  }

  @Override
  public List<Operation> getAllOperations() throws IOException {
    return List.of(objectMapper.readValue(operationFilePath, new TypeReference<>(){}));
  }

  @Override
  public void saveOperation(Operation operation) {

  }

  @Override
  public List<Product> getAllProducts() throws IOException {
    return List.of(objectMapper.readValue(productsFilePath, new TypeReference<>(){}));
  }

  @Override
  public List<Tool> getUsedTools() {
    return List.of();
  }

}
