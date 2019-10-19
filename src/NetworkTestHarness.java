/* Network Test Harness by Moritz Bergemann
 *  Class for testing functionality of the created social network's methods.
 *  Created Date: 19/10/2019
 */

import static java.lang.System.out;

public class NetworkTestHarness
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

        //Delete User //TODO
        /*
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
        */

        //Add follower
        out.println("Adding followers to network 1:");
        out.println("user1 follows user2");
        network1.addFollower("user1", "user2");
        out.println("user2 follows user1");
        network1.addFollower("user2", "user1");
        out.println("user2 follows user5");
        network1.addFollower("user2", "user5");
        out.println("user4 follows user5");
        network1.addFollower("user4", "user5");
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
        out.println("Checking if network 1 has follower 'user5 follows user2'" +
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


    }
}
