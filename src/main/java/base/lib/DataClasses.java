package base.lib;
import java.awt.*;
 
import java.time.LocalTime;
import java.util.ArrayList;

import base.lib.Enums.*;
   
public class DataClasses {
    public static class Shot implements Comparable{
        public Point position;
         
        public Goal scored;
        
        public LocalTime timeStamp;
         
        public Shot(Point position_, Goal goal_, LocalTime timeStamp_){
            this.position = position_;
             
            this.scored = goal_;
            
            this.timeStamp = timeStamp_;
         
        }
        
        @Override
        public boolean equals(Object other){
            boolean toReturn;
            if(other.getClass() != Shot.class){
                return false;
            }
            Shot other_ = (Shot)other;
    
            toReturn = this.position.getX() == other_.position.getX()
                    || (Math.abs(this.position.getX() - other_.position.getX())<=10);
            toReturn = toReturn && this.position.getY() == other_.position.getY()
                    || (Math.abs(this.position.getY() - other_.position.getY())<=10);
            toReturn = toReturn && (this.scored == other_.scored);
            return toReturn;
        }
        
        @Override
        public int compareTo(Object other){
            return ((this.timeStamp.getMinute()*60+this.timeStamp.getSecond()
                    - (((Shot)other).timeStamp.getMinute()*60+((Shot)other).timeStamp.getSecond())));
        }
    }            
    public static class DataScoutMatch{
        public String scoutName;
        public int matchNum;
        public Station allPos;
        public int teamNum;
        public boolean absent;
        public double startingPosition;
        public boolean moved;
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
        
        public void copy(DataScoutMatch other){
            this.scoutName = other.scoutName;
            this.matchNum = other.matchNum;
            this.allPos = other.allPos;
            this.teamNum = other.teamNum;
            this.absent = other.absent;
            this.startingPosition = other.startingPosition;
            this.capacityTimeS1 = other.capacityTimeS1;
            this.capacityTimeS2 = other.capacityTimeS2;
            this.capacityTimeS3 = other.capacityTimeS3;
            this.defenseRank = other.defenseRank;
            this.defenseAvoidanceRank = other.defenseAvoidanceRank;
            this.numShots = other.numShots;
            this.shots = other.shots;
            //todo fouls
            this.yellowCard = other.yellowCard;
            this.redCard = other.redCard;
            this.activateTimeS2 = other.activateTimeS2;
            this.activateTimeS3 = other.activateTimeS3;
            this.climbDuration = other.climbDuration;
            this.climb = other.climb;
            this.buddyClimb = other.buddyClimb;
            this.wasAssisted = other.wasAssisted;
            this.leveled = other.leveled;
            this.parked = other.parked;
            this.disabled = other.disabled;
            this.incapacitated = other.incapacitated;
            this.climbRP = other.climbRP;
            this.totalRP = other.totalRP;
            this.totalPoints = other.totalPoints;
            this.driverRank = other.driverRank;
            this.humanPlayerRank = other.humanPlayerRank;
            this.dataNotes = other.dataNotes;
        }
    }
    
    public static class NoteScoutMatch{
        public String scoutName;
        public int matchNum;
        public boolean isBlue;
        public String bigNotes;
        
        public NoteScoutMatch(){}
        
        public void copy(NoteScoutMatch other){
            this.scoutName = other.scoutName;
            this.matchNum = other.matchNum;
            this.isBlue = other.isBlue;
            this.bigNotes = other.bigNotes;
        }
    }
    
    public static class HumanPlayerPreferences{
        public Pref loading;
        public Pref collecting;
        public Pref passing;
        public Pref storing;
        public Pref tracking;
        public Pref other;
        
        public HumanPlayerPreferences(){}
    }
}