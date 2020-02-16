package base.lib;

import base.controllers.ControlInterface;
import javafx.application.Application;
import javafx.stage.Stage;

public class FxFunctions {
    
    public static <T extends Application & ControlInterface> void changePage(T to, T from) throws Exception {
        to.start(new Stage());
        from.getStage().close();
    }
}
