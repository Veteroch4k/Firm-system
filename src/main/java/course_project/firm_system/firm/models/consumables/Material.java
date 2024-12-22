package course_project.firm_system.firm.models.consumables;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import course_project.firm_system.firm.models.Order;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Material implements Comparable<Material>{

  private int id;

  private String name;

  @Override
  public int compareTo(Material o) {
    return Integer.compare(this.getId(), o.getId());
  }
}
