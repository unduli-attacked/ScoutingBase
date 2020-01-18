package base;

import base.lib.DataClasses;
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
    public boolean absent;
    public double startingPosition;
    public LocalTime capacityTimeS1;
    public LocalTime capacityTimeS2;
    public LocalTime capacityTimeS3;
    public double defenseRank;
    public double defenseAvoidanceRank;
    public ArrayList<DataClasses.Shot> shots;
    //TODO foul counters here
    public boolean yellowCard;
    public boolean redCard;
    public LocalTime activateTimeS2;
    public LocalTime activateTimeS3;
    public LocalTime climbDuration;
    public boolean climb;
    public boolean buddyClimb;
    public boolean wasAssisted;
    public boolean leveled;
    public boolean parked;
    public boolean disabled;
    public boolean incapacitated;
    public boolean climbRP;
    public int totalRP;
    public int totalPoints;
    public double driverRank;
    public double humanPlayerRank;
    public String dataNotes;
    
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
    public boolean passFinalData(HashMap<String, Object> finalData_) throws ClassCastException{
        this.absent = (boolean)finalData_.get("absent");
        if(this.absent){ return false; }
        
        this.startingPosition = (double)finalData_.get("startingPosition");
        this.capacityTimeS1 = (LocalTime)finalData_.get("capacityTimeS1");
        this.capacityTimeS2 = (LocalTime)finalData_.get("capacityTimeS2");
        this.capacityTimeS3 = (LocalTime)finalData_.get("capacityTimeS3");
        this.defenseRank = (double)finalData_.get("defenseRank");
        this.defenseAvoidanceRank = (double)finalData_.get("defenseAvoidanceRank");
        this.shots = (ArrayList<DataClasses.Shot>)finalData_.get("shots");
        //TODO fouls
        this.yellowCard = (boolean)finalData_.get("yellowCard");
        this.redCard = (boolean)finalData_.get("redCard");
        this.activateTimeS2 = (LocalTime)finalData_.get("activateTimeS2");
        this.activateTimeS3 = (LocalTime)finalData_.get("activateTimeS3");
        this.climbDuration = (LocalTime)finalData_.get("climbDuration");
        this.climb = (boolean)finalData_.get("climb");
        this.buddyClimb = (boolean)finalData_.get("buddyClimb");
        this.wasAssisted = (boolean)finalData_.get("wasAssisted");
        this.leveled = (boolean)finalData_.get("leveled");
        this.parked = (boolean)finalData_.get("parked");
        this.disabled = (boolean)finalData_.get("disabled");
        this.incapacitated = (boolean)finalData_.get("incapacitated");
        this.climbRP = (boolean)finalData_.get("climbRP");
        this.totalRP = (int)finalData_.get("totalRP");
        this.totalPoints = (int)finalData_.get("totalPoints");
        this.driverRank = (double)finalData_.get("driverRank");
        this.humanPlayerRank = (double)finalData_.get("humanPlayerRank");
        this.dataNotes = (String)finalData_.get("dataNotes");
        
        this.bigNotes = (String)finalData_.get("bigNotes");
        
        return true;
    }
    
}
