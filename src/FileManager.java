/* File Manager by Moritz Bergemann
 * Contains generic methods for writing to & reading text files, receiving input
 *  & returning output in the form of linked lists for easier management
 * Created Date: 4/10/2019
 */

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class FileManager
{
    public static void main(String[] args)
    {
        DSALinkedList n = new DSALinkedList();
        n.insertLast("A");
        n.insertLast("B");
        n.insertLast("C");
        n.insertLast("D");

        writeFile("test.txt", n, false);
    }

    /* Writes the imported linked list of strings (each representing a line)
     *  to the file at the imported filename. Overwrites if the 'append'
     *  parameter is false & appends if true.
     */
    public static void writeFile(String filename, DSALinkedList lineList,
                                 boolean append)
    {
        FileOutputStream fileStrm = null;
        PrintWriter pw;
        Iterator lineIterator;
        String line;

        try
        {
            fileStrm = new FileOutputStream(filename, append);
            pw = new PrintWriter(fileStrm);

            lineIterator = lineList.iterator();

            //Printing all lines in imported linked list to file
            while (lineIterator.hasNext())
            {
                line = (String) lineIterator.next();
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

            throw new IllegalArgumentException("Failed to write to file: " +
                    io.getMessage());
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

    /* Creates a logfile name for the imported network being run in simulation
     *  mode based on the name of the input network & events files and the
     *  current time.
     *  Result Format: log-<netfilename>-<eventfilename>_<year>-<month>-<...>
     *      <...><day>_<hour>-<min>-<sec>.txt
     */
    public static String createLogFileName(String networkFileName,
                                           String eventFileName)
    {
        String logFileName;
        if (!networkFileName.equals("") && !eventFileName.equals("")) /*If
            neither of input file names to create log file name from are empty*/
        {
            logFileName = "log-" + networkFileName + "-" + eventFileName + "_";

            //Creating format of current date/time to add to log file name
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd_HH-mm-ss");

            //Getting current date & time
            LocalDateTime now = LocalDateTime.now();

            //Adding current date & time to log file name in specified format
            logFileName += dtf.format(now);

            //Adding file extension to log file name
            logFileName += ".txt";
        }
        else
        {
            throw new IllegalArgumentException("Network/Event file names " +
                    "cannot be empty");
        }
        return logFileName;
    }
}
