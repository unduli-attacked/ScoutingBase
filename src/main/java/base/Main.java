package base;

import base.lib.SheetsFunctions;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
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
