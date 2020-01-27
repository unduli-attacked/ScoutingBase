package base;

import base.lib.DataClasses;
import base.lib.Enums.*;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

public class Pit {
    //MAJOR IDENTIFIERS
    public int teamNum;
    public String teamName;
    public String scoutID;
    public LocalTime timeScouted;
    public String interviewee;
    
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
    public HashMap<LocalTime, SecondPit> secondPits = new HashMap<LocalTime, SecondPit>();
    
    public Pit(int teamNum_, String teamName_, String scoutEmail_, String timeScouted_, String interviewee_){
        this.teamNum = teamNum_;
        this.teamName = teamName_;
        this.scoutID = scoutEmail_.split("@")[0]; //Get the first part of the email
        this.timeScouted = LocalTime.parse(timeScouted_, DateTimeFormatter.ofPattern("MM/dd/uuuu kk:mm:ss")); //TODO test me s3nd help
        this.interviewee = interviewee_;
    }
}
