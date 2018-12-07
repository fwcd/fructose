package fwcd.fructose.structs;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * An {@link ArrayList}-based {@link List} implementation
 * that maintains weak references to its elements.
 */
public class WeakArrayList<T> implements List<T> {
	private final List<WeakReference<T>> entries;
	
	public WeakArrayList() {
		entries = new ArrayList<>();
	}
	
	public WeakArrayList(Collection<? extends T> c) {
		entries = new ArrayList<>(c.size());
		for (T entry : c) {
			entries.add(new WeakReference<>(entry));
		}
	}
	
	private WeakArrayList(List<WeakReference<T>> delegate, boolean delegateDirectly) {
		if (delegateDirectly) {
			entries = delegate;
		} else {
			entries = new ArrayList<>(delegate);
		}
	}
	
	private void removeObsoleteEntries() {
		Iterator<WeakReference<T>> iterator = entries.iterator();
		while (iterator.hasNext()) {
			WeakReference<T> ref = iterator.next();
			if (ref.get() == null) {
				iterator.remove();
			}
		}
	}
	
	@Override
	public int size() {
		removeObsoleteEntries();
		return entries.size();
	}

	@Override
	public boolean isEmpty() {
		removeObsoleteEntries();
		return entries.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		Iterator<WeakReference<T>> iterator = entries.iterator();
		boolean containsElement = false;
		while (iterator.hasNext()) {
			T value = iterator.next().get();
			if (value == null) {
				iterator.remove();
			} else if (value.equals(o)) {
				containsElement = true;
			}
		}
		return containsElement;
	}

	@Override
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private final Iterator<WeakReference<T>> delegate = entries.iterator();
			private T peeked = null;
			
			@Override
			public boolean hasNext() {
				// Advance the delegate iterator until a valid reference
				// was found or the iteration ended
				while (delegate.hasNext()) {
					peeked = delegate.next().get();
					if (peeked != null) {
						return true;
					}
				}
				return false;
			}
			
			@Override
			public T next() {
				if (peeked != null) {
					T result = peeked;
					peeked = null;
					return result;
				}
				
				// Advance the delegate iterator until a valid reference
				// was found or the iteration ended
				while (delegate.hasNext()) {
					T value = delegate.next().get();
					if (value != null) {
						return value;
					}
				}
				
				throw new NoSuchElementException("WeakArrayList.Iterator could not find a next element");
			}
		};
	}

	@Override
	public Object[] toArray() {
		return entries.stream()
			.filter(it -> it.get() != null)
			.map(WeakReference::get)
			.toArray();
	}

	@Override
	public <A> A[] toArray(A[] a) {
		return entries.stream()
			.filter(it -> it.get() != null)
			.map(WeakReference::get)
			.collect(Collectors.toList())
			.toArray(a);
	}

	@Override
	public boolean add(T e) {
		removeObsoleteEntries();
		return entries.add(new WeakReference<T>(e));
	}

	@Override
	public boolean remove(Object o) {
		Iterator<WeakReference<T>> iterator = entries.iterator();
		boolean removed = false;
		while (iterator.hasNext()) {
			T value = iterator.next().get();
			if (value == null) {
				iterator.remove();
			} else if (value.equals(o)) {
				iterator.remove();
				removed = true;
			}
		}
		return removed;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object value : c) {
			if (!contains(value)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		removeObsoleteEntries();
		boolean changed = false;
		for (T value : c) {
			changed |= entries.add(new WeakReference<>(value));
		}
		return changed;
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		removeObsoleteEntries();
		for (T value : c) {
			entries.add(index, new WeakReference<>(value));
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		Iterator<WeakReference<T>> iterator = entries.iterator();
		boolean removed = false;
		while (iterator.hasNext()) {
			T value = iterator.next().get();
			if (value == null) {
				iterator.remove();
			} else if (c.contains(value)) {
				iterator.remove();
				removed = true;
			}
		}
		return removed;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		Iterator<WeakReference<T>> iterator = entries.iterator();
		boolean removed = false;
		while (iterator.hasNext()) {
			T value = iterator.next().get();
			if (value == null) {
				iterator.remove();
			} else if (!c.contains(value)) {
				iterator.remove();
				removed = true;
			}
		}
		return removed;
	}

	@Override
	public void clear() {
		entries.clear();
	}

	@Override
	public T get(int index) {
		removeObsoleteEntries();
		return entries.get(index).get();
	}

	@Override
	public T set(int index, T element) {
		removeObsoleteEntries();
		return entries.set(index, new WeakReference<>(element)).get();
	}

	@Override
	public void add(int index, T element) {
		removeObsoleteEntries();
		entries.add(index, new WeakReference<>(element));
	}

	@Override
	public T remove(int index) {
		removeObsoleteEntries();
		return entries.remove(index).get();
	}

	@Override
	public int indexOf(Object o) {
		removeObsoleteEntries();
		return entries.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		removeObsoleteEntries();
		return entries.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		removeObsoleteEntries();
		return new ListIteratorImpl(entries.listIterator());
	}

	@Override
	public ListIterator<T> listIterator(int index) {
		removeObsoleteEntries();
		return new ListIteratorImpl(entries.listIterator(index));
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		removeObsoleteEntries();
		return new WeakArrayList<>(entries.subList(fromIndex, toIndex), true);
	}
	
	private class ListIteratorImpl implements ListIterator<T> {
		private final ListIterator<WeakReference<T>> delegate;
		
		public ListIteratorImpl(ListIterator<WeakReference<T>> delegate) {
			this.delegate = delegate;
		}
		
		@Override
		public boolean hasNext() { return delegate.hasNext(); }
		
		@Override
		public T next() { return delegate.next().get(); }

		@Override
		public boolean hasPrevious() { return delegate.hasPrevious(); }

		@Override
		public T previous() { return delegate.previous().get(); }

		@Override
		public int nextIndex() { return delegate.nextIndex(); }

		@Override
		public int previousIndex() { return delegate.previousIndex(); }

		@Override
		public void remove() { delegate.remove(); }

		@Override
		public void set(T e) { delegate.set(new WeakReference<>(e)); }

		@Override
		public void add(T e) { delegate.add(new WeakReference<>(e)); }
	}
	
	@Override
	public int hashCode() {
		return 9 * entries.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!getClass().equals(obj.getClass())) return false;
		
		WeakArrayList<?> other = (WeakArrayList<?>) obj;
		Iterator<T> thisIterator = iterator();
		Iterator<?> otherIterator = other.iterator();
		boolean thisHasNext;
		boolean otherHasNext;
		
		while (true) {
			thisHasNext = thisIterator.hasNext();
			otherHasNext = otherIterator.hasNext();
			
			if (thisHasNext && otherHasNext) {
				if (!thisIterator.next().equals(otherIterator.next())) {
					return false;
				}
			} else {
				// Only return true when both iterators have no more elements
				// left, otherwise one of the iterators has to be longer than
				// the other.
				return !thisHasNext && !otherHasNext;
			}
		}
	}
	
	@Override
	public String toString() {
		Iterator<WeakReference<T>> iterator = entries.iterator();
		StringBuilder str = new StringBuilder().append('[');
		while (iterator.hasNext()) {
			T value = iterator.next().get();
			if (value == null) {
				iterator.remove();
			} else {
				str.append(value).append(", ");
			}
		}
		return str
			.delete(str.length() - 2, str.length())
			.append(']')
			.toString();
	}
}
