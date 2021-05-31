// Created: 30.09.2012
package de.freese.cdi.weld.tellermachine;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author Thomas Freese
 */
@Named("atm")
public class AutomatedTellerMachineImpl implements AutomatedTellerMachine
{
    /**
     *
     */
    private ATMTransport transport = null;

    /**
     * @see de.freese.cdi.weld.tellermachine.AutomatedTellerMachine#deposit(float)
     */
    @Override
    public void deposit(final float bd)
    {
        System.out.println("AutomatedTellerMachineImpl.deposit()");

        this.transport.communicateWithBank(toByteArray(bd));
    }

    /**
     * @param transport {@link ATMTransport}
     */
    @Inject
    public void setTransport(final ATMTransport transport)
    {
        this.transport = transport;
    }

    /**
     * @param f float
     * @return byte[]
     */
    private byte[] toByteArray(final float f)
    {
        byte[] buf = new byte[4];
        int x = Float.floatToRawIntBits(f);

        buf[0] = (byte) ((x >>> 24) & 0xFF);
        buf[1] = (byte) ((x >>> 16) & 0xFF);
        buf[2] = (byte) ((x >>> 8) & 0xFF);
        buf[3] = (byte) (x & 0xFF);

        return buf;
    }

    /**
     * @see de.freese.cdi.weld.tellermachine.AutomatedTellerMachine#withdraw(float)
     */
    @Override
    public void withdraw(final float bd)
    {
        System.out.println("AutomatedTellerMachineImpl.withdraw()");

        this.transport.communicateWithBank(toByteArray(bd));
    }
}
