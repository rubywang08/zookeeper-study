package src.com.mytest;

public class TestSwap {
    public static void main(String[] args) {
        Integer i = 1;
        Integer j = 2;
        System.out.println(i);
        System.out.println(j);

        Ox o1 = new Ox("01");
        Ox o2 = new Ox("02");
        swap(o1,o2);
        System.out.println(o1);
        System.out.println(o2);

    }

    public static void swap(Ox i, Ox j) {
      i.setValue("011");
      j.setValue("012");
    }


    public static class Ox {
        private String value;

        public Ox(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }

        @Override
        public String toString() {
            return "Ox{" +
                    "value='" + value + '\'' +
                    '}';
        }
    }
}
