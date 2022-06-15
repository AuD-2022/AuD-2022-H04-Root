package h04;

import org.junit.jupiter.api.Test;
import org.sourcegrade.jagr.api.rubric.TestForSubmission;

@TestForSubmission("h04")
public class H3_2 {

    @Test
    public void t1() {
        try {
            TUtils.assertImplemented(() -> SortingExperiment.computeOptimalThresholds(10, 4, 3, 2), "", true);
        } catch (RuntimeException e) {
            // do nothing
        }
    }

//    @Test
//    public void t2() {
//        TUtils.assertImplemented(() -> {
//            SortingExperiment.main(new String[] {});
//            return null;
//        });
//    }
}
