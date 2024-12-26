package course_project.firm_system.firm.models;


import java.time.LocalDate;
import java.util.Objects;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Order order = (Order) o;
    return id == order.id;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }
}
