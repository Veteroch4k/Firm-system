package course_project.firm_system.firm.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  private int id;

  private String description; // Краткое описание продукта

  private int drawing_id; // id Чертежа, соответствующего данному продукту

}
