package course_project.firm_system.firm.services.materialsService;

import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.consumables.Material;
import java.io.IOException;
import java.util.Map;

public interface MaterialsRequests {

  // Получение необходимых материалов для данной операции
  Map<Material,Integer> getOperationMaterials(int operation_id) throws IOException;


  // Получение материалов, необходимых для выполнения данного наряда
  Map<Material, Integer> getOrderMaterials(Order order) throws IOException;

  // Получение материалов, хранящихся в цеху
  Map<Material, Integer> getFactoryMaterials(int factory_id) throws IOException;


  // Получение материалов, необходимых цеху для выполнения операции (создания 1 продукта)
  Map<Material, Integer> checkFactoryRequiredMaterials(int factory_id, int quantity) throws IOException; // возвращает, сколько недостает материалов


}
