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
    private double likeChance; //Chance of liking a post
    private double followChance;

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
        private String content; /*Actual content of post*/
        private int likes; //Number of likes post has received
        private int createdTime; //Timestep post was created
        private boolean complete; //Whether post can be shared any further
        DSALinkedList usersLiked; //Users who have liked this post
        DSALinkedList usersShared; //Users who have had this post shared to them
        DSALinkedList usersToLike; /*Users who will have a chance to like/share
            the next post in the next timestep*/

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

    /* Creates a post with given content*/
    public void makePost(String userName, String content)
    {
        Post newPost;
        if (super.hasVertex(userName))
        {
            try
            {
                newPost = new Post(userName, content, curTime);

                //Add post to list of posts
                posts.insertLast(newPost);

                /*Do initial share of post to all of OP's
                    followers*/
                sharePost(newPost, super.getVertex(userName));
            }
            catch (IllegalArgumentException i) /*If post
            constructor threw exception*/
            {
                throw new IllegalArgumentException("Could not create post: " +
                        i.getMessage());
            }
        }
        else
        {
            throw new IllegalArgumentException("User is not in network");
        }
    }

    /* Shares the imported post to all the followers of the imported user
     *  (given they have not already had the post shared with them). Adds
     *  all users the post has been shared with to the imported linked list.
     */
    private void sharePost(Post inPost, String inUser,
                           DSALinkedList justSharedUsers)
    {
        Iterator followerIter;
        DSAGraphVertex curFollower;

        if (super.hasVertex(inUser))
        {
            followerIter = super.getVertex(inUser).adjacencyList.iterator();

            while (followerIter.hasNext())
            {
                curFollower = (DSAGraphVertex)followerIter.next();

                if (!seenPost(inPost, inUser)) /*If user has not already seen
                    the imported post*/
                {
                    /*Adding user to list of users post has been shared with
                        in this timestep*/
                    justSharedUsers.insertLast(inUser);

                    /*Adding user to post's list of users it has been shared
                        with*/
                    inPost.usersShared.insertLast(inUser);
                }
            }
        }
        else
        {
            throw new IllegalArgumentException("User does not exist");
        }
    }

    /* Increases the imported user's likes by 1 and adds the user's name to the
     *  list of users who have liked the post.
     */
    private void likePost(Post inPost, String inUser)
    {
        if (super.hasVertex(inUser))
        {
            //Increase post's number of likes
            inPost.likes++;

            //Add user who liked post to list of users who have liked it
            inPost.usersLiked.insertLast(inUser);
        }
        else
        {
            throw new IllegalArgumentException("Input user does not exist");
        }
    }

    public void timeStep()
    {
        Iterator postIter, toLikeIter;
        Post curPost;
        String curUser;
        DSALinkedList sharedUsers; /*Linked list of users who post was shared to
            in this timestep*/

        postIter = posts.iterator();
        while (postIter.hasNext()) //For each post in network
        {
            curPost = (Post)postIter.next();

            toLikeIter = curPost.usersToLike.iterator();

            if (!curPost.complete)
            {
                while (toLikeIter.hasNext())
                {
                    sharedUsers = new DSALinkedList();
                    curUser = (String)toLikeIter.next();

                    if (chance(likeChance)) /*If the chance to like the post is
                        met*/
                    {
                        //Make the current user like the post
                        likePost(curPost, curUser);

                        if (chance(shareChance))
                        //TODO INCOMPLETE
                    }
                    //Share post to all users following the current user
                    sharePost(curPost, curUser, sharedUsers);
                }
            }
        }
    }

    /* Returns true if the imported user has already been shared the imported
     *  post and false if not. Throws exception if user does not exist.
     */
    private boolean seenPost(Post inPost, String inUser)
    {
        Iterator sharedUserIter;
        String curUser;
        boolean seen = false; /*Stores whether imported user has seen imported
            post*/

        if (super.hasVertex(inUser))
        {
            sharedUserIter = inPost.usersShared.iterator();

            while (sharedUserIter.hasNext() && !seen) /*For each user post has
                been shared to & while it has not been determined imported user
                has seen post*/
            {
                curUser = (String) sharedUserIter.next();

                if (curUser.equals(inUser)) //If user being searched for found
                {
                    seen = true;
                }
            }
        }
        return seen;
    }

    /*Returns a boolean that has the imported chance (between 0.0 & 1.0) of
     *  being true.
     */
    private boolean chance(double inChance)
    {
        //Sanity check
        assert (inChance >= 0.0) && (inChance <= 1.0) : "Chance not >0 & <1";

        return (inChance >= Math.random());
    }
}
