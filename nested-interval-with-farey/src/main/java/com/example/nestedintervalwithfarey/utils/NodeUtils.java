package com.example.nestedintervalwithfarey.utils;

import com.example.nestedintervalwithfarey.domain.Node_;
import com.example.nestedintervalwithfarey.domain.Rational;

public class NodeUtils {
    public static String getNodeElement(Rational.Element element) {
        return element.equals(Rational.Element.NUMERATOR) ? Node_.LEFT_NUM : Node_.LEFT_DEN;
    }

    public enum NodeFunction {
        RIGHT_NUM("rightNum"), RIGHT_DEN("rightNum");

        private final String name;

        NodeFunction(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
