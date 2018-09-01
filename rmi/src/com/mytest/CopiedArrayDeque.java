package src.com.test;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * 
 * @author copy JDK ArrayDeque to debug
 *
 * @param <E>
 */
public class CopiedArrayDeque<E> extends AbstractCollection<E> implements Deque<E>, Cloneable, Serializable {
	transient Object[] elements;
	transient int head;
	transient int tail;
	private static final int MIN_INITIAL_CAPACITY = 8;
	private static final long serialVersionUID = 2340985798034038923L;

	private static int calculateSize(int paramInt) {
		int i = 8;
		if (paramInt >= i) {
			i = paramInt;
			i |= i >>> 1;
			i |= i >>> 2;
			i |= i >>> 4;
			i |= i >>> 8;
			i |= i >>> 16;
			if (++i < 0)
				i >>>= 1;
		}
		return i;
	}

	private void allocateElements(int paramInt) {
		this.elements = new Object[calculateSize(paramInt)];
	}

	private void doubleCapacity() {
		assert (this.head == this.tail);
		int i = this.head;
		int j = this.elements.length;
		int k = j - i;
		int l = j << 1;
		if (l < 0)
			throw new IllegalStateException("Sorry, deque too big");
		Object[] arrayOfObject = new Object[l];
		System.arraycopy(this.elements, i, arrayOfObject, 0, k);
		System.arraycopy(this.elements, 0, arrayOfObject, k, i);
		this.elements = arrayOfObject;
		this.head = 0;
		this.tail = j;
	}

	private <T> T[] copyElements(T[] paramArrayOfT) {
		if (this.head < this.tail) {
			System.arraycopy(this.elements, this.head, paramArrayOfT, 0, size());
		} else if (this.head > this.tail) {
			int i = this.elements.length - this.head;
			System.arraycopy(this.elements, this.head, paramArrayOfT, 0, i);
			System.arraycopy(this.elements, 0, paramArrayOfT, i, this.tail);
		}
		return paramArrayOfT;
	}

	public CopiedArrayDeque() {
		this.elements = new Object[16];
	}

	public CopiedArrayDeque(int paramInt) {
		allocateElements(paramInt);
	}

	public CopiedArrayDeque(Collection<? extends E> paramCollection) {
		allocateElements(paramCollection.size());
		addAll(paramCollection);
	}

	public void addFirst(E paramE) {
		if (paramE == null)
			throw new NullPointerException();
		this.elements[(this.head = this.head - 1 & this.elements.length - 1)] = paramE;
		if (this.head != this.tail)
			return;
		doubleCapacity();
	}

	public void addLast(E paramE) {
		if (paramE == null)
			throw new NullPointerException();
		this.elements[this.tail] = paramE;
		if ((this.tail = this.tail + 1 & this.elements.length - 1) != this.head)
			return;
		doubleCapacity();
	}

	public boolean offerFirst(E paramE) {
		addFirst(paramE);
		return true;
	}

	public boolean offerLast(E paramE) {
		addLast(paramE);
		return true;
	}

	public E removeFirst() {
		Object localObject = pollFirst();
		if (localObject == null)
			throw new NoSuchElementException();
		return (E) localObject;
	}

	public E removeLast() {
		Object localObject = pollLast();
		if (localObject == null)
			throw new NoSuchElementException();
		return (E) localObject;
	}

	public E pollFirst() {
		int i = this.head;
		Object localObject = this.elements[i];
		if (localObject == null)
			return null;
		this.elements[i] = null;
		this.head = (i + 1 & this.elements.length - 1);
		return (E) localObject;
	}

	public E pollLast() {
		int i = this.tail - 1 & this.elements.length - 1;
		Object localObject = this.elements[i];
		if (localObject == null)
			return null;
		this.elements[i] = null;
		this.tail = i;
		return (E) localObject;
	}

	public E getFirst() {
		Object localObject = this.elements[this.head];
		if (localObject == null)
			throw new NoSuchElementException();
		return (E) localObject;
	}

	public E getLast() {
		Object localObject = this.elements[(this.tail - 1 & this.elements.length - 1)];
		if (localObject == null)
			throw new NoSuchElementException();
		return (E) localObject;
	}

	public E peekFirst() {
		return (E) this.elements[this.head];
	}

	public E peekLast() {
		return (E) this.elements[(this.tail - 1 & this.elements.length - 1)];
	}

	public boolean add(E paramE) {
		addLast(paramE);
		return true;
	}

	public boolean offer(E paramE) {
		return offerLast(paramE);
	}

	public E remove() {
		return removeFirst();
	}

	public E poll() {
		return pollFirst();
	}

	public E element() {
		return getFirst();
	}

	public E peek() {
		return peekFirst();
	}

	public void push(E paramE) {
		addFirst(paramE);
	}

	public E pop() {
		return removeFirst();
	}

	public int size() {
		return (this.tail - this.head & this.elements.length - 1);
	}

	public boolean isEmpty() {
		return (this.head == this.tail);
	}

	public Iterator<E> iterator() {
		return new DeqIterator();
	}

	public boolean contains(Object paramObject) {
		if (paramObject == null)
			return false;
		int i = this.elements.length - 1;
		Object localObject;
		for (int j = this.head; (localObject = this.elements[j]) != null; j = j + 1 & i) {
			if (paramObject.equals(localObject))
				return true;
		}
		return false;
	}

	public boolean remove(Object paramObject) {
		return removeFirstOccurrence(paramObject);
	}

	public void clear() {
		int i = this.head;
		int j = this.tail;
		if (i == j)
			return;
		this.head = (this.tail = 0);
		int k = i;
		int l = this.elements.length - 1;
		do {
			this.elements[k] = null;
			k = k + 1 & l;
		} while (k != j);
	}

	public Object[] toArray() {
		return copyElements(new Object[size()]);
	}

	private class DeqIterator implements Iterator<E> {
		private int cursor = CopiedArrayDeque.this.head;
		private int fence = CopiedArrayDeque.this.tail;
		private int lastRet = -1;

		public boolean hasNext() {
			return (this.cursor != this.fence);
		}

		public E next() {
			if (this.cursor == this.fence)
				throw new NoSuchElementException();
			Object localObject = CopiedArrayDeque.this.elements[this.cursor];
			if ((CopiedArrayDeque.this.tail != this.fence) || (localObject == null))
				throw new ConcurrentModificationException();
			this.lastRet = this.cursor;
			this.cursor = (this.cursor + 1 & CopiedArrayDeque.this.elements.length - 1);
			return (E) localObject;
		}

	}

	@Override
	public boolean removeFirstOccurrence(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeLastOccurrence(Object paramObject) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> descendingIterator() {
		// TODO Auto-generated method stub
		return null;
	}

}

/*
 * Location: C:\Program Files\Java\jre1.8.0_151\lib\rt.jar Qualified Name:
 * java.util.CopiedArrayDeque Java Class Version: 8 (52.0) JD-Core Version: 0.5.3
 */
