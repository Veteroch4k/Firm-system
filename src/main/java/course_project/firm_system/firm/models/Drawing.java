package course_project.firm_system.firm.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
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
}
