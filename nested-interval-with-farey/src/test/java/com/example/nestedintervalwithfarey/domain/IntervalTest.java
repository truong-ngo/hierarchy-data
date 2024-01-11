package com.example.nestedintervalwithfarey.domain;

import com.example.nestedintervalwithfarey.exception.FareyTreeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

public class IntervalTest {

    @Test
    public void testCalculateRight() {
        Rational l = new Rational(3L, 5L);
        Interval itv = new Interval(l);
        Rational rs = new Rational(2L, 3L);
        Assertions.assertEquals(itv.getRight(), rs);
    }

    @Test
    public void testGetParent() {
        Interval itv = new Interval(new Rational(3L, 5L));
        Interval p = itv.getParent();
        Interval rs = new Interval(new Rational(1L, 2L));
        Assertions.assertEquals(p, rs);
    }

    @Test
    public void testGetChild() {
        Interval p = new Interval(new Rational(1L, 2L));
        Interval c = p.getChild(3);
        Interval rs = new Interval(new Rational(4L, 7L));
        Assertions.assertEquals(c, rs);
    }

    @Test
    public void testFindInsertionInterval() {
        List<Rational> pts = List.of(
                new Rational(1L, 2L), new Rational(1L, 3L),
                new Rational(1L, 5L), new Rational(1L, 6L),
                new Rational(1L, 8L), new Rational(1L, 9L),
                new Rational(1L, 10L), new Rational(1L, 11L),
                new Rational(1L, 12L), new Rational(1L, 13L));
        Interval itv = new Interval(new Rational(0L, 1L));
        List<Interval> cs = pts.stream().map(Interval::new).toList();
        Interval p = itv.findInsertionInterval(cs);
        Interval rs = new Interval(new Rational(1L, 4L));
        Assertions.assertEquals(p, rs);
    }

    @Test
    public void testLinearTransform() {
        Interval itv = new Interval(new Rational(3L, 8L));
        Interval oldI = new Interval(new Rational(0L, 1L));
        Interval newI = new Interval(new Rational(1L, 3L));
        Interval linearTrans = itv.linearTransform(oldI, newI);
        Interval rs = new Interval(new Rational(8L, 21L));
        Assertions.assertEquals(linearTrans, rs);

        Interval oldP = new Interval(new Rational(1L, 2L));
        Executable ex = () -> itv.linearTransform(oldP, newI);
        String mess = "old ancestor doesn't contain interval";
        Assertions.assertThrows(FareyTreeException.class, ex, mess);
    }

    @Test
    public void testIsRoot() {
        Interval itv = new Interval(new Rational(0L, 1L));
        boolean isRoot = itv.isRoot();
        Assertions.assertTrue(isRoot);
        Interval itv2 = new Interval(new Rational(1L, 2L));
        boolean isRoot2 = itv2.isRoot();
        Assertions.assertFalse(isRoot2);
    }

    @Test
    public void testIsChild() {
        Interval itv1 = new Interval(new Rational(1L, 2L));
        Interval itv2 = new Interval(new Rational(0L, 1L));
        boolean isChild1 = itv1.isChild(itv2);
        Assertions.assertTrue(isChild1);

        Interval itv3 = new Interval(new Rational(1L, 3L));
        boolean isChild3 = itv3.isChild(itv1);
        Assertions.assertFalse(isChild3);

        Executable ex = () -> itv2.isChild(itv1);
        String mess = "Interval input is root";
        Assertions.assertThrows(FareyTreeException.class, ex, mess);
    }

    @Test
    public void testContains() {
        Interval itv1 = new Interval(new Rational(1L, 2L));
        Interval itv2 = new Interval(new Rational(2L, 3L));
        boolean isContains = itv1.contains(itv2);
        Assertions.assertTrue(isContains);

        Interval itv3 = new Interval(new Rational(1L, 4L));
        boolean isContains2 = itv3.contains(itv2);
        Assertions.assertFalse(isContains2);
    }
}
