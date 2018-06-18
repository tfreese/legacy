/**
 * Created: 07.09.2016
 */

package de.freese.osgi.logservice;

import org.osgi.framework.ServiceReference;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Thomas Freese
 */
public class Slf4JLogService implements LogService
{
    /**
    *
    */
    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4JLogService.class);

    /**
     * Erstellt ein neues {@link Slf4JLogService} Object.
     */
    public Slf4JLogService()
    {
        super();
    }

    /**
     * @param logger {@link Logger}
     * @param level int
     * @param message String
     */
    private void doLog(final Logger logger, final int level, final String message)
    {
        switch (level)
        {
            case LogService.LOG_DEBUG:
                if (logger.isDebugEnabled())
                {
                    logger.debug(message);
                }
                break;
            case LogService.LOG_ERROR:
                if (logger.isErrorEnabled())
                {
                    logger.error(message);
                }
                break;
            case LogService.LOG_INFO:
                if (logger.isInfoEnabled())
                {
                    logger.info(message);
                }
                break;
            case LogService.LOG_WARNING:
                if (logger.isWarnEnabled())
                {
                    logger.warn(message);
                }
                break;
            default:
                logger.trace(message);
                break;
        }
    }

    /**
     * @param logger {@link Logger}
     * @param level int
     * @param message String
     * @param exception {@link Throwable}
     */
    private void doLog(final Logger logger, final int level, final String message, final Throwable exception)
    {
        switch (level)
        {
            case LogService.LOG_DEBUG:
                if (logger.isDebugEnabled())
                {
                    logger.debug(message, exception);
                }
                break;
            case LogService.LOG_ERROR:
                if (logger.isErrorEnabled())
                {
                    logger.error(message, exception);
                }
                break;
            case LogService.LOG_INFO:
                if (logger.isInfoEnabled())
                {
                    logger.info(message, exception);
                }
                break;
            case LogService.LOG_WARNING:
                if (logger.isWarnEnabled())
                {
                    logger.warn(message, exception);
                }
                break;
            default:
                logger.trace(message, exception);
                break;
        }
    }

    /**
     * @see org.osgi.service.log.LogService#log(int, java.lang.String)
     */
    @Override
    public void log(final int level, final String message)
    {
        doLog(LOGGER, level, message);
    }

    /**
     * @see org.osgi.service.log.LogService#log(int, java.lang.String, java.lang.Throwable)
     */
    @Override
    public void log(final int level, final String message, final Throwable exception)
    {
        doLog(LOGGER, level, message, exception);
    }

    /**
     * @see org.osgi.service.log.LogService#log(org.osgi.framework.ServiceReference, int, java.lang.String)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void log(final ServiceReference sr, final int level, final String message)
    {
        String name = String.format("%s:%s[V%s]", getClass().getSimpleName(), sr.getBundle().getSymbolicName(), sr.getBundle().getVersion());

        doLog(LoggerFactory.getLogger(name), level, message);
    }

    /**
     * @see org.osgi.service.log.LogService#log(org.osgi.framework.ServiceReference, int, java.lang.String, java.lang.Throwable)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public void log(final ServiceReference sr, final int level, final String message, final Throwable exception)
    {
        String name = String.format("%s:%s[V%s]", getClass().getSimpleName(), sr.getBundle().getSymbolicName(), sr.getBundle().getVersion());

        doLog(LoggerFactory.getLogger(name), level, message, exception);
    }
}
