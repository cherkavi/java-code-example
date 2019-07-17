/*
 * Stage.fx
 *
 * Created on 30.10.2009, 13:21:38
 */

package avtozvukchooser.window;

import javafx.scene.*;
import javafx.ext.swing.*;
import avtozvukchooser.Action;

public class Workplace extends CustomNode{
    public var mainAction:Action;

    var button=SwingButton{
        text:"click me"
        action:function(){
            mainAction.setCurrent("Login");
        }
    }

    override function create():Node{
        return Group{
            content: [button]
        }
    }
}

