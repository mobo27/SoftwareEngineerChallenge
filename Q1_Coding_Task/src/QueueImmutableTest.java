import static org.junit.Assert.*;

import org.junit.Test;

public class QueueImmutableTest {

	/*
	 * Test Enqueue and cascade of enqueue
	 */
	@Test
	public <T> void enqueue_test1() {
		Queue<String> queue = new QueueImmutable<>();
		queue = queue.enQueue("1").enQueue("2").enQueue("3").enQueue("4");
		assertEquals(queue.head(), "1");
	}

	@Test
	public <T> void enqueue_test2() {
		Queue<String> queue = new QueueImmutable<>();
		queue = queue.enQueue("1");
		queue = queue.enQueue("2");
		queue = queue.enQueue("3");
		queue = queue.enQueue("4");
		assertEquals(queue.head(), "1");
	}
	
	/*
	 * Test Dequeue and check only middle
	 */
	@Test
	public <T> void dequeue_test1() {
		Queue<String> queue = new QueueImmutable<>();
		queue = queue.enQueue("1").enQueue("2").enQueue("3").enQueue("4");
		queue = queue.deQueue().deQueue();
		assertEquals(queue.head(), "3");
	}
	
	@Test
	public <T> void dequeue_test2() {
		Queue<String> queue = new QueueImmutable<>();
		queue = queue.enQueue("1");
		queue = queue.enQueue("2");
		queue = queue.enQueue("3");
		queue = queue.enQueue("4");
		queue = queue.deQueue();
		queue = queue.deQueue();
		assertEquals(queue.head(), "3");
	}
	
	/*
	 * Check the return of the empty queue
	 */
	@Test
	public <T> void empty_queue_test() {
		Queue<String> queue = new QueueImmutable<>();
		queue = queue.enQueue("1").enQueue("2").enQueue("3").enQueue("4");
		queue = queue.deQueue().deQueue().deQueue().deQueue();
		assertEquals(queue.head(), null);
	}
	
	/*
	 * Empty load test for queue.
	 */
	@Test
	public <T> void empty_load_test() {
		Queue<String> queue = new QueueImmutable<>();
		assertEquals(queue.head(), null);
	}
	
	/*
	 * Empty dequeue for empty queue
	 */
	@Test
	public <T> void empty_dequeue_test() {
		Queue<String> queue = new QueueImmutable<>();
		queue.deQueue();
		assertEquals(queue.head(), null);
	}
}
