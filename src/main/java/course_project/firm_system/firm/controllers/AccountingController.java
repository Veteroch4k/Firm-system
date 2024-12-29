package course_project.firm_system.firm.controllers;

import course_project.firm_system.firm.models.reports.MaterialsAccounting;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/accountings")
public class AccountingController {


  @Autowired
  private BaseRepository repository;


  @GetMapping("/wareHouse")
  public ModelAndView wareHouse(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Отчётность склада");


    modelAndView.addObject("accountings", repository.getMaterialAccountings());


    modelAndView.setViewName("aside/reports/wareHouseAccountings");

    return modelAndView;

  }

  @GetMapping("/toolWareHouse")
  public ModelAndView toolWareHouse(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Отчётность Ц. И.");

    modelAndView.addObject("accountings", repository.getToolAccounting());

    modelAndView.setViewName("aside/reports/toolWareHouseAccountings");

    return modelAndView;
  }

  @GetMapping("/orders")
  public ModelAndView ordersAcc(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Отчётность фирмы");

    modelAndView.addObject("accountings", repository.getOrderAccounting());

    modelAndView.setViewName("aside/reports/ordersAccounting");

    return modelAndView;

  }

  @GetMapping("/materials")
  public ModelAndView materials(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Материалы");

    modelAndView.addObject("accountings", repository.getAllMaterials());

    modelAndView.setViewName("aside/reports/materials");

    return modelAndView;

  }

}
