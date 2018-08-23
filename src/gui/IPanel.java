package gui;

import configurations.Resource;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 1/26/15
 * Time: 6:25 PM
 *
 * IPanels represent a panel that will be displayed on the main screen, holding reference to the previous
 * panel and the current panel. This will provide an option to go back
 * Each panel will have a unique identifier id
 */
public class IPanel extends JPanel {


    //the main panel
    protected final PropertyChangeListener listener;
    //unique identifier id
    protected final int id;


    //IPanel shell
    public IPanel(final PropertyChangeListener listener, final int id) {
        setLayout(new BorderLayout());
        this.listener = listener;
        this.id = id;

        final JPanel south = new JPanel();
        south.setLayout(new BorderLayout());

        final JButton back = new JButton(Resource.loadImageIcon(36, 16, "backbutton.png"));
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                hide();
            }
        });
        south.add(back, BorderLayout.WEST);

        add(south, BorderLayout.SOUTH);
    }

    /**
     * change displayed panel to the IPanel
     * @deprecated is no longer valid due as @override
     */

    @Override
    public void show() {
        listener.propertyChange(new PropertyChangeEvent(this, "panelchange", null, this));
    }

    /**
     * switch to old panel
     * @deprecated is no longer valid due as @override
     */

    @Override
    public void hide() {
        listener.propertyChange(new PropertyChangeEvent(this, "back", null, null));
    }

}
