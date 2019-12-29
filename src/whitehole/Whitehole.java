// Copyright © 2019 - Whitehole for SMG1 team
//
// This file is part of "Whitehole for SMG1"
//
// "Whitehole for SMG1" is free software: you can redistribute it and/or modify it under
// the terms of the GNU General Public License as published by the Free
// Software Foundation, either version 3 of the License, or (at your option)
// any later version.
//
// "Whitehole for SMG1" is distributed in the hope that it will be useful, but WITHOUT ANY 
// WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS 
// FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License along 
// with "Whitehole for SMG1". If not, see http://www.gnu.org/licenses/.

package whitehole;

import whitehole.swing.MainFrame;
import java.nio.charset.Charset;
import java.util.prefs.Preferences;
import javax.media.opengl.GLProfile;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import whitehole.smg.GameArchive;
import java.awt.Image;
import java.awt.Toolkit;

public class Whitehole {
    public static final String NAME = "Whitehole for SMG1";
    public static final Image ICON = Toolkit.getDefaultToolkit().createImage(Whitehole.class.getResource("/res/icon.png"));
    public static GameArchive game;
    
    private Whitehole() {}
    
    public static void main(String[] args) {
        if (!Charset.isSupported("SJIS")) {
            if (!Preferences.userRoot().getBoolean("whitehole.warnedCharset", false)) {
                JOptionPane.showMessageDialog(
                        null,
                        "Shift-JIS encoding isn't supported.\nWhitehole will default to ASCII, which may cause certain strings to look corrupted.\n\nThis message appears only once.", 
                        Whitehole.NAME,
                        JOptionPane.WARNING_MESSAGE
                );
                Preferences.userRoot().putBoolean("whitehole.warnedCharset", true);
            }
        }

        Settings.init();
        HashDB.init();
        GalaxyDB.init();
        ObjectDB.init();
        GLProfile.initSingleton();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex) { System.out.println("Error: Could not set system look and feel."); }

        new MainFrame().setVisible(true);
    }
}