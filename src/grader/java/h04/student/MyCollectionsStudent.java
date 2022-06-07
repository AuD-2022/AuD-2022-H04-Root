package h04.student;

import java.util.List;

import static java.util.Comparator.naturalOrder;
import static org.mockito.Mockito.spy;

import h04.TUtils;
import h04.collection.ListItem;
import h04.collection.MyCollections;
import h04.tutor.MyCollectionsTutor;

public class MyCollectionsStudent {

    public final MyCollectionsTutor<String> tutor = new MyCollectionsTutor<>(l -> 0, naturalOrder());

    public final MyCollections<String> student;

    public MyCollectionsStudent() {
        this.student = spy(new MyCollections<>(l -> 1, naturalOrder()));
    }

    public ListItem<String> listToListItem(List<String> list) {
        return TUtils.getMethod(MyCollections.class, "listToListItem", List.class).callMethod(student, list);
    }

    public ListItem<String> listItemToList(ListItem<String> head, List<String> list) {
        return TUtils.getMethod(MyCollections.class, "listItemToList", ListItem.class, List.class)
            .callMethod(student, head, list);
    }

    public Void sort(List<String> list) {
        TUtils.getMethod(MyCollections.class, "sort", List.class).callMethod(student, list);
        return null;
    }

    public ListItem<String> adaptiveMergeSortInPlace(ListItem<?> head, int threshold) {
        return TUtils.getMethod(MyCollections.class, "adaptiveMergeSortInPlace", ListItem.class, int.class)
            .callMethod(student, head, threshold);
    }
}
