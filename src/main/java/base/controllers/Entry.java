package base.controllers;

import base.Main;
import base.lib.FxFunctions;
import base.models.Session;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Entry extends Application implements ControlInterface{
    @FXML
    ChoiceBox<Session> sessionSelect;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        System.out.println("breakpoint");
        // Create the Scene
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/fxml/Entry.fxml")));
    
        primaryStage.setTitle("BREAD 2020 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println("breakpoint");
    }
    
    @Override
    public void initialize(){
        if(!Main.recoveredSessions.isEmpty()) {
            sessionSelect.getItems().addAll(Main.recoveredSessions);
        }
    }
    
    
    @FXML
    public void handleNewSession(ActionEvent event){
        try {
            FxFunctions.changePage(new NewSession(), event);
        } catch (Exception e) {
            FxFunctions.pageChangeFail(e, "handleNewSession");
        }
    }
    
    @FXML
    public void handleGo(ActionEvent event){
        Main.currentSession = (Session) sessionSelect.getValue();
        try{
            FxFunctions.changePage(new SessionLaunch(), event);
        }catch(Exception e){
            FxFunctions.pageChangeFail(e, "handleGo");
        }
    }
    
}
