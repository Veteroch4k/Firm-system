package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.factories.Factory;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.operations.OpMaterials;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.repositories.BaseRepository;
import java.io.IOException;
import java.util.ArrayList;
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

  // Один-ко-многим
  @Override
  public List<Operation> getFactoryOperations(int factory_id) throws IOException {
    Optional<Factory> factory = repository.getAllFactories().stream().filter(x->x.getId() == factory_id).findFirst();
    List<Operation> operations = repository.getAllOperations();
    List<Integer> involvedOp = factory.get().getOperations();
    return operations.stream()
        .filter(op -> involvedOp.contains(op.getId()))
        .toList();
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

  @Override
  public Map<ToolType, Integer> getOperationTools(int operation_id) throws IOException {
    Operation operation = repository.getAllOperations().get(operation_id);
    Map<Integer, Integer> involvedTools = operation.getTools();

    Map<ToolType, Integer> toolList = new HashMap<>();

    for(Integer key : involvedTools.keySet()) {
      toolList.put(repository.getAllToolsTypes().get(key), involvedTools.get(key));
    }

    return toolList;
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


}


