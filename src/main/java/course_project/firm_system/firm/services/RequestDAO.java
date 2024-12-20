package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.Drawing;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.factories.Factory;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.factories.FactoryMaterials;
import course_project.firm_system.firm.models.operations.OpMaterials;
import course_project.firm_system.firm.models.operations.OpTools;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.repositories.BaseRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
    return operations.stream()
        .filter(op -> involvedOp == (op.getId())).findFirst().get();
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
      if(factoryMaterials.get(material) < requiredMaterials.get(material)) {
        missingMaterials.put(material, requiredMaterials.get(material) - factoryMaterials.get(material));
      }
    }
    return missingMaterials;

  }

  @Override
  public Map<Material, Integer> getFactoryTools(int factory_id) throws IOException {
    return Map.of();
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
    List<ToolType> materials = repository.getAllToolsTypes();
    List<OpTools> opTools = repository.getOpTools();

    Map<ToolType, Integer> op = new HashMap<>();

    //2. Создаем Map для быстрого доступа
    Map<Integer, ToolType> toolMap = new HashMap<>();
    for(ToolType tool : materials){
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




}


