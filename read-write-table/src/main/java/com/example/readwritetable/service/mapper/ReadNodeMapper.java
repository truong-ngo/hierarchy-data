package com.example.readwritetable.service.mapper;

import com.example.readwritetable.domain.ReadNode;
import com.example.readwritetable.service.dto.NodeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ReadNodeMapper extends EntityMapper<ReadNode, NodeDto> {
}
