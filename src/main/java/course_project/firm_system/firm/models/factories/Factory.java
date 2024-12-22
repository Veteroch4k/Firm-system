package course_project.firm_system.firm.models.factories;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.List;
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
}
