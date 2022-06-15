package h04.student;

import java.util.Comparator;
import java.util.List;

import static h04.TUtils.assertImplemented;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

import h04.function.FunctionOnRatioOfInversions;

public class FunctionOnRatioOfInversionsStudent {

    public int apply(List<String> elements) {
        var callString = String.format("apply(%s) with f(Ratio)=Ratio*NumberOfItems", elements);
        var n = elements.size();
        var function = new FunctionOnRatioOfInversions<String>(
            Comparator.naturalOrder(),
            new RatioToNumberFunction((n * (n - 1)) / 2));
        return assertImplemented(
            () -> function.apply(elements),
            callString
        );
    }

    public void assertEqualsApply(int expected, List<String> elements) {
        var string = elements.toString();
        assertEquals(expected, apply(elements), format("apply(%s) differs with f(Ratio)=Ratio*NumberOfItems", string));
    }

}
