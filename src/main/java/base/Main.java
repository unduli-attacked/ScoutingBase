package base;

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
        launch(args);
    }
}
