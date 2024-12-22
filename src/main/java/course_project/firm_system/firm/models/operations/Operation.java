package course_project.firm_system.firm.models.operations;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Operation implements Comparable<Operation> {

  private int id;

  private String name;

  private String description;

  private int duration; // Исчисляется часами

  private int factory_id;


  @Override
  public int compareTo(Operation o) {
    return Integer.compare(this.getId(), o.getId());
  }
}
