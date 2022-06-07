package h04.student;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

import h04.TUtils;
import h04.function.DoubleToIntFunction;
import h04.function.LinearDoubleToIntFunction;

public final class LinearDoubleToIntFunctionStudent implements DoubleToIntFunction {

    public final double a, b;
    public final LinearDoubleToIntFunction instance;

    public LinearDoubleToIntFunctionStudent(double a, double b) {
        this.a = a;
        this.b = b;
        this.instance = TUtils.assertImplemented(() -> new LinearDoubleToIntFunction(a, b));
    }

    public int apply(double value) {
        return TUtils.assertImplemented(() -> instance.apply(value));
    }


    public IllegalArgumentException assertThrowsApply(double value) {
        return TUtils.assertImplemented(() -> assertThrows(IllegalArgumentException.class, () -> apply(value),
            () -> format("apply(%s) for a=%s, b=%s", value, a, b)));
    }

    public void assertEqualsApply(double value, double expected) {
        assertEquals(expected, apply(value), () -> format("apply(%s) differs for a=%s, b=%s", value, a, b));
    }
}
