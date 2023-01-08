package iterator;

import java.util.Iterator;

public class TwoArrays implements Iterable<Integer> {
	private int[] a1;
	private int[] a2;

	public TwoArrays(int[] a1, int[] a2) {
		this.a1 = a1;
		this.a2 = a2;
	}

	private class TwoArraysIterator implements Iterator<Integer> {
		private int count1 = 0;
		private int count2 = 0;
		boolean flag = true;

		@Override
		public Integer next() {
			if (count1 == a1.length)
				return a2[count2++];
			else if (count2 == a2.length)
				return a1[count1++];
			else if (flag) // flag=true means we in the first(a1) array and flag=false mean we at the
							// second(a2)
			{
				flag = false;
				return a1[count1++];
			}
			flag = true;
			return a2[count2++];
		}

		@Override
		public boolean hasNext() { // check if counters finished or not
			if (count1 < a1.length || count2 < a2.length)
				return true;
			return false;
		}
	}

	@Override
	public Iterator<Integer> iterator() {
		return new TwoArraysIterator();
	}

}
