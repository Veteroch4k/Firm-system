package com.veteroch4k.toolwarehouse.mappers;

import com.veteroch4k.toolwarehouse.dto.ToolResponse;
import com.veteroch4k.toolwarehouse.dto.ToolTypeResponse;
import com.veteroch4k.toolwarehouse.models.Tool;
import com.veteroch4k.toolwarehouse.models.ToolType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToolMapper {

    ToolTypeResponse toToolTypeResponse(ToolType toolType);

    ToolResponse toToolResponse(Tool tool);

}
