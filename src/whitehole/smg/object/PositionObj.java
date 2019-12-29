/*
    © 2012 - 2017 - Whitehole Team

    Whitehole is free software: you can redistribute it and/or modify it under
    the terms of the GNU General Public License as published by the Free
    Software Foundation, either version 3 of the License, or (at your option)
    any later version.

    Whitehole is distributed in the hope that it will be useful, but WITHOUT ANY 
    WARRANTY; See the GNU General Public License for more details.

    You should have received a copy of the GNU General Public License along 
    with Whitehole. If not, see http://www.gnu.org/licenses/.
*/

package whitehole.smg.object;

import whitehole.swing.PropertyGrid;
import whitehole.smg.Bcsv;
import whitehole.smg.ZoneArchive;
import whitehole.vectors.Vector3;
import java.util.ArrayList;
import java.util.List;

public class PositionObj extends TObject {
    private static final List<String> CHOICES_POSITIONNAME = new ArrayList() {{
        add("いいえ選択移動位置");
        add("ウォーターバズーカマリオ位置");
        add("エピローグマリオ");
        add("ガード出現ポイント1");
        add("キノピオメッセンジャー");
        add("クッパ階段戦の砲弾出現");
        add("クッパＪｒシップ戦マリオ位置");
        add("グランドスター出現");
        add("グランドスター帰還リザルト");
        add("コア中心");
        add("コーチチュートリアル位置");
        add("コーチレース終了後位置");
        add("コーチ２回目位置");
        add("ゴーストデモゴースト位置");
        add("ゴーストデモマリオ位置");
        add("スタートカメラマリオ座標");
        add("スタート位置（サーフィン）");
        add("ダウンデモ後（マリオ）");
        add("ダウンデモ");
        add("デモ中心");
        add("ドドリュウ再セット");
        add("ドドリュウ岩");
        add("ドーム中心");
        add("ハニークイーンとの会話位置");
        add("バトラーデモ終了");
        add("バトラーマップレクチャー");
        add("バトルシップ・タイムアタック前位置");
        add("バトルシップ・タイムアタック後位置");
        add("パワーアップデモ（クッパ）");
        add("パワーアップデモ（マリオ）");
        add("パワーアップデモＬｖ２（クッパ）");
        add("パワーアップデモＬｖ２（マリオ）");
        add("パワーアップデモＬｖ３（クッパ）");
        add("パワーアップデモＬｖ３（マリオ）");
        add("ピーチ登場デモ後（マリオ）");
        add("ペンギン移動後");
        add("ポルタデモプレイヤー位置");
        add("ポルタ開始デモプレイヤー位置");
        add("マリオイベント会話２");
        add("マリオイベント会話３");
        add("マリオイベント会話");
        add("マリオボスべー対決");
        add("マリオ位置");
        add("マリオ再セット位置");
        add("マリオ再セット位置1");
        add("マリオ再セット位置2");
        add("マリオ再セット");
        add("マリオ再セット1");
        add("マリオ再セット2");
        add("マリオ再セット3");
        add("マリオ再セット4");
        add("マリオ移動後");
        add("メラキンデモ後セット位置");
        add("メラキンマリオ再セット位置");
        add("リスタート");
        add("レース終了後位置テレサ");
        add("レース終了後位置");
        add("レース開始時マリオ位置");
        add("ロゼッタ状況説明マリオ");
        add("ワープ位置（サーフィン）");
        add("合体ブロック故郷点");
        add("図書室中心");
        add("子連れカメムシデモ後ポイント");
        add("惑星中心");
        add("惑星Ｌｖ２");
        add("惑星Ｌｖ３");
        add("戦闘開始（クッパ）");
        add("戦闘開始（マリオ）");
        add("朗読デモ終了");
        add("未入力");
        add("爆破デモ後マリオ");
        add("落下点1");
        add("落下点2");
        add("落下点3");
        add("落下点4");
        add("落下点5");
        add("負け時マリオ位置");
        add("開始マリオ");
        add("階段の戦い０（クッパ）");
        add("階段の戦い１（クッパ）");
        add("階段の戦い２（クッパ）");
        add("隠れ位置");
        add("Ｌｖ１終了（クッパ）");
        add("Ｌｖ１終了（マリオ）");
        add("Ｌｖ１開始（クッパ）");
        add("Ｌｖ１開始（マリオ）");
        add("Ｌｖ２終了（クッパ）");
        add("Ｌｖ２終了（マリオ）");
        add("Ｌｖ２開始（クッパ）");
        add("Ｌｖ２開始（マリオ）");
        add("Ｌｖ３内側（クッパ）");
        add("Ｌｖ３内側（マリオ）");
        add("Ｌｖ３外側（クッパ）");
        add("Ｌｖ３外側（マリオ）");
        add("MarioDemoPos");
        add("MarioDemoPos2");
        add("MarioDemoPos3");
        add("MarioDemoPos4");
        add("TicoDemoPos1");
        add("TicoDemoPos2");
        add("TicoDemoPos3");
    }};
    
    public PositionObj(ZoneArchive zone, String layer, Bcsv.Entry entry) {
        this.zone = zone;
        this.layer = layer;
        
        renderer = null;
        uniqueID = -1;
        
        data = entry;
        name = (String)data.get("name");
        
        position = new Vector3((float)data.get("pos_x"), (float)data.get("pos_y"), (float)data.get("pos_z"));
        rotation = new Vector3((float)data.get("dir_x"), (float)data.get("dir_y"), (float)data.get("dir_z"));
        scale = new Vector3(1,1,1);
        
        loadDBInfo();
    }
    
    public PositionObj(ZoneArchive zone, String layer, Vector3 pos) {
        this.zone = zone;
        this.layer = layer;
        
        renderer = null;
        uniqueID = -1;
        
        name = "GeneralPos";
        position = pos;
        rotation = new Vector3(0f, 0f, 0f);
        scale = new Vector3(1f, 1f, 1f);
        data = new Bcsv.Entry();
        
        data.put("name", name);
        data.put("PosName", "undefined");
        data.put("pos_x", position.x);
        data.put("pos_y", position.y);
        data.put("pos_z", position.z);
        data.put("dir_x", rotation.x);
        data.put("dir_y", rotation.y);
        data.put("dir_z", rotation.z);
        data.put("Obj_ID", (short)-1);
        data.put("ChildObjId", (short)-1);
        
        loadDBInfo();
    }
    
    @Override
    public void save() {
        data.put("name", name);
        data.put("pos_x", position.x);
        data.put("pos_y", position.y);
        data.put("pos_z", position.z);
        data.put("dir_x", rotation.x);
        data.put("dir_y", rotation.y);
        data.put("dir_z", rotation.z);
    }

    @Override
    public void getProperties(PropertyGrid panel) {
        panel.addCategory("obj_rendering", "Rendering");
        panel.addField("pos_x", "X position", "float", null, position.x, "Default");
        panel.addField("pos_y", "Y position", "float", null, position.y, "Default");
        panel.addField("pos_z", "Z position", "float", null, position.z, "Default");
        panel.addField("dir_x", "X rotation", "float", null, rotation.x, "Default");
        panel.addField("dir_y", "Y rotation", "float", null, rotation.y, "Default");
        panel.addField("dir_z", "Z rotation", "float", null, rotation.z, "Default");
        
        panel.addCategory("obj_position", "Settings");
        panel.addField("PosName", "Position name", "textlist", CHOICES_POSITIONNAME, data.get("PosName"), "Default"); 
        panel.addField("Obj_ID", "Linked Object ID", "int", null, data.get("Obj_ID"), "Default");
        panel.addField("ChildObjId", "Linked Child ID", "int", null, data.get("ChildObjId"), "Default"); 
    }
    
    public ObjFile getFileInfo() {
        return ObjFile.GeneralPos;
    }
    
    @Override
    public String toString() {
        return String.format("%s [%s]", data.get("PosName"), getLayerName());
    }
}