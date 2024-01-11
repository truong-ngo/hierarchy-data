package com.example.nestedintervalwithfarey.domain;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.concurrent.atomic.AtomicLong;

public class RationalTest {
    @Test
    public void testGcd() {
        long a = 6;
        long b = 9;
        long gcd = Rational.gcd(a, b);
        long result = 3;
        Assertions.assertEquals(gcd, result);
    }

    @Test
    public void testMediant() {
        Rational a = new Rational(1L, 3L);
        Rational b = new Rational(2L, 5L);
        Rational mediant = Rational.getMediant(a, b);
        Rational result = new Rational(3L, 8L);
        Assertions.assertEquals(mediant, result);
    }

    @Test
    public void testInverse() {
        long a = 3;
        long m = 8;
        long inverse = Rational.inverse(a, m);
        long rs = 5;
        Assertions.assertEquals(inverse, rs);

        long n = 9;
        AtomicLong inv = new AtomicLong();
        Executable ex = () -> inv.set(Rational.inverse(a, n));
        String mess = "Inverse does not exist";
        Assertions.assertThrows(ArithmeticException.class, ex, mess);
    }
}
