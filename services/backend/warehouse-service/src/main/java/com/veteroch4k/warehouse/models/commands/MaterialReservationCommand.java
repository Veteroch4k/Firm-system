package com.veteroch4k.warehouse.models.commands;


import java.util.List;

public record MaterialReservationCommand(Long orderId, List<RequiredMaterial> materials, Long factoryId) {

}
