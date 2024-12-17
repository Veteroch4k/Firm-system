package course_project.firm_system.firm.models.consumables;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Warehouse {

  private int id;

  private int material_id;

  private int quantity;

}
