package course_project.firm_system.firm.controllers;

import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.ToolType;
import course_project.firm_system.firm.models.factories.Factory;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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



  /*
    modelAndView.addObject("title", "Операции");
    List<Map<Material,Integer>> op = new ArrayList<>();
    for(int i = 0; i < baseRepository.getAllOperations().size(); i ++) {
       op.add(requests.getOperationMaterials(i));
    }

    modelAndView.addObject("ops", op);
    modelAndView.setViewName("aside/tools");

    return modelAndView;

   */

}
