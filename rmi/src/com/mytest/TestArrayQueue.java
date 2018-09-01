package src.com.test;

public class TestArrayQueue {
	
	public static void main(String[] args) {
		CopiedArrayDeque<Integer> queue = new CopiedArrayDeque<>();
		queue.addFirst(1);
		queue.addFirst(2);
		queue.addFirst(3);
		queue.addLast(4);
		queue.addLast(5);
		queue.addLast(6);
		for (Integer integer : queue) {
			System.out.println(integer);
		}
		queue.removeFirst();
		queue.removeLast();
		
	}

}
