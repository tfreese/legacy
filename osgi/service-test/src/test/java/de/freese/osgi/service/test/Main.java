/**
 * Created: 06.09.2016
 */
package de.freese.osgi.service.test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.Set;
import org.apache.commons.io.FileUtils;
import org.apache.felix.main.AutoProcessor;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.Constants;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.launch.Framework;
import org.osgi.framework.launch.FrameworkFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Autostart: java -jar bin/felix.jar -b /path/to/dir<br>
 * : (org.apache.felix.main.Main)<br>
 * http://svn.apache.org/repos/asf/felix/releases/org.apache.felix.main-1.2.1/doc/launching-and-embedding-apache-felix.html<br>
 * http://stackoverflow.com/questions/1887809/how-to-start-and-use-apache-felix-from-code<br>
 *
 * @author Thomas Freese
 */
public class Main
{
    /**
     * @author Thomas Freese
     */
    static class HostActivator implements BundleActivator
    {
        /**
         *
         */
        private BundleContext context = null;

        /**
         * Erzeugt eine neue Instanz von {@link HostActivator}
         */
        public HostActivator()
        {
            super();
        }

        /**
         * @return {@link BundleContext}
         */
        public BundleContext getContext()
        {
            return this.context;
        }

        /**
         * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
         */
        @Override
        public void start(final BundleContext bc)
        {
            this.context = bc;
        }

        /**
         * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
         */
        @Override
        public void stop(final BundleContext bc)
        {
            this.context = null;
        }
    }

    /**
     *
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * @param frameworkFactory {@link FrameworkFactory}
     *
     * @return {@link Framework}
     *
     * @throws BundleException Falls was schief geht.
     */
    private static Framework getConfiguredFramework(final FrameworkFactory frameworkFactory) throws BundleException
    {
        Map<String, String> configMap = new HashMap<>();

        configMap.put(Constants.FRAMEWORK_STORAGE, "osgi-data");
        configMap.put(Constants.FRAMEWORK_STORAGE_CLEAN, Constants.FRAMEWORK_STORAGE_CLEAN_ONFIRSTINIT);

        configMap.put("felix.auto.deploy.dir", "bundles");
        configMap.put("felix.auto.deploy.action", "install, start");
        // configMap.put("felix.auto.start.1", "file:bundles/maven-osgi-0.0.1-SNAPSHOT.jar");
        // configMap.put("felix.auto.start.2", "file:../service-api/0.0.1-SNAPSHOT/service-api-0.0.1-SNAPSHOT.jar");

        // configMap.put("felix.startlevel.bundle", "4");
        configMap.put("felix.log.level", "4");

        // Provide the Java 1.8 execution environment
        // config.put(Constants.FRAMEWORK_EXECUTIONENVIRONMENT, "J2SE-1.8");
        Framework framework = frameworkFactory.newFramework(configMap);
        framework.init();
        AutoProcessor.process(configMap, framework.getBundleContext());

        // System.out.println(org.apache.felix.main.Main.loadConfigProperties());
        return framework;
    }

    /**
     * @return {@link FrameworkFactory}
     */
    private static FrameworkFactory getFrameworkFactory()
    {
        ServiceLoader<FrameworkFactory> loader = ServiceLoader.load(FrameworkFactory.class);
        FrameworkFactory frameworkFactory = null;

        for (FrameworkFactory ff : loader)
        {
            frameworkFactory = ff;
            break;
        }

        if (frameworkFactory == null)
        {
            throw new NullPointerException("frameworkFactory");
        }

        return frameworkFactory;
    }

    /**
     * @param args String[]
     *
     * @throws IOException Falls was schief geht.
     */
    public static void main(final String[] args) throws IOException
    {
        // Files.delete(Paths.get());
        FileUtils.deleteDirectory(new File("felix-cache"));
        FileUtils.deleteDirectory(new File("osgi-data"));

        // String[] equinoxArgs = {"-console","1234","-noExit"};
        // BundleContext context = EclipseStarter.startup(equinoxArgs,null);
        // Bundle bundle =
        // context.installBundle("http://www.eclipsezone.com/files/jsig/bundles/HelloWorld.jar");
        // bundle.start();
        try
        {
            // Map<String, Object> map = new HashMap<>();
            // HostActivator activator = new HostActivator();
            //
            // List<Object> list = new ArrayList<>();
            // list.add(activator);
            // map.put(FelixConstants.SYSTEMBUNDLE_ACTIVATORS_PROP, list);

            // org.apache.felix.main.Main.loadSystemProperties();
            //
            FrameworkFactory frameworkFactory = getFrameworkFactory();
            Framework framework = getConfiguredFramework(frameworkFactory);

            // Framework framework = new Felix(map);
            LOGGER.info("starting OSGI...");
            framework.start();

            BundleContext context = framework.getBundleContext();
            List<Bundle> installedBundles = new ArrayList<>();

            Path path = Paths.get("/home/tommy/.m2/repository");

            // Für org.osgi.service.log.LogService
            installedBundles.add(context
                    .installBundle("file:" + path.resolve("org/osgi/org.osgi.compendium/5.0.0/org.osgi.compendium-5.0.0.jar")));

            // Logging
            installedBundles.add(context.installBundle("file:" + path.resolve("org/slf4j/slf4j-api/1.7.21/slf4j-api-1.7.21.jar")));
            installedBundles.add(context.installBundle("file:" + path.resolve("org/slf4j/slf4j-simple/1.7.21/slf4j-simple-1.7.21.jar")));
            installedBundles.add(context.installBundle("file:../logservice/target/logservice-0.0.1-SNAPSHOT.jar"));

            // Bundles
            installedBundles.add(context.installBundle("file:../simple/target/simple-0.0.1-SNAPSHOT.jar"));
            installedBundles.add(context.installBundle("file:../service-api/target/service-api-0.0.1-SNAPSHOT.jar"));
            installedBundles.add(context.installBundle("file:../service-impl/target/service-impl-0.0.1-SNAPSHOT.jar"));
            installedBundles.add(context.installBundle("file:../service-consumer/target/service-consumer-0.0.1-SNAPSHOT.jar"));

            // Bundles starten.
            for (Bundle bundle : installedBundles)
            {
                if (bundle.getHeaders().get(Constants.FRAGMENT_HOST) == null)
                {
                    LOGGER.info("starting bundle: {}, {}", bundle.getLocation(), bundle.getSymbolicName());

                    bundle.start();
                }
                else
                {
                    LOGGER.info("fragmented: {}, {}", bundle.getLocation(), bundle.getSymbolicName());
                }
            }

            Set<String> inUse = new HashSet<>();
            ServiceReference<?>[] serviceReferences = framework.getServicesInUse();

            if (serviceReferences != null)
            {
                for (ServiceReference<?> sr : serviceReferences)
                {
                    inUse.add(sr.toString());
                }
            }

            for (ServiceReference<?> sr : framework.getRegisteredServices())
            {
                String flag = (inUse.contains(sr.toString()) ? " [in use] " : " ");
                LOGGER.info("+ registered service:{}{}", flag, sr.toString());
            }

            // ServiceTracker<IMyService, IMyService> serviceTracker = new ServiceTracker<>(context, IMyService.class, null);
            // serviceTracker.open();
            // IMyService service = serviceTracker.getService();
            // LOGGER.info(service.getInfo());
            framework.stop();
            framework.waitForStop(0);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            System.exit(0);
        }
    }
}
