/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package junit;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author First
 */
public class MainTest {

    public MainTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class Main.
     */
    @Test
    public void testJFrame_main() {
        System.out.println("Begin test");
        JFrame_main frame=new JFrame_main();
        boolean value=true;
        boolean value_other=true;
        assertTrue(frame.calculate()==20);
        System.out.println("End test");
    }

}