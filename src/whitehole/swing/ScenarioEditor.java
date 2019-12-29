package whitehole.swing;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import whitehole.Whitehole;
import whitehole.smg.GalaxyArchive;
import whitehole.util.Utils;

public class ScenarioEditor extends javax.swing.JFrame {
    public static final HashMap<String, String> COMETS = new LinkedHashMap() {{
        put("", "None");
        put("Red", "Speedy");
        put("Blue", "Cosmic");
        put("Dark", "Daredevil");
        put("Quick", "Fast Foes");
        put("Purple", "Purple");
        put("Black", "Timed Purple");
    }};
    public static final HashMap<String, String> SPAWNERS = new LinkedHashMap() {{
        put("", "None");
        put("１００枚コイン", "100PurpleCoins");
        put("ボスベーゴマン", "BossBegoman");
	put("ボスカメック", "BossKameck");
	put("ボスカメック2", "BossKameck2");
	put("ボスカメムシ", "BossStinkBug");
        put("壊れる籠", "BreakableCage");
        put("壊れる籠[大]", "BreakableCageL");
        put("壊れる籠[回転タイプ]", "BreakableCageRotate");
        put("壊れる固定具", "BreakableFixation");
        put("壊れるゴミ", "BreakableTrash");
	put("ゴミ管理人", "Caretaker");
	put("クロックワークハンドル", "ClockworkHandle");
	put("チコ集め", "CollectTico");
	put("コンクリートブロック", "ConcreteBlock");
	put("クリスタルケージ[大]", "CrystalCageL");
	put("クリスタルケージ[中]", "CrystalCageM");
	put("クリスタルケージ[小]", "CrystalCageS");
	put("ディノパックン", "DinoPackun");
	put("ディノパックン戦Lv2惑星", "DinoPackunVs2");
	put("ドドリュウ", "Dodoryu");
	put("エレクトリックバズーカ", "ElectricBazooka");
	put("クリボー全滅チェッカー", "ExterminationCheckerKuribo");
	put("メラメラ全滅チェッカー", "ExterminationMeramera");
	put("スカルシャークベイビー全滅チェッカー", "ExterminationSkeletalFishBaby");
	put("フリップパネル監視者", "FlipPanelObserver");
        put("アイスメラメラキング", "IceMerameraKing");
	put("つらら岩", "IcicleRock");
	put("キノピオ", "Kinopio");
	put("天文台用キノピオ", "KinopioAstro");
	put("クッパＪｒシップ", "KoopaJrShip");
	put("クッパ彫像", "KoopaStatue");
	put("クッパ大王（ＶＳ１）", "KoopaVs1");
	put("クッパ大王（ＶＳ２）", "KoopaVs2");
	put("クッパ大王（ＶＳ３）", "KoopaVs3");
	put("イベント用ルイージ", "LuigiEvent");
	put("ビー玉惑星", "MarblePlanet");
	put("メカクッパパーツヘッド", "MechaKoopaPartsHead");
	put("音符の妖精", "NoteFairy");
	put("オタキング", "OtaKing");
        put("ペンギンコーチ", "PenguinCoach");
	put("ペンギンレーサーリーダー", "PenguinRacerLeader");
	put("ポルタ", "Polta");
	put("砂上下タワー破壊壁Ａ", "SandUpDownTowerBreakableWallA");
	put("砂上下タワー破壊壁Ｂ", "SandUpDownTowerBreakableWallB");
	put("スカルシャーク", "SkeletalFishBoss");
	put("雪ブロック", "SnowBlockA");
	put("子連れカメムシ", "StinkBugParent");
	put("沈没船", "SunkenShip");
	put("シャッチー", "Syati");
	put("タルタコの樽A", "TakoBarrelA");
	put("たまころ", "Tamakoro");
	put("たまころ[チュートリアル付き]", "TamakoroWithTutorial");
	put("テレサチーフ", "TeresaChief");
	put("テレサマンション通り抜け穴のフタ", "TeresaMansionHoleCover");
	put("テレサレーサー", "TeresaRacer");
	put("トゥームスパイダー", "TombSpider");
	put("ひび割れ宝箱（パワースター）", "TreasureBoxCrackedPowerStar");
	put("いたずらウサギ", "TrickRabbit");
	put("いたずらウサギ[自由逃走]", "TrickRabbitFreeRun");
	// TrickRabbitGhost has no proper ObjNameTable entry
	put("いたずらウサギ集め", "TrickRabbitSnowCollect");
	put("三脚ボス", "TripodBoss");
	put("三脚ボス２", "Tripod2Boss");
	put("ウォーターバズーカ", "WaterBazooka");
	put("木箱", "WoodBox");
    }};
    
    private final String galaxyName;
    private GalaxyArchive galaxy;
    private GalaxyArchive.Scenario selected;
    
    private final DefaultListModel modelScenarios, modelZones;
    private final CheckBoxList listPowerStarId, listLayers;
    private final JCheckBox[] chksPowerStarId, chksLayers;
    
    public ScenarioEditor(String galaxy) {
        galaxyName = galaxy;
        initComponents();
        
        modelScenarios = (DefaultListModel)listScenarios.getModel();
        modelZones = (DefaultListModel)listZones.getModel();
        listPowerStarId = new CheckBoxList();
        listLayers = new CheckBoxList();
        scrListPowerStarId.setViewportView(listPowerStarId);
        scrListLayers.setViewportView(listLayers);
        chksPowerStarId = new JCheckBox[7];
        chksLayers = new JCheckBox[16];
        
        for (int i = 0 ; i < 7 ; i++)
            chksPowerStarId[i] = new JCheckBox(String.format("Star %d", i + 1));
        listPowerStarId.setListData(chksPowerStarId);
        listPowerStarId.setEnabled(false);
        listPowerStarId.setEventListener((int index, boolean status) -> {
            togglePowerStarIdBit(index, status);
        });
        
        for (int i = 0 ; i < 16 ; i++)
            chksLayers[i] = new JCheckBox("Layer" + (char)('A' + i));
        listLayers.setListData(chksLayers);
        listLayers.setEnabled(false);
        listLayers.setEventListener((int index, boolean status) -> {
               toggleLayerBit(index, status);
        });
        
        load();
    }
    
    private void load() {
        try {
            galaxy = Whitehole.game.openGalaxy(galaxyName);
        }
        catch(IOException ex) { System.out.println(ex); }
        
        if (galaxy != null) {
            for (GalaxyArchive.Scenario scenario : galaxy.scenarios)
                modelScenarios.addElement(scenario);

            for (String zone : galaxy.zones)
                modelZones.addElement(zone);
        }
    }
    
    private void save() {
        if (galaxy == null)
            return;
        
        try {
            galaxy.save();
        }
        catch (IOException ex) { System.out.println(ex); }
    }
    
    private void close() {
        if (galaxy == null)
            return;
        
        try {
            galaxy.close();
        }
        catch (IOException ex) { System.out.println(ex); }
    }
    
    private void enableComponents(boolean b) {
        spnScenarioNo.setEnabled(b);
        txtScenarioName.setEnabled(b);
        cmoAppearPowerStarObj.setEnabled(b);
        cmoComet.setEnabled(b);
        chkIsHidden.setEnabled(b);
        listPowerStarId.setEnabled(b);
    }
    
    private void loadScenario() {
        int index = listScenarios.getSelectedIndex();
        if (index < 0) {
            selected = null;
            enableComponents(false);
        }
        else {
            selected = galaxy.scenarios.get(index);
            spnScenarioNo.setValue(selected.index);
            txtScenarioName.setText(selected.name);
            cmoAppearPowerStarObj.setSelectedItem(SPAWNERS.getOrDefault(selected.appearPowerStarObj, "None"));
            cmoComet.setSelectedItem(COMETS.getOrDefault(selected.comet, "None"));
            chkIsHidden.setSelected(selected.isHidden);
            for (int i = 0 ; i < 7 ; i++)
                chksPowerStarId[i].setSelected(((selected.powerStarId >> i) & 1) == 1);

            listPowerStarId.repaint();
            enableComponents(true);
            loadLayer();
        }
    }
    
    private void loadLayer() {
        int indexScenario = listScenarios.getSelectedIndex();
        int indexZone = listZones.getSelectedIndex();
        if (indexScenario < 0 || indexZone < 0) {
            listLayers.setEnabled(false);
            return;
        }
        
        String zone = galaxy.zones.get(indexZone);
        int layers = selected.layers.get(zone);
        for (int i = 0 ; i < 16 ; i++) {
            chksLayers[i].setEnabled(false);
            chksLayers[i].setSelected(((layers >> i) & 1) == 1);
        }
        
        listLayers.setEnabled(true);
        listLayers.repaint();
    }
    
    private void togglePowerStarIdBit(int index, boolean status) {
        selected.powerStarId &= ~(1 << index);
        if (status)
            selected.powerStarId |= 1 << index;
    }
    
    private void toggleLayerBit(int index, boolean status) {
        String zone = listZones.getSelectedValue();
        int layers = selected.layers.get(zone);

        layers &= ~(1 << index);
        if (status)
            layers |= 1 << index;

        selected.layers.put(zone, layers);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jSplitPane1 = new javax.swing.JSplitPane();
        pnlLeft = new javax.swing.JPanel();
        lblScenarios = new javax.swing.JLabel();
        tlbScenarios = new javax.swing.JToolBar();
        btnAddScenario = new javax.swing.JButton();
        sep1 = new javax.swing.JToolBar.Separator();
        btnRemoveScenario = new javax.swing.JButton();
        scrListScenarios = new javax.swing.JScrollPane();
        listScenarios = new javax.swing.JList<>();
        lblZones = new javax.swing.JLabel();
        tlbZones = new javax.swing.JToolBar();
        btnAddZone = new javax.swing.JButton();
        sep2 = new javax.swing.JToolBar.Separator();
        btnRemoveZone = new javax.swing.JButton();
        scrListZones = new javax.swing.JScrollPane();
        listZones = new javax.swing.JList<>();
        fillerLeft = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        pnlRight = new javax.swing.JPanel();
        lblScenarioGeneral = new javax.swing.JLabel();
        lblScenarioNo = new javax.swing.JLabel();
        lblScenarioName = new javax.swing.JLabel();
        lblAppearPowerStarObj = new javax.swing.JLabel();
        lblComet = new javax.swing.JLabel();
        spnScenarioNo = new javax.swing.JSpinner();
        txtScenarioName = new javax.swing.JTextField();
        cmoAppearPowerStarObj = new javax.swing.JComboBox<>();
        cmoComet = new javax.swing.JComboBox<>();
        chkIsHidden = new javax.swing.JCheckBox();
        lblScenarioStars = new javax.swing.JLabel();
        scrListPowerStarId = new javax.swing.JScrollPane();
        lblScenarioLayers = new javax.swing.JLabel();
        scrListLayers = new javax.swing.JScrollPane();
        fillerRight = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 32767));
        menu = new javax.swing.JMenuBar();
        mnuFile = new javax.swing.JMenu();
        mnuSave = new javax.swing.JMenuItem();
        mnuClose = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(galaxyName + " -- " + Whitehole.NAME);
        setIconImage(Whitehole.ICON);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jSplitPane1.setDividerLocation(300);

        pnlLeft.setLayout(new java.awt.GridBagLayout());

        lblScenarios.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblScenarios.setText("Scenarios");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlLeft.add(lblScenarios, gridBagConstraints);

        tlbScenarios.setFloatable(false);
        tlbScenarios.setRollover(true);

        btnAddScenario.setText("Add");
        btnAddScenario.setFocusable(false);
        btnAddScenario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddScenario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddScenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddScenarioActionPerformed(evt);
            }
        });
        tlbScenarios.add(btnAddScenario);
        tlbScenarios.add(sep1);

        btnRemoveScenario.setText("Remove");
        btnRemoveScenario.setFocusable(false);
        btnRemoveScenario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemoveScenario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveScenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveScenarioActionPerformed(evt);
            }
        });
        tlbScenarios.add(btnRemoveScenario);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlLeft.add(tlbScenarios, gridBagConstraints);

        listScenarios.setModel(new DefaultListModel());
        listScenarios.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listScenarios.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listScenariosValueChanged(evt);
            }
        });
        scrListScenarios.setViewportView(listScenarios);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlLeft.add(scrListScenarios, gridBagConstraints);

        lblZones.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblZones.setText("Zones");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlLeft.add(lblZones, gridBagConstraints);

        tlbZones.setFloatable(false);
        tlbZones.setRollover(true);

        btnAddZone.setText("Add");
        btnAddZone.setFocusable(false);
        btnAddZone.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnAddZone.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnAddZone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddZoneActionPerformed(evt);
            }
        });
        tlbZones.add(btnAddZone);
        tlbZones.add(sep2);

        btnRemoveZone.setText("Remove");
        btnRemoveZone.setFocusable(false);
        btnRemoveZone.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnRemoveZone.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnRemoveZone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveZoneActionPerformed(evt);
            }
        });
        tlbZones.add(btnRemoveZone);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlLeft.add(tlbZones, gridBagConstraints);

        listZones.setModel(new DefaultListModel());
        listZones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        listZones.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                listZonesValueChanged(evt);
            }
        });
        scrListZones.setViewportView(listZones);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlLeft.add(scrListZones, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        pnlLeft.add(fillerLeft, gridBagConstraints);

        jSplitPane1.setLeftComponent(pnlLeft);

        pnlRight.setLayout(new java.awt.GridBagLayout());

        lblScenarioGeneral.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblScenarioGeneral.setText("General settings");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(lblScenarioGeneral, gridBagConstraints);

        lblScenarioNo.setText("Scenario index");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(lblScenarioNo, gridBagConstraints);

        lblScenarioName.setText("Scenario name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(lblScenarioName, gridBagConstraints);

        lblAppearPowerStarObj.setText("Power Star spawner");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(lblAppearPowerStarObj, gridBagConstraints);

        lblComet.setText("Comet type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(lblComet, gridBagConstraints);

        spnScenarioNo.setModel(new javax.swing.SpinnerNumberModel());
        spnScenarioNo.setEnabled(false);
        spnScenarioNo.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                spnScenarioNoStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(spnScenarioNo, gridBagConstraints);

        txtScenarioName.setEnabled(false);
        txtScenarioName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtScenarioNameKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(txtScenarioName, gridBagConstraints);

        cmoAppearPowerStarObj.setModel(new DefaultComboBoxModel(SPAWNERS.values().toArray()));
        cmoAppearPowerStarObj.setEnabled(false);
        cmoAppearPowerStarObj.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmoAppearPowerStarObjActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(cmoAppearPowerStarObj, gridBagConstraints);

        cmoComet.setModel(new DefaultComboBoxModel(COMETS.values().toArray()));
        cmoComet.setEnabled(false);
        cmoComet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmoCometActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(cmoComet, gridBagConstraints);

        chkIsHidden.setText("Is hidden star?");
        chkIsHidden.setEnabled(false);
        chkIsHidden.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chkIsHiddenActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(chkIsHidden, gridBagConstraints);

        lblScenarioStars.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblScenarioStars.setText("Available Power Stars");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(lblScenarioStars, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(scrListPowerStarId, gridBagConstraints);

        lblScenarioLayers.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        lblScenarioLayers.setText("Loaded zone layers");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(lblScenarioLayers, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.6;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        pnlRight.add(scrListLayers, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        pnlRight.add(fillerRight, gridBagConstraints);

        jSplitPane1.setRightComponent(pnlRight);

        mnuFile.setMnemonic('F');
        mnuFile.setText("File");

        mnuSave.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        mnuSave.setMnemonic('S');
        mnuSave.setText("Save");
        mnuSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuSaveActionPerformed(evt);
            }
        });
        mnuFile.add(mnuSave);

        mnuClose.setMnemonic('C');
        mnuClose.setText("Close");
        mnuClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuCloseActionPerformed(evt);
            }
        });
        mnuFile.add(mnuClose);

        menu.add(mnuFile);

        setJMenuBar(menu);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 700, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSplitPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 518, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddScenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddScenarioActionPerformed
        GalaxyArchive.Scenario scenario = galaxy.new Scenario();
        galaxy.scenarios.add(scenario);
        modelScenarios.addElement(scenario);
    }//GEN-LAST:event_btnAddScenarioActionPerformed

    private void btnRemoveScenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveScenarioActionPerformed
        int index = listScenarios.getSelectedIndex();
        if (index < 0)
            return;
        
        modelScenarios.remove(index);
        galaxy.scenarios.remove(index);
    }//GEN-LAST:event_btnRemoveScenarioActionPerformed

    private void listScenariosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listScenariosValueChanged
        loadScenario();
    }//GEN-LAST:event_listScenariosValueChanged

    private void btnAddZoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddZoneActionPerformed
        String newzone = (String)JOptionPane.showInputDialog(this,
                "Enter the zone's name:",
                Whitehole.NAME, JOptionPane.PLAIN_MESSAGE
        );
        
        if (newzone == null || newzone.isEmpty())
            return;
        
        modelZones.addElement(newzone);
        galaxy.zones.add(newzone);
        
        for (GalaxyArchive.Scenario scenario : galaxy.scenarios)
            scenario.layers.put(newzone, 0);
    }//GEN-LAST:event_btnAddZoneActionPerformed

    private void btnRemoveZoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveZoneActionPerformed
        int index = listZones.getSelectedIndex();
        if (index < 0)
            return;
        
        String zone = galaxy.zones.get(index);
        modelZones.remove(index);
        galaxy.zones.remove(index);
        
        for (GalaxyArchive.Scenario scenario : galaxy.scenarios)
            scenario.layers.remove(zone);
    }//GEN-LAST:event_btnRemoveZoneActionPerformed

    private void listZonesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_listZonesValueChanged
        loadLayer();
    }//GEN-LAST:event_listZonesValueChanged

    private void spnScenarioNoStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_spnScenarioNoStateChanged
        selected.index = (int)spnScenarioNo.getValue();
    }//GEN-LAST:event_spnScenarioNoStateChanged

    private void txtScenarioNameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtScenarioNameKeyReleased
        selected.name = txtScenarioName.getText();
    }//GEN-LAST:event_txtScenarioNameKeyReleased

    private void cmoAppearPowerStarObjActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmoAppearPowerStarObjActionPerformed
        selected.appearPowerStarObj = Utils.valueToKey((String)cmoAppearPowerStarObj.getSelectedItem(), SPAWNERS);
    }//GEN-LAST:event_cmoAppearPowerStarObjActionPerformed

    private void cmoCometActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cmoCometActionPerformed
        selected.comet = Utils.valueToKey((String)cmoComet.getSelectedItem(), COMETS);
    }//GEN-LAST:event_cmoCometActionPerformed

    private void chkIsHiddenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chkIsHiddenActionPerformed
        selected.isHidden = chkIsHidden.isSelected();
    }//GEN-LAST:event_chkIsHiddenActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        close();
    }//GEN-LAST:event_formWindowClosing

    private void mnuSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuSaveActionPerformed
        save();
    }//GEN-LAST:event_mnuSaveActionPerformed

    private void mnuCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuCloseActionPerformed
        close();
    }//GEN-LAST:event_mnuCloseActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddScenario;
    private javax.swing.JButton btnAddZone;
    private javax.swing.JButton btnRemoveScenario;
    private javax.swing.JButton btnRemoveZone;
    private javax.swing.JCheckBox chkIsHidden;
    private javax.swing.JComboBox<String> cmoAppearPowerStarObj;
    private javax.swing.JComboBox<String> cmoComet;
    private javax.swing.Box.Filler fillerLeft;
    private javax.swing.Box.Filler fillerRight;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JLabel lblAppearPowerStarObj;
    private javax.swing.JLabel lblComet;
    private javax.swing.JLabel lblScenarioGeneral;
    private javax.swing.JLabel lblScenarioLayers;
    private javax.swing.JLabel lblScenarioName;
    private javax.swing.JLabel lblScenarioNo;
    private javax.swing.JLabel lblScenarioStars;
    private javax.swing.JLabel lblScenarios;
    private javax.swing.JLabel lblZones;
    private javax.swing.JList<String> listScenarios;
    private javax.swing.JList<String> listZones;
    private javax.swing.JMenuBar menu;
    private javax.swing.JMenuItem mnuClose;
    private javax.swing.JMenu mnuFile;
    private javax.swing.JMenuItem mnuSave;
    private javax.swing.JPanel pnlLeft;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JScrollPane scrListLayers;
    private javax.swing.JScrollPane scrListPowerStarId;
    private javax.swing.JScrollPane scrListScenarios;
    private javax.swing.JScrollPane scrListZones;
    private javax.swing.JToolBar.Separator sep1;
    private javax.swing.JToolBar.Separator sep2;
    private javax.swing.JSpinner spnScenarioNo;
    private javax.swing.JToolBar tlbScenarios;
    private javax.swing.JToolBar tlbZones;
    private javax.swing.JTextField txtScenarioName;
    // End of variables declaration//GEN-END:variables
}