package course_project.firm_system.firm.controllers;


import course_project.firm_system.firm.models.repositories.BaseRepository;
import course_project.firm_system.firm.services.RequestDAO;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

  @Autowired
  private Requests requests;


  @GetMapping("/")
  public ModelAndView index(ModelAndView modelAndView) throws IOException {
    modelAndView.addObject("title", "Главная страница");

    modelAndView.setViewName("index");

    return modelAndView;
  }

}


