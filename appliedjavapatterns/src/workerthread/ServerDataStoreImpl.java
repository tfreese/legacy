package workerthread;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * @author Thomas Freese
 */
public class ServerDataStoreImpl extends UnicastRemoteObject implements ServerDataStore
{
    /**
     *
     */
    private static final long serialVersionUID = 755478423206644891L;

    /**
     *
     */
    private static final String WORKER_SERVER_SERVICE_NAME = "workerThreadServer";

    /**
     * Creates a new ServerDataStoreImpl object.
     *
     * @throws RemoteException Falls was schief geht.
     */
    public ServerDataStoreImpl() throws RemoteException
    {
        super();

        try
        {
            Naming.rebind(WORKER_SERVER_SERVICE_NAME, this);
        }
        catch (Exception exc)
        {
            System.err.println("Error using RMI to register the ServerDataStoreImpl " + exc);
        }
    }

    /**
     * @see workerthread.ServerDataStore#retrieveAddress(long)
     */
    @Override
    public Address retrieveAddress(final long addressID)
    {
        if (addressID == 5280L)
        {
            return new AddressImpl("Fine Dining", "416 Chartres St.", "New Orleans", "LA", "51720");
        }
        else if (addressID == 2010L)
        {
            return new AddressImpl("Mystic Yacht Club", "19 Imaginary Lane", "Mystic", "CT", "46802");
        }
        else
        {
            return new AddressImpl();
        }
    }

    /**
     * @see workerthread.ServerDataStore#retrieveContact(long)
     */
    @Override
    public Contact retrieveContact(final long contactID)
    {
        if (contactID == 5280L)
        {
            return new ContactImpl("Dwayne", "Dibley", "Accountant", "Virtucon");
        }

        return new ContactImpl();
    }
}
