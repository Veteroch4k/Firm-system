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
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public class BaseRepositoryImpl implements BaseRepository{

  private final ObjectMapper objectMapper;

  private static final File factoryFilePath = new File("src/main/resources/db/factory/factories.json");
  private static final File operationFilePath = new File( "src/main/resources/db/op/operations.json");
  private static final File productsFilePath = new File( "src/main/resources/db/products.json");
  private static final File drawingsFilePath = new File( "src/main/resources/db/drawings.json");
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
  public List<Operation> getAllOperations() throws IOException {
    if (operationFilePath.exists()) {
      return objectMapper.readValue(operationFilePath, new TypeReference<>(){});

    }
    return new ArrayList<>();
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


  @Override
  public Operation getCertainOp(int id) throws IOException {

    return getAllOperations().stream().filter(x-> x.getId() == id).findFirst().get();
  }


  @Override
  public List<Employer> getAllEmployers() throws IOException {
    if (employersFilePath.exists()) {
      return objectMapper.readValue(employersFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public void saveOperation(Operation operation) throws IOException {

    List<Operation> operations = getAllOperations();

    operations.add(operation);

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(operationFilePath, operations);

  }

  @Override
  public List<Product> getAllProducts() throws IOException {
    return objectMapper.readValue(productsFilePath, new TypeReference<>(){});
  }


  @Override
  public List<Drawing> getAllDrawings() throws IOException {
    if (drawingsFilePath.exists()) {
      return objectMapper.readValue(drawingsFilePath, new TypeReference<>(){});
    }
    return new ArrayList<>();
  }

  // 1-к-1
  @Override
  public Operation getFactoryOperation(int factory_id) throws IOException {
    Optional<Factory> factory = getAllFactories().stream().filter(x->x.getId() == factory_id).findFirst();
    int involvedOp = factory.get().getOperation_id();
    return getCertainOp(involvedOp);
  }

  @Override
  public Drawing getDrawingByProductId(int product_id) throws IOException {
    Product product = getAllProducts()
        .stream().filter(x-> x.getId() == product_id).findFirst().get();

    return getAllDrawings()
        .stream().filter(x->x.getId() == product.getDrawing_id()).findFirst().get();

  }


}
