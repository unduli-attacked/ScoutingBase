package base.threads;

import base.Main;
import base.lib.Functions;
import base.models.BaseMatch;
import base.models.BasePit;
import base.models.SecondPit;
import base.models.BaseTeam;

import java.util.ArrayList;

public class TeamCollationThread extends Thread{
    
    @Override
    public void start(){
    
    }
    
    @Override
    public void run(){
        for(BaseTeam t : Main.currentSession.teams.values()){
            ArrayList<BaseMatch> tempBaseMatches = Functions.findMatch(t.teamNum);
            for(BaseMatch m : tempBaseMatches){
                if(!t.matchesScouted.contains(m)){
                    t.addMatch(m);
                }
            }
            
            BasePit tempBasePit = Functions.findPit(t.teamNum);
            if(!t.mainBasePit.equals(tempBasePit)){
                t.addPit(tempBasePit);
            }
            
            ArrayList<SecondPit> secondPits = (ArrayList<SecondPit>) tempBasePit.secondPits.values();
            for(SecondPit p : secondPits){
                if(!t.secondPits.contains(p)){
                    t.addSecondPit(p);
                }
            }
        }
    }
    
}
