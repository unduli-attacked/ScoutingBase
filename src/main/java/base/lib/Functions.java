package base.lib;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import base.Main;
import base.models.*;
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
        float floatStamp = getFloatTime(timeStamp_);
        for(Shot shot_ : shots_){
            float floatShot = getFloatTime(shot_.timeStamp);
            if(floatShot==floatStamp || (Math.abs(floatStamp-floatShot)<=margin)){
                return shot_;
            }
            
        }
        return null;
    }
    
    /**
     * finds a Team for a session based on their number
     * @param teamNum_ the team number
     * @param session_ the session to search in
     * @return the correct team, or null if it doesn't exist
     */
    public static Team findTeam(int teamNum_, Session session_){
        for(Team team_ : session_.teams.values()){
            if(team_.teamNum==teamNum_){
                return team_;
            }
        }
        return null;
    }
    /**
     * finds a Team for the current Session based on their number
     * @param teamNum_ the team number
     * @return the correct team, or null if it doesn't exist
     */
    public static Team findTeam(int teamNum_){
        return findTeam(teamNum_, Main.currentSession);
    }
    
    /**
     * finds an existing Pit data class for a team
     * @param teamNum_ the team number
     * @param session_ the session to search in
     * @return the team's pit, or null if not found
     */
    public static Pit findPit(int teamNum_, Session session_){
        for(Pit pit : session_.pits.values()){
            if(pit.teamNum == teamNum_){
                return pit;
            }
        }
        return null;
    }
    /**
     * finds an existing Pit data class for a team in the current session
     * @param teamNum_ the team number
     * @return the team's pit, or null if not found
     */
    public static Pit findPit(int teamNum_){
        return findPit(teamNum_, Main.currentSession);
    }
    
    /**
     * finds existing Match data classes for a team
     * @param teamNum_ the team number
     * @param session_ the session to search in
     * @return the team's matches, or null if none found
     */
    public static ArrayList<Match> findMatch(int teamNum_, Session session_){
        ArrayList<Match> temp = new ArrayList<>();
        for(Match match : session_.matches.values()){
            if(match.teamNum == teamNum_){
                temp.add(match);
            }
        }
        return temp;
    }
    /**
     * finds existing Match data classes for a team in teh current session
     * @param teamNum_ the team number
     * @return the team's matches, or null if none found
     */
    public static ArrayList<Match> findMatch(int teamNum_){
        return findMatch(teamNum_, Main.currentSession);
    }
    
    /**
     * compares two local times
     * @param first later time
     * @param second earlier time
     * @return the difference (first - second)
     */
    public static float compareLocalTime(LocalTime first, LocalTime second){
        return (first.getSecond()+first.getMinute()*60)-(second.getSecond()+second.getMinute()*60);
    }
    
    /**
     * converts a LocalTime to a float of seconds. IGNORES HOURS
     * @param obTime LocalTime time
     * @return
     */
    public static float getFloatTime(LocalTime obTime){
        float temp = obTime.getSecond()+obTime.getMinute()*60;
        return temp;
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
    
    /**
     * get the sum of an array list of floats
     * @param ls the arraylist
     * @return the sum of the items in the arraylist
     */
    public static float getSum(ArrayList<Float> ls){
        float sum = 0;
        for(float fl : ls){
            sum += fl;
        }
        return sum;
        
    }
    
    /**
     * turns a List of Doubles into an Array of doubles
     * @param ls a List of Doubles
     * @return the same list as a double[]
     */
    public static double[] listToArray(List<Double> ls){
        //FIXME i know, i know, it makes me want to commit scooter ankle too
        double[] dbl = new double[ls.size()];
        for(int i=0; i<ls.size(); i++){
            dbl[i] = ls.get(i);
        }
        return dbl;
    }
}
