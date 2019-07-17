/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import testdio.PointOfSaleTerminal;
import static org.junit.Assert.*;

/**
 *
 * @author First
 */
public class testCalculate {
    PointOfSaleTerminal terminal;
    public testCalculate() {
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

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    public void testing_terminal(){
        assertTrue(terminal.calculateTotal("ABCDABA")==13.25);
        assertTrue(terminal.calculateTotal("CCCCCCC")==6.0);
        assertTrue(terminal.calculateTotal("ABCD")==7.25);
    }
}