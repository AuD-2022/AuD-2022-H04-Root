package h04;

import java.util.Arrays;

import static java.lang.Math.abs;
import static java.lang.Math.ceil;
import static java.lang.Math.floor;
import static java.lang.Math.round;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

import h04.JUnitUtils.IntArrayConverter;
import h04.function.ArrayDoubleToIntFunction;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

class H1_1_1 {

    static final double DELTA = 1.0e-6;
    static final double DELTA_TEST = 1. / 8.;

    @ParameterizedTest
    @CsvFileSource(resources = "/h1/arrays")
    void test1(@ConvertWith(IntArrayConverter.class) int[] array) {
        var function = new ArrayDoubleToIntFunction(array);
        for (int i = 0; i < array.length; i++) {
            int expected = array[i];
            // with negative difference
            if (i != 0) {
                double value = ((double) i - DELTA) / (array.length - 1);
                int actual = function.apply(value);
                assertEqualsApply(array, value, expected, actual);
            }
            // with positive difference
            if (i != array.length - 1) {
                double value = ((double) i + DELTA) / (array.length - 1);
                int actual = function.apply(value);
                assertEqualsApply(array, value, expected, actual);
            }
            // without difference
            double value = (double) i / (array.length - 1);
            int actual = function.apply(value);
            assertEqualsApply(array, value, expected, actual);
        }
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/h1/arrays")
    void test2(@ConvertWith(IntArrayConverter.class) int[] array) {
        var function = new ArrayDoubleToIntFunction(array);
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
            int actual = function.apply(value);
            assertEqualsApply(array, value, expected, actual);
        }
    }

    void assertEqualsApply(int[] array, double value, int expected, int actual) {
        assertEquals(expected, actual, format("apply(%s) differs for array %s", value, Arrays.toString(array)));
    }
}
