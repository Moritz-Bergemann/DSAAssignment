import java.util.Iterator;

/* Network by Moritz Bergemann
 * Uses contains methods for simulation of social network using a graph object,
 *  where each vertex is an account & each (directional) edge is directed
 *  towards a follower (sink).
 * Created Date: 3/10/2019
 */
public class Network extends DSAGraph
{
    //CLASS FIELDS
    private DSALinkedList posts; //List of all posts made in this network
    private int curTime;

    //PRIVATE INNER CLASSES
    /* Stores user information (excluding the user name as this will be the
     *  node label in the graph) of a user in the network
     */
    private class UserInfo
    {
        //CLASS FIELDS
        private int followers;
        private int following;
        private int createdTime; /*Timestep at which user was added to network
            (0 if initial*/

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
        private int createdTime;
        DSALinkedList usersLiked; //Users who have liked this post
        DSALinkedList usersToLike; //Users who will have a chance to like the next post in the next timestep

        /* Alternate Constructor
         */
        private Post(String inOP, String inContent, int inCreatedTime)
        {
            if (inContent != "") //If content of post is not empty
            {
                op = inOP;
                content = inContent;
                likes = 0; //Likes starts initially at 0
                createdTime = inCreatedTime;

            }
            else
            {
                throw new IllegalArgumentException("Post content cannot be negative");
            }
        }
    }

    //CONSTRUCTORS
    /* Default Constructor
     */
    public Network()
    {
        super(); //Constructing DSAGraph Superclass
        posts = new DSALinkedList();
        curTime = 0;
    }

    /* Adds new user to network (using their name as label), throws exception
     *  if user with imported name already in network
     */
    public void addUser(String inName)
    {
        UserInfo newUserInfo;

        if (!super.hasVertex(inName)) //If user not already in network
        {
            newUserInfo = new UserInfo(curTime);
            super.addVertex(inName, newUserInfo);
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
        if (!super.hasVertex(inName1)) //If first user doesn't exist
        {
            throw new IllegalArgumentException("User '" + inName1 + "' not in" +
                    "graph");
        }
        else if (!super.hasVertex(inName2)) //If 2nd user doesn't exist
        {
            throw new IllegalArgumentException("User '" + inName2 + "' not in" +
                    "graph");
        } else if (super.hasEdge(inName1, inName2)) /*If relationship already
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
            super.addEdge(inName2, inName1);
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
            if (super.hasEdge(inName2, inName1)) /*If network has a follower-
                followed relationship between user1 & user2*/
            {
                super.removeEdge(inName2, inName1);
            }
        }
        catch (IllegalArgumentException i) /*If one of the imported vertices
            does not exist*/
        {
            throw new IllegalArgumentException("One of users does not exist");
        }
    }

    public void makePost(String user, String content)
    {
        Post inPost;
        if (super.hasVertex(user))
        {
            try
            {
                inPost = new Post(user, content, curTime);
            } catch (IllegalArgumentException i) {
                throw new IllegalArgumentException("Could not create post: " + i.getMessage());
            }
        }
        else
        {
            throw new IllegalArgumentException("User is not in network");
        }
    }

    public void timeStep()
    {
        Iterator postIterator = posts.iterator();
    }
}
