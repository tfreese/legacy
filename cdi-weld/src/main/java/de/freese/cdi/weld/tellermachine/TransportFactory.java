// Created: 30.09.2012
package de.freese.cdi.weld.tellermachine;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.Produces;

/**
 * @author Thomas Freese
 */
@Alternative
public class TransportFactory
{
    /**
     * @return {@link ATMTransport}
     */
    @Produces
    public ATMTransport createTransport()
    {
        System.out.println("ATMTransport created with producer");

        // return new StandardAtmTransport();
        // return new SoapAtmTransport();
        return new AtmJsonRestTransport();
    }
}
