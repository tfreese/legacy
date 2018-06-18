/**
 * Created: 04.01.2016
 */

package rmi;

import java.util.Date;

/**
 * @author Thomas Freese
 */
public class Service implements IService
{
    /**
     *
     */
    private static final long serialVersionUID = -6176570853476581026L;

    /**
     * Erstellt ein neues {@link Service} Object.
     */
    public Service()
    {
        super();
    }

    /**
     * @see rmi.IService#getDate(java.lang.String)
     */
    @Override
    public String getDate(final String message)
    {
        return String.format("%1$s: %2$tY-%2$td-%2$td_%2$tH:%2$tM:%2$tS", message, new Date());
    }
}
