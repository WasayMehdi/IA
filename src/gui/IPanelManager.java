package gui;

import gui.impl.AdminCP;
import gui.impl.DirectionPanel;

import java.beans.PropertyChangeListener;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Wasay
 * Date: 1/26/15
 * Time: 6:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class IPanelManager {
    //list containing all current IPanels
    private final List<IPanel> panels = new ArrayList<IPanel>();

    //Load All IPanel classes runtime using reflection
    public final void load(final Gui gui){
        for(final String s : new String[]{"AdminCP", "DirectionPanel"}) {
            try {
                final Class<IPanel> clazz = (Class<IPanel>)Class.forName(String.format("gui.impl.%s", s));
                final Constructor<IPanel> constructor = clazz.getDeclaredConstructor(PropertyChangeListener.class);
                panels.add(constructor.newInstance(gui));
            } catch (final Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
        }
    }

    //show IPanel for specified ID (replace current panel)

    public final void show(final int id) {
        get(id).show();
    }

    //get IPanel for certain ID
    private final IPanel get(final int id) {
        for(final IPanel panel : panels) {
            if(id == panel.id)
                return panel;
        }
        return null;
    }



}
