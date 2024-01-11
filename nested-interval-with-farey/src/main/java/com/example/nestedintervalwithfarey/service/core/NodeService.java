package com.example.nestedintervalwithfarey.service.core;

import com.example.nestedintervalwithfarey.service.dto.NodeDto;

public interface NodeService extends CrudService<NodeDto, Long>, HierarchyTreeService<NodeDto, Long> {

}
