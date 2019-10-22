/* DSA Graph Test Harness by Moritz Bergemann
 * Test Harness for DSAGraph model class
 * Created DateL 4/09/2019
 */

import static java.lang.System.out;
import java.util.*;
import java.io.*;

public class UnitTestDSAGraph
{
    public static void main (String[] args)
    {
        String[] labelArray = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

        //Constructor
        out.println("Creating 3 graphs for testing...");
        DSAGraph graph1 = new DSAGraph();
        DSAGraph graph2 = new DSAGraph();
        DSAGraph graph3 = new DSAGraph();
        out.println();

        //Adding vertices
        out.println("Adding Set of vertices to graph 1 (labels A, B, C, D, E, F, G, H, I, J), values 'value [label]");
        for (int ii = 0; ii < labelArray.length; ii++)
        {
            System.out.println("Adding vertex: label '" + labelArray[ii] + "' value 'value " + labelArray[ii] + "'");
            graph1.addVertex(labelArray[ii], "value " + labelArray[ii]);
        }
        
        out.println("Adding same set of vertices to graph 2 (labels A, B, C, D, E, F, G, H, I, J), values 'value [label]");
        out.println();

        out.println("Attempting to add vertex with label 'A' (already in graph) to graph 1:");
        try
        {
            graph1.addVertex("A", "doesn't matter");
            out.println("Succeeded (shouldn't have)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("Exception Caught: " + i.getMessage());
        }
        out.println();

        for (int ii = 0; ii < labelArray.length; ii++)
        {
            System.out.println("Adding vertex: label '" + labelArray[ii] + "' value 'value " + labelArray[ii] + "'");
            graph2.addVertex(labelArray[ii], "value " + labelArray[ii]);
        }
        out.println();

        //Adding edges
        out.println("Adding set of edges to graph 2");
        out.println("Adding edge {A, B}");
        graph2.addEdge("A", "B");
        out.println("Adding edge {A, C}");
        graph2.addEdge("A", "C");
        out.println("Adding edge {D, G}");
        graph2.addEdge("D", "G");
        out.println("Adding edge {E, A}");
        graph2.addEdge("E", "A");
        out.println("Adding edge {H, J}");
        graph2.addEdge("H", "J");
        out.println("Adding edge {G, I}");
        graph2.addEdge("G", "I");
        out.println("Adding edge {A, B} (again)");
        graph2.addEdge("A", "B");
        out.println("Adding edge {I, J}");
        graph2.addEdge("I", "J");
        out.println();
        out.println("Attempting to add edge {A, X} to graph (label X doesn't exist)");
        try
        {
            graph2.addEdge("A", "X");
            out.println("Succeeded (shouldn't have)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("Exception Caught: " + i.getMessage());
        }
        out.println();

        //Removing edges
        out.println("Removing edges from graph 2");
        out.println("Removing edge {D, G}");
        graph2.removeEdge("D", "G");
        out.println("Removing edge {A, B} (only one instance should be removed)");
        graph2.removeEdge("A", "B");
        out.println("Attempting to remove edge {A, J} (doesn't exist)");
        try
        {
            graph2.removeEdge("A", "J");
            out.println("\tSucceeded (shouldn't have)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException Caught: " + i.getMessage());
        }
        out.println("Attempting to remove edge {A, X} (vertex X doesn't exist)");
        try
        {
            graph2.removeEdge("A", "X");
            out.println("\tSucceeded (shouldn't have)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException Caught: " + i.getMessage());
        }
        out.println();

        //Displaying graph
        out.println("Displaying graph 1 (No edges):");
        graph1.displayAsList();
        out.println();

        out.println("Displaying graph 2: ");
        graph2.displayAsList();
        out.println();

        out.println("Displaying graph 3 (empty): ");
        graph3.displayAsList();
        out.println();
        
        out.println("Displaying graph 1 as matrix: ");
        graph1.displayAsMatrix();
        out.println();

        out.println("Displaying graph 2 as matrix: ");
        graph2.displayAsMatrix();
        out.println();
    
        //Other Accessors
        //hasVertex
        out.println("Checking whether graph 2 has vertex with label 'D' (should have): " + graph2.hasVertex("D"));
        out.println("Checking whether graph 1 has vertex with label 'A' (should have): " + graph1.hasVertex("A"));
        out.println("Checking whether graph 1 has vertex with label 'test' (shouldn't have): " + graph1.hasVertex("X"));
        out.println("Checking whether graph 3 has vertex with label 'A' (graph empty): " + graph3.hasVertex("A"));
        //getVertexCount
        out.println("Getting vertex count of graph 1 (should be 10): " + graph1.getVertexCount());
        out.println("Getting vertex count of graph 2 (should be 10): " + graph2.getVertexCount());
        out.println("Getting vertex count of graph 3 (should be 0): " + graph3.getVertexCount());
        //getEdgeCount
        out.println("Getting edge count of graph 1 (should be 0): " + graph1.getEdgeCount());
        out.println("Getting edge count of graph 2 (should be 8): " + graph2.getEdgeCount());
        out.println("Getting edge count of graph 3 (should be 0): " + graph3.getEdgeCount());

        //removeVertex
        out.println("Removing edges from graph 2 (should remove also remove all references in adjacency lists)");
        out.println("Removing 'A'");
        graph2.removeVertex("A");
        out.println("Removing 'J'");
        graph2.removeVertex("J");

        out.println("Redisplaying graph 2:");
        graph2.displayAsList();
        out.println();

        out.println("Checking whether nodes 'A' & 'B' in graph 3 adjacent (graph is empty, should throw exception): ");
        try
        {
            out.println(graph3.isAdjacent("A", "B"));
            out.println("Succeeded (shouldn't have)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("Exception Caught: " + i.getMessage());
        }
        out.println();
    
        //Search methods
        DSAGraph pracGraph1 = new DSAGraph();
        DSAGraph pracGraph2 = new DSAGraph();
        Iterator traversalIterator;

        out.println("Creating graphs from DSA practical 5...");
        pracGraph1 = readGraph("prac5graph1.al");
        pracGraph2 = readGraph("prac5graph2.al");
        out.println();

        out.println("Graph 1:");
        pracGraph1.displayAsList();
        out.println();
        out.println("Graph 1 depth first result: ");
        traversalIterator = pracGraph1.depthFirstSearch().iterator();
        while (traversalIterator.hasNext())
        {
            out.print(traversalIterator.next() + " ");
        }
        out.println();
        out.println("Graph 1 breadth first result: ");
        traversalIterator = pracGraph1.breadthFirstSearch().iterator();
        while (traversalIterator.hasNext())
        {
            out.print(traversalIterator.next() + " ");
        }
        out.println();
        out.println();

        out.println("Graph 2:");
        pracGraph2.displayAsList();
        out.println();
        out.println("Graph 2 depth first result: ");
        traversalIterator = pracGraph2.depthFirstSearch().iterator();
        while (traversalIterator.hasNext())
        {
            out.print(traversalIterator.next() + " ");
        }
        out.println();
        out.println("Graph 2 breadth first result: ");
        traversalIterator = pracGraph2.breadthFirstSearch().iterator();
        while (traversalIterator.hasNext())
        {
            out.print(traversalIterator.next() + " ");
        }
        out.println();
    }

    public static DSAGraph readGraph(String filename)
    {
        //Creating graph to return
        DSAGraph graph = new DSAGraph();

        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader buffRdr;
        String line;
        String[] lineArray;

        try
        {
            fileStrm = new FileInputStream(filename);
            rdr = new InputStreamReader(fileStrm);
            buffRdr = new BufferedReader(rdr);

            line = buffRdr.readLine();
            while (line != null)
            {
                lineArray = line.split(" ");

                if (lineArray.length != 2)
                {
                    throw new IllegalArgumentException("Invalid file format");
                }

                //Adding vertices to graph if do not already exist
                if (!graph.hasVertex(lineArray[0]))
                {
                    graph.addVertex(lineArray[0], "value " + lineArray[0]);
                }
                if (!graph.hasVertex(lineArray[1]))
                {
                    graph.addVertex(lineArray[1], "value " + lineArray[1]);
                }

                /*Adding edge between line's 2 vertices (since the 2 vertices
                    on this line must now exist in the graph*/
                graph.addEdge(lineArray[0], lineArray[1]);

                line = buffRdr.readLine();
            }

            fileStrm.close();
        }
        catch (IOException io)
        {
            if (fileStrm != null)
            {
                try { fileStrm.close(); } catch (IOException io2) { }

                System.out.println("Failed to read file");
            }
        }

        return graph;
    }
}
