package base.controllers;

import base.lib.Functions;
import base.lib.FxFunctions;
import base.models.BaseTeam;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class TeamSearch extends Application implements ControlInterface {
    
    @FXML
    TextField teamNum;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        // Create the Scene
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/fxml/TeamSearch.fxml")));
        primaryStage.setTitle("BREAD 2020 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @FXML
    public void handleReturnLaunch(ActionEvent event){
        try {
            FxFunctions.changePage(new SessionLaunch(), event);
        } catch (Exception e) {
            FxFunctions.pageChangeFail(e, "handleReturnLaunch");
        }
    }
    
    @FXML
    public void handleSearch(){
        String teamNumber = (String)teamNum.getCharacters();
        try{
            BaseTeam t = Functions.findTeam(Integer.valueOf(teamNumber));
            //TODO team page stuff
        }catch(NumberFormatException e){
            teamNum.setText("INVALID");
        }
    }
}
