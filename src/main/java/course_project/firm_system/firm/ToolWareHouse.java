package course_project.firm_system.firm;

import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.models.reports.ToolAccounting;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.repositories.toolsRepo.ToolsRepository;
import course_project.firm_system.firm.services.Requests;
import course_project.firm_system.firm.services.toolsService.ToolsRequests;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ToolWareHouse {


  @Autowired
  private ToolsRequests toolsRequests;

  @Autowired
  private ToolsRepository toolsRepository;



  public void giveSomeTools(int factory_id, Order order) throws IOException {

    Map<ToolType, Integer> neededToolTypes = toolsRequests.checkFactoryRequiredTools(factory_id, order.getProduct_quantity()); // Получаем нужные инструменты и их кол-во

    List<ToolAccounting> accounting = toolsRepository.getToolAccounting(); // Отчётность инструментов (какой цех какими инструментами располагает)


    for(ToolType toolType : neededToolTypes.keySet()) {

      List<Integer> toolsList = new ArrayList<>();
      toolsRequests.checkEnoughFreeTools(toolType, neededToolTypes.get(toolType));

      for(int i = 0; i < neededToolTypes.get(toolType); i++) {
        toolsList.add(toolsRequests.getRandomTool(toolType.getId()).getId());
      }

      ToolAccounting mat = new ToolAccounting();
      mat.setId(accounting.size());
      mat.setTools_id(toolsList);
      mat.setFactory_id(factory_id);
      mat.setOrder_id(order.getId());
      mat.setToolType_id(toolType.getId());

      accounting.add(mat);
    }

    toolsRepository.saveToolAccounting(accounting);
  }
}
