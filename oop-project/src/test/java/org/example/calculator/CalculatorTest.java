package org.example.calculator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.*;

public class CalculatorTest {

    @ParameterizedTest
    @MethodSource("formulateAndResult")
    void calculateTest(int operand1, String operator, int operand2, int result) {
        int calculateReuslt = Calculator.calculate(operand1, operator, operand2);

        assertThat(calculateReuslt).isEqualTo(result);

    }

    private static Stream<Arguments> formulateAndResult() {
        return Stream.of(
                arguments(1, "+", 2, 3)
                ,arguments(1, "-", 2, -1)
                ,arguments(4, "*", 2, 8)
                ,arguments(4, "/", 2, 2)
        );
    }
}
