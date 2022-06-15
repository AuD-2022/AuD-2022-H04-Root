package h04;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

import h04.collection.ListItem;
import org.opentest4j.AssertionFailedError;

public class ListUtils {

    public static <T> Stream<ListItem<T>> streamItems(ListItem<T> head) {
        return Stream.iterate(head, Objects::nonNull, l -> l != null ? l.next : null).limit(50);
    }

    public static <T> Stream<T> stream(ListItem<T> head) {
        return streamItems(head).map(li -> li.key);
    }

    public static <T> Collector<T, ListItem<T>, ListItem<T>> listItemCollector() {
        return new Collector<>() {

            public ListItem<T> lastHead, lastTail;

            @Override
            public Supplier<ListItem<T>> supplier() {
                return () -> lastHead = lastTail = new ListItem<>();
            }

            @Override
            public BiConsumer<ListItem<T>, T> accumulator() {
                return (i, s) -> {
                    if (i != lastHead) {
                        throw new IllegalStateException();
                    }
                    lastTail = lastTail.next = new ListItem<>();
                    lastTail.key = s;
                };
            }

            @Override
            public BinaryOperator<ListItem<T>> combiner() {
                return (l1, l2) -> {
                    if (l1 != lastHead) {
                        throw new IllegalStateException();
                    }
                    lastTail = lastTail.next = l2;
                    return lastHead;
                };
            }

            @Override
            public Function<ListItem<T>, ListItem<T>> finisher() {
                return l -> l.next;
            }

            @Override
            public Set<Characteristics> characteristics() {
                return Collections.emptySet();
            }
        };
    }

    public static String toString(ListItem<?> head) {
        return toString(head, 50);
    }

    public static String toString(ListItem<?> head, int expectedSize) {
        if (head == null) {
            return "[]";
        }

        return streamItems(head).limit(expectedSize + 1).collect(

            new Collector<ListItem<?>, StringBuilder, String>() {
                int counter = 0;

                @Override
                public Supplier<StringBuilder> supplier() {
                    return () -> new StringBuilder("[");
                }

                @Override
                public BiConsumer<StringBuilder, ListItem<?>> accumulator() {

                    return (b, i) -> {
                        counter++;
                        b.append(i != null ? i.key : null);
                        if (i != null && i.next != null) {
                            b.append("|");
                        }
                        if (counter > expectedSize) {
                            b.append("...");
                        }
                    };
                }

                @Override
                public BinaryOperator<StringBuilder> combiner() {
                    return StringBuilder::append;
                }

                @Override
                public Function<StringBuilder, String> finisher() {
                    return b -> b.append("]").toString();
                }

                @Override
                public Set<Characteristics> characteristics() {
                    return Set.of();
                }
            });
    }

    public static Collector<CharSequence, ?, String> listToString() {
        return Collectors.joining("|", "[", "]");
    }

    public static String toString(List<?> list) {
        return list.stream().map(String::valueOf).collect(Collectors.joining("|", "[", "]"));
    }

    public static <T> ListItem<T> clone(ListItem<T> head) {
        return streamItems(head).map(i -> i.key).collect(ListUtils.listItemCollector());
    }

    public static <T> void assertSameItems(Stream<ListItem<T>> expected, Stream<ListItem<T>> actual, Supplier<String> message) {
        var expectedList = expected.limit(50).toList();
        var actualList = actual.limit(50).toList();
        var stringExpected = expectedList.stream().map(i -> String.valueOf(i.key)).collect(listToString());
        var stringActual = actualList.stream().map(i -> String.valueOf(i.key)).collect(listToString());
        assertEquals(stringExpected, stringActual, message.get());
        var expectedIterator = expectedList.iterator();
        var actualIterator = actualList.iterator();
        while (expectedIterator.hasNext()) {
            var expectedElement = expectedIterator.next();
            var actualElement = actualIterator.next();
            if (expectedElement != actualElement) {
                throw new AssertionFailedError(message.get(), "no new ListItem objects", "new ListItem objects");
            }
        }
    }

    public static <T> void assertSameItems(Stream<ListItem<T>> expected, Stream<ListItem<T>> actual, String message) {
        assertSameItems(expected, actual, () -> message);
    }
}
