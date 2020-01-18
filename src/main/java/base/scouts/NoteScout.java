package base.scouts;

import base.Match;
import base.Session;

import java.util.ArrayList;
import java.util.HashMap;

public class NoteScout {
    String name;
    public ArrayList<Match> matches;
    ArrayList<HashMap<String, Object>> matchData;
    ArrayList<Integer> matchesScouted;
    
    public NoteScout(String name_){
        this.name = name_;
    }
    
    public String getName() {
        return this.name;
    }
    
    /**
     * adds a match of notes to the note scout. does NOT add it to any Match
     * @param match_  a NOTE SCOUT match hashmap
     */
    public void addMatch(HashMap<String, Object> match_){
        this.matchesScouted.add((int)match_.get("matchNum"));
        
        //sanitize input
        String str = (String) match_.get("bigNotes");
        str = str.replaceAll("\n", "  ..  ");
        
        HashMap<String, Object> temp = new HashMap<>();
        temp.putAll(match_);
        temp.put("bigNotes", str);
    
        this.matchData.add(temp);
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
