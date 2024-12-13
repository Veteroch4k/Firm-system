package course_project.firm_system.firm.models;


import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Operation {

  private int id;

  private int duration; // Исчисляется днями

  private List<Object> materials;

  private List<Object>  Tools;

}
