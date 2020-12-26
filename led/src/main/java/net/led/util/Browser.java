package net.led.util;

import java.io.IOException;

/**
 * @author Thomas Freese
 */
public class Browser
{
    /**
     *
     */
    private static String o;

    /**
     *
     */
    private static int r;

    /**
     *
     */
    private static String shell;

    static
    {
        String osName = System.getProperty("os.name");

        if (osName.startsWith("Windows"))
        {
            if (osName.indexOf("9") != -1)
            {
                r = 6;
            }
            else
            {
                r = 5;
            }
        }
        else
        {
            r = -1;
        }
    }

    /**
     * @return String
     */
    private static String getShell()
    {
        if (shell != null)
        {
            return shell;
        }

        switch (r)
        {
            case 5: // '\005'
                shell = "cmd.exe";

                break;

            case 6: // '\006'
                shell = "command.com";

                break;

            case -1:
            default:
                shell = "netscape";

                break;
        }

        return shell;
    }

    /**
     * @param s1 String
     * @throws IOException Falls was schief geht.
     */
    public static void openUrl(final String s1) throws IOException
    {
        Object obj = getShell();

        if (obj == null)
        {
            throw new IOException("Unable to locate browser: " + o);
        }

        switch (r)
        {
            case 5: // '\005'
            case 6: // '\006'

                Process process = Runtime.getRuntime().exec(new String[]
                {
                        (String) obj, "/c", "start", "\"\"", '"' + s1 + '"'
                });

                try
                {
                    process.waitFor();
                    process.exitValue();
                }
                catch (InterruptedException interruptedexception)
                {
                    throw new IOException("InterruptedException while launching browser: " + interruptedexception.getMessage());
                }

                break;

            case -1:

                Process process1 = Runtime.getRuntime().exec(new String[]
                {
                        (String) obj, "-remote", "'openURL(" + s1 + ")'"
                });

                try
                {
                    int i1 = process1.waitFor();

                    if (i1 != 0)
                    {
                        Runtime.getRuntime().exec(new String[]
                        {
                                (String) obj, s1
                        });
                    }
                }
                catch (InterruptedException interruptedexception1)
                {
                    throw new IOException("InterruptedException while launching browser: " + interruptedexception1.getMessage());
                }

                break;

            default:
                Runtime.getRuntime().exec(new String[]
                {
                        (String) obj, s1
                });

                break;
        }
    }

    /**
     * Erstellt ein neues {@link Browser} Object.
     */
    private Browser()
    {
        super();
    }
}
