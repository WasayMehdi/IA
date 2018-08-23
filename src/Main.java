import configurations.Resource;
import gui.Gui;

import java.applet.Applet;
import java.awt.Dimension;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.swing.*;

import configurations.Email;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.pushingpixels.substance.api.skin.BusinessBlueSteelSkin;

/**
 * Inside default package, no other class should need to access this
 */

public final class Main {

    final Map<String, Object> map = new HashMap<String, Object>();

  /*  private <T> Optional<T> get(final Class<T> clazz, final String key) {
        final Object result = map.get(key);
        try {
            final T t = (T)clazz.getMethod("valueOf", String.class).invoke(null, result.toString());
            return Optional.of(t);
        } catch (final Exception return_empty) {
            return Optional.empty();
        }
    }*/
	
	/**
	 * Creates test frame and sets contentpane to {@link gui.Gui}
	 */
	
	public static void main(String... args) throws Exception {

        //run program on EDT
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                //JFrame.setDefaultLookAndFeelDecorated(true);
                SubstanceLookAndFeel.setSkin(new BusinessBlueSteelSkin());

                final JFrame frame = new JFrame("Test");
                frame.setIconImage(Resource.loadImage("test.png"));

                frame.setUndecorated(true);
                frame.setContentPane(new Gui());

                frame.pack();
                frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                frame.setVisible(true);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            }
        });
		
	}

}
