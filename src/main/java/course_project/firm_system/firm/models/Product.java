package course_project.firm_system.firm.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {

  @JsonCreator
  public Product(
      @JsonProperty("id") int id,
      @JsonProperty("description") String description,
      @JsonProperty("drawing_id") int drawing_id) {

  }

  private int id;

  private String description; // Краткое описание продукта

  private int drawing_id; // id Чертежа, соответствующего данному продукту

}
