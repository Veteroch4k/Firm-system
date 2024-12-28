package course_project.firm_system.firm.repositories;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.firm_system.firm.models.Drawing;
import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.reports.Employer;
import course_project.firm_system.firm.models.reports.FreeTools;
import course_project.firm_system.firm.models.reports.MaterialsAccounting;
import course_project.firm_system.firm.models.reports.OrdersAccounting;
import course_project.firm_system.firm.models.reports.ToolAccounting;
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

  private static final File factoryFilePath = new File("src/main/resources/db/factory/factories.json");
  private static final File factoryMaterialsFilePath = new File("src/main/resources/db/factory/fctrMaterials.json");
  private static final File factoryToolsFilePath = new File("src/main/resources/db/factory/fctrTools.json");

  private static final File operationFilePath = new File( "src/main/resources/db/op/operations.json");
  private static final File opMaterialsFilePath = new File( "src/main/resources/db/op/opMaterials.json");
  private static final File opToolsFilePath = new File( "src/main/resources/db/op/opTools.json");

  private static final File materialFilePath = new File("src/main/resources/db/materials.json");

  private static final File toolTypesFilePath = new File("src/main/resources/db/toolTypes.json");
  private static final File toolFilePath = new File("src/main/resources/db/tools.json");
  private static final File freeToolFilePath = new File("src/main/resources/db/reports/freeTools.json");

  private static final File productsFilePath = new File( "src/main/resources/db/products.json");

  private static final File drawingsFilePath = new File( "src/main/resources/db/drawings.json");

  private static final File materialAccountingsFilePath = new File( "src/main/resources/db/reports/materialAccountings.json");
  private static final File toolAccountingsFilePath = new File( "src/main/resources/db/reports/toolAccountings.json");
  private static final File employersFilePath = new File( "src/main/resources/db/reports/employers.json");
  private static final File ordersFilePath = new File( "src/main/resources/db/reports/orders.json");
  private static final File ordersAccountingFilePath = new File( "src/main/resources/db/reports/ordersAccounting.json");


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
  public List<FactoryMaterials> getFactoryMaterials() throws IOException {
    if (factoryMaterialsFilePath.exists()) {
      return objectMapper.readValue(factoryMaterialsFilePath, new TypeReference<>(){});
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
  public List<FactoryTools> getFactoryTools() throws IOException {
    if (factoryToolsFilePath.exists()) {
      return objectMapper.readValue(factoryToolsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public Operation getCertainOp(int id) throws IOException {

    return getAllOperations().stream().filter(x-> x.getId() == id).findFirst().get();
  }

  @Override
  public List<MaterialsAccounting> getMaterialAccountings() throws IOException {

    if (materialAccountingsFilePath.exists()) {
      return objectMapper.readValue(materialAccountingsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<ToolAccounting> getToolAccounting() throws IOException {
    if (toolAccountingsFilePath.exists()) {
      return objectMapper.readValue(toolAccountingsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<Employer> getAllEmployers() throws IOException {
    if (employersFilePath.exists()) {
      return objectMapper.readValue(employersFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public void saveMaterialAccounting(List<MaterialsAccounting> list) throws IOException {

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(materialAccountingsFilePath, list);

  }

  @Override
  public void saveToolAccounting(List<ToolAccounting> list) throws IOException {
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(toolAccountingsFilePath, list);

  }

  @Override
  public void saveFactoryMaterials(List<FactoryMaterials> list) throws IOException {
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(factoryMaterialsFilePath, list);
  }

  @Override
  public List<Order> getAllOrders() throws IOException {
    if (ordersFilePath.exists()) {
      return objectMapper.readValue(ordersFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public Order getOrder(int order_id) throws IOException {
    return getAllOrders().stream().filter(x->x.getId()==order_id).findFirst().get();
  }

  @Override
  public void saveOrder(Order order) throws IOException {

    List<Order> orders = getAllOrders();

    orders.add(order);

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(ordersFilePath, orders);

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
  public void saveFactoryTools(List<FactoryTools> list) throws IOException {
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(factoryToolsFilePath, list);
  }

  @Override
  public List<FreeTools> getFreeTools() throws IOException {
    if (freeToolFilePath.exists()) {
      return objectMapper.readValue(freeToolFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
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
  public List<Drawing> getAllDrawings() throws IOException {
    if (drawingsFilePath.exists()) {
      return objectMapper.readValue(drawingsFilePath, new TypeReference<>(){});
    }
    return new ArrayList<>();
  }

  @Override
  public void saveFreeTools(List<FreeTools> freeTools) throws IOException {
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(freeToolFilePath, freeTools);

  }

  @Override
  public List<OrdersAccounting> getOrderAccounting() throws IOException {
    if (ordersAccountingFilePath.exists()) {
      return objectMapper.readValue(ordersAccountingFilePath, new TypeReference<>(){});
    }
    return new ArrayList<>();
  }

  @Override
  public void saveOrderAccounting(List<OrdersAccounting> accountings) throws IOException {
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(ordersAccountingFilePath, accountings);
  }

}
