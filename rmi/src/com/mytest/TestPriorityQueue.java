package src.com.test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.PriorityQueue;


public class TestPriorityQueue {
	public static void main(String[] args) {
		PriorityQueue<GregorianCalendar> pq = new PriorityQueue<>();
		pq.offer(new GregorianCalendar(1906, Calendar.DECEMBER, 9));
		pq.offer(new GregorianCalendar(1815, Calendar.DECEMBER, 10));
		pq.offer(new GregorianCalendar(1903, Calendar.DECEMBER, 3));
		pq.offer(new GregorianCalendar(1910, Calendar.JUNE, 22));
		System.out.println("Iterating over elements.....");
		for (GregorianCalendar gregorianCalendar : pq) {
			System.out.println(gregorianCalendar.get(Calendar.YEAR));
		}
		System.out.println("Removing elements....");
		while (!pq.isEmpty()) {
			System.out.println(pq.remove().get(Calendar.YEAR));
		}
	}
	

}
