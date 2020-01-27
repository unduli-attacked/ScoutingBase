package base.lib;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import base.Main;
import base.Pit;
import base.lib.DataClasses.*;

public class Functions {
    /**
     *
     * @param shots_ list of shots from one scout
     * @param timeStamp_ timestamp of desired shot
     * @param margin margin of error on timestamp IN SECONDS
     * @return desired shot
     */
    public static Shot findShot(ArrayList<Shot> shots_, LocalTime timeStamp_, double margin){
        for(Shot shot_ : shots_){
            if(shot_.timeStamp.equals(timeStamp_) ||
                    (shot_.timeStamp.isAfter(timeStamp_.minusSeconds((long) margin))&&shot_.timeStamp.isBefore(timeStamp_.plusSeconds((long) margin)))){
                return shot_;
            }
        }
//        ReportFunctions.report("Shot at "+timeStamp_.format(DateTimeFormatter.ISO_LOCAL_TIME)+" not found.");
        return null;
    }
    
    public static Pit findPit(int teamNum_){
        for(Pit pit : Main.currentSession.pits){
            if(pit.teamNum == teamNum_){
                return pit;
            }
        }
        return null;
    }
}
