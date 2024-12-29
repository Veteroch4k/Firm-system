package course_project.firm_system.firm.services.toolsService;

import course_project.firm_system.firm.models.Drawing;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.models.factories.FactoryTools;
import course_project.firm_system.firm.models.operations.OpTools;
import course_project.firm_system.firm.models.reports.FreeTools;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.repositories.toolsRepo.ToolsRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ToolsDAO  implements ToolsRequests {

  @Autowired
  private ToolsRepository repository;

  @Autowired
  private BaseRepository baseRepository;


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
  public Map<ToolType, Integer> checkFactoryRequiredTools(int factory_id, int quantity) throws IOException {

    Map<ToolType, Integer> factoryTools = getFactoryTools(baseRepository.getFactoryOperation(factory_id).getId());

    // Получение кол-ва инструментов, необходимых для выполнения операции данной фабрикой
    Map<ToolType, Integer> requiredTools = getOperationTools(baseRepository.getFactoryOperation(factory_id).getId());


    Map<ToolType, Integer> missingToolTypes = new HashMap<>();

    // Для обновления таблицы материалов фабрики
    List<FactoryTools> factoryTools1 = repository.getFactoryTools();

    for(ToolType toolType : factoryTools.keySet()) {
      if(factoryTools.get(toolType) < requiredTools.get(toolType)*quantity) {
        missingToolTypes.put(toolType, requiredTools.get(toolType)*quantity - factoryTools.get(toolType));
        factoryTools1.stream()
            .filter(x -> x.getFactory_id() == factory_id)
            .filter(x -> x.getToolType_id() == toolType.getId())
            .findFirst()
            .ifPresent(x -> x.setQuantity((int) Math.ceil(requiredTools.get(toolType)*quantity)));



      }
      else {
        factoryTools1.stream()
            .filter(x -> x.getFactory_id() == factory_id)
            .filter(x -> x.getToolType_id() == toolType.getId())
            .findFirst()
            .ifPresent(x -> x.setQuantity(x.getQuantity() - (int) Math.ceil(requiredTools.get(toolType)*quantity)));

      }
    }

    repository.saveFactoryTools(factoryTools1);


    return missingToolTypes;

  }

  // Возвращает кол-во недостающих инструментов в инструментальной
  public void checkEnoughFreeTools(ToolType type, int quantity) throws IOException {
    List<FreeTools> tools = repository.getFreeTools().stream()
        .filter(x-> x.getToolType_id() == type.getId())
        .toList();

    if(tools.size() >= quantity) {
      return;
    }

    generateRequiredTools(type, quantity);
  }

  @Override
  public Tool getRandomTool(int toolType_id) throws IOException {
    List<FreeTools> freeTools = repository.getFreeTools().stream()
        .filter(x -> x.getToolType_id() == toolType_id)
        .toList();

    //Генерируем случайный индекс только если есть свободные инструменты

    int index = new Random().nextInt(freeTools.size());


    FreeTools selectedFreeTool = freeTools.get(index); // Получаем FreeTool по индексу

    // Получаем Tool по id из выбранного FreeTools
    Optional<Tool> optionalTool = repository.getAllTools().stream()
        .filter(x -> x.getId() == selectedFreeTool.getTool_id())
        .findFirst();


    // Проверяем, найден ли Tool по id
    Tool tool = optionalTool.orElseThrow(() -> new NoSuchElementException("Tool not found with ID: " + selectedFreeTool.getTool_id()));

    // Удаляем выбранный freeTool из репозитория.
    // Делаем копию, чтобы не удалять из списка, который может быть неизменяемым
    List<FreeTools> mutableFreeTools = new ArrayList<>(repository.getFreeTools());
    mutableFreeTools.remove(selectedFreeTool);
    repository.saveFreeTools(mutableFreeTools);


    return tool;
  }

  // Добавление в инструментальную новых инструментов
  @Override
  public void generateRequiredTools(ToolType type, int quantity) throws IOException {
    List<FreeTools> freeTools = repository.getFreeTools();

    for(int i = 0; i < quantity; i ++) {
      Tool tool = generateNewTool(type.getId());

      repository.saveTool(tool);

      FreeTools freeTools1 = new FreeTools();
      freeTools1.setToolType_id(type.getId());

      freeTools1.setId(freeTools.isEmpty() ? 0 : Collections.max(freeTools).getId() + 1);
      freeTools1.setTool_id(tool.getId());
      freeTools1.setReceiveDate(LocalDate.now());

      freeTools.add(freeTools1);
    }

    repository.saveFreeTools(freeTools);

  }

  // Генерация нового инструмента
  @Override
  public Tool generateNewTool(int toolType_id) throws IOException {
    Tool tool = new Tool();
    tool.setToolType_id(toolType_id);
    tool.setId(repository.getAllTools().size());
    return tool;
  }


  @Override
  public List<Tool> getUsedTools() throws IOException {
    List<FreeTools> freeTools = repository.getFreeTools();
    List<Tool> tools = repository.getAllTools();

    List<Integer> freeToolIds = freeTools.stream()
        .map(FreeTools::getTool_id)
        .toList();

    List<Tool> usedTools = tools.stream()
        .filter(tool -> !freeToolIds.contains(tool.getId()))
        .toList();

    return usedTools;

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
    List<Product> products = baseRepository.getAllProducts();
    List<Drawing> drawings = baseRepository.getAllDrawings();

    Map<Product, Map<ToolType, Integer>> res = new HashMap<>();

    for(Product prdct : products) {
      Map<ToolType, Integer> toolTypeList = getOperationTools(drawings.stream()
          .filter(x-> x.getId() == prdct.getDrawing_id()).findFirst().get().getOperation_id());
      res.put(prdct, toolTypeList);

    }

    return res;

  }


}
