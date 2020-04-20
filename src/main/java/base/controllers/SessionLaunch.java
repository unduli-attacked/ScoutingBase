package base.controllers;

import base.Main;
import base.lib.FxFunctions;
import base.threads.TBACollectionThread;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class SessionLaunch extends Application implements ControlInterface {
    @FXML
    Label sessionName;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        // Create the Scene
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/fxml/SessionLaunch.fxml")));
        
        primaryStage.setTitle("BREAD 2020 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void initialize() {
        sessionName.setText(Main.currentSession.eventName);
    }
    
    @FXML
    public void handleBeginSync() {
        Main.tbaCollectionThread = new TBACollectionThread();
        Main.tbaCollectionThread.start();
        Main.tbaIsSync = true;
    }
    
    @FXML
    public void handleBeginCollation() {
        //TODO maybe sync here?
        Main.matchCollationThread.start();
        
        Main.pitCollectionThread.start();
        
        Main.teamCollationThread.start();
    }
    
    @FXML
    public void handleBeginData() {
        Main.dataCollectionThread.start();
    }
    
    @FXML
    public void handleReturnEntry(ActionEvent event) {
        try {
            FxFunctions.changePage(new Entry(), event);
        } catch (Exception e) {
            FxFunctions.pageChangeFail(e, "handleReturnEntry");
        }
    }
    
    @FXML
    public void handleTeamSearch(ActionEvent event) {
        try {
            FxFunctions.changePage(new TeamSearch(), event);
        } catch (Exception e) {
            FxFunctions.pageChangeFail(e, "handleTeamSearch");
        }
    }
}
