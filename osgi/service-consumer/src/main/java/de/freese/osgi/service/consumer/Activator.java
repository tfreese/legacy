/**
 * Created: 06.09.2016
 */
package de.freese.osgi.service.consumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.service.log.LogService;
import org.osgi.util.tracker.ServiceTracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import de.freese.osgi.service.api.IMyService;

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
    *
    */
    private ServiceTracker<LogService, LogService> logServiceTracker = null;

    /**
     *
     */
    private ServiceTracker<IMyService, IMyService> myServiceTracker = null;

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
    }

    /**
     * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(final BundleContext context) throws Exception
    {
        LOGGER.info("start");
        context.addServiceListener(this);

        // LogService
        this.logServiceTracker = new ServiceTracker<>(context, LogService.class, null);
        this.logServiceTracker.open();
        LogService logService = this.logServiceTracker.getService();
        logService.log(LogService.LOG_INFO, "OSGILogService");

        // MyService
        this.myServiceTracker = new ServiceTracker<>(context, IMyService.class, null);
        this.myServiceTracker.open();
        IMyService myService = this.myServiceTracker.getService();
        LOGGER.info(myService.getInfo());

        logService.log(this.myServiceTracker.getServiceReference(), LogService.LOG_INFO, "MyService");
    }

    /**
     * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(final BundleContext bundleContext) throws Exception
    {
        LOGGER.info("stop");
        bundleContext.removeServiceListener(this);

        this.logServiceTracker.close();
        this.logServiceTracker = null;

        this.myServiceTracker.close();
        this.myServiceTracker = null;
    }
}
