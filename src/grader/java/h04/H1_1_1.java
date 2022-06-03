package h04;

import java.util.stream.DoubleStream;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.round;

import h04.JUnitUtils.IntArrayConverter;
import h04.student.ArrayDoubleToIntFunctionStudent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

class H1_1_1 {

    static final double DELTA = 1e-6;
    static final double DELTA_TEST = 1. / 8.;

    /**
     * Checks if function fulfils criterion (a).
     *
     * @param array the array to test the function with
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/h1/arrays")
    public void t1(@ConvertWith(IntArrayConverter.class) int[] array) {
        var function = new ArrayDoubleToIntFunctionStudent(array);
        for (int i = 0; i < array.length; i++) {
            int expected = array[i];
            // with negative difference
            if (i != 0) {
                double value = ((double) i - DELTA) / (array.length - 1);
                function.assertEqualsApply(value, expected);
            }
            // with positive difference
            if (i != array.length - 1) {
                double value = ((double) i + DELTA) / (array.length - 1);
                function.assertEqualsApply(value, expected);
            }
            // without difference
            double value = (double) i / (array.length - 1);
            function.assertEqualsApply(value, expected);
        }
    }

    /**
     * Checks if function fulfuls criterion(b)
     *
     * @param array the array to test the function with
     */
    @ParameterizedTest
    @CsvFileSource(resources = "/h1/arrays")
    public void t2(@ConvertWith(IntArrayConverter.class) int[] array) {
        var function = new ArrayDoubleToIntFunctionStudent(array);
        int n = array.length;
        for (double x = DELTA_TEST; x < n - 1; x += DELTA_TEST) {
            if (abs(x - round(x)) < DELTA) {
                continue;
            }
            double value = x / (n - 1);
            int x0 = (int) floor(x);
            int x1 = (int) ceil(x);
            double f0 = array[x0];
            double f1 = array[x1];
            int expected = (int) round(f0 + ((f1 - f0) / (x1 - x0)) * (x - x0));
            function.assertEqualsApply(value, expected);
        }
    }

    @Test
    public void t3() {
        var function = new ArrayDoubleToIntFunctionStudent(new int[] {1, 2, 3});
        DoubleStream.of(-1, -0.1, 1.1, 2).forEach(function::assertThrowsApply);
    }
}
