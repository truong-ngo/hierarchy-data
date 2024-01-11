package com.example.nestedintervalwithfarey.service;

import com.example.nestedintervalwithfarey.domain.Node;
import com.example.nestedintervalwithfarey.repository.NodeRepository;
import com.example.nestedintervalwithfarey.service.core.impl.NodeServiceImpl;
import com.example.nestedintervalwithfarey.service.dto.NodeDto;
import com.example.nestedintervalwithfarey.service.mapper.NodeMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NodeServiceTest {
    @InjectMocks
    private NodeServiceImpl service;

    @Mock
    private NodeRepository repository;

    @Mock
    private NodeMapper mapper;

    @BeforeEach
    public void init() {
        Node root = Node.builder().
                name("root").
                leftNum(0L).
                leftDen(1L).
                build();

        Node node1 = Node.builder().
                name("node 1").
                leftNum(1L).
                leftDen(2L).
                build();

        Node node2 = Node.builder().
                name("node 2").
                leftNum(1L).
                leftDen(3L).
                build();

        Node node3 = Node.builder().
                name("node 3").
                leftNum(1L).
                leftDen(4L).
                build();

        Node node1$1 = Node.builder().
                name("node 1.1").
                leftNum(2L).
                leftDen(3L).
                build();

        Node node1$1$1  = Node.builder().
                name("node 1.1.1").
                leftNum(3L).
                leftDen(4L).
                build();

        Node node1$1$2  = Node.builder().
                name("node 1.1.2").
                leftNum(5L).
                leftDen(7L).
                build();

        repository.save(root);
        repository.save(node1);
        repository.save(node2);
        repository.save(node3);
        repository.save(node1$1);
        repository.save(node1$1$1);
        repository.save(node1$1$2);
    }

    @Test
    public void testFind() {
        NodeDto node = service.find(1L);
        Assertions.assertNotNull(node);
    }
}
