package h04;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class JUnitUtils {

    public static class IntArrayConverter extends SimpleArgumentConverter {

        @Override
        public Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            if (source instanceof String s && int[].class.isAssignableFrom(targetType)) {
                return Arrays.stream(s.split(" ")).mapToInt(Integer::valueOf).toArray();
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    @Deprecated
    public static class StringListConverter extends SimpleArgumentConverter {

        @Override
        public Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            if (source instanceof String s && List.class.isAssignableFrom(targetType)) {
                return Arrays.stream(s.split(" ")).collect(Collectors.toList());
            } else {
                throw new IllegalArgumentException();
            }
        }
    }

    public static class StreamConverter extends SimpleArgumentConverter {

        @Override
        public Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            if (source instanceof String s && Stream.class.isAssignableFrom(targetType)) {
                return Arrays.stream(s.split(" "));
            } else {
                throw new IllegalArgumentException();
            }
        }
    }
}
