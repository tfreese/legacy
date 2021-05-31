// Created: 28.06.2012
package de.freese.docbook;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;

import javax.help.HelpSet;
import javax.help.JHelp;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/**
 * @author Thomas Freese
 */
public class TestJavaHelp
{
    /**
     * @param args String[]
     * @throws Exception Falls was schief geht.
     */
    public static void main(final String[] args) throws Exception
    {
        TestJavaHelp test = new TestJavaHelp();
        test.testJavaDoc2();
    }

    /**
     * @throws Exception Falls was schief geht.
     */
    void testJavaDoc() throws Exception
    {
        URL helpURL = new File("mydocu.jar").toURI().toURL();
        System.out.println(helpURL);

        try (URLClassLoader classLoader = new URLClassLoader(new URL[]
        {
                helpURL
        }))
        {
            URL helpSetURL = new URL("jar:" + helpURL.toExternalForm() + "!/jhelpset.hs");

            JHelp helpViewer = new JHelp(new HelpSet(classLoader, helpSetURL));

            JFrame frame = new JFrame();
            frame.setTitle("My Help");
            frame.getContentPane().add(helpViewer);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setSize(1024, 800);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
    }

    /**
     * @throws Exception Falls was schief geht.
     */
    void testJavaDoc2() throws Exception
    {
        File file = new File("target/docbkx/javahelp/jhelpset.hs");

        URL helpSetURL = file.toURI().toURL();

        JHelp helpViewer = new JHelp(new HelpSet(ClassLoader.getSystemClassLoader(), helpSetURL));

        JFrame frame = new JFrame();
        frame.setTitle("My Help");
        frame.getContentPane().add(helpViewer);
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setSize(1024, 800);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
