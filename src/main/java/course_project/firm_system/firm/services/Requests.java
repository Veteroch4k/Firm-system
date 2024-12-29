package course_project.firm_system.firm.services;

import course_project.firm_system.firm.models.Drawing;
import course_project.firm_system.firm.models.Order;
import course_project.firm_system.firm.models.Product;
import course_project.firm_system.firm.models.consumables.Material;
import course_project.firm_system.firm.models.reports.Employer;
import course_project.firm_system.firm.models.operations.Operation;
import course_project.firm_system.firm.models.consumables.Tool;
import course_project.firm_system.firm.models.consumables.ToolType;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface Requests {


  // Получение нарядов в заданном интервале дат
  List<Order> getDateOrders(LocalDate start, LocalDate end) throws IOException;


  LocalDate getOrderDeadLine(Order order) throws IOException;

  // Получение случайного рабочего
  Employer getRandomEmployer() throws IOException;





}
