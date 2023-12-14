package com.example.readwritetable.service.dto;

import lombok.Data;

@Data
public class NodeDto {
    private Long id;
    private String name;
    private Long parentId;
    private Long left;
    private Long right;
    private Integer nodeNumber;
    private Integer nodeCount;
    private Integer depth;
}
