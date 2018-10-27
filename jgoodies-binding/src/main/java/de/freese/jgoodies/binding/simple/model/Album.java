/**
 * Created: 20.10.2013
 */

package de.freese.jgoodies.binding.simple.model;

/**
 * Zusammenfassendes Objekt für eine CD.
 *
 * @author Thomas Freese
 */
public interface Album extends Iterable<String>
{
    /**
     * Bei Compilations null.
     * 
     * @return String
     */
    public String getArtist();

    /**
     * @return String
     */
    public String getComment();

    /**
     * @return String
     */
    public String getDiskID();

    /**
     * @return int
     */
    public int getDiskNumber();

    /**
     * @return String
     */
    public String getGenre();

    /**
     * @return String
     */
    public String getTitle();

    /**
     * @return int
     */
    public int getTotalDisks();

    /**
     * Liefert den String am Index.
     * 
     * @param index int
     * @return String
     */
    public String getTrack(int index);

    /**
     * Liefert die Anzahl der Tracks.
     * 
     * @return int
     */
    public int getTrackCount();

    /**
     * @return int
     */
    public int getYear();

    /**
     * Liefert true, wenn das Album eine Compilation ist.
     * 
     * @return boolean
     */
    public boolean isCompilation();

    /**
     * Bei Compilations null.
     * 
     * @param artist String
     */
    public void setArtist(final String artist);

    /**
     * @param comment String
     */
    public void setComment(final String comment);

    /**
     * @param diskNumber int
     */
    public void setDiskNumber(final int diskNumber);

    /**
     * @param genre String
     */
    public void setGenre(final String genre);

    /**
     * @param title String
     */
    public void setTitle(final String title);

    /**
     * @param totalDisks int
     */
    public void setTotalDisks(final int totalDisks);

    /**
     * @param year int
     */
    public void setYear(final int year);
}