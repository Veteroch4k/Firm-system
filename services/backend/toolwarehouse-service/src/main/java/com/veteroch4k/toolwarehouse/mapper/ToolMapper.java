package com.veteroch4k.toolwarehouse.mapper;

import com.veteroch4k.toolwarehouse.dto.ToolResponse;
import com.veteroch4k.toolwarehouse.dto.ToolTypeResponse;
import com.veteroch4k.toolwarehouse.model.Tool;
import com.veteroch4k.toolwarehouse.model.ToolType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToolMapper {

    ToolTypeResponse toToolTypeResponse(ToolType toolType);

    ToolResponse toToolResponse(Tool tool);

}
