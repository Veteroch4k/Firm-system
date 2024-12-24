package course_project.firm_system.firm.models.consumables.reports;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import course_project.firm_system.firm.models.consumables.Tool;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ToolAccounting implements Comparable<ToolAccounting> {

  private int id;

  private List<Integer> tools_id;

  private int toolType_id;

  private int factory_id;

  private int order_id;

  @Override
  public int compareTo(ToolAccounting o) {
    return Integer.compare(this.getId(), o.getId());
  }
}
