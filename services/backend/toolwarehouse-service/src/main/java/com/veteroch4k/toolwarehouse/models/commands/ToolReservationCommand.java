package com.veteroch4k.toolwarehouse.models.commands;

import java.util.List;

public record ToolReservationCommand(int orderId, List<RequiredTools> tools, int factoryId) {

}
