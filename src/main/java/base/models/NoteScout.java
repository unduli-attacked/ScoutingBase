package base.models;

import base.Main;
import base.lib.DataClasses.*;

import java.util.ArrayList;

public class NoteScout {
    public String name;
    public ArrayList<Match> matches = new ArrayList<>();
    public ArrayList<NoteScoutMatch> matchData = new ArrayList<>();
    public ArrayList<Integer> matchesScouted = new ArrayList<>();
    
    public NoteScout(String name_){
        this.name = name_;
        Main.currentSession.noteScouts.put(this.name, this);
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
