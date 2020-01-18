package base.threads;

import base.Main;
import base.Match;
import base.lib.DataClasses;
import base.scouts.DataScout;
import base.scouts.NoteScout;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CollationThread extends Thread{

    @Override
    public void run(){
        for(Match match_ : Main.currentSession.matches){
            if(match_.dataRediness()){
                HashMap<String, Object> finalData = new HashMap<String, Object>();
                DataScout[] scouts = (DataScout[])match_.matchScouts.toArray();
                Arrays.sort(scouts);
                NoteScout[] noteScouts = (NoteScout[])match_.noteScouts.toArray();
                finalData.put("matchNum", checkData(scouts, "matchNum", match_.matchNum));
                finalData.put("allPos", checkData(scouts, "allPos", match_.matchNum));
                finalData.put("teamNum", checkData(scouts, "teamNum", match_.matchNum));
                finalData.put("absent", checkData(scouts, "absent", match_.matchNum));
                finalData.put("startingPosition", checkData(scouts, "startingPosition", match_.matchNum));
                finalData.put("capacityTimeS1", checkData(scouts, "capacityTimeS1", match_.matchNum));
                finalData.put("capacityTimeS2", checkData(scouts, "capacityTimeS2", match_.matchNum));
                finalData.put("capacityTimeS3", checkData(scouts, "capacityTimeS3", match_.matchNum));
                finalData.put("defenseRank", checkData(scouts, "defenseRank", match_.matchNum));
                finalData.put("defenseAvoidanceRank", checkData(scouts, "defenseAvoidanceRank", match_.matchNum));
                finalData.put("numShots", checkData(scouts, "numShots", match_.matchNum));
                finalData.put("shots", checkShots((int)finalData.get("numShots"), scouts));
                
                
            }
        }
    }
    
    private Object checkData(DataScout[] scouts, String key, int matchNum){
        boolean isConsist = true;
        ArrayList<Object> scoutData = new ArrayList<>();
        scoutData.add(scouts[0].matchData.get(scouts[0].matchesScouted.indexOf(matchNum)).get(key));
        if(scouts.length>=2){
            scoutData.add(scouts[1].matchData.get(scouts[1].matchesScouted.indexOf(matchNum)).get(key));
            if(scouts.length>=3){
                scoutData.add(scouts[2].matchData.get(scouts[2].matchesScouted.indexOf(matchNum)).get(key));
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
    
    private Object checkShots(int numShots, DataScout[] scouts, ){
        for(int i=0; i<=numShots; i++){
            DataClasses.Shot zeroShot = ((ArrayList<DataClasses.Shot>)scouts[0].matchData.get(scouts[0].matchesScouted.indexOf(match_.matchNum)).get("shots")).get(i);
            DataClasses.Shot oneShot=null, twoShot=null;
            if(scouts.length>=2) oneShot = ((ArrayList<DataClasses.Shot>)scouts[1].matchData.get(scouts[1].matchesScouted.indexOf(match_.matchNum)).get("shots")).get(i);
            if(scouts.length>=3) twoShot = ((ArrayList<DataClasses.Shot>)scouts[2].matchData.get(scouts[2].matchesScouted.indexOf(match_.matchNum)).get("shots")).get(i);
            if(zeroShot!=null){
                if(oneShot!=null){
                    if(twoShot !=null){
                        checkData(scouts, )
                    }
                }
            }
        }
    }
    
    
    private boolean checkTBA(String key, Object data){
        //TODO once the map comes out
        return true;
    }
    
    private Object getTBA(String key, DataScout[] scouts, ArrayList<Object> scoutData){
        //TODO once the map comes out
        //TODO also include rank setting here
        return scoutData.get(0);
    }
}
