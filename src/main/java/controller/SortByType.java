package controller;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Alucard on 2016-12-11.
 */
public class SortByType implements Comparator<File> {
    private boolean order;

    public SortByType(boolean order){
        this.order=order;
    }

    @Override
    public int compare(File o1, File o2) {
        String s1 = o1.getName().toLowerCase();
        String s2 = o2.getName().toLowerCase();
        int s1dot = s1.lastIndexOf('.');
        int s2dot = s2.lastIndexOf('.');
        if ((s1dot == -1) == (s2dot == -1)) {
            s1 = s1.substring(s1dot + 1);
            s2 = s2.substring(s2dot + 1);
            if(order) {
                return s1.compareTo(s2);
            }
            else {
                return s2.compareTo(s1);
            }
        } else if (s1dot == -1) {
            return -1;
        } else {
            return 1;
        }
    }
}
