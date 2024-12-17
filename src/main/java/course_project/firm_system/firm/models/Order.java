package course_project.firm_system.firm.models;


import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Order {

  private int product_id;

  private Date order_date;

  private int order_duration;

  private int product_quantity;

}
