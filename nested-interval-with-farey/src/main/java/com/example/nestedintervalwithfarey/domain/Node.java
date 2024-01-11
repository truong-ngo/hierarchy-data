package com.example.nestedintervalwithfarey.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "nested_interval_farey")
public class Node {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(name = "lft_num")
    private Long leftNum;
    @Column(name = "lft_den")
    private Long leftDen;
    @Column(name = "parent_id")
    private Long parentId;
}
