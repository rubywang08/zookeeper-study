package src.com.test;

public class TestFianl {
	public static void testfinal() {
		String a = "hello2";
		String d = "hello";
		String d2 = "hello";
		String d3 = "hello";
		final String b = "hello";
		String c = b + 2;
		String e = "hello" + 2;
		String e2 = d2 + 2;
		String e3 = d3 + 2;
		String f = "hello2";
		System.out.println(a == c);
		System.out.println(a == f);
		System.out.println(b == d);
		System.out.println(d == d2);
		System.out.println(a == e);
		System.out.println(a == e2);
		System.out.println(a == e3);
	}

	public static String appendStr(String s) {
		s += "bbb";
		return s;

	}

	public static String appendSB(StringBuilder sb) {
		sb.append("bbb");
		return sb.toString();
	}

	public static void main(String[] args) {
		testfinal();
//		String s = "aaa";
//		System.out.println(appendStr(s));
//		System.out.println(s);
//		StringBuilder sb = new StringBuilder("aaa");
//		System.out.println(appendSB(sb));
//		System.out.println(sb);
	}
}
