/**
 * Created: 04.01.2016
 */

package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * https://docs.oracle.com/javase/8/docs/technotes/guides/rmi/hello/hello-world.html<br>
 * https://docs.oracle.com/javase/tutorial/rmi/running.html<br>
 * -Djava.rmi.server.codebase=file:/home/tommy/git/misc/de.freese.appliedjavapatterns/target/classes/
 *
 * @author Thomas Freese
 */
public class MainServer
{
    /**
     * @param args String[]
     * @throws Exception Falls was schief geht.
     */
    public static void main(final String[] args) throws Exception
    {
        // if (System.getSecurityManager() == null)
        // {
        // System.setSecurityManager(new SecurityManager());
        // }

        // System.out.println("Starting the rmiregistry");
        // Runtime.getRuntime().exec("rmiregistry " + IService.SERVICE_PORT);

        IService service = new Service();
        IService stub = (IService) UnicastRemoteObject.exportObject(service, 0);

        Registry registry = LocateRegistry.getRegistry(IService.SERVICE_PORT);
        registry.rebind(IService.SERVICE_NAME, stub);

        System.out.println("RMI-Server ready...");
    }
}
