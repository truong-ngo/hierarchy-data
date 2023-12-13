package com.hierachy.adjacencylist.domain;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "adjacency_list")
public class Node {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "parent_id")
    private Long parentId;
}
