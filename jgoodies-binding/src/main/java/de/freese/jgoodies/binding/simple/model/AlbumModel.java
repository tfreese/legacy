/**
 * Created:17.10.2013
 */

package de.freese.jgoodies.binding.simple.model;

import java.util.List;
import javax.swing.ListModel;
import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.common.collect.ArrayListModel;
import com.jgoodies.common.collect.ObservableList;

/**
 * {@link PresentationModel} von {@link AlbumBean}.<br>
 * spinner.setModel(SpinnerAdapterFactory.createNumberAdapter(albumModel.getModel(AlbumBean.PROPERTY_DISKNUMBER), 1, 1, 50, 1));<br>
 * JTextArea textArea = JGoodiesComponentFactory.createTextArea(albumModel.getModel(AlbumBean.PROPERTY_COMMENT));
 *
 * @author Thomas Freese
 */
public class AlbumModel extends PresentationModel<AlbumBean>
{
    /**
    *
    */
    private static final long serialVersionUID = 1231784491376321488L;

    /**
    *
    */
    private final ObservableList<String> listModelTracks;

    /**
    *
    */
    private final SelectionInList<String> selectionInListGenres;

    /**
     * Erstellt ein neues {@link AlbumModel} Object.
     */
    @SuppressWarnings("unchecked")
    public AlbumModel()
    {
        super();

        this.listModelTracks = new ArrayListModel<>();

        // Damit der geänderte Artist richtig gerendert wird, ist nicht schön die Lösung aber dafür selten ;-)
        // @formatter:off
        getModel(AlbumBean.PROPERTY_ARTIST).addValueChangeListener(event ->
            fillListModel()
        );
        // @formatter:on

        // Genres
        SelectionInList<String> selectionInList = null;

        try
        {
            ListModel<String> genreList = new ArrayListModel<>(List.of("A", "B", "C"));
            selectionInList = new SelectionInList<>(genreList, getModel(AlbumBean.PROPERTY_GENRE));
        }
        catch (Exception ex)
        {
            selectionInList = new SelectionInList<>();

            System.err.println(ex);
        }

        this.selectionInListGenres = selectionInList;
    }

    /**
    *
    */
    private void fillListModel()
    {
        this.listModelTracks.clear();

        for (String track : getBean())
        {
            this.listModelTracks.add(track);
        }
    }

    /**
     * @return {@link ObservableList}<String>
     */
    public ObservableList<String> getListModelTracks()
    {
        return this.listModelTracks;
    }

    /**
     * @return SelectionInList<String>
     */
    public SelectionInList<String> getSelectionInListGenres()
    {
        return this.selectionInListGenres;
    }

    /**
     * @see com.jgoodies.binding.PresentationModel#setBean(java.lang.Object)
     */
    @Override
    public void setBean(final AlbumBean newBean)
    {
        super.setBean(newBean);

        fillListModel();
    }
}
