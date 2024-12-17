package course_project.firm_system.firm.models.operations;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import course_project.firm_system.firm.models.consumables.Tool;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OperationsTools {

  private int id;

  private Operation operation_id; // id операции

  private Tool tool_id; // id инструмента, требуемого для данной операции


}
