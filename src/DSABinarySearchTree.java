/* DSA Binary Search Tree by Moritz Bergemann
 * Model class for binary search trees containing functionality for viewing
 *  & editing contained data
 * Created DAte: 19/08/2019
 * REFERENCE: This class was adapted from my submission for DSA Prac 4
 */

import java.io.Serializable;
import java.util.Iterator;

public class DSABinarySearchTree implements Serializable, Iterable
{
    //CLASS FIELDS
    private DSATreeNode root; //Root node of this binary tree
    private int count; //Tracks number of nodes in tree

    //INNER CLASSES
    /* Class for defining properties of node in binary search tree, contain
     *  stored values & links to child nodes
     */
    private class DSATreeNode implements Serializable
    {
        //CLASS FIELDS
        private String key; //Unique key of list node used to identify it
        private Object value; //Value stored by list node
        private DSATreeNode left; //Left child node (lower key)
        private DSATreeNode right; //Right tree node (higher key)

        //CONSTRUCTORS
        //Alternate Constructor (input node's value & key)
        private DSATreeNode(String inKey, Object inValue)
        {
            key = inKey;
            value = inValue;
            left = null;
            right = null;
        }
    }

    /* Iterator class for binary search tree (performs in-list iteration of
     *  tree). Iterator does NOT update with changes made to tree.
     */
    private class DSABinarySearchTreeIterator implements Iterator
    {
        //CLASS FIELDS
        private DSAQueue iterQueue; /*Stores queue of all elements in tree in
            post-order*/

        //CONSTRUCTORS
        /* Alternate Constructor
         */
        public DSABinarySearchTreeIterator(DSABinarySearchTree inTree)
        {
            //Getting post-list traversal of tree
            iterQueue = new DSAQueue();

            if (root != null)
            {
                //Recursively filling queue with tree's nodes
                getInListRec(iterQueue, inTree.root);
            }
        }

        /* Returns whether next node for iterator exists
         */
        public boolean hasNext()
        {
            return (!iterQueue.isEmpty());
        }

        /* Moves iterator to next node (the one currently stored by iterNext) if
         *  it exists and returns its value
         */
        public Object next()
        {
            Object nextVal = null;

            if (!iterQueue.isEmpty()) /*If tree traversal has not completed*/
            {
                //Getting value object of next node in queue
                nextVal = ((DSATreeNode) iterQueue.dequeue()).value;
            }

            return nextVal;
        }

        /* Would be method for removing node currently at cursor though not
         *  implemented here
         */
        public void remove()
        {
            throw new UnsupportedOperationException("Not Supported");
        }

        /* Recursively traverses tree using in-list traversal and returns result
         *  as queue of tree nodes for use in iterator.
         */
        private void getInListRec(DSAQueue treeQueue, DSATreeNode currentNode)
        {
            //Applying algorithm to left-child tree (if exists)
            if (currentNode.left != null)
            {
                getInListRec(treeQueue, currentNode.left);
            }

            //Adding current node to output queue
            treeQueue.enqueue(currentNode);

            //Applying algorithm to right-child tree (if exists)
            if (currentNode.right != null)
            {
                getInListRec(treeQueue, currentNode.right);
            }
        }
    }

    //CONSTRUCTORS
    //Default Constructor
    public DSABinarySearchTree()
    {
        root = null;
        count = 0;
    }

    //MUTATORS
    /* Inserts a new node into the binary tree at the correct location based
     *  on it's imported key. Throws exception if node with key found in tree
     */
    public void insert(String newKey, Object newValue)
    {
        //Starting recursive insert at root node
        root = insertRec(newKey, newValue, root);
        count++;
    }

    /* Recursive method for inserting new node into tree, inserts node once
     *  reaches end of tree in sequence corresponding to new node's key,
     *  otherwise checks child nodes based on key comparison
     */
    private DSATreeNode insertRec(String newKey, Object newValue, DSATreeNode
            currentNode)
    {
        DSATreeNode updateNode = currentNode; /*Value that this node will be 
            updated to (as referenced by its parent node), same as current node
            by default (only changes if current node null, then new inserted)*/
        if (currentNode == null) //If end of path reached
        {
            DSATreeNode newNode = new DSATreeNode(newKey, newValue);
            updateNode = newNode;
        }
        else if (newKey.equals(currentNode.key)) //If new key already in tree
        {
            throw new IllegalArgumentException("Key '" + newKey +
                    "' already in tree");
        }
        else if (newKey.compareTo(currentNode.key) < 0) /*If new key smaller
            than current node's key*/
        {
            /*Setting current node's left to result of next recursion (in case
                left child is null and is where new node should be inserted)*/
            currentNode.left = insertRec(newKey, newValue, currentNode.left);
        }
        else //If new key larger than current node's key 
        {
            //Setting current node's right child to result of next recursion
            currentNode.right = insertRec(newKey, newValue, currentNode.right);
        }

        //Updating value of this node as referenced by parent node 
        return updateNode;
    }

    /* Deletes node with imported key from tree if found (& moves connections
     *  of remaining nodes to retain tree integrity), throws exception if
     *  not found
     */
    public void delete(String deleteKey)
    {
        root = deleteRec(deleteKey, root);
        count--;
    }

    /* Recursively searches for node with given key & calls method to delete it
     * if found, throws exception if not found
     */
    private DSATreeNode deleteRec(String deleteKey, DSATreeNode currentNode)
    {
        DSATreeNode updateNode = currentNode; /*Value the node from which this 
            instance of method was called from should be set to (current node 
            by default if no changes to be made)*/

        if (currentNode == null) //If node with imported key not found
        {
            throw new IllegalArgumentException("Key '" + deleteKey +
                    "'not found");
        }
        else if (currentNode.key.equals(deleteKey)) /*If current node is to be 
            deleted*/
        {
            //Calling method to delete node based on child nodes
            updateNode = deleteNode(deleteKey, currentNode);
        }
        else if (deleteKey.compareTo(currentNode.key) < 0) /*If key to be 
            deleted is smaller than current node's key*/
        {
            currentNode.left = deleteRec(deleteKey, currentNode.left);
        }
        else if (deleteKey.compareTo(currentNode.key) > 0) /*If key to be 
            deleted is larger than current node's key*/
        {
            currentNode.right = deleteRec(deleteKey, currentNode.right);
        }

        return updateNode;
    }

    /* Deletes specified node from tree, rearranging other node connections
     *  to ensure no nodes lost & key rules maintained
     */
    private DSATreeNode deleteNode(String deleteKey, DSATreeNode delNode)
    {
        DSATreeNode updateNode; /*Value node position held by node to be
            deleted will be set to*/

        if (delNode.left == null && delNode.right == null) //If has no children
        {
            //Only setting node to null as no child nodes will be lost
            updateNode = null;
        }
        else if (delNode.left != null && delNode.right == null) /*If only has 
            left child*/
        {
            /*Replacing deleted node with left child as child must be smaller
                than parent of deleted node (due to insertion rules*/
            updateNode = delNode.left;
        }
        else if (delNode.left == null && delNode.right != null) /*If only has 
            right child*/
        {
            /*Replacing deleted node with rogjt child as child must be smaller
                than parent of deleted node (due to insertion rules) even though
                is larger than deleted node itself*/
            updateNode = delNode.right;
        }
        else //If has both children
        {
            /*Getting successor node that will maintain proper relationships
                from right sub-tree*/
            updateNode = promoteSuccessor(delNode.right);
            if (updateNode != delNode.right) /*If successor ended up not being
                node to right of node to be deleted*/
            {
                /*Making successor node's right child same as deleted node's 
                    right child*/
                updateNode.right = delNode.right;
            }
            //Making successor node's left child same as deleted node's
            updateNode.left = delNode.left;
        }

        return updateNode;
    }

    /* Recursively gets node that can logically replace node to be deleted
     *  (leftmost node of right subtree as this must be smaller than all other
     *  right subtree nodes & bigger than all left subtree nodes) & removes that
     *  node from its previous position*/
    private DSATreeNode promoteSuccessor(DSATreeNode currentNode)
    {
        DSATreeNode successor = currentNode;

        if (currentNode.left != null)
        {
            successor = promoteSuccessor(currentNode.left);
            if (successor == currentNode.left) /*If successor ended up being
                left child of current node*/
            {
                /*Changing current node's left child from successor node to
                    successor's right child (automatically null if successor
                    doesn't have right child) so it can be removed*/
                currentNode.left = successor.right;
            }
        }
        return successor;
    }

    //ACCESSORS
    /* Returns value held by node with imported key (throws exception if node
     *  does not exist)
     */
    public Object find(String searchKey)
    {
        //Starting recursive search on root node
        return findRec(searchKey, root);
    }

    /* Recursive method for searching binary tree by key, checks imported node
     *  & searches one of child nodes based on key comparison
     */
    private Object findRec(String searchKey, DSATreeNode currentNode)
    {
        Object foundValue; //Value stored by node being searched for once found

        if (currentNode == null) /*If end of tree reached without finding node
            with the given key*/
        {
            throw new IllegalArgumentException("Key '" + searchKey +
                    "' not found");
        }
        else if (searchKey.equals(currentNode.key)) /*If current node is the 
            node being searched for*/
        {
            foundValue = currentNode.value;
        }
        else if (searchKey.compareTo(currentNode.key) < 0) /*If search key 
            smaller than current node's key*/
        {
            //Continuing search on left child
            foundValue = findRec(searchKey, currentNode.left);
        }
        else //If search key larger than current node's key 
        {
            //Continuing search on right child
            foundValue = findRec(searchKey, currentNode.right);
        }

        return foundValue;
    }

    /* Returns true if tree has node with imported key, throws exception
     *  otherwise
     */
    public boolean has(String inKey) //FIXME is this dodgy?
    {
        return hasRec(inKey, root);
    }

    /* Recursive method for determining whether tree has node with imported key
     *  checks current node & then its children & returns boolean of whether
     *  found
     */
    private boolean hasRec(String searchKey, DSATreeNode currentNode)
    {
        boolean found; //Whether or not node found in tree

        if (currentNode == null) /*If end of tree reached without finding node
            with the given key*/
        {
            found = false;
        }
        else if (searchKey.equals(currentNode.key)) /*If current node is the
            node being searched for*/
        {
            found = true;
        }
        else if (searchKey.compareTo(currentNode.key) < 0) /*If search key
            smaller than current node's key*/
        {
            //Continuing search on left child
            found = hasRec(searchKey, currentNode.left);
        }
        else //If search key larger than current node's key
        {
            //Continuing search on right child
            found = hasRec(searchKey, currentNode.right);
        }

        return found;
    }

    /* Returns whether tree currently empty or not
     */
    public boolean isEmpty()
    {
        return root == null;
    }

    /* Returns number of nodes currently in tree
     */
    public int getCount()
    {
        return count;
    }

    public Iterator iterator()
    {
        //Creating & returning new iterator for this tree
        return new DSABinarySearchTreeIterator(this);
    }

    /* Returns all nodes in the list as a queue of strings using in-list
     * traversal
     */
    public DSAQueue traverseInList()
    {
        DSAQueue treeQueue = new DSAQueue(); /*Queue storing all accessed 
            nodes in order (pass by reference)*/

        //Starting recursive accessing of tree nodes
        inListRec(treeQueue, root);

        return treeQueue;
    }

    /* Recursive algorithm for in-list traversal. Recursively accesses
     *  left-child tree, then current, then right-child tree
     */
    private void inListRec(DSAQueue treeQueue, DSATreeNode currentNode)
    {
        //Applying algorithm to left-child tree (if exists)
        if (currentNode.left != null)
        {
            inListRec(treeQueue, currentNode.left);
        }

        //Reading current node as a string (key, value)
        treeQueue.enqueue(currentNode.key + "," + currentNode.value);

        //Applying algorithm to right-child tree (if exists)
        if (currentNode.right != null)
        {
            inListRec(treeQueue, currentNode.right);
        }
    }

    /* Returns all nodes in list as queue of strings using pre-order
     * (children-first) traversal
     */
    public DSAQueue traversePreList()
    {
        DSAQueue treeQueue = new DSAQueue(); /*Queue storing all accessed 
            nodes in order (pass by reference)*/

        //Starting recursive accessing of tree nodes
        preListRec(treeQueue, root);

        return treeQueue;
    }

    /* Recursive algorithm for pre-list traversal. Recursively accesses
     *  current, then left-child tree, then right-child tree
     */
    private void preListRec(DSAQueue treeQueue, DSATreeNode currentNode)
    {
        //Reading current node as a string (key, value)
        treeQueue.enqueue(currentNode.key + "," + currentNode.value);

        //Applying algorithm to left-child tree (if exists)
        if (currentNode.left != null)
        {
            preListRec(treeQueue, currentNode.left);
        }

        //Applying algorithm to left-child tree (if exists)
        if (currentNode.right != null)
        {
            preListRec(treeQueue, currentNode.right);
        }
    }

    /* Returns all nodes in list as queue of strings using post-order
     * (parents-first) traversal
     */
    public DSAQueue traversePostList()
    {
        DSAQueue treeQueue = new DSAQueue(); /*Queue storing all accessed 
            nodes in order (pass by reference)*/

        //Starting recursive accessing of tree nodes
        postListRec(treeQueue, root);

        return treeQueue;
    }

    /* Recursive algorithm for post-list traversal. Recursively accesses
     *  left-child tree, right-child tree, then current
     */
    private void postListRec(DSAQueue treeQueue, DSATreeNode currentNode)
    {
        //Applying algorithm to left-child tree (if exists)
        if (currentNode.left != null)
        {
            postListRec(treeQueue, currentNode.left);
        }

        //Applying algorithm to left-child tree (if exists)
        if (currentNode.right != null)
        {
            postListRec(treeQueue, currentNode.right);
        }

        //Reading current node as a string (key, value)
        treeQueue.enqueue(currentNode.key + "," + currentNode.value);
    }

    /* Displays binary tree by showing all nodes in order of their keys
     */
    public void display()
    {
        if (root == null) //If tree is empty
        {
            throw new IllegalArgumentException("Tree is empty");
        }

        //Getting list of nodes in in-list order
        DSAQueue treeQueue = traverseInList();

        //Printing out key & toString of value held by each node 
        while (!treeQueue.isEmpty())
        {
            System.out.println(treeQueue.dequeue());
        }
    }

    /* Returns value of smallest key in tree
     */
    public String min()
    {
        if (root == null) //If tree is empty
        {
            throw new IllegalArgumentException("Tree is empty");
        }

        return minRec(root);
    }

    /* Recursive method for finding smallest key in tree
     */
    private String minRec(DSATreeNode currentNode)
    {
        String minKey; //Key to be returned as largest key

        if (currentNode.left == null) /*If left child is null (meaning this is
            the smallest node in the tree*/
        {
            minKey = currentNode.key;
        }
        else //If there is still a left node (which will have a smaller key)
        {
            //Continue recursive search
            minKey = minRec(currentNode.left);
        }

        return minKey;
    }

    /* Returns value of largest key in tree
     */
    public String max()
    {
        if (root == null) //If tree is empty
        {
            throw new IllegalArgumentException("Tree is empty");
        }

        return maxRec(root);
    }

    /* Recursive method for finding largest key in tree
     */
    private String maxRec(DSATreeNode currentNode)
    {
        String maxKey; //Key to be returned as largest key

        if (currentNode.right == null) /*If right child is null (meaning this is
            the largest node in the tree*/
        {
            maxKey = currentNode.key;
        }
        else //If there is still a right node (which will have a larger key)
        {
            //Continue recursive search
            maxKey = maxRec(currentNode.right);
        }

        return maxKey;
    }

    /*Returns height (number of levels) in binary tree
     */
    public int height()
    {
        return heightRec(root);
    }

    /*Recursively determines height of binary tree
     */
    private int heightRec(DSATreeNode currentNode)
    {
        int heightSoFar, leftHeight, rightHeight;

        if (currentNode == null) //If no more nodes along this path 
        {
            heightSoFar = -1; //NOTE: Why -1 & not 0?
        }
        else
        {
            //Calculate height of left & right branches
            leftHeight = heightRec(currentNode.left);
            rightHeight = heightRec(currentNode.right);
            
            /*Make current height height of tallest sub-branch + 1 for current 
                node*/
            if (leftHeight > rightHeight)
            {
                heightSoFar = leftHeight + 1;
            }
            else
            {
                heightSoFar = rightHeight + 1;
            }
        }

        return heightSoFar;
    }

    /*Returns number of nodes currently in tree
     */
    public int count()
    {
        int totalCount = 0;

        if (root != null)
        {
            totalCount = countRec(0, root);
        }

        return totalCount;

    }

    private int countRec(int countSoFar, DSATreeNode currentNode)
    {
        if (currentNode != null) /*If current node exists (in case method is 
            called on to null node*/
        {
            /*Adding count of nodes in left/right sub-trees (if exist) to total
                count*/
            if (currentNode.left != null)
            {
                countSoFar = countRec(countSoFar, currentNode.left);
            }
            if (currentNode.right != null)
            {
                countSoFar = countRec(countSoFar, currentNode.right);
            }

            //Adding 1 to total count for current node
            countSoFar++;
        }

        return countSoFar;
    }

    /*Returns percentage score for how balanced the tree is (i.e. percentage
     * ratio between left & right branches of root)
     */
    public double balance()
    {
        if (root == null) //If tree is empty
        {
            throw new IllegalArgumentException("Tree is empty");
        }

        int leftCount, rightCount; /*Store number of nodes in left/right 
            sub-branches of root*/

        leftCount = countRec(0, root.left);
        rightCount = countRec(0, root.right);

        double balancePercent; //Balance level of tree as percentage ratio

        //Calculating balance ratio (smaller side/larger side)
        if (leftCount == 0 && rightCount == 0) /*If both sides 0 (technically
            balanced since no imbalance exists*/
        {
            balancePercent = 100.0;
        }
        else if (leftCount > rightCount) //If left count bigger
        {
            balancePercent = ((double) rightCount / (double) leftCount) * 100.0;
        }
        else //If right count bigger or if equal
        {
            balancePercent = ((double) leftCount / (double) rightCount) * 100.0;
        }

        return balancePercent;
    }
}
