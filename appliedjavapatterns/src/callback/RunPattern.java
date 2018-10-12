package callback;

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
    @SuppressWarnings("unused")
    public static void main(final String[] arguments)
    {
        // if (System.getSecurityManager() == null)
        // {
        // System.setSecurityManager(new SecurityManager());
        // }

        System.out.println("Example for the Callback pattern");
        System.out.println("This code will run two RMI objects to demonstrate");
        System.out.println(" callback capability. One will be CallbackClientImpl,");
        System.out.println(" which will request a project from the other remote");
        System.out.println(" object, CallbackServerImpl.");
        System.out.println("To demonstrate how the Callback pattern allows the");
        System.out.println(" client to perform independent processing, the main");
        System.out.println(" progam thread will go into a wait loop until the");
        System.out.println(" server sends the object to its client.");
        System.out.println();

        System.out.println("Running the RMI compiler (rmic)");
        System.out.println();

        try
        {
            Process process =
                    Runtime.getRuntime().exec("rmic -d target/classes -classpath target/classes callback.CallbackServerImpl callback.CallbackClientImpl");

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

        System.out.println("Creating the client and server objects");
        System.out.println();

        new CallbackServerImpl();
        CallbackClientImpl callbackClient = new CallbackClientImpl();

        System.out.println("CallbackClientImpl requesting a project");
        callbackClient.requestProject("New Java Project");

        try
        {
            while (!callbackClient.isProjectAvailable())
            {
                System.out.println("Project not available yet; sleeping for 2 seconds");
                Thread.sleep(2000);
            }
        }
        catch (InterruptedException exc)
        {
            // Ignore
        }

        System.out.println("Project retrieved: " + callbackClient.getProject());
    }
}
