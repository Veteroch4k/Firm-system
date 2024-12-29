package course_project.firm_system.firm.controllers;

import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.reports.FreeTools;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/adding")
public class AddingController {


  @Autowired
  private BaseRepository baseRepository;

  @Autowired
  private Requests requests;



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
    freeTool.setId(Collections.max(freeTools).getId() + 1);
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

    modelAndView.setViewName("aside/adding/addNewOperation");

    return modelAndView;

  }

  @PostMapping("create-operation")
  public ResponseEntity<String> toolCreating(@RequestBody Operation op) throws IOException {

    op.setId(baseRepository.getAllOperations().size());

    baseRepository.saveOperation(op);

    return ResponseEntity.ok("Ответы успешно обработаны");
  }

}
