/* Social Sim by Moritz Bergemann
 * Contains the user interface & calls functions from other classes to run
 *  the social simulation program. The manner in which the program is run is
 *  dependant on the given command line parameters.
 * NOTE: How does the '-s' version of the program running determine for how many
 *  timesteps the program should be run?
 */
import java.security.cert.CertificateEncodingException;
import java.util.*;

public class SocialSim {
    boolean runMenu;

    public static void main(String[] args) {
        //Taking actions based on command line parameters
        if (args.length == 0) /*If no arguments given (means should just
            display usage information)*/ {
            usageInfo();
        } else if (args[0].equals("-s")) /*If Simulation Mode flag given*/ {
            if (args.length == 5) /*If correct number of other parameters
                provided for simulation mode NOTE: Should this even be 8? Don't
                you need more information (i.e. timesteps)*/ {
                simulation(args[1], args[2], Double.parseDouble(args[3]),
                        Double.parseDouble(args[4]));
            } else {
                System.out.println("Invalid number of command line arguments" +
                        "given for simulation mode! Run without command line" +
                        "parameters for usage information.");
            }
        } else if (args[0].equals("-i")) /*If Interactive Mode flag given*/ {
            if (args.length == 1) /*If no additional command line parameters
                given (as interactive mode doesn't take any*/ {
                interactiveMenu();
            } else {
                System.out.println("Invalid number of command line arguments" +
                        "given for interactive mode! Run without command line" +
                        "parameters for usage information.");
            }
        } else {
            System.out.println("Invalid command line arguments given! Run " +
                    "without command line parameters for usage information.");
        }
    }

    /* Displays program usage information.
     */
    public static void usageInfo() {
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

    /*Runs (and repeats) the menu for the program's interactive mode, and calls
     * the required functions to perform the selected tasks.
     */
    public static void interactiveMenu() {
        Scanner sc = new Scanner(System.in);
        String netFile, eventFile, postUser;
        String optionText = "\t1. Load network" + "\n" +
                "\t2. Set probabilities" + "\n" +
                "\t3. User operations (find/insert/delete users)" + "\n" +
                "\t4. Relationship operations (find/insert/remove follower/" +
                "followed relationships)" + "\n" +
                "\t5. Create post" + "\n" +
                "\t6. Display network" + "\n" +
                "\t7. Statistics" + "\n" +
                "\t8. Next Timestep" + "\n" +
                "\t9. Save network";

        //Creating network for use in simulation
        Network network = new Network();

        //Running menu
        int menuChoice;
        boolean end = false;
        do {
            System.out.println("MAIN MENU:");
            System.out.println("Please choose one of the following options:");
            System.out.println(optionText);
            menuChoice = inputInt("Choice", 1, 9);

            switch (menuChoice) /*NOTE: Maybe move a lot of this into
                NetworkManager if you can extensively modify the graph*/ {
                case 1:
                    System.out.print("Input name of network file to read: ");
                    netFile = sc.nextLine();
                    //TODO read network file & use to create network

                    System.out.print("Input name of events file to read: ");
                    eventFile = sc.nextLine();
                    //TODO read event file
                    break;
                case 2:
                    /*TODO*/
                    inputDouble("Input probability of liking a post",
                            0.0, 1.0);
                    inputDouble("Input probability of following the original poster",
                            0.0, 1.0);
                    break;
                case 3:
                    //TODO
                    break;
                case 4:
                    //TODO
                    break;
                case 5:
                    System.out.print("Input name of user to make post: ");
                    postUser = sc.nextLine();
                    //TODO
                    break;
                case 6:
                    //TODO (How? Just do a traversal? Do you have to display followers?)
                    break;
                case 7:
                    statisticsMenu(network);
                    break;
                case 8:
                    //TODO
                    break;
                case 9:
                    System.out.println("Exiting...");
                    end = true;
                    break;
            }
        } while (!end);
    }

    /*Creates a network based on the imported simulation files and simulates
     *  the network using the imported like and follow probabilities.
     *  NOTE: How many timesteps???
     */
    public static void simulation(String netFile, String eventFile,
                                  double likeProb, double followProb) {
        //Creating network for use in simulation
        Network network = new Network();
        //TODO
    }

    /* Displays the statistics menu to the user & returns the information
     *  requested.
     */
    public static void statisticsMenu(Network network) {
        int menuChoice;
        String recordUser;
        Scanner sc = new Scanner(System.in);
        System.out.println("\t1. Show posts in order of popularity\n" +
                "\t2. Show users in order of popularity\n" +
                "\t3. Show a user record"); //TODO any more?
        menuChoice = inputInt("Choice", 1, 3);
        switch (menuChoice) {
            case 1:
                //TODO
                break;
            case 2:
                //TODO
                break;
            case 3:
                System.out.print("Input name of user to display record:");
                recordUser = sc.nextLine();
                //TODO display user record
        }

    }

    /* Processes and validates user input of an integer using the imported
     *  prompt between the imported minimum and maximum
     */
    public static int inputInt(String initalPrompt, int min, int max) {
        Scanner sc = new Scanner(System.in);
        int input = 0;
        String prompt = initalPrompt;

        //Repeating input prompt until user input is within range
        do {
            try {
                System.out.print(prompt);
                input = sc.nextInt();

                /*Modifying prompt to include message for input out of allowed
                    range (in case input was out of range and loop is run
                    again)*/
                prompt = "Invalid Input! Please input an integer between " +
                        min + " & " + max + " (inclusive)";
            } catch (NumberFormatException n) {
                //Modifying prompt to include message for invalid integer input
                prompt = "Invalid Input! Please input an integer (whole number)"
                        + "\n" + initalPrompt;

                //Putting user input outside valid range so loop continues
                input = min - 1;
            }
        } while (input < min || input > max);

        return input;
    }

    /* Processes and validates user input of a double using the imported
     *  prompt between the imported minimum and maximum
     */
    public static double inputDouble(String initalPrompt, double min,
                                     double max) {
        Scanner sc = new Scanner(System.in);
        double input = 0.0;
        String prompt = initalPrompt;

        //Repeating input prompt until user input is within range
        do {
            try {
                System.out.print(prompt);
                input = sc.nextDouble();

                /*Modifying prompt to include message for input out of allowed
                    range (in case input was out of range and loop is run
                    again)*/
                prompt = "Invalid Input! Please input a decimal number " +
                        "between " + min + " & " + max + " (inclusive)";
            } catch (NumberFormatException n) {
                /*Modifying prompt to include message for invalid data type
                    input*/
                prompt = "Invalid Input! Please input a decimal number"
                        + "\n" + initalPrompt;

                //Putting user input outside valid range so loop continues
                input = min - 1;
            }
        } while (input < min || input > max);

        return input;
    }


}
