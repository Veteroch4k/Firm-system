package course_project.firm_system.firm.models.consumables;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Tool implements Comparable<Tool> {

  int id;

  int toolType_id;

  @Override
  public int compareTo(Tool o) {
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
    Tool tool = (Tool) o;
    return id == tool.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
