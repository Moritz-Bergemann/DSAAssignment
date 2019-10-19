/* File Manager by Moritz Bergemann
 * Contains generic methods for writing to & reading text files, receiving input
 *  & returning output in the form of linked lists for easier management
 * Created Date: 4/10/2019
 */

import java.io.*;
import java.util.*;

public class FileManager
{
    /* Writes the imported linked list of strings (each representing a line)
     * to the file at the imported filename.
     */
    public static void writeFile(String filename, DSALinkedList lineList)
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw;
        Iterator lineIterator;
        String line;

        try
        {
            fileStrm = new FileOutputStream(filename);
            pw = new PrintWriter(fileStrm);

            lineIterator = lineList.iterator();

            //Printing all lines in imported linked list to file
            while (lineIterator.hasNext())
            {
                line = (String) lineIterator.next(); /*NOTE: Will this throw
                    exception if line is not of type string?*/
                pw.println(line);
            }
            pw.close();
        }
        catch (IOException io)
        {
            if (fileStrm != null)
            {
                try { fileStrm.close(); } catch (IOException io2) { }
            }

            /*TODO something here (depending on how IO failure should be handled)*/
        }
    }

    public static DSALinkedList readFile(String filename)
    {
        FileInputStream fileStrm = null;
        InputStreamReader rdr;
        BufferedReader buffRdr;
        DSALinkedList lineList = new DSALinkedList();
        String line;

        try
        {
            fileStrm = new FileInputStream(filename);
            rdr = new InputStreamReader(fileStrm);
            buffRdr = new BufferedReader(rdr);

            line = buffRdr.readLine();

            while (line != null)
            {
                //Adding read line to linked list to be returned
                lineList.insertLast(line);

                line = buffRdr.readLine();
            }
        }
        catch (IOException io)
        {
            if (fileStrm != null)
            {
                try { fileStrm.close(); } catch (IOException io2) { }
            }
            throw new IllegalArgumentException("Failed to read file: " +
                    io.getMessage());
        }

        return lineList;
    }
}
