package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.reports.Employer;
import course_project.firm_system.firm.repositories.BaseRepository;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RequestDAO implements Requests {

  @Autowired
  private BaseRepository repository;


  @Override
  public LocalDate getOrderDeadLine(Order order) throws IOException {

    LocalDate finish = order.getFinish_date(); // Вначале дата начала и конца совпадают

    int hours = 0;
    int drawing_id = repository.getDrawingByProductId(order.getProduct_id()).getId();

    int op_id = repository.getAllDrawings()
        .stream().filter(x->x.getId() == drawing_id).findFirst().get().getOperation_id();
    hours = repository.getAllOperations()
        .stream().filter(x->x.getId() == op_id).findFirst().get().getDuration();

    hours *= order.getProduct_quantity();

    int days = hours % 24;

    finish.plusDays(days);

    return finish;

  }

  @Override
  public List<Order> getDateOrders(LocalDate start, LocalDate end)  throws IOException {
    List<Order> orders = repository.getAllOrders();

    return orders.stream()
        .filter(x-> ((start.isBefore(x.getOrder_date()) || start.isEqual(x.getOrder_date()))
            && x.getFinish_date().isBefore(end) || x.getFinish_date().isEqual(end) ) ).toList();

  }


  @Override
  public Employer getRandomEmployer() throws IOException {
    Random r = new Random();

    List<Employer> employers = repository.getAllEmployers();

    return employers.get(r.nextInt(employers.size()));

  }



}


