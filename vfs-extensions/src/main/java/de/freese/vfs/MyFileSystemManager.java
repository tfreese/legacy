/**
 *
 */
package de.freese.vfs;

import java.io.File;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.apache.commons.vfs2.FileSystemException;
import org.apache.commons.vfs2.FileSystemManager;
import org.apache.commons.vfs2.impl.DefaultFileReplicator;
import org.apache.commons.vfs2.impl.DefaultFileSystemManager;
import org.apache.commons.vfs2.impl.PrivilegedFileReplicator;
import org.apache.commons.vfs2.provider.https.HttpsFileProvider;
import org.apache.commons.vfs2.provider.local.DefaultLocalFileProvider;
import org.apache.commons.vfs2.provider.temp.TemporaryFileProvider;
import org.apache.commons.vfs2.provider.url.UrlFileProvider;
import org.apache.commons.vfs2.provider.webdav4.Webdav4FileProvider;

/**
 * {@link FileSystemManager}, der auch die Temporären Dateien im gleichen Verzeichnis<br>
 * wie alle anderen ablegt.<br>
 *
 * @see <a href="https://commons.apache.org/proper/commons-vfs/filesystems.html">commons-vfs filesystems</a>
 * @author Thomas Freese
 */
class MyFileSystemManager extends DefaultFileSystemManager
{
    /**
     * @return String
     */
    public static String getTempDir()
    {
        PrivilegedAction<String> doGetUserHome = () -> System.getProperty("user.dir");

        String userDir = AccessController.doPrivileged(doGetUserHome);

        if (userDir == null)
        {
            throw new IllegalStateException("user.dir is null");
        }

        StringBuilder sb = new StringBuilder();
        sb.append(userDir);
        sb.append(File.separator);
        sb.append("temp");
        // sb.append(File.separator);
        // sb.append(System.getProperty("user.name"));
        // sb.append(File.separator);

        return sb.toString();
    }

    /**
     * Closes all files created by this manager, and cleans up any temporary files. Also closes all providers and the replicator.
     *
     * @see org.apache.commons.vfs2.impl.DefaultFileSystemManager#close()
     */
    @Override
    public void close()
    {
        // Close löscht alle Dateien.
        super.close();
    }

    /**
     * @see org.apache.commons.vfs2.impl.DefaultFileSystemManager#init() {@link "http://commons.apache.org/vfs/api.html"}
     */
    @Override
    public void init() throws FileSystemException
    {
        // Nur Datei Provider definieren.
        addProvider("file", new DefaultLocalFileProvider());
        addProvider("tmp", new TemporaryFileProvider());
        addProvider("webdav", new Webdav4FileProvider());
        addProvider("https", new HttpsFileProvider());
        // addProvider("iso", new IsoFileProvider());

        setDefaultProvider(new UrlFileProvider());

        // BaseFile = Root Verzeichniss
        File baseFile = new File(getTempDir());

        DefaultFileReplicator replicator = new DefaultFileReplicator(baseFile);
        setReplicator(new PrivilegedFileReplicator(replicator));
        setTemporaryFileStore(replicator);
        // setFilesCache(new LRUFilesCache());

        super.init();

        setBaseFile(baseFile);
    }

    // /**
    // * @see org.apache.commons.vfs2.impl.StandardFileSystemManager#createDefaultFileReplicator()
    // */
    // @Override
    // protected DefaultFileReplicator createDefaultFileReplicator()
    // {
    // return new DefaultFileReplicator(new File(getTempDir()));
    // }
}