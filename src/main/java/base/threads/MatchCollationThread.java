package base.threads;

import base.Main;
import base.models.Match;
import base.lib.DataClasses.*;
import base.lib.Enums;
import base.lib.Functions;
import base.models.DataScout;
import base.models.NoteScout;

import java.awt.*;
import java.time.LocalTime;
import java.util.*;

public class MatchCollationThread extends Thread{

    @Override
    public void run(){
        for(Match match_ : Main.currentSession.matches.values()){
            if(match_.dataRediness()){
                collate(match_, Main.currentSession.standScouts, Main.currentSession.noteScouts);
            }
        }
    }
    
    public static void collate(Match match_, HashMap<String, DataScout> dataScoutList, HashMap<String, NoteScout> noteScoutList){
        DataScoutMatch finalData = new DataScoutMatch();
        DataScout[] scouts = new DataScout[match_.matchScouts.size()];
        for(int i=0; i<match_.matchScouts.size(); i++){
            scouts[i] = dataScoutList.get(match_.matchScouts.get(i).getName());
        }
        Arrays.sort(scouts);
        finalData.matchNum =  (int) checkData(scouts, "matchNum", match_.matchNum);
        finalData.allPos = (Enums.Station) checkData(scouts, "allPos", match_.matchNum);
        finalData.teamNum = (int) checkData(scouts, "teamNum", match_.matchNum);
        finalData.absent = (boolean) checkData(scouts, "absent", match_.matchNum);
        finalData.startingPosition = (double) checkData(scouts, "startingPosition", match_.matchNum);
        finalData.moved = (boolean) checkData(scouts, "moved", match_.matchNum);
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
            noteScouts[i] = noteScoutList.get(match_.noteScouts.get(i).getName());
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
        for(DataScout scout : scouts) {
            try {
                scoutData.add(DataScoutMatch.class.getField(key).get(scout.matchData.get(scout.matchesScouted.indexOf(matchNum))));
            } catch (Exception e) {
                System.out.println(e);
                return null;
            }
        }
    
        //FIXME this is jank
        Object correctData = findScoutMean(scouts, scoutData, key, matchNum);
        for(int i=0; i<scouts.length; i++){
            scouts[i].calculateRank(key, scoutData.get(i).equals(correctData));
        }
        return correctData;
    }
    
    public static Object findScoutMean(DataScout[] scouts, ArrayList<Object> scoutData, String key, int matchNum){
        HashMap<Object, Integer> data = new HashMap<>();
        Object correctData = null;
        
        for(int i=0; i<scoutData.size(); i++){
            boolean found = false;
            for(Object k : data.keySet()){
                if(k.equals(scoutData.get(i))){
                    data.put(k, data.get(k)+1);
                    found = true;
                }
            }
            if(!found){
                data.put(scoutData.get(i), 1);
            }
        }
        
//        for(Object scoutPoint : scoutData){
//            if(data.containsKey(scoutPoint)){
//                data.put(scoutPoint, data.get(scoutPoint)+1);
//            }else{
//                data.put(scoutPoint, 1);
//            }
//        }
        
        int correctDataRank = 0;
        for(Object point : data.keySet()){
            if(data.get(point) > correctDataRank){
                correctData = point;
                correctDataRank = data.get(correctData);
            }else if(data.get(point)==correctDataRank){
                correctData = null;
            }
        }
        if(correctData==null){
            correctData = scoutData.get(0); //highest ranked
        }
        
        //CHECK TBA
        if(!checkTBA(key, correctData)){
            correctData = getTBA(key, scouts, scoutData);
        }
        
        return correctData;
    }
    
    public static ArrayList<Shot> checkShots(DataScout[] scouts, int matchNum, int numShots){
        ArrayList<Shot> finalShots = new ArrayList<>();
        ArrayList<ArrayList<Shot>> scoutData = new ArrayList<>();
        for(DataScout scout : scouts){
            scoutData.add(scout.matchData.get(scout.matchesScouted.indexOf(matchNum)).shots);
        }
        ArrayList<ArrayList<LocalTime>> timeLines = new ArrayList<>();
        for(int i=0; i<scoutData.size(); i++){
            timeLines.add(i,new ArrayList<>());
            for(int j=0; j<scoutData.get(i).size(); j++){
                timeLines.get(i).add(j, scoutData.get(i).get(j).timeStamp);
            }
        }
        ArrayList<LocalTime> finalTimeLine = alignTimelines(timeLines, numShots, scouts);
        for(LocalTime shotTime : finalTimeLine){
            ArrayList<Shot> shotList = new ArrayList<>();
            for(int scoutShots=0; scoutShots<scoutData.size(); scoutShots++){
                Shot tempShot = Functions.findShot(scoutData.get(scoutShots), shotTime, 5);
                if(tempShot!=null) {
                    shotList.add(tempShot); //todo determine if 5s is a good margin
                }
            }
            finalShots.add(findShotMean(scouts, shotList, matchNum));
        }
        
        return finalShots;
    }
    
    public static Shot findShotMean(DataScout[] scouts, ArrayList<Shot> scoutData, int matchNum){
        HashMap<LocalTime, Integer> timeFrequency = new HashMap<>();
        //Time mean
        for(int i=0; i<scoutData.size(); i++){
            boolean found = false;
            for(LocalTime k : timeFrequency.keySet()){
                if(k.equals(scoutData.get(i).timeStamp)){
                    timeFrequency.put(k, timeFrequency.get(k)+1);
                    found = true;
                }
            }
            if(!found){
                timeFrequency.put(scoutData.get(i).timeStamp, 1);
            }
        }
        
        int timeMaxFreq = 0;
        LocalTime correctTime = null;
        for(LocalTime k : timeFrequency.keySet()){
            if(timeFrequency.get(k) > timeMaxFreq){
                correctTime = k;
                timeMaxFreq = timeFrequency.get(k);
            }else if(timeFrequency.get(k) == timeMaxFreq){
                correctTime = null;
            }
        }
        
        if(correctTime==null){
            correctTime = scoutData.get(0).timeStamp; //set to best scout
        }
        
        //Type mean
        HashMap<Enums.Goal, Integer> typeFrequency = new HashMap<>();
        typeFrequency.put(Enums.Goal.MISS, 0);
        typeFrequency.put(Enums.Goal.INNER, 0);
        typeFrequency.put(Enums.Goal.OUTER, 0);
        typeFrequency.put(Enums.Goal.LOWER, 0);
        for(int i=0; i<scoutData.size(); i++){
            typeFrequency.put(scoutData.get(i).scored, typeFrequency.get(scoutData.get(i).scored)+1);
        }
    
        int typeMaxFreq = 0;
        Enums.Goal correctType = null;
        for(Enums.Goal k : typeFrequency.keySet()){
            if(typeFrequency.get(k) > typeMaxFreq){
                correctType = k;
                typeMaxFreq = typeFrequency.get(k);
            }else if(typeFrequency.get(k) == typeMaxFreq){
                correctType = null;
            }
        }
    
        if(correctType==null){
            correctType = scoutData.get(0).scored; //set to best scout
        }
        
        
        //Position mean
        HashMap<Point, Integer> positionFrequency = new HashMap<>();
        for(int i=0; i<scoutData.size(); i++){
            boolean found = false;
            for(Point k : positionFrequency.keySet()){
                //fixme test margins
                if((Math.abs(k.getX()-scoutData.get(i).position.getX())<=10)&&(Math.abs(k.getY()-scoutData.get(i).position.getY())<=10)){
                    positionFrequency.put(k, positionFrequency.get(k)+1);
                    found = true;
                }
            }
            if(!found){
                positionFrequency.put(scoutData.get(i).position, 1);
            }
        }
    
        int positionMaxFreq = 0;
        Point correctPos = null;
        for(Point k : positionFrequency.keySet()){
            if(positionFrequency.get(k) > positionMaxFreq){
                correctPos = k;
                positionMaxFreq = positionFrequency.get(k);
            }else if(positionFrequency.get(k) == positionMaxFreq){
                correctPos = null;
            }
        }
    
        if(correctPos==null){
            correctPos = scoutData.get(0).position; //set to best scout
        }
        
        //todo TBA
        
        return new Shot(correctPos, correctType, correctTime);
        
    }
    
    public static ArrayList<LocalTime> alignTimelines(ArrayList<ArrayList<LocalTime>> timeLines, int numShots, DataScout[] scouts){
        //Convert timelines to floats
        ArrayList<ArrayList<Float>> floatTimeLines = new ArrayList<>();
        for(int i=0; i<timeLines.size(); i++){
            floatTimeLines.add(i, new ArrayList<>());
            for(int j=0; j<timeLines.get(i).size(); j++) {
                floatTimeLines.get(i).add(Functions.getFloatTime(timeLines.get(i).get(j)));
            }
        }
        
        ArrayList<ArrayList<Float>> correctLength = new ArrayList<>();
        
        for(int i=0; i<floatTimeLines.size(); i++){
            if(numShots == floatTimeLines.get(i).size()){
                correctLength.add(floatTimeLines.get(i));
            }else{
                correctLength.add(new ArrayList<>());
                for(int j=0; j<numShots; j++){
                    correctLength.get(i).add((float)254); //IMPORTANT the number 254 is a key to see what hasn't been set
                }
            }
        }
        
        ArrayList<Float> correctTimeline = new ArrayList<>();
        //get highest ranked scout with correct length
        for(int i=0; i<floatTimeLines.size(); i++){
            if(correctLength.contains(floatTimeLines.get(i))){
                correctTimeline = floatTimeLines.get(i);
                i=floatTimeLines.size()+1;
            }
        }
        
        for(int wrongTL=0; wrongTL<floatTimeLines.size(); wrongTL++){
            if(!correctLength.contains(floatTimeLines.get(wrongTL))){
                //timeline is actually wrong
                // create a matrix that is wrongTimes x rightTimes
                // this will be filled with the time difference between
                // each wrong time and every right time
                Float[][] times = new Float[floatTimeLines.get(wrongTL).size()][correctTimeline.size()];
                
                for(int i=0; i<floatTimeLines.get(wrongTL).size(); i++){
                    for(int j=0; j<correctTimeline.size(); j++){
                        times[i][j] = Math.abs(correctTimeline.get(j)-floatTimeLines.get(wrongTL).get(i));
                    }
                }
                
                while(Functions.getMin(times)[0][0]<200){
                    Float[][] lowestIndex = Functions.getMin(times); // [y,x]
                    correctLength.get(wrongTL).set(Math.round(lowestIndex[1][1]), floatTimeLines.get(wrongTL).get(Math.round(lowestIndex[1][0])));
                    int lowestRow = Math.round(lowestIndex[1][0]);
                    for(int col=0; col<times[lowestRow].length; col++){
                        times[lowestRow][col] = (float)200;
                    }
                }
                
                for(int t=0; t<correctLength.get(wrongTL).size(); t++){
                    if(correctLength.get(wrongTL).get(t)==254){
                        correctLength.get(wrongTL).set(t, correctTimeline.get(t));
                    }
                }
            }
        }
        ArrayList<LocalTime> finalTimeline = new ArrayList<>();
        for(int indivT = 0; indivT<numShots; indivT++){
            ArrayList<Float> potentialTimes = new ArrayList<>();
            for(int sNum=0; sNum<correctLength.size(); sNum++){
                potentialTimes.add(correctLength.get(sNum).get(indivT));
            }
            finalTimeline.add(consolidateFloatTimes(potentialTimes, scouts));
            System.out.println("ksjfslkdf");
        }
        return finalTimeline;
    }
    
    public static LocalTime consolidateTimes(ArrayList<LocalTime> times, DataScout[] scouts){
        ArrayList<Float> floatTimes = new ArrayList<>();
        for(LocalTime time : times){
            floatTimes.add(Functions.getFloatTime(time));
        }
        return consolidateFloatTimes(floatTimes, scouts);
    }
    
    public static LocalTime consolidateFloatTimes(ArrayList<Float> floatTimes, DataScout[] scouts){
        ArrayList<Float> weights = new ArrayList<>();
        for(int w=0; w<floatTimes.size(); w++){
            weights.add((float)Math.abs(floatTimes.size()-w)+1);
        }
        ArrayList<Float> weightedTimes = new ArrayList<>();
        for(int i=0; i<floatTimes.size(); i++){
            weightedTimes.add(floatTimes.get(i)*weights.get(i));
        }
        float avTime = Functions.getSum(weightedTimes)/Functions.getSum(weights);
        return Functions.getLocalTime(avTime);
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
