/* DSAStackList by Moritz Bergemann
 * Purpose: Model class for stack data structure using a linked list
 * Created Date: 16/08/2019
 */
import java.util.*;

public class DSAStackList implements Iterable
{
    //CLASS FIELDS
    private DSALinkedList stack; //Linked list storing stack elements
    private int count; //Stores number of elements currently in list

    //CONSTRUCTORS
    //Default Constructor
    public DSAStackList()
    {
        stack = new DSALinkedList();
        count = 0;
    }

    //MUTATORS
    /* Adds the imported element to the top of the stack 
    */
    public void push(Object value)
    {
        stack.insertLast(value);
        count++;
    }

    /* Returns element at top of stack & removes it from stack
     */
    public Object pop()
    {
        //Getting value of last node in list that removeLast method returns
        Object value = stack.removeLast();
        
        count--;
        
        return value;
    }

    //ACCESSORS
    /* Returns current stack size
     */
    public int getCount()
    {
        return count;
    }

    /* Returns whether stack is currently empty
     */
    public boolean isEmpty()
    {
        return (count == 0);
    }

    /* Returns value of current top element in stack
     */
    public Object top()
    {
        if (isEmpty())
        {
            throw new IllegalArgumentException("Stack is empty");
        }
        else
        {
            return stack.peekLast();
        }
    }

    /* Returns iterator for this stack from the 'stack' linked list
     */
    public Iterator iterator()
    {
        return stack.iterator();
    }
}
