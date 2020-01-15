package base.scouts;

import base.Session;

import java.util.ArrayList;
import java.util.HashMap;

public class PitScout{
    String name;
//    ArrayList<Pit> pits;
    public ArrayList<Integer> teamsAssigned;
    ArrayList<Integer> teamsScouted;
    
    
    public PitScout(String name_){
        this.name = name_;
    }
    
    public String getName() {
        return name;
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
    
    public boolean addPit(HashMap<String, Object> pit_){
        this.teamsScouted.add((int)pit_.get("teamNum"));
        //TODO send to Pit
        return true;
    }
    
    
}
