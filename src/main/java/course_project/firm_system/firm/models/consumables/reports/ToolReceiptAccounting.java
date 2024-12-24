package course_project.firm_system.firm.models.consumables.reports;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ToolReceiptAccounting implements Comparable<ToolReceiptAccounting>{

  private int id;

  private int tool_id;

  private LocalDate receive_date;

  @Override
  public int compareTo(ToolReceiptAccounting o) {
    return Integer.compare(this.getId(), o.getId());
  }
}
