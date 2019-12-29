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

package whitehole.smg;

import whitehole.io.ExternalFilesystem;
import whitehole.io.RarcFilesystem;
import java.util.*;
import java.io.*;

public class GalaxyArchive {
    public class Scenario {
        public int index, powerStarId, luigiModeTimer;
        public String name, appearPowerStarObj, comet;
        public boolean isHidden;
        public HashMap<String, Integer> layers;
        
        public Scenario() {
            index = 0;
            name = "undefined";
            powerStarId = 0;
            appearPowerStarObj = "";
            comet = "";
            luigiModeTimer = 0;
            isHidden = false;
            layers = new LinkedHashMap();
            
            for (String zone : zones)
                layers.put(zone, 0);
        }
        
        public Scenario(Bcsv.Entry entry) {
            index = entry.getInt("ScenarioNo");
            name = entry.getString("ScenarioName");
            powerStarId = entry.getInt("PowerStarId");
            appearPowerStarObj = entry.getString("AppearPowerStarObj");
            comet = entry.getString("Comet");
            luigiModeTimer = entry.getInt("LuigiModeTimer");
            isHidden = entry.getInt("IsHidden") != 0;
            layers = new LinkedHashMap();
            
            for (String zone : zones) {
                if (entry.containsKey(zone))
                    layers.put(zone, (int)entry.get(zone));
                else
                    layers.put(zone, 0);
            }
        }
        
        @Override
        public String toString() {
            return String.format("%d: %s", index, name);
        }
        
        public Bcsv.Entry toBcsv() {
            Bcsv.Entry entry = new Bcsv.Entry();
            entry.put("ScenarioNo", index);
            entry.put("ScenarioName", name);
            entry.put("PowerStarId", powerStarId);
            entry.put("AppearPowerStarObj", appearPowerStarObj);
            entry.put("Comet", comet);
            entry.put("LuigiModeTimer", luigiModeTimer);
            entry.put("IsHidden", isHidden ? 1 : 0);
            for (String zone : layers.keySet())
                entry.put(zone, layers.get(zone));
            return entry;
        }
    }
    
    public GameArchive game;
    public ExternalFilesystem filesystem;
    public String name;
    public List<String> zones;
    public List<Scenario> scenarios;
    private RarcFilesystem archive;
    private Bcsv bcsvZones, bcsvScenarios;
    
    public GalaxyArchive(GameArchive gamearc, String galname) throws IOException {
        game = gamearc;
        filesystem = gamearc.filesystem;
        name = galname;
        zones = new ArrayList();
        scenarios = new ArrayList();
        
        load();
    }
    
    private void load() throws IOException {
        archive = new RarcFilesystem(filesystem.openFile("StageData/" + name + '/' + name + "Scenario.arc"));
        
        bcsvZones = new Bcsv(archive.openFile(String.format("/%1$sScenario/ZoneList.bcsv", name)));
        for (Bcsv.Entry entry : bcsvZones.entries)
            zones.add(entry.getString("ZoneName"));
        
        bcsvScenarios = new Bcsv(archive.openFile(String.format("/%1$sScenario/ScenarioData.bcsv", name)));
        for (Bcsv.Entry entry : bcsvScenarios.entries)
            scenarios.add(new Scenario(entry));
    }
    
    public void save() throws IOException {
        bcsvScenarios.entries.clear();
        bcsvScenarios.fields.clear();
        bcsvZones.entries.clear();
        bcsvZones.fields.clear();
        
        bcsvScenarios.addField("ScenarioNo", Bcsv.TYPE_INT32, -1, 0, 0);
        bcsvScenarios.addField("ScenarioName", Bcsv.TYPE_STRING, -1, 0, "");
        bcsvScenarios.addField("PowerStarId", Bcsv.TYPE_INT32, -1, 0, 0);
        bcsvScenarios.addField("AppearPowerStarObj", Bcsv.TYPE_STRING, -1, 0, "");
        bcsvScenarios.addField("Comet", Bcsv.TYPE_STRING, -1, 0, "");
        bcsvScenarios.addField("LuigiModeTimer", Bcsv.TYPE_INT32, -1, 0, 0);
        bcsvScenarios.addField("IsHidden", Bcsv.TYPE_INT32, -1, 0, 0);
        bcsvZones.addField("ZoneName", Bcsv.TYPE_STRING, 0, 0, "");
        
        for (String zone : zones) {
            Bcsv.Entry entry = new Bcsv.Entry();
            entry.put("ZoneName", zone);
            bcsvZones.entries.add(entry);
            
            bcsvScenarios.addField(zone, Bcsv.TYPE_INT32, -1, 0, 0);
        }
        
        for (Scenario scenario : scenarios)
            bcsvScenarios.entries.add(scenario.toBcsv());
        
        bcsvZones.save();
        bcsvScenarios.save();
        archive.save();
    }
    
    public void close() throws IOException {
        bcsvZones.close();
        bcsvScenarios.close();
        archive.close();
    }
    
    public ZoneArchive openZone(String name) throws IOException {
        if (!zones.contains(name))
            return null;
        return new ZoneArchive(game, name);
    }
}