package com.example.nestedintervalwithfarey.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * Rational represent left and right of farey interval
 * */
@Data
@NoArgsConstructor
public class Rational implements Comparable<Rational> {
    public enum Element {
        NUMERATOR, DENOMINATOR
    }

    private Long num;
    private Long den;

    public Rational(Long num, Long den) {
        this.num = num / gcd(num, den);
        this.den = den / gcd(num, den);
    }

    /**
     * Greatest common divisor
     * */
    public static long gcd(Long a, Long b) {
        if (a == 0) return b;
        if (b == 0) return a;
        if (a > b) return gcd(a % b, b);
        else return (gcd(a, b % a));
    }

    /**
     * Find mediant of two rational
     * */
    public static Rational getMediant(Rational a, Rational b) {
        return new Rational(a.num + b.num, a.den + b.den);
    }

    /**
     * A modification's inverse of x modulo m in -a*x + m*y = 1
     * @Use to calculate next element's denominator (x) of a/m in F(m) Farey sequence
     * @Support H2 database function test
     * */
    public static long inverse(long a, long m) {
        long u = m;
        long v = a;
        long x = 0;
        long y = 1;
        while (v != 0) {
            long q = u / v;
            long r = u % v;
            long tempX = x;
            x = y;
            y = tempX - q * y;
            u = v;
            v = r;
        }
        if (u == 1 || u == -1) {
            return (x < 0) ? -x : m - x;
        } else {
            throw new ArithmeticException("Inverse does not exist");
        }
    }

    /**
     * Find numerator of next element of a/m in F(m) Farey sequence
     * @Support H2 database function test
     * */
    public static long nextNum(long a, long m) {
        long den = inverse(a, m);
        return (a * den + 1) / m;
    }

    /**
     * Comparing a/b and c/d
     * @Support H2 database function test
     * */
    public static long compare(long a, long b, long c, long d) {
        return a * d - b * c;
    }

    @Override
    public int compareTo(Rational arg) {
        return (int) (compare(num, den, arg.num, arg.den));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rational rational = (Rational) o;
        return Objects.equals(num, rational.num) && Objects.equals(den, rational.den);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, den);
    }

    public String toString() {
        return String.format("%s/%s", num, den);
    }
}