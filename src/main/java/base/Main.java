package base;

import base.controllers.ControlInterface;
import base.lib.SheetsFunctions;
import base.models.Session;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application implements ControlInterface {
    public static String eventName = "";
    public static Session currentSession;
    public static ArrayList<Session> recoveredSessions;
    
    @Override
    public void start(Stage stage) throws IOException {
        VBox root = (VBox) new FXMLLoader().load(new FileInputStream("src/main/resources/layouts/Entry.fxml"));
    
        // Create the Scene
        Scene scene = new Scene(root);
        
        stage.setTitle("BREAD 2020 Scouting Base");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String args[]){
        launch();
    }
    
    public Main(){}
}
