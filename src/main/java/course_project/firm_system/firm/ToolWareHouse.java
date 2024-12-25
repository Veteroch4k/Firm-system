package course_project.firm_system.firm;

import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.models.consumables.reports.ToolAccounting;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public class ToolWareHouse {

  @Autowired
  private Requests requests;

  @Autowired
  private BaseRepository repository;

  public void giveSomeTools(int factory_id, Order order) throws IOException {

    Map<ToolType, Integer> neededToolTypes = requests.checkFactoryRequiredTools(factory_id, order.getProduct_quantity()); // Получаем нужные инструменты и их кол-во

    List<ToolAccounting> accounting = repository.getToolAccounting(); // Отчётность инструментов (какой цех какими инструментами располагает)

    /** т.к. checkFactoryRequiredTools() уже обновила кол-во инструментов, имеющихся в цеху
     * теперь стоит этому цеху присвоить отдельные экземпляры каждого инструмента
     */


    for(ToolType toolType : neededToolTypes.keySet()) {

      List<Integer> toolsList = new ArrayList<>();

      for(int i = 0; i < neededToolTypes.get(toolType); i++) {
        toolsList.add(requests.getRandomTool(toolType.getId()).getId());
      }

      ToolAccounting mat = new ToolAccounting();
      mat.setId(accounting.size());
      mat.setTools_id(toolsList);
      mat.setFactory_id(factory_id);
      mat.setOrder_id(order.getId());

      accounting.add(mat);
    }
    /** Теперь проблема в том, что фирма как-то должна иметь информацию ЕСТЬ В TOOLACCOUNTING ++
     * в каком цеху какие инструменты выделены + даты поступления на склад должны быть-> FreeTools стоит добавить ещё поле дату ++
     * +  что делать, если инструментов не хвататет допустим -> надо создать -> Создать функцию генерации инструментов +-Fixed
     * также инструменты может создавать пользователь - в таком случае они появятся как и в Tools, as well in FreeTools
     * После всего этого надо подумать над созданием наряда
     * И тут у меня есть ошибка -> у меня расчет кол-ва материалов и инструмента для 1 продукта, а их может быть 10 FIXED++
     * -> нецелесообразно вызывать по 10 раз одни и те же функции FIXED ++
     * -> переделать фукнкции checkRequiredTools(Materials) FIXED ++
     * Надо будет удалить Comparable везде, но у меня могут криво работать методы из-за того, что придется переопределить метод equals*/
    repository.saveToolAccounting(accounting);
  }


}
