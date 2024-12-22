package course_project.firm_system.firm;

import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.reports.MaterialsAccounting;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public class Warehouse {

  @Autowired
  private Requests requests;

  public void giveSomeMaterials(int factory_id, Operation op, int quantity) throws IOException {

    Map<Material, Integer> neededMaterials = requests.checkFactoryRequiredMaterials(factory_id);

    List<MaterialsAccounting> accounting = requests;

    for(Material material : neededMaterials.keySet()) {
      accounting.
    }


  }

}
