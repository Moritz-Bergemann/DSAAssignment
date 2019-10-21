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
    public static DSALinkedList saveNetwork(Network network)
    {
        DSALinkedList networkList = new DSALinkedList();

        //Getting list of users
        DSALinkedList userList = network.getUserList();

        //Appending list of users to output list
        networkList.append(userList);

        Iterator userListIter = userList.iterator();
        String curUserName, curFollowerName;
        DSALinkedList followerList;
        Iterator followerListIter;
        while (userListIter.hasNext()) /*For all users in network*/
        {
            curUserName = (String) userListIter.next();

            //Getting list of all users following current user
            followerList = network.getFollowers(curUserName);

            followerListIter = followerList.iterator();
            while (followerListIter.hasNext()) /*For all followers of current
                user*/
            {
                curFollowerName = (String) followerListIter.next();

                //Adding follower-followed relationship to output list
                networkList.insertLast(curUserName + ":" + curFollowerName);
            }
        }

        return networkList;
    }

    /* Applies the imported list of events to the imported network if valid.
     *  If line invalid, error message is printed to console & reading
     *  continues on next line.
     */
    public static void applyEvents(Network network, DSALinkedList eventList)
    {
        Iterator eventIter = eventList.iterator();
        String curLine;
        String[] splitLine;
        int lineNum = 0;
        while (eventIter.hasNext()) //For each string in imported list
        {
            curLine = (String) eventIter.next();
            lineNum++;

            try
            {
                if (curLine.equals("")) //If line to read is empty
                {
                    throw new IllegalArgumentException("Invalid Format (line "
                            + lineNum + "): Line cannot be empty");
                }
                else if (curLine.indexOf(':') < 0) /*If current line does not
                    contain a semicolon*/
                {
                    throw new IllegalArgumentException("Invalid Format (line "
                            + lineNum + "): Line must contain at least 1 colon");
                }
                switch (curLine.charAt(0)) /*Getting first character in line (should
                    define event to occur*/
                {
                    case 'A': //Add User
                        splitLine = curLine.split(":");
                        if (splitLine.length == 2)
                        {
                            try
                            {
                                network.addUser(splitLine[1]);
                            }
                            catch (IllegalArgumentException i)
                            {
                                throw new IllegalArgumentException("Logical " +
                                        "Error (line " + lineNum + "): " +
                                        i.getMessage());
                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("Invalid Format " +
                                    "(line " + lineNum + "): Add-User line " +
                                    "must contain 2 sections");
                        }
                        break;
                    case 'F': //Add follower
                        splitLine = curLine.split(":");
                        if (splitLine.length == 3)
                        {
                            try
                            {
                                network.addFollower(splitLine[2].trim(),
                                        splitLine[1].trim());
                            }
                            catch (IllegalArgumentException i)
                            {
                                throw new IllegalArgumentException("Logical " +
                                        "Error (line " + lineNum + "): " +
                                        i.getMessage());
                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("Invalid Format " +
                                    "(line " + lineNum + "): Add-Follower line " +
                                    "must contain 3 sections");
                        }
                        break;
                    case 'P': //Add post
                        splitLine = curLine.split(":");
                        if (splitLine.length == 3) //No clickbait factor
                        {
                            try
                            {
                                network.makePost(splitLine[1].trim(),
                                        splitLine[2].trim(), 1);
                            }
                            catch (IllegalArgumentException i)
                            {
                                throw new IllegalArgumentException("Logical " +
                                        "Error (line " + lineNum + "): " +
                                        i.getMessage());
                            }
                        }
                        else if (splitLine.length == 4) //Clickbait factor
                        {
                            try
                            {
                                network.makePost(splitLine[1].trim(),
                                        splitLine[2].trim(),
                                        Double.parseDouble(splitLine[3]));
                            }
                            catch (NumberFormatException n)
                            {
                                throw new IllegalArgumentException("Invalid " +
                                        "Format (line " + lineNum + "): Clickbait" +
                                        " factor is not a number");
                            }
                            catch (IllegalArgumentException i)
                            {
                                throw new IllegalArgumentException("Logical " +
                                        "Error (line " + lineNum + "): " +
                                        i.getMessage());
                            }
                        }
                        else
                        {
                            throw new IllegalArgumentException("Invalid Format " +
                                    "(line " + lineNum + "): Add-Follower line " +
                                    "must contain 3 or 4 sections");
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("Invalid Format (line "
                                + lineNum + "): Event descriptor must be " +
                                "either A (add user), F (add follower) or " +
                                "P (make post)");
                }
            }
            catch (IllegalArgumentException i) /*If failed to read current
                line*/
            {
                System.out.println(i.getMessage());
            }
        }
    }

    /* Creates a log containing information of the imported network at the
     *  current timestep and returns as a linked list of strings.
     */
    public static DSALinkedList logTimeStep(Network network)
    {
        DSALinkedList timeStepList = new DSALinkedList();

        //Adding header
        timeStepList.insertLast("TIMESTEP " + network.getCurTime() + ":");

        //Adding Network Diagram:
        timeStepList.insertLast("Network Diagram:");

        Iterator diagramIter = network.returnAsList().iterator();
        while (diagramIter.hasNext())
        {
            //Indenting each part of network description for readability
            timeStepList.insertLast("\t" + (String)diagramIter.next());
        }
        timeStepList.insertLast("");

        //Adding Users by Popularity
        timeStepList.insertLast("Users by Popularity:");
        Iterator userIter = network.getUsersByFollowers().iterator();
        int userNum = 1; //Used to number users for readability
        String curUserInfo;
        while (userIter.hasNext())
        {
            /*Adding tab characters to end of all line breaks in returned string
                so all of it is indented*/
            curUserInfo = ((String)userIter.next()).replaceAll("\n", "\n\t");
            timeStepList.insertLast("\t" + userNum + ".");
            timeStepList.insertLast("\t" + curUserInfo);
            userNum++;
        }
        timeStepList.insertLast("");

        //Adding Posts by Popularity
        timeStepList.insertLast("Posts by Popularity:");
        Iterator postIter = network.getPostsByLikes().iterator();
        int postNum = 1; //Used to number users for readability
        String curPostInfo;
        while (postIter.hasNext())
        {
            //Adding tab characters after line breaks for readability
            curPostInfo = ((String) postIter.next()).replaceAll("\n", "\n\t");
            timeStepList.insertLast("\t" + postNum + ".");
            timeStepList.insertLast("\t" + curPostInfo);
            postNum++;
        }
        timeStepList.insertLast("");

        return timeStepList;
    }
}
