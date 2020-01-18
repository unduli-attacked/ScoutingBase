package base.scouts;

import base.Match;
import base.Session;
import base.lib.DataClasses.*;

import java.util.ArrayList;
import java.util.HashMap;

public class NoteScout {
    public String name;
    public ArrayList<Match> matches;
    public ArrayList<NoteScoutMatch> matchData;
    public ArrayList<Integer> matchesScouted;
    
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
    public void addMatch(NoteScoutMatch match_){
        this.matchesScouted.add(match_.matchNum);
        
        //sanitize input
        String str = match_.bigNotes;
        str = str.replaceAll("\n", "  ..  ");
        
        NoteScoutMatch temp = match_;
        temp.bigNotes = str;
    
        this.matchData.add(temp);
    }
    
    public NoteScoutMatch submitMatch(int matchNum_){
        if(!matchesScouted.contains(matchNum_)){
            return null;
        }
        for(NoteScoutMatch match_ : matchData){
            if((int)match_.matchNum==matchNum_){
                return match_;
            }
        }
        return null;
    }
}
