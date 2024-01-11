package com.example.nestedintervalwithfarey.repository;

import com.example.nestedintervalwithfarey.domain.Interval;
import com.example.nestedintervalwithfarey.domain.Node;
import com.example.nestedintervalwithfarey.domain.Node_;
import com.example.nestedintervalwithfarey.domain.Rational;
import com.example.nestedintervalwithfarey.utils.NodeUtils;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Root;
import com.example.nestedintervalwithfarey.utils.NodeUtils.NodeFunction;

/**
 * Expression of node represent expression of node interval
 * */
public class NodeExpression {

    /**
     * Compare expression of two node
     * @Represent 'compare' db function
     * */
    public static Expression<Long> compare(Root<Node> root, Root<Node> node, CriteriaBuilder builder, Interval.Side side) {
        Expression<Long> rootNumExpr = getNodeExpression(root, builder, side, Rational.Element.NUMERATOR);
        Expression<Long> rootDenExpr = getNodeExpression(root, builder, side, Rational.Element.DENOMINATOR);
        Expression<Long> nodeNumExpr = getNodeExpression(node, builder, side, Rational.Element.NUMERATOR);
        Expression<Long> nodeDenExpr = getNodeExpression(node, builder, side, Rational.Element.DENOMINATOR);

        return builder.function("compare", Long.class,
                rootNumExpr, rootDenExpr,
                nodeNumExpr, nodeDenExpr);
    }

    /**
     * Right expression of node
     * @Represent 'rightNum' and 'rightDen' db function
     * */
    public static Expression<Long> right(Root<Node> root, CriteriaBuilder builder, Rational.Element element) {
        String function = element.equals(Rational.Element.NUMERATOR) ? NodeFunction.RIGHT_NUM.getName() : NodeFunction.RIGHT_DEN.getName();
        return builder.function(function, Long.class, root.get(Node_.LEFT_NUM), root.get(Node_.LEFT_DEN));
    }

    /**
     * Find different of rational element expression
     * @Return leftNum - rightNum or leftDen - rightDen
     * */
    public static Expression<Long> nodeDiff(Root<Node> root, CriteriaBuilder builder, Rational.Element element) {
        String el = NodeUtils.getNodeElement(element);
        String function = element.equals(Rational.Element.NUMERATOR) ? NodeFunction.RIGHT_NUM.getName() : NodeFunction.RIGHT_DEN.getName();
        return builder.diff(root.get(el), builder.function(function, Long.class, root.get(Node_.LEFT_NUM), root.get(Node_.LEFT_DEN)));
    }

    /**
     * Get expression of node interval (left, right)
     * @Return one of (leftNum, leftDen, rightNum, rightDen)
     * */
    public static Expression<Long> getNodeExpression(Root<Node> root, CriteriaBuilder builder, Interval.Side side, Rational.Element element) {
        String el = NodeUtils.getNodeElement(element);
        return side.equals(Interval.Side.LEFT) ? root.get(el) : right(root, builder, element);
    }
}
