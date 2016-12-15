package controller;

import java.io.File;
import java.util.Comparator;

/**
 * Created by Alucard on 2016-12-11.
 */
public class SortBySize implements Comparator<File> {
    private boolean order;
    public SortBySize(boolean order){
        this.order=order;
    }

    @Override
    public int compare(File o1, File o2) {
        if (order) {
            return Long.compare(o1.length(), o2.length());
        }
        else{
            return Long.compare(o2.length(), o1.length());
        }
    }
}
