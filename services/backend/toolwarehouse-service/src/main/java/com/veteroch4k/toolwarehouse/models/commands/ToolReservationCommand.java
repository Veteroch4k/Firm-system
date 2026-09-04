package com.veteroch4k.toolwarehouse.models.commands;

import java.util.List;

public record ToolReservationCommand(Long orderId, List<RequiredTools> tools, Long factoryId) {

}
