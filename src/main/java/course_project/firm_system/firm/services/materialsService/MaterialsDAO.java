package course_project.firm_system.firm.services.materialsService;

import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.factories.FactoryMaterials;
import course_project.firm_system.firm.models.operations.OpMaterials;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.repositories.materialsRepo.MaterialsRepository;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MaterialsDAO implements MaterialsRequests {


  @Autowired
  private MaterialsRepository repository;

  @Autowired
  private BaseRepository baseRepository;


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
  public Map<Material, Integer> getOrderMaterials(Order order) throws IOException {
    int drawing_id = baseRepository.getDrawingByProductId(order.getProduct_id()).getId();
    ; // Получение чертежа продукта через id продукта в наряде

    Map<Material, Integer> map = getOperationMaterials(baseRepository.getAllDrawings()
        .stream().filter(x-> x.getId() == drawing_id).findFirst().get().getOperation_id());

    map.replaceAll((m, v) -> map.get(m) * order.getProduct_quantity()); // Умножаем каждое значение на кол-во продуктов

    return map;

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


  // Проверка недостающих материалов и их выдача фабрике
  @Override
  public Map<Material, Integer> checkFactoryRequiredMaterials(int factory_id, int quantity) throws IOException {

    // Получение материалов, хранящихся в цеху данный момент
    Map<Material, Integer> factoryMaterials = getFactoryMaterials(factory_id);

    // Получение кол-ва материалов, необходимых для выполнения операции данной фабрикой
    Map<Material, Integer> requiredMaterials = getOperationMaterials(baseRepository.getFactoryOperation(factory_id).getId());


    Map<Material, Integer> missingMaterials = new HashMap<>();

    // Для обновления таблицы материалов фабрики
    List<FactoryMaterials> factoryMaterials1 = repository.getFactoryMaterials();


    for(Material material : factoryMaterials.keySet()) {
      if(factoryMaterials.get(material) < (requiredMaterials.get(material)*quantity)*2) { // Домножаю на два, т.к. даю материалы с излишком, на 2 продукта
        missingMaterials.put(material, (requiredMaterials.get(material)*quantity)*2 - factoryMaterials.get(material));
        factoryMaterials1.stream()
            .filter(x -> x.getFactory_id() == factory_id)
            .filter(x -> x.getMaterial_id() == material.getId())
            .findFirst()
            .ifPresent(x -> x.setQuantity((int) Math.ceil((requiredMaterials.get(material)*quantity)*0.75))); // Один из 4 товаров всегда будет бракованный -> отсюда и рассчет

      }
      else {
        factoryMaterials1.stream()
            .filter(x -> x.getFactory_id() == factory_id)
            .filter(x -> x.getMaterial_id() == material.getId())
            .findFirst()
            .ifPresent(x -> x.setQuantity(x.getQuantity() - (int) Math.ceil((requiredMaterials.get(material)*quantity)*1.25)));

      }
    }

    repository.saveFactoryMaterials(factoryMaterials1);


    return missingMaterials;

  }


}
