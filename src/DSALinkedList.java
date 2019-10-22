/* DSA Linked List by Moritz Bergemann
 * Model class for doubly-linked double-ended linked list
 * Created Date: 12/08/2019
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
        while (curNode != null) /*For each node in imported list*/
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

    //TODO change sorts to quicksort

    /* Sorts nodes in link list in ascending order based on comparable
     *  comparison of node values, algorithm used is effectively bubble sort
     */
    public void sortAsc()
    {
        DSAListNode currentNode;
        Object temp;
        boolean sorted = false;

        while (sorted == false)
        {
            sorted = true; /*Setting sorted to true until disproven if 2
                elements are out of order*/

            //Iterating through list
            currentNode = head;
            while (currentNode != null)
            {
                if (!(currentNode.value instanceof Comparable)) /*If the current
                    node's value doesn't implement the comparable interface*/
                {
                    throw new IllegalStateException("Node does not " +
                            "implement sortable");
                }
                if (currentNode.next != null) //If current node isn't last node
                {
                    /*If current node's value greater than next node's value
                        (using the comparable interface's compareTo function*/
                    if (((Comparable)currentNode.value).compareTo(
                            (Comparable)currentNode.next.value) > 0)
                    {
                        sorted = false;

                        //Swapping values of current & next node
                        temp = currentNode.value;
                        currentNode.value = currentNode.next.value;
                        currentNode.next.value = temp;
                    }
                }

                //Moving to next node
                currentNode = currentNode.next;
            }
        }
    }

    /* Sorts nodes in link list in descending order based on comparable
     *  comparison of node values, algorithm used is effectively bubble sort
     */
    public void sortDesc()
    {
        DSAListNode currentNode;
        Object temp;
        boolean sorted = false;

        while (sorted == false)
        {
            sorted = true; /*Setting sorted to true until disproven if 2
                elements are out of order*/

            //Iterating through list
            currentNode = head;
            while (currentNode != null)
            {
                if (!(currentNode.value instanceof Comparable)) /*If the current
                    node's value doesn't implement the comparable interface*/
                {
                    throw new IllegalStateException("Node does not " +
                            "implement sortable");
                }
                if (currentNode.next != null) //If current node isn't last node
                {
                    /*If current node's value greater than next node's value
                        (using the comparable interface's compareTo function*/
                    if (((Comparable)currentNode.value).compareTo(
                            (Comparable)currentNode.next.value) < 0)
                    {
                        sorted = false;

                        //Swapping values of current & next node
                        temp = currentNode.value;
                        currentNode.value = currentNode.next.value;
                        currentNode.next.value = temp;
                    }
                }

                //Moving to next node
                currentNode = currentNode.next;
            }
        }
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
