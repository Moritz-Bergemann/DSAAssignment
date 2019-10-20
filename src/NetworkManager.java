import java.util.*;

/* Network by Moritz Bergemann
 * Static class containing methods for manipulating a network that are not
 *  directly related to the function of the network itself.
 * Created Date: 3/10/2019
 */
public class NetworkManager
{
    /* Creates and returns a new network based on the imported list of
     *  information strings. Throws an exception if any of the imported strings
     *  are invalid.
     */
    public static Network loadNetwork(DSALinkedList networkList)
    {
        Network newNetwork = new Network();

        //Creating iterator for imported list of information strings
        Iterator networkIter = networkList.iterator();
        String curLine;
        String[] splitLine;
        int lineNum = 0;
        String user, user1, user2;

        while (networkIter.hasNext())
        {
            curLine = (String)networkIter.next();
            lineNum++;

            if (curLine.indexOf(':') != -1) /*If string contains colon
                (indicating it describes follower relationship*/
            {
                splitLine = curLine.split(":");
                if (splitLine.length != 2) /*If splitting line resulted in more
                    than 2 strings*/
                {
                    throw new IllegalArgumentException("Invalid Format (line "
                            + lineNum + "): Line defining follower " +
                            "relationship must have single semicolon");
                }
                else if (splitLine[0].equals("") || splitLine[1].equals(""))
                    /*If one of 2 strings resulting from split is empty*/
                {
                    throw new IllegalArgumentException("Invalid Format (line "
                            + lineNum + "): Followed & follower cannot be " +
                            "empty line");
                }
                else if (splitLine[0].trim().length() > 30 ||
                        splitLine[1].trim().length() > 30) /*If either of 2
                        usernames input are longer than 30 characters (not
                        counting trailing whitespace)*/
                {
                    throw new IllegalArgumentException("Invalid Format (line "
                            + lineNum + "): Followed/follower name cannot be " +
                            "greater than 30 characters");
                }
                else
                {
                    try
                    {
                        /*Attempt to add follower-followed relationship to
                            network*/
                        newNetwork.addFollower(splitLine[1].trim(),
                                splitLine[0].trim());
                    }
                    catch (IllegalArgumentException i) /*If relationship failed
                        to add as network rule was broken*/
                    {
                        throw new IllegalArgumentException("Logical Error " +
                                "(line " + lineNum + "): " + i.getMessage());
                    }
                }
            }
            else //If line defines user to be added
            {
                if (curLine.equals("")) //If name of user to be added is blank
                {
                    throw new IllegalArgumentException("Invalid Format (line "
                            + lineNum + "): New user cannot be empty line");
                }
                else if (curLine.trim().length() > 30) /*If name of user to be
                    added is >30 characters*/
                {
                    throw new IllegalArgumentException("Invalid Format (line "
                            + lineNum + "): New user name cannot be greater " +
                            "than 30 characters");
                }
                else
                {
                    try
                    {
                        //Try to add user to network
                        newNetwork.addUser(curLine.trim());
                    }
                    catch (IllegalArgumentException i) /*If user failed to add
                        as network rule was broken*/
                    {
                        throw new IllegalArgumentException("Logical Error " +
                                "(line " + lineNum + "): " + i.getMessage());
                    }
                }
            }
        }

        return newNetwork;
    }

    /* Returns a linked list containing the user/relationship information of
     *  the imported network in the network file format, each line being
     *  an element in the list.
     */
    public DSALinkedList saveNetwork(Network network)
    {
        DSALinkedList networkList = new DSALinkedList();

        //Getting list of users
        DSALinkedList userList = network.getUserList();


        return networkList;
    }
    //TODO all in one method (that calls 2 others) or 2 separate methods?

    //TODO save network
}
