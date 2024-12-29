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
import course_project.firm_system.firm.repositories.toolsRepo.ToolsRepository;
import course_project.firm_system.firm.services.Requests;
import course_project.firm_system.firm.services.materialsService.MaterialsRequests;
import course_project.firm_system.firm.services.toolsService.ToolsRequests;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
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
  private ToolsRequests toolsRequests;

  @Autowired
  private MaterialsRequests materialsRequests;

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

  @GetMapping("/drawing/{id}")
  public ModelAndView drawing(@PathVariable int id, ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Чертежи");
    modelAndView.addObject("draw_id", id);

    Operation op = baseRepository.getCertainOp(
        baseRepository.getAllDrawings().stream().filter(x-> x.getId() == id).findFirst().get().getOperation_id());

    Map<ToolType, Integer> res = toolsRequests.getOperationTools(op.getId());

    modelAndView.addObject("draws", res);


    modelAndView.setViewName("aside/certainDrawing");

    return modelAndView;

  }

  @GetMapping("/factories")
  public ModelAndView factories(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Цеха");

    Map<Factory, Operation> factr = new HashMap<>();
    for(Factory factory : baseRepository.getAllFactories()) {
      factr.put(factory, baseRepository.getFactoryOperation(factory.getId()));
    }

    modelAndView.addObject("factories", factr);

    modelAndView.setViewName("aside/factories");

    return modelAndView;

  }

  /****/

  @GetMapping("/tools")
  public ModelAndView tools(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Инструменты");

    modelAndView.addObject("toolsTypes", toolsRequests.getToolsWithTypes());
    modelAndView.setViewName("aside/tools");

    return modelAndView;

  }

  @GetMapping("/usedTools")
  public ModelAndView usedTools(ModelAndView modelAndView) throws IOException {
    modelAndView.addObject("title", "Используемые инструменты");

    modelAndView.addObject("usedTools", toolsRequests.getUsedTools());

    modelAndView.addObject("toolTypes", toolsRequests.getToolsWithTypes());

    modelAndView.setViewName("aside/usedTools");

    return modelAndView;

  }

  /****/

  @GetMapping("/operations")
  public ModelAndView operations(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Операции");

    modelAndView.addObject("ops", baseRepository.getAllOperations());
    modelAndView.addObject("toolsTypes", toolsRequests.getToolsWithTypes());
    modelAndView.setViewName("aside/operations");

    return modelAndView;

  }

  @GetMapping("/operation/{id}")
  public ModelAndView operation(@PathVariable int id, ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Операции");
    modelAndView.addObject("op", baseRepository.getCertainOp(id).getName());

    Map<Material, Integer> res = materialsRequests.getOperationMaterials(id);

    modelAndView.addObject("materials", res);
    modelAndView.setViewName("aside/certainOperation");

    return modelAndView;

  }

  /****/

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

  @GetMapping("/order/{order_id}")
  public ModelAndView orderMaterials(@PathVariable int order_id, ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Продукты");


    Map<Material, Integer> res = materialsRequests.getOrderMaterials(baseRepository.getOrder(order_id));

    modelAndView.addObject("productMaterials", res);


    modelAndView.setViewName("aside/ordersMaterials");

    return modelAndView;

  }


}
