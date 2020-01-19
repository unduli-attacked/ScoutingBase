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
import java.util.HashMap;

public class CollationThread extends Thread{

    @Override
    public void run(){
        for(Match match_ : Main.currentSession.matches){
            if(match_.dataRediness()){
                DataScoutMatch finalData = new DataScoutMatch();
                DataScout[] scouts = (DataScout[])match_.matchScouts.toArray();
                Arrays.sort(scouts);
                finalData.matchNum =  (int) checkData(scouts, "matchNum", match_.matchNum);
                finalData.allPos = (Enums.Station) checkData(scouts, "allPos", match_.matchNum);
                finalData.teamNum = (int) checkData(scouts, "teamNum", match_.matchNum);
                finalData.absent = (boolean) checkData(scouts, "absent", match_.matchNum);
                finalData.startingPosition = (double) checkData(scouts, "startingPosition", match_.matchNum);
                finalData.capacityTimeS1 = (LocalTime) checkData(scouts, "capacityTimeS1", match_.matchNum);
                finalData.capacityTimeS2 = (LocalTime) checkData(scouts, "capacityTimeS2", match_.matchNum);
                finalData.capacityTimeS3 = (LocalTime) checkData(scouts, "capacityTimeS3", match_.matchNum);
                finalData.defenseRank = (double) checkData(scouts, "defenseRank", match_.matchNum);
                finalData.defenseAvoidanceRank = (double) checkData(scouts, "defenseAvoidanceRank", match_.matchNum);
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
                finalData.driverRank = (double) checkData(scouts, "driverRank", match_.matchNum);
                finalData.humanPlayerRank = (double) checkData(scouts, "humanPlayerRank", match_.matchNum);
                
                for (DataScout scout_ : scouts){
                    finalData.dataNotes += scout_.matchData.get(scout_.matchesScouted.indexOf(match_.matchNum)).dataNotes;
                    finalData.dataNotes += "  ;  ";
                }
                
    
                NoteScout[] noteScouts = (NoteScout[])match_.noteScouts.toArray();
                
                String finalNotes = "";
                for(NoteScout scout_ : noteScouts){
                    finalNotes += scout_.matchData.get(scout_.matchesScouted.indexOf(match_.matchNum)).bigNotes;
                    finalNotes += "  ;  ";
                }
    
                match_.passFinalData(finalData, finalNotes);
                
                for(DataScout scout_ : scouts){
                    scout_.matches.add(match_);
                }
                
                for(NoteScout scout_ : noteScouts){
                    scout_.matches.add(match_);
                }
            }
        }
    }
    
    private Object checkData(DataScout[] scouts, String key, int matchNum){
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
        Object[] confScouts = confScouts(scoutData);
        boolean[] scoutsCorrect = (boolean[])confScouts[1];
        Object finalData = confScouts[0];
        
        
        if(!checkTBA(key, finalData)){
            finalData = getTBA(key, scouts, scoutData);
        }else{
            scouts[0].calculateRank(key, scoutsCorrect[0]);
            if(scouts.length >= 2) scouts[1].calculateRank(key, scoutsCorrect[1]);
            if(scouts.length >= 3) scouts[2].calculateRank(key, scoutsCorrect[2]);
        }
        return finalData;
    }
    
    private Object[] confScouts(ArrayList<Object> scoutData){
        boolean[] scoutsCorrect = new boolean[scoutData.size()];
    
        Object finalData = null;
    
        if(scoutData.size()==3){
            if(scoutData.get(0).equals(scoutData.get(1))&&scoutData.get(0).equals(scoutData.get(2))){
                scoutsCorrect[0] = scoutsCorrect[1] = scoutsCorrect[2] = true;
                finalData= scoutData.get(0);
            }else if(scoutData.get(0).equals(scoutData.get(1))&&!scoutData.get(0).equals(scoutData.get(2))){
                scoutsCorrect[0] = scoutsCorrect[1] = true;
                scoutsCorrect[2] = false;
                finalData= scoutData.get(0);
            }else if(scoutData.get(0).equals(scoutData.get(2))&&!scoutData.get(0).equals(scoutData.get(1))){
                scoutsCorrect[0] = scoutsCorrect[2] = true;
                scoutsCorrect[1] = false;
                finalData= scoutData.get(0);
            }else if(scoutData.get(1).equals(scoutData.get(2))&&!scoutData.get(1).equals(scoutData.get(0))){
                scoutsCorrect[2] = scoutsCorrect[1] = true;
                scoutsCorrect[0] = false;
                finalData= scoutData.get(1);
            }
        }else if(scoutData.size()==2){
            if(scoutData.get(0).equals(scoutData.get(1))){
                scoutsCorrect[0] = scoutsCorrect[1] = true;
                finalData = scoutData.get(0);
            }else{
                scoutsCorrect[0] = true;
                scoutsCorrect[1] = false;
                finalData = scoutData.get(0);
            }
        }
        
        Object[] toReturn = {finalData, scoutsCorrect};
        return toReturn;
    }
    
    private ArrayList<Shot> checkShots(DataScout[] scouts, int matchNum, int numShots){
        ArrayList<Shot> finalShots = new ArrayList<>();
        ArrayList<ArrayList<Shot>> scoutData = new ArrayList<>();
        scoutData.add(scouts[0].matchData.get(scouts[0].matchesScouted.indexOf(matchNum)).shots);
        if(scouts.length >=2){
            scoutData.add(scouts[1].matchData.get(scouts[1].matchesScouted.indexOf(matchNum)).shots);
            if(scouts.length >= 3){
                scoutData.add(scouts[2].matchData.get(scouts[2].matchesScouted.indexOf(matchNum)).shots);
            }
        }
    
        for(int i=0; i<numShots-1; i++){
            LocalTime shotTime = LocalTime.of(0,0);
            ArrayList<Object> temp = new ArrayList<>();
            Shot finalShot = null;
            if (scoutData.get(0).get(i)!=null) {
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
                if(scouts.length >= 2) scouts[1].calculateRank("shots", scoutsCorrect[1]);
                if(scouts.length >= 3) scouts[2].calculateRank("shots", scoutsCorrect[2]);
            }else if(scoutData.size()>=2 && scoutData.get(1).get(i)!=null){
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
                if(scouts.length >= 3) scouts[2].calculateRank("shots", scoutsCorrect[2]);
            }else if (scoutData.size()>=3 && scoutData.get(2).get(i)!=null){
                scouts[2].calculateRank("shots", true);
                finalShot = scoutData.get(2).get(i);
            }
    
            finalShots.add(finalShot);
        
        }
        
        return finalShots;
        
    }
    
    
    private boolean checkTBA(String key, Object data){
        //TODO once the map comes out
        return true;
    }
    
    private Object getTBA(String key, DataScout[] scouts, ArrayList<Object> scoutData){
        //TODO once the map comes out
        //TODO also include rank calc here
        return scoutData.get(0);
    }
}
