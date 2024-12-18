package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.Factory;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.repositories.BaseRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestDAO implements Requests {

  @Autowired
  private BaseRepository repository;

  @Override
  public List<Operation> getFactoryOperations(int factory_id) throws IOException {
    Factory factory = repository.getAllFactories().get(factory_id);
    List<Operation> operations = repository.getAllOperations();
    List<Integer> involvedOp = factory.getOperations();
    return operations.stream()
        .filter(op -> involvedOp.contains(op.getId()))
        .toList();
  }

  @Override
  public Map<Material, Integer> getOperationMaterials(int operation_id) throws IOException {
    Operation operation = repository.getAllOperations().get(operation_id);
    Map<Integer, Integer> involvedMaterials = operation.getMaterials();

    Map<Material, Integer> materialList = new HashMap<>();

    for(Integer key : involvedMaterials.keySet()) {
      materialList.put(repository.getAllMaterials().get(key), involvedMaterials.get(key));
    }

    return materialList;
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


