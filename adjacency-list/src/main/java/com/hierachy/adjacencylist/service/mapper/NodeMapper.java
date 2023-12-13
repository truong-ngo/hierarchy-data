package com.hierachy.adjacencylist.service.mapper;

import com.hierachy.adjacencylist.domain.Node;
import com.hierachy.adjacencylist.service.dto.NodeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NodeMapper extends EntityMapper<Node, NodeDto> {
}
