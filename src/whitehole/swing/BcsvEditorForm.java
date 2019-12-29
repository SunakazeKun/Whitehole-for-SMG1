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

import whitehole.Whitehole;
import whitehole.io.FilesystemBase;
import whitehole.io.RarcFilesystem;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;
import javax.swing.table.*;
import whitehole.smg.Bcsv;

public class BcsvEditorForm extends javax.swing.JFrame {
    private FilesystemBase archive;
    private Bcsv bcsv;
    private String input;
    
    public BcsvEditorForm() {
        initComponents();
        
        archive = null;
        bcsv = null;
        input = null;
        
        tbArchiveName.setText("/StageData/EggStarGalaxy/EggStarGalaxyScenario.arc");
        tbFileName.setText("/EggStarGalaxyScenario/ScenarioData.bcsv");
    }
    
    private void open() {
        DefaultTableModel table = (DefaultTableModel)tblBcsv.getModel();
        table.setRowCount(0);
        table.setColumnCount(0);
        
        try {
            if (archive != null)
                archive.close();
            if (bcsv != null)
                bcsv.close();
            
            archive = null;
            bcsv = null;
            
            archive = new RarcFilesystem(Whitehole.game.filesystem.openFile(tbArchiveName.getText()));
            bcsv = new Bcsv(archive.openFile(tbFileName.getText()));
        }
        catch (IOException ex) {
            JOptionPane.showMessageDialog(
                    this, "Can't open BCSV file: " + ex.getMessage(),
                    Whitehole.NAME, JOptionPane.ERROR_MESSAGE
            );
            
            try {
                if (bcsv != null)
                    bcsv.close();
                if (archive != null)
                    archive.close();
            }
            catch (IOException ex2) {}
            
            bcsv = null;
            archive = null;
            return;
        }
        
        table.setRowCount(0);
        table.setColumnCount(0);
        
        for (Bcsv.Field field : bcsv.fields.values())
            table.addColumn(field.name);
        
        for (Bcsv.Entry entry : bcsv.entries) {
            Vector<Object> row = new Vector<>(bcsv.fields.size());
            
            for (Bcsv.Field field : bcsv.fields.values())
                row.add(entry.get(field.hash));
            
            table.addRow(row);
        }
    }
    
    private void save() {
        bcsv.entries.clear();
        
        for (int r = 0; r < tblBcsv.getRowCount(); r++) {
            Bcsv.Entry entry = new Bcsv.Entry();
            int c = 0;
            
            for (Bcsv.Field field : bcsv.fields.values()) {
                Object valobj = tblBcsv.getValueAt(r, c);
                String val = (valobj == null) ? "" : valobj.toString();
                
                try {
                    switch (field.type) {
                        case 0:
                        case 3:
                            entry.put(field.hash, Integer.parseInt(val));
                            break;
                        case 2:
                            entry.put(field.hash, Float.parseFloat(val));
                            break;
                        case 4:
                            entry.put(field.hash, Short.parseShort(val));
                            break;
                        case 5:
                            entry.put(field.hash, Byte.parseByte(val));
                            break;
                        case 6:
                            entry.put(field.hash, val);
                            break;
                    }
                }
                catch (NumberFormatException ex) {
                    switch (field.type) {
                        case 0:
                        case 3: entry.put(field.hash, (int)0); break;
                        case 2: entry.put(field.hash, 0f); break;
                        case 4: entry.put(field.hash, (short)0); break;
                        case 5: entry.put(field.hash, (byte)0); break;
                        case 6: entry.put(field.hash, ""); break;
                    }
                }
                c++;
            }
            bcsv.entries.add(entry);
        }
        try { 
            bcsv.save();
            archive.save();
        }
        catch (IOException ex) {
            System.out.println(ex);
        }
    }

    private void enterZoneName() {
        input = (String)JOptionPane.showInputDialog(this,
                    "Enter the name of the stage:",
                    Whitehole.NAME, JOptionPane.PLAIN_MESSAGE
        );
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jToolBar1 = new javax.swing.JToolBar();
        lblArchive = new javax.swing.JLabel();
        tbArchiveName = new javax.swing.JTextField();
        lblFile = new javax.swing.JLabel();
        tbFileName = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblBcsv = new javax.swing.JTable();
        jToolBar3 = new javax.swing.JToolBar();
        btnOpen = new javax.swing.JButton();
        spr2 = new javax.swing.JToolBar.Separator();
        btnSave = new javax.swing.JButton();
        spr3 = new javax.swing.JToolBar.Separator();
        btnAddRow = new javax.swing.JButton();
        spr5 = new javax.swing.JToolBar.Separator();
        btnDuplicateRow = new javax.swing.JButton();
        spr6 = new javax.swing.JToolBar.Separator();
        btnDeleteRow = new javax.swing.JButton();
        spr7 = new javax.swing.JToolBar.Separator();
        btnClear = new javax.swing.JButton();
        menubar = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        subOpen = new javax.swing.JMenuItem();
        subSave = new javax.swing.JMenuItem();
        subClose = new javax.swing.JMenuItem();
        mnuOpen = new javax.swing.JMenu();
        mnuGalaxy = new javax.swing.JMenu();
        subScenarioData = new javax.swing.JMenuItem();
        subZoneList = new javax.swing.JMenuItem();
        mnuZone = new javax.swing.JMenu();
        subCameraParam = new javax.swing.JMenuItem();
        subLightDataZone = new javax.swing.JMenuItem();
        subStageInfo = new javax.swing.JMenuItem();
        subChangeSceneListInfo = new javax.swing.JMenuItem();
        mnuObjects = new javax.swing.JMenu();
        subPlanetMapData = new javax.swing.JMenuItem();
        subObjName = new javax.swing.JMenuItem();
        subAstroNamePlateData = new javax.swing.JMenuItem();
        mnuNPCData = new javax.swing.JMenu();
        subCaretaker = new javax.swing.JMenuItem();
        subHoneyBee = new javax.swing.JMenuItem();
        subKinopio = new javax.swing.JMenuItem();
        subPenguinRacer = new javax.swing.JMenuItem();
        subPenguinRacerLeader = new javax.swing.JMenuItem();
        subTicoComet = new javax.swing.JMenuItem();
        subTicoGalaxy = new javax.swing.JMenuItem();
        subTicoFat = new javax.swing.JMenuItem();
        subTicoShop = new javax.swing.JMenuItem();
        mnuEffects = new javax.swing.JMenu();
        subParticleNames = new javax.swing.JMenuItem();
        subAutoEffectList = new javax.swing.JMenuItem();
        mnuOther = new javax.swing.JMenu();
        subLightData = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("BCSV editor");
        setIconImage(Whitehole.ICON);
        setSize(new java.awt.Dimension(0, 0));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jToolBar1.setFloatable(false);
        jToolBar1.setRollover(true);

        lblArchive.setText(" Archive: ");
        jToolBar1.add(lblArchive);

        tbArchiveName.setToolTipText("");
        tbArchiveName.setMaximumSize(new java.awt.Dimension(375, 20));
        tbArchiveName.setMinimumSize(new java.awt.Dimension(375, 20));
        tbArchiveName.setPreferredSize(new java.awt.Dimension(375, 20));
        jToolBar1.add(tbArchiveName);

        lblFile.setText("                    File: ");
        jToolBar1.add(lblFile);

        tbFileName.setMaximumSize(new java.awt.Dimension(375, 20));
        tbFileName.setMinimumSize(new java.awt.Dimension(375, 20));
        tbFileName.setPreferredSize(new java.awt.Dimension(375, 20));
        jToolBar1.add(tbFileName);

        tblBcsv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblBcsv.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        jScrollPane1.setViewportView(tblBcsv);

        jToolBar3.setFloatable(false);
        jToolBar3.setRollover(true);
        jToolBar3.setAlignmentY(0.5F);
        jToolBar3.setInheritsPopupMenu(true);

        btnOpen.setText("Open");
        btnOpen.setFocusable(false);
        btnOpen.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnOpen.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOpenActionPerformed(evt);
            }
        });
        jToolBar3.add(btnOpen);
        jToolBar3.add(spr2);

        btnSave.setText("Save");
        btnSave.setFocusable(false);
        btnSave.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSave.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jToolBar3.add(btnSave);
        jToolBar3.add(spr3);

        btnAddRow.setText("Add row");
        btnAddRow.setFocusable(false);
        btnAddRow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddRow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRowActionPerformed(evt);
            }
        });
        jToolBar3.add(btnAddRow);
        jToolBar3.add(spr5);

        btnDuplicateRow.setText("Duplicate row");
        btnDuplicateRow.setToolTipText("");
        btnDuplicateRow.setFocusable(false);
        btnDuplicateRow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDuplicateRow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDuplicateRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDuplicateRowActionPerformed(evt);
            }
        });
        jToolBar3.add(btnDuplicateRow);
        jToolBar3.add(spr6);

        btnDeleteRow.setText("Delete row");
        btnDeleteRow.setFocusable(false);
        btnDeleteRow.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnDeleteRow.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnDeleteRow.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRowActionPerformed(evt);
            }
        });
        jToolBar3.add(btnDeleteRow);
        jToolBar3.add(spr7);

        btnClear.setText("Delete all rows");
        btnClear.setFocusable(false);
        btnClear.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnClear.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClearActionPerformed(evt);
            }
        });
        jToolBar3.add(btnClear);

        mnuFile.setText("File");

        subOpen.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        subOpen.setText("Open");
        subOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subOpenActionPerformed(evt);
            }
        });
        mnuFile.add(subOpen);

        subSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        subSave.setText("Save");
        subSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subSaveActionPerformed(evt);
            }
        });
        mnuFile.add(subSave);

        subClose.setText("Close");
        subClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCloseActionPerformed(evt);
            }
        });
        mnuFile.add(subClose);

        menubar.add(mnuFile);

        mnuOpen.setText("Open");

        mnuGalaxy.setText("Galaxy");
        mnuGalaxy.setToolTipText("");

        subScenarioData.setText("ScenarioData");
        subScenarioData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subScenarioDataActionPerformed(evt);
            }
        });
        mnuGalaxy.add(subScenarioData);

        subZoneList.setText("ZoneList");
        subZoneList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subZoneListActionPerformed(evt);
            }
        });
        mnuGalaxy.add(subZoneList);

        mnuOpen.add(mnuGalaxy);

        mnuZone.setText("Zone");
        mnuZone.setToolTipText("");

        subCameraParam.setText("Camera");
        subCameraParam.setToolTipText("");
        subCameraParam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCameraParamActionPerformed(evt);
            }
        });
        mnuZone.add(subCameraParam);

        subLightDataZone.setText("Light");
        subLightDataZone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subLightDataZoneActionPerformed(evt);
            }
        });
        mnuZone.add(subLightDataZone);

        subStageInfo.setText("StageInfo");
        subStageInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subStageInfoActionPerformed(evt);
            }
        });
        mnuZone.add(subStageInfo);

        subChangeSceneListInfo.setText("ChangeSceneListInfo");
        subChangeSceneListInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subChangeSceneListInfoActionPerformed(evt);
            }
        });
        mnuZone.add(subChangeSceneListInfo);

        mnuOpen.add(mnuZone);

        mnuObjects.setText("Objects");

        subPlanetMapData.setText("PlanetMapDataTable");
        subPlanetMapData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subPlanetMapDataActionPerformed(evt);
            }
        });
        mnuObjects.add(subPlanetMapData);

        subObjName.setText("ObjNameTable");
        subObjName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subObjNameActionPerformed(evt);
            }
        });
        mnuObjects.add(subObjName);

        subAstroNamePlateData.setText("AstroNamePlateData");
        subAstroNamePlateData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subAstroNamePlateDataActionPerformed(evt);
            }
        });
        mnuObjects.add(subAstroNamePlateData);

        mnuNPCData.setText("NPCData");

        subCaretaker.setText("CaretakerItem");
        subCaretaker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subCaretakerActionPerformed(evt);
            }
        });
        mnuNPCData.add(subCaretaker);

        subHoneyBee.setText("HoneyBeeItem");
        subHoneyBee.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subHoneyBeeActionPerformed(evt);
            }
        });
        mnuNPCData.add(subHoneyBee);

        subKinopio.setText("KinopioItem");
        subKinopio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subKinopioActionPerformed(evt);
            }
        });
        mnuNPCData.add(subKinopio);

        subPenguinRacer.setText("PenguinRacerItem");
        subPenguinRacer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subPenguinRacerActionPerformed(evt);
            }
        });
        mnuNPCData.add(subPenguinRacer);

        subPenguinRacerLeader.setText("PenguinRacerLeaderItem");
        subPenguinRacerLeader.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subPenguinRacerLeaderActionPerformed(evt);
            }
        });
        mnuNPCData.add(subPenguinRacerLeader);

        subTicoComet.setText("TicoCometItem");
        subTicoComet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subTicoCometActionPerformed(evt);
            }
        });
        mnuNPCData.add(subTicoComet);

        subTicoGalaxy.setText("TicoGalaxyItem");
        subTicoGalaxy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subTicoGalaxyActionPerformed(evt);
            }
        });
        mnuNPCData.add(subTicoGalaxy);

        subTicoFat.setText("TicoFatItem");
        subTicoFat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subTicoFatActionPerformed(evt);
            }
        });
        mnuNPCData.add(subTicoFat);

        subTicoShop.setText("TicoShopItem");
        subTicoShop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subTicoShopActionPerformed(evt);
            }
        });
        mnuNPCData.add(subTicoShop);

        mnuObjects.add(mnuNPCData);

        mnuOpen.add(mnuObjects);

        mnuEffects.setText("Effects");

        subParticleNames.setText("ParticleNames");
        subParticleNames.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subParticleNamesActionPerformed(evt);
            }
        });
        mnuEffects.add(subParticleNames);

        subAutoEffectList.setText("AutoEffectList");
        subAutoEffectList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subAutoEffectListActionPerformed(evt);
            }
        });
        mnuEffects.add(subAutoEffectList);

        mnuOpen.add(mnuEffects);

        mnuOther.setText("Other");

        subLightData.setText("LightData");
        subLightData.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                subLightDataActionPerformed(evt);
            }
        });
        mnuOther.add(subLightData);

        mnuOpen.add(mnuOther);

        menubar.add(mnuOpen);

        setJMenuBar(menubar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(jToolBar3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jToolBar3, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddRowActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnAddRowActionPerformed
    {//GEN-HEADEREND:event_btnAddRowActionPerformed
        DefaultTableModel table = (DefaultTableModel)tblBcsv.getModel();
        table.addRow((Object[])null);
    }//GEN-LAST:event_btnAddRowActionPerformed

    private void btnDuplicateRowActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDuplicateRowActionPerformed
        DefaultTableModel table = (DefaultTableModel)tblBcsv.getModel();
        int[] sel = tblBcsv.getSelectedRows();
        if (sel.length < 1)
            return;

        Vector data = table.getDataVector();
        Vector row;

        for (int i = 0 ; i < sel.length ; i++) {
            row = (Vector)data.elementAt(sel[i]);
            table.addRow((Vector)row.clone());
        }
    }//GEN-LAST:event_btnDuplicateRowActionPerformed

    private void btnDeleteRowActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnDeleteRowActionPerformed
    {//GEN-HEADEREND:event_btnDeleteRowActionPerformed
        int[] sel = tblBcsv.getSelectedRows();
        if (sel.length < 1)
            return;
        
        DefaultTableModel table = (DefaultTableModel)tblBcsv.getModel();
        for (int i = 0 ; i < sel.length ; i++)
            table.removeRow(sel[i]-i);
    }//GEN-LAST:event_btnDeleteRowActionPerformed

    private void btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClearActionPerformed
        DefaultTableModel table = (DefaultTableModel)tblBcsv.getModel();
        table.setRowCount(0);
    }//GEN-LAST:event_btnClearActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowClosing
    {//GEN-HEADEREND:event_formWindowClosing
        try {
            if (bcsv != null)
                bcsv.close();
            if (archive != null)
                archive.close();
        }
        catch (IOException ex) {}
    }//GEN-LAST:event_formWindowClosing

    private void subOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subOpenActionPerformed
        open();
    }//GEN-LAST:event_subOpenActionPerformed

    private void subSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subSaveActionPerformed
        save();
    }//GEN-LAST:event_subSaveActionPerformed

    private void subCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCloseActionPerformed
        dispose();
    }//GEN-LAST:event_subCloseActionPerformed

    private void subPlanetMapDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subPlanetMapDataActionPerformed
        tbArchiveName.setText("/ObjectData/PlanetMapDataTable.arc");
        tbFileName.setText("/PlanetMapDataTable/PlanetMapDataTable.bcsv");
        open();
    }//GEN-LAST:event_subPlanetMapDataActionPerformed

    private void subObjNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subObjNameActionPerformed
        tbArchiveName.setText("/StageData/ObjNameTable.arc");
        tbFileName.setText("/ObjNameTable/ObjNameTable.tbl");
        open();
    }//GEN-LAST:event_subObjNameActionPerformed

    private void subAstroNamePlateDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subAstroNamePlateDataActionPerformed
        tbArchiveName.setText("/ObjectData/AstroNamePlateData.arc");
        tbFileName.setText("/AstroNamePlateData/AstroNamePlateData.bcsv");
        open();
    }//GEN-LAST:event_subAstroNamePlateDataActionPerformed

    private void subCaretakerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCaretakerActionPerformed
        tbArchiveName.setText("/ObjectData/NPCData.arc");
        tbFileName.setText("/NPCData/CaretakerItem.bcsv");
        open();
    }//GEN-LAST:event_subCaretakerActionPerformed

    private void subHoneyBeeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subHoneyBeeActionPerformed
        tbArchiveName.setText("/ObjectData/NPCData.arc");
        tbFileName.setText("/NPCData/HoneyBeeItem.bcsv");
        open();
    }//GEN-LAST:event_subHoneyBeeActionPerformed

    private void subKinopioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subKinopioActionPerformed
        tbArchiveName.setText("/ObjectData/NPCData.arc");
        tbFileName.setText("/NPCData/KinopioItem.bcsv");
        open();
    }//GEN-LAST:event_subKinopioActionPerformed

    private void subPenguinRacerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subPenguinRacerActionPerformed
        tbArchiveName.setText("/ObjectData/NPCData.arc");
        tbFileName.setText("/NPCData/PenguinRacerItem.bcsv");
        open();
    }//GEN-LAST:event_subPenguinRacerActionPerformed

    private void subPenguinRacerLeaderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subPenguinRacerLeaderActionPerformed
        tbArchiveName.setText("/ObjectData/NPCData.arc");
        tbFileName.setText("/NPCData/PenguinRacerLeaderItem.bcsv");
        open();
    }//GEN-LAST:event_subPenguinRacerLeaderActionPerformed

    private void subTicoCometActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subTicoCometActionPerformed
        tbArchiveName.setText("/ObjectData/NPCData.arc");
        tbFileName.setText("/NPCData/TicoCometItem.bcsv");
        open();
    }//GEN-LAST:event_subTicoCometActionPerformed

    private void subTicoGalaxyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subTicoGalaxyActionPerformed
        tbArchiveName.setText("/ObjectData/NPCData.arc");
        tbFileName.setText("/NPCData/TicoGalaxyItem.bcsv");
        open();
    }//GEN-LAST:event_subTicoGalaxyActionPerformed

    private void subTicoFatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subTicoFatActionPerformed
        tbArchiveName.setText("/ObjectData/NPCData.arc");
        tbFileName.setText("/NPCData/TicoFatItem.bcsv");
        open();
    }//GEN-LAST:event_subTicoFatActionPerformed

    private void subTicoShopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subTicoShopActionPerformed
        tbArchiveName.setText("/ObjectData/NPCData.arc");
        tbFileName.setText("/NPCData/TicoShopItem.bcsv");
        open();
    }//GEN-LAST:event_subTicoShopActionPerformed

    private void subParticleNamesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subParticleNamesActionPerformed
        tbArchiveName.setText("/ParticleData/Effect.arc");
        tbFileName.setText("/Effect/ParticleNames.bcsv");
        open();
    }//GEN-LAST:event_subParticleNamesActionPerformed

    private void subAutoEffectListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subAutoEffectListActionPerformed
        tbArchiveName.setText("/ParticleData/Effect.arc");
        tbFileName.setText("/Effect/AutoEffectList.bcsv");
        open();
    }//GEN-LAST:event_subAutoEffectListActionPerformed

    private void subLightDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subLightDataActionPerformed
        tbArchiveName.setText("/ObjectData/LightData.arc");
        tbFileName.setText("/LightData/LightData.bcsv");
        open();
    }//GEN-LAST:event_subLightDataActionPerformed

    private void btnOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOpenActionPerformed
        open();
    }//GEN-LAST:event_btnOpenActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        save();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void subScenarioDataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subScenarioDataActionPerformed
        enterZoneName();
        
        if (input != null && !input.isEmpty()) {
            tbArchiveName.setText("/StageData/" + input + "/" + input + "Scenario.arc");
            tbFileName.setText("/" + input + "Scenario/ScenarioData.bcsv");
            open();
        }
    }//GEN-LAST:event_subScenarioDataActionPerformed

    private void subZoneListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subZoneListActionPerformed
        enterZoneName();
        
        if (input != null && !input.isEmpty()) {
            tbArchiveName.setText("/StageData/" + input + "/" + input + "Scenario.arc");
            tbFileName.setText("/" + input + "Scenario/ZoneList.bcsv");
            open();
        }
    }//GEN-LAST:event_subZoneListActionPerformed

    private void subCameraParamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subCameraParamActionPerformed
        enterZoneName();
        
        if (input != null && !input.isEmpty()) {
            tbArchiveName.setText("/StageData/" + input + ".arc");
            tbFileName.setText("/Stage/camera/CameraParam.bcam");
            open();
        }
    }//GEN-LAST:event_subCameraParamActionPerformed

    private void subLightDataZoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subLightDataZoneActionPerformed
        enterZoneName();
        
        if (input != null && !input.isEmpty()) {
            tbArchiveName.setText("/ObjectData/LightData.arc");
            tbFileName.setText("/LightData/Light" + input + ".bcsv");
            open();
        }
    }//GEN-LAST:event_subLightDataZoneActionPerformed

    private void subStageInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subStageInfoActionPerformed
        enterZoneName();
        
        if (input != null && !input.isEmpty()) {
            tbArchiveName.setText("/StageData/" + input + ".arc");
            tbFileName.setText("/Stage/jmp/List/StageInfo");
            open();
        }
    }//GEN-LAST:event_subStageInfoActionPerformed

    private void subChangeSceneListInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_subChangeSceneListInfoActionPerformed
        enterZoneName();
        
        if (input != null && !input.isEmpty()) {
            tbArchiveName.setText("/StageData/" + input + ".arc");
            tbFileName.setText("/Stage/jmp/List/ChangeSceneListInfo");
            open();
        }
    }//GEN-LAST:event_subChangeSceneListInfoActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddRow;
    private javax.swing.JButton btnClear;
    private javax.swing.JButton btnDeleteRow;
    private javax.swing.JButton btnDuplicateRow;
    private javax.swing.JButton btnOpen;
    private javax.swing.JButton btnSave;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JToolBar jToolBar3;
    private javax.swing.JLabel lblArchive;
    private javax.swing.JLabel lblFile;
    private javax.swing.JMenuBar menubar;
    private javax.swing.JMenu mnuEffects;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenu mnuGalaxy;
    private javax.swing.JMenu mnuNPCData;
    private javax.swing.JMenu mnuObjects;
    private javax.swing.JMenu mnuOpen;
    private javax.swing.JMenu mnuOther;
    private javax.swing.JMenu mnuZone;
    private javax.swing.JToolBar.Separator spr2;
    private javax.swing.JToolBar.Separator spr3;
    private javax.swing.JToolBar.Separator spr5;
    private javax.swing.JToolBar.Separator spr6;
    private javax.swing.JToolBar.Separator spr7;
    private javax.swing.JMenuItem subAstroNamePlateData;
    private javax.swing.JMenuItem subAutoEffectList;
    private javax.swing.JMenuItem subCameraParam;
    private javax.swing.JMenuItem subCaretaker;
    private javax.swing.JMenuItem subChangeSceneListInfo;
    private javax.swing.JMenuItem subClose;
    private javax.swing.JMenuItem subHoneyBee;
    private javax.swing.JMenuItem subKinopio;
    private javax.swing.JMenuItem subLightData;
    private javax.swing.JMenuItem subLightDataZone;
    private javax.swing.JMenuItem subObjName;
    private javax.swing.JMenuItem subOpen;
    private javax.swing.JMenuItem subParticleNames;
    private javax.swing.JMenuItem subPenguinRacer;
    private javax.swing.JMenuItem subPenguinRacerLeader;
    private javax.swing.JMenuItem subPlanetMapData;
    private javax.swing.JMenuItem subSave;
    private javax.swing.JMenuItem subScenarioData;
    private javax.swing.JMenuItem subStageInfo;
    private javax.swing.JMenuItem subTicoComet;
    private javax.swing.JMenuItem subTicoFat;
    private javax.swing.JMenuItem subTicoGalaxy;
    private javax.swing.JMenuItem subTicoShop;
    private javax.swing.JMenuItem subZoneList;
    private javax.swing.JTextField tbArchiveName;
    private javax.swing.JTextField tbFileName;
    private javax.swing.JTable tblBcsv;
    // End of variables declaration//GEN-END:variables
}