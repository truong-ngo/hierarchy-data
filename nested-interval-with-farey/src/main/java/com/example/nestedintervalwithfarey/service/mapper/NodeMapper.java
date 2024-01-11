package com.example.nestedintervalwithfarey.service.mapper;

import com.example.nestedintervalwithfarey.domain.Node;
import com.example.nestedintervalwithfarey.service.dto.NodeDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface NodeMapper extends EntityMapper<Node, NodeDto> {

    @Mapping(source = "interval.left.num", target = "leftNum")
    @Mapping(source = "interval.left.den", target = "leftDen")
    Node toEntity(NodeDto dto);

    @Mapping(target = "interval.left.num", source = "leftNum")
    @Mapping(target = "interval.left.den", source = "leftDen")
    NodeDto toDto(Node entity);
}
