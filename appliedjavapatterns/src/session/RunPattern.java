package session;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Thomas Freese
 */
public class RunPattern
{
    /**
     * @param arguments String[]
     */
    @SuppressWarnings("unused")
    public static void main(final String[] arguments)
    {
        System.out.println("Example for the Session pattern");
        System.out.println("This demonstration will show how a Session can be used");
        System.out.println(" to organize a series of actions between a client and");
        System.out.println(" server.");
        System.out.println("In this case, clients will use sessions to coordinate");
        System.out.println(" edits of Contact addresses.");
        System.out.println();

        System.out.println("Running the RMI compiler (rmic)");
        System.out.println();

        try
        {
            Process process = Runtime.getRuntime().exec("rmic -d target/classes -classpath target/classes session.SessionServerImpl");

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream())))
            {
                reader.lines().forEach(System.out::println);
            }

            System.out.println();
            System.out.println("rmic exit value: " + process.waitFor());
        }
        catch (IOException exc)
        {
            System.err.println("Unable to run rmic utility. Exiting application.");
            System.exit(1);
        }
        catch (InterruptedException exc)
        {
            System.err.println("Threading problems encountered while using the rmic utility.");
        }

        System.out.println("Starting the rmiregistry");
        System.out.println();

        try
        {
            // Process rmiProcess =
            Runtime.getRuntime().exec("rmiregistry");
            Thread.sleep(15000);
        }
        catch (IOException exc)
        {
            System.err.println("Unable to start the rmiregistry. Exiting application.");
            System.exit(1);
        }
        catch (InterruptedException exc)
        {
            System.err.println("Threading problems encountered when starting the rmiregistry.");
        }

        System.out.println();
        System.out.println("Creating the SessionServer and two SessionClient objects");
        System.out.println();

        new SessionServerImpl();
        SessionClient clientOne = new SessionClient();
        SessionClient clientTwo = new SessionClient();

        System.out.println("Creating sample Contacts and Addresses");
        System.out.println();
        Contact firstContact = new ContactImpl("First", "Contact", "primo", "OOI", null);
        Contact secondContact = new ContactImpl("Second", "Contact", "secondo", "OOI", null);
        Address workAddress = new AddressImpl("Work address", "5440 Division", "Fargo", "ND", "54321");
        Address homeAddress = new AddressImpl("Home address", "40 Planar Way", "Paris", "TX", "84301");

        System.out.println("Adding a contact. Both clients will attempt to edit");
        System.out.println(" the same contact at first, which will result in a");
        System.out.println(" SessionException.");

        try
        {
            clientOne.addContact(firstContact);
            clientTwo.addContact(firstContact);
        }
        catch (SessionException exc)
        {
            System.err.println("Exception encountered:");
            System.err.println(exc);
        }

        try
        {
            System.out.println("Adding a different contact to the second client");
            clientTwo.addContact(secondContact);
            System.out.println("Adding addresses to the first and second clients");
            clientTwo.addAddress(workAddress);
            clientOne.addAddress(homeAddress);
            clientTwo.addAddress(workAddress);
            clientTwo.addAddress(homeAddress);
            System.out.println("Removing address from a client");
            clientTwo.removeAddress(homeAddress);
            System.out.println("Finalizing the edits to the contacts");
            clientOne.commitChanges();
            clientTwo.commitChanges();
            System.out.println("Changes finalized");
            clientTwo.addContact(firstContact);
        }
        catch (SessionException exc)
        {
            System.err.println("Exception encountered:");
            System.err.println(exc);
        }

        System.out.println("The following lines will show the state");
        System.out.println(" of the server-side delegate, which in this");
        System.out.println(" example represents a persistent data store.");
        System.out.println();
        System.out.println("Contact list:");
        System.out.println(SessionServerDelegate.getContacts());
        System.out.println("Address list:");
        System.out.println(SessionServerDelegate.getAddresses());
        System.out.println("Edit contacts:");
        System.out.println(SessionServerDelegate.getEditContacts());
    }
}
