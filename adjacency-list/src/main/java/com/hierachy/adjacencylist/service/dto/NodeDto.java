package com.hierachy.adjacencylist.service.dto;

import lombok.Data;

@Data
public class NodeDto {
    private Long id;
    private String name;
    private Long parentId;
}
