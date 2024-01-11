package com.example.nestedintervalwithfarey.repository;

import com.example.nestedintervalwithfarey.domain.Interval;
import com.example.nestedintervalwithfarey.domain.Node;
import com.example.nestedintervalwithfarey.domain.enumerate.Operator;
import com.example.nestedintervalwithfarey.domain.Rational;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.Specification;

/**
 * Contain node specification to query database
 * */
public class NodeSpec {

    /**
     * Specification to find child node
     * */
    public static Specification<Node> isChildOf(Long id) {
        return (root, query, criteriaBuilder) -> {
            Root<Node> parent = query.from(Node.class);
            return criteriaBuilder.and(
                    criteriaBuilder.equal(
                            NodeExpression.nodeDiff(root, criteriaBuilder, Rational.Element.NUMERATOR),
                            parent.get("leftNum")
                    ),
                    criteriaBuilder.equal(
                            NodeExpression.nodeDiff(root, criteriaBuilder, Rational.Element.DENOMINATOR),
                            parent.get("leftDen")
                    ),
                    hasId(parent, criteriaBuilder, id)
            );
        };
    }

    /**
     * Specification to find parent node
     * */
    public static Specification<Node> isParentOf(Rational left) {
        return (root, query, criteriaBuilder) -> {
            Interval parent = new Interval(left).getParent();
            return criteriaBuilder.and(hasLeft(root, criteriaBuilder, parent.getLeft()));
        };
    }

    /**
     * Specification to find descendants node
     * */
    public static Specification<Node> isDescendantsOf(Long id) {
        return (descendants, query, criteriaBuilder) -> {
            Root<Node> node = query.from(Node.class);
            return criteriaBuilder.and(
                    compare(descendants, node, criteriaBuilder, Operator.GREATER, Interval.Side.LEFT),
                    compare(descendants, node, criteriaBuilder, Operator.LESSER_THAN_EQUAL, Interval.Side.RIGHT),
                    hasId(node, criteriaBuilder, id)
            );
        };
    }

    /**
     * Specification to find ancestors node
     * */
    public static Specification<Node> isAncestorsOf(Long id) {
        return (ancestors, query, criteriaBuilder) -> {
            Root<Node> node = query.from(Node.class);
            return criteriaBuilder.and(
                    compare(ancestors, node, criteriaBuilder, Operator.LESSER, Interval.Side.LEFT),
                    compare(ancestors, node, criteriaBuilder, Operator.GREATER_THAN_EQUAL, Interval.Side.RIGHT),
                    hasId(node, criteriaBuilder, id)
            );
        };
    }

    /**
     * Specification for left
     * */
    public static Specification<Node> hasLeft(Rational left) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.and(hasLeft(root, criteriaBuilder, left));
    }

    /**
     * Predicate of compare left, right of two interval
     * */
    public static Predicate compare(Root<Node> root, Root<Node> node, CriteriaBuilder builder, Operator operator, Interval.Side side) {
        Expression<Long> expression = NodeExpression.compare(root, node, builder, side);
        switch (operator) {
            case GREATER -> {
                return builder.greaterThan(expression, 0L);
            }
            case GREATER_THAN_EQUAL -> {
                return builder.greaterThanOrEqualTo(expression, 0L);
            }
            case LESSER -> {
                return builder.lessThan(expression, 0L);
            }
            case LESSER_THAN_EQUAL -> {
                return builder.lessThanOrEqualTo(expression, 0L);
            }
            case EQUAL -> {
                return builder.equal(expression, 0L);
            }
            default -> {
                return null;
            }
        }
    }

    /**
     * Predicate of id field
     * */
    public static Predicate hasId(Root<Node> root, CriteriaBuilder builder, Long id) {
        return builder.equal(root.get("id"), id);
    }

    /**
     * Predicate of left field (leftNum, leftDen)
     * */
    public static Predicate hasLeft(Root<Node> root, CriteriaBuilder builder, Rational left) {
        return builder.and(
                builder.equal(root.get("leftNum"), left.getNum()),
                builder.equal(root.get("leftDen"), left.getDen())
        );
    }
}
