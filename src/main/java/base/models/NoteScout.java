package base.models;

import base.Main;
import base.lib.DataClasses.*;

import java.util.ArrayList;

public class NoteScout implements Saveable{
    public String name;
    public ArrayList<Match> matches = new ArrayList<>();
    public ArrayList<NoteScoutMatch> matchData = new ArrayList<>();
    public ArrayList<Integer> matchesScouted = new ArrayList<>();
    
    public NoteScout(String name_, Session session_){
        this.name = name_;
        session_.noteScouts.put(this.name, this);
    }
    
    public NoteScout(String name_){
        new NoteScout(name_, Main.currentSession);
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
    
    /**
     * Used in saving raw data
     *
     * @return the file name WITHOUT directory or extension (ex. M14Blue1)
     */
    @Override
    public String getFileName() {
        return "NOTE_"+this.getName();
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
