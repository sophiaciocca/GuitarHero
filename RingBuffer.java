
/*************************************************************************
  * Name         : Sophia Ciocca
  * PennKey      : sciocca
  * Recitation # : 211
  * Dependencies: given maximum capacity for the array, and 
  *                   values to queue in the ring buffer
  * 
  * RingBuffer models a ring buffer, taking an array and buffering 
  * it -- putting things in and taking them out, and keeping the 
  * boundaries of the ring buffer such that it keeps the array small,
  * with only the values that still remain there.
  *
  *****************************************************************************/

public class RingBuffer {
  private double[] rb;          // items in the buffer
  private int first;            // index for the next dequeue or peek
  private int last;             // index for the next enqueue
  private int size;             // number of items in the buffer
  
  // CONSTRUCTOR: create an empty buffer, with given max capacity
  public RingBuffer(int capacity) {
    first = 0;
    last = 0;
    size = 0;
    rb = new double[capacity];
  }
  
  // RingBuffer.size: return number of items currently in the buffer
  public int size() {
    return size;
  }
  
  // RingBuffer.isEmpty: returns whether or not the buffer is empty
  public boolean isEmpty() {
    return (size == 0);
  }
  
  // RingBuffer.isFull: returns whether or not the buffer is full 
  public boolean isFull() {
    return (size == rb.length);
  }
  
  // RingBuffer.enqueue: add item x to the end
  public void enqueue(double x) {
    
    // if buffer is full, give exception error
    if (isFull()) {
      throw new RuntimeException("Ring buffer overflow");
    }
    
    // put x in slot "last"
    rb[last] = x;
    
    // increment last
    last++;
    
    // increment size (of buffer)
    size++;
    
    // when last is at the very end (last = capacity), wrap it around, make it = 0
    if (last >= rb.length) {
      last = 0;
    }
  }
  
  // RingBuffer.dequeue: delete and return item from the front
  public double dequeue() {
    
    // if buffer is empty, give exception error 
    if (isEmpty()) {
      throw new RuntimeException("Ring buffer underflow");
    }
    
    // remove an item - take from "first"
    double tempValue = rb[first];
    
    // increment first
    first++;
    
    // when first is at the very end (first = capacity), wrap it around, make it = 0
    if (first >= rb.length) {
      first = 0;
    }
    
    // decrement size (of buffer)
    size--;
    
    // return the dequeued value from the front
    return tempValue;
  }
  
  // RingBuffer.peek: return (but do not delete) item from the front
  public double peek() {
    
    // if the buffer is empty, give exception error
    if (isEmpty()) {
      throw new RuntimeException("Ring buffer underflow");
    }
    
    //return item from the front (first)
    return rb[first];
  }
  
  // MAIN: a simple test of the constructor and methods in RingBuffer
  public static void main(String[] args) {
    int N = Integer.parseInt(args[0]);
    RingBuffer buffer = new RingBuffer(N);
    for (int i = 1; i <= N; i++) {
      buffer.enqueue(i);
    }
    double t = buffer.dequeue();
    buffer.enqueue(t);
    System.out.println("Size after wrap-around is " + buffer.size());
    while (buffer.size() >= 2) {
      double x = buffer.dequeue();
      double y = buffer.dequeue();
      buffer.enqueue(x + y);
    }
    System.out.println(buffer.peek());
  }
  
}
