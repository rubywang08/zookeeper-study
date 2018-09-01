package src.com.test;

import java.util.TreeSet;

public class Main {
    private static int hash(int h) {
        h += (h << 15) ^ 0xffffcd7d;
        h ^= (h >>> 10);
        h += (h << 3);
        h ^= (h >>> 6);
        h += (h << 2) + (h << 14);
        return h ^ (h >>> 16);
    }

    public static void main(String[] args) {
//        testLeftMove();
//        testRehash();
        TreeSet treeSet= new TreeSet<String>();
        treeSet.add("1");
        treeSet.add("2");
        treeSet.add("3");
        treeSet.add("4");
        System.out.println(treeSet.first());
        System.out.println(treeSet.headSet("3").last());

    }

    private static void testLeftMove() {
        int size = 1;
        for (int i = 0; i < 16; i++) {
            size <<= 1;
            System.out.println(size);
        }
    }

    private static void testRehash() {
        System.out.println((hash("ab".hashCode()) >>> 28) & 15);
        System.out.println(("ab".hashCode() >>> 28) & 15);
    }


}
