package com.veteroch4k.factory_service.models.commands;

import com.veteroch4k.factory_service.models.RequiredMaterial;

import java.util.List;

public record MaterialReservationCommand(Long orderId, List<RequiredMaterial> materials, Long factoryId) {

}
