package com.veteroch4k.product.dto.product;

import com.veteroch4k.product.dto.drawing.DrawingResponse;

public record ProductResponse(


        Long id,

        String description,

        DrawingResponse drawing

) {

}
