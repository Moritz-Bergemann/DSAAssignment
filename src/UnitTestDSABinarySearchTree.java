/* Test harness for DSABinarySearchTree by Moritz Bergemann
 */

import java.util.Iterator;

import static java.lang.System.out;
public class UnitTestDSABinarySearchTree
{
    public static void main(String[] args)
    {
        //Constructing Trees
        out.println("Constructing trees 0 & 1to apply operations to...");
        DSABinarySearchTree[] trees = new DSABinarySearchTree[4];
        trees[0] = new DSABinarySearchTree();
        trees[1] = new DSABinarySearchTree();
        trees[2] = new DSABinarySearchTree();
        trees[3] = new DSABinarySearchTree();
     
        //Insert
        out.println("Adding series of 10 values to tree 0:");
        String[] keys1 = {"011", "030", "095", "001", "044", "019", "160", "155", "199", "006"};
        String[] values1 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int ii = 0; ii < 10; ii++)
        {
            out.println("adding key " + keys1[ii] + ", value " + values1[ii]);
            trees[0].insert(keys1[ii], values1[ii]);
        }
        out.println();
        out.println("Adding single value to tree 2:");
        out.println("adding key 111, value only_node");
        trees[2].insert("111", "only_node");
        out.println();
        out.println("Adding series of 10 increasing values to tree 3:");
        String[] keys2 = {"001", "002", "003", "004", "005", "006", "007", "008", "009", "010"};
        String[] values2 = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        for (int ii = 0; ii < 10; ii++)
        {
            out.println("adding key " + keys2[ii] + ", value " + values2[ii]);
            trees[3].insert(keys2[ii], values2[ii]);
        }
        out.println();

        //Find
        out.println("Searching for value of tree 0 node with key '044' (Should give 'E'): " 
            + trees[0].find("044"));
        out.println("Searching for value of tree 0 node with key '199' (Should give 'I'): " 
            + trees[0].find("199"));
        out.println("Searching for value of tree 0 node with key '011' (Should give 'A'): " 
            + trees[0].find("011"));
        out.println("Searching for value of tree 0 node with key '006' (Should give 'J'): " 
            + trees[0].find("006"));
        out.println("Searching for value of non-existing tree 0 node with key '200' (Should throw exception:");
        try
        {
            out.println(trees[0].find("200"));
            out.println("Succeeded (shouldn't have)");
        out.println("");
        }
        catch (IllegalArgumentException i)
        {
            out.println("Exception Caught: " + i.getMessage());
        }
        out.println("Searching for value in empty tree 1 with key 'test' (should throw exception): ");
        try
        {
            out.println(trees[1].find("test"));
            out.println("Succeeded (shouldn't have)");
        out.println("");
        }
        catch (IllegalArgumentException i)
        {
            out.println("Exception Caught: " + i.getMessage());
        }
        out.println();

        //Has
        out.println("Checking if tree 0 has key '044' (should be true): " + trees[0].has("044"));
        out.println("Checking if tree 0 has key '111' (should be false): " + trees[0].has("111"));
        out.println("Checking if empty tree 1 has key '050' (should be false): " + trees[1].has("050"));

        out.println();

        //Delete
        out.println("Deleting node from tree 0 with key 019 (0 children)...");
        trees[0].delete("019");
        out.println("Deleting node from tree 0 with key 001 (1 child)...");
        trees[0].delete("001");
        out.println("Deleting node from tree 0 with key 011 (2 children)...");
        trees[0].delete("011"); //prev "095"
        out.println();

        //Display
        out.println("Calling display method of tree 0 (should return all nodes in order of key");
        trees[0].display();

        out.println("Attempting to display empty tree 1:");
        try
        {
            trees[1].display();
            out.println("Succeeded (shouldn't have)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("Exception Caught: " + i.getMessage());
        }
        out.println();

        //Traversal algorithms
        DSAQueue treeQueue;
        
        out.println("Returning values/keys of nodes ouput by each traversal algorithm for tree 0");
        out.println("In-List (same as 'display'):");
        treeQueue = trees[0].traverseInList();
        while (!treeQueue.isEmpty())
        {
            System.out.println(treeQueue.dequeue());
        }
        out.println();
        out.println("Pre-List:");
        treeQueue = trees[0].traversePreList();
        while (!treeQueue.isEmpty())
        {
            System.out.println(treeQueue.dequeue());
        }
        out.println();
        out.println("Post-List:");
        treeQueue = trees[0].traversePostList();
        while (!treeQueue.isEmpty())
        {
            System.out.println(treeQueue.dequeue());
        }
        out.println();

        //Other Accessors
        out.println("Getting value of minimum key in tree 0 (should be 006): " + trees[0].min());
        out.println("Getting value of maximum key in tree 0 (should be 199): " + trees[0].max());
        out.println("Getting value of minimum key in tree 3 (should be 001): " + trees[3].min());
        out.println("Getting value of maximum key in tree 3 (should be 010): " + trees[3].max());
        out.println("Getting height of tree 0 (should be 5?): " + trees[0].height());
        out.println("Getting height of tree 1 (should be 0?): " + trees[1].height());
        out.println("Getting height of tree 2 (should be 1?): " + trees[2].height());
        out.println("Getting height of tree 3 (should be 10?): " + trees[3].height());
        out.println("Getting count of nodes in tree 0 (should be 7): " + trees[0].count());
        out.println("Getting count of nodes in tree 1 (should be 0): " + trees[1].count());
        out.println("Getting count of nodes in tree 2 (should be 1): " + trees[2].count());
        out.println("Getting count of nodes in tree 3 (should be 10): " + trees[3].count());
        out.println("Getting balance of tree 0 (should be 20.0): " + trees[0].balance());
        out.println("Getting balance of tree 2 (should be 100): " + trees[2].balance());
        out.println("Getting balance of tree 3 (should be 0): " + trees[3].balance());
        out.println("Getting balance of tree 1 (should throw exception): ");
        try
        {
            out.println(trees[1].balance());
            out.println("Succeeded (shouldn't have)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("Exception Caught: " + i.getMessage());
        }
        out.println();


        //Iterator
        out.println("Creating iterator for filled binary search tree");
        out.println("Iterating through & displaying values of iterator (should be same values as in-list traversal):");
        Iterator treeIter = trees[0].iterator();
        while (treeIter.hasNext())
        {
            System.out.println((String) treeIter.next());
        }
    }
}
/*
        String[] values1 = {"", "", "", "", "", "", "", "", "", ""};
        
        out.println("");
        
        try
        {
            out.println("Succeeded (shouldn't have)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("Exception Caught: " + i.getMessage());
        }
    
*/
