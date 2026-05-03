package com.veteroch4k.factory_service.models.commands;

import com.veteroch4k.factory_service.models.OperationTools;
import java.util.List;

public record ToolReservationCommand(int orderId, List<RequiredTools> tools, int factoryId) {

}
