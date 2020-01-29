package base.threads;

import base.Main;
import base.Match;
import base.lib.DataClasses.*;
import base.lib.Enums;
import base.lib.Functions;
import base.scouts.DataScout;
import base.scouts.NoteScout;

import javax.xml.crypto.Data;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

public class CollationThread extends Thread{

    @Override
    public void run(){
        for(Match match_ : Main.currentSession.matches){
            if(match_.dataRediness()){
                collate(match_, Main.currentSession.standScouts, Main.currentSession.noteScouts);
            }
        }
    }
    
    public static void collate(Match match_, HashMap<String, DataScout> dataScoutList, HashMap<String, NoteScout> noteScoutList){
        DataScoutMatch finalData = new DataScoutMatch();
        DataScout[] scouts = new DataScout[match_.matchScouts.size()];
        for(int i=0; i<match_.matchScouts.size(); i++){
            scouts[i] = dataScoutList.get(match_.matchScouts.get(i));
        }
        Arrays.sort(scouts);
        finalData.matchNum =  (int) checkData(scouts, "matchNum", match_.matchNum);
        finalData.allPos = (Enums.Station) checkData(scouts, "allPos", match_.matchNum);
        finalData.teamNum = (int) checkData(scouts, "teamNum", match_.matchNum);
        finalData.absent = (boolean) checkData(scouts, "absent", match_.matchNum);
        finalData.startingPosition = (double) checkData(scouts, "startingPosition", match_.matchNum);
        finalData.capacityTimeS1 = (LocalTime) checkData(scouts, "capacityTimeS1", match_.matchNum);
        finalData.capacityTimeS2 = (LocalTime) checkData(scouts, "capacityTimeS2", match_.matchNum);
        finalData.capacityTimeS3 = (LocalTime) checkData(scouts, "capacityTimeS3", match_.matchNum);
        finalData.numShots = (int) checkData(scouts, "numShots", match_.matchNum);
        finalData.shots = checkShots(scouts, match_.matchNum, finalData.numShots);
        //TODO fouls
        finalData.yellowCard = (boolean) checkData(scouts, "yellowCard", match_.matchNum);
        finalData.redCard = (boolean) checkData(scouts, "redCard", match_.matchNum);
        finalData.activateTimeS2 = (LocalTime) checkData(scouts, "activateTimeS2", match_.matchNum);
        finalData.activateTimeS3 = (LocalTime) checkData(scouts, "activateTimeS3", match_.matchNum);
        finalData.climbDuration = (LocalTime) checkData(scouts, "climbDuration", match_.matchNum);
        finalData.climb = (boolean) checkData(scouts, "climb", match_.matchNum);
        finalData.buddyClimb = (boolean) checkData(scouts, "buddyClimb", match_.matchNum);
        finalData.wasAssisted = (boolean) checkData(scouts, "wasAssisted", match_.matchNum);
        finalData.leveled = (boolean) checkData(scouts, "leveled", match_.matchNum);
        finalData.parked = (boolean) checkData(scouts, "parked", match_.matchNum);
        finalData.disabled = (boolean) checkData(scouts, "disabled", match_.matchNum);
        finalData.incapacitated = (boolean) checkData(scouts, "incapacitated", match_.matchNum);
        finalData.climbRP = (boolean) checkData(scouts, "climbRP", match_.matchNum);
        finalData.totalRP = (int) checkData(scouts, "totalRP", match_.matchNum);
        finalData.totalPoints = (int) checkData(scouts, "totalPoints", match_.matchNum);
        
        
        finalData.dataNotes = "";
        for (DataScout scout_ : scouts){
            DataScoutMatch scoutMatch = scout_.matchData.get(scout_.matchesScouted.indexOf(match_.matchNum));
            finalData.defenseRank += scoutMatch.defenseRank;
            finalData.defenseAvoidanceRank += scoutMatch.defenseAvoidanceRank;
            finalData.driverRank += scoutMatch.driverRank;
            finalData.humanPlayerRank += scoutMatch.humanPlayerRank;
            finalData.dataNotes += scoutMatch.dataNotes;
            finalData.dataNotes += "  ;  ";
        }
        
        //TODO check for outliers
        finalData.defenseRank = finalData.defenseRank/scouts.length;
        finalData.defenseAvoidanceRank = finalData.defenseAvoidanceRank/scouts.length;
        finalData.driverRank = finalData.driverRank/scouts.length;
        finalData.humanPlayerRank = finalData.humanPlayerRank/scouts.length;
    
    
        NoteScout[] noteScouts = new NoteScout[match_.noteScouts.size()];
        for(int i=0; i<match_.noteScouts.size(); i++){
            noteScouts[i] = noteScoutList.get(match_.noteScouts.get(i));
        }
    
        String finalNotes = "";
        for(NoteScout scout_ : noteScouts){
            finalNotes += scout_.matchData.get(scout_.matchesScouted.indexOf(match_.matchNum)).bigNotes;
            finalNotes += "  ;  ";
        }
    
        match_.passFinalData(finalData, finalNotes);
    }
    
    public static Object checkData(DataScout[] scouts, String key, int matchNum){
        ArrayList<Object> scoutData = new ArrayList<>();
        try {
            scoutData.add(DataScoutMatch.class.getField(key).get(scouts[0].matchData.get(scouts[0].matchesScouted.indexOf(matchNum))));
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        if(scouts.length>=2){
            try {
                scoutData.add(DataScoutMatch.class.getField(key).get(scouts[1].matchData.get(scouts[1].matchesScouted.indexOf(matchNum))));
            }catch (Exception e){
                System.out.println(e);
                return null;
            }
            if(scouts.length>=3){
                try {
                    scoutData.add(DataScoutMatch.class.getField(key).get(scouts[2].matchData.get(scouts[2].matchesScouted.indexOf(matchNum))));
                }catch (Exception e){
                    System.out.println(e);
                    return null;
                }
            }
        }
    
        //FIXME this is jank
        Object correctData = findScoutMean(scouts, scoutData, key, matchNum);
        for(int i=0; i<scouts.length-1; i++){
            scouts[i].calculateRank(key, scoutData.get(i).equals(correctData));
        }
        return correctData;
    }
    
    public static ArrayList<Shot> checkShots(DataScout[] scouts, int matchNum, int numShots){
        ArrayList<Shot> finalShots = new ArrayList<>();
        ArrayList<ArrayList<Shot>> scoutData = new ArrayList<>();
        scoutData.add(scouts[0].matchData.get(scouts[0].matchesScouted.indexOf(matchNum)).shots);
        if(scouts.length >=2){
            scoutData.add(scouts[1].matchData.get(scouts[1].matchesScouted.indexOf(matchNum)).shots);
            if(scouts.length >= 3){
                scoutData.add(scouts[2].matchData.get(scouts[2].matchesScouted.indexOf(matchNum)).shots);
            }
        }
        
        for(int i=0; i<=numShots; i++){
            LocalTime shotTime = LocalTime.of(0,0);
            ArrayList<Object> temp = new ArrayList<>();
            Shot finalShot = null;
            if (scoutData.get(0).size()>i) {
                shotTime = scoutData.get(0).get(i).timeStamp;
                temp.add(scoutData.get(0).get(i));
                if(scoutData.size()>=2) {
                    Shot tempShot = Functions.findShot(scoutData.get(1), shotTime, 2.0);
                    if (tempShot != null) temp.add(tempShot);
                    if(scoutData.size()>=3){
                        tempShot = Functions.findShot(scoutData.get(2), shotTime, 2.0);
                        if (tempShot!= null) temp.add(tempShot);
                    }
                }
                //FIXME this is jank
                Object[] confScouts = confScouts(temp);
                boolean[] scoutsCorrect = (boolean[])confScouts[1];
                finalShot = (Shot) confScouts[0];
    
                //TODO confirm that there's no way to check this on TBA
    
                scouts[0].calculateRank("shots", scoutsCorrect[0]);
                if(scoutsCorrect.length >= 2){
                    scouts[1].calculateRank("shots", scoutsCorrect[1]);
                    if(scoutsCorrect.length >= 3){
                        scouts[2].calculateRank("shots", scoutsCorrect[2]);
                    }
                }
            }else if(scoutData.size()>=2 && scoutData.get(1).size()>i){
                shotTime = scoutData.get(1).get(i).timeStamp;
                temp.add(scoutData.get(1).get(i));
                if(scoutData.size()>=3){
                    Shot tempShot = Functions.findShot(scoutData.get(2), shotTime, 2.0);
                    if (tempShot!= null) temp.add(tempShot);
                }
                //FIXME this is jank
                Object[] confScouts = confScouts(temp);
                boolean[] scoutsCorrect = (boolean[])confScouts[1];
                finalShot = (Shot) confScouts[0];
    
                //TODO confirm that there's no way to check this on TBA
    
                scouts[1].calculateRank("shots", scoutsCorrect[1]);
                if(scoutsCorrect.length >= 3){
                    scouts[2].calculateRank("shots", scoutsCorrect[2]);
                }
                scouts[0].calculateRank("shots", false);
            }else if (scoutData.size()>=3 && scoutData.get(2).size()>i){
                scouts[2].calculateRank("shots", true);
                finalShot = scoutData.get(2).get(i);
                scouts[1].calculateRank("shots", false);
                scouts[0].calculateRank("shots", false);
            }else{
                continue;
            }
    
            finalShots.add(finalShot);
        
        }
        Collections.sort(finalShots);
        return finalShots;
        
    }
    
    
    public static boolean checkTBA(String key, Object data){
        //TODO once the map comes out
        return true;
    }
    
    public static Object getTBA(String key, DataScout[] scouts, ArrayList<Object> scoutData){
        //TODO once the map comes out
        //TODO also include rank calc here
        return scoutData.get(0);
    }
}
