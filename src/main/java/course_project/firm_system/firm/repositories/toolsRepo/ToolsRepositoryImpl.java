package course_project.firm_system.firm.repositories.toolsRepo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.models.factories.FactoryTools;
import course_project.firm_system.firm.models.operations.OpTools;
import course_project.firm_system.firm.models.reports.FreeTools;
import course_project.firm_system.firm.models.reports.ToolAccounting;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class ToolsRepositoryImpl implements ToolsRepository {

  private final ObjectMapper objectMapper;


  private static final File factoryToolsFilePath = new File("src/main/resources/db/factory/fctrTools.json");
  private static final File opToolsFilePath = new File( "src/main/resources/db/op/opTools.json");
  private static final File toolTypesFilePath = new File("src/main/resources/db/toolTypes.json");
  private static final File toolFilePath = new File("src/main/resources/db/tools.json");
  private static final File freeToolFilePath = new File("src/main/resources/db/reports/freeTools.json");
  private static final File toolAccountingsFilePath = new File( "src/main/resources/db/reports/toolAccountings.json");

  public ToolsRepositoryImpl(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public List<FactoryTools> getFactoryTools() throws IOException {
    if (factoryToolsFilePath.exists()) {
      return objectMapper.readValue(factoryToolsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }


  @Override
  public List<ToolAccounting> getToolAccounting() throws IOException {
    if (toolAccountingsFilePath.exists()) {
      return objectMapper.readValue(toolAccountingsFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }


  @Override
  public void saveToolAccounting(List<ToolAccounting> list) throws IOException {
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(toolAccountingsFilePath, list);

  }


  @Override
  public List<ToolType> getAllToolsTypes() throws IOException {

    if (toolTypesFilePath.exists()) {
      return objectMapper.readValue(toolTypesFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }

  @Override
  public List<Tool> getAllTools() throws IOException {
    if (toolFilePath.exists()) {
      return objectMapper.readValue(toolFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }


  @Override
  public void saveTool(Tool tool) throws IOException {

    List<Tool> tools = getAllTools();

    tools.add(tool);

    objectMapper.writerWithDefaultPrettyPrinter().writeValue(toolFilePath, tools);

  }

  @Override
  public void saveFactoryTools(List<FactoryTools> list) throws IOException {
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(factoryToolsFilePath, list);
  }

  @Override
  public List<FreeTools> getFreeTools() throws IOException {
    if (freeToolFilePath.exists()) {
      return objectMapper.readValue(freeToolFilePath, new TypeReference<>(){});
    }

    return new ArrayList<>();
  }


  @Override
  public List<OpTools> getOpTools() throws IOException {
    if (opToolsFilePath.exists()) {
      return objectMapper.readValue(opToolsFilePath, new TypeReference<>(){});
    }
    return new ArrayList<>();
  }


  @Override
  public void saveFreeTools(List<FreeTools> freeTools) throws IOException {
    objectMapper.writerWithDefaultPrettyPrinter().writeValue(freeToolFilePath, freeTools);

  }




}
