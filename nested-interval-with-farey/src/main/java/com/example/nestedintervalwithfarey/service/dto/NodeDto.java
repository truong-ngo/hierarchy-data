package com.example.nestedintervalwithfarey.service.dto;

import com.example.nestedintervalwithfarey.domain.Interval;
import lombok.Data;

@Data
public class NodeDto {
    private Long id;
    private String name;
    private Interval interval;
    private Long parentId;
}
