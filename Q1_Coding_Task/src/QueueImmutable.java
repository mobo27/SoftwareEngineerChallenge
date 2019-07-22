
public final class QueueImmutable<T> implements Queue<T> {

	static class Node<T> {
		public T value;
		public Node<T> next, prev;

		public Node(T value) {
			this.value = value;
		}
	}

	public QueueImmutable() {

	}

	private Node<T> head, tail;

	@Override
	public Queue<T> enQueue(T t) {
		// TODO Auto-generated method stub
		Node<T> tempNode = new Node<T>(t);
		if (isEmpty()) {
			head = tempNode;
			tail = head;
		} else {
			tempNode.prev = tail;
			tail.next = tempNode;
			tail = tempNode;
		}
		return this;
	}

	@Override
	public Queue<T> deQueue() {
		if (!isEmpty()) {
			if (head.next != null) {
				head = head.next;
				head.prev = null;
			} else {
				head = null;
				tail = null;
			}
		}
		return this;
	}
	/*
	 * Extract the head of the queue
	 * @see Queue#head()
	 */
	@Override
	public T head() {
		if (head != null) {
			return head.value;
		} else {
			return null;
		}
	}
	/*
	 * Check if Queue is Empty
	 * If head and tail is null, the queue is empty
	 * 
	 * @see Queue#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return (head == null && tail == null);
	}

}
