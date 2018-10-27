/*** Created:17.10.2013 */

package de.freese.jgoodies.binding.simple.model;

import java.util.Iterator;
import com.jgoodies.binding.beans.Model;
import com.jgoodies.common.bean.Bean;

/**
 * {@link Bean} für das {@link Album}.
 *
 * @author Thomas Freese
 */
public class AlbumBean extends Model implements Album// Bean
{
    /**
    *
    */
    public static final String PROPERTY_ARTIST = "artist";

    /**
    *
    */
    public static final String PROPERTY_COMMENT = "comment";

    /**
    *
    */
    public static final String PROPERTY_DISKNUMBER = "diskNumber";

    /**
    *
    */
    public static final String PROPERTY_GENRE = "genre";

    /**
    *
    */
    public static final String PROPERTY_TITLE = "title";

    /**
    *
    */
    public static final String PROPERTY_TOTALDISKS = "totalDisks";

    /**
    *
    */
    public static final String PROPERTY_YEAR = "year";
    /**
    *
    */
    private static final long serialVersionUID = 9196811641238029075L;

    /**
    *
    */
    private final Album album;

    /**
     * Erstellt ein neues {@link AlbumBean} Object.
     *
     * @param album {@link Album}
     */
    public AlbumBean(final Album album)
    {
        super();

        this.album = album;
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getArtist()
     */
    @Override
    public String getArtist()
    {
        return this.album.getArtist();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getComment()
     */
    @Override
    public String getComment()
    {
        return this.album.getComment();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getDiskID()
     */
    @Override
    public String getDiskID()
    {
        return this.album.getDiskID();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getDiskNumber()
     */
    @Override
    public int getDiskNumber()
    {
        return this.album.getDiskNumber();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getGenre()
     */
    @Override
    public String getGenre()
    {
        return this.album.getGenre();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getTitle()
     */
    @Override
    public String getTitle()
    {
        return this.album.getTitle();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getTotalDisks()
     */
    @Override
    public int getTotalDisks()
    {
        return this.album.getTotalDisks();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getTrack(int)
     */
    @Override
    public String getTrack(final int index)
    {
        return this.album.getTrack(index);
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getTrackCount()
     */
    @Override
    public int getTrackCount()
    {
        return this.album.getTrackCount();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#getYear()
     */
    @Override
    public int getYear()
    {
        return this.album.getYear();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#isCompilation()
     */
    @Override
    public boolean isCompilation()
    {
        return this.album.isCompilation();
    }

    /**
     * @see java.lang.Iterable#iterator()
     */
    @Override
    public Iterator<String> iterator()
    {
        return this.album.iterator();
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#setArtist(java.lang.String)
     */
    @Override
    public void setArtist(final String artist)
    {
        Object oldValue = getArtist();
        this.album.setArtist(artist.trim());

        // Alle Tracks aktualisieren.
        // for (String track : this.album)
        // {
        // track.setArtist(artist);
        // }

        firePropertyChange(PROPERTY_ARTIST, oldValue, artist);
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#setComment(java.lang.String)
     */
    @Override
    public void setComment(final String comment)
    {
        Object oldValue = getComment();
        this.album.setComment(comment.trim());

        firePropertyChange(PROPERTY_COMMENT, oldValue, comment);
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#setDiskNumber(int)
     */
    @Override
    public void setDiskNumber(final int diskNumber)
    {
        Object oldValue = getDiskNumber();
        this.album.setDiskNumber(diskNumber);

        firePropertyChange(PROPERTY_DISKNUMBER, oldValue, diskNumber);
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#setGenre(java.lang.String)
     */
    @Override
    public void setGenre(final String genre)
    {
        Object oldValue = getGenre();
        this.album.setGenre(genre.trim());

        firePropertyChange(PROPERTY_GENRE, oldValue, genre);
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#setTitle(java.lang.String)
     */
    @Override
    public void setTitle(final String title)
    {
        Object oldValue = getTitle();
        this.album.setTitle(title.trim());

        firePropertyChange(PROPERTY_TITLE, oldValue, title);
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#setTotalDisks(int)
     */
    @Override
    public void setTotalDisks(final int totalDisks)
    {
        Object oldValue = getTotalDisks();
        this.album.setTotalDisks(totalDisks);

        firePropertyChange(PROPERTY_TOTALDISKS, oldValue, totalDisks);
    }

    /**
     * @see de.freese.jgoodies.binding.simple.model.Album#setYear(int)
     */
    @Override
    public void setYear(final int year)
    {
        Object oldValue = getYear();
        this.album.setYear(year);

        firePropertyChange(PROPERTY_YEAR, oldValue, year);
    }
}
