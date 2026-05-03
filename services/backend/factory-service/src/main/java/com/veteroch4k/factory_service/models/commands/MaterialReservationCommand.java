package com.veteroch4k.factory_service.models.commands;

import java.util.List;

public record MaterialReservationCommand(long orderId, List<RequiredMaterial> materials) {

}
