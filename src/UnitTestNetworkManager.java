/* Network Manager Test Harness by Moritz Bergemann
 *  Test harness for unit tests of network manager class
 *  Since NetworkManager class is inherently correlated with Network class,
 *      functionality of this test harness depends on functionality of Network
 *      class
 */

import static java.lang.System.out;

public class UnitTestNetworkManager
{
    public static void main(String[] args)
    {
        //NetworkManager Tests
        out.println("Reading in network from 'netfile1.txt' (network 1)");
        DSALinkedList networkFile1 = FileManager.readFile("netfile1.txt");
        Network fileNetwork1 = NetworkManager.loadNetwork(networkFile1);
        out.println();

        out.println("Displaying network 1");
        fileNetwork1.displayAsList();
        out.println();

        out.println("Reading in network from 'netfile2.txt'");
        DSALinkedList networkFile2 = FileManager.readFile("netfile2.txt");
        Network fileNetwork2 = NetworkManager.loadNetwork(networkFile2);
        out.println("Applying events file 'eventsfile2-2.txt' to read network");
        DSALinkedList eventsFile2_2 = FileManager.readFile("eventsfile2-2.txt");
        NetworkManager.applyEvents(fileNetwork2, eventsFile2_2);
        out.println();

        out.println("Displaying network 2");
        fileNetwork2.displayAsList();
        out.println();

        out.println("Setting network 1's probabilities to 1.0");
        fileNetwork1.setFollowChance(1.0); fileNetwork1.setLikeChance(1.0);
        out.println("Making post in network 1:");
        out.println("Emperor: TEST");
        fileNetwork1.makePost("Emperor", "TEST", 1.0);
        out.println("Applying 3 timesteps to network 1");
        fileNetwork1.timeStep();
        fileNetwork1.timeStep();
        fileNetwork1.timeStep();
        out.println();

        out.println("Redisplaying network 1 after timesteps:");
        fileNetwork1.displayAsList();
        out.println();

        out.println("Saving network 1 to NetworkTestOut1.txt");
        DSALinkedList saveList = NetworkManager.saveNetwork(fileNetwork1);
        FileManager.writeFile("NetworkTestOut.txt", saveList, false);
        out.println();

        out.println("Saving log of network 1's current timestep to NetworkTestLog.txt");
        DSALinkedList logList = NetworkManager.logTimeStep(fileNetwork1);
        FileManager.writeFile("NetworkTestLog.txt", logList, false);
        out.println();

        out.println("Reloading & displaying saved network 1:");
        DSALinkedList reloadedFile = FileManager.readFile("NetworkTestOut.txt");
        Network reloadedNetwork = NetworkManager.loadNetwork(reloadedFile);
        reloadedNetwork.displayAsList();
    }
}
