package course_project.firm_system.firm.models.operations;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import course_project.firm_system.firm.models.consumables.Material;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class OperationMaterial {

  private int id;

  private Operation operation_id; // id операции

  private Material material_id; // id материала, требуемого для данной операции

  private int quantity; // количество требуемого материала

}
