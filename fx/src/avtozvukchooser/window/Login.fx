package avtozvukchooser.window;

import javafx.scene.*;
import javafx.ext.swing.SwingButton;
import javafx.scene.shape.*;
import avtozvukchooser.Action;
import javafx.scene.input.MouseEvent;
import moved_wrap.*;
import javafx.stage.Alert;


/**  */
public class Login extends CustomNode{
    public var action:Action;
   
    var button=SwingButton{
        text: "touch me"
        layoutX:50;
        layoutY:20;
        onMouseClicked:function(evt:MouseEvent){
            println("goto workplace {this.action}");
            if(Alert.confirm("Уверены в переходе ?")){
                this.action.setCurrent("Workplace");
            }else{
            }
        }
    }

    var circle=WrapCircle{
        centerX: 100;
        centerY: 100;
        radius: 50;
    }


    override function create():Node{
        return Group{
            content:[
            	button,
            	circle
            ]
        }
    }
    
    
}