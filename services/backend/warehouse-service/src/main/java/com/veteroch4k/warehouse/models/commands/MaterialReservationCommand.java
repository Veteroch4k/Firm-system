package com.veteroch4k.warehouse.models.commands;


import java.util.List;

public record MaterialReservationCommand(int orderId, List<RequiredMaterial> materials ) {

}
