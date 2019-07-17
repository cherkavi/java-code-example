package moved_wrap;
import javafx.scene.shape.*;
import javafx.scene.input.MouseEvent;

public class WrapCircle extends Circle{
    var deltaX:Float;
    var deltaY:Float;

    override def onMousePressed=function(evt:MouseEvent){
        deltaX=this.centerX-evt.x;
        deltaY=this.centerY-evt.y;
    }

    override def onMouseDragged=function(evt:MouseEvent){
        this.centerX=deltaX+evt.x;
        this.centerY=deltaY+evt.y;
    }
    
}
