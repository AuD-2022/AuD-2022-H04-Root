package h04;

import java.util.concurrent.Callable;

import static h04.TextConstants.NOT_IMPLEMENTED;
import static org.junit.jupiter.api.Assertions.*;

import org.opentest4j.AssertionFailedError;

public class TUtils {

    public static final String PATTERN_NOT_IMPLEMENTED = "H(\\d+(.\\d+)*) - not implemented";

    public static <R> R assertImplemented(Callable<R> r) {
        try {
            return assertImplementedT(r);
        } catch (RuntimeException e) {
            throw e;
        } catch (Exception e) {
            return fail("this case can not occur");
        }
    }

    public static <C> C assertImplementedT(Callable<C> c) throws Exception {
        try {
            return c.call();
        } catch (RuntimeException e) {
            if (!e.getMessage().matches("H(\\d+(.\\d+)*) - not implemented")) {
                throw e;
            }
            throw new AssertionFailedError(NOT_IMPLEMENTED);
        }
    }
}
