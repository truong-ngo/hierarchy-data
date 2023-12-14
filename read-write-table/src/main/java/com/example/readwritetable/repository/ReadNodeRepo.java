package com.example.readwritetable.repository;

import com.example.readwritetable.domain.ReadNode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReadNodeRepo extends JpaRepository<ReadNode, Long> {
    @Query(value = "select children.* from nested_set as node, nested_set as children\n" +
                   "where node.lft < children.lft and node.rgt > children.rgt and node.id = :id;", nativeQuery = true)
    List<ReadNode> findDescendants(Long id);

    @Query(value = "select parent.* from nested_set as node, nested_set as parent\n" +
                   "where node.lft > parent.lft and node.rgt < parent.rgt and node.id = :id;", nativeQuery = true)
    List<ReadNode> findAncestors(Long id);
}
