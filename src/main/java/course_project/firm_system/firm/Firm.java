package course_project.firm_system.firm;

import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.reports.OrdersAccounting;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class Firm {

  @Autowired
  private ToolWareHouse toolWareHouse;

  @Autowired
  private Warehouse warehouse;


  @Autowired
  private  BaseRepository baseRepository;


  @Autowired
  private  Requests requests;

  public void createOrder(Order order) throws IOException {

    int factory_id = requests.getDrawingByProductId(order.getProduct_id()).getFactory_id();

    warehouse.giveSomeMaterials(factory_id,order);

    toolWareHouse.giveSomeTools(factory_id, order);

    OrdersAccounting accounting = new OrdersAccounting();
    accounting.setFactory_id(factory_id);
    accounting.setId(Collections.max(baseRepository.getOrderAccounting()).getId() + 1);
    accounting.setProduct_id(order.getProduct_id());
    accounting.setQuantity(order.getProduct_quantity());

    List<OrdersAccounting> accountings = baseRepository.getOrderAccounting();

    accountings.add(accounting);

    baseRepository.saveOrderAccounting(accountings);

  }

}
