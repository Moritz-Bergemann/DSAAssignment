/* Social Sim by Moritz Bergemann
 * Contains the user interface & calls functions from other classes to run
 *  the social simulation program. The manner in which the program is run is
 *  dependant on the given command line parameters.
 * NOTE: How does the '-s' version of the program running determine for how many
 *  timesteps the program should be run?
 */

import java.util.*;

public class SocialSim
{
    public static void main(String[] args)
    {
        //Taking actions based on command line parameters
        if (args.length == 0) /*If no arguments given (means should just
            display usage information)*/
        {
            usageInfo();
        }
        else if (args[0].equals("-s")) /*If Simulation Mode flag given*/
        {
            if (args.length == 5) /*If correct number of other parameters
                provided for simulation mode NOTE: Should this even be 8? Don't
                you need more information (i.e. timesteps)*/
            {
                simulation(args[1], args[2], Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]));
            }
            else
            {
                System.out.println("Invalid number of command line arguments" +
                        "given for simulation mode! Run without command line" +
                        "parameters for usage information.");
            }
        }
        else if (args[0].equals("-i")) /*If Interactive Mode flag given*/
        {
            if (args.length == 1) /*If no additional command line parameters
                given (as interactive mode doesn't take any*/
            {
                interactive();
            }
            else
            {
                System.out.println("Invalid number of command line arguments" +
                        "given for interactive mode! Run without command line" +
                        "parameters for usage information.");
            }
        }
        else
        {
            System.out.println("Invalid command line arguments given! Run " +
                    "without command line parameters for usage information.");
        }
    }

    /* Displays program usage information.
     */
    public static void usageInfo()
    {
        System.out.println("SocialSim by Moritz Bergemann (2019)");
        System.out.println("Program for simulating a social network of " +
                "people, including followers and the creating, sharing & " +
                "liking of posts.");
        System.out.println("Command line parameters for usage:");
        System.out.println("None: display usage info (current)");
        System.out.println("\"-s\": Simulation Mode (automatically run " +
                "simulation of an imported network. Requires further command " +
                "line arguments (in the following order):");
        System.out.println("\tnetfile: Name of the file containing the " +
                "initial network information");
        System.out.println("\teventfile: Name of the file containing " +
                "information on the events that have occurred on the network " +
                "before the start of the simulation");
        System.out.println("\tprob_like: Probability (as decimal 0.0 to 1.0) " +
                "that a person who has a post shared with them will like it, " +
                "sharing it with their followers");
        System.out.println("\tprob_foll: Probability (as decimal 0.0 to 1.0) " +
                "that a person who has a post shared with them will follow " +
                "the original poster");
        System.out.println("\"-i\": Interactive Mode (allows the user to " +
                "manually load/save networks and configure parts of the " +
                "network live. Requires no further command line arguments.");
    }


    /*Creates a network based on the imported simulation files and simulates
     *  the network using the imported like and follow probabilities.
     *  NOTE: How many timesteps???
     */
    public static void simulation(String netFile, String eventFile,
                                  double likeProb, double followProb)
    {
        //Creating network for use in simulation
        Network network = new Network();
        //TODO
    }

    /*Runs (and repeats) the menu for the program's interactive mode, and calls
     * the required functions to perform the selected tasks.
     */
    public static void interactive()
    {
        Scanner sc = new Scanner(System.in);
        String optionText = "\t1. Load network" + "\n" +
                "\t2. Set probabilities" + "\n" +
                "\t3. User operations (find/insert/delete users)" + "\n" +
                "\t4. Relationship operations (find/insert/remove follower/" +
                "followed relationships)" + "\n" +
                "\t5. Create post" + "\n" +
                "\t6. Display network" + "\n" +
                "\t7. Statistics" + "\n" +
                "\t8. Next Timestep" + "\n" +
                "\t9. Save network" + "\n" +
                "\t10. Exit Program";

        //Creating network for use in simulation
        Network network = new Network();

        //Running menu
        int menuChoice;
        boolean end = false;
        do
        {
            System.out.println("MAIN MENU:");
            System.out.println("Please choose one of the following options:");
            System.out.println(optionText);
            menuChoice = inputInt("Choice", 1, 10);

            switch (menuChoice) /*NOTE: Maybe move a lot of this into
                NetworkManager if you can extensively modify the graph*/
            {
                case 1: //Load network
                    System.out.print("Input name of network file to read: ");
                    String netFileName = sc.nextLine();
                    try
                    {
                        //Reading file at filename input by user
                        DSALinkedList loadList =
                                FileManager.readFile(netFileName);

                        /*Attempting to create new network from contents of
                            network file & setting used network to it if
                            successful (otherwise exception will abort)*/
                        network = NetworkManager.loadNetwork(loadList);
                        System.out.println("File read successfully. The " +
                                "previous network has been overwritten.");
                    }
                    catch (IllegalArgumentException i)
                    {
                        System.out.println(i.getMessage());
                        System.out.println("The existing network has not " +
                                "been changed.");
                    }
                    break;
                case 2: //Set probabilities
                    network.setLikeChance(inputDouble("Input " +
                            "probability of liking/sharing a post " +
                            "(current is " + network.getLikeChance() +
                            ")", 0.0, 1.0));
                    network.setFollowChance(inputDouble("Input " +
                            "probability of following the original " +
                            "poster (current is " +
                            network.getFollowChance() + ")", 0.0, 1.0));
                    break;
                case 3: //User operations
                    userMenu(network);
                    break;
                case 4: //Relationship operations
                    relationshipMenu(network);
                    break;
                case 5: //Create post
                    System.out.print("Input name of user to make post: ");
                    String postUser = sc.nextLine();
                    System.out.print("Input content of post: ");
                    String postContent = sc.nextLine();
                    double postClickbait = inputDouble("Input " +
                            "clickbait factor", 0.0, 100000.0);
                    try
                    {
                        network.makePost(postUser, postContent, postClickbait);
                        System.out.println("Post created successfully.");
                    }
                    catch (IllegalArgumentException i)
                    {
                        System.out.println("Failed to create post: " +
                                i.getMessage());
                    }

                    break;
                case 6: //Display Network
                    //Displaying network as graph adjacency list (NOTE: Is this good enough?)
                    network.displayAsList();
                    break;
                case 7: //Statistics menu
                    statisticsMenu(network);
                    break;
                case 8: //Next timestep NOTE: UNTESTED
                    if (network.getLikeChance() > 0.0 &&
                            network.getFollowChance() > 0.0) /*If like & follow
                                chance settings have been set*/
                    {
                        network.timeStep();
                    }
                    else
                    {
                        System.out.println("Failed: Please set the like & " +
                                "follow chances for the simulation (option " +
                                "2) before running the first timestep.");
                    }
                    break;
                case 9: //Save network
                    if (network.getUserCount() > 0)
                    {
                        System.out.print("Input filename to save to: ");
                        String filename = sc.nextLine();
                        try
                        {
                            //Creating network file format list from network
                            DSALinkedList saveList =
                                    NetworkManager.saveNetwork(network);
                            FileManager.writeFile(filename, saveList);
                        }
                        catch (IllegalArgumentException i)
                        {
                            System.out.println("Failed to save network: " +
                                    i.getMessage());
                        }
                    }
                    else
                    {
                        System.out.println("Nothing to save - network is " +
                                "empty");
                    }
                    break;
                case 10: //Exit
                    System.out.println("Exiting...");
                    end = true;
                    break;

            }
            System.out.println();
        } while (!end);
    }

    /* Displays the statistics menu to the user & returns the information
     *  requested.
     */
    public static void statisticsMenu(Network network)
    {
        int menuChoice;
        String inputUser;
        Scanner sc = new Scanner(System.in);
        System.out.println("Statistics Menu:");
        System.out.println("Please choose one of the following options:");
        System.out.println("\t1. Show posts in order of popularity\n" +
                "\t2. Show users in order of popularity\n" +
                "\t3. Show a user record\n" +
                "\t4. Cancel");
        menuChoice = inputInt("Choice", 1, 4);
        switch (menuChoice)
        {
            case 1: //Show posts by popularity TODO Add more info to this (namely who has liked posts)
                if (network.getPostCount() > 0)
                {
                    int postNum = 1;
                    Iterator popularPostIter =
                            network.getPostsByLikes().iterator();
                    while (popularPostIter.hasNext())
                    {
                        System.out.println(postNum + ".");
                        System.out.println(popularPostIter.next());
                        postNum++;
                    }
                }
                else
                {
                    System.out.println("Nothing to show: No posts have been " +
                            "made.");

                }
                break;
            case 2: //Show users by popularity
                if (network.getUserCount() > 0)
                {
                    int userNum = 1;
                    Iterator popularUserIter =
                            network.getUsersByFollowers().iterator();
                    while (popularUserIter.hasNext())
                    {
                        System.out.println(userNum + ".");
                        System.out.println(popularUserIter.next());
                        userNum++;
                    }
                }
                else
                {
                    System.out.println("Nothing to show: Network has no users");
                }
                break;
            case 3: //Show user record
                System.out.print("Input name of user to display record:");
                inputUser = sc.nextLine();
                try
                {
                    System.out.println(network.getUserInfo(inputUser));
                }
                catch (IllegalArgumentException i)
                {
                    System.out.println("Failed to display user record: " +
                            i.getMessage());
                }
                break;
        }
    }

    /* Displays menu & performs functionality for user operations
     */
    public static void userMenu(Network network)
    {
        int menuChoice;
        String inputUser;
        Scanner sc = new Scanner(System.in);

        System.out.println("User Operations Menu:");
        System.out.println("Please choose one of the following options:");
        System.out.println("\t1. Find User & Display Info\n" +
                "\t2. Insert New User\n" +
                "\t3. Delete Existing User\n" +
                "\t4. Cancel");
        menuChoice = inputInt("Choice", 1, 4);
        switch (menuChoice)
        {
            case 1: //Find user
                System.out.print("Input name of user to display information: ");
                inputUser = sc.nextLine();
                if (network.hasVertex(inputUser))
                {
                    System.out.println("User exists in network!");
                }
                else
                {
                    System.out.println("User does not exist in network.");
                }
                break;
            case 2: //Insert user
                System.out.print("Input name of new user to insert: ");
                inputUser = sc.nextLine();
                try
                {
                    network.addUser(inputUser);
                    System.out.println("User added successfully.");
                }
                catch (IllegalArgumentException i)
                {
                    System.out.println("Failed to add user: " +
                            i.getMessage());
                }
                break;
            case 3: //Delete user
                System.out.print("Input name of user to delete: ");
                inputUser = sc.nextLine();
                try
                {
                    network.removeUser(inputUser);
                    System.out.println("User deleted successfully.");
                }
                catch (IllegalArgumentException i)
                {
                    System.out.println("Failed to delete user: " +
                            i.getMessage());
                }
                break;
        }
    }

    /* Displays menu & performs functionality for follower/followed relationship
     *  operations
     */
    public static void relationshipMenu(Network network)
    {
        int menuChoice;
        Scanner sc = new Scanner(System.in);
        String inUser1, inUser2;

        System.out.println("Relationship Operations Menu:");
        System.out.println("Please choose one of the following options:");
        System.out.println("\t1. Determine if graph contains relationship\n" +
                "\t2. Add Follower-Followed Relationship\n" +
                "\t3. Delete Existing Follower-Followed Relationship\n" +
                "\t4. Cancel");
        menuChoice = inputInt("Choice", 1, 4);

        switch (menuChoice)
        {
            case 1: //Find relationship
                System.out.print("Input name of following user: ");
                inUser1 = sc.nextLine();
                System.out.print("Input name of followed user: ");
                inUser2 = sc.nextLine();
                try
                {
                    if (network.hasFollower(inUser1, inUser2))
                    {
                        System.out.println("The relationship exists.");
                    }
                    else
                    {
                        System.out.println("The relationship does not exist.");
                    }
                }
                catch (IllegalArgumentException i)
                {
                    System.out.println("Failed to search for relationship: " +
                            i.getMessage());
                }
                break;
            case 2: //Insert relationship
                System.out.print("Input name of following user: ");
                inUser1 = sc.nextLine();
                System.out.print("Input name of followed user: ");
                inUser2 = sc.nextLine();
                try
                {
                    network.addFollower(inUser1, inUser2);
                    System.out.println("Relationship added successfully.");
                }
                catch (IllegalArgumentException i)
                {
                    System.out.println("Failed to add relationship: " +
                            i.getMessage());
                }
                break;
            case 3: //Delete relationship
                System.out.print("Input name of following user: ");
                inUser1 = sc.nextLine();
                System.out.print("Input name of followed user: ");
                inUser2 = sc.nextLine();
                try
                {
                    network.removeFollower(inUser1, inUser2);
                    System.out.println("Relationship deleted successfully.");
                }
                catch (IllegalArgumentException i)
                {
                    System.out.println("Failed to remove relationship: " +
                            i.getMessage());
                }
                break;
        }
    }

    /* Processes and validates user input of an integer using the imported
     *  prompt between the imported minimum and maximum
     */
    public static int inputInt(String initialPrompt, int min, int max)
    {
        Scanner sc = new Scanner(System.in);
        int input = 0;

        initialPrompt += ": ";
        String prompt = initialPrompt;

        //Repeating input prompt until user input is within range
        do
        {
            try
            {
                System.out.print(prompt);
                input = sc.nextInt();

                /*Modifying prompt to include message for input out of allowed
                    range (in case input was out of range and loop is run
                    again)*/
                prompt = "Invalid Input! Please input an integer between " +
                        min + " & " + max + " (inclusive)" + "\n" +
                        initialPrompt;
            }
            catch (InputMismatchException i)
            {
                //Modifying prompt to include message for invalid integer input
                prompt = "Invalid Input! Please input an integer (whole number)"
                        + "\n" + initialPrompt;

                sc.nextLine(); //Clears input buffer for next input

                //Putting user input outside valid range so loop continues
                input = min - 1;
            }
        } while (input < min || input > max);

        return input;
    }

    /* Processes and validates user input of a double using the imported
     *  prompt between the imported minimum and maximum
     */
    public static double inputDouble(String initialPrompt, double min,
                                     double max)
    {
        Scanner sc = new Scanner(System.in);
        double input = 0.0;

        initialPrompt += ": ";
        String prompt = initialPrompt;

        //Repeating input prompt until user input is within range
        do
        {
            try
            {
                System.out.print(prompt);
                input = sc.nextDouble();

                /*Modifying prompt to include message for input out of allowed
                    range (in case input was out of range and loop is run
                    again)*/
                prompt = "Invalid Input! Please input a decimal number " +
                        "between " + min + " & " + max + " (inclusive)" + "\n" +
                        initialPrompt;
            }
            catch (InputMismatchException i)
            {
                /*Modifying prompt to include message for invalid data type
                    input*/
                prompt = "Invalid Input! Please input a decimal number"
                        + "\n" + initialPrompt;

                sc.nextLine(); //Clears input buffer for next input

                //Putting user input outside valid range so loop continues
                input = min - 1;
            }
        } while (input < min || input > max);

        return input;
    }

    /* Returns whether the two imported double values are equal up to an
     *  imported tolerance value
     */
    public static boolean doubleCompare(double n1, double n2)
    {
        return Math.abs(n1 - n2) > 0.0001;
    }
}
