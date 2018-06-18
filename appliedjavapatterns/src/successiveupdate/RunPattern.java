package successiveupdate;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Thomas Freese
 */
public class RunPattern
{
    /**
     * @param arguments String[]
     */
    public static void main(final String[] arguments)
    {
        System.out.println("Example for the SuccessiveUpdate pattern");
        System.out.println("This code provides a basic demonstration");
        System.out.println(" of how the client pull form of this pattern");
        System.out.println(" could be applied.");
        System.out.println("In this case, a change made by a client to a");
        System.out.println(" central Task object is subsequently retrieved");
        System.out.println(" and displayed by another client.");

        System.out.println("Running the RMI compiler (rmic)");
        System.out.println();

        try
        {
            Process process = Runtime.getRuntime().exec("rmic -d target/classes -classpath target/classes successiveupdate.ClientPullServerImpl");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())))
            {
                reader.lines().forEach(System.out::println);
            }

            System.out.println();
            System.out.println("rmic exit value: " + process.waitFor());
        }
        catch (IOException exc)
        {
            System.err.println("Unable to run rmic utility. Exiting application.");
            System.exit(1);
        }
        catch (InterruptedException exc)
        {
            System.err.println("Threading problems encountered while using the rmic utility.");
        }

        System.out.println();
        System.out.println("Starting the rmiregistry");
        System.out.println();

        try
        {
            // Process rmiProcess =
            Runtime.getRuntime().exec("rmiregistry");
            Thread.sleep(15000);
        }
        catch (IOException exc)
        {
            System.err.println("Unable to start the rmiregistry. Exiting application.");
            System.exit(1);
        }
        catch (InterruptedException exc)
        {
            System.err.println("Threading problems encountered when starting the rmiregistry.");
        }

        System.out.println("Creating the ClientPullServer and two PullClient objects");

        new ClientPullServerImpl();
        PullClient clientOne = new PullClient("Thing I");
        PullClient clientTwo = new PullClient("Thing II");

        clientOne.requestTask("First work step");
        clientTwo.requestTask("First work step");

        try
        {
            Thread.sleep(10000);
        }
        catch (InterruptedException exc)
        {
            // Ignore
        }

        Task task = clientOne.getUpdatedTask();

        task.setTaskDetails("Trial for task update");
        clientOne.updateTask(task);

        Task newTask = clientTwo.getUpdatedTask();

        newTask.setTaskDetails("New details string");
        clientTwo.updateTask(newTask);
    }
}
