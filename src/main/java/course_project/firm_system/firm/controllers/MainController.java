package course_project.firm_system.firm.controllers;


import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.services.RequestDAO;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

  @Autowired
  private RequestDAO requestDAO;

  @GetMapping("/")
  public ModelAndView index(ModelAndView modelAndView) throws IOException {
    modelAndView.addObject("title", "Главная страница");
    List<Material> list = requestDAO.getMaterials();
    modelAndView.setViewName("index");

    return modelAndView;
  }

}


