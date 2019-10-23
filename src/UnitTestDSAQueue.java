/* DSAQueueList test harness by Moritz Bergemann
 * Created date: 16/8/2019
 * REFERENCE: This class was adapted from my submission for DSA Prac 3
 */

import java.util.*;
import static java.lang.System.out;
public class UnitTestDSAQueue
{
    public static void main(String[] args)
    {
        //Declaring array of queues 
        DSAQueue[] queues = new DSAQueue[3];
        
        out.println("Creating 3 queues");
        queues[0] = new DSAQueue();
        queues[1] = new DSAQueue();
        queues[2] = new DSAQueue();

        //Enqueue
        out.println("Adding strings to queue (size 10) until (also adding 10 strings to queue of size 100)");
        for (int ii = 1; ii <= 10; ii++)
        {
            out.println(ii);
            queues[2].enqueue(Integer.toString(ii));
            queues[0].enqueue(Integer.toString(ii));
        }
        out.println();

        //Accessors
        out.println("Testing Accessors:");
        out.println("Reading value at front of full queue: " + queues[2].peek());
        out.println("Reading value at front of partially full queue: " + queues[0].peek());
        out.println("Reading value at front of empty queue (should throw exception): ");
        try
        {
            out.println(queues[1].peek());
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println();

        out.println("Asking if full queue is empty: " + queues[2].isEmpty());
        out.println("Asking if empty queue is empty: " + queues[1].isEmpty());
        out.println("Asking if partially filled queue is empty: " + queues[0].isEmpty());
        out.println();

        out.println("Getting size of full size 10 queue: " + queues[2].getCount());
        out.println("Getting size of partially filled (10) size 100 queue: " + queues[0].getCount());
        out.println("Getting size of empty size 1 queue: " + queues[1].getCount());
        out.println();

        //Iterators
        out.println("Creating iterator for filled queue");
        Iterator qIterator = queues[2].iterator();
        out.println("Going through all elements in queue using created iterator:");
        while (qIterator.hasNext())
        {
            out.println(qIterator.next());
        }
        out.println();

        //Dequeue
        out.println("Taking all elements from filled queue:");
        for (int ii = 0; ii < 10; ii++)
        {
            out.println(queues[2].dequeue());
        }
        out.println();

        out.println("Attempting to take element from empty queue (should throw exception):");
        try
        {
            out.println(queues[1].dequeue());
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println();
    }
}
