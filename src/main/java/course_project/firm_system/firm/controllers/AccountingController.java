package course_project.firm_system.firm.controllers;

import course_project.firm_system.firm.models.reports.Employer;
import course_project.firm_system.firm.models.reports.MaterialsAccounting;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.repositories.materialsRepo.MaterialsRepository;
import course_project.firm_system.firm.repositories.toolsRepo.ToolsRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

  @Autowired
  private MaterialsRepository materialsRepository;

  @Autowired
  private ToolsRepository toolsRepository;


  @GetMapping("/wareHouse")
  public ModelAndView wareHouse(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Отчётность склада");


    modelAndView.addObject("accountings", materialsRepository.getMaterialAccountings());

    Map<Integer, Employer> employerMap = new HashMap<>();
    List<Employer> employers = repository.getAllEmployers();

    for( int i = 0; i < employers.size(); i ++) {
      employerMap.put(i, employers.get(i));
    }
    modelAndView.addObject("employers", employerMap);


    modelAndView.setViewName("aside/reports/wareHouseAccountings");

    return modelAndView;

  }

  @GetMapping("/toolWareHouse")
  public ModelAndView toolWareHouse(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Отчётность Ц. И.");

    modelAndView.addObject("accountings", toolsRepository.getToolAccounting());


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

    modelAndView.addObject("accountings", materialsRepository.getAllMaterials());

    modelAndView.setViewName("aside/reports/materials");

    return modelAndView;

  }

}
