/* DSA Linked List by Moritz Bergemann
 * Model class for doubly-linked double-ended linked list
 * Created Date: 12/08/2019
 * REFERENCE: This class was adapted from my submission for DSA Prac 3
 */
import java.util.*;
import java.io.Serializable;

public class DSALinkedList implements Iterable, Serializable
{
    //CLASS FIELDS
    private DSAListNode head; //First node in linked list
    private DSAListNode tail; //Last node in linked list
    private int count; //Stores number of elements in linked list

    //INNER CLASSES
    /* Class DSAListNode
     * Model class for nodes within a linked list
     */
    private class DSAListNode implements Serializable
    {
        //CLASS FIELDS (Can be accessed from DSALinkedList since inner class)
        private Object value; //Value held by node 
        private DSAListNode next; //Next node in linked list
        private DSAListNode prev; //Previous node in linked list

        //CONSTRUCTORS
        /* Alternate Constructor
         */
        private DSAListNode(Object inValue)
        {
            value = inValue;
            //Next/Previous nodes set to null by default (can then be modified)
            next = null;
            prev = null;
        }
    }

    /* Class DSALinkedListIterator
     * Model Class for iterators for DSALinkedList
     */
    private class DSALinkedListIterator implements Iterator, Serializable
    {
        //CLASS FIELDS
        private DSAListNode iterNext; /*Cursor for iterator pointing to next
            node in list (not the one cursor is currently "at")*/

        //CONSTRUCTORS
        /* Alternate Constructor
         */
        public DSALinkedListIterator(DSALinkedList inList)
        {
            iterNext = inList.head; /*Sets cursor for next node to first node 
                in list (iterator actually "starts" before first node)*/
        }

        /* Returns whether next node for iterator exists
         */
        public boolean hasNext()
        {
            return (iterNext != null);
        }

        /* Moves iterator to next node (the one currently stored by iterNext) if
         *  it exists and returns its value
         */
        public Object next()
        {
            Object value; //Value of next node to be returned

            if (iterNext == null) /*If next node in list is null (meaning either
                "current" is end of list or list is empty*/
            {
                value = null; //Make return value null since next element
            }
            else
            {
                //Getting current value of cursor node
                value = iterNext.value;

                //Moving cursor to next node
                iterNext = iterNext.next;
            }

            return value;
        }

        /* Would be method for removing node currently at cursor though not
         *  implemented here
         */
        public void remove()
        {
            throw new UnsupportedOperationException("Not Supported");
        }
    }

    //CONSTRUCTORS
    /* Default Constructor
     */
    public DSALinkedList()
    {
        //Head/tail initially point to no node (empty list) 
        head = null;
        tail = null;
    }

    //MUTATORS
    /* Insert node as first in list
     */
    public void insertFirst(Object newValue)
    {
        //Creating new node with imported value
        DSAListNode newNode = new DSAListNode(newValue);

        if (isEmpty())
        {
            //Setting head/tail reference to new node
            head = newNode;
            tail = newNode;
            count = 0;
        }
        else
        {
            /*Setting new node's next to current head node & current head node's
                previous to new node*/
            newNode.next = head;
            head.prev = newNode;

            //Setting head reference to new node
            head = newNode;
        }

        count++;
    }

    /* Insert node as last in list
     */
    public void insertLast(Object newValue)
    {
        //Creating new node with imported value
        DSAListNode newNode = new DSAListNode(newValue);

        if (isEmpty())
        {
            //Setting head/tail reference to new node
            head = newNode;
            tail = newNode;
        }
        else
        {
            /*Setting current tail node's next to new node & new node's previous
                to current tail node*/
            tail.next = newNode;
            newNode.prev = tail;

            //Setting tail reference to new node
            tail = newNode;
        }

        count++;
    }

    /* Appends the contents of the imported linked list to the end of this
     *  linked list
     */
    public void append(DSALinkedList inList)
    {
        DSAListNode curNode = inList.head;

        /*Making count of imported list constant (in case attempting to append
            list to itself)*/
        int countInit = inList.count;
        for (int ii = 0; ii < countInit; ii++) /*For each element in imported
            list*/
        {
            //Adding value of current node in imported list to this list
            insertLast(curNode.value);
            curNode = curNode.next;
        }
    }

    /* Remove first node in list & return its value
     */
    public Object removeFirst()
    {
        Object nodeValue;

        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty");
        }
        else
        {
            //Getting value of head node to return
            nodeValue = head.value;

            /*Making head node current head node's next (null if was last
                node in list*/
            head = head.next;

            if (head == null) //If removing head node made list empty
            {
                tail = null;
            }
            else
            {
                //Making new head node's previous null to delink old head node
                head.prev = null;
            }
        }

        count--;
        return nodeValue;
    }

    /* Remove last node in list & return its value
     */
    public Object removeLast()
    {
        Object nodeValue;

        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty");
        }
        else
        {
            //Getting value of tail node to return
            nodeValue = tail.value;

            /*Making tail node current tail node's previous (null if tail was
                only element in list*/
            tail = tail.prev;

            if (tail == null) //If removing tail node made list empty
            {
                head = null;
            }
            else
            {
                //Making new tail node's next null to delink old tail node
                tail.next = null;
            }
        }

        count--;
        return nodeValue;
    }

    /* Removes node at imported position in linked list (starting at 0) &
     *  returns its value. Throws exception if position is larger than linked
     *  list size
     * MOSTLY TESTED
     */
    public Object removeAt(int position)
    {
        DSAListNode curNode;
        Object nodeValue;

        if (position < count && position >= 0) /*If position is within range of
            list*/
        {
            if (position == 0) //If removing first element
            {
                nodeValue = removeFirst();
            }
            else if (position == count - 1) //If removing last element
            {
                nodeValue = removeLast();
            }
            else
            {
                curNode = head;

                //Getting to node to remove
                for (int ii = 0; ii < position; ii++)
                {
                    curNode = curNode.next;
                }

                //Getting node's value to return
                nodeValue = curNode.value;

                /*Unlinking node (can be done in uniform way as known not to be
                    first or last*/
                curNode.prev.next = curNode.next;
                curNode.next.prev = curNode.prev;

                /*Decreasing count (ONLY for this remove as other removes
                    decrease the count themselves*/
                count--;
            }

        }
        else
        {
            throw new IllegalArgumentException("Imported position out of list" +
                    "range");
        }

        return nodeValue;
    }

    /* Sorts nodes in link list in ascending order based on comparable
     *  comparison of node values, algorithm used is effectively quicksort
     *  REFERENCE: Algorithm, recursive components and getMid are based on
     *      https://www.geeksforgeeks.org/merge-sort-for-linked-list/ (Retrieved
     *      2019)
     */
    public void sortAsc()
    {
        head = mergeSortRecAsc(head);

        if (head != null)
        {
            /*Setting tail to end of link chain of new head exported by sort (since
                sorting does not modify the tail)*/
            DSAListNode curNode = head;
            while (curNode.next != null)
            {
                curNode = curNode.next;
            }
            tail = curNode;
        }
        else
        {
            tail = null;
        }
    }

    /*Recursive method for descending merge sort. Splits input section by
     *  setting 'next' of imported node to null.
     */
    private DSAListNode mergeSortRecAsc(DSAListNode firstNode)
    {
        if (firstNode != null && firstNode.next != null) /*If list can still
            be split further*/
        {
            //Getting middle of list section for splitting
            DSAListNode midNode = getMid(firstNode);
            DSAListNode section2FirstNode = midNode.next;

            /*Splitting list (by setting next of first segment's last node to
                null*/
            midNode.next = null;

            //Recursively applying mergesort to created list segments
            firstNode = mergeSortRecAsc(firstNode);
            section2FirstNode = mergeSortRecAsc(section2FirstNode);

            /*Merging 2 segments (now sorted) together to form fully sorted
                segment*/
            firstNode = mergeAsc(firstNode, section2FirstNode);
        }

        return firstNode;
    }

    /* Merges 2 imported ListNode chains together in ascending order, returns
     *  first node of completed chain.
     */
    private DSAListNode mergeAsc(DSAListNode firstNodeLeft,
                                 DSAListNode firstNodeRight)
    {
        DSALinkedList temp = new DSALinkedList();
        DSAListNode curLeft = firstNodeLeft;
        DSAListNode curRight = firstNodeRight;

        while (curLeft != null && curRight != null)
        {
            if (((Comparable) curLeft.value).compareTo(
                    (Comparable) curRight.value) < 0) /*If current node of left
                    section has smaller value than current node of right
                    section*/
            {
                temp.insertLast(curLeft.value);
                curLeft = curLeft.next;
            }
            else //If current right is smaller
            {
                temp.insertLast(curRight.value);
                curRight = curRight.next;
            }
        }

        //Flushing rest of nodes into sorted segments
        while (curLeft != null)
        {
            temp.insertLast(curLeft.value);
            curLeft = curLeft.next;
        }
        while (curRight != null)
        {
            temp.insertLast(curRight.value);
            curRight = curRight.next;
        }

        return temp.head;
    }

    /* Returns node at middle of list (i.e. between imported list & end node
     *  which has null as its next)
     */
    private DSAListNode getMid(DSAListNode startNode)
    {
        DSAListNode singleStep = startNode;
        DSAListNode doubleStep = startNode;

        /*Stepping through linked list at single & double rate (singe rate
            will always be halfway as far through list as double step)*/
        while (doubleStep.next != null && doubleStep.next.next != null)
        {
            singleStep = singleStep.next;
            doubleStep = doubleStep.next.next;
        }

        /*Getting middle node as single step (since if double step reached end
            of list, single step should be halfway through list*/
        DSAListNode midNode = singleStep;

        return midNode;
    }

    /* Sorts nodes in link list in descending order based on comparable
     *  comparison of node values, algorithm used is effectively bubble sort
     *  REFERENCE: Algorithm, recursive components and getMid are based on
     *      https://www.geeksforgeeks.org/merge-sort-for-linked-list/ (Retrieved
     *      2019)
     */
    public void sortDesc()
    {
        head = mergeSortRecDesc(head);

        if (head != null)
        {
            /*Setting tail to end of link chain of new head exported by sort (since
                sorting does not modify the tail)*/
            DSAListNode curNode = head;
            while (curNode.next != null)
            {
                curNode = curNode.next;
            }
            tail = curNode;
        }
        else
        {
            tail = null;
        }
    }

    /*Recursive method for descending merge sort. Splits input section by
     *  setting 'next' of imported node to null.
     */
    private DSAListNode mergeSortRecDesc(DSAListNode firstNode)
    {
        if (firstNode != null && firstNode.next != null) /*If list can still
            be split further*/
        {
            //Getting middle of list section for splitting
            DSAListNode midNode = getMid(firstNode);
            DSAListNode section2FirstNode = midNode.next;

            /*Splitting list (by setting next of first segment's last node to
                null*/
            midNode.next = null;

            //Recursively applying mergesort to created list segments
            firstNode = mergeSortRecDesc(firstNode);
            section2FirstNode = mergeSortRecDesc(section2FirstNode);

            /*Merging 2 segments (now sorted) together to form fully sorted
                segment*/
            firstNode = mergeDesc(firstNode, section2FirstNode);
        }

        return firstNode;
    }

    /* Merges 2 imported ListNode chains together in ascending order, returns
     *  first node of completed chain.
     */
    private DSAListNode mergeDesc(DSAListNode firstNodeLeft,
                                  DSAListNode firstNodeRight)
    {
        DSALinkedList temp = new DSALinkedList();
        DSAListNode curLeft = firstNodeLeft;
        DSAListNode curRight = firstNodeRight;

        while (curLeft != null && curRight != null)
        {
            if (((Comparable) curLeft.value).compareTo(
                    (Comparable) curRight.value) >= 0) /*If current node of left
                    section has larger value than current node of right
                    section (Equal to to maintain reverse sort as stable as
                    left list's elements are then still added first)*/
            {
                temp.insertLast(curLeft.value);
                curLeft = curLeft.next;
            }
            else //If current right is larger
            {
                temp.insertLast(curRight.value);
                curRight = curRight.next;
            }
        }

        //Flushing rest of nodes into sorted segments
        while (curLeft != null)
        {
            temp.insertLast(curLeft.value);
            curLeft = curLeft.next;
        }
        while (curRight != null)
        {
            temp.insertLast(curRight.value);
            curRight = curRight.next;
        }

        return temp.head;
    }

    //ACCESSORS
    /* Returns an iterator for this list
     */
    public Iterator iterator()
    {
        //Creating & returning iterator for this linked list
        return new DSALinkedListIterator(this);
    }

    /*Returns number of items currently in list
     */
    public int getCount()
    {
        return count;
    }

    /* Returns whether list is currently empty
     */
    public boolean isEmpty()
    {
        return (head == null);
    }

    /* Returns value of first node in list
     */
    public Object peekFirst()
    {
        Object nodeValue;

        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty");
        }
        else
        {
            //Getting value of head node to return
            nodeValue = head.value;
        }

        return nodeValue;
    }

    /* Returns value of last node in list
     */
    public Object peekLast()
    {
        Object nodeValue;

        if (isEmpty())
        {
            throw new IllegalArgumentException("List is empty");
        }
        else
        {
            //Getting value of tail node to return
            nodeValue = tail.value;
        }

        return nodeValue;
    }
}
