package gui;

import gui.impl.StartPanel;

import java.awt.BorderLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JPanel;
/**
 * Contains two main panels inside the frame, the top panel and the test panel
 * @SuppressWarnings("serial") This class will never need to be serialized
 */
@SuppressWarnings("serial")
public final class Gui extends JPanel implements PropertyChangeListener {

    //active panel in the center
	private JPanel currentPanel;
    //previous panel in the center
    private JPanel lastPanel;

    private final IPanelManager manager = new IPanelManager();

    //sets original panel - the startpanel
	public Gui() {
		setLayout(new BorderLayout());
				
		add(lastPanel = currentPanel = new StartPanel(this), BorderLayout.CENTER);

        manager.load(this);
    }

    /**
     *    send a property change change between panels on the main GUI
     *    {@link PropertyChangeEvent}
     */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
        if(event.getPropertyName().equalsIgnoreCase("panelchange")) {
            remove(lastPanel = currentPanel);
            add(currentPanel = (JPanel)event.getNewValue());
        } else if(event.getPropertyName().equalsIgnoreCase("back")) {
            final JPanel old = currentPanel;
            remove(currentPanel);
            currentPanel = lastPanel;
            add(lastPanel);
            lastPanel = old;
        }
        repaint();
        revalidate();
	}

    public void changePanelId(final int id) {
        manager.show(id);
    }
	
	

}
