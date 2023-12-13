package com.hierachy.adjacencylist.service.core.impl;

import com.hierachy.adjacencylist.domain.Node;
import com.hierachy.adjacencylist.repository.NodeRepo;
import com.hierachy.adjacencylist.service.core.NodeService;
import com.hierachy.adjacencylist.service.dto.NodeDto;
import com.hierachy.adjacencylist.service.mapper.NodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepo repo;
    private final NodeMapper mapper;

    @Override
    public NodeDto find(Long id) {
        return repo.findById(id).map(mapper::toDto).orElse(null);
    }

    @Override
    public NodeDto save(NodeDto node) {
        return mapper.toDto(repo.save(mapper.toEntity(node)));
    }

    @Override
    public void delete(Long id) {
        Node currentNode = repo.findById(id).orElse(null);
        if (currentNode == null) {
            log.info("Node doesn't exist: {}", id);
        } else {
            List<Node> child = repo.findAllByParentId(currentNode.getId());
            if (child.size() > 0) {
                child.forEach(c -> {
                    c.setParentId(currentNode.getParentId());
                    repo.save(c);
                });
            }
            repo.delete(currentNode);
            log.info("Delete successfully: {}", id);
        }
    }

    @Override
    public List<NodeDto> findDescendants(Long id) {
        return repo.findDescendants(id).stream().map(mapper::toDto).toList();
    }

    @Override
    public List<NodeDto> findAncestors(Long id) {
        return repo.findAncestors(id).stream().map(mapper::toDto).toList();
    }

    @Override
    public NodeDto insertNode(String childIds, NodeDto node) {
        node = save(node);
        if (childIds != null) {
            List<Long> child = Arrays.stream(childIds.split(",")).map(Long::valueOf).toList();
            NodeDto tmpNode = node;
            child.forEach(id -> {
                NodeDto childNode = find(id);
                childNode.setParentId(tmpNode.getId());
                save(childNode);
            });
        }
        return node;
    }
}
