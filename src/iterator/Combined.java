package iterator;

import java.util.Iterator;

public class Combined<E> implements Iterable<E> {
	private Iterator<E> first;
	private Iterator<E> second;

	public Combined(Iterable<E> first, Iterable<E> second) {
		this.first = first.iterator();
		this.second = second.iterator();
	}

	private class CombinedIterator implements Iterator<E> {
		boolean flag = true;

		@Override
		public boolean hasNext() {
			if (first.hasNext()||second.hasNext())
				return true;
			return false;
		}

		@Override
		public E next() {
			if (!first.hasNext()) // Check if the first iterator is finished, return the variable from the second
				// iterator
				return second.next();
			else if (!second.hasNext()) // Check if the second array is finished, return the variable from the first
				// array
				return first.next();
			else if (flag) {
				flag=false;
				return first.next();
			} else {
				flag = true;
				return second.next();
			}
		}

	}

	@Override
	public Iterator<E> iterator() {
		return new CombinedIterator();
	}
}
