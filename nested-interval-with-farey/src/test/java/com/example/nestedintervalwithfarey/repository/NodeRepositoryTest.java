package com.example.nestedintervalwithfarey.repository;

import com.example.nestedintervalwithfarey.domain.Interval;
import com.example.nestedintervalwithfarey.domain.Node;
import com.example.nestedintervalwithfarey.domain.Rational;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import java.util.List;

@DataJpaTest
@SqlGroup({
        @Sql(value = "classpath:sql/h2_right_den.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS),
        @Sql(value = "classpath:sql/h2_right_num.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS),
        @Sql(value = "classpath:sql/h2_compare.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
})
public class NodeRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private NodeRepository nodeRepository;

    @BeforeEach
    void init() {
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

        entityManager.persist(root);
        entityManager.persist(node1);
        entityManager.persist(node2);
        entityManager.persist(node3);
        entityManager.persist(node1$1);
        entityManager.persist(node1$1$1);
        entityManager.persist(node1$1$2);
        entityManager.flush();
    }

    @Test
    public void testFindByLeft() {
        Rational left = new Rational(2L, 3L);
        Node node = nodeRepository.findOne(Specification.where(NodeSpec.hasLeft(left))).orElse(null);
        Assertions.assertNotNull(node);
        Assertions.assertEquals(left, new Rational(node.getLeftNum(), node.getLeftDen()));
    }

    @Test
    public void testFindAncestors() {
        Node node = nodeRepository.findById(5L).orElse(null);
        Assertions.assertNotNull(node);
        List<Node> ancestors = nodeRepository.findAll(NodeSpec.isAncestorsOf(node.getId()));
        Assertions.assertEquals(ancestors.size(), 2);
    }

    @Test
    public void testFindDescendants() {
        Node node = nodeRepository.findById(5L).orElse(null);
        Assertions.assertNotNull(node);
        List<Node> descendants = nodeRepository.findAll(NodeSpec.isDescendantsOf(node.getId()));
        Assertions.assertEquals(descendants.size(), 2);
    }

    @Test
    public void testFindParent() {
        Node node = nodeRepository.findById(5L).orElse(null);
        Assertions.assertNotNull(node);
        Node parent = nodeRepository.findOne(NodeSpec.isParentOf(new Rational(node.getLeftNum(), node.getLeftDen()))).orElse(null);
        Assertions.assertNotNull(parent);
        Interval itv = new Interval(new Rational(node.getLeftNum(), node.getLeftDen())).getParent();
        Assertions.assertEquals(itv.getLeft().getNum(), parent.getLeftNum());
        Assertions.assertEquals(itv.getLeft().getDen(), parent.getLeftDen());
    }

    @Test
    public void testFindChild() {
        Node node = nodeRepository.findById(5L).orElse(null);
        Assertions.assertNotNull(node);
        List<Node> child = nodeRepository.findAll(NodeSpec.isChildOf(node.getId()));
        Assertions.assertEquals(child.size(), 2);
    }
}
