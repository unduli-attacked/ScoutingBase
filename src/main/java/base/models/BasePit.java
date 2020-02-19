package base.models;

import base.Main;
import base.lib.DataClasses;
import base.lib.Enums.*;

<<<<<<< Updated upstream:src/main/java/base/models/Pit.java
import java.time.LocalTime;
=======
import java.time.LocalDateTime;
>>>>>>> Stashed changes:src/main/java/base/models/BasePit.java
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

<<<<<<< Updated upstream:src/main/java/base/models/Pit.java
public class Pit {
=======
public class BasePit implements Saveable{
>>>>>>> Stashed changes:src/main/java/base/models/BasePit.java
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
    
<<<<<<< Updated upstream:src/main/java/base/models/Pit.java
    public Pit(int teamNum_, String teamName_, String scoutEmail_, String timeScouted_, String interviewee_){
=======
    public BasePit(int teamNum_, String teamName_, String scoutEmail_, String timeScouted_, String interviewee_){
        new BasePit(teamNum_, teamName_, scoutEmail_, timeScouted_, interviewee_, Main.currentSession);
    }
    
    public BasePit(int teamNum_, String teamName_, String scoutEmail_, String timeScouted_, String interviewee_, Session session_){
>>>>>>> Stashed changes:src/main/java/base/models/BasePit.java
        this.teamNum = teamNum_;
        this.teamName = teamName_;
        this.scoutID = scoutEmail_.split("@")[0]; //Get the first part of the email
        this.timeScouted = LocalTime.parse(timeScouted_, DateTimeFormatter.ofPattern("MM/dd/uuuu kk:mm:ss")); //TODO test me s3nd help
        this.interviewee = interviewee_;
<<<<<<< Updated upstream:src/main/java/base/models/Pit.java
        Main.currentSession.pits.add(this);
=======
        session_.pits.put(this.teamNum, this);
    }
    
    @Override
    public boolean equals(Object o){
        if(o.getClass() != BasePit.class){
            return false;
        }
        return ((BasePit) o).teamNum == this.teamNum
                && ((BasePit) o).teamName.equals(this.teamName)
                && ((BasePit) o).scoutID.equals(this.scoutID)
                && ((BasePit) o).timeScouted.equals(this.timeScouted);
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
>>>>>>> Stashed changes:src/main/java/base/models/BasePit.java
    }
}
