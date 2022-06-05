package h04.student;

import static java.lang.String.format;

import h04.function.DoubleToIntFunction;
import org.opentest4j.AssertionFailedError;

public class RatioToNumberFunction implements DoubleToIntFunction {

    private final int number;

    public RatioToNumberFunction(int number) {
        this.number = number;
    }

    @Override
    public int apply(double ratio) {
        if (ratio * number != (int) (ratio * number)) {
            throw new AssertionFailedError(format("invalid ratio: %s", ratio));
        }
        return (int) (ratio * number);
    }
}
