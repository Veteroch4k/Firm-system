package course_project.firm_system.firm.controllers;

import course_project.firm_system.firm.Firm;
import course_project.firm_system.firm.models.DateRange;
import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.models.factories.Factory;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.reports.FreeTools;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
@RequestMapping("/content")
public class AsideController {

  @Autowired
  private BaseRepository baseRepository;

  @Autowired
  private Requests requests;


  private List<Order> filteredOrders; // Сохраняем отфильтрованные заказы


  @GetMapping("/products")
  public ModelAndView products(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Продукты");

    modelAndView.addObject("products", baseRepository.getAllProducts());

    modelAndView.setViewName("aside/products");

    return modelAndView;

  }

  @GetMapping("/order/{order_id}")
  public ModelAndView orderMaterials(@PathVariable int order_id, ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Продукты");


    Map<Material, Integer> res = requests.getOrderMaterials(baseRepository.getOrder(order_id));

    modelAndView.addObject("productMaterials", res);


    modelAndView.setViewName("aside/ordersMaterials");

    return modelAndView;

  }

  @GetMapping("/drawing/{id}")
  public ModelAndView drawing(@PathVariable int id, ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Чертежи");
    modelAndView.addObject("draw_id", id);

    Operation op = baseRepository.getCertainOp(
        baseRepository.getAllDrawings().stream().filter(x-> x.getId() == id).findFirst().get().getOperation_id());

    Map<ToolType, Integer> res = requests.getOperationTools(op.getId());

    modelAndView.addObject("draws", res);


    modelAndView.setViewName("aside/certainDrawing");

    return modelAndView;

  }

  @GetMapping("/factories")
  public ModelAndView factories(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Цеха");

    Map<Factory, Operation> factr = new HashMap<>();
    for(Factory factory : baseRepository.getAllFactories()) {
      factr.put(factory, requests.getFactoryOperation(factory.getId()));
    }

    modelAndView.addObject("factories", factr);

    modelAndView.setViewName("aside/factories");

    return modelAndView;

  }

  @GetMapping("/tools")
  public ModelAndView tools(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Инструменты");

    modelAndView.addObject("toolsTypes", requests.getToolsWithTypes());
    modelAndView.setViewName("aside/tools");

    return modelAndView;

  }

  @GetMapping("/operations")
  public ModelAndView operations(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Операции");

    modelAndView.addObject("ops", baseRepository.getAllOperations());
    modelAndView.addObject("toolsTypes", requests.getToolsWithTypes());
    modelAndView.setViewName("aside/operations");

    return modelAndView;

  }

  @GetMapping("/operation/{id}")
  public ModelAndView operation(@PathVariable int id, ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Операции");
    modelAndView.addObject("op", baseRepository.getCertainOp(id).getName());

    Map<Material, Integer> res = requests.getOperationMaterials(id);

    modelAndView.addObject("materials", res);
    modelAndView.setViewName("aside/certainOperation");

    return modelAndView;

  }

  @GetMapping("/orders")
  public ModelAndView orders(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Наряды");

    // Получаем отфильтрованные заказы, если они есть, иначе все
    modelAndView.addObject("orders", baseRepository.getAllOrders());


    modelAndView.setViewName("aside/orders");
    return modelAndView;

  }

  @PostMapping("/orders/dates")
  public ResponseEntity<String>  orderDates(@RequestBody DateRange range) throws IOException {

    LocalDate startDate = range.getStartDate();
    LocalDate endDate = range.getEndDate();

    // Фильтруем заказы по диапазону дат
    filteredOrders = requests.getDateOrders(startDate,endDate);

    return ResponseEntity.ok("Ответы успешно обработаны");
  }

  @GetMapping("/orders/date")
  public ModelAndView ordersDate(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Наряды по датам");

    // Получаем отфильтрованные заказы, если они есть, иначе все
    modelAndView.addObject("orders", filteredOrders);


    filteredOrders = null;

    modelAndView.setViewName("aside/ordersDate");
    return modelAndView;

  }


  @GetMapping("/usedTools")
  public ModelAndView usedTools(ModelAndView modelAndView) throws IOException {
    modelAndView.addObject("title", "Используемые инструменты");

    modelAndView.addObject("usedTools", requests.getUsedTools());

    modelAndView.addObject("toolTypes", requests.getToolsWithTypes());

    modelAndView.setViewName("aside/usedTools");

    return modelAndView;

  }

  /**/


  @GetMapping("/newTool")
  public ModelAndView addTool(ModelAndView modelAndView) throws IOException {
    modelAndView.addObject("title", "Добавление нового инструмента");

    modelAndView.addObject("toolTypes", baseRepository.getAllToolsTypes());

    modelAndView.setViewName("aside/adding/addNewTool");

    return modelAndView;
  }

  @PostMapping("create-tool")
  public ResponseEntity<String> toolCreating(@RequestBody Tool tool) throws IOException {

    tool.setId(baseRepository.getAllTools().size());

    baseRepository.saveTool(tool);

    // Помимо списка всех инструментов, мы должны добавить новый инструмент на склад
    List<FreeTools> freeTools = baseRepository.getFreeTools();

    FreeTools freeTool = new FreeTools();
    freeTool.setId(freeTools.size());
    freeTool.setTool_id(tool.getId());
    freeTool.setToolType_id(tool.getToolType_id());
    freeTool.setReceiveDate(LocalDate.now());


    freeTools.add(freeTool);

    baseRepository.saveFreeTools(freeTools);

    return ResponseEntity.ok("Ответы успешно обработаны");
  }

 /**/

  @GetMapping("/newOperation")
  public ModelAndView addOperation(ModelAndView modelAndView) throws IOException {
    modelAndView.addObject("title", "Добавление новой операции");

    modelAndView.addObject("toolTypes", baseRepository.getAllToolsTypes());

    modelAndView.setViewName("aside/adding/addNewTool");

    return modelAndView;

  }

  @PostMapping("create-operation")
  public ResponseEntity<String> toolCreating(@RequestBody Operation op) throws IOException {

    tool.setId(baseRepository.getAllTools().size());

    baseRepository.saveTool(tool);

    // Помимо списка всех инструментов, мы должны добавить новый инструмент на склад
    List<FreeTools> freeTools = baseRepository.getFreeTools();

    FreeTools freeTool = new FreeTools();
    freeTool.setId(freeTools.size());
    freeTool.setTool_id(tool.getId());
    freeTool.setToolType_id(tool.getToolType_id());
    freeTool.setReceiveDate(LocalDate.now());


    freeTools.add(freeTool);

    baseRepository.saveFreeTools(freeTools);

    return ResponseEntity.ok("Ответы успешно обработаны");
  }


}
