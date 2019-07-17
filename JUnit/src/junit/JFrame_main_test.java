/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package junit;
import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.assertTrue;
/**
 *
 * @author First
 */
public class JFrame_main_test {
    int field_x;
    int field_y;

    @Before
    public void init(){
        this.field_x=10;
        this.field_y=10;
    }
    @Test
    public void calculate_value_test(){
        JFrame_main frame=new JFrame_main();
        assertTrue("this is test",(frame.calculate()==20));
        //assertTrue("this is test",false);
    }

}
