package base.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public interface ControlInterface {
    /**
     *
     * @param stage the default start stage
     * @param resource_ the file name for the layout (ex. "template.fxml")
     * @return the stage
     * @throws IOException if something bad happens
     */
    default Stage activate(Stage stage, VBox resource_) throws IOException {
        Scene scene = new Scene(resource_);
    
        stage.setTitle("BREAD 2020 Scouting Base");
        stage.setScene(scene);
        stage.show();
        return stage;
    }
    
    default void initialize(){
    
    }
    
    Stage getStage();
}
