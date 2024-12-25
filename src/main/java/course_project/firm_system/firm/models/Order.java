package course_project.firm_system.firm.models;


import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order implements Comparable<Order>{

  private int id;

  private int product_id;

  private int product_quantity;

  private LocalDate order_date;

  private LocalDate finish_date;


  @Override
  public int compareTo(Order o) {
    return Integer.compare(this.getId(), o.getId());
  }
}
