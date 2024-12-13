package course_project.firm_system.firm.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class MainController {

  @GetMapping("")
  public ModelAndView index(ModelAndView modelAndView) {
    modelAndView.addObject("title", "Главная страница");

    modelAndView.setViewName("index");

    return modelAndView;
  }

}
