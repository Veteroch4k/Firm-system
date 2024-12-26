package course_project.firm_system.firm.models;


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
public class Drawing implements Comparable<Drawing>{

  private int id;

  private int operation_id; // Выполняемая операция по данному чертежу

  private int factory_id; // Цех, где может выполниться данный чертёж

  @Override
  public int compareTo(Drawing o) {
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
    Drawing drawing = (Drawing) o;
    return id == drawing.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
