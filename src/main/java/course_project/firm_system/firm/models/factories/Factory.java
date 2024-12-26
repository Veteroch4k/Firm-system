package course_project.firm_system.firm.models.factories;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Factory implements Comparable<Factory>{

  private int id;

  private String name;

  private int operation_id;


  @Override
  public int compareTo(Factory o) {
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
    Factory factory = (Factory) o;
    return id == factory.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
