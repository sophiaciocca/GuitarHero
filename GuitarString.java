
/*************************************************************************
 * Name         : Sophia Ciocca
 * PennKey      : sciocca
 * Recitation # : 211
 * Dependencies: RingBuffer.java, inputted frequency
 * 
 * Simulates a guitar string using a ring buffer and a given frequency from the user.
 * Plays A and C using the keyboard keys 'a' and 'c'.
 *
 *****************************************************************************/

public class GuitarString {

    //Instance Variables:
    private RingBuffer buffer; // ring buffer
    private double frequency;
    private double N;
    private int SAMPLING_RATE = 44100;
    private int counter;

   //CONSTRUCTOR #1 - make guitarstring from given frequency, sampling rate 44100
    public GuitarString(double frequency) {
        
        //set N = sampling rate (44100) / frequency
        N = SAMPLING_RATE / frequency;
        
        // round frequency UP to nearest integer
        int intN = (int) (Math.ceil(N));
        
        //create RingBuffer of capacity N
        buffer = new RingBuffer(intN);
        
        //enqueue N zeros (to represent guitar at rest)
        for (int i = 0; i < N; i++) {
            buffer.enqueue(0);
        }
    }
    
    //CONSTRUCTOR #2 - make guitar string w/ size & init values from array
    public GuitarString(double[] init) {
        
        // set capacity = length of array
        int length = init.length;
        
        //create RingBuffer of capacity length of array
        buffer = new RingBuffer(length);
        
        //initialize contents of buffer to values in array
        for (int j = 0; j < length; j++) {
            buffer.enqueue(init[j]);
        }       
    }
  
    // GuitarString.pluck(): replace all N items in the buffer with white noise
    public void pluck() {
       
        // for loop: go through all N items in buffer, 
           // replace with N random values between -0.5 & +0.5
        for (int k = 0; k < N; k++) {
            
            //dequeue 1
            buffer.dequeue();
        
            //enqueue a random value between -0.5 & +0.5
            buffer.enqueue(Math.random() - 0.5);     
        }      
    }
    
    // GuitarString.tic(): advance the simulation one time step
    public void tic() {
        
        //peek at first value, save as firstValue
        double firstValue = buffer.peek();
        
        // delete (dequeue) sample at front of ring buffer
        buffer.dequeue();
        
        //peek at new first value, save as secondValue
        double secondValue = buffer.peek();
        
        // calculate average of first two samples, multiplied by energy decay factor
        double calculation = 0.994 * (0.5 * (firstValue + secondValue));
        
        // enqueue that value at end
        buffer.enqueue(calculation);
        
        // advance tic counter
        counter++;
    }
    
    // GuitarString.sample(): return the current sample
    public double sample() {      
        return buffer.peek();
    }
    
    // return number of times tic was called
    public int time() {
        return counter;
    }
    
    // MAIN: for testing
    public static void main(String[] args) {
        int N = Integer.parseInt(args[0]);
        double[] samples = { .2, .4, .5, .3, -.2, .4, .3, .0, -.1, -.3 };  
        GuitarString testString = new GuitarString(samples);
        for (int i = 0; i < N; i++) {
            int t = testString.time();
            double sample = testString.sample();
            System.out.printf("%6d %8.4f\n", t, sample);
            testString.tic();
        }
    }

}