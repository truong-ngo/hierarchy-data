package com.hierachy.adjacencylist.repository;

import com.hierachy.adjacencylist.domain.Node;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NodeRepo extends JpaRepository<Node, Long> {

    @Query(value = "with recursive ancestors as (\n" +
            "    select anchor.* from adjacency_list as anchor where id = :id\n" +
            "    union all\n" +
            "    select recur.* from adjacency_list as recur join ancestors on ancestors.parent_id = recur.id\n" +
            ")\n" +
            "select * from ancestors where id <> :id order by id;", nativeQuery = true)
    List<Node> findAncestors(Long id);

    @Query(value = "with recursive descendants as (\n" +
            "    select anchor.* from adjacency_list as anchor where parent_id = :id\n" +
            "    union all\n" +
            "    select recur.* from adjacency_list as recur join descendants on descendants.id = recur.parent_id\n" +
            ")\n" +
            "select * from descendants order by id;", nativeQuery = true)
    List<Node> findDescendants(Long id);

    List<Node> findAllByParentId(Long parentId);
}
