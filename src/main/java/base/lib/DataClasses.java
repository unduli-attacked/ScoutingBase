package base.lib;
import java.awt.*;
 
import java.time.LocalTime;
import java.util.ArrayList;

import base.lib.Enums.*;
   
public class DataClasses {
    public static class Shot{
        public Point position;
         
        public Goal scored;
        
        public LocalTime timeStamp;
         
        public Shot(Point position_, Goal goal_, LocalTime timeStamp_){
            this.position = position_;
             
            this.scored = goal_;
            
            this.timeStamp = timeStamp_;
         
        }      
    }            
    public static class DataScoutMatch{
        public String scoutName;
        public int matchNum;
        public Station allPos;
        public int teamNum;
        public boolean absent;
        public double startingPosition;
        public LocalTime capacityTimeS1;
        public LocalTime capacityTimeS2;
        public LocalTime capacityTimeS3;
        public double defenseRank;
        public double defenseAvoidanceRank;
        public int numShots;
        public ArrayList<Shot> shots;
        //TODO foul counters
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
        
        public DataScoutMatch(){}
    }
    
    public static class NoteScoutMatch{
        public String scoutName;
        public int matchNum;
        public boolean isBlue;
        public String bigNotes;
        
        public NoteScoutMatch(){}
    }
}