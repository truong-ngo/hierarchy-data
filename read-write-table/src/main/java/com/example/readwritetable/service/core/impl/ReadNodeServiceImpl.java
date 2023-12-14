package com.example.readwritetable.service.core.impl;

import com.example.readwritetable.repository.ReadNodeRepo;
import com.example.readwritetable.service.core.ReadNodeService;
import com.example.readwritetable.service.dto.NodeDto;
import com.example.readwritetable.service.mapper.ReadNodeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReadNodeServiceImpl implements ReadNodeService {
    private final ReadNodeRepo repo;
    private final ReadNodeMapper mapper;

    @Override
    public NodeDto find(Long id) {
        return repo.findById(id).map(mapper::toDto).orElse(null);
    }

    @Override
    public NodeDto save(NodeDto node) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<NodeDto> findDescendants(Long id) {
        return repo.findDescendants(id).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<NodeDto> findAncestors(Long id) {
        return repo.findAncestors(id).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public NodeDto insertNode(String childIds, NodeDto node) {
        return null;
    }
}
