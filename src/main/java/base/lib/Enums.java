package base.lib;

import java.awt.*;

public class Enums {
    
    public enum Station{
        RED_1(false, "Red 1"), RED_2(false, "Red 2"), RED_3(false, "Red 3"),
        BLUE_1(true, "Blue 1"), BLUE_2(true, "Blue 2"), BLUE_3(true, "Blue 3");
        
        boolean isBlue;
        String basic;
        Station(boolean blu_, String str_){
            isBlue=blu_;
            basic=str_;
        }
        
        public boolean isBlue(){return isBlue;}
        
        @Override
        public String toString(){return basic;}
    }
    
    public enum Goal{
        MISS(0, "Miss"), LOWER(1, "Lower"),
        OUTER(2, "Outer"), INNER(3, "Inner");
        
        int basic;
        String name;
        Goal(int pts_, String str_){
            this.name = str_;
            basic=pts_;
        }
        
        @Override
        public String toString(){return name;}
    }

    public enum Pickup{
        STATION("Station"), FLOOR("Floor"), BOTH("Both"), NEITHER("Neither");
        
        public String basic;
        private Pickup(String str_){
            this.basic = str_;
        }
        
        @Override
        public String toString(){
            return basic;
        }
    }
    
    public enum Target{
        LOADING("Loading"), INNER("Inner"), OUTER("Outer"), LOWER("Lower"), OTHER("Other");
    
        public String basic;
        private Target(String str_){
            this.basic = str_;
        }
    
        @Override
        public String toString(){
            return basic;
        }
    }
    
    public enum Control{
        ROTATION("Rotation"), POSITION("Position"),  BOTH("Both"), NEITHER("Neither");
        
        public String basic;
        private Control(String str_){
            this.basic = str_;
        }
        
        @Override
        public String toString(){
            return basic;
        }
    }
    
    public enum Strat{
        AUTOLINE("Autoline"), COLLECT("Collect"), HIGH("High"), LOW("Low"),
        HELPER("Helper"), DEFENSE("Defense"), CLIMB("Climb"),
        CONTROL_PANEL("Control Panel"), OTHER("Other");
        
        public String basic;
        private Strat(String str_){
            this.basic = str_;
        }
    
        @Override
        public String toString(){
            return basic;
        }
    }
    
    public enum Pref {
        PRIMARY("Primary"), PREFERRED("Preferred"), OKAY("Okay"),
        UNCOMFORTABLE("Uncomfortable"), NO("Absolutely Not");
    
        public String basic;
    
        private Pref(String str_) {
            this.basic = str_;
        }
    
        @Override
        public String toString() {
            return basic;
        }
    }
    
    public enum Zone{
        NONE("None"), TRENCH("Trench"), GOAL("Goal"), RENDEZVOUS("Rendezvous"),
        OPPONENT_SECTOR("Opponent Sector"), ALLIANCE_SECTOR("Alliance Sector"),
        LOADING("Loading"), OTHER("Other");
    
        public String basic;
        private Zone(String str_){
            this.basic = str_;
        }
    
        @Override
        public String toString(){
            return basic;
        }
    }
    
    public enum Dot {
        SUCCESS(Colors.succ, "Success"), FAILURE(Colors.failure, "Failure"),
        NO_ATTEMPT(Colors.noAttempt, "Not Attempted"), RECOVERED(Colors.recovered, "Recovered"),
        WAS_LIFTED(Colors.wasLifted, "Was Lifted"), LEVEL_SELF(Colors.levelSelf, "Leveled alone"),
        LEVEL_TEAM(Colors.levelTeam, "Leveled With Multiple Bots");
        
        public Color col;
        String basic;
        Dot(Color col_, String basic_){
            this.col = col_;
            this.basic = basic_;
        }
        
        @Override
        public String toString(){
            return basic;
        }
    }
    
    public enum Foul {
        GEN_TECH("General Tech Foul", true), TRENCH_TECH("Trench Intrusion", true),
        RDV_TECH("Endgame Rendezvous Intrusion", true), ZONE_TECH("Protected Zone Intrusion", true),
        PIN_FOUL("Pinning", false), CLIMB_PEN("Touching Climbing Robot", false),
        GEN_FOUL("General Foul", false), EXTEND_FOUL("Outside Frame Perimeter", false),
        HUMAN_FOUL("Human Player Error", false);
        
        String basic;
        public boolean isTech;
        Foul(String str_, boolean tech_){
            this.basic = str_;
            this.isTech = tech_;
        }
        
        @Override
        public String toString() { return basic; }
    }
}
