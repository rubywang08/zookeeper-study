package src.com.mytest;

public class TestString {
    public static void main(String[] args) {

//        String a= "abc";
//        String b = "abc";
//        String c = new String("abc");
//        String d = "ab" + "c";
//        System.out.println(a==b);
//        System.out.println(a==c);
//        System.out.println(b==c);
//        System.out.println(d==a);
//        System.out.println(d==b);
//        System.out.println(d==c);
        System.out.println(test());
    }

    private static int test() {
        try{
            return 1;
        }catch(Exception e){
            return 2;
        }finally{
            return 3;
        }
    }
}
