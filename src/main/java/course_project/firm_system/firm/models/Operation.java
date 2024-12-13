package course_project.firm_system.firm.models;


import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.Tool;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Operation {

  private int id;

  private String description;

  private int duration; // Исчисляется днями

  private Map<Material, Integer> materials;

  private Map<Tool, Integer>  Tools;

}
