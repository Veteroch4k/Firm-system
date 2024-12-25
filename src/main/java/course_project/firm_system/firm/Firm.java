package course_project.firm_system.firm;

import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.reports.OrdersAccounting;
import course_project.firm_system.firm.repositories.BaseRepository;
import course_project.firm_system.firm.services.Requests;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class Firm {

  private static ToolWareHouse toolWareHouse = new ToolWareHouse();

  private static Warehouse warehouse = new Warehouse();


  @Autowired
  private static BaseRepository baseRepository;


  @Autowired
  private static Requests requests;

  public static void createOrder(Order order) throws IOException {

    int factory_id = requests.getDrawingByProductId(order.getProduct_id()).getFactory_id();

    warehouse.giveSomeMaterials(factory_id,order);

    toolWareHouse.giveSomeTools(factory_id, order);

    OrdersAccounting accounting = new OrdersAccounting();
    accounting.setFactory_id(factory_id);
    accounting.setId(baseRepository.getOrderAccounting().size());
    accounting.setProduct_id(order.getProduct_id());
    accounting.setQuantity(order.getProduct_quantity());

    List<OrdersAccounting> accountings = baseRepository.getOrderAccounting();

    accountings.add(accounting);

    baseRepository.saveOrderAccounting(accountings);

  }

}
