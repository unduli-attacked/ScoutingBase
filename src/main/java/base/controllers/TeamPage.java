package base.controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class TeamPage extends Application implements ControlInterface {
    
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Create the Scene
        Scene scene = new Scene(FXMLLoader.load(this.getClass().getResource("/fxml/TeamPage.fxml")));
        primaryStage.setTitle("BREAD 2020 Scouting Base");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
