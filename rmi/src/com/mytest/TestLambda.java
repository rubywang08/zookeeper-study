package src.com.test;

import java.lang.ref.SoftReference;
import java.util.Comparator;

public class TestLambda {

    public static void main(String[] args) {
        Comparator c1 = Comparator.comparing((Person p) -> p.getName());
        Comparator c = Comparator.comparing(Person::getName);
    }
}
