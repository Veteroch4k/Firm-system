package course_project.firm_system.firm.models.consumables;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ToolType toolType = (ToolType) o;
    return id == toolType.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
