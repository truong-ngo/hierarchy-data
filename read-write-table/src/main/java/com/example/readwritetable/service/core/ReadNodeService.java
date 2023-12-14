package com.example.readwritetable.service.core;

import com.example.readwritetable.service.dto.NodeDto;

public interface ReadNodeService extends CrudService<NodeDto, Long>, HierarchyService<NodeDto, Long> {
}
