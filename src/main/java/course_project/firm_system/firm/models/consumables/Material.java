package course_project.firm_system.firm.models.consumables;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Material {

  private int id;

  private String name;

  private int quantity; // Количество материала на складе

}
