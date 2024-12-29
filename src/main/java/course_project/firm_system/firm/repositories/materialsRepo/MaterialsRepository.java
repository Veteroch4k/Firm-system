package course_project.firm_system.firm.repositories.materialsRepo;

import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.factories.FactoryMaterials;
import course_project.firm_system.firm.models.operations.OpMaterials;
import course_project.firm_system.firm.models.reports.MaterialsAccounting;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface MaterialsRepository {

  // Получение данных о материалах в цехах
  List<FactoryMaterials> getFactoryMaterials() throws IOException;

  // Получение материалов, необходимых для выполнения операций
  List<OpMaterials> getOpMaterials() throws IOException;

  // Сохранение отчетности
  void saveMaterialAccounting(List<MaterialsAccounting> list) throws IOException;

  // Получение отчетности о выданных складом материалах
  List<MaterialsAccounting> getMaterialAccountings() throws IOException;


  // Сохранение информации о материалах в цеху
  void saveFactoryMaterials(List<FactoryMaterials> list) throws IOException;


  // Список всех существующих материалов
  List<Material> getAllMaterials() throws IOException;


}
