package base.models;

import base.Main;
import base.lib.DataClasses.*;
import base.lib.Enums.*;
import base.models.DataScout;
import base.models.NoteScout;

import java.util.ArrayList;
import java.util.HashMap;

public class Match implements Comparable{
    
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
    
    
    public Match (int matchNum_, int teamNum_, Station alliancePosition_){
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
    
    public void passFinalData(DataScoutMatch finalData_, String bigNotes_){
        this.matchData = finalData_;
        this.bigNotes = bigNotes_;
        this.isColl = true;
    }
    
    @Override
    public int compareTo(Object o) {
        int compareRank = (int)Math.floor(((Match)o).matchNum);
        int rank = (int)Math.floor(this.matchNum);
        //FIXME this needs to be tested idk what im do
        return rank - compareRank; //ascending
        
    }
    
    @Override
    public boolean equals(Object o){
        return ((Match) o).teamNum==this.teamNum
                && ((Match) o).matchNum == this.matchNum
                && ((Match) o).allPos.equals(this.allPos);
    }
    
}
