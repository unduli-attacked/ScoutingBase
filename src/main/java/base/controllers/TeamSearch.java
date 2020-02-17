package base.controllers;

import base.lib.Functions;
import base.lib.FxFunctions;
import base.models.Team;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class TeamSearch extends Application implements ControlInterface {
    
    Stage stage;
    @FXML
    TextField teamNum;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        VBox root = (VBox) new FXMLLoader().load(new FileInputStream("src/main/resources/layouts/TeamSearch.fxml"));
        
        // Create the Scene
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("BREAD 2020 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show();
        this.stage = primaryStage;
    }
    
    @FXML
    public void handleReturnLaunch(){
        try {
            FxFunctions.changePage(new SessionLaunch(), this);
        } catch (Exception e) {
            System.out.println("Page change failed at handleReturnLaunch.");
            e.printStackTrace();
        }
    }
    
    @FXML
    public void handleSearch(){
        String teamNumber = (String)teamNum.getCharacters();
        try{
            Team t = Functions.findTeam(Integer.valueOf(teamNumber));
            //TODO team page stuff
        }catch(NumberFormatException e){
            teamNum.setText("INVALID");
        }
    }
    
    @Override
    public Stage getStage() {
        return this.stage;
    }
}
