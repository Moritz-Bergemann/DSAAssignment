import java.util.*;

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
            assert createdTime >= 0 : "User's created time below 0";
            followers = 0;
            following = 0;
            createdTime = inCreatedTime;
        }
    }

    /* Stores all information on a post made within the network
     */
    private class Post implements Comparable<Post>
    {
        //CLASS FIELDS
        private String op; //Label of original poster
        private String content; /*Actual content of post*/
        private int likes; //Number of likes post has received
        private int createdTime; //Timestep post was created
        private boolean complete; //Whether post can be shared any further
        private DSALinkedList usersLiked; //Users who have liked this post
        private DSALinkedList usersShared; //Users who have had this post shared to them
        private DSALinkedList usersToLike; /*Users who will have a chance to like/share
            the next post in the next timestep*/

        /* Alternate Constructor
         */
        private Post(String inOP, String inContent, int inCreatedTime)
        {
            if (!hasVertex(inOP)) /*If network doesn't have user with imported
                name*/
            {
                throw new IllegalArgumentException("User to make post does" +
                        "not exist in network");
            }
            else if (inContent.equals("")) //If content of post is not empty
            {
                throw new IllegalArgumentException("Post content cannot be " +
                        "empty");
            }
            else if (inContent.indexOf(':') > 0) /*If post content contains
                colon*/
            {
                throw new IllegalArgumentException("Post content cannot " +
                        "contain colon");
            }
            else
            {
                op = inOP;
                content = inContent;
                likes = 0; //Likes starts initially at 0
                createdTime = inCreatedTime;
                usersLiked = new DSALinkedList();
                usersShared = new DSALinkedList();
                usersToLike = new DSALinkedList();
            }
        }

        //PUBLIC ACCESSORS
        /* Compares this post to another post by the number of likes
         */
        public int compareTo(Post compPost)
        {
            return likes - compPost.likes;
        }

        /* Returns post information as a string
         */
        public String toString()
        {
            return "Original Poster: " + op + "\n" +
                    "Content: " + content + "\n" +
                    "Number of Likes: " + likes + "\n" +
                    "Created Time: " + createdTime;
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
        likeChance = -1.0;
        followChance = -1.0;
    }

    /*Sets the chance to like a post to the imported double if valid, throws
     *  exception otherwise
     */
    public void setLikeChance(double inChance)
    {
        if (inChance >= 0.0 && inChance <= 1.0)
        {
            likeChance = inChance;
        }
        else
        {
            throw new IllegalArgumentException("Like chance must be between " +
                    "0.0 & 1.0");
        }
    }

    /* Gets the chance of liking a post
     */
    public double getLikeChance()
    {
        return likeChance;
    }

    /*Sets the chance to follow the OP after linking a post to the imported
     *  double if valid, throws exception otherwise
     */
    public void setFollowChance(double inChance)
    {
        if (inChance >= 0.0 && inChance <= 1.0)
        {
            followChance = inChance;
        }
        else
        {
            throw new IllegalArgumentException("Follow chance must be " +
                    "between 0.0 & 1.0");
        }
    }

    /* Gets the chance of following the OP after liking a post
     */
    public double getFollowChance()
    {
        return followChance;
    }

    /* Adds new user to network (using their name as label), throws exception
     *  if user with imported name already in network
     */
    public void addUser(String inName)
    {
        UserInfo newUserInfo;

        if (!super.hasVertex(inName)) //If user not already in network
        {
            if (inName.indexOf(':') < 0) /*If input username does not contain
                any semicolons*/
            {
                newUserInfo = new UserInfo(curTime);
                super.addVertex(inName, newUserInfo);
            }
            else
            {
                throw new IllegalArgumentException("User name cannot contain" +
                        "semicolons (':')");
            }
        }
        else
        {
            throw new IllegalArgumentException("User with name already in " +
                    "network");
        }
    }

    /* Returns information on the user with the imported name as a string,
     *  throws exception is user does not exist
     */
    public String getUserInfo(String inName)
    {
        UserInfo inUserInfo;
        String infoString;

        if (super.hasVertex(inName))
        {
            //Getting user info from graph
            inUserInfo = (UserInfo)super.getVertex(inName).value;

            infoString = "User: " + inName + "\n" +
                    "Followers: " + inUserInfo.followers + "\n" +
                    "Following: " + inUserInfo.following + "\n";
        }
        else
        {
            throw new IllegalArgumentException("User does not exist in " +
                    "network");
        }

        return infoString;
    }

    /* Removes a user from the network via their name, throws exception if user
     *  is not in network
     */
    public void removeUser(String inName)
    {
        if (super.hasVertex(inName))
        {
            super.removeVertex(inName);
        }
        else
        {
            throw new IllegalArgumentException("User does not exist in " +
                    "network");
        }
    }

    /* Adds a follower-followed relationship between the imported two users
     *  in the form inUser1 follows inUser2. Throws exception if either of
     *  the users do not exist or if the relationship already exists.
     */
    public void addFollower(String inUser1, String inUser2)
    {
        if (!super.hasVertex(inUser1)) //If first user doesn't exist
        {
            throw new IllegalArgumentException("User '" + inUser1 + "' not in" +
                    " network");
        }
        else if (!super.hasVertex(inUser2)) //If 2nd user doesn't exist
        {
            throw new IllegalArgumentException("User '" + inUser2 + "' not in" +
                    " network");
        } else if (super.hasEdge(inUser2, inUser1)) /*If relationship already
            exists*/
        {
            throw new IllegalArgumentException("Relationship already exists");
        }
        else if (inUser1.equals(inUser2)) //If 2 imported names are the same
        {
            throw new IllegalArgumentException("User cannot follow themselves");
        }
        else
        {
            /*Adding relationship between users (directional so that points from
                followed to follower (since this is how posts spread)*/
            super.addEdge(inUser2, inUser1);

            //Increasing following user's 'following' count by 1
            ((UserInfo)super.getVertex(inUser1).value).following++;

            //Increasing followed user's 'follower' count by 1
            ((UserInfo)super.getVertex(inUser2).value).followers++;
        }
    }

    /* Returns whether the network contains the follower-followed relationship
     *  between the imported users in the form inUser1 follows inUser2
     */
    public boolean hasFollower(String inUser1, String inUser2)
    {
        boolean followerPresent = false;

        if (super.hasVertex(inUser1))
        {
            if (super.hasVertex(inUser2))
            {
                followerPresent = super.hasEdge(inUser2, inUser1); /*Reverse as
                    follower-followed represented by followed having edge
                    directed towards follower*/
            }
            else
            {
                throw new IllegalArgumentException("User" + inUser2 + "not in" +
                        " network");
            }
        }

        return followerPresent;
    }

    /*Removes a follower-followed relationship between the imported two users
     *  (in form User1 follows User2). Throws exception if either of the users
     *  or the relationship doesn't exist.
     */
    public void removeFollower(String inUser1, String inUser2)
    {
        if (!super.hasVertex(inUser1)) //If first user doesn't exist
        {
            throw new IllegalArgumentException("User '" + inUser1 + "' not in" +
                    " network");
        }
        else if (!super.hasVertex(inUser2)) //If 2nd user doesn't exist
        {
            throw new IllegalArgumentException("User '" + inUser2 + "' not in" +
                    " network");
        } else if (!super.hasEdge(inUser2, inUser1)) /*If relationship does not
            exist*/
        {
            throw new IllegalArgumentException("Relationship does not exist");
        }
        else
        {
            //Removing relationship from graph
            super.removeEdge(inUser2, inUser1);

            //Decreasing following user's 'following' count by 1
            ((UserInfo)super.getVertex(inUser1).value).following--;

            //Decreasing followed user's 'follower' count by 1
            ((UserInfo)super.getVertex(inUser2).value).followers--;
        }
    }

    /* Creates a post with given content, posted by the imported user and shares
     *  it with the OPs followers
    */
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
                sharePost(newPost, userName, newPost.usersToLike); //NOTE does this make sense?
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

    /* Returns a linked list of strings, each string being the information of a
     *  post made in the network (sorted by number of likes the post has
     *  received)
     */
    public DSALinkedList getPostsByPopularity()
    {
        DSALinkedList sortedPosts = new DSALinkedList();
        DSALinkedList postInfoList = new DSALinkedList();

        //Creating copy of post list to be sorted
        Iterator postIter = posts.iterator();
        while (postIter.hasNext()) /*For each post in network*/
        {
            sortedPosts.insertLast(postIter.next());
        }

        //Sorting created list by post's number of likes
        sortedPosts.sort();

        //Adding list of post info strings to list to return (from sorted list)
        Iterator sortedPostIter = sortedPosts.iterator();
        while (sortedPostIter.hasNext()) /*For each post in sorted list*/
        {
            postInfoList.insertLast(sortedPostIter.next().toString());
        }

        return postInfoList;
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
                    justSharedUsers.insertLast(curFollower);

                    /*Adding user to post's list of users it has been shared
                        with*/
                    inPost.usersShared.insertLast(curFollower);
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
        DSALinkedList justSharedUsers; /*Linked list of users who post was
            shared to in this timestep*/

        //Increasing timestep count
        curTime++;

        postIter = posts.iterator();
        while (postIter.hasNext()) //For each post in network
        {
            curPost = (Post)postIter.next();

            toLikeIter = curPost.usersToLike.iterator();

            if (!curPost.complete)
            {
                /*Creating linked list of users who have had this post shared
                    with them on this timestep*/
                justSharedUsers = new DSALinkedList();

                while (toLikeIter.hasNext())
                {
                    curUser = (String)toLikeIter.next();

                    if (chance(likeChance)) /*If the chance to like the post is
                        met*/
                    {
                        //Make the current user like the post
                        likePost(curPost, curUser);

                        /*Make the current user share the post (& add all users
                            who received it to list of just shared users)*/
                        sharePost(curPost, curUser, justSharedUsers);

                        if (chance(followChance)) /*If chance of following OP
                            also met (only occurs if post was also liked)*/
                        {
                            if (!hasFollower(curUser, curPost.op)) /*If user
                                is not already following OP*/
                            {
                                /*Making current user follow post's original
                                    poster*/
                                addFollower(curUser, curPost.op);
                            }
                        }
                    }
                }
                if (!justSharedUsers.isEmpty()) /*If at least 1 user had the
                    post shared to them this timestep (who will have a chance
                    of liking/sharing it in the next timestep)*/
                {
                    /*Discarding users to like (as has been used) & setting it
                        to all users who have a chance to like the post in the
                        next timestep*/
                    curPost.usersToLike = justSharedUsers;
                }
                else
                {
                    /*Marking post as complete (as there is are no users that
                        may share it in the next timestep and therefore no
                        chance of it being shared again)*/
                    curPost.complete = true;
                    curPost.usersToLike = null;
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