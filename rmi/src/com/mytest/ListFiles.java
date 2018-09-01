package src.com.mytest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ListFiles {
    public static void main(String[] args) {
        List<File> files = new ArrayList<>();
        File startDir = new File("D:\\");
        recurseDirs(files, startDir);
        System.out.println(files);
    }

    private static void recurseDirs(List<File> files, File startDir) {
        for (File item : startDir.listFiles()) {
            if (item.isDirectory()) {
                recurseDirs(files, item);
            } else {
                files.add(item);
            }
        }
    }
}
