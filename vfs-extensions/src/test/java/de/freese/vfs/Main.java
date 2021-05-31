/**
 *
 */
package de.freese.vfs;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import org.apache.commons.vfs2.FileContent;
import org.apache.commons.vfs2.FileObject;
import org.apache.commons.vfs2.util.FileObjectUtils;

/**
 * @author Thomas Freese
 */
public class Main
{
    /**
     * @param args String[]
     * @throws Exception Falls was schief geht.
     */
    @SuppressWarnings("resource")
    public static void main(final String[] args) throws Exception
    {
        // FileSystemOptions opts = new FileSystemOptions();

        // WebdavHTTPSFileSystemConfigBuilder.getInstance().setUserAuthenticator(opts, new StaticUserAuthenticator(null, "...", "..."));

        MyFileSystemManager fsManager = new MyFileSystemManager();
        fsManager.init();

        try (FileObject tempDirRoot = fsManager.getBaseFile())
        {

            // tempDirRoot = fsManager.resolveFile("webdavHTTPS://https://sd2dav.1und1.de/maven", opts);
            // // tempDirRoot = fsManager.getBaseFile();
            //
            // System.out.println("tempDirRoot:" + tempDirRoot.toString());
            //
            // if (tempDirRoot.exists())
            // {
            // for (FileObject fileObject : tempDirRoot.getChildren())
            // {
            // System.out.println("\tChild: " + fileObject.getName());
            // }
            //
            // // TMP Verzeichniss löschen
            // // tempDirRoot.delete(Selectors.EXCLUDE_SELF);
            // }
            // else
            // {
            // tempDirRoot.createFolder();
            // }

            // Tmp-Datei schreiben.
            try (FileObject testObject = fsManager.resolveFile(tempDirRoot, "tmp:/bla/test.txt"))
            {
                // FileObject testObject = fsManager.resolveFile(tempDirRoot, "bla/test.txt");
                testObject.createFile();

                try (FileContent fc = testObject.getContent();
                     PrintWriter pw = new PrintWriter(fc.getOutputStream()))
                {
                    pw.write("blabla");

                    // Tmp-Datei lesen
                    OutputStream outputStream = new ByteArrayOutputStream();
                    FileObjectUtils.writeContent(testObject, outputStream);

                    StringBuilder sb = new StringBuilder();
                    sb.append(outputStream);

                    System.out.println(sb);
                    System.out.println(testObject.getName());
                }
            }
        }

        // Close löscht alle Dateien.
        // fsManager.close();
    }
}
