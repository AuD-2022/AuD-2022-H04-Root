package h04;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

import org.sourcegrade.jagr.api.rubric.Criterion;
import org.sourcegrade.jagr.api.rubric.Grader;
import org.sourcegrade.jagr.api.rubric.JUnitTestRef;

public class RubricUtils {

    @SafeVarargs
    public static Criterion criterion(String description, int points, Callable<Method>... methods) {
        Criterion.Builder criterionBuilder = Criterion.builder();
        criterionBuilder.shortDescription(description);
        criterionBuilder.maxPoints(points);
        Grader.TestAwareBuilder grader = Grader.testAwareBuilder();
        for (Callable<Method> m : methods) {
            grader = grader.requirePass(JUnitTestRef.ofMethod(m));
        }
        grader.pointsPassedMax();
        grader.pointsFailedMin();
        return criterionBuilder.grader(grader.build()).build();
    }
}
