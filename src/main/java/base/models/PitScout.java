package base.models;

import base.Main;

import java.util.ArrayList;

public class PitScout{
    String name;
    String scoutID;
    ArrayList<Pit> pits;
    public ArrayList<Integer> teamsAssigned;
    ArrayList<Integer> teamsScouted;
    
    
    public PitScout(String name_, String scoutID_) {
        this.name = name_;
        this.scoutID = scoutID_;
        Main.currentSession.pitScouts.put(this.scoutID, this);
    }
    
    public String getID() {
        return scoutID;
    }
    
    /**
     * add new teams to the existing list assigned
     * @param teams_ a list of team numbers to add
     */
    public void addTeams(ArrayList<Integer> teams_){
        for(int team_ : teams_){
            if(!this.teamsAssigned.contains(team_)){
                this.teamsAssigned.add(team_);
            }
        }
    }
    
    public void addPit(Pit pit_){
        this.teamsScouted.add(pit_.teamNum);
        this.pits.add(pit_);
    }
    
    
}
