package h04.student;

import java.util.Comparator;
import java.util.List;

import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

import h04.TUtils;
import h04.function.FunctionOnRatioOfRuns;

public class FunctionOnRatioOfRunsStudent {

    public int apply(List<String> elements) {
        var callString = String.format("apply(%s) with f(Ratio)=Ratio*NumberOfItems", elements);
        var function = new FunctionOnRatioOfRuns<String>(Comparator.naturalOrder(), new RatioToNumberFunction(elements.size()));
        return TUtils.assertImplemented(() -> function.apply(elements), callString);
    }

    public void assertEqualsApply(int expected, List<String> elements) {
        var string = elements.toString();
        assertEquals(expected, apply(elements), format("apply(%s) differs with f(Ratio)=Ratio*NumberOfItems", string));
    }
}
