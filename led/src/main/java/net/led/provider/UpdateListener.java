package net.led.provider;

/**
 * @author Thomas Freese
 */
@FunctionalInterface
public interface UpdateListener
{
    /**
     * @param newValue Object
     */
    public void update(Object newValue);
}
