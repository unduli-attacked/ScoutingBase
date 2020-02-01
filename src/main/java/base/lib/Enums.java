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
        MISS(0), LOWER(1), OUTER(2), INNER(3);
        
        int basic;
        Goal(int pts_){
            basic=pts_;
        }
        
        @Override
        public String toString(){return String.valueOf(basic);}
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
        SUCCESS(Color.CYAN, "Success"), FAILURE(Color.RED, "Failure"),
        NO_ATTEMPT(Color.GRAY, "Not Attempted"), RECOVERED(Color.PINK, "Recovered"),
        WAS_LIFTED(Color.MAGENTA, "Was Lifted"), LEVEL_TEAM(Color.BLUE, "Leveled With Multiple Bots");
        
        public Color col;
        String basic;
        Dot(Color col_, String basic_){
            this.col = col_;
            this.basic = basic_;
        }
        
        public Dot getDot(Color color){
            if(color.equals(Color.CYAN)){
                return SUCCESS;
            }else if(color.equals(Color.RED)){
                return FAILURE;
            }else if(color.equals(Color.GRAY)){
                return NO_ATTEMPT;
            }else if(color.equals(Color.PINK)){
                return RECOVERED;
            }else if(color.equals(Color.MAGENTA)){
                return WAS_LIFTED;
            }else if(color.equals(Color.BLUE)){
                return LEVEL_TEAM;
            }else{
                return null;
            }
        }
        
        @Override
        public String toString(){
            return basic;
        }
    }
}
