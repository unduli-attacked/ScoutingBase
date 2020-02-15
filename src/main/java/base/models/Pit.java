package base.models;

import base.Main;
import base.lib.DataClasses;
import base.lib.Enums.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Pit implements Saveable{
    //MAJOR IDENTIFIERS
    public int teamNum;
    public String teamName;
    public String scoutID;
    public LocalDateTime timeScouted;
    public String interviewee;
    public boolean dataFilled = false;
    
    //TECHNICAL
    public double practialWeight;
    public double officialWeight;
    public double dtLength;
    public double dtWidth;
    public double height;
    public double sideExtend;
    public double teleUpExtend;
    public double climbUpExtend;
    public String bumperCoverage;
    public int maxCells;
    public Pickup intake;
    public boolean canClimb;
    public String climbDescrip;
    public boolean canBuddy;
    public ArrayList<Target> visionPickup;
    public ArrayList<String> visionAngle; // options on form
    public boolean shootAnywhere;
    public Control controlPanel;
    public String issues;
    public String techNotes;
    
    //STRATEGY
    public ArrayList<Strat> autoStrategies = new ArrayList<>();
    public boolean autoScore;
    public ArrayList<Strat> teleopStrategies = new ArrayList<>();
    public DataClasses.HumanPlayerPreferences hpPreferences = new DataClasses.HumanPlayerPreferences();
    public ArrayList<Zone> travelArea;
    public ArrayList<Zone> shotArea;
    public ArrayList<Zone> collectArea;
    public Strat teleopPref;
    public Strat autoPref;
    public String stratNotes;
    public ArrayList<String> imageLink;
    
    //SECOND
    public HashMap<LocalDateTime, SecondPit> secondPits = new HashMap<LocalDateTime, SecondPit>();
    
    public Pit(int teamNum_, String teamName_, String scoutEmail_, String timeScouted_, String interviewee_){
        new Pit(teamNum_, teamName_, scoutEmail_, timeScouted_, interviewee_, Main.currentSession);
    }
    
    public Pit(int teamNum_, String teamName_, String scoutEmail_, String timeScouted_, String interviewee_, Session session_){
        this.teamNum = teamNum_;
        this.teamName = teamName_;
        this.scoutID = scoutEmail_.split("@")[0]; //Get the first part of the email
        String formatPattern = "M/dd/yyyy H:mm:ss";
        if(timeScouted_.charAt(1)!='/'){
            formatPattern = "MM/dd/yyyy H:mm:ss";
            if(timeScouted_.charAt(12)!=':'){
                formatPattern="MM/dd/yyyy HH:mm:ss";
            }
        }else if(timeScouted_.charAt(11)!=':'){
            formatPattern="M/dd/yyyy HH:mm:ss";
        }
        this.timeScouted = LocalDateTime.parse(timeScouted_, DateTimeFormatter.ofPattern(formatPattern)); //TODO test me s3nd help
        this.interviewee = interviewee_;
        session_.pits.put(this.teamNum, this);
    }
    
    @Override
    public boolean equals(Object o){
        if(o.getClass() != Pit.class){
            return false;
        }
        return ((Pit) o).teamNum == this.teamNum
                && ((Pit) o).teamName.equals(this.teamName)
                && ((Pit) o).scoutID.equals(this.scoutID)
                && ((Pit) o).timeScouted.equals(this.timeScouted);
    }
    
    /**
     * Used in saving raw data
     *
     * @return the file name WITHOUT directory or extension (ex. M14Blue1)
     */
    @Override
    public String getFileName() {
        return "PRIMARY_"+this.teamNum;
    }
    
    /**
     * Used in saving raw data
     *
     * @return the raw directory WITHOUT slashes (ex. rawMatches)
     */
    @Override
    public String getRawDirName() {
        return "rawPits";
    }
}
