package telran.list.model;

import java.util.Iterator;

import telran.list.interfaces.IList;

public class MyLinkedList<E> implements IList<E> {
	private Node<E> first;
	private Node<E> last;
	private int size;

	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			Node<E>  node = first;
			@Override
			public boolean hasNext() {
				return node.next != null;
			}

			@Override
			public E next() {
				node = node.next;
				return node.prev.data;
			}
		};

	}

	@Override
	public boolean add(E element) {
		Node<E> newNode = new Node<E>(last, element, null);
		if (last != null) {
			last.next = newNode;
		} else {
			first = newNode;
		}
		last = newNode;
		size++;
		return true;
	}

	@Override
	public void clear() {
		Node<E> node = first;
		while (node.next != null){
			node.data = null;
			node.prev = null;
			node = node.next;
			if ( node != null )
				node.prev.next = null;
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public boolean add(int index, E element) {
		Node<E> node = getNodeByIndex(index);
		Node<E> newNode = new Node<>(null,element,null);
		if(node.prev != null){
			newNode.prev = node.prev;
			newNode.prev.next = newNode;
		}
		node.prev = newNode;
		newNode.next = node;
		return true;
	}

	@Override
	public E get(int index) {
		Node<E> node = getNodeByIndex(index);
		return node.data;
	}

	private Node<E> getNodeByIndex(int index) {
		checkIndex(index);
		Node<E> node = first;
		if(index < size / 2) {
			for (int i = 0; i < index; i++) {
				node = node.next;
			}
		}else {
			node = last;
			for (int i = size - 1; i > index; i-- ){
				node = node.prev;
			}
		}
		return node;
	}

	private void checkIndex(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException(index);
		}

	}

	@Override
	public int indexOf(Object o) {
		int index = 0;
		if (o != null) {
			for (Node<E> x = first; x != null; x = x.next, index++) {
				if (o.equals(x.data)) {
					return index;
				}
			}
		} else {
			for (Node<E> x = first; x != null; x = x.next, index++) {
				if (o == x.data) {
					return index;
				}
			}
		}
		return -1;
	}

	@Override
	public int lastIndexOf(Object o) {
		int index = size - 1;
		if (o != null) {
			for (Node<E> x = last; x != null; x = x.prev, index--) {
				if (o.equals(x.data)) {
					return index;
				}
			}
		} else {
			for (Node<E> x = last; x != null; x = x.prev, index--) {
				if (o == x.data) {
					return index;
				}
			}
		}
		return -1;
	}

	@Override
	public E remove(int index) {
		Node<E> node = getNodeByIndex(index);
		return unlink(node);
	}

	private E unlink(Node<E> node) {
		E victim = node.data;
		Node<E> prev = node.prev;
		Node<E> next = node.next;
		if (prev != null) {
			prev.next = next;
			node.prev = null;
		} else {
			first = next;
		}
		if (next != null) {
			next.prev = prev;
			node.next = null;
		} else {
			last = prev;
		}
		node.data = null;
		size--;
		return victim;
	}

	@Override
	public E set(int index, E element) {
		Node<E> node = getNodeByIndex(index);
		E victim = node.data;
		node.data = element;
		return victim;
	}
	
	private static class Node<E> {
		Node<E> prev;
		E data;
		Node<E> next;

		public Node(Node<E> prev, E data, Node<E> next) {
			this.prev = prev;
			this.data = data;
			this.next = next;
		}
	}


}
