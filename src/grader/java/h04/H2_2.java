package h04;

import h04.student.MyCollectionsStudent;
import org.junit.jupiter.api.BeforeEach;

public class H2_2 {

    public MyCollectionsStudent<String> instance;

    @BeforeEach
    public void beforeEach() {
        instance = MyCollectionsStudent.forString();
    }
}
