package base.scouts;

import base.Match;
import base.Session;

import javax.xml.crypto.Data;
import java.util.ArrayList;
import java.util.HashMap;

public class DataScout implements Comparable{
    String name;
    double rank; //FIXME this is currently calculated the dumb way. what if it wasnt
    public ArrayList<Match> matches;
    public ArrayList<HashMap<String, Object>> matchData;
    public ArrayList<Integer> matchesScouted;
    
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
    
    
    @Override
    public int compareTo(Object o) {
        int compareRank = (int)Math.floor(((DataScout)o).getRank());
        int rank = (int)Math.floor(this.getRank());
        //FIXME this needs to be tested idk what im do
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
}
