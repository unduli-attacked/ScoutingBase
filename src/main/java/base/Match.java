package base;

import base.lib.DataClasses.*;
import base.lib.Enums.*;
import base.scouts.DataScout;
import base.scouts.NoteScout;

import java.awt.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Match {
    
    //MAJOR IDENTIFIERS
    public final int matchNum;
    public final Station allPos;
    public int teamNum;
    private boolean isColl = false;
    
    //RAW DATA
     public ArrayList<DataScout> matchScouts;
     public ArrayList<NoteScout> noteScouts;
    public HashMap<String, Object> matchBreakdown;
    
    //FINAL DATA
    public DataScoutMatch matchData;
    
    public String bigNotes;
    
    
    public Match (int matchNum_, int teamNum_, Station alliancePosition_){
        this.matchNum = matchNum_;
        this.teamNum = teamNum_;
        this.allPos = alliancePosition_;
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
    
}
