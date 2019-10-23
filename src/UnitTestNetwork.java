/* Network Test Harness by Moritz Bergemann
 *  Class for testing functionality of the created social network's methods.
 *  Created Date: 19/10/2019
 */

import static java.lang.System.out;

import java.time.format.DateTimeFormatter;
import java.util.*;

public class UnitTestNetwork
{
    public static void main(String[] args)
    {
        out.println("Creating 3 network objects for testing");
        Network network1 = new Network();
        Network network2 = new Network();
        Network network3 = new Network();

        //Add User
        out.println("Adding 5 users (user1-5) to network 1:");
        for (int ii = 0; ii < 5; ii++)
        {
            out.println("Adding user \"user" + (ii + 1) + "\"");
            network1.addUser("user" + (ii+1));
        }
        out.println("Attempting to add user 'user1' to network 1 (already " +
                "exists)");
        try
        {
            network1.addUser("user1");
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught:" + i.getMessage());
        }
        out.println();

        //Add follower
        out.println("Adding followers to network 1:");
        out.println("user1 follows user2");
        network1.addFollower("user1", "user2");
        out.println("user2 follows user1");
        network1.addFollower("user2", "user1");
        out.println("user2 follows user5");
        network1.addFollower("user2", "user5");
        out.println("user1 follows user5");
        network1.addFollower("user4", "user5");
        out.println("user5 follows user1");
        network1.addFollower("user5", "user1");
        out.println("user1 follows user4");
        network1.addFollower("user1", "user4");
        out.println("user3 follows user2");
        network1.addFollower("user3", "user2");
        out.println("user1 follows user3");
        network1.addFollower("user1", "user3");
        out.println("user5 follows user3");
        network1.addFollower("user5", "user3");
        out.println("Attempting to add follower 'user2 follows user5' to " +
                "network 1 (already exists)");
        try
        {
            network1.addFollower("user2", "user5");
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught:" + i.getMessage());
        }
        out.println("Attempting to add follower 'user6 follows user9' to " +
                "network 1 (users do not exist)");
        try
        {
            network1.addFollower("user6", "user9");
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught:" + i.getMessage());
        }
        out.println();

        out.println("Displaying network 1 (for readability)");
        network1.displayAsList();
        out.println();

        //Has follower
        out.println("Checking if network 1 has follower 'user1 follows user2'" +
                " (true):" + network1.hasFollower("user1", "user2"));
        out.println("Checking if network 1 has follower 'user4 follows user1'" +
                " (false):" + network1.hasFollower("user5", "user2"));
        out.println("Attempting to see if network 1 has follower 'user100 " +
                "follows user4' (1st user doesn't exist)");
        try
        {
            network1.addFollower("user100", "user4");
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught:" + i.getMessage());
        }
        out.println();

        //Delete User
        out.println("Deleting user 'user3' from network 1...");
        network1.removeUser("user3");
        out.println("Attempting to delete user 'user3' to network 1 (already " +
                "deleted)");
        try
        {
            network1.removeUser("user3");
            out.println("Succeeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("Exception caught:" + i.getMessage());
        }
        out.println();


        //Remove follower
        out.println("Removing followers from network 1:");
        out.println("user2 follows user1");
        network1.removeFollower("user2", "user1");
        out.println("user4 follows user5");
        network1.removeFollower("user4", "user5");
        out.println("Attempting to remove follower 'user4 follows user5' from" +
                "network 1 (already removed)");
        try
        {
            network1.removeFollower("user4", "user5");
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught:" + i.getMessage());
        }
        out.println("Attempting to remove follower 'user2 follows user9' from" +
                "network 1 (2nd user does not exist)");
        try
        {
            network1.removeFollower("user2", "user9");
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught:" + i.getMessage());
        }
        out.println();

        out.println("Displaying network 1 (for readability)");
        network1.displayAsList();
        out.println();

        //Getting followers & posts
        out.println("Getting list of users in network 1:");
        DSALinkedList userList = network1.getUserList();
        Iterator userIter = userList.iterator();
        while (userIter.hasNext()) { out.print((String)userIter.next() + " "); } out.println();
        out.println("Getting list of followers to user1 in network 1:");
        DSALinkedList followerList = network1.getFollowers("user1");
        Iterator followerIter = followerList.iterator();
        while (followerIter.hasNext()) { out.print((String)followerIter.next() + " "); } out.println();
        out.println("Attempting to get follower list of nonexistant user user11");
        try
        {
            network1.getFollowers("user11");
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught:" + i.getMessage());
        }
        out.println();

        //Setting network probabilities
        out.println("Setting network's like & follow probabilities to 0.5");
        network1.setLikeChance(1.0);
        network1.setFollowChance(1.0);
        out.println("Attempting to set like chance to -0.5 in network 2");
        try
        {
            network2.setLikeChance(-0.5);
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught: " + i.getMessage());
        }
        out.println("Attempting to set follow chance to 1.01 in network 2");
        try
        {
            network2.setFollowChance(1.01);
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught: " + i.getMessage());
        }
        out.println();

        //Make Posts
        out.println("Adding 3 posts to network 1");
        out.println("Adding post by user1 (content 'this is post1')");
        network1.makePost("user1", "this is post1", 1.0);
        out.println("Adding post by user4 (content 'this is post2') (CLICKBAIT FACTOR 2)");
        network1.makePost("user4", "this is post2", 2);
        out.println("Adding post by user1 (content 'this is post3')");
        network1.makePost("user1", "this is post3", 1.0);
        out.println("Attempting to create post by non-existent user11");
        try
        {
            network1.makePost("user11", "invalid post", 1.0);
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught: " + i.getMessage());
        }
        out.println("Attempting to create post with empty content");
        try
        {
            network1.makePost("user1", "", 1.0);
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught: " + i.getMessage());
        }
        out.println("Attempting to create post where content contains colon");
        try
        {
            network1.makePost("user1", "invalid : post", 1.0);
            out.println("\tSucceeded (SHOULDN'T HAVE)");
        }
        catch (IllegalArgumentException i)
        {
            out.println("\tException caught: " + i.getMessage());
        }
        out.println();

        //Display Posts
        out.println("Displaying all of network 1's posts by popularity:");
        Iterator postIter = network1.getPostsByLikes().iterator();
        while (postIter.hasNext()) { out.println(postIter.next()); }
        out.println();

        //Display Users
        out.println("Displaying all of network 1's users by number of followers:");
        userIter = network1.getUsersByFollowers().iterator();
        while (userIter.hasNext()) { out.println(userIter.next()); }
        out.println();

        //AllStale
        out.println("Checking if all posts in network 1 are stale (shouldn't be) :" + network1.allPostsStale());
        out.println();

        //Timestep
        out.println("Running 3 timesteps in network 1");
        network1.timeStep();
        network1.timeStep();
        network1.timeStep();


        out.println("Again displaying all of network 1's posts by popularity (after timesteps):");
        postIter = network1.getPostsByLikes().iterator();
        while (postIter.hasNext()) { out.println(postIter.next()); }
        out.println();

        out.println("Checking if all posts in network 1 are stale after timesteps (should be) :" + network1.allPostsStale());
        out.println();

        out.println("Redisplaying network 1 (after timesteps):");
        network1.displayAsList();

        out.println();
        out.println();

    }
}