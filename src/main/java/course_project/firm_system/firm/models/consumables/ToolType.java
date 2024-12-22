package course_project.firm_system.firm.models.consumables;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ToolType implements Comparable<ToolType> {

  private int id;

  private String name;

  private String description;

  @Override
  public int compareTo(ToolType o) {
    return Integer.compare(this.getId(), o.getId());
  }
}
