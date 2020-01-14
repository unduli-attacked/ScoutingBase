package base.lib;

import java.awt.*;
import base.lib.Enums.*;

public class DataClasses {
    public class Shot{
        public Point position;
        public Goal scored;
        public Shot(Point position_, Goal goal_){
            this.position = position_;
            this.scored = goal_;
        }
    }
}
