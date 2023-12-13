package com.hierachy.adjacencylist.resource;

import com.hierachy.adjacencylist.service.core.NodeService;
import com.hierachy.adjacencylist.service.dto.NodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class NodeResource {
    private final NodeService nodeService;

    @GetMapping("/{id}")
    public ResponseEntity<NodeDto> find(@PathVariable Long id) {
        return ResponseEntity.ok(nodeService.find(id));
    }

    @GetMapping("/{id}/descendants")
    public ResponseEntity<List<NodeDto>> findDescendants(@PathVariable Long id) {
        return ResponseEntity.ok(nodeService.findDescendants(id));
    }

    @GetMapping("/{id}/ancestors")
    public ResponseEntity<List<NodeDto>> findAncestors(@PathVariable Long id) {
        return ResponseEntity.ok(nodeService.findAncestors(id));
    }

    @PostMapping
    public ResponseEntity<NodeDto> insertNode(@RequestBody NodeDto node, @RequestParam(required = false) String childIds) {
        return ResponseEntity.ok(nodeService.insertNode(childIds, node));
    }

    @PutMapping
    public ResponseEntity<NodeDto> update(@RequestBody NodeDto node) {
        return ResponseEntity.ok(nodeService.save(node));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        nodeService.delete(id);
        return ResponseEntity.ok().build();
    }
}
