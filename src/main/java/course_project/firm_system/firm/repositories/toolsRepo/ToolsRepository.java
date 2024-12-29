package course_project.firm_system.firm.repositories.toolsRepo;

import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.models.factories.FactoryTools;
import course_project.firm_system.firm.models.operations.OpTools;
import course_project.firm_system.firm.models.reports.FreeTools;
import course_project.firm_system.firm.models.reports.ToolAccounting;
import java.io.IOException;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ToolsRepository {


  // Получение данных об инструментах в цехах
  List<FactoryTools> getFactoryTools() throws IOException;


  // Сохранение информации об инструментах в цеху
  void saveFactoryTools(List<FactoryTools> list) throws IOException;



  // Получение инструментов, необходимых для выполнения операций
  List<OpTools> getOpTools() throws IOException;


  // Список всех существующих типов инструментов
  List<ToolType> getAllToolsTypes() throws IOException;

  // Список всех существующих экземпляров инструментов
  List<Tool> getAllTools() throws IOException;

  void saveFreeTools(List<FreeTools> freeTools) throws IOException;

  // Сохранение нового инструмента
  void saveTool(Tool tool) throws IOException;

  // Получение списка инструментов, находящихся в инструментальной
  List<FreeTools> getFreeTools() throws IOException;


  // Получение отчетности о выданных инструментальной инструментов
  List<ToolAccounting> getToolAccounting() throws IOException;



  void saveToolAccounting(List<ToolAccounting> list) throws IOException;


}
