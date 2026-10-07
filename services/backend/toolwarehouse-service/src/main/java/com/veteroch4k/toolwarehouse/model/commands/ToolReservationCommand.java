package com.veteroch4k.toolwarehouse.model.commands;

import java.util.List;

public record ToolReservationCommand(Long orderId, List<RequiredTools> tools, Long factoryId) {

}
