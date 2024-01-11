package com.example.nestedintervalwithfarey.repository;

import com.example.nestedintervalwithfarey.domain.Node;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface NodeRepository extends JpaRepository<Node, Long>, JpaSpecificationExecutor<Node> {
    /**
     * Find all using specification
     * */
    List<Node> findAll(Specification<Node> specification);

    /**
     * Find one using specification
     * */
    Optional<Node> findOne(Specification<Node> specification);
}
