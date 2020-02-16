package base.controllers;

import base.Main;
import base.lib.FxFunctions;
import base.threads.MatchCollationThread;
import base.threads.PitCollectionThread;
import base.threads.TeamCollationThread;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SessionLaunch extends Application implements ControlInterface{
    
    Stage stage;
    @FXML Label sessionName;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        VBox root = (VBox) new FXMLLoader().load(new FileInputStream("src/main/resources/layouts/SessionLaunch.fxml"));
        
        // Create the Scene
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("BREAD 2020 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.stage = primaryStage;
    }
    
    @Override
    public void initialize(){
        sessionName.setText(Main.currentSession.eventName);
    }
    
    @FXML
    public void handleBeginSync(){
        //TODO TBA sync thread
    }
    
    @FXML
    public void handleBeginCollation(){
        
        Main.matchCollationThread = new MatchCollationThread();
        Main.matchCollationThread.start();
    
        Main.pitCollectionThread = new PitCollectionThread();
        Main.pitCollectionThread.start();
        
        Main.teamCollationThread = new TeamCollationThread();
        Main.teamCollationThread.start();
    }
    
    @FXML
    public void handleReturnEntry(){
        try {
            FxFunctions.changePage(new Entry(), this);
        } catch (Exception e) {
            System.out.println("Page change failed at handleReturnEntry.");
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleTeamSearch(){
        try {
            FxFunctions.changePage(new TeamSearch(), this);
        } catch (Exception e) {
            System.out.println("Page change failed at handleTeamSearch");
            e.printStackTrace();
        }
    }
    
    @Override
    public Stage getStage() {
        return this.stage;
    }
}
