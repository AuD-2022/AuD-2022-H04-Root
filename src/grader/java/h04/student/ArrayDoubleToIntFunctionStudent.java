package h04.student;

import java.util.Arrays;
import java.util.stream.IntStream;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

import h04.TUtils;
import h04.function.ArrayDoubleToIntFunction;
import h04.function.DoubleToIntFunction;

public final class ArrayDoubleToIntFunctionStudent implements DoubleToIntFunction {

    private final int[] array;
    private final ArrayDoubleToIntFunction instance;

    public ArrayDoubleToIntFunctionStudent(int[] array) {
        this.array = IntStream.of(array).toArray();
        this.instance = TUtils.assertImplemented(
            () -> new ArrayDoubleToIntFunction(array),
            String.format("new ArrayDoubleToIntFunction(%s)", Arrays.toString(array)));
    }

    public int apply(double value) {
        return TUtils.assertImplemented(() -> instance.apply(value), String.format("apply(%s)", value));
    }


    public IllegalArgumentException assertThrowsApply(double value) {
        return TUtils.assertImplemented(
            () -> assertThrows(
                IllegalArgumentException.class,
                () -> instance.apply(value),
                () -> format("apply(%s) differs for array=%s", value, Arrays.toString(array))
            ),
            String.format("apply(%s)", value)
        );
    }

    public void assertEqualsApply(double value, double expected) {
        assertEquals(
            expected,
            apply(value),
            () -> format("apply(%s) differs for array=%s", value, Arrays.toString(array))
        );
    }
}
