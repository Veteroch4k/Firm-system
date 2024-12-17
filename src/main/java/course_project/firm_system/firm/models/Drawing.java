package course_project.firm_system.firm.models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import course_project.firm_system.firm.models.operations.Operation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Drawing {

  private int id;

  private Operation operation_id; // Выполняемая операция по данному чертежу

  private Factory factory_id; // Цех, где может выполниться данный чертёж


}
