package h04.function;

import org.jetbrains.annotations.Nullable;

/**
 * Fits a function to a set of data points.
 *
 * @author Nhan Huynh
 */
public interface DoubleToIntFunctionFitter {

    /**
     * Fits a function to the given data.
     *
     * @param y the samples
     *
     * @return the fitted function
     */
    DoubleToIntFunction fitFunction(@Nullable Integer[] y);
}
