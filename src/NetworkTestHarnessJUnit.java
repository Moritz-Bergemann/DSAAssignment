/* Network Test Harness by Moritz Bergemann
 *  Class for testing functionality of the created social network's methods.
 */

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NetworkTestHarnessJUnit
{
    private Network network;

    @BeforeEach
    public void setUp()
    {
        network = new Network();
    }

    @AfterEach
    public void tearDown()
    {
        network = null;
    }

    @Test
    public void addUser()
    {
        for (int ii = 1; ii <= 4; ii++)
        {
            network.addUser("user" + ii);
            assertTrue(network.hasVertex("user" + ii),
                    "User user" + ii + " not in network");
            assertThrows(IllegalArgumentException.class, () ->
            {network.addUser("user" + ii);});
        }

    }

    @Test
    public void addFollower()
    {
    }

    @Test
    public void hasFollower()
    {
    }

    @Test
    public void removeFollower()
    {
    }

    @Test
    public void makePost()
    {
    }

    @Test
    public void timeStep()
    {
    }
}
