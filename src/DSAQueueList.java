/* DSAQueueList by Moritz Bergemann
 * Purpose: Model class for implementing queue data 
 *  structure using a linked list instead of an array
 * Created Date: 16/08/2019
 */
import java.util.*;

public class DSAQueueList implements Iterable /*Does not extend DSAQueue as 
    there are too many differences for this to be useful*/
{
    //CLASS FIELDS
    private DSALinkedList queue; //Linked list for storing queue elements
    int count; //Stores number of elements currently in queue

    //CONSTRUCTORS
    //Default Constructor
    public DSAQueueList()
    {
        queue = new DSALinkedList();
        count = 0;
    }

    //MUTATORS
    /* MUTATOR enqueue
     * Adds the imported element to the end of the queue list
     */
    public void enqueue(Object value)
    {
        //Inserting new value at end of linked list (back of queue)
        queue.insertLast(value);
        count++;
    }

    /* MUTATOR dequeue
     * Returns element at front of queue & removes it from queue
     */
    public Object dequeue()
    {
        if (isEmpty())
        {
            throw new IllegalArgumentException("Queue is empty");
        }
        else
        {
            //Taking value from start of linked list (front of queue)
            Object value = queue.removeFirst();
            count--;

            return value;
        }
    }

    /* ACCESSOR peek
     * Returns value of current front element in queue
     */
    public Object peek()
    {
        return queue.peekFirst();
    }

    /*Returns whether queue currently empty
     */
    public boolean isEmpty()
    {
        return (count == 0);
    }

    /*Returns current size of queue
     */
    public int getCount()
    {
        return count;
    }
    
    /* Returns iteratror for this queue
     */
    public Iterator iterator()
    {
        //Get iterator from 'queue' linked list 
        return queue.iterator();
    }
}
