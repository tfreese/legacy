/**
 * Created: 06.09.2016
 */
package de.freese.osgi.logservice;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Freese
 */
public class Activator implements BundleActivator, ServiceListener
{
    /**
    *
    */
    private static final Logger LOGGER = LoggerFactory.getLogger(Activator.class);

    /**
     * Erstellt ein neues {@link Activator} Object.
     */
    public Activator()
    {
        super();
    }

    /**
     * @see org.osgi.framework.ServiceListener#serviceChanged(org.osgi.framework.ServiceEvent)
     */
    @Override
    public void serviceChanged(final ServiceEvent se)
    {
        String status;

        switch (se.getType())
        {
            case ServiceEvent.MODIFIED:
                status = "modified";
                break;
            case ServiceEvent.REGISTERED:
                status = "registered";
                break;
            case ServiceEvent.UNREGISTERING:
                status = "unregistered";
                break;
            default:
                status = "unknown";
        }

        LOGGER.info("service changed = {}", status);
        // System.out.println("Activator.serviceChanged(): " + status);
    }

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(final BundleContext context) throws Exception
    {
        LOGGER.info("start");
        context.addServiceListener(this);

        context.registerService(LogService.class.getName(), new Slf4JLogService(), null);
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(final BundleContext bundleContext) throws Exception
    {
        LOGGER.info("stop");
        bundleContext.removeServiceListener(this);
    }
}
