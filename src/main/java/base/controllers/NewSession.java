package base.controllers;

import base.Main;
import base.lib.FxFunctions;
import base.models.Session;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class NewSession extends Application implements ControlInterface{
    
    Stage stage;
    
    @FXML
    TextField year;
    @FXML TextField name;
    @FXML TextField dir;
    @FXML TextField eventKey;
    @FXML TextField numData;
    @FXML TextField numNote;
    @FXML TextField sheetID;
    @FXML TextField mainPitTab;
    @FXML TextField secondPitTab;
    @FXML TextField finalMainCol;
    @FXML TextField finalSecondCol;
    @FXML TextField dataTab;
    @FXML TextField finalDataCol;
    @FXML TextField noteTab;
    @FXML TextField finalNoteCol;
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        VBox root = (VBox) new FXMLLoader().load(new FileInputStream("src/main/resources/layouts/LAYOUT.fxml"));
        
        // Create the Scene
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("BREAD 2020 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        this.stage = primaryStage;
    }
    
    @Override
    public void initialize() {
    
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
    public void handleSubmit() throws IOException {
        //validate
        int year_, numData_, numNote_;
        String dir_;
        try {
            year_ = Integer.valueOf((String) year.getCharacters());
        }catch (NumberFormatException e){
            year.setText("INVALID");
            return;
        }
        
        try{
            numData_ = Integer.valueOf((String) numData.getCharacters());
        }catch (NumberFormatException e){
            numData.setText("INVALID");
            return;
        }
        
        try{
            numNote_ = Integer.valueOf((String) numData.getCharacters());
        }catch (NumberFormatException e){
            numData.setText("INVALID");
            return;
        }
        
        dir_ = (String) dir.getCharacters();
        if(dir_.charAt(dir_.length()-1) != '/'){
            if(dir_.charAt(dir_.length()-1) == '\\'){
                dir_ = dir_.substring(0, dir_.length()-1);
            }
            dir_=dir_+"/";
        }
        Session tempSesh = new Session(year_, (String) name.getCharacters(), (String) eventKey.getCharacters(), dir_, numData_, numNote_);
        tempSesh.setSheet((String) sheetID.getCharacters(), (String) mainPitTab.getCharacters(), (String) secondPitTab.getCharacters(),
                (String) dataTab.getCharacters(), (String) noteTab.getCharacters(), (String) finalMainCol.getCharacters(), (String) finalSecondCol.getCharacters(),
                (String) finalDataCol.getCharacters(), (String) finalNoteCol.getCharacters());
        Main.recoveredSessions.add(tempSesh);
        tempSesh.saveSession();
    
        try {
            FxFunctions.changePage(new Entry(), this);
        } catch (Exception e) {
            System.out.println("Page change failed at handleSubmit.");
            e.printStackTrace();
        }
    }
    
    @Override
    public Stage getStage() {
        return this.stage;
    }
}
