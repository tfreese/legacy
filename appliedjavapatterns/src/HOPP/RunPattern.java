package HOPP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author Thomas Freese
 */
public class RunPattern
{
    /**
     *
     */
    private static Calendar dateCreator = Calendar.getInstance();

    /**
     * @param year int
     * @param month int
     * @param day int
     * @param hour int
     * @param minute int
     * @return {@link Date}
     */
    public static Date createDate(final int year, final int month, final int day, final int hour, final int minute)
    {
        dateCreator.set(year, month, day, hour, minute);

        return dateCreator.getTime();
    }

    /**
     * @param arguments String[]
     * @throws RemoteException Falls was schief geht
     */
    @SuppressWarnings("unused")
    public static void main(final String[] arguments) throws RemoteException
    {
        System.out.println("Example for the HOPP pattern");
        System.out.println();
        System.out.println("This example will use RMI to demonstrate the HOPP pattern.");
        System.out.println(" In the sample, there will be two objects created, CalendarImpl");
        System.out.println(" and CalendarHOPP. The CalendarImpl object provides the true");
        System.out.println(" server-side implementation, while the CalendarHOPP would be");
        System.out.println(" a client or middle-tier representative. The CalendarHOPP will");
        System.out.println(" provide some functionality, in this case supplying the hostname");
        System.out.println(" in response to the getHost method.");
        System.out.println();
        System.out.println("Note: This example runs the rmiregistry, CalendarHOPP and CalendarImpl");
        System.out.println(" on the same machine.");
        System.out.println();

        try
        {
            Process process = Runtime.getRuntime().exec("rmic -d target/classes -classpath target/classes HOPP.CalendarImpl HOPP.CalendarHOPP");

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

        System.out.println();
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

        System.out.println("Creating the CalendarImpl object, which provides the server-side implementation.");
        System.out.println("(Note: If the CalendarImpl object does not have a file containing Appointments,");
        System.out.println("  this call will produce an error message. This will not affect the example.)");

        new CalendarImpl();

        System.out.println();
        System.out.println("Creating the CalendarHOPP object, which provides client-side functionality.");
        CalendarHOPP localObject = new CalendarHOPP();

        System.out.println();
        System.out.println("Getting the hostname. The CalendarHOPP will handle this method locally.");
        System.out.println("Hostname is " + localObject.getHost());
        System.out.println();

        System.out.println("Creating and adding appointments. The CalendarHOPP will forward");
        System.out.println(" these calls to the CalendarImpl object.");
        Contact attendee = new ContactImpl("Jenny", "Yip", "Chief Java Expert", "MuchoJava LTD");
        List<Contact> contacts = new ArrayList<>();

        contacts.add(attendee);
        Location place = new LocationImpl("Albuquerque, NM");

        localObject.addAppointment(
                new Appointment("Opening speeches at annual Java Guru's dinner", contacts, place, createDate(2001, 4, 1, 16, 0), createDate(2001, 4, 1, 18, 0)),
                createDate(2001, 4, 1, 0, 0));
        localObject.addAppointment(
                new Appointment("Java Guru post-dinner Cafe time", contacts, place, createDate(2001, 4, 1, 19, 30), createDate(2001, 4, 1, 21, 45)),
                createDate(2001, 4, 1, 0, 0));
        System.out.println("Appointments added.");
        System.out.println();

        System.out.println("Getting the Appointments for a date. The CalendarHOPP will forward");
        System.out.println(" this call to the CalendarImpl object.");
        System.out.println(localObject.getAppointments(createDate(2001, 4, 1, 0, 0)));
    }
}