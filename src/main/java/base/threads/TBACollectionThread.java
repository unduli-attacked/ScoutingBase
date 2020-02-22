package base.threads;

import base.Main;
import base.models.Match;
import com.cpjd.models.events.Event;

import java.util.ArrayList;
import java.util.HashMap;

public class TBACollectionThread extends Thread{
    
    
    @Override
    public void run(){
        for(Match match_ : Main.currentSession.matches.values()){
            HashMap<String, Object> breakdown;
            if(match_.allPos.isBlue()) {
                breakdown = Main.tbaApi.getMatch(Main.currentSession.tbaEventKey + "_qm" + match_.matchNum).getBlueScoreBreakdown();
            }else{
                breakdown = Main.tbaApi.getMatch(Main.currentSession.tbaEventKey + "_qm" + match_.matchNum).getRedScoreBreakdown();
            }
            if(breakdown!=null){
                match_.matchBreakdown =breakdown;
            }
        }
    }
}
