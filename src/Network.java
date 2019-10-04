/* Network by Moritz Bergemann
 * Uses contains methods for simulation of social network using a graph object,
 *  where each vertex is an account & each (directional) edge is directed
 *  towards a follower (sink).
 * Created Date: 3/10/2019
 */
public class Network
{
    //CLASS FIELDS
    DSAGraph network; //Graph representing network
    DSALinkedList posts; //List of all posts made in this network
    int curTime;

    //PRIVATE INNER CLASSES

    /* Stores user information (excluding the user name as this will be the
     *  node label in the graph) of a user in the network
     */
    private class UserInfo
    {
        //CLASS FIELDS
        private int followers;
        private int following;
        private int createdTime; /*Timestep at which user was added to network (0 if
            initial*/

        /* Alternate Constructor (takes in created time)
         */
        private UserInfo(int inCreatedTime)
        {
            assert createdTime >= 0 : "User created time below 0";
            followers = 0;
            following = 0;
            createdTime = inCreatedTime;
        }
    }

    /* Stores all information on a post made within the network
     */
    private class Post
    {
        //CLASS FIELDS
        private String op; //Label of original poster
        private int likes; //Number of likes post has received
        private String content; /*Actual content of post*/
    }

    //CONSTRUCTORS
    /* Default Constructor
     */
    public Network()
    {
        network = new DSAGraph();
        posts = new DSALinkedList();
        curTime = 0;
    }

    /* Adds new user to network (using their name as label), throws exception
     *  if user with imported name already in network
     */
    public void addUser(String inName)
    {
        UserInfo newUserInfo;

        if (!network.hasVertex(inName)) //If user not already in network
        {
            newUserInfo = new UserInfo(curTime);
            network.addVertex(inName, newUserInfo);
        }
        else
        {
            throw new IllegalArgumentException("User with name already in " +
                    "network");
        }
    }

    /* Adds a follower-followed relationship between the imported two users
     *  in the form inUser1 follows inUser2. Throws exception if either of
     *  the users do not exist or if the relationship already exists.
     */
    public void addFollower(String inName1, String inName2)
    {
        if (!network.hasVertex(inName1)) //If first user doesn't exist
        {
            throw new IllegalArgumentException("User '" + inName1 + "' not in" +
                    "graph");
        }
        else if (!network.hasVertex(inName2)) //If 2nd user doesn't exist
        {
            throw new IllegalArgumentException("User '" + inName2 + "' not in" +
                    "graph");
        } else if (network.hasEdge(inName1, inName2)) /*If relationship already
            exists*/
        {
            throw new IllegalArgumentException("Relationship already exists");
        }
        else if (inName1.equals(inName2)) //If 2 imported names are the same
        {
            throw new IllegalArgumentException("User cannot follow themselves");
        }
        else
        {
            /*Adding relationship between users (directional so that points from
                followed to follower (since this is how posts spread)*/
            network.addEdge(inName2, inName1);
        }
    }

    /*Removes a follower-followed relationship between the imported two users
     *  (in form User1 follows User2). Throws exception if either of the users
     *  or the relationship doesn't exist.
     */
    public void removeFollower(String inName1, String inName2)
    {
        try
        {
            if (network.hasEdge(inName2, inName1)) /*If network has a follower-
                followed relationship between user1 & user2*/
            {
                network.removeEdge(inName2, inName1);
            }
        }
        catch (IllegalArgumentException i) /*If one of the imported vertices
            does not exist*/
        {
            throw new IllegalArgumentException("One of users does not exist");
        }
    }
}
