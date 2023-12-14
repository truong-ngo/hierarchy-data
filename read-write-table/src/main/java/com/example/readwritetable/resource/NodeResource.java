package com.example.readwritetable.resource;

import com.example.readwritetable.service.core.ReadNodeService;
import com.example.readwritetable.service.dto.NodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NodeResource {
    private final ReadNodeService readNodeService;

    @GetMapping("/{id}/descendants")
    public ResponseEntity<List<NodeDto>> findDescendants(@PathVariable Long id) {
        return ResponseEntity.ok(readNodeService.findDescendants(id));
    }

    @GetMapping("/{id}/ancestors")
    public ResponseEntity<List<NodeDto>> findAncestors(@PathVariable Long id) {
        return ResponseEntity.ok(readNodeService.findAncestors(id));
    }
}
