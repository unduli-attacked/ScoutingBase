package base.scouts;

import base.Match;
import base.Session;

import java.util.ArrayList;
import java.util.HashMap;

public class DataScout {
    String name;
    double rank;
    public ArrayList<Match> matches;
    ArrayList<HashMap<String, Object>> matchData;
    ArrayList<Integer> matchesScouted;
    
    public DataScout(String name_){
        this.name = name_;
    }
    
    public String getName() {
        return this.name;
    }
    
    public double getRank() {
        return this.rank;
    }
    
    /**
     * adds a match to the data scout. does not add to any Match
     * @param match_  a DATA SCOUT hashmap
     */
    public void addMatch(HashMap<String, Object> match_){
        this.matchData.add(match_);
        this.matchesScouted.add((int)match_.get("matchNum"));
    }
    
    public HashMap<String, Object> submitMatch(int matchNum_){
        if(!matchesScouted.contains(matchNum_)){
            return null;
        }
        for(HashMap match_ : matchData){
            if((int)match_.get("matchNum")==matchNum_){
                return match_;
            }
        }
        return null;
    }
}
