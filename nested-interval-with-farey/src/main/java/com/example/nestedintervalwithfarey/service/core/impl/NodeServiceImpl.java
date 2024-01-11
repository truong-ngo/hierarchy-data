package com.example.nestedintervalwithfarey.service.core.impl;

import com.example.nestedintervalwithfarey.domain.Interval;
import com.example.nestedintervalwithfarey.exception.FareyTreeException;
import com.example.nestedintervalwithfarey.repository.NodeRepository;
import com.example.nestedintervalwithfarey.repository.NodeSpec;
import com.example.nestedintervalwithfarey.service.core.NodeService;
import com.example.nestedintervalwithfarey.service.dto.NodeDto;
import com.example.nestedintervalwithfarey.service.mapper.NodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service for manipulate farey interval tree
 * */
@Slf4j
@Service
@RequiredArgsConstructor
public class NodeServiceImpl implements NodeService {
    private final NodeRepository repository;
    private final NodeMapper mapper;

    @Override
    public NodeDto find(Long id) {
        return repository.findById(id).map(mapper::toDto).orElse(null);
    }

    @Override
    public NodeDto save(NodeDto node) {
        return mapper.toDto(repository.save(mapper.toEntity(node)));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * Find all descendants of node
     * */
    @Override
    public List<NodeDto> findDescendants(Long id) {
        return repository.findAll(NodeSpec.isDescendantsOf(id)).stream().map(mapper::toDto).toList();
    }

    /**
     * Find all ancestors of node
     * */
    @Override
    public List<NodeDto> findAncestors(Long id) {
        return repository.findAll(NodeSpec.isAncestorsOf(id)).stream().map(mapper::toDto).toList();
    }

    /**
     * Find parent node
     * */
    @Override
    public NodeDto findParent(Long id) {
        NodeDto node = find(id);
        if (node != null) {
            Interval pItv = node.getInterval().getParent();
            return repository.findOne(Specification.where(NodeSpec.isParentOf(pItv.getLeft())))
                    .map(mapper::toDto).orElse(null);
        } else {
            throw new FareyTreeException("Node not found");
        }
    }

    /**
     * Find all direct child of node
     * */
    @Override
    public List<NodeDto> findChild(Long id) {
        return repository.findAll(NodeSpec.isChildOf(id)).stream().map(mapper::toDto).toList();
    }

    /**
     * Add node to a specific parent node
     * */
    @Override
    public NodeDto add(NodeDto node) {
        NodeDto parent = find(node.getParentId());
        if (parent == null) {
            throw new FareyTreeException("Parent not found");
        }
        List<Interval> child = findChild(node.getParentId()).stream().map(NodeDto::getInterval).toList();
        Interval interval = parent.getInterval().findInsertionInterval(child);
        node.setInterval(interval);
        return save(node);
    }

    @Override
    @Transactional
    public void remove(Long id) {

    }

    /**
     * Insert between two node
     * */
    @Override
    public NodeDto insert(NodeDto node, Long childId) {
        NodeDto child = find(childId);
        try {
            node.setInterval(child.getInterval());
            Interval oldAnc = child.getInterval();
            Interval newAnc = node.getInterval().getChild(1);
            moveDescendants(child, oldAnc, newAnc);
            child.setInterval(newAnc);
            save(child);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return save(node);
    }

    /**
     * Move node to new position
     * */
    @Override
    public NodeDto move(Long id, Long newParentId) {
        NodeDto node = find(id);
        NodeDto newParent = find(newParentId);
        if (node == null || newParent == null) {
            throw new FareyTreeException("Node or parent node not exist");
        }
        List<Interval> newParentChildItv = findChild(newParentId).stream().map(NodeDto::getInterval).toList();
        Interval oldAnc = node.getInterval();
        Interval newAnc = newParent.getInterval().findInsertionInterval(newParentChildItv);
        moveDescendants(node, oldAnc, newAnc);
        node.setInterval(newAnc);
        return save(node);
    }

    /**
     * Move descendants node when ancestor node move
     * */
    private void moveDescendants(NodeDto node, Interval oldAnc, Interval newAnc) {
        List<NodeDto> descendants = findDescendants(node.getId());
        for (NodeDto n : descendants) {
            Interval newItv = n.getInterval().linearTransform(oldAnc, newAnc);
            n.setInterval(newItv);
            save(n);
        }
    }
}
