package course_project.firm_system.firm.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Operation {

  private int id;

  private int duration; // Исчисляется днями

  private Object matrrials;

  private Object Tools;

}
