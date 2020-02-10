package base.models;

import base.Main;
import base.lib.DataClasses.*;

import java.util.ArrayList;

public class DataScout implements Comparable, Saveable{
    String name;
    double rank; //FIXME this is currently calculated the dumb way. what if it wasnt
    public ArrayList<Match> matches =  new ArrayList<>();
    public ArrayList<DataScoutMatch> matchData = new ArrayList<>();
    public ArrayList<Integer> matchesScouted = new ArrayList<>();
    
    public DataScout(String name_){
        this.name = name_;
        Main.currentSession.standScouts.put(this.name, this);
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getRank() {
        return this.rank;
    }
    
    /**
     * adds a match to the data scout. does not add to any Match
     * @param match_  a DATA SCOUT data class
     */
    public void addMatch(DataScoutMatch match_){
        this.matchData.add(match_);
        this.matchesScouted.add((int)match_.matchNum);
    }
    
    public DataScoutMatch submitMatch(int matchNum_){
        if(!matchesScouted.contains(matchNum_)){
            return null;
        }
        for(DataScoutMatch match_ : matchData){
            if((int)match_.matchNum==matchNum_){
                return match_;
            }
        }
        return null;
    }
    
    
    @Override
    public int compareTo(Object o) {
        int compareRank = (int)Math.floor(((DataScout)o).getRank());
        int rank = (int)Math.floor(this.getRank());
        
        return compareRank - rank;
        
    }
    
    public void setRank(double rank_){
        this.rank = rank_;
    }
    
    public void calculateRank(String key_, boolean wasCorrect){ //FIXME implement citrus's code
        if(wasCorrect){
            this.rank+=1;
        }else{
            switch (key_){
                case "matchNum":
                    this.rank-=2;
                    break;
                case "allPos":
                    this.rank-=2;
                    break;
                case "teamNum":
                    this.rank-=4;
                    break;
                case "absent":
                    this.rank-=5;
                    break;
                case "redCard":
                    this.rank-=3;
                    break;
                case "yellowCard":
                    this.rank-=2;
                    break;
                case "climb":
                    this.rank-=1;
                    break;
                case "totalRP":
                    this.rank-=1;
                    break;
                default:
                        break;
            }
        }
    }
    
    /**
     * Used in saving raw data
     *
     * @return the file name WITHOUT directory or extension (ex. M14Blue1)
     */
    @Override
    public String getFileName() {
        return "DATA_"+this.getName();
    }
    
    /**
     * Used in saving raw data
     *
     * @return the raw directory WITHOUT slashes (ex. rawMatches)
     */
    @Override
    public String getRawDirName() {
        return "rawScouts";
    }
}
