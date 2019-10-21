/* DSA Graph by Moritz Bergemann
 * Model class for graph data structure consisting of set of vertices linked
 *  together by (directional) edges
 * Created Date: 3/09/2019
 * Updated Date: 3/10/2019
 */
import java.util.*;

public class DSAGraph
{
    //CLASS FIELDS
    protected DSALinkedList vertexList; //List of vertices in graph (ordered)

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
        DSALinkedList adjacencyList; /*Stores links to vertices that this 
            vertex is adjacent to*/
        boolean visited; //Stores whether vertex has been visited during search

        //CONSTRUCTORS
        /* Alternate constructor
         */
        public DSAGraphVertex(String inLabel, Object inValue)
        {
            label = inLabel;
            value = inValue;
            adjacencyList = new DSALinkedList(); /*List of vertices that this
                connected to this graph (where this graph is source)*/
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
        vertexList = new DSALinkedList();
    }

    //PUBLIC ACCESSORS
    /* Returns whether or not vertex with given label exists in graph (by 
     *  checking list of all vertices
     */
    public boolean hasVertex(String inLabel)
    {
        boolean vertexPresent = false; /*Stores whether vertex in graph (false
            by default)*/
        Iterator vertexListIterator = vertexList.iterator();
        DSAGraphVertex currentVertex;

        while (vertexListIterator.hasNext())
        {
            currentVertex = (DSAGraphVertex)vertexListIterator.next();
            if (currentVertex.label.equals(inLabel)) /*If current vertex in
                vertex list has label being searched for*/
            {
                vertexPresent = true;
            }
        }
        
        return vertexPresent;
    }

    /* Returns whether the graph possess a directional edge going from label1
     *  to label2. Throws exception if one of the imported labels does not
     *  exist.
     */
    public boolean hasEdge(String inLabel1, String inLabel2)
    {
        boolean edgePresent = false;
        DSAGraphVertex vertex1, curAdjVertex;
        Iterator adjListIterator;

        /*Getting vertices in graph from imported labels (will throw exception
            if either does not exist)*/
        vertex1 = getVertex(inLabel1);
        getVertex(inLabel2);

        adjListIterator = vertex1.adjacencyList.iterator();

        while (adjListIterator.hasNext()) /*For each vertex in the first
            imported label's adjacency list*/
        {
            curAdjVertex = (DSAGraphVertex)adjListIterator.next();
            if (curAdjVertex.label.equals(inLabel2)) /*If the current vertex
                in the adjacency list has the same label as the one being
                searched for*/
            {
                edgePresent = true;
            }
        }

        return edgePresent;
    }

    /* Returns whether graph is currently empty
     */
    public boolean isEmpty()
    {
        return vertexList.isEmpty();
    }

    /* Returns number of vertices currently in graph
     */
    public int getVertexCount()
    {
        return vertexList.getCount();
    }

    /* Returns number of edges currently in graph
     */
    public int getEdgeCount()
    {
        int edgeCount = 0; /*Total number of edges in graph (equivalent to total
            number of items in each vertex's adjacency list*/
        Iterator vertexListIterator = vertexList.iterator();
        DSAGraphVertex currentVertex, currentAdjacentVertex;

        //Iterating through each vertex in list of vertices
        while (vertexListIterator.hasNext())
        {
            currentVertex = (DSAGraphVertex)vertexListIterator.next();

            //Increasing edgeCount by number of vertices in adjacency list
            edgeCount += currentVertex.adjacencyList.getCount();
        }

        //Getting number of edges as number of adjacent vertices listed / 2
        return edgeCount;
    }

    /* Returns true if 2 vertices with imported labels are adjacent & false if
     *  not, throws exception if one/both of vertices are not in graph
     */
    public boolean isAdjacent(String inLabel1, String inLabel2)
    {
        boolean areAdjacent = false;
        DSAGraphVertex vertex1, vertex2, currentAdjacentVertex;

        /*Getting 2 vertices using their labels (throws IllegalArgumentException
            if either doesn't exist*/
        vertex1 = getVertex(inLabel1);
         vertex2 = getVertex(inLabel2);

        //Creating iterator for vertex 1's adjacency list (2 would also work)
        Iterator vertex1AdjacencyListIterator =
            vertex1.adjacencyList.iterator();

        while (vertex1AdjacencyListIterator.hasNext())
        {
            currentAdjacentVertex =
                (DSAGraphVertex)vertex1AdjacencyListIterator.next();
            if (currentAdjacentVertex == vertex2) /*If vertex 1's adjacency
                list contains link to vertex 2*/
            {
                areAdjacent = true;
            }
        }
        return areAdjacent;
    }

    /* Prints the graph's vertices to the console in adjacency list form
     */
    public void displayAsList()
    {
        Iterator vertexListIterator = vertexList.iterator();
        Iterator adjacencyListIterator;
        DSAGraphVertex currentVertex, currentAdjacentVertex;
        String vertexString; /*String containing label of current vertex & 
            labels of all adjacent vertices*/

        //Iterating through each vertex in vertex list
        while (vertexListIterator.hasNext())
        {
            vertexString = "";

            currentVertex = (DSAGraphVertex)vertexListIterator.next();
            
            //Adding current vertex's label to vertex string
            vertexString += currentVertex.label + ": ";

            //Iterating through adjacency list of current vertex
            adjacencyListIterator = currentVertex.adjacencyList.iterator();
            while (adjacencyListIterator.hasNext())
            {
                currentAdjacentVertex = 
                    (DSAGraphVertex)adjacencyListIterator.next();        
                
                //Adding label of current adjacent vertex to printed list
                vertexString += currentAdjacentVertex.label;

                /*Conditionally adding comma & space (if this wasn't last vertex
                    in adjacency list)*/
                if (adjacencyListIterator.hasNext())
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

        Iterator vertexListIterator = vertexList.iterator();
        Iterator adjacencyListIterator;
        DSAGraphVertex currentVertex, currentAdjacentVertex;
        String vertexString; /*String containing label of current vertex &
            labels of all adjacent vertices*/

        //Iterating through each vertex in vertex list
        while (vertexListIterator.hasNext())
        {
            vertexString = "";

            currentVertex = (DSAGraphVertex)vertexListIterator.next();

            //Adding current vertex's label to vertex string
            vertexString += currentVertex.label + ": ";

            //Iterating through adjacency list of current vertex
            adjacencyListIterator = currentVertex.adjacencyList.iterator();
            while (adjacencyListIterator.hasNext())
            {
                currentAdjacentVertex =
                        (DSAGraphVertex)adjacencyListIterator.next();

                //Adding label of current adjacent vertex to printed list
                vertexString += currentAdjacentVertex.label;

                /*Conditionally adding comma & space (if this wasn't last vertex
                    in adjacency list)*/
                if (adjacencyListIterator.hasNext())
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
        Iterator rowVertexIterator = vertexList.iterator();
        Iterator columnVertexIterator;
        DSAGraphVertex rowVertex, columnVertex;

        //Filling adjacency matrix
        for (int ii = 0; ii < vertexCount; ii++) //For each row in matrix
        {
            /*Increasing row vertex list iterator & reinitialising column 
                vertex list iterator*/
            rowVertex = (DSAGraphVertex)rowVertexIterator.next();
            columnVertexIterator = vertexList.iterator();

            //For each column in row
            for (int jj = 0; jj < vertexCount; jj++)
            {
                columnVertex = (DSAGraphVertex)columnVertexIterator.next();
                
                if (isAdjacent(rowVertex.label, columnVertex.label)) /*If 2
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
        
        Iterator vertexListIterator = vertexList.iterator();
        DSAGraphVertex currentVertex;

        //Printing out adjacency matrix to console (with row/column labels)
        //Printing out first line (column labels)
        String currentLine = "   "; //3 spaces (for row label)
        for (int ii = 0; ii < vertexCount; ii++)
        {
            currentVertex = (DSAGraphVertex)vertexListIterator.next();
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
        vertexListIterator = vertexList.iterator();

        //Printing out each row of matrix as line
        for (int row = 0; row < vertexCount; row++)
        {
            currentVertex = (DSAGraphVertex)vertexListIterator.next();
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
        DSAStackList vertexStack = new DSAStackList(); /*Stack for storing 
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
        vertexStack.push(vertexList.peekFirst());
        traversalList.insertLast(((DSAGraphVertex)vertexStack.top()).label);
        ((DSAGraphVertex)vertexStack.top()).visited = true;

        while (!vertexStack.isEmpty())
        {
            firstUnvisitedVertex = null;

            /*Searching for first vertex in current top of stack's adjacency 
                list that has not yet been visited*/
            adjListIterator = 
                ((DSAGraphVertex)vertexStack.top()).adjacencyList.iterator();
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
        vertexQueue.enqueue(vertexList.peekFirst());
        ((DSAGraphVertex)vertexList.peekFirst()).visited = true;

        while (!vertexQueue.isEmpty())
        {
            /*Adding all unvisited vertices adjacent to current front
                of queue to queue*/
            adjListIterator = 
                ((DSAGraphVertex)vertexQueue.peek()).adjacencyList.iterator();
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
        DSAGraphVertex currentVertex;

        Iterator vertexListIterator = vertexList.iterator();
        
        while (vertexListIterator.hasNext() && (vertexToGet == null)) /*While
            vertex list still has entries and vertex to find has not been 
            found*/
        {
            currentVertex = (DSAGraphVertex)vertexListIterator.next();
            if (currentVertex.label.equals(inLabel))
            {
                vertexToGet = currentVertex;
            }
        }

        if (vertexToGet == null) //If vertex to find wasn't in graph
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

        if(!hasVertex(inLabel)) /*If graph does not already have vertex with 
            same label as new vertex*/
        {
            //Creating new vertex & adding to list of vertices
            newVertex = new DSAGraphVertex(inLabel, inValue);
            vertexList.insertLast(newVertex);

            //Sorting list of vertices to be in ASCII order
            vertexList.sortAsc();
        }
        else
        {
            throw new IllegalArgumentException("Vertex with label '" + inLabel +
                "' already exists in graph");
        }
    }

    /* Removes vertex with given label from graph (and returns its value) if it
     *  exists, throws exception otherwise
     */
    public Object removeVertex(String inLabel)
    {
        Iterator vertexListIter, adjListIter;
        DSAGraphVertex curVertex, curAdjVertex;
        boolean found = false;
        Object vertexValue = null;
        int vertexListIndex, adjListIdx; /*Stores indexes in vertex/adjacency
            lists to remove*/

        if (hasVertex(inLabel))
        {
            //Getting value of vertex to return
            vertexValue = getVertex(inLabel).value;

            //Removing vertex from overall list of vertices
            vertexListIter = vertexList.iterator();
            vertexListIndex = 0;
            while (vertexListIter.hasNext() && !found) /*For each vertex in
                graph & while vertex to remove has not been found*/
            {
                curVertex = (DSAGraphVertex)vertexListIter.next();

                if (curVertex.label.equals(inLabel))
                {
                    vertexList.removeAt(vertexListIndex);
                    found = true;
                }

                vertexListIndex++;
            }

            //Removing vertex from every vertex's adjacency list
            vertexListIter = vertexList.iterator();
            while (vertexListIter.hasNext())
            {
                curVertex = (DSAGraphVertex)vertexListIter.next();

                adjListIter = curVertex.adjacencyList.iterator();
                adjListIdx = 0;
                found = false;
                while (adjListIter.hasNext() && !found) /*For each vertex in the
                    current vertex's adjacency list & while the vertex to remove
                    has not been found*/
                {
                    curAdjVertex = (DSAGraphVertex)adjListIter.next();

                    if (curAdjVertex.label.equals(inLabel)) /*If current vertex
                        in adjacency list has label of vertex to be removed*/
                    {
                        curVertex.adjacencyList.removeAt(adjListIdx);
                        found = true;
                    }

                    adjListIdx++;
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("Vertex with label '" + inLabel +
                    "' does not exist in graph");
        }

        return vertexValue;
    }
    
    /* Adds directional edge connecting vertex with first label (source) to
     *  one with second label (sink) if vertices with both labels exist
     *  in graph, throws exception otherwise
     */
    public void addEdge(String inLabel1, String inLabel2)
    {
        DSAGraphVertex vertex1, vertex2;
        
        //NEW DESIGN
        /*Getting vertices with imported labels (getVertex will throw an
            IllegalArgumentException if either of vertices are not in graph*/
        vertex1 = getVertex(inLabel1);
        vertex2 = getVertex(inLabel2);
        
        //Adding link to other vertex to the first's adjacency list
        vertex1.adjacencyList.insertLast(vertex2);

        //Sorting both vertice's adjacency lists to be in ASCII order
        vertex1.adjacencyList.sortAsc();
        vertex2.adjacencyList.sortAsc();
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
            adjListIterator = vertex1.adjacencyList.iterator();

            //Searching for & removing connected node in adjacency list
            adjListPosition = 0;
            removed = false;
            while (adjListIterator.hasNext() && !removed)
            {
                curAdjVertex = (DSAGraphVertex)adjListIterator.next();

                if (curAdjVertex.label.equals(inLabel2)) /*If current adjacency
                    list node has label of edge to be removed*/
                {
                    vertex1.adjacencyList.removeAt(adjListPosition);

                    /*Setting cancelling iteration to avoid removing multiple
                        instances of the same edge (if they were to exist)*/
                    removed = true;
                }
                adjListPosition++;
            }
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
        Iterator vertexListIterator = vertexList.iterator();
        
        while (vertexListIterator.hasNext())
        {
            currentVertex = (DSAGraphVertex)vertexListIterator.next();
            currentVertex.visited = false;
        }
    }
}