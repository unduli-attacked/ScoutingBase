package base.threads;

import base.Main;
import base.lib.Functions;
import base.models.Match;
import base.models.Pit;
import base.models.SecondPit;
import base.models.Team;

import java.util.ArrayList;

public class TeamCollationThread extends Thread{
    
    @Override
    public void start(){
    
    }
    
    @Override
    public void run(){
        for(Team t : Main.currentSession.teams.values()){
            ArrayList<Match> tempMatches = Functions.findMatch(t.teamNum);
            for(Match m : tempMatches){
                if(!t.matchesScouted.contains(m)){
                    t.addMatch(m);
                }
            }
            
            Pit tempPit = Functions.findPit(t.teamNum);
            if(!t.mainPit.equals(tempPit)){
                t.addPit(tempPit);
            }
            
            ArrayList<SecondPit> secondPits = (ArrayList<SecondPit>)tempPit.secondPits.values();
            for(SecondPit p : secondPits){
                if(!t.secondPits.contains(p)){
                    t.addSecondPit(p);
                }
            }
        }
    }
    
}
