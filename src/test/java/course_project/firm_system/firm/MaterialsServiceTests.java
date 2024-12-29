package course_project.firm_system.firm;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import course_project.firm_system.firm.models.Drawing;
import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.models.factories.Factory;
import course_project.firm_system.firm.models.factories.FactoryMaterials;
import course_project.firm_system.firm.models.operations.OpMaterials;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.repositories.materialsRepo.MaterialsRepository;
import course_project.firm_system.firm.services.RequestDAO;
import course_project.firm_system.firm.services.materialsService.MaterialsDAO;
import course_project.firm_system.firm.services.materialsService.MaterialsRequests;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MaterialsServiceTests {

  @Mock
  private MaterialsRepository materialsRepository;

  @Mock
  private BaseRepository repository;

  @InjectMocks
  private MaterialsDAO MaterialsDAO;

  private Factory factory;
  private Operation operation;
  private Material material1;
  private Material material2;

  @BeforeEach
  void setUp() {
    // Инициализация базовых «заглушек» данных
    factory = new Factory();
    factory.setId(1);
    factory.setOperation_id(10);

    operation = new Operation();
    operation.setId(10);
    operation.setDuration(8);

    material1 = new Material();
    material1.setId(100);
    material1.setName("Steel");

    material2 = new Material();
    material2.setId(101);
    material2.setName("Plastic");

  }

  @Test
  void testGetOperationMaterials() throws IOException {
    // Готовим список всех материалов
    when(materialsRepository.getAllMaterials()).thenReturn(List.of(material1, material2));

    // Готовим OpMaterials
    OpMaterials opMat1 = new OpMaterials();
    opMat1.setOperation_id(10);
    opMat1.setMaterial_id(100);
    opMat1.setQuantity(3);

    OpMaterials opMat2 = new OpMaterials();
    opMat2.setOperation_id(10);
    opMat2.setMaterial_id(101);
    opMat2.setQuantity(5);

    OpMaterials opMat3 = new OpMaterials();
    opMat3.setOperation_id(11);
    opMat3.setMaterial_id(100);
    opMat3.setQuantity(99);

    when(materialsRepository.getOpMaterials()).thenReturn(List.of(opMat1, opMat2, opMat3));

    // Вызываем метод
    Map<Material, Integer> result = MaterialsDAO.getOperationMaterials(10);

    // Проверяем
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(3, (int) result.get(material1));
    assertEquals(5, (int) result.get(material2));
  }

  @Test
  void testGetOrderMaterials() throws IOException {
    // Заказ
    Order order = new Order();
    order.setProduct_id(10);
    order.setProduct_quantity(3);

    // Продукт и чертёж
    Product product = new Product();
    product.setId(10);
    product.setDrawing_id(500);

    Drawing drawing = new Drawing();
    drawing.setId(500);
    drawing.setOperation_id(10);

    when(repository.getDrawingByProductId(order.getProduct_id())).thenReturn(drawing);
    when(repository.getAllDrawings()).thenReturn(List.of(drawing));

    // У операции (id=10) есть два материала
    OpMaterials om1 = new OpMaterials();
    om1.setOperation_id(10);
    om1.setMaterial_id(100);
    om1.setQuantity(2);

    OpMaterials om2 = new OpMaterials();
    om2.setOperation_id(10);
    om2.setMaterial_id(101);
    om2.setQuantity(4);

    when(materialsRepository.getOpMaterials()).thenReturn(List.of(om1, om2));
    when(materialsRepository.getAllMaterials()).thenReturn(List.of(material1, material2));

    Map<Material, Integer> result = MaterialsDAO.getOrderMaterials(order);

    // Проверяем:
    // требуется 2 шт. материала #100 на продукт, всего продуктов 3 => 6 шт.
    // требуется 4 шт. материала #101, всего продуктов 3 => 12 шт.
    assertEquals(6, (int)result.get(material1));
    assertEquals(12, (int)result.get(material2));
  }

  @Test
  void testGetFactoryMaterials() throws IOException {
    // Список всех материалов
    when(materialsRepository.getAllMaterials()).thenReturn(List.of(material1, material2));

    // Связки "фабрика-материалы" (FactoryMaterials)
    FactoryMaterials fm1 = new FactoryMaterials();
    fm1.setFactory_id(1);
    fm1.setMaterial_id(100);
    fm1.setQuantity(50);

    FactoryMaterials fm2 = new FactoryMaterials();
    fm2.setFactory_id(1);
    fm2.setMaterial_id(101);
    fm2.setQuantity(30);

    FactoryMaterials fm3 = new FactoryMaterials();
    fm3.setFactory_id(2); // это для другой фабрики
    fm3.setMaterial_id(100);
    fm3.setQuantity(10);

    when(materialsRepository.getFactoryMaterials()).thenReturn(List.of(fm1, fm2, fm3));

    Map<Material, Integer> factoryMaterials = MaterialsDAO.getFactoryMaterials(1);

    assertEquals(2, factoryMaterials.size(), "Должны храниться 2 материала в фабрике #1");
    assertTrue(factoryMaterials.containsKey(material1));
    assertTrue(factoryMaterials.containsKey(material2));
    assertEquals(50, (int)factoryMaterials.get(material1));
    assertEquals(30, (int)factoryMaterials.get(material2));
  }

  @Test
  void testCheckFactoryRequiredMaterials() throws IOException {
    // Мокаем материалы фабрики
    when(materialsRepository.getAllMaterials()).thenReturn(List.of(material1, material2));

    FactoryMaterials fm1 = new FactoryMaterials();
    fm1.setFactory_id(1);
    fm1.setMaterial_id(100);
    fm1.setQuantity(10);

    FactoryMaterials fm2 = new FactoryMaterials();
    fm2.setFactory_id(1);
    fm2.setMaterial_id(101);
    fm2.setQuantity(5);

    when(materialsRepository.getFactoryMaterials()).thenReturn(List.of(fm1, fm2));

    // Мокаем фабрику и операцию
    factory.setId(1);
    factory.setOperation_id(10);

    when(repository.getAllFactories()).thenReturn(List.of(factory));
    when(repository.getCertainOp(10)).thenReturn(operation);

    // Мокаем необходимые материалы операции (OpMaterials)
    OpMaterials opMat1 = new OpMaterials();
    opMat1.setOperation_id(10);
    opMat1.setMaterial_id(100);
    opMat1.setQuantity(4);

    OpMaterials opMat2 = new OpMaterials();
    opMat2.setOperation_id(10);
    opMat2.setMaterial_id(101);
    opMat2.setQuantity(2);

    when(materialsRepository.getOpMaterials()).thenReturn(List.of(opMat1, opMat2));

    // Настраиваем сохранение (просто без ошибок)
    doNothing().when(materialsRepository).saveFactoryMaterials(anyList());

    when(repository.getFactoryOperation(1)).thenReturn(operation);
    // Вызываем метод
    Map<Material, Integer> missing = MaterialsDAO.checkFactoryRequiredMaterials(1, 1);

    // Проверяем, что для Steel (id=100) 10 единиц уже есть, а нужно 4*1*2 = 8, значит хватает с излишком

    assertNotNull(missing);

    assertTrue(missing.isEmpty(), "Ничего не должно недоставать в данном тестовом сценарии");

    // Можно проверить, что saveFactoryMaterials был вызван:
    verify(materialsRepository, times(1)).saveFactoryMaterials(anyList());
  }


}
