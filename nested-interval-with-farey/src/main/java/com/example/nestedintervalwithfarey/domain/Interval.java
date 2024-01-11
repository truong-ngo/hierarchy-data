package com.example.nestedintervalwithfarey.domain;

import com.example.nestedintervalwithfarey.exception.FareyTreeException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;


/**
 * An interval base on farey fraction
 * */
@Data
@NoArgsConstructor
public class Interval {
    public enum Side {
        LEFT, RIGHT
    }
    private Rational left;
    private Rational right;

    /**
     * Constructor with left rational of interval
     * @param left left rational
     * */
    public Interval(Rational left) {
        this.left = left;
        this.right = calculateRight();
    }

    /**
     * Calculate right rational of interval
     * @Right rational is the next element of left (a/b) in F(b) Farey sequence
     * */
    public Rational calculateRight() {
        long den = Rational.inverse(left.getNum(), left.getDen());
        long num = (left.getNum() * den + 1) / left.getDen();
        return new Rational(num, den);
    }

    /**
     * Getter of right, ensure that value is not null
     * */
    public Rational getRight() {
        if (right == null) {
            right = calculateRight();
        }
        return right;
    }

    /**
     * Find parent interval
     * */
    @JsonIgnore
    public Interval getParent() {
        if (left.getNum() == 0) {
            return null;
        }
        long num = this.left.getNum() - this.getRight().getNum();
        long den = this.left.getDen() - this.getRight().getDen();
        return new Interval(new Rational(num, den));
    }

    /**
     * Find child number n of interval
     * @param n child number n
     * */
    public Interval getChild(int n) {
        long num = left.getNum() * n + right.getNum();
        long den = left.getDen() * n + right.getDen();
        return new Interval(new Rational(num, den));
    }

    /**
     * Find insertion interval of an interval
     * @param child list of point available in interval
     * */
    public Interval findInsertionInterval(List<Interval> child) {
        boolean isValidPoints = child.stream().allMatch(p -> p.isChild(this));
        if (!isValidPoints) throw new FareyTreeException("Invalid point");
        Rational mediant = Rational.getMediant(left, getRight());
        List<Rational> pts = child.stream().map(Interval::getLeft).toList();
        while (pts.contains(mediant)) {
            mediant = Rational.getMediant(left, mediant);
        }
        return new Interval(mediant);
    }

    /**
     * Transform descendants interval of an interval when move to new interval
     * */
    public Interval linearTransform(Interval oldAncestor, Interval newAncestor) {
        if (!oldAncestor.contains(this)) {
            throw new FareyTreeException("old ancestor doesn't contain interval");
        }
        if (right == null) right = getRight();
        long num = (oldAncestor.left.getDen() * newAncestor.right.getNum() - oldAncestor.right.getDen() * newAncestor.left.getNum()) * left.getNum() +
                   (oldAncestor.right.getNum() * newAncestor.left.getNum() - oldAncestor.left.getNum() * newAncestor.right.getNum()) * left.getDen();
        long den = (oldAncestor.left.getDen() * newAncestor.right.getDen() - oldAncestor.right.getDen() * newAncestor.left.getDen()) * left.getNum() +
                   (oldAncestor.right.getNum() * newAncestor.left.getDen() - oldAncestor.left.getNum() * newAncestor.right.getDen()) * left.getDen();
        return new Interval(new Rational(num, den));
    }

    /**
     * Check if interval is root
     * */
    @JsonIgnore
    public boolean isRoot() {
        return left.getNum() == 0 && left.getDen() == 1;
    }

    /**
     * Check if interval is child of another interval
     * */
    public boolean isChild(Interval interval) {
        if (this.isRoot()) {
            throw new FareyTreeException("Interval input is root");
        }
        return left.getNum() - right.getNum() == interval.left.getNum() ||
               left.getDen() - right.getDen() == interval.left.getDen();
    }

    public String toString() {
        return String.format("(%s, %s]", left.toString(), right.toString());
    }

    public boolean contains(Interval interval) {
        return left.compareTo(interval.left) < 0 && right.compareTo(interval.right) >= 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Interval interval = (Interval) o;
        return Objects.equals(left, interval.left) && Objects.equals(right, interval.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, right);
    }
}
