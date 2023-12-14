package com.example.readwritetable.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "nested_set")
public class ReadNode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "parent_id")
    private Long parentId;
    @Column(name = "lft")
    private Long left;
    @Column(name = "rgt")
    private Long right;
    @Column(name = "node_number")
    private Integer nodeNumber;
    @Column(name = "node_count")
    private Integer nodeCount;
    private Integer depth;
}
