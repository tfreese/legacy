package workerthread;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * @author Thomas Freese
 */
public class RunPattern
{
    /**
     *
     */
    private static final String WORKER_SERVER_URL = "rmi://localhost/workerThreadServer";

    // rmi://localhost:1099

    /**
     * @param arguments String[]
     * @throws RemoteException Falls was schief geht.
     */
    @SuppressWarnings("unused")
    public static void main(final String[] arguments) throws RemoteException
    {
        System.out.println("Example for the WorkerThread pattern");
        System.out.println("In this example, a ConcreteQueue object which uses a");
        System.out.println(" worker thread, will retrieve a number of objects from");
        System.out.println(" the server.");
        System.out.println();

        System.out.println("Running the RMI compiler (rmic)");
        System.out.println();

        // try
        // {
        // Process p1 =
        // Runtime.getRuntime().exec(
        // "/opt/sun-jdk-1.4.2.08/bin/rmic -classpath /home/tommy/workspace/AppliedJavaPatterns/src/
        // -d
        // /home/tommy/workspace/AppliedJavaPatterns/bin/ workerthread.ServerDataStoreImpl"
        // );
        // p1.waitFor();
        // }
        // catch (IOException exc)
        // {
        // System.err.println("Unable to run rmic utility. Exiting application.");
        // System.exit(1);
        // }
        // catch (InterruptedException exc)
        // {
        // System.err.println("Threading problems encountered while using the rmic utility.");
        // }

        System.out.println("Starting the rmiregistry");
        System.out.println();

        try
        {
            // Process rmiProcess =
            // Runtime.getRuntime().exec("/opt/sun-jdk-1.4.2.08/bin/rmiregistry");
            // Registry reg = LocateRegistry.createRegistry(1099);
            // Registry registry = LocateRegistry.getRegistry(10999);
            LocateRegistry.getRegistry(10999);

            Thread.sleep(1500);
        }
        catch (IOException exc)
        {
            System.err.println("Unable to start the rmiregistry: " + exc.getMessage());
            System.exit(1);
        }
        catch (InterruptedException exc)
        {
            System.err.println("Threading problems encountered when starting the rmiregistry.");
        }

        System.out.println("Creating the queue, which will be managed by the worker thread");
        System.out.println();

        ConcreteQueue workQueue = new ConcreteQueue();

        System.out.println("Creating the RMI server object, ServerDataStoreImpl");
        System.out.println();

        // ServerDataStore server = new ServerDataStoreImpl();
        new ServerDataStoreImpl();

        System.out.println("Creating AddressRetrievers and ContactRetreivers.");
        System.out.println(" These will placed in the queue, as tasks to be");
        System.out.println(" performed by the worker thread.");
        System.out.println();

        AddressRetriever firstAddr = new AddressRetriever(5280L, WORKER_SERVER_URL);
        AddressRetriever secondAddr = new AddressRetriever(2010L, WORKER_SERVER_URL);
        ContactRetriever firstContact = new ContactRetriever(5280L, WORKER_SERVER_URL);

        workQueue.put(firstAddr);
        workQueue.put(firstContact);
        workQueue.put(secondAddr);

        while (!secondAddr.isAddressAvailable())
        {
            try
            {
                Thread.sleep(1000);
            }
            catch (InterruptedException exc)
            {
                // Ignore
            }
        }

        System.out.println("WorkerThread completed the processing of its Tasks");
        System.out.println("Printing out the retrieved objects now:");
        System.out.println();
        System.out.println(firstAddr.getAddress());
        System.out.println(firstContact.getContact());
        System.out.println(secondAddr.getAddress());
    }
}
