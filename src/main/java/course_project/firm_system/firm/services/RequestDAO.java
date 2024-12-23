package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.Drawing;
import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.reports.Employer;
import course_project.firm_system.firm.models.factories.Factory;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.factories.FactoryMaterials;
import course_project.firm_system.firm.models.factories.FactoryTools;
import course_project.firm_system.firm.models.operations.OpMaterials;
import course_project.firm_system.firm.models.operations.OpTools;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.repositories.BaseRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestDAO implements Requests {

  @Autowired
  private BaseRepository repository;

  // 1-к-1
  @Override
  public Operation getFactoryOperation(int factory_id) throws IOException {
    Optional<Factory> factory = repository.getAllFactories().stream().filter(x->x.getId() == factory_id).findFirst();
    List<Operation> operations = repository.getAllOperations();
    int involvedOp = factory.get().getOperation_id();
    return repository.getCertainOp(involvedOp);
  }

  @Override
  public Map<Material, Integer> getFactoryMaterials(int factory_id) throws IOException {
    List<Material> materials = repository.getAllMaterials();
    List<FactoryMaterials> opMaterials = repository.getFactoryMaterials();

    Map<Material, Integer> factory = new HashMap<>();

    //2. Создаем Map для быстрого доступа
    Map<Integer, Material> materialMap = new HashMap<>();
    for(Material material : materials){
      materialMap.put(material.getId(), material);
    }

    // 3. Обрабатываем связи
    for(FactoryMaterials mater : opMaterials) {
      if(mater.getFactory_id() == factory_id) {
        factory.put(materialMap.get(mater.getMaterial_id()), mater.getQuantity());
      }

    }

    return factory;
  }


  @Override
  public Map<Material, Integer> checkFactoryRequiredMaterials(int factory_id) throws IOException {

    Map<Material, Integer> factoryMaterials = getFactoryMaterials(factory_id);

    Map<Material, Integer> requiredMaterials = getOperationMaterials(getFactoryOperation(factory_id).getId());

    Map<Material, Integer> missingMaterials = new HashMap<>();

    for(Material material : factoryMaterials.keySet()) {
      if(factoryMaterials.get(material) < requiredMaterials.get(material)*2) { // Домножаю на два, т.к. даю материалы с излишком, на 2 продукта
        missingMaterials.put(material, requiredMaterials.get(material)*2 - factoryMaterials.get(material));
      }
    }

    // Обновление таблицы материалов фабрики
    List<FactoryMaterials> factoryMaterials1 = repository.getFactoryMaterials().stream().filter(x->x.getFactory_id()==factory_id).toList();
    for(Material material : requiredMaterials.keySet()) {
      factoryMaterials1.stream().filter(x->x.getMaterial_id()==material.getId()).findFirst().get()
          .setQuantity((int) Math.ceil(requiredMaterials.get(material)*0.75));
    }
    repository.saveFactoryMaterials(factoryMaterials1);


    return missingMaterials;


  }


  @Override
  public Map<ToolType, Integer> getFactoryTools(int factory_id) throws IOException {

    List<ToolType> materials = repository.getAllToolsTypes();
    List<FactoryTools> opTools = repository.getFactoryTools();

    Map<ToolType, Integer> factory = new HashMap<>();

    //2. Создаем Map для быстрого доступа
    Map<Integer, ToolType> materialMap = new HashMap<>();
    for(ToolType toolType : materials){
      materialMap.put(toolType.getId(), toolType);
    }

    // 3. Обрабатываем связи
    for(FactoryTools factoryTools : opTools) {
      if(factoryTools.getFactory_id() == factory_id) {
        factory.put(materialMap.get(factoryTools.getToolType_id()), factoryTools.getQuantity());
      }

    }

    return factory;
  }

  @Override
  public Map<ToolType, Integer> checkFactoryRequiredTools(int factory_id) throws IOException {
    Map<ToolType, Integer> factoryTools = getFactoryTools(factory_id);

    Map<ToolType, Integer> requiredMaterials = getOperationTools(getFactoryOperation(factory_id).getId());

    Map<ToolType, Integer> missingToolTypes = new HashMap<>();

    for(ToolType toolType : factoryTools.keySet()) {
      if(factoryTools.get(toolType) < requiredMaterials.get(toolType)) {
        missingToolTypes.put(toolType, requiredMaterials.get(toolType)- factoryTools.get(toolType));
      }
    }

    // Обновление таблицы материалов фабрики
    List<FactoryTools> factoryTools1 = repository.getFactoryTools().stream().filter(x->x.getFactory_id()==factory_id).toList();
    for(ToolType toolType : requiredMaterials.keySet()) {
      factoryTools1.stream().filter(x->x.getToolType_id()==toolType.getId()).findFirst().get()
          .setQuantity(requiredMaterials.get(toolType));
    }
    repository.saveFactoryTools(factoryTools1);


    return missingToolTypes;
  }

  // Многие-ко-многим
  @Override
  public Map<Material, Integer> getOperationMaterials(int operation_id) throws IOException {

    List<Material> materials = repository.getAllMaterials();
    List<OpMaterials> opMaterials = repository.getOpMaterials();

    Map<Material, Integer> op = new HashMap<>();

    //2. Создаем Map для быстрого доступа
    Map<Integer, Material> materialMap = new HashMap<>();
    for(Material material : materials){
      materialMap.put(material.getId(), material);
    }

    // 3. Обрабатываем связи
    for(OpMaterials mtrls : opMaterials) {
      if(mtrls.getOperation_id() == operation_id) {
        op.put(materialMap.get(mtrls.getMaterial_id()), mtrls.getQuantity());
      }

    }

    return op;
  }

  // Многие-ко-многим
  @Override
  public Map<ToolType, Integer> getOperationTools(int operation_id) throws IOException {
    List<ToolType> toolTypes = repository.getAllToolsTypes();
    List<OpTools> opTools = repository.getOpTools();

    Map<ToolType, Integer> op = new HashMap<>();

    //2. Создаем Map для быстрого доступа
    Map<Integer, ToolType> toolMap = new HashMap<>();
    for(ToolType tool : toolTypes){
      toolMap.put(tool.getId(), tool);
    }

    // 3. Обрабатываем связи
    for(OpTools tls : opTools) {
      if(tls.getOperation_id() == operation_id) {
        op.put(toolMap.get(tls.getToolType_id()), tls.getQuantity());
      }

    }

    return op;
  }

  @Override
  public Map<Tool,ToolType> getToolsWithTypes() throws IOException {
    List<ToolType> toolTypes = repository.getAllToolsTypes();
    List<Tool> tools = repository.getAllTools();


    Map<Tool, ToolType> toolsTips = new HashMap<>();

    for(Tool tool : tools) {
      toolsTips.put(tool, toolTypes.get(tool.getToolType_id()));
    }

    return toolsTips;

  }

  // Многие-ко-многим
  @Override
  public Map<Product, Map<ToolType, Integer>> getProductsWithTools() throws IOException {
    List<Product> products = repository.getAllProducts();
    List<Drawing> drawings = repository.getAllDrawings();

    Map<Product, Map<ToolType, Integer>> res = new HashMap<>();

    for(Product prdct : products) {
      Map<ToolType, Integer> toolTypeList = getOperationTools(drawings.stream()
              .filter(x-> x.getId() == prdct.getDrawing_id()).findFirst().get().getOperation_id());
      res.put(prdct, toolTypeList);

    }

    return res;

  }

  @Override
  public Employer getRandomEmployer() throws IOException {
    Random r = new Random();

    List<Employer> employers = repository.getAllEmployers();

    return employers.get(r.nextInt(employers.size()));

  }

  @Override
  public List<Order> getDateOrders(LocalDate start, LocalDate end)  throws IOException {
    List<Order> orders = repository.getAllOrders();

    return orders.stream()
        .filter(x-> ((start.isBefore(x.getOrder_date()) || start.isEqual(x.getOrder_date()))
            && end.isBefore(x.getFinish_date()) || end.isEqual(x.getFinish_date()) ) ).toList();

  }

  @Override
  public Map<Material, Integer> getOrderMaterials(Order order) throws IOException {
    int drawing_id = repository.getAllProducts()
        .stream().filter(x-> x.getId() == order.getProduct_id()).findFirst().get()
        .getDrawing_id(); // Получение чертежа продукта через id продукта в наряде

    Map<Material, Integer> map = getOperationMaterials(repository.getAllDrawings()
            .stream().filter(x-> x.getId() == drawing_id).findFirst().get().getOperation_id());

    map.replaceAll((m, v) -> map.get(m) * order.getProduct_quantity()); // Умножаем каждое значение на кол-во продуктов

    return map;

  }

}


