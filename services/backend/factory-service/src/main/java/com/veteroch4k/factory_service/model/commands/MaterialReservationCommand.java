package com.veteroch4k.factory_service.model.commands;

import com.veteroch4k.factory_service.model.RequiredMaterial;

import java.util.List;

public record MaterialReservationCommand(Long orderId, List<RequiredMaterial> materials, Long factoryId) {

}
