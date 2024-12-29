package course_project.firm_system.firm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import course_project.firm_system.firm.models.Drawing;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.models.factories.Factory;
import course_project.firm_system.firm.models.factories.FactoryTools;
import course_project.firm_system.firm.models.operations.OpTools;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.reports.FreeTools;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.repositories.toolsRepo.ToolsRepository;
import course_project.firm_system.firm.services.toolsService.ToolsDAO;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ToolsServiceTests {

  @Mock
  private ToolsRepository toolsRepository;

  @Mock
  private BaseRepository repository;


  @InjectMocks
  private ToolsDAO toolsDAO;

  private ToolType toolType1;
  private ToolType toolType2;

  @BeforeEach
  void setUp() {

    toolType1 = new ToolType();
    toolType1.setId(0);
    toolType1.setName("Hammer");

    toolType2 = new ToolType();
    toolType2.setId(1);
    toolType2.setName("Wrench");
  }



  @Test
  void testGetOperationTools() throws IOException {
    when(toolsRepository.getAllToolsTypes()).thenReturn(List.of(toolType1, toolType2));

    OpTools opT1 = new OpTools();
    opT1.setOperation_id(10);
    opT1.setToolType_id(0);
    opT1.setQuantity(3);

    OpTools opT2 = new OpTools();
    opT2.setOperation_id(10);
    opT2.setToolType_id(1);
    opT2.setQuantity(4);

    OpTools opT3 = new OpTools();
    opT3.setOperation_id(11);
    opT3.setToolType_id(0);
    opT3.setQuantity(99);

    when(toolsRepository.getOpTools()).thenReturn(List.of(opT1, opT2, opT3));

    Map<ToolType, Integer> result = toolsDAO.getOperationTools(10);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(3, (int) result.get(toolType1));
    assertEquals(4, (int) result.get(toolType2));
  }


  @Test
  void testGetFactoryTools() throws IOException {
    when(toolsRepository.getAllToolsTypes()).thenReturn(List.of(toolType1, toolType2));

    FactoryTools ft1 = new FactoryTools();
    ft1.setFactory_id(1);
    ft1.setToolType_id(toolType1.getId());
    ft1.setQuantity(5);

    FactoryTools ft2 = new FactoryTools();
    ft2.setFactory_id(1);
    ft2.setToolType_id(toolType2.getId());
    ft2.setQuantity(2);

    FactoryTools ft3 = new FactoryTools();
    ft3.setFactory_id(2);
    ft3.setToolType_id(toolType1.getId());
    ft3.setQuantity(50);

    when(toolsRepository.getFactoryTools()).thenReturn(List.of(ft1, ft2, ft3));

    Map<ToolType, Integer> factoryTools = toolsDAO.getFactoryTools(1);

    assertNotNull(factoryTools);
    assertEquals(2, factoryTools.size());
    assertEquals(5, factoryTools.get(toolType1));
    assertEquals(2, factoryTools.get(toolType2));
  }

  @Test
  void testGetRandomTool() throws IOException {
    // Имитируем наличие «свободных инструментов»
    FreeTools free1 = new FreeTools();
    free1.setTool_id(1);
    free1.setToolType_id(200);

    FreeTools free2 = new FreeTools();
    free2.setTool_id(2);
    free2.setToolType_id(200);

    when(toolsRepository.getFreeTools()).thenReturn(List.of(free1, free2));

    // Имитируем наличие «Tool»
    Tool t1 = new Tool();
    t1.setId(1);
    t1.setToolType_id(200);

    Tool t2 = new Tool();
    t2.setId(2);
    t2.setToolType_id(200);

    when(toolsRepository.getAllTools()).thenReturn(List.of(t1, t2));

    // Тестируем метод
    Tool randomTool = toolsDAO.getRandomTool(200);

    // Проверяем удаление из freeTools
    ArgumentCaptor<List<FreeTools>> captor = ArgumentCaptor.forClass(List.class);
    verify(toolsRepository).saveFreeTools(captor.capture());
    List<FreeTools> updatedList = captor.getValue();

    assertEquals(1, updatedList.size(), "Должен остаться только один инструмент в списке свободных");
    assertTrue(randomTool.getId() == 1 || randomTool.getId() == 2,
        "Мы могли получить либо инструмент с id=1, либо с id=2");


  }


  @Test
  void testGenerateNewTool() throws IOException {
    // Имитируем список всех инструментов
    when(toolsRepository.getAllTools()).thenReturn(new ArrayList<>());

    Tool newTool = toolsDAO.generateNewTool(toolType1.getId());
    assertEquals(toolType1.getId(), newTool.getToolType_id());
    // id = размер списка до + 0 = 0
    assertEquals(0, newTool.getId());
  }


  @Test
  void testGenerateRequiredTools() throws IOException {
    when(toolsRepository.getFreeTools()).thenReturn(new ArrayList<>()); // Пустой список

    // Имитируем сохранение
    doNothing().when(toolsRepository).saveTool(any(Tool.class));
    doNothing().when(toolsRepository).saveFreeTools(anyList());

    toolsDAO.generateRequiredTools(toolType1, 3);

    // Проверим, сколько раз вызывался saveTool
    verify(toolsRepository, times(3)).saveTool(any(Tool.class));

    // Проверим, что в saveFreeTools передавался список из 3 элементов
    ArgumentCaptor<List<FreeTools>> captor = ArgumentCaptor.forClass(List.class);
    verify(toolsRepository).saveFreeTools(captor.capture());
    assertEquals(3, captor.getValue().size());
  }


  @Test
  void testGetToolsWithTypes() throws IOException {
    Tool t1 = new Tool(); t1.setId(1); t1.setToolType_id(0);
    Tool t2 = new Tool(); t2.setId(2); t2.setToolType_id(1);

    when(toolsRepository.getAllTools()).thenReturn(List.of(t1, t2));
    when(toolsRepository.getAllToolsTypes()).thenReturn(List.of(toolType1, toolType2));

    Map<Tool, ToolType> map = toolsDAO.getToolsWithTypes();
    assertEquals(2, map.size());
    assertTrue(map.containsKey(t1));
    assertTrue(map.containsKey(t2));
    // Проверим соответствие
    assertEquals(toolType1, map.get(t1));
    assertEquals(toolType2, map.get(t2));
  }

  @Test
  void testGetProductsWithTools() throws IOException {
    // Продукты
    Product pr1 = new Product();
    pr1.setId(10);
    pr1.setDrawing_id(500);

    Product pr2 = new Product();
    pr2.setId(11);
    pr2.setDrawing_id(501);

    // Чертежи
    Drawing dr1 = new Drawing(); dr1.setId(500); dr1.setOperation_id(10);
    Drawing dr2 = new Drawing(); dr2.setId(501); dr2.setOperation_id(11);

    // OpTools (только для operation_id=10)
    OpTools opT1 = new OpTools(); opT1.setOperation_id(10); opT1.setToolType_id(0); opT1.setQuantity(2);
    OpTools opT2 = new OpTools(); opT2.setOperation_id(10); opT2.setToolType_id(1); opT2.setQuantity(3);

    // Для другой операции
    OpTools opT3 = new OpTools(); opT3.setOperation_id(11); opT3.setToolType_id(1); opT3.setQuantity(5);

    when(repository.getAllProducts()).thenReturn(List.of(pr1, pr2));
    when(repository.getAllDrawings()).thenReturn(List.of(dr1, dr2));
    when(toolsRepository.getOpTools()).thenReturn(List.of(opT1, opT2, opT3));
    when(toolsRepository.getAllToolsTypes()).thenReturn(List.of(toolType1, toolType2));

    Map<Product, Map<ToolType, Integer>> result = toolsDAO.getProductsWithTools();

    assertEquals(2, result.size());
    assertTrue(result.containsKey(pr1));
    assertTrue(result.containsKey(pr2));

    // Для pr1 (operation_id=10) должно быть 2 записи
    assertEquals(2, result.get(pr1).size());
    // Для pr2 (operation_id=11) — 1 запись
    assertEquals(1, result.get(pr2).size());
  }

  @Test
  void testGetUsedTools() throws IOException {
    // Все инструменты
    Tool t1 = new Tool(); t1.setId(1); t1.setToolType_id(200);
    Tool t2 = new Tool(); t2.setId(2); t2.setToolType_id(200);

    // Свободные инструменты
    FreeTools freeT1 = new FreeTools(); freeT1.setTool_id(1);
    freeT1.setToolType_id(200);

    when(toolsRepository.getAllTools()).thenReturn(List.of(t1, t2));
    when(toolsRepository.getFreeTools()).thenReturn(List.of(freeT1));

    List<Tool> usedTools = toolsDAO.getUsedTools();

    // Ожидаем, что инструмент с id=2 — это «used», так как он не в freeTools
    assertEquals(1, usedTools.size());
    assertEquals(2, usedTools.get(0).getId());
  }

}
