// Created: 30.09.2012
package de.freese.cdi.weld.tellermachine;

/**
 * @author Thomas Freese
 */
// @Alternative
public class AtmJsonRestTransport implements ATMTransport
{
    /**
     * @see de.freese.cdi.weld.tellermachine.ATMTransport#communicateWithBank(byte[])
     */
    @Override
    public void communicateWithBank(final byte[] packet)
    {
        System.out.println("communicating with bank via JSON REST transport");
    }
}
