package base.lib;

import base.Main;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;

public class ReportFunctions {
    public static void report(Object toReport){
        System.out.println(toReport.toString());
    
        FileWriter writer = null;
        try{
            writer = new FileWriter("/main_storage/Log_"+ Main.eventName+".csv", true);
        }catch(IOException e){
            System.out.println("File not found error in report");
            System.out.println(e);
        }
        
        try{
            writer.write(Instant.now().toString()+","+toReport.toString()); //TODO check that date tostring works
        }catch(IOException e){
            System.out.println("Write error in report");
            System.out.println(e);
        }
    }
    
//    public static void reportMatchDataError(Object toReport, int matchNum, Enums.MatchData.Station alliancePos){
//        System.out.println(toReport.toString()+" in match "+matchNum+" alliance station "+alliancePos.toString());
//
//        FileWriter writer = null;
//        try{
//            writer = new FileWriter("/main_storage/MatchDataLog_"+ Main.eventName+".csv", true);
//        }catch(IOException e){
//            System.out.println("File not found error in report");
//            System.out.println(e);
//        }
//
//        try{
//            writer.write(Instant.now().toString()+","+toReport.toString()+","+matchNum+","+alliancePos.toString()); //TODO check that date tostring works
//        }catch(IOException e){
//            System.out.println("Write error in report");
//            System.out.println(e);
//        }
//    }
}
