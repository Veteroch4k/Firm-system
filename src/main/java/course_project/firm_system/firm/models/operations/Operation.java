package course_project.firm_system.firm.models.operations;


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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Operation operation = (Operation) o;
    return id == operation.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
