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
import java.util.*;
import java.util.Map.Entry;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import whitehole.ObjectDB;
import whitehole.ObjectDB.DbObject;
import whitehole.ObjectDB.DbProperty;

public class ObjectSelectDialog extends javax.swing.JDialog {
    private final DefaultMutableTreeNode objList, searchList;
    public String selectedObject, selectedLayer;
    
    public ObjectSelectDialog(java.awt.Frame parent, String selobj) {
        super(parent, true);
        
        initComponents();
        
        selectedObject = selobj;
        selectedLayer = null;
        objList = new DefaultMutableTreeNode("Objects");
        searchList = new DefaultMutableTreeNode("Search results");
        
        if (selectedObject != null) {
            lblLayer.setVisible(false);
            cmoLayer.setVisible(false);
        }
        else {
            GalaxyEditorForm galaxy = (GalaxyEditorForm)parent;
            DefaultComboBoxModel layers = (DefaultComboBoxModel)cmoLayer.getModel();
            layers.addElement("Common");
            
            for (int i = 0 ; i < 26 ; i++) {
                String ls = String.format("Layer%1$c", 'A'+i);
                if (galaxy.curZoneArc.objects.containsKey(ls.toLowerCase()))
                    layers.addElement(ls);
            }
            
            selectedObject = "";
            selectedLayer = "Common";
            cmoLayer.setSelectedItem(selectedLayer);
        }
        
        ((DefaultTreeModel)treeObjects.getModel()).setRoot(objList);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtSearch = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        treeObjects = new javax.swing.JTree();
        tlbLayer = new javax.swing.JToolBar();
        lblObject = new javax.swing.JLabel();
        txtObject = new javax.swing.JTextField();
        lblLayer = new javax.swing.JLabel();
        cmoLayer = new javax.swing.JComboBox();
        btnSelect = new javax.swing.JButton();
        pnlInfo = new javax.swing.JPanel();
        lblInternalName = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblJapaneseName = new javax.swing.JLabel();
        lblClassName = new javax.swing.JLabel();
        lblList = new javax.swing.JLabel();
        lblNotes = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaNotes = new javax.swing.JTextArea();
        lblProperties = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProperties = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Select object");
        setIconImage(Whitehole.ICON);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtSearchKeyReleased(evt);
            }
        });

        jLabel1.setText("Search: ");
        jLabel1.setToolTipText("");

        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("root");
        treeObjects.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treeObjects.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                treeObjectsMouseClicked(evt);
            }
        });
        treeObjects.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treeObjectsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treeObjects);

        tlbLayer.setFloatable(false);
        tlbLayer.setRollover(true);

        lblObject.setText("Object: ");
        tlbLayer.add(lblObject);

        txtObject.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtObjectKeyReleased(evt);
            }
        });
        tlbLayer.add(txtObject);

        lblLayer.setText(" Layer: ");
        tlbLayer.add(lblLayer);

        tlbLayer.add(cmoLayer);

        btnSelect.setText("Select");
        btnSelect.setEnabled(false);
        btnSelect.setFocusable(false);
        btnSelect.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSelect.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnSelect.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectActionPerformed(evt);
            }
        });
        tlbLayer.add(btnSelect);

        lblInternalName.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblInternalName.setText("dummy");
        lblInternalName.setToolTipText("");

        lblName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblName.setText("dummy");

        lblJapaneseName.setText("Japanese");

        lblClassName.setText("Class");

        lblList.setText("List");
        lblList.setToolTipText("");

        lblNotes.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblNotes.setText("Notes");
        lblNotes.setToolTipText("");

        txaNotes.setEditable(false);
        txaNotes.setColumns(20);
        txaNotes.setLineWrap(true);
        txaNotes.setRows(5);
        txaNotes.setWrapStyleWord(true);
        txaNotes.setAutoscrolls(false);
        jScrollPane2.setViewportView(txaNotes);

        lblProperties.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        lblProperties.setText("Properties");
        lblProperties.setToolTipText("");

        tblProperties.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Property", "Description", "Type", "Values"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tblProperties);

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 550, Short.MAX_VALUE)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblInternalName)
                            .addComponent(lblName)
                            .addComponent(lblJapaneseName)
                            .addComponent(lblClassName)
                            .addComponent(lblList)
                            .addComponent(lblNotes)
                            .addComponent(lblProperties))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblInternalName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblJapaneseName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblClassName)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblList)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblNotes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblProperties)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(tlbLayer, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSearch))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(12, 12, 12)
                .addComponent(pnlInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tlbLayer, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents
    
    private void formWindowOpened(java.awt.event.WindowEvent evt)//GEN-FIRST:event_formWindowOpened
    {//GEN-HEADEREND:event_formWindowOpened
        HashMap<String, DefaultMutableTreeNode> catList = new LinkedHashMap();
        for (Entry<String, String> cat : ObjectDB.CATEGORIES.entrySet())
            catList.put(cat.getKey(), new DefaultMutableTreeNode(cat.getValue()));
        
        HashMap<String, ObjSelectTreeNode> tempList = new LinkedHashMap();
        for (DbObject obj : ObjectDB.OBJECTS.values()) {
            if (!obj.existsForGame("smg1"))
                continue;

            ObjSelectTreeNode objnode = new ObjSelectTreeNode(obj.internalName);
            catList.get(obj.category).add(objnode);
            tempList.put(obj.internalName, objnode);
        }
        
        for (DefaultMutableTreeNode catnode : catList.values()) {
            if (catnode.getChildCount() == 0)
                continue;
            
            objList.add(catnode);
        }
        
        DefaultTreeModel treemodel = (DefaultTreeModel)treeObjects.getModel();
        TreePath path;
        
        if (!selectedObject.isEmpty() && ObjectDB.OBJECTS.containsKey(selectedObject))
            path = new TreePath(treemodel.getPathToRoot(tempList.get(selectedObject)));
        else
            path = new TreePath(treemodel.getPathToRoot(objList.getChildAt(0)));
        
        treeObjects.setSelectionPath(path);
        treeObjects.scrollPathToVisible(path);
    }//GEN-LAST:event_formWindowOpened

    private void treeObjectsValueChanged(javax.swing.event.TreeSelectionEvent evt)//GEN-FIRST:event_treeObjectsValueChanged
    {//GEN-HEADEREND:event_treeObjectsValueChanged
        TreePath selection = treeObjects.getSelectionPath();
        
        if (selection == null)
            return;
        
        DefaultTableModel modelProperties = (DefaultTableModel)tblProperties.getModel();
        
        modelProperties.setRowCount(0);
        lblInternalName.setText("(No object)");
        lblName.setText("(No name)");
        lblJapaneseName.setText("Japanese");
        lblClassName.setText("Class");
        lblList.setText("List");
        txaNotes.setText("(No notes available for this object.)");
        btnSelect.setEnabled(false);
        
        MutableTreeNode tn = (MutableTreeNode)selection.getLastPathComponent();
        
        if (!(tn instanceof ObjSelectTreeNode))
            return;
        
        String objname = ((ObjSelectTreeNode)tn).objectID;
        DbObject dbinfo = ObjectDB.OBJECTS.get(objname);
        
        lblInternalName.setText(objname);
        lblName.setText(dbinfo.name);
        lblJapaneseName.setText("Japanese: " + dbinfo.japaneseName);
        lblClassName.setText("Class: " + dbinfo.className);
        lblList.setText("List: " + dbinfo.list);
        txaNotes.setText(dbinfo.notes.isEmpty() ? "(No notes available for this object.)" : dbinfo.notes);
        
        for (DbProperty dbprop : dbinfo.properties.values()) {
            String valuestext = "";
            
            Iterator values = dbprop.values.keySet().iterator();
            while (values.hasNext()) {
                int id = (int)values.next();
                String value = dbprop.values.get(id);
                valuestext += id + " = " + value;
                
                if (values.hasNext())
                    valuestext += ", ";
            }
            
            modelProperties.addRow(new Object[] {
                dbprop.name,
                dbprop.description,
                dbprop.type,
                valuestext
            });
        }
        
        txtObject.setText(objname);
        btnSelect.setEnabled(true);
    }//GEN-LAST:event_treeObjectsValueChanged

    private void btnSelectActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnSelectActionPerformed
    {//GEN-HEADEREND:event_btnSelectActionPerformed
        selectedObject = txtObject.getText();
        
        if (selectedLayer != null)
            selectedLayer = (String)cmoLayer.getSelectedItem();
        
        dispose();
    }//GEN-LAST:event_btnSelectActionPerformed

    private void txtSearchKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_txtSearchKeyReleased
    {//GEN-HEADEREND:event_txtSearchKeyReleased
        String search = txtSearch.getText().toLowerCase();
        if (search.isEmpty()) {
            ((DefaultTreeModel) treeObjects.getModel()).setRoot(objList);
        }
        else {
            searchList.removeAllChildren();
            for (DbObject obj : ObjectDB.OBJECTS.values()) {
                if (!obj.existsForGame("smg1"))
                    continue;
                if (!obj.internalName.toLowerCase().contains(search) && !obj.name.toLowerCase().contains(search))
                    continue;

                ObjSelectTreeNode objnode = new ObjSelectTreeNode(obj.internalName);
                searchList.add(objnode);
            }
            
            if (searchList.getChildCount() == 0)
                searchList.add(new DefaultMutableTreeNode("(no results)"));
            
            ((DefaultTreeModel) treeObjects.getModel()).setRoot(searchList);
        }
    }//GEN-LAST:event_txtSearchKeyReleased

    private void treeObjectsMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_treeObjectsMouseClicked
    {//GEN-HEADEREND:event_treeObjectsMouseClicked
        TreePath selection = treeObjects.getSelectionPath();
        
        if (selection == null)
            return;
        
        MutableTreeNode tn = (MutableTreeNode)selection.getLastPathComponent();
        
        if (!(tn instanceof ObjSelectTreeNode) || evt.getClickCount() < 2)
            return;
        
        selectedObject = ((ObjSelectTreeNode)tn).objectID;
        dispose();
    }//GEN-LAST:event_treeObjectsMouseClicked

    private void txtObjectKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtObjectKeyReleased
        btnSelect.setEnabled(!(selectedObject = txtObject.getText()).isEmpty());
    }//GEN-LAST:event_txtObjectKeyReleased
    
    public class ObjSelectTreeNode extends SimpleTreeNode {
        private String objectID;
        
        public ObjSelectTreeNode(String objid) {
            parent = null;
            objectID = objid;
        }
        
        @Override
        public String toString() {
            return ObjectDB.OBJECTS.get(objectID).toString();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnSelect;
    private javax.swing.JComboBox cmoLayer;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblClassName;
    private javax.swing.JLabel lblInternalName;
    private javax.swing.JLabel lblJapaneseName;
    private javax.swing.JLabel lblLayer;
    private javax.swing.JLabel lblList;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNotes;
    private javax.swing.JLabel lblObject;
    private javax.swing.JLabel lblProperties;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JTable tblProperties;
    private javax.swing.JToolBar tlbLayer;
    private javax.swing.JTree treeObjects;
    private javax.swing.JTextArea txaNotes;
    private javax.swing.JTextField txtObject;
    private javax.swing.JTextField txtSearch;
    // End of variables declaration//GEN-END:variables
}