package h04.function;

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
    DoubleToIntFunction fitFunction(Integer[] y);
}
