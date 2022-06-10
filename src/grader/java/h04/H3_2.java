package h04;

import h04.SortingExperiment;
import h04.TUtils;
import org.junit.jupiter.api.Test;

public class H3_2 {

    @Test
    public void t1() {
        TUtils.assertImplemented(() -> SortingExperiment.computeOptimalThresholds(10, 4, 3, 2));
    }

    @Test
    public void t2() {
        TUtils.assertImplemented(() -> {
            SortingExperiment.main(new String[] {});
            return null;
        });
    }
}
