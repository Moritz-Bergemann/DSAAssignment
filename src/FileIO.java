/* FileIO by Moritz Bergemann
 * Performs file I/O for inserting vertices/edges into graph in format defined
 *  in prac sheet 5
 * Created Date: 5/09/2019
 */
import java.io.*;

public class FileIO
{
    public static void main(String[] args)
    {
        DSAGraph graph;

        System.out.println("Reading 1st graph from 'prac6_1.al'...");
        graph = readGraph("prac6_1.al");
        System.out.println("Displaying read graph as adjacency lists:");
        graph.displayAsList();
        System.out.println();
        System.out.println("Displaying read graph as adjacency matrix:");
        graph.displayAsMatrix();
        System.out.println();

        System.out.println("Reading 2nd graph from 'prac6_2.al'...");
        graph = readGraph("prac6_2.al");
        System.out.println("Displaying read graph as adjacency lists:");
        graph.displayAsList();
        System.out.println();
        System.out.println("Displaying read graph as adjacency matrix:");
        graph.displayAsMatrix();
        System.out.println();
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
