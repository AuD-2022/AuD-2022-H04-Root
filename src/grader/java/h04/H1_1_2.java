package h04;


import java.util.stream.DoubleStream;

import h04.student.LinearDoubleToIntFunctionStudent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

public class H1_1_2 {

    static final double DELTA_TEST = 1. / 8.;

    @ParameterizedTest
    @CsvFileSource(resources = "/h1/abs")
    void test1(double a, double b) {
        var function = new LinearDoubleToIntFunctionStudent(a, b);
        for (double x = 0; x <= 1; x += DELTA_TEST) {
            int expected = (int) Math.round(a * x + b);
            function.assertEqualsApply(x, expected);
        }
    }

    @Test
    void test2() {
        var function = new LinearDoubleToIntFunctionStudent(42, 69);
        DoubleStream.of(-1, -0.1, 1.1, 2).forEach(function::assertThrowsApply);
    }
}
