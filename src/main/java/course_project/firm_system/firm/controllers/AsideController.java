package course_project.firm_system.firm.controllers;

import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/content")
public class AsideController {

  @Autowired
  private BaseRepository baseRepository;


  @Autowired
  private Requests requests;

  @GetMapping("/products")
  public ModelAndView products(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Продукты");

    modelAndView.addObject("products", baseRepository.getAllProducts());

    modelAndView.setViewName("aside/products");

    return modelAndView;

  }

  @GetMapping("/factories")
  public ModelAndView factories(ModelAndView modelAndView) throws IOException {

    modelAndView.addObject("title", "Цеха");

    modelAndView.addObject("factories", baseRepository.getAllFactories());

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


}
