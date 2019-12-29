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

package whitehole.rendering;

import whitehole.rendering.object.UFOKinokoRenderer;
import whitehole.rendering.object.ItemBubbleRenderer;
import whitehole.rendering.object.OtaKingRenderer;
import whitehole.rendering.object.KinopioRenderer;
import whitehole.rendering.object.AstroSkyRenderer;
import whitehole.rendering.object.BlackHoleRenderer;
import whitehole.rendering.object.PoleRenderer;
import whitehole.rendering.object.AstroRenderer;
import whitehole.smg.object.TObject;
import whitehole.smg.object.ObjFile;
import whitehole.rendering.object.AreaRenderer;
import whitehole.vectors.Color4;
import whitehole.vectors.Vector3;
import whitehole.Settings;
import whitehole.rendering.object.AreaRenderer.Shape;
import java.io.IOException;

public class Substitutor {
    public static String substituteModelName(TObject obj, String model) {
        switch (obj.name) {
            case "ArrowSwitchMulti": return "ArrowSwitch";
            case "AstroDomeBlueStar": return "GCaptureTarget";
            case "BenefitItemInvincible": return "PowerUpInvincible";
            case "BenefitItemLifeUp": return "KinokoLifeUp";
            case "BenefitItemOneUp": return "KinokoOneUp";
            case "BigBubbleGenerator":
            case "BigObstructBubbleGenerator": return "AirBubbleGenerator";
            case "Bomb": return "BombHei";
            case "BombLauncher": return "BombHeiLauncher";
            case "BossKameck2": return "BossKameck";
            case "BreakableCageRotate": return "BreakableCage";
            case "ButlerExplain":
            case "ButlerMap": return "Butler";
            case "ChildIceMeramera": return "IceMeramera";
            case "ChildKameck": return "Kameck";
            case "ChildKuribo": return "Kuribo";
            case "ChildMeramera": return "Meramera";
            case "ChildSkeletalFishBaby": return "SnakeFish";
            case "CoinReplica": return "Coin";
            case "Creeper": return "CreeperFlower";
            case "CutBushGroup": return "CutBush";
            case "DemoKoopaJrShip": return "KoopaJrShip";
            case "DharmaSambo": return "DharmaSamboParts";
            case "FireBallBeamKameck": return "Kameck";
            case "FirePressureRadiate": return "FirePressure";
            case "FishGroupA": return "FishA";
            case "FishGroupB": return "FishB";
            case "FishGroupC": return "FishC";
            case "FishGroupD": return "FishD";
            case "FishGroupE": return "FishE";
            case "FishGroupF": return "FishF";
            case "FlowerBlueGroup": return "FlowerBlue";
            case "FlowerGroup": return "Flower";
            case "GhostPlayer": return "GhostMario";
            case "GoldenTurtle": return "KouraShine";
            case "Hanachan": return "HanachanHead";
            case "JetTurtle": return "Koura";
            case "Karikari": return "Karipon";
            case "KirairaRail": return "Kiraira";
            case "KoopaBattleMapCoinPlate": return "KoopaPlateCoin";
            case "KoopaBattleMapPlate": return "KoopaPlate";
            case "KoopaBattleMapStairturnAppear": return "KoopaBattleMapStairTurn";
            case "LavaProminenceWithoutShadow": return "LavaProminence";
            case "MagicBell": return "Bell";
            case "MarioActor": return "Mario";
            case "MeteorCannon":
            case "MeteorStrikeEnvironment": return "MeteorStrike";
            case "MiniKoopaBattleVs1Galaxy":
            case "MiniKoopaBattleVs2Galaxy":
            case "MiniKoopaBattleVs3Galaxy": return "MiniKoopaGalaxy";
            case "MorphItemNeoBee": return "PowerUpBee";
            case "MorphItemNeoFire": return "PowerUpFire";
            case "MorphItemNeoFoo": return "PowerUpFoo";
            case "MorphItemNeoHopper": return "PowerUpHopper";
            case "MorphItemNeoIce": return "PowerUpIce";
            case "MorphItemNeoTeresa": return "PowerUpTeresa";
            case "NoteFairy": return "Note";
            case "OnimasuPivot": return "Onimasu";
            case "PenguinSkater":
            case "PenguinStudent": return "Penguin";
            case "Plant": return "PlantSeed";
            case "Rabbit": return "MoonRabbit";
            case "RockCreator": return "Rock";
            case "RunawayRabbitCollect": return "TrickRabbit";
            case "SeaGullGroup": return "SeaGull";
            case "ShellfishBlueChip":
            case "ShellfishCoin":
            case "ShellfishKinokoOneUp":
            case "ShellfishYellowChip": return "Shellfish";
            case "SkeletalFishBaby": return "SnakeFish";
            case "SpiderAttachPoint": return "SpiderThreadAttachPoint";
            case "SpiderCoin": return "Coin";
            case "ItemBlockSwitch":
            case "SplashCoinBlock":
            case "SplashPieceBlock":
            case "TimerCoinBlock":
            case "TimerPieceBlock": return "CoinBlock";
            case "SuperSpinDriverGreen":
            case "SuperSpinDriverPink": return "SuperSpinDriver";
            case "SurpBeltConveyerExGalaxy":
            case "SurpCocoonExGalaxy":
            case "SurpCubeBubbleExLv2Galaxy":
            case "SurpFishTunnelGalaxy":
            case "SurpPeachCastleFinalGalaxy":
            case "SurpSnowCapsuleGalaxy":
            case "SurpSurfingLv2Galaxy":
            case "SurpTamakoroExLv2Galaxy":
            case "SurpTearDropGalaxy":
            case "SurpTeresaMario2DGalaxy":
            case "SurpTransformationExGalaxy": return "MiniSurprisedGalaxy";
            case "TalkSyati": return "Syati";
            case "TamakoroWithTutorial": return "Tamakoro";
            case "Teresa":
            case "TeresaChief": return "TeresaWater";
            case "TicoAstro":
            case "TicoDomeLecture": return "Tico";
            case "TicoGalaxy": return "TicoFat";
            case "TicoRail":
            case "TicoReading":
            case "TicoStarRing": return "Tico";
            case "TreasureBoxBlueChip":
            case "TreasureBoxCoin": return "TreasureBox";
            case "TreasureBoxCrackedAirBubble":
            case "TreasureBoxCrackedBlueChip":
            case "TreasureBoxCrackedCoin":
            case "TreasureBoxCrackedEmpty":
            case "TreasureBoxCrackedKinokoLifeUp":
            case "TreasureBoxCrackedKinokoOneUp":
            case "TreasureBoxCrackedPowerStar":
            case "TreasureBoxCrackedYellowChip": return "TreasureBoxCracked";
            case "TreasureBoxEmpty": return "TreasureBox";
            case "TreasureBoxGoldEmpty": return "TreasureBoxGold";
            case "TreasureBoxKinokoLifeUp":
            case "TreasureBoxKinokoOneUp":
            case "TreasureBoxYellowChip": return "TreasureBox";
            case "TrickRabbitFreeRun":
            case "TrickRabbitFreeRunCollect":
            case "TrickRabbitGhost": return "TrickRabbit";
            case "TripodBossCoin": return "Coin";
            case "TripodBossBottomKillerCannon":
            case "TripodBossKillerGenerator": return "TripodBossKillerCannon";
            case "TripodBossKinokoOneUp": return "KinokoOneUp";
            case "TripodBossUnderKillerCannon":
            case "TripodBossUpperKillerCannon": return "TripodBossKillerCannon";
            case "TurtleBeamKameck": return "Kameck";
        }
        
        return model;
    }
    
    public static String substituteObjectKey(TObject obj, String objectkey) {
        switch (obj.name) {
            case "Pole":
            case "PoleSquare": objectkey += String.format("_%1$3f", obj.scale.y / obj.scale.x); break;
            case "BlackHole":
            case "BlackHoleCube": objectkey += String.format("_%1$d_%2$f_%3$f_%4$f", obj.data.getOrDefault("Obj_arg0", 1000), obj.scale.x, obj.scale.y, obj.scale.z); break;
            case "Kinopio": 
            case "KinopioAstro": objectkey += String.format("_%1$d", obj.data.get("Obj_arg1")); break; 
            case "UFOKinoko": objectkey += String.format("_%1$d", obj.data.get("Obj_arg0")); break;
            case "OtaKing": objectkey += String.format("_%1$d", obj.data.get("Obj_arg1")); break;
            case "Coin":
            case "PurpleCoin": objectkey += String.format("_%1$d", obj.data.get("Obj_arg7")); break;
            case "AstroDome":
            case "AstroDomeEntrance":
            case "AstroDomeSky":
            case "AstroStarPlate": objectkey += String.format("_%1$d", obj.data.get("Obj_arg0")); break;
            case "BreakableCage": objectkey += String.format("_%1$d", obj.data.get("Obj_arg7")); break;
            case "AssemblyBlock":
            case "ClipFieldMapParts":
            case "FlexibleSphere":
            case "MercatorFixParts":
            case "MercatorRailMoveParts":
            case "MercatorRotateParts":
            case "TripodBossFixParts":
            case "TripodBossRailMoveParts":
            case "TripodBossRotateParts":
            case "SimpleNormalMapObj":
            case "SunshadeMapParts": objectkey += String.format("_%d", obj.data.get("ShapeModelNo")); break;
        }
        
        return objectkey;
    }
    
    public static GLRenderer substituteRenderer(TObject obj, GLRenderer.RenderInfo info) {
        try {
            if (obj.getFileInfo() == ObjFile.Obj || obj.getFileInfo() == ObjFile.ChildObj || obj.getFileInfo() == ObjFile.MapParts) {
                switch (obj.name) {
                    case "Pole":
                    case "PoleNoModel": return new PoleRenderer(info, obj.scale, "Pole");
                    case "PoleSquare":
                    case "PoleSquareNoModel": return new PoleRenderer(info, obj.scale, "PoleSquare");
                    case "Flag": return new BtiRenderer(info, "Flag", new Vector3(0f,150f,0f), new Vector3(0f,-150f,600f), true);
                    case "FlagRaceA": return new BtiRenderer(info, "FlagRaceA", new Vector3(0f,75f,0f), new Vector3(0f,-75f,300f), true);
                    case "FlagSurfing": return new BtiRenderer(info, "FlagSurfing", new Vector3(0f,150f,0f), new Vector3(0f,-150f,600f), true);
                    case "FlagTamakoro": return new BtiRenderer(info, "FlagTamakoro", new Vector3(0f,150f,0f), new Vector3(0f,-150f,600f), true);
                    case "FlagPeachCastleA": return new BtiRenderer(info, "FlagPeachCastleA", new Vector3(0f,150f,0f), new  Vector3(0f,-150f,600f), true);
                    case "FlagPeachCastleB": return new BtiRenderer(info, "FlagPeachCastleB", new Vector3(0f,150f,0f), new  Vector3(0f,-150f,600f), true);
                    case "FlagPeachCastleC": return new BtiRenderer(info, "FlagPeachCastleC", new Vector3(0f,150f,0f), new  Vector3(0f,-150f,600f), true);
                    case "FlagKoopaA": return new BtiRenderer(info, "FlagKoopaA", new Vector3(0f,150f,0f), new Vector3(0f,-150f,600f), true);
                    case "FlagKoopaB": return new BtiRenderer(info, "FlagKoopaB", new Vector3(0f,75f,0f), new Vector3(0f,-75f,600f), true);
                    case "FlagKoopaCastle": return new BtiRenderer(info, "FlagKoopaCastle", new Vector3(0f,150f,0f), new Vector3(0f,-150f,600f), true);
                    case "AstroStarPlate":
                    case "AstroDome":
                    case "AstroDomeEntrance": return new AstroRenderer(info, obj.name, (int)obj.data.get("Obj_arg0"));
                    case "AstroDomeSky": return new AstroSkyRenderer(info, (int)obj.data.get("Obj_arg0"));
                    case "BlackHole": return new BlackHoleRenderer(info, (int) obj.data.get("Obj_arg0"), obj.scale, Shape.SPHERE);
                    case "BlackHoleCube": return new BlackHoleRenderer(info, (int) obj.data.get("Obj_arg0"), obj.scale, Shape.CENTEREDCUBE);
                    case "Coin":
                    case "PurpleCoin": return new ItemBubbleRenderer(info, obj.name, (int)obj.data.get("Obj_arg7"));
                    case "OtaKing": return new OtaKingRenderer(info, obj.name, (int)obj.data.get("Obj_arg1"));
                    case "Kinopio": 
                    case "KinopioAstro": return new KinopioRenderer(info, (int)obj.data.get("Obj_arg1"));
                    case "UFOKinoko": return new UFOKinokoRenderer(info, (int)obj.data.get("Obj_arg0"));
                    case "EarthenPipe":
                    case "EarthenPipeInWater": return new BmdRendererSingle(info, "EarthenPipe", new Vector3(0f,100f,0f), new Vector3());
                    
                    case "StrayTico": return new MultiRenderer(
                            new BmdRendererSingle(info, "StrayTico"),
                            new BmdRendererSingle(info, "ItemBubble")
                    );
                    case "HammerHeadPackun": return new MultiRenderer(
                            new BmdRendererSingle(info, "PackunFlower"),
                            new BmdRendererSingle(info, "PackunLeaf")
                    );
                    case "CocoSambo": return new MultiRenderer(
                            new BmdRendererSingle(info, "CocoSamboBody"),
                            new BmdRendererSingle(info, "CocoSamboHead", new Vector3(0f,325f,0f), new Vector3())
                    );
                    case "Kiraira": return new MultiRenderer(
                            new BmdRendererSingle(info, "Kiraira", new Vector3(0f,50f,0f), new Vector3()),
                            new BmdRendererSingle(info, "KirairaChain", new Vector3(0f,-160f,0f), new Vector3()),
                            new BmdRendererSingle(info, "KirairaFixPointBottom", new Vector3(0f,-15f,0f), new Vector3())
                    );
                    case "Torpedo": return new MultiRenderer(
                            new BmdRendererSingle(info, "Torpedo"),
                            new BmdRendererSingle(info, "TorpedoPropeller")
                    );
                    case "BegomanSpike": return new MultiRenderer(
                            new BmdRendererSingle(info, "BegomanSpike"),
                            new BmdRendererSingle(info, "BegomanSpikeHead")
                    );
                    case "BegomanSpring":
                    case "BegomanSpringHide": return new MultiRenderer(
                            new BmdRendererSingle(info, "BegomanSpring"),
                            new BmdRendererSingle(info, "BegomanSpringHead")
                    );
                    case "JumpBeamer": return new MultiRenderer(
                            new BmdRendererSingle(info, "JumpBeamerBody"),
                            new BmdRendererSingle(info, "JumpBeamerHead")
                    );
                    case "JumpGuarder": return new MultiRenderer(
                            new BmdRendererSingle(info, "JumpGuarder"),
                            new BmdRendererSingle(info, "JumpGuarderHead")
                    );
                    case "WaterBazooka": return new MultiRenderer(
                            new BmdRendererSingle(info, "WaterBazooka"),
                            new BmdRendererSingle(info, "WaterBazookaCapsule", new Vector3(0f, 475f, 0f), new Vector3()),
                            new BmdRendererSingle(info, "MogucchiShooter", new Vector3(0f,-160f,0f), new Vector3())
                    );
                    case "ElectricBazooka": return new MultiRenderer(
                            new BmdRendererSingle(info, "ElectricBazooka"),
                            new BmdRendererSingle(info, "WaterBazookaCapsule", new Vector3(0f, 475f, 0f), new Vector3()),
                            new BmdRendererSingle(info, "MogucchiShooter", new Vector3(0f,-160f,0f), new Vector3())
                    );
                    case "DinoPackun": return new MultiRenderer(
                            new BmdRendererSingle(info, "DinoPackun"),
                            new BmdRendererSingle(info, "DinoPackunTailBall", new Vector3(0f,150f,-750f), new Vector3(0f,90f,0f))
                    );
                    case "DinoPackunVs2": return new MultiRenderer(
                            new BmdRendererSingle(info, "DinoPackun2"),
                            new BmdRendererSingle(info, "DinoPackunTailBall", new Vector3(0f,150f,-750f), new Vector3(0f,90f,0f))
                    );
                    case "BossBegoman": return new MultiRenderer(
                            new BmdRendererSingle(info, "BossBegoman"),
                            new BmdRendererSingle(info, "BossBegomanHead")
                    );
                    case "TombSpider": return new MultiRenderer(
                            new BmdRendererSingle(info, "TombSpider"),
                            new BmdRendererSingle(info, "TombSpiderPlanet")
                    );
                    case "SkeletalFishBoss": return new MultiRenderer(
                            new BmdRendererSingle(info, "SkeletalFishBoss"),
                            new BmdRendererSingle(info, "SkeletalFishBossHeadA")
                    );
                }
            }
            
            if (Settings.showAreas && (obj.getFileInfo() == ObjFile.AreaObj || obj.getFileInfo() == ObjFile.CameraCube)) {
                switch(obj.name) {
                    case "BindEndCube":
                    case "BloomCube":
                    case "CollisionArea":
                    case "DepthOfFieldCube":
                    case "OnimasuCube":
                    case "ScreenBlurCube":
                    case "SimpleBloomCube": return new AreaRenderer(new Color4(0.3f, 1f, 1f), Shape.CENTEREDCUBE);
                    case "AstroChangeStageCube":
                    case "AudioEffectCube":
                    case "BeeWallShortDistAreaCube":
                    case "BigBubbleGoalAreaBox":
                    case "BigBubbleSwitchBox":
                    case "BlueStarGuidanceCube":
                    case "ChangeBgmCube":
                    case "DarkMatterCube":
                    case "DeathCube":
                    case "ExtraWallCheckArea":
                    case "FallsCube":
                    case "ForbidJumpCube":
                    case "ForbidTriangleJumpCube":
                    case "ForbidWaterSearchCube":
                    case "HazeCube":
                    case "HeavySteeringCube":
                    case "LensFlareArea":
                    case "LightCtrlCube":
                    case "MercatorCube":
                    case "MessageAreaCube":
                    case "MirrorAreaCube":
                    case "NonSleepCube":
                    case "PipeModeCube":
                    case "PlaneCircularModeCube":
                    case "PlaneCollisionCube":
                    case "PlaneModeCube":
                    case "PlayerSeCube":
                    case "PullBackCube":
                    case "QuakeEffectAreaCube":
                    case "RasterScrollCube":
                    case "RestartCube":
                    case "SmokeEffectColorAreaCube":
                    case "SoundEmitterCube":
                    case "SpinGuidanceCube":
                    case "SunLightAreaBox":
                    case "SwitchCube":
                    case "TamakoroJumpGuidanceCube":
                    case "TamakoroMoveGuidanceCube":
                    case "TicoSeedGuidanceCube":
                    case "ViewGroupCtrlCube":
                    case "WarpCube":
                    case "WaterCube": return new AreaRenderer(new Color4(0.3f, 1f, 1f), Shape.CUBE);
                    case "AreaMoveSphere":
                    case "AudioEffectSphere":
                    case "BgmProhibitArea":
                    case "BigBubbleGoalAreaSphere":
                    case "BigBubbleSwitchSphere":
                    case "BloomSphere":
                    case "CelestrialSphere":
                    case "DeathSphere":
                    case "DepthOfFieldSphere":
                    case "PlayerSeSphere":
                    case "SimpleBloomSphere":
                    case "SoundEmitterSphere":
                    case "ScreenBlurSphere":
                    case "SwitchSphere":
                    case "TripodBossStepStartArea": return new AreaRenderer(new Color4(0.3f, 1f, 1f), Shape.SPHERE);
                    case "AstroOverlookAreaCylinder":
                    case "AudioEffectCylinder":
                    case "BigBubbleGoalAreaCylinder":
                    case "BigBubbleSwitchCylinder":
                    case "BloomCylinder":
                    case "DarkMatterCylinder":
                    case "DashChargeCylinder":
                    case "DeathCylinder":
                    case "DepthOfFieldCylinder":
                    case "DodoryuClosedCylinder":
                    case "EffectCylinder":
                    case "ExtraWallCheckCylinder":
                    case "GlaringLightAreaCylinder":
                    case "LightCtrlCylinder":
                    case "MessageAreaCylinder":
                    case "PlayerSeCylinder":
                    case "PullBackCylinder":
                    case "ScreenBlurCylinder":
                    case "SimpleBloomCylinder":
                    case "SwitchCylinder":
                    case "TowerModeCylinder":
                    case "WaterCylinder": return new AreaRenderer(new Color4(0.3f, 1f, 1f), Shape.CYLINDER);
                    case "BigBubbleCameraAreaBox":
                    case "CubeCameraBox": return new AreaRenderer(new Color4(0.8f, 0f, 0f), Shape.CUBE);
                    case "BigBubbleCameraAreaSphere":
                    case "CameraRepulsiveSphere":
                    case "CubeCameraSphere": return new AreaRenderer(new Color4(0.8f, 0f, 0f), Shape.SPHERE);
                    case "BigBubbleCameraAreaCylinder":
                    case "CameraRepulsiveCylinder":
                    case "CubeCameraCylinder": return new AreaRenderer(new Color4(0.8f, 0f, 0f), Shape.CYLINDER);
                    case "CubeCameraBowl": return new AreaRenderer(new Color4(0.8f, 0f, 0f), Shape.BOWL);
                }
            }
            
            switch(obj.getFileInfo()) {
                case AreaObj: return new ColorCubeRenderer(100f, new Color4(1f, 0.5f, 0.5f), new Color4(0.3f, 1f, 1f), true);
                case CameraCube: return new ColorCubeRenderer(100f, new Color4(0.3f, 0f, 1f), new Color4(0.8f, 0f, 0f), true);
                case PlanetObj: return new ColorCubeRenderer(100f, new Color4(1f, 1f, 1f), new Color4(0f,0.8f,0f), true);
                case DemoObj: return new ColorCubeRenderer(100f, new Color4(1f, 0.5f, 0.5f), new Color4(1.0f, 1.0f, 0.3f), true);
                case GeneralPos: return new ColorCubeRenderer(100f, new Color4(1f, 1f, 1f), new Color4(1f,0.5f,0f), true);
                case Sound: return new ColorCubeRenderer(100f, new Color4(1f, 1f, 1f), new Color4(1f, 0.5f, 1f), true);
                case DebugMove: return new ColorCubeRenderer(100f, new Color4(1f, 1f, 1f), new Color4(0.8f, 0.5f, 0.1f), true);
            }
        }
        catch (IOException ex) {}
        
        return null;
    }
}