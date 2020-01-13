package base;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {
    public static String eventName = "";
    
    @Override
    public void start(Stage stage) {
        //Creating a Group object
        Parent root = new VBox();
        
        //Creating a scene object
        Scene scene = new Scene(root, 600, 300);
        
        //Setting title to the Stage
        stage.setTitle("BREAD Scouting Base");
        
        //Adding scene to the stage
        stage.setScene(scene);
        
        //Displaying the contents of the stage
        stage.show();
    }
    public static void main(String args[]){
        Session testSession = new Session(2019, "Sac", "2019cada", "");
        System.out.println(testSession.tbaEventKey);
        launch(args);
    }
}
