package course_project.firm_system.firm.models.consumables.reports;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MaterialsAccounting implements Comparable<MaterialsAccounting> {

  private int id;

  private int material_id;

  private int quantity;

  private int product_id;

  private int employer_id;

  private Date date;

  private int order_id;


  @Override
  public int compareTo(MaterialsAccounting o) {
    return Integer.compare(this.getId(), o.getId());
  }
}
