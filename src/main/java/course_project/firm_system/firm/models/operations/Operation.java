package course_project.firm_system.firm.models.operations;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Operation {

  private int id;

  private String name;

  private String description;

  private int duration; // Исчисляется часами

  private int factory_id;

  private Map<Integer, Integer> materials;

  private Map<Integer, Integer> tools;

}
