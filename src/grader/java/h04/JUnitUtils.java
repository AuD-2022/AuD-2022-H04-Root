package h04;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
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

        private static final Function<String, String> defaultConverter = Function.identity();

        private final Function<String, ?> converter;

        public StreamConverter() {
            this(defaultConverter);
        }

        public StreamConverter(Function<String, ?> converter) {
            this.converter = converter;
        }

        @Override
        public Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            if (source instanceof String s && Stream.class.isAssignableFrom(targetType)) {
                return Arrays.stream(s.split(" ")).map(converter);
            } else {
                throw new IllegalArgumentException();
            }
        }

        public static class IntStream extends StreamConverter {

            public IntStream() {
                super(s -> !s.equals("null") ? Integer.valueOf(s) : null);
            }

        }
    }
}
