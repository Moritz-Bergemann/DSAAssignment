/*DSA Linked List Test Harness by Moritz Bergemann
 * CDate: 12/08/2019
 */
import java.util.*;

import static java.lang.System.load;
import static java.lang.System.out;
public class UnitTestDSALinkedList
{
    public static void main(String[] args)
    {
        //Creating array of linked lists to perform tests on 
        DSALinkedList[] lists = new DSALinkedList[2];
        
        //Constructors
        out.println("Constructing linked lists...:");
        lists[0] = new DSALinkedList(); //Will have stuff added
        lists[1] = new DSALinkedList(); //Will remain empty
        out.println();
        
        //Adding elements
        out.println("Adding 10 items to end of initally empty linked list:");
        for (int ii = 1; ii <= 10; ii++)
        {
            out.println(ii);
            lists[0].insertLast(Integer.toString(ii));
        }
        out.println();

        out.println("Adding 10 items to front of linked list with 10 items in it:");
        for (int ii = -1; ii >= -10; ii--)
        {
            out.println(ii);
            lists[0].insertFirst(Integer.toString(ii));
        }
        out.println();
        
        //Viewing Elements
        out.println("Peeking at last element in 20-item list: " + lists[0].peekLast());
        out.println("Peeking at first element in 20-item list: " + lists[0].peekFirst());
        out.println();

        out.println("Attempting to peek at last element in empty list");
        try
        {
            out.print(lists[1].peekLast());
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println("Attempting to peek at first element in empty list");
        try
        {
            out.print(lists[1].peekFirst());
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println();
    
        //Removing Elements
        out.println("Removing last 5 elements from 20-item list:");
        for (int ii = 1; ii <= 5; ii++)
        {
            out.println(lists[0].removeLast());
        }
        out.println();
        
        out.println("Removing first 5 elements from 20-item list:");
        for (int ii = 1; ii <= 5; ii++)
        {
            out.println(lists[0].removeFirst());
        }
        out.println();
    
        out.println("Attempting to remove last element in empty list");
        try
        {
            out.print(lists[1].removeLast());
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println("Attempting to remove first element in empty list");
        try
        {
            out.print(lists[1].removeFirst());
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println();
        out.println("Removing element at position 4 (5th element) of now " +
                "10-item list:");
        out.println(lists[0].removeAt(4));
        out.println("Removing element at position 0 (1st element) of now " +
                "9-item list:");
        out.println(lists[0].removeAt(0));
        out.println("Removing element at position 7 (last element) of now " +
                "8-item list:");
        out.println(lists[0].removeAt(7));
        out.println("Attempting to remove element at position 10 (out of range)" +
                "of now 7-item list:");
        try
        {
            out.println(lists[0].removeAt(10));
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println();

        //isEmpty
        out.println("Checking if not empty list is empty: " + lists[0].isEmpty());
        out.println("Checking if empty list is empty: " + lists[1].isEmpty());
        out.println();

        //Iterators
        out.println("Creating 2 iterators for created linked lists:");
        Iterator[] iterators = new Iterator[3];

        iterators[0] = lists[0].iterator();
        iterators[1] = lists[1].iterator();
    
        out.println("Cycling through all entries in loaded list:");
        while (iterators[0].hasNext())
        {
            out.println(iterators[0].next());
        }
        out.println();

        out.println("Seeing if iterator for empty list has next element: " + iterators[1].hasNext());
        out.println("Attempting to get first element of empty list:  " + iterators[1].next());
        out.println("Attempting to get second element of empty list:  " + iterators[1].next());
        out.println();

        //Append
        out.println("Appending filled list to itself");
        lists[0].append(lists[0]);
        out.println();

        iterators[0] = lists[0].iterator();
        out.println("Cycling through all entries in loaded list with itself appended to back:");
        while (iterators[0].hasNext())
        {
            out.println(iterators[0].next());
        }
        out.println();

        out.println("Attempting to use remove function on iterator: ");
        try
        {
            iterators[0].remove();
            out.println("Succeeded (shouldn't have)");
        }
        catch (Exception e)
        {
            out.println("Exception caught: " + e.getMessage());
        }
        out.println();
        
        //Sort Ascending
        String[] reverseArr = {"H", "G", "F", "E", "D", "C", "B", "A"};
        String[] randomArr = {"D", "H", "A", "C", "B", "G", "E", "F"};
        String[] sortedArr = {"A", "B", "C", "D", "E", "F", "G", "H"};
        DSALinkedList reverseList = new DSALinkedList();
        DSALinkedList randomList = new DSALinkedList();
        DSALinkedList sortedList = new DSALinkedList();
        
        Iterator iter;
        
        out.println("Creating 3 lists to test sorting...");
        for (int ii = 0; ii < 8; ii++)
        {
            reverseList.insertLast(reverseArr[ii]);
            randomList.insertLast(randomArr[ii]);
            sortedList.insertLast(sortedArr[ii]);
        }
        out.print("Reverse List: ");
        iter = reverseList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
        out.print("Random List: ");
        iter = randomList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
        out.print("Sorted List: ");
        iter = sortedList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();

        out.println("Performing ascending sorts...");
        reverseList.sortAsc();
        randomList.sortAsc();
        sortedList.sortAsc();
        out.println();

        out.println("RESULTS:");
        out.print("Reverse List: ");
        iter = reverseList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
        out.print("Sorted List: ");
        iter = randomList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
        out.print("Sorted List: ");
        iter = sortedList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
        out.println();

        //Sort Descending
        reverseList = new DSALinkedList();
        randomList = new DSALinkedList();
        sortedList = new DSALinkedList();

        out.println("Creating 3 lists to test sorting...");
        for (int ii = 0; ii < 8; ii++)
        {
            reverseList.insertLast(reverseArr[ii]);
            randomList.insertLast(randomArr[ii]);
            sortedList.insertLast(sortedArr[ii]);
        }
        out.print("Reverse List: ");
        iter = reverseList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
        out.print("Random List: ");
        iter = randomList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
        out.print("Sorted List: ");
        iter = sortedList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();

        out.println("Performing descending sorts...");
        reverseList.sortDesc();
        randomList.sortDesc();
        sortedList.sortDesc();
        out.println();

        out.println("RESULTS:");
        out.print("Reverse List: ");
        iter = reverseList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
        out.print("Sorted List: ");
        iter = randomList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
        out.print("Sorted List: ");
        iter = sortedList.iterator();
        while (iter.hasNext()) { out.print(iter.next() + " "); }
        out.println();
    }
}
