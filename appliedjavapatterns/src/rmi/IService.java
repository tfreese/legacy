/**
 * Created: 04.01.2016
 */

package rmi;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Thomas Freese
 */
public interface IService extends Remote, Serializable
{
    /**
     *
     */
    public static final String SERVICE_HOST = "localhost";

    /**
     *
     */
    public static final String SERVICE_NAME = "myService";

    /**
     *
     */
    public static final int SERVICE_PORT = 1099;

    /**
     *
     */
    public static final String SERVICE_URL = String.format("rmi://%s:%d/%s", SERVICE_HOST, SERVICE_PORT, SERVICE_NAME);

    /**
     * @param message String
     * @return String
     * @throws RemoteException Falls was schief geht.
     */
    public String getDate(String message) throws RemoteException;
}
