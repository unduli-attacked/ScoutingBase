package base.threads;

import base.Main;
import base.models.BasePit;
import base.models.SecondPit;
import base.lib.ColumnMappings;
import base.lib.Enums;
import base.lib.Enums.*;
import base.lib.Functions;
import base.lib.SheetsFunctions;
import static base.lib.ColumnMappings.MainPit.*;

<<<<<<< Updated upstream
import java.time.LocalTime;
=======
import java.time.LocalDateTime;
>>>>>>> Stashed changes
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PitCollectionThread extends Thread {
    List<Object> headers;
    HashMap<String, Integer> valuesToCols; // The alphabet starts at 0
    int lastSavedRow;
    int secondLastSavedRow;
    
    @Override
    public void start(){
        try {
            this.headers = SheetsFunctions.getHeaders(Main.currentSession.spreadsheetID, Main.currentSession.mainPitTab, Main.currentSession.finalMainPitCol);
        }catch(Exception e){
            System.out.println(e);
        }
        lastSavedRow = 1;
    
        try {
            this.headers = SheetsFunctions.getHeaders(Main.currentSession.spreadsheetID, Main.currentSession.secondPitTab, Main.currentSession.finalSecondPitCol);
        }catch(Exception e){
            System.out.println(e);
        }
        secondLastSavedRow = 1;
    }
    
    @Override
    public void run(){
        List<Object> mainTemp = null;
        try{
            mainTemp = SheetsFunctions.getData(Main.currentSession.spreadsheetID, Main.currentSession.mainPitTab, lastSavedRow+1, Main.currentSession.finalMainPitCol);
        }catch (Exception e){
        
        }
        if(mainTemp!=null){
            if(Functions.findPit((int)mainTemp.get(TEAM_NUM.val))==null){
<<<<<<< Updated upstream
                Pit pt = addPit(mainTemp);
                Main.currentSession.pits.add(pt);
=======
                BasePit pt = addPit(mainTemp);
                Main.currentSession.pits.put(pt.teamNum, pt);
>>>>>>> Stashed changes
                Main.currentSession.pitScouts.get(pt.scoutID).addPit(pt);
            }
            lastSavedRow++;
        }
        List<Object> secondTemp = null;
        try{
            secondTemp = SheetsFunctions.getData(Main.currentSession.spreadsheetID, Main.currentSession.secondPitTab, secondLastSavedRow, Main.currentSession.finalSecondPitCol);
        }catch (Exception e){
        
        }
        if(secondTemp!=null){
<<<<<<< Updated upstream
            Pit foundPit = Functions.findPit((int)secondTemp.get(ColumnMappings.ReScoutPit.TEAM_NUM.val));
            if(foundPit!=null){
                foundPit.secondPits.put(LocalTime.parse((String)secondTemp.get(ColumnMappings.ReScoutPit.TIMESTAMP.val), DateTimeFormatter.ofPattern("MM/dd/uuuu kk:mm:ss")),
=======
            BasePit foundBasePit = Functions.findPit((int)secondTemp.get(ColumnMappings.ReScoutPit.TEAM_NUM.val));
            if(foundBasePit !=null){
                foundBasePit.secondPits.put(LocalDateTime.parse((String)secondTemp.get(ColumnMappings.ReScoutPit.TIMESTAMP.val), DateTimeFormatter.ofPattern("MM/dd/uuuu kk:mm:ss")),
>>>>>>> Stashed changes
                                    addSecondPit(secondTemp));
            }
        }
        secondLastSavedRow++;
    }
    
    
    public SecondPit addSecondPit(List<Object> secondTemp){
        SecondPit tempSecondPit = new SecondPit((int)secondTemp.get(ColumnMappings.ReScoutPit.TEAM_NUM.val),
                                            (String)secondTemp.get(ColumnMappings.ReScoutPit.TEAM_NAME.val),
                                            (String)secondTemp.get(ColumnMappings.ReScoutPit.SCOUT_ID.val),
                                            (String)secondTemp.get(ColumnMappings.ReScoutPit.TIMESTAMP.val),
                                            (String)secondTemp.get(ColumnMappings.ReScoutPit.INTERVIEWEE.val));
        tempSecondPit.changes = (String)secondTemp.get(ColumnMappings.ReScoutPit.CHANGES.val);
        tempSecondPit.issues = (String)secondTemp.get(ColumnMappings.ReScoutPit.ISSUES.val);
        tempSecondPit.basicNotes = (String)secondTemp.get(ColumnMappings.ReScoutPit.NOTES.val);
        tempSecondPit.images = (ArrayList<String>)Arrays.asList(((String)secondTemp.get(ColumnMappings.ReScoutPit.PHOTOS.val)).split(", "));
        tempSecondPit.specificQuestions.put((String)secondTemp.get(ColumnMappings.ReScoutPit.Q1.val), (String)secondTemp.get(ColumnMappings.ReScoutPit.A1.val));
        tempSecondPit.specificQuestions.put((String)secondTemp.get(ColumnMappings.ReScoutPit.Q2.val), (String)secondTemp.get(ColumnMappings.ReScoutPit.A2.val));
        tempSecondPit.specificQuestions.put((String)secondTemp.get(ColumnMappings.ReScoutPit.Q3.val), (String)secondTemp.get(ColumnMappings.ReScoutPit.A3.val));
        tempSecondPit.specificQuestions.put((String)secondTemp.get(ColumnMappings.ReScoutPit.Q4.val), (String)secondTemp.get(ColumnMappings.ReScoutPit.A4.val));
        tempSecondPit.specificQuestions.put((String)secondTemp.get(ColumnMappings.ReScoutPit.Q5.val), (String)secondTemp.get(ColumnMappings.ReScoutPit.A5.val));
        return tempSecondPit;
    
    }
<<<<<<< Updated upstream
    public Pit addPit(List<Object> temp){
        Pit tempPit = new Pit((int)temp.get(TEAM_NUM.val), (String)temp.get(TEAM_NAME.val),
                (String)temp.get(SCOUT_ID.val), (String)temp.get(TIMESTAMP.val), (String)temp.get(INTERVIEWEE.val));
        tempPit.practialWeight = (double)temp.get(PRAC_WEIGHT.val);
        tempPit.officialWeight = (double)temp.get(OFF_WEIGHT.val);
        tempPit.dtLength = (double)temp.get(DT_LEN.val);
        tempPit.dtWidth = (double)temp.get(DT_WIDTH.val);
        tempPit.height = (double)temp.get(HEIGHT.val);
        tempPit.sideExtend = (double)temp.get(EX_SIDE.val);
        tempPit.teleUpExtend = (double)temp.get(EX_TELE.val);
        tempPit.climbUpExtend = (double)temp.get(EX_CLIMB.val);
        tempPit.bumperCoverage = (String)temp.get(COVERAGE.val);
        tempPit.maxCells = (int)temp.get(CAPACITY.val);
=======
    public static BasePit addPit(List<Object> temp){
        return addPit(temp, Main.currentSession);
    }
    public static BasePit addPit(List<Object> temp, Session session_){
        BasePit tempBasePit = new BasePit(Integer.valueOf((String)temp.get(TEAM_NUM.val)), (String)temp.get(TEAM_NAME.val),
                (String)temp.get(SCOUT_ID.val), (String)temp.get(TIMESTAMP.val), (String)temp.get(INTERVIEWEE.val), session_);
        tempBasePit.practialWeight = Double.valueOf((String)temp.get(PRAC_WEIGHT.val));
        tempBasePit.officialWeight = Double.valueOf((String)temp.get(OFF_WEIGHT.val));
        tempBasePit.dtLength = Double.valueOf((String)temp.get(DT_LEN.val));
        tempBasePit.dtWidth = Double.valueOf((String)temp.get(DT_WIDTH.val));
        tempBasePit.height = Double.valueOf((String)temp.get(HEIGHT.val));
        tempBasePit.sideExtend = Double.valueOf((String)temp.get(EX_SIDE.val));
        tempBasePit.teleUpExtend = Double.valueOf((String)temp.get(EX_TELE.val));
        tempBasePit.climbUpExtend = Double.valueOf((String)temp.get(EX_CLIMB.val));
        tempBasePit.bumperCoverage = (String)temp.get(COVERAGE.val);
        tempBasePit.maxCells = Integer.valueOf((String)temp.get(CAPACITY.val));
>>>>>>> Stashed changes
        switch((String)temp.get(INTAKE.val)){
            case "Floor":
                tempBasePit.intake = Enums.Pickup.FLOOR;
                break;
            case "Station":
                tempBasePit.intake = Enums.Pickup.STATION;
                break;
            case "Both":
                tempBasePit.intake = Enums.Pickup.BOTH;
                break;
            default:
                tempBasePit.intake = Enums.Pickup.NEITHER;
                break;
        }
        tempBasePit.canClimb = (((String)temp.get(CLIMB.val)).equals("Yes"));
        tempBasePit.climbDescrip = (String)temp.get(CLIMBER.val);
        tempBasePit.canBuddy = ((String)temp.get(BUDDY.val)).equals("Yes");
        tempBasePit.visionPickup = getTarget(((String)temp.get(VIS_PICKUP.val)).split(", "));
        tempBasePit.visionAngle = new ArrayList<>(Arrays.asList(((String)temp.get(VIS_ANGLE.val)).split(", ")));
        tempBasePit.shootAnywhere = ((String)temp.get(SHOOT_ANY.val)).equals("Yes");
        switch((String)temp.get(CONTROL.val)){
            case "Both":
                tempBasePit.controlPanel = Control.BOTH;
                break;
            case "Rotation":
                tempBasePit.controlPanel = Control.ROTATION;
                break;
            case "Position":
                tempBasePit.controlPanel = Control.POSITION;
                break;
            default:
                tempBasePit.controlPanel = Control.NEITHER;
                break;
        }
        tempBasePit.issues = (String)temp.get(ISSUES.val);
        tempBasePit.techNotes = (String)temp.get(TECH_NOTES.val);
    
<<<<<<< Updated upstream
        tempPit.autoStrategies = getStrat(((String)temp.get(AUTO_STRAT.val)).split(", "));
        tempPit.autoScore = ((String)temp.get(AUTO_SCORE.val)).equals("Yes");
        tempPit.teleopStrategies = getStrat((((String)temp.get(TELE_STRAT.val)).split(", ")));
        tempPit.teleopStrategies = getStrat(((String)temp.get(AUTO_SCORE.val)).split(", "));
        tempPit.hpPreferences.collecting = getPref((String)temp.get(HP_COLLEC.val));
        tempPit.hpPreferences.loading = getPref((String)temp.get(HP_LOAD.val));
        tempPit.hpPreferences.passing = getPref((String)temp.get(HP_PASS.val));
        tempPit.hpPreferences.storing = getPref((String)temp.get(HP_STORE.val));
        tempPit.hpPreferences.tracking = getPref((String)temp.get(HP_TRACK.val));
        tempPit.hpPreferences.other = getPref((String)temp.get(HP_OTHER.val));
        tempPit.travelArea = getZone(new String[]{(String) temp.get(TRAVEL.val)});
        tempPit.shotArea = getZone(((String)temp.get(SHOOT.val)).split(", "));
        tempPit.collectArea = getZone(((String)temp.get(COLLECT.val)).split(", "));
        tempPit.teleopPref = getStrat(new String[]{(String)temp.get(TELE_PREF.val)}).get(0);
        tempPit.autoPref = getStrat(new String[]{(String)temp.get(AUTO_PREF.val)}).get(0);
        tempPit.stratNotes = (String)temp.get(STRAT_NOTES.val);
        tempPit.imageLink = (ArrayList<String>)Arrays.asList(((String)temp.get(PHOTOS.val)).split(", "));
        return tempPit;
=======
        tempBasePit.autoStrategies = getStrat(((String)temp.get(AUTO_STRAT.val)).split(", "));
        tempBasePit.autoScore = ((String)temp.get(AUTO_SCORE.val)).equals("Yes");
        tempBasePit.teleopStrategies = getStrat((((String)temp.get(TELE_STRAT.val)).split(", ")));
        tempBasePit.teleopStrategies = getStrat(((String)temp.get(AUTO_SCORE.val)).split(", "));
        tempBasePit.hpPreferences.collecting = getPref((String)temp.get(HP_COLLEC.val));
        tempBasePit.hpPreferences.loading = getPref((String)temp.get(HP_LOAD.val));
        tempBasePit.hpPreferences.passing = getPref((String)temp.get(HP_PASS.val));
        tempBasePit.hpPreferences.storing = getPref((String)temp.get(HP_STORE.val));
        tempBasePit.hpPreferences.tracking = getPref((String)temp.get(HP_TRACK.val));
        tempBasePit.hpPreferences.other = getPref((String)temp.get(HP_OTHER.val));
        tempBasePit.travelArea = getZone(new String[]{(String) temp.get(TRAVEL.val)});
        tempBasePit.shotArea = getZone(((String)temp.get(SHOOT.val)).split(", "));
        tempBasePit.collectArea = getZone(((String)temp.get(COLLECT.val)).split(", "));
        tempBasePit.teleopPref = getStrat(new String[]{(String)temp.get(TELE_PREF.val)}).get(0);
        tempBasePit.autoPref = getStrat(new String[]{(String)temp.get(AUTO_PREF.val)}).get(0);
        tempBasePit.stratNotes = (String)temp.get(STRAT_NOTES.val);
        tempBasePit.imageLink = new ArrayList<String>(Arrays.asList(((String)temp.get(PHOTOS.val)).split(", ")));
        
        tempBasePit.dataFilled = true;
        return tempBasePit;
>>>>>>> Stashed changes
    }
    
    public ArrayList<Enums.Target> getTarget(String[] input){
        ArrayList<Target> toReturn = new ArrayList<>();
        for(String str : input){
            switch (str){
                case "Loading Bays":
                    toReturn.add(Target.LOADING);
                    break;
                case "Inner Port":
                    toReturn.add(Target.INNER);
                    break;
                case "Outer Port":
                    toReturn.add(Target.OUTER);
                    break;
                case "Lower Port":
                    toReturn.add(Target.LOWER);
                    break;
                default:
                    toReturn.add(Target.OTHER);
                    break;
            }
        }
        return toReturn;
    }
    
    public ArrayList<Strat> getStrat(String[] input){
        ArrayList<Strat> toReturn = new ArrayList<>();
        for(String str : input){
            switch(str){
                case "Just off line":
                    toReturn.add(Strat.AUTOLINE);
                    break;
                case "Collect cells":
                    toReturn.add(Strat.COLLECT);
                    break;
                case "Shoot high": case "High Goal Offense": case "High Goal":
                    toReturn.add(Strat.HIGH);
                    break;
                case "Shoot low": case "Low Goal Offense": case "Low Goal":
                    toReturn.add(Strat.LOW);
                    break;
                case "Helper": case "Helper Bot":
                    toReturn.add(Strat.HELPER);
                    break;
                case "Defense":
                    toReturn.add(Strat.DEFENSE);
                    break;
                case "Climb":
                    toReturn.add(Strat.CLIMB);
                    break;
                case "Control panel":
                    toReturn.add(Strat.CONTROL_PANEL);
                default:
                    toReturn.add(Strat.OTHER);
                    break;
            }
        }
        return toReturn;
    }
    
    public Pref getPref(String input){
        switch (input){
            case "Primary":
                return Pref.PRIMARY;
            case "Preferred":
                return Pref.PREFERRED;
            case "Uncomfortable":
                return Pref.UNCOMFORTABLE;
            case "Absolutely not":
                return Pref.NO;
            default:
                return Pref.OKAY;
        }
    }
    
    public ArrayList<Zone> getZone(String[] input){
        ArrayList<Zone> toReturn = new ArrayList<>();
        for(String str : input){
            switch (str){
                case "N/A": case "None":
                    toReturn.add(Zone.NONE);
                    break;
                case "Loading bay":
                    toReturn.add(Zone.LOADING);
                    break;
                case "Alliance sector":
                    toReturn.add(Zone.ALLIANCE_SECTOR);
                    break;
                case "Opponent sector":
                    toReturn.add(Zone.OPPONENT_SECTOR);
                    break;
                case "Rendezvous":
                    toReturn.add(Zone.RENDEZVOUS);
                    break;
                case "Trench":
                    toReturn.add(Zone.TRENCH);
                    break;
                case "Goal":
                    toReturn.add(Zone.GOAL);
                    break;
                case "Both":
                    toReturn.add(Zone.TRENCH);
                    toReturn.add(Zone.RENDEZVOUS);
                    break;
                default:
                    toReturn.add(Zone.OTHER);
                    break;
            }
        }
        return toReturn;
    }
}
