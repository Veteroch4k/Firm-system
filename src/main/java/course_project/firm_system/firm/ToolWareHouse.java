package course_project.firm_system.firm;

import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.consumables.reports.MaterialsAccounting;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@NoArgsConstructor
public class ToolWareHouse {

  @Autowired
  private Requests requests;

  @Autowired
  private BaseRepository repository;

  public void giveSomeTools(int factory_id, Order order) throws IOException {

    Map<Material, Integer> neededMaterials = requests.checkFactoryRequiredMaterials(factory_id);

    List<MaterialsAccounting> accounting = repository.getMaterialAccountings();

    for(Material material : neededMaterials.keySet()) {
      MaterialsAccounting mat = new MaterialsAccounting();
      mat.setId(Collections.max(accounting).getId() + 1);
      mat.setMaterial_id(material.getId());
      mat.setQuantity(neededMaterials.get(material));
      mat.setProduct_id(order.getProduct_id());
      mat.setEmployer_id(requests.getRandomEmployer().getId()); // Случайный работник
      mat.setDate(order.getOrder_date());
      mat.setOrder_id(order.getId());

      accounting.add(mat);
    }

    repository.saveMaterialAccounting(accounting);
  }

}
