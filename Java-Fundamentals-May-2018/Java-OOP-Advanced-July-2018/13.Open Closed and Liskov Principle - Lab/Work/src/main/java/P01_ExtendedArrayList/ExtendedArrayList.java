package P01_ExtendedArrayList;

import java.util.ArrayList;
import java.util.Iterator;

public class ExtendedArrayList<T extends Comparable<T>> extends ArrayList<T> {

    public T min() {
        Iterator<T> iterator = super.iterator();
        if (iterator.hasNext()) {
            T min = iterator.next();

            while (iterator.hasNext()) {
                T element = iterator.next();
                if (min.compareTo(element) > 0) {
                    min = element;
                }
            }

            return min;
        }

        return null;
    }

    public T max() {
        Iterator<T> iterator = super.iterator();
        if (iterator.hasNext()) {
            T max = iterator.next();

            while (iterator.hasNext()) {
                T element = iterator.next();
                if (max.compareTo(element) < 0) {
                    max = element;
                }
            }

            return max;
        }

        return null;
    }
}
