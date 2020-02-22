package base.controllers;

import base.Main;
import base.lib.FxFunctions;
import base.threads.MatchCollationThread;
import base.threads.PitCollectionThread;
import base.threads.TBACollectionThread;
import base.threads.TeamCollationThread;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class SessionLaunch extends Application implements ControlInterface{
    @FXML Label sessionName;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        // Create the Scene
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/fxml/SessionLaunch.fxml")));
        
        primaryStage.setTitle("BREAD 2020 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void initialize(){
        sessionName.setText(Main.currentSession.eventName);
    }
    
    @FXML
    public void handleBeginSync(){
        Main.tbaCollectionThread = new TBACollectionThread();
        Main.tbaCollectionThread.start();
        Main.tbaIsSync = true;
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
    public void handleReturnEntry(ActionEvent event){
        try {
            FxFunctions.changePage(new Entry(), event);
        } catch (Exception e) {
            FxFunctions.pageChangeFail(e, "handleRE");
        }
    }
    
    @FXML
    public void handleTeamSearch(ActionEvent event){
        try {
            FxFunctions.changePage(new TeamSearch(), event);
        } catch (Exception e) {
            FxFunctions.pageChangeFail(e, "handleTeamSearch");
        }
    }
}
