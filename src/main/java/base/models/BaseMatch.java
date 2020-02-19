package base.models;

import base.Main;
import base.lib.DataClasses.*;
import base.lib.Enums.*;

import java.util.ArrayList;
import java.util.HashMap;

<<<<<<< Updated upstream:src/main/java/base/models/Match.java
public class Match implements Comparable{
=======
public class BaseMatch implements Comparable, Saveable{
>>>>>>> Stashed changes:src/main/java/base/models/BaseMatch.java
    
    //MAJOR IDENTIFIERS
    public final int matchNum;
    public final Station allPos;
    public int teamNum;
    private boolean isColl = false;
    
    //RAW DATA
     public ArrayList<DataScout> matchScouts = new ArrayList<>(); //if something breaks, it's bc this was strings
     public ArrayList<NoteScout> noteScouts = new ArrayList<>();
    public HashMap<String, Object> matchBreakdown = new HashMap<>();
    
    //FINAL DATA
    public DataScoutMatch matchData;
    
    public String bigNotes;
    
    
<<<<<<< Updated upstream:src/main/java/base/models/Match.java
    public Match (int matchNum_, int teamNum_, Station alliancePosition_){
=======
    public BaseMatch(int matchNum_, int teamNum_, Station alliancePosition_, Session currentSession_){
        this.matchNum = matchNum_;
        this.teamNum = teamNum_;
        this.allPos = alliancePosition_;
        currentSession_.matches.put(this.getFileName(), this);
    }
    
    public BaseMatch(int matchNum_, int teamNum_, Station alliancePosition_){
>>>>>>> Stashed changes:src/main/java/base/models/BaseMatch.java
        this.matchNum = matchNum_;
        this.teamNum = teamNum_;
        this.allPos = alliancePosition_;
        Main.currentSession.matches.add(this);
    }
    
    public void addDataScout(DataScout scout_){
        this.matchScouts.add(scout_);
    }

    public void addNoteScout(NoteScout scout_) {
        this.noteScouts.add(scout_);
    }
    
    public void addTBAData(HashMap<String, Object> matchBreakdown_){
        this.matchBreakdown = matchBreakdown_;
    }
    
    public boolean dataRediness(){
        return (!isColl)&&(!matchBreakdown.isEmpty()) && (matchScouts.size() >= Main.currentSession.numDataScouts)
            && (noteScouts.size() >= Main.currentSession.numNoteScouts);
    }
    
    /**
     * Pass data correlated by the correlation thread to the Match class
     * @param finalData_
     *      String is the Match.java variable name
     *      Object is it's value
     */
    public void passFinalData(DataScoutMatch finalData_, String bigNotes_) throws ClassCastException{
        this.matchData = finalData_;
        this.bigNotes = bigNotes_;
    }
    
    @Override
    public int compareTo(Object o) {
        int compareRank = (int)Math.floor(((BaseMatch)o).matchNum);
        int rank = (int)Math.floor(this.matchNum);
        //FIXME this needs to be tested idk what im do
        return rank - compareRank; //ascending
        
    }
    
<<<<<<< Updated upstream:src/main/java/base/models/Match.java
=======
    @Override
    public boolean equals(Object o){
        return ((BaseMatch) o).teamNum==this.teamNum
                && ((BaseMatch) o).matchNum == this.matchNum
                && ((BaseMatch) o).allPos.equals(this.allPos);
    }
    
    /**
     * Used in saving raw data
     *
     * @return the file name WITHOUT directory or extension (ex. M14Blue1)
     */
    @Override
    public String getFileName() {
        return "M"+this.matchNum+"_"+this.allPos.toString();
    }
    
    /**
     * Used in saving raw data
     *
     * @return the raw directory WITHOUT slashes (ex. rawMatches)
     */
    @Override
    public String getRawDirName() {
        return "rawMatches";
    }
>>>>>>> Stashed changes:src/main/java/base/models/BaseMatch.java
}
