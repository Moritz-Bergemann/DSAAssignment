/* SaveLoad by Moritz Bergemann
 * Contains methods for saving/loading linked list objects using serialization
 */

import java.io.*;
public class SaveLoad
{
    /*Saves input linked list to input filename
     */
    public static void saveList(DSALinkedList listToSave, String filename)
    {
        FileOutputStream fileStrm = null;
        ObjectOutputStream objStrm;

        try
        {
            fileStrm = new FileOutputStream(filename);
            objStrm = new ObjectOutputStream(fileStrm);
            
            objStrm.writeObject(listToSave);

            objStrm.close();
        }
        catch (Exception e)
        {
            if (fileStrm != null) 
            { 
                try { fileStrm.close(); } catch (IOException io) { }
            }
            System.out.println("Failed to save object!");
        }
    }

    /*Loads linked list from input filename
     */
    public static DSALinkedList loadList(String filename)
    {
        FileInputStream fileStrm = null;
        ObjectInputStream objStrm;
        DSALinkedList inList = null;

        try
        {
            fileStrm = new FileInputStream(filename);
            objStrm = new ObjectInputStream(fileStrm);
            inList = (DSALinkedList)objStrm.readObject();
            objStrm.close();
        }
        catch (ClassNotFoundException c)
        {
            if (fileStrm != null) 
            { 
                try { fileStrm.close(); } catch (IOException io) { }
            }
            System.out.println("Class DSALinkedList not found: " + 
                c.getMessage());
        }
        catch (Exception e)
        {
            if (fileStrm != null) 
            { 
                try { fileStrm.close(); } catch (IOException io) { }
            }
            System.out.println("Failed to load object!");
        }
        return inList;
    }
}
