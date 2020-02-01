package base;

import base.lib.SheetsFunctions;
import base.models.Session;
import javafx.application.Application;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    public static String eventName = "";
    public static Session currentSession;
    public static ArrayList<Session> recoveredSessions;
    
    @Override
    public void start(Stage stage) {
        stage.setTitle("BREAD Scouting Base");
        stage.show();
    }
    public static void main(String args[]){
        currentSession = new Session(2020, "testEvent", "na", "\\testEvent\\", 2, 1);
        currentSession.spreadsheetID = "1b2jZZsnmQ71mW3oEc0Sr2sQ3aDmlQOeIyL8Uws0gnNk";
        currentSession.mainPitTab = "Main Pit Data";
        currentSession.finalMainPitCol = "AN";
        List<Object> testPull = new ArrayList<>();
        try {
            testPull = SheetsFunctions.getHeaders(currentSession.spreadsheetID, currentSession.mainPitTab, currentSession.finalMainPitCol);
        }catch(Exception e){
            System.out.println(e);
        }
        for(Object o : testPull){
            System.out.println(o);
        }
        launch(args);
    }
}
