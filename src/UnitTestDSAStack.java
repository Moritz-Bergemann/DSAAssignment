/* DSAStack test harness by Moritz Bergemann
 * Created Date: 16/08/2019
 * REFERENCE: This class was adapted from my submission for DSA Prac 3
 */
import java.util.*;
import static java.lang.System.out;
public class UnitTestDSAStack
{
    public static void main(String[] args)
    {
        //Declaring array of stacks 
        DSAStack[] stacks = new DSAStack[3];
        
        //Creating stacks
        out.println("Creating 3 Stacks");
        stacks[0] = new DSAStack();
        stacks[1] = new DSAStack();
        stacks[2] = new DSAStack();
        out.println();
    

        //Adding to stacks
        out.println("Adding strings to stack (size 10) until full (also pushing 10 strings to stack of size 100)");
        for (int ii = 1; ii <= 10; ii++)
        {
            out.println(ii);
            stacks[2].push(Integer.toString(ii));
            stacks[0].push(Integer.toString(ii));
        }
        out.println();

        //Accessors
        out.println("Testing Accessors:");
        out.println("Reading value of top from filled stack: " + stacks[2].top());
        out.println("Reading value of top from empty stack (should throw exception): ");
        try
        {
            out.println(stacks[1].top());
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println();

        out.println("Asking if full stack is empty: " + stacks[2].isEmpty());
        out.println("Asking if partially filled stack is empty: " + stacks[0].isEmpty());
        out.println();

        out.println("Getting size of stack with 10 elements added: " + stacks[2].getCount());
        out.println("Getting size of empty size 1 stack: " + stacks[1].getCount());
        out.println();

        //Iterators
        out.println("Creating iterator for filled stack: ");
        Iterator sIterator = stacks[2].iterator();
        out.println("Going through all elements in stack using created iterator:");
        while (sIterator.hasNext())
        {
            out.println(sIterator.next());
        }
        out.println();

        //Pop
        out.println("Taking all elements from filled stack:");
        for (int ii = 0; ii < 10; ii++)
        {
            out.println(stacks[2].pop());
        }
        out.println();

        out.println("Attempting to take element from empty stack (should throw exception):");
        try
        {
            out.println(stacks[1].pop());
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println();
    }
}
