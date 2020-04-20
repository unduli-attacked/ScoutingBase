package base.controllers;

import base.Main;
import base.lib.FxFunctions;
import base.models.Session;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class NewSession extends Application implements ControlInterface {
    
    @FXML
    TextField year;
    @FXML
    TextField name;
    @FXML
    TextField dir;
    @FXML
    TextField eventKey;
    @FXML
    TextField numData;
    @FXML
    TextField numNote;
    @FXML
    TextField sheetID;
    @FXML
    TextField mainPitTab;
    @FXML
    TextField secondPitTab;
    @FXML
    TextField finalMainCol;
    @FXML
    TextField finalSecondCol;
    @FXML
    TextField dataTab;
    @FXML
    TextField finalDataCol;
    @FXML
    TextField noteTab;
    @FXML
    TextField finalNoteCol;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        
        
        // Create the Scene
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/fxml/NewSession.fxml")));
        
        primaryStage.setTitle("BREAD 2020 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    @Override
    public void initialize() {
    
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
    public void handleSubmit(ActionEvent event) throws IOException {
        //validate
        int year_, numData_, numNote_;
        String dir_;
        try {
            year_ = Integer.valueOf(year.getCharacters().toString());
        } catch (NumberFormatException e) {
            year.setText("INVALID");
            return;
        }
        
        try {
            numData_ = Integer.valueOf(numData.getCharacters().toString());
        } catch (NumberFormatException e) {
            numData.setText("INVALID");
            return;
        }
        
        try {
            numNote_ = Integer.valueOf((numData.getCharacters().toString()));
        } catch (NumberFormatException e) {
            numData.setText("INVALID");
            return;
        }
        
        dir_ = dir.getCharacters().toString();
        if (dir_.charAt(dir_.length() - 1) != '/') {
            if (dir_.charAt(dir_.length() - 1) == '\\') {
                dir_ = dir_.substring(0, dir_.length() - 1);
            }
            dir_ = dir_ + "/";
        }
        Session tempSesh = new Session(year_, name.getCharacters().toString(), eventKey.getCharacters().toString(), dir_, numData_, numNote_);
        tempSesh.setSheet(sheetID.getCharacters().toString(), mainPitTab.getCharacters().toString(), secondPitTab.getCharacters().toString(),
                dataTab.getCharacters().toString(), noteTab.getCharacters().toString(), finalMainCol.getCharacters().toString(), finalSecondCol.getCharacters().toString(),
                finalDataCol.getCharacters().toString(), finalNoteCol.getCharacters().toString());
        Main.recoveredSessions.add(tempSesh);
        tempSesh.saveSession();
        
        try {
            FxFunctions.changePage(new Entry(), event);
        } catch (Exception e) {
            FxFunctions.pageChangeFail(e, "handleSubmit");
        }
    }
}
