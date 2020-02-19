package base.lib;

import base.controllers.ControlInterface;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class FxFunctions {
    
    public static <T extends Application & ControlInterface> void changePage(T to, ActionEvent from) throws Exception {
        to.start(new Stage());
        ((Stage)((Node)from.getSource()).getScene().getWindow()).close();
    }
    
    public static void pageChangeFail(Exception e, String methodName){
        System.out.println("Page change failed at "+methodName+".");
        e.printStackTrace();
    }
}
