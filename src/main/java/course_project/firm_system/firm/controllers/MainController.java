package course_project.firm_system.firm.controllers;


import course_project.firm_system.firm.Firm;
import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.Warehouse;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

  @Autowired
  private BaseRepository baseRepository;

  @Autowired
  private Requests requests;


  @GetMapping("")
  public ModelAndView index(ModelAndView modelAndView) throws IOException {
    modelAndView.addObject("title", "Главная страница");
    modelAndView.addObject("products", baseRepository.getAllProducts());

    modelAndView.setViewName("index");

    return modelAndView;
  }

  @PostMapping("create-order")
  public ResponseEntity<String> gpuAns(@RequestBody Order order) throws IOException {
    System.out.println("Получили заказ:" + order);

    order.setFinish_date(requests.getOrderDeadLine(order));
    order.setId(baseRepository.getAllOrders().size());

    Firm.createOrder(order);

    return ResponseEntity.ok("Ответы успешно обработаны");
  }
}


