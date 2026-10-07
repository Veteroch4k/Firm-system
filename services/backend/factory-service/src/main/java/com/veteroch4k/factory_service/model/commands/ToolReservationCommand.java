package com.veteroch4k.factory_service.model.commands;

import com.veteroch4k.factory_service.model.RequiredTools;

import java.util.List;

public record ToolReservationCommand(Long orderId, List<RequiredTools> tools, Long factoryId) {

}
