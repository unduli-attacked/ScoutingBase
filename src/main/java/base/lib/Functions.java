package base.lib;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import base.Main;
import base.Pit;
import base.lib.DataClasses.*;
import base.scouts.PitScout;

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
    
    /**
     * finds an existing Pit data class for a team
     * @param teamNum_ the team number
     * @return the team's pit, or null if not found
     */
    public static Pit findPit(int teamNum_){
        for(Pit pit : Main.currentSession.pits){
            if(pit.teamNum == teamNum_){
                return pit;
            }
        }
        return null;
    }
    
    public static float compareLocalTime(LocalTime first, LocalTime second){
        return (first.getSecond()+first.getMinute()*60)-(second.getSecond()+second.getMinute()*60);
    }
    
    /**
     * converts a LocalTime to a float of seconds. IGNORES HOURS
     * @param obTime LocalTime time
     * @return
     */
    public static float getFloatTime(LocalTime obTime){
        return obTime.getSecond()+obTime.getMinute()*60;
    }
    
    /**
     * converts a float of seconds to a LocalTime
     * @param seconds the desired time in seconds
     * @return the LocalTime with minutes and seconds
     */
    public static LocalTime getLocalTime(Float seconds){
        int mins = (int)Math.floor(seconds/60);
        int secs = (int)Math.floor(seconds % 60);
        
        return LocalTime.of(0, mins, secs);
    }
    
    /**
     *
     * @param array the 2d array you want to find the min of
     * @return [0][0] = min value, [1][0] = first index, [1][1] = second index
     */
    public static Float[][] getMin(Float[][] array){
        float min = array[0][0];
        int y = 0;
        int x = 0;
        for (int i = 0; i <array.length;i++){
            for (int k=0;k < array[i].length;k++){
                if (array[i][k] < min) {
                    min = array[i][k];
                    y = i;
                    x=k;
                }
            }
        }
        return new Float[][]{{min},{(float)y,(float)x}};
    }
}
