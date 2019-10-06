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
    boolean runMenu;
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
                interactiveMenu();
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
        System.out.println("Program for simulating a social network of people" +
                "including followers and the creating, sharing & liking of " +
                "posts.");
        System.out.println("Command line parameters for usage:");
        System.out.println("None: display usage info (current)");
        System.out.println("\"-s\": Simulation Mode (automatically run " +
                "simulation of an imported network. Requires further command " +
                "line parameters (in the following order):");
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
                "network live");
    }

    /*Runs (and repeats) the menu for the program's interactive mode, and calls
     * the required functions to perform the selected tasks.
     */
    public static void interactiveMenu()
    {
        //TODO
    }

    /*Creates a network based on the imported simulation files and simulates
     *  the network using the imported like and follow probabilities.
     *  NOTE: How many timesteps???
     */
    public static void simulation(String netFile, String eventFile,
                                  double likeProb, double followProb)
    {
        //TODO
    }
}
