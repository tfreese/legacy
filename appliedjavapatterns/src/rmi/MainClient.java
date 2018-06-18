/**
 * Created: 04.01.2016
 */

package rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * @author Thomas Freese
 */
public class MainClient
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

        System.out.println("Main.startClient()");

        Registry registry = LocateRegistry.getRegistry(IService.SERVICE_HOST, IService.SERVICE_PORT);
        IService service = (IService) registry.lookup(IService.SERVICE_URL);
        // IService service = (IService) Naming.lookup(IService.SERVICE_URL);

        System.out.println(service.getDate("Hello World"));
    }

}
