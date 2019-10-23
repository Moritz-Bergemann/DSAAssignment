/* DSA Graph by Moritz Bergemann
 * Model class for graph data structure consisting of set of vertices linked
 *  together by (directional) edges
 * Created Date: 3/09/2019
 * Updated Date: 3/10/2019
 * REFERENCE: This class was adapted from my submission for DSA Prac 5
 */
import java.util.*;

public class DSAGraph
{
    //CLASS FIELDS
    protected DSABinarySearchTree vertices;

    //INNER CLASSES
    /* Model class for vertices within linked list
     * Implements sortable so that it can be sorted by its label fields
     *  in the DSALinkedList class
     */
    protected class DSAGraphVertex implements Comparable<DSAGraphVertex>
    {
        //CLASS FIELDS
        String label;
        Object value;
        DSABinarySearchTree adjacent; /*Stores links to vertices that this
            vertex is adjacent to*/
        boolean visited; //Stores whether vertex has been visited during search

        //CONSTRUCTORS
        /* Alternate constructor
         */
        public DSAGraphVertex(String inLabel, Object inValue)
        {
            label = inLabel;
            value = inValue;
            adjacent = new DSABinarySearchTree();
            visited = false;
        }

        //ACCESSORS
        /* Returns label (since this is what vertices should be sorted by
         */
        public String getSortValue()
        {
            return label;
        }

        /* Returns custom comparison result for comparing graph vertices with
         *  each other for sorting (i.e. the string label)
         */
        public int compareTo(DSAGraphVertex compVertex)
        {
            return label.compareTo(compVertex.label);
        }
    }

    //CONSTRUCTORS
    /* Default Constructor
     */
    public DSAGraph()
    {
        vertices = new DSABinarySearchTree();
    }

    //PUBLIC ACCESSORS
    /* Returns whether or not vertex with given label exists in graph (by 
     *  checking tree of all vertices
     */
    public boolean hasVertex(String inLabel)
    {
        return vertices.has(inLabel);
    }

    /* Returns whether the graph possess a directional edge going from label1
     *  to label2. Throws exception if one of the imported labels does not
     *  exist.
     */
    public boolean hasEdge(String inLabel1, String inLabel2)
    {
        DSAGraphVertex vertex1 = getVertex(inLabel1);
        getVertex(inLabel2);

        return vertex1.adjacent.has(inLabel2);
    }

    /* Returns whether graph is currently empty
     */
    public boolean isEmpty()
    {
        return vertices.isEmpty();
    }

    /* Returns number of vertices currently in graph
     */
    public int getVertexCount()
    {
        return vertices.getCount();
    }

    /* Returns number of edges currently in graph
     */
    public int getEdgeCount()
    {
        int edgeCount = 0; /*Total number of edges in graph (equivalent to total
            number of items in each vertex's adjacency list*/
        Iterator verticesIterator = vertices.iterator();
        DSAGraphVertex currentVertex, currentAdjacentVertex;

        //Iterating through each vertex in list of vertices
        while (verticesIterator.hasNext())
        {
            currentVertex = (DSAGraphVertex)verticesIterator.next();

            //Increasing edgeCount by number of vertices in adjacency list
            edgeCount += currentVertex.adjacent.getCount();
        }

        //Getting number of edges as number of adjacent vertices listed / 2
        return edgeCount;
    }

    /* Prints the graph's vertices to the console in adjacency list form
     */
    public void displayAsList()
    {
        Iterator verticesIterator = vertices.iterator();
        Iterator adjacentIterator;
        DSAGraphVertex currentVertex, currentAdjacentVertex;
        String vertexString; /*String containing label of current vertex & 
            labels of all adjacent vertices*/

        //Iterating through each vertex in vertex tree
        while (verticesIterator.hasNext())
        {
            vertexString = "";

            currentVertex = (DSAGraphVertex)verticesIterator.next();
            
            //Adding current vertex's label to vertex string
            vertexString += currentVertex.label + ": ";

            //Iterating through adjacency list of current vertex
            adjacentIterator = currentVertex.adjacent.iterator();
            while (adjacentIterator.hasNext())
            {
                currentAdjacentVertex = 
                    (DSAGraphVertex)adjacentIterator.next();
                
                //Adding label of current adjacent vertex to printed list
                vertexString += currentAdjacentVertex.label;

                /*Conditionally adding comma & space (if this wasn't last vertex
                    in adjacency list)*/
                if (adjacentIterator.hasNext())
                {
                    vertexString += ", ";
                }
            }

            //Printing out label + adjacency list of current vertex
            System.out.println(vertexString);
        }
    }

    /* Returns each of the graph's vertices with their adjacency lists as
        a linked list of strings
     */
    public DSALinkedList returnAsList()
    {
        //Creating linked list to return
        DSALinkedList graphList = new DSALinkedList();

        Iterator verticesIterator = vertices.iterator();
        Iterator adjacentIterator;
        DSAGraphVertex currentVertex, currentAdjacentVertex;
        String vertexString; /*String containing label of current vertex &
            labels of all adjacent vertices*/

        //Iterating through each vertex in vertex list
        while (verticesIterator.hasNext())
        {
            vertexString = "";

            currentVertex = (DSAGraphVertex)verticesIterator.next();

            //Adding current vertex's label to vertex string
            vertexString += currentVertex.label + ": ";

            //Iterating through adjacency list of current vertex
            adjacentIterator = currentVertex.adjacent.iterator();
            while (adjacentIterator.hasNext())
            {
                currentAdjacentVertex =
                        (DSAGraphVertex)adjacentIterator.next();

                //Adding label of current adjacent vertex to printed list
                vertexString += currentAdjacentVertex.label;

                /*Conditionally adding comma & space (if this wasn't last vertex
                    in adjacency list)*/
                if (adjacentIterator.hasNext())
                {
                    vertexString += ", ";
                }
            }

            //Adding label + adjacency list of current vertex to list to return
            graphList.insertLast(vertexString);
        }

        return graphList;
    }

    public void displayAsMatrix()
    {
        int vertexCount = getVertexCount();

        //Creating adjacency matrix (size n x n)
        int[][] adjacencyMatrix = new int[vertexCount][vertexCount];
        
        /*Creating/declaring iterators for vertex list for rows/columns
            (& for adjacency list)*/
        Iterator rowVertexIterator = vertices.iterator();
        Iterator columnVertexIterator;
        DSAGraphVertex rowVertex, columnVertex;

        //Filling adjacency matrix
        for (int ii = 0; ii < vertexCount; ii++) //For each row in matrix
        {
            /*Increasing row vertex list iterator & reinitialising column 
                vertex list iterator*/
            rowVertex = (DSAGraphVertex)rowVertexIterator.next();
            columnVertexIterator = vertices.iterator();

            //For each column in row
            for (int jj = 0; jj < vertexCount; jj++)
            {
                columnVertex = (DSAGraphVertex)columnVertexIterator.next();
                
                if (hasEdge(rowVertex.label, columnVertex.label)) /*If 2
                    current vertices are adjacent*/
                {
                    adjacencyMatrix[ii][jj] = 1;
                }
                else
                {
                    adjacencyMatrix[ii][jj] = 0;
                }
            }
        }
        
        Iterator verticesIterator = vertices.iterator();
        DSAGraphVertex currentVertex;

        //Printing out adjacency matrix to console (with row/column labels)
        //Printing out first line (column labels)
        String currentLine = "   "; //3 spaces (for row label)
        for (int ii = 0; ii < vertexCount; ii++)
        {
            currentVertex = (DSAGraphVertex)verticesIterator.next();
            currentLine += currentVertex.label; /*Headers will not be properly
                aligned with columns unless are of length 1 character, no easy
                solution*/
            if (ii < vertexCount - 1)
            {
                currentLine += " ";
            }
        }
        System.out.println(currentLine);
        
        //Resetting iterator for printing row labels
        verticesIterator = vertices.iterator();

        //Printing out each row of matrix as line
        for (int row = 0; row < vertexCount; row++)
        {
            currentVertex = (DSAGraphVertex)verticesIterator.next();
            currentLine = currentVertex.label + ": "; /*Starting current line 
                with row label*/
            
            for (int col = 0; col < vertexCount; col++)
            {
                currentLine += adjacencyMatrix[row][col];
                if (col < vertexCount - 1)
                {
                    currentLine += " ";
                }
            }
            System.out.println(currentLine);
        }
    }

    /* Performs depth-first traversal of graph & returns vertex result as 
     *  linked list of label strings, throws exception if graph is empty
     */
    public DSALinkedList depthFirstSearch()
    {
        DSAStack vertexStack = new DSAStack(); /*Stack for storing 
            vertices during search*/
        DSALinkedList traversalList = new DSALinkedList(); /*List containing 
            labels for all vertices in order of traversal*/

        Iterator adjListIterator;
        DSAGraphVertex currentAdjVertex, firstUnvisitedVertex;

        //Setting all vertices to unvisited
        setAllUnvisited();

        //Throwing exception if graph is empty
        if (isEmpty())
        {
            throw new IllegalArgumentException("Graph is empty");
        }

        /*Adding starting vertex (chosen as first in vertex list) to stack & 
            traversal list*/
        Iterator verticesIterator = vertices.iterator();
        DSAGraphVertex firstVertex = (DSAGraphVertex)vertices.iterator().next();
        vertexStack.push(firstVertex);
        traversalList.insertLast(((DSAGraphVertex)vertexStack.top()).label);
        ((DSAGraphVertex)vertexStack.top()).visited = true;

        while (!vertexStack.isEmpty())
        {
            firstUnvisitedVertex = null;

            /*Searching for first vertex in current top of stack's adjacency 
                list that has not yet been visited*/
            adjListIterator = 
                ((DSAGraphVertex)vertexStack.top()).adjacent.iterator();
            while (adjListIterator.hasNext() && firstUnvisitedVertex == null)
            {
                currentAdjVertex = (DSAGraphVertex)adjListIterator.next();
                
                if (currentAdjVertex.visited == false)
                {
                    firstUnvisitedVertex = currentAdjVertex;
                }
            }
            
            if (firstUnvisitedVertex != null) /*If an unvisited vertex was
                found*/
            {
                /*Making new unvisited vertex top of stack & setting it to 
                    visited*/
                vertexStack.push(firstUnvisitedVertex);
                firstUnvisitedVertex.visited = true;

                //Adding new unvisited vertex to traversal list
                traversalList.insertLast(firstUnvisitedVertex.label);
            }
            else //If there were no more unvisited vertices
            {
                //Removing vertex from stack
                vertexStack.pop();
            }
        }

        //Returning traversal list
        return traversalList;
    }

    /* Performs breadth-first traversal of graph & returns vertex result as
     *  linked list of label strings, throws exception if graph is empty
     */
    public DSALinkedList breadthFirstSearch()
    {
        DSAQueue vertexQueue = new DSAQueue(); /*Queue for storing
            vertices during search*/
        DSALinkedList traversalList = new DSALinkedList();
        
        Iterator adjListIterator;
        DSAGraphVertex currentAdjVertex;
        
        //Throwing exception if graph is empty
        if (isEmpty())
        {
            throw new IllegalArgumentException("Graph is empty");
        }

        //Setting all vertices to unvisited
        setAllUnvisited();

        //Adding starting vertex (chosen as first in vertex list) to queue
        DSAGraphVertex startVertex = (DSAGraphVertex)vertices.iterator().next();
        vertexQueue.enqueue(startVertex);
        startVertex.visited = true;

        while (!vertexQueue.isEmpty())
        {
            /*Adding all unvisited vertices adjacent to current front
                of queue to queue*/
            adjListIterator = 
                ((DSAGraphVertex)vertexQueue.peek()).adjacent.iterator();
            while (adjListIterator.hasNext())
            {
                currentAdjVertex = (DSAGraphVertex)adjListIterator.next();
                if (!currentAdjVertex.visited)
                {
                    vertexQueue.enqueue(currentAdjVertex);
                    
                    //Making added vertex visited
                    currentAdjVertex.visited = true;
                }
            }
            
            /*Removing current front of vertex from queue & adding it to 
                traversal list*/
            traversalList.insertLast((
                (DSAGraphVertex)vertexQueue.peek()).label);
            vertexQueue.dequeue();
        }

        //Returning value of found vertex
        return traversalList;
    }

    //PRIVATE ACCESSORS
    /* Returns a vertex in the graph (so that it may be modified within this
     *  class) by searching through the list of all vertices, throws exception
     *  if vertex not present in list
     */
    protected DSAGraphVertex getVertex(String inLabel)
    {
        DSAGraphVertex vertexToGet = null; //Vertex to return (null by default)

        try
        {
            vertexToGet = (DSAGraphVertex) vertices.find(inLabel);
        }
        catch (IllegalArgumentException i) //If vertex not in graph
        {
            throw new IllegalArgumentException("Vertex with label '" + inLabel +
                    "' not in graph");
        }

        return vertexToGet;
    }

    //PUBLIC MUTATORS
    /* Adds vertex with given label & value to graph if label does not already
     *  exist in graph, throws exception otherwise*/
    public void addVertex(String inLabel, Object inValue) 
    {
        DSAGraphVertex newVertex;
        newVertex = new DSAGraphVertex(inLabel, inValue);

        try
        {
            vertices.insert(inLabel, newVertex);
        }
        catch (IllegalArgumentException i) //If vertex already in graph
        {
            throw new IllegalArgumentException("Vertex with label '" + inLabel +
                    "' already exists in graph");
        }
    }

    /* Removes vertex with given label from graph if it exists, throws exception
     *  otherwise
     */
    public void removeVertex(String inLabel)
    {
        Iterator verticesListIter;
        DSAGraphVertex curVertex;

        if (hasVertex(inLabel))
        {
            //Removing vertex from overall vertex tree
            vertices.delete(inLabel);

            //Removing vertex from every vertex's adjacent
            verticesListIter = vertices.iterator();
            while (verticesListIter.hasNext())
            {
                curVertex = (DSAGraphVertex)verticesListIter.next();

                try
                {
                    curVertex.adjacent.delete(inLabel);
                }
                catch (IllegalArgumentException i) /*If current vertex's
                    adjacent does not have vertex to be removed*/
                {
                    //Do nothing
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Vertex with label '" + inLabel +
                    "' does not exist in graph");
        }
    }
    
    /* Adds directional edge connecting vertex with first label (source) to
     *  one with second label (sink) if vertices with both labels exist
     *  in graph and edge does not already exist, throws exception otherwise
     */
    public void addEdge(String inLabel1, String inLabel2)
    {
        DSAGraphVertex vertex1, vertex2;
        
        /*Getting vertices with imported labels (getVertex will throw an
            IllegalArgumentException if either of vertices are not in graph*/
        vertex1 = getVertex(inLabel1);
        vertex2 = getVertex(inLabel2);

        try
        {
            //Adding link to other vertex to the first's adjacent
            vertex1.adjacent.insert(inLabel2, vertex2);
        }
        catch (IllegalArgumentException i) //If edge already exists
        {
            throw new IllegalArgumentException("Edge already exists in graph");
        }
    }

    /* Removes the edge connecting the 2 imported labels (directionally) if it
     *  exists, throws exception if edge or vertices don't exist
     */
    public void removeEdge(String inLabel1, String inLabel2)
    {
        DSAGraphVertex vertex1, curAdjVertex;
        Iterator adjListIterator;
        int adjListPosition;
        boolean removed;

        if (hasEdge(inLabel1, inLabel2)) /*If graph has the edge to be removed
            (will throw exception if either of vertices do not exist)*/
        {
            vertex1 = getVertex(inLabel1);

            vertex1.adjacent.delete(inLabel2);
        }
        else
        {
            throw new IllegalArgumentException("Edge {" + inLabel1 + ", " +
                    inLabel2 + "} does not exist");
        }
    }

    /* Sets all vertices in graph to unvisited
     */
    private void setAllUnvisited()
    {
        DSAGraphVertex currentVertex;
        Iterator verticesIterator = vertices.iterator();
        
        while (verticesIterator.hasNext())
        {
            currentVertex = (DSAGraphVertex)verticesIterator.next();
            currentVertex.visited = false;
        }
    }
}