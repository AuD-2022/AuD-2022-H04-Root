package h04;

import java.util.Arrays;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static org.junit.jupiter.api.Assertions.*;

import h04.JUnitUtils.StreamConverter.IntStream;
import h04.function.ArrayDoubleToIntFunction;
import h04.student.LinearInterpolationStudent;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvFileSource;

public class H3_1 {

    LinearInterpolationStudent instance = new LinearInterpolationStudent();

    @ParameterizedTest
    @CsvFileSource(resources = "h3_1/LinearInterpolation_one_value")
    public void t1(
        @ConvertWith(IntStream.class) Stream<Integer> streamForArray,
        @ConvertWith(IntStream.class) Stream<Integer> streamForExpectedElements) {
        assertCorrectFitFunction(streamForArray, streamForExpectedElements);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "h3_1/LinearInterpolation_multiple_values")
    public void t2(
        @ConvertWith(IntStream.class) Stream<Integer> streamForArray,
        @ConvertWith(IntStream.class) Stream<Integer> streamForExpectedElements) {
        assertCorrectFitFunction(streamForArray, streamForExpectedElements);
    }

    public void assertCorrectFitFunction(Stream<Integer> streamForArray, Stream<Integer> streamForExpectedElements) {
        var array = streamForArray.toArray(Integer[]::new);
        var arrayString = Arrays.toString(array);
        var function = instance.fitFunction(array);
        assertNotNull(function, format("fitFunction(%s)", Arrays.toString(array)));
        assertEquals(
            ArrayDoubleToIntFunction.class,
            function.getClass(), format("unexpected class of object returned by fitFunction(%s)", arrayString));
        var actualElements = stream(((ArrayDoubleToIntFunction) function).elements).boxed().toArray(Integer[]::new);
        var expectedElements = streamForExpectedElements.toArray(Integer[]::new);
        assertEquals(
            Arrays.toString(expectedElements),
            Arrays.toString(actualElements),
            format("array in ArrayDoubleToIntFunction returned by fitFunction(%s) differs from expected one", arrayString));
    }

}
