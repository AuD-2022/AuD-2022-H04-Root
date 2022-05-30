package h04;

import java.util.concurrent.Callable;

import static h04.TextConstants.NOT_IMPLEMENTED;

import org.opentest4j.AssertionFailedError;

public class TUtils {

    public static <R> R assertImplemented(Callable<R> r) {
        try {
            return assertImplementedT(r);
        } catch (Exception e) {
            // case may not occur
            return null;
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
