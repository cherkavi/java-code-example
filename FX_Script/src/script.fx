import javafx.ui.*;
import javafx.ui.canvas.*;
import javafx.ui.filter.*;

Frame
{
   width: 800
   height: 400
   content: Canvas
   {
      content: Text
      {
         x: 1
         y: 1
         content: "{msg:<<java.lang.String>>}"
         fill: LinearGradient
         {
            x1: 0, y1: 0, x2: 0, y2: 1

            stops: 
            [
               Stop
               {
                  offset: 0
                  color: blue
               },
            ]
         }

         filter: [MotionBlur { distance: 10.5 }, Glow {amount: 0.15},
                  Noise {monochrome: false, distribution: 0}]
      }
   }
   visible: true
}