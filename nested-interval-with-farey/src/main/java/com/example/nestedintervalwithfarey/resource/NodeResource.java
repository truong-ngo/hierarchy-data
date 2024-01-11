package com.example.nestedintervalwithfarey.resource;

import com.example.nestedintervalwithfarey.service.core.NodeService;
import com.example.nestedintervalwithfarey.service.dto.NodeDto;
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

    @GetMapping("/{id}/parent")
    public ResponseEntity<NodeDto> findParent(@PathVariable Long id) {
        return ResponseEntity.ok(nodeService.findParent(id));
    }

    @GetMapping("/{id}/child")
    public ResponseEntity<List<NodeDto>> findChild(@PathVariable Long id) {
        return ResponseEntity.ok(nodeService.findChild(id));
    }

    @PostMapping("/")
    public ResponseEntity<NodeDto> insert(@RequestBody NodeDto node, @RequestParam(name = "childId", required = false) Long childId) throws Exception {
        NodeDto body = (childId == null) ? nodeService.add(node) : nodeService.insert(node, childId);
        return ResponseEntity.ok(body);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NodeDto> move(@PathVariable Long id, @RequestParam(name = "parentId") Long parentId) {
        NodeDto body = nodeService.move(id, parentId);
        return ResponseEntity.ok(body);
    }
}
