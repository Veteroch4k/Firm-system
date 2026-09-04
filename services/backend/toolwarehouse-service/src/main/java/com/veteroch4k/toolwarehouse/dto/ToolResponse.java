package com.veteroch4k.toolwarehouse.dto;

import com.veteroch4k.toolwarehouse.models.ToolType;

public record ToolResponse(
        Long id,
        ToolTypeResponse toolTypeResponse
) {
}
