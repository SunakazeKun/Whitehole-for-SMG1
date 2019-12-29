/*
    © 2012 - 2019 - Whitehole Team

    Whitehole is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option)
    any later version.

    Whitehole is distributed in the hope that it will be useful, but WITHOUT ANY 
    WARRANTY; See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along 
    with Whitehole. If not, see http://www.gnu.org/licenses/.
*/

package whitehole.swing;

import whitehole.rendering.RendererCache;
import whitehole.rendering.TextureCache;
import whitehole.rendering.ShaderCache;
import whitehole.Whitehole;
import whitehole.io.ExternalFilesystem;
import whitehole.smg.GameArchive;
import java.util.prefs.Preferences;
import javax.swing.*;
import java.io.*;
import java.util.HashMap;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeSelectionModel;
import whitehole.GalaxyDB;

public class MainFrame extends javax.swing.JFrame {
    private final HashMap<String, JFrame> galaxyEditors;
    
    public MainFrame() {
        galaxyEditors = new HashMap();
        
        initComponents();
        populateGalaxies();
        
        treeGalaxies.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        btnOpenGame = new javax.swing.JButton();
        sep1 = new javax.swing.JToolBar.Separator();
        btnEditGalaxy = new javax.swing.JButton();
        sep2 = new javax.swing.JToolBar.Separator();
        btnEditScenario = new javax.swing.JButton();
        sep3 = new javax.swing.JToolBar.Separator();
        btnBcsvEditor = new javax.swing.JButton();
        sep4 = new javax.swing.JToolBar.Separator();
        btnSettings = new javax.swing.JButton();
        sep5 = new javax.swing.JToolBar.Separator();
        btnAbout = new javax.swing.JButton();
        lbStatusBar = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        treeGalaxies = new javax.swing.JTree();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle(Whitehole.NAME);
        setIconImage(Whitehole.ICON);

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        btnOpenGame.setText("Select game folder");
        btnOpenGame.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnOpenGame.setFocusable(false);
        btnOpenGame.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpenGame.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpenGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenGameActionPerformed(evt);
            }
        });
        jToolBar1.add(btnOpenGame);
        jToolBar1.add(sep1);

        btnEditGalaxy.setText("Open galaxy");
        btnEditGalaxy.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditGalaxy.setEnabled(false);
        btnEditGalaxy.setFocusable(false);
        btnEditGalaxy.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditGalaxy.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditGalaxy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditGalaxyActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEditGalaxy);
        jToolBar1.add(sep2);

        btnEditScenario.setText("Open scenario");
        btnEditScenario.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnEditScenario.setEnabled(false);
        btnEditScenario.setFocusable(false);
        btnEditScenario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEditScenario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEditScenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditScenarioActionPerformed(evt);
            }
        });
        jToolBar1.add(btnEditScenario);
        jToolBar1.add(sep3);

        btnBcsvEditor.setText("BCSV editor");
        btnBcsvEditor.setEnabled(false);
        btnBcsvEditor.setFocusable(false);
        btnBcsvEditor.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnBcsvEditor.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnBcsvEditor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBcsvEditorActionPerformed(evt);
            }
        });
        jToolBar1.add(btnBcsvEditor);
        jToolBar1.add(sep4);

        btnSettings.setText("Settings");
        btnSettings.setFocusable(false);
        btnSettings.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSettings.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSettingsActionPerformed(evt);
            }
        });
        jToolBar1.add(btnSettings);
        jToolBar1.add(sep5);

        btnAbout.setText("About");
        btnAbout.setFocusable(false);
        btnAbout.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAbout.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAboutActionPerformed(evt);
            }
        });
        jToolBar1.add(btnAbout);

        lbStatusBar.setText("Ready");
        lbStatusBar.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeGalaxies.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeGalaxies.setEnabled(false);
        treeGalaxies.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                treeGalaxiesMousePressed(evt);
            }
        });
        treeGalaxies.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeGalaxiesValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(treeGalaxies);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
            .addComponent(lbStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lbStatusBar, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void btnOpenGameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenGameActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fc.setDialogTitle("Open a game archive");
        String lastdir = Preferences.userRoot().get("lastGameDir", null);
        if (lastdir != null)
            fc.setSelectedFile(new File(lastdir));
        if (fc.showOpenDialog(this) != JFileChooser.APPROVE_OPTION)
            return;
        
        String seldir = fc.getSelectedFile().getPath();
        Preferences.userRoot().put("lastGameDir", seldir);
        
        for (JFrame form : galaxyEditors.values())
            form.dispose();
        galaxyEditors.clear();
        
        try {
            Whitehole.game = new GameArchive(new ExternalFilesystem(seldir));
        }
        catch(IOException ex) {}
        
        if (Whitehole.game.isValid()) {
            treeGalaxies.setEnabled(true);
            btnBcsvEditor.setEnabled(true);
            lbStatusBar.setText("Game directory successfully opened");
        }
        else {
            treeGalaxies.setEnabled(false);
            btnBcsvEditor.setEnabled(false);
            lbStatusBar.setText("Failed to open the directory");
        }
    }//GEN-LAST:event_btnOpenGameActionPerformed

    private void btnBcsvEditorActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnBcsvEditorActionPerformed
    {//GEN-HEADEREND:event_btnBcsvEditorActionPerformed
        new BcsvEditorForm().setVisible(true);
    }//GEN-LAST:event_btnBcsvEditorActionPerformed

    private void btnSettingsActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSettingsActionPerformed
    {//GEN-HEADEREND:event_btnSettingsActionPerformed
        new SettingsDialog(this).setVisible(true);
    }//GEN-LAST:event_btnSettingsActionPerformed

    private void btnAboutActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAboutActionPerformed
    {//GEN-HEADEREND:event_btnAboutActionPerformed
        new AboutDialog(this).setVisible(true);
    }//GEN-LAST:event_btnAboutActionPerformed

    private void treeGalaxiesMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_treeGalaxiesMousePressed
        if (evt.getClickCount() > 1)
            openGalaxy();
    }//GEN-LAST:event_treeGalaxiesMousePressed

    private void btnEditGalaxyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditGalaxyActionPerformed
        openGalaxy();
    }//GEN-LAST:event_btnEditGalaxyActionPerformed

    private void btnEditScenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditScenarioActionPerformed
        openScenario();
    }//GEN-LAST:event_btnEditScenarioActionPerformed

    private void treeGalaxiesValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treeGalaxiesValueChanged
        boolean enable = evt.getPath().getLastPathComponent() instanceof GalaxyTreeNode;
        btnEditGalaxy.setEnabled(enable);
        btnEditScenario.setEnabled(enable);
    }//GEN-LAST:event_treeGalaxiesValueChanged

    private void populateGalaxies() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Galaxies");
        
        for (String world : GalaxyDB.WORLDS.keySet()) {
            HashMap<String, String> galaxies = GalaxyDB.WORLDS.get(world);
            DefaultMutableTreeNode nworld = new DefaultMutableTreeNode(world);
            
            for (String galaxyfile : galaxies.keySet()) {
                String galaxyname = galaxies.get(galaxyfile);
                nworld.add(new GalaxyTreeNode(galaxyfile, galaxyname));
            }
            
            root.add(nworld);
        }
        
        ((DefaultTreeModel)treeGalaxies.getModel()).setRoot(root);
    }
    
    private void openGalaxy() {
        Object selectedNode = treeGalaxies.getLastSelectedPathComponent();
        if (!(selectedNode instanceof GalaxyTreeNode))
            return;
        
        String galaxy = ((GalaxyTreeNode)selectedNode).getFile();
        JFrame form;
        
        if (galaxyEditors.containsKey(galaxy)) {
            form = galaxyEditors.get(galaxy);
            if (form.isVisible()) {
                form.toFront();
                return;
            }
            else
                galaxyEditors.remove(galaxy);
        }
        
        ShaderCache.init();
        TextureCache.init();
        RendererCache.init();
        
        if (Whitehole.game.galaxyExists(galaxy)) {
            form = new GalaxyEditorForm(galaxy);
            form.setVisible(true);
            galaxyEditors.put(galaxy, form);
        }
    }
    
    private void openScenario() {
        Object selectedNode = treeGalaxies.getLastSelectedPathComponent();
        if (!(selectedNode instanceof GalaxyTreeNode))
            return;
        
        String galaxy = ((GalaxyTreeNode)selectedNode).getFile();
        JFrame form;
        
        if (galaxyEditors.containsKey(galaxy)) {
            form = galaxyEditors.get(galaxy);
            if (form.isVisible()) {
                form.toFront();
                return;
            }
            else
                galaxyEditors.remove(galaxy);
        }
        
        if (Whitehole.game.galaxyExists(galaxy)) {
            form = new ScenarioEditor(galaxy);
            form.setVisible(true);
            galaxyEditors.put(galaxy, form);
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbout;
    private javax.swing.JButton btnBcsvEditor;
    private javax.swing.JButton btnEditGalaxy;
    private javax.swing.JButton btnEditScenario;
    private javax.swing.JButton btnOpenGame;
    private javax.swing.JButton btnSettings;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JLabel lbStatusBar;
    private javax.swing.JToolBar.Separator sep1;
    private javax.swing.JToolBar.Separator sep2;
    private javax.swing.JToolBar.Separator sep3;
    private javax.swing.JToolBar.Separator sep4;
    private javax.swing.JToolBar.Separator sep5;
    private javax.swing.JTree treeGalaxies;
    // End of variables declaration//GEN-END:variables
}