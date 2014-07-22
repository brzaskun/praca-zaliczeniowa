/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Osito
 */
public class DokTest {
    
    public DokTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getId method, of class Dok.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Dok instance = new Dok();
        instance.setId(1);
        Integer expResult = 1;
        Integer result = instance.getId();
        assertEquals(expResult, result);
    }

    /**
     * Test of setId method, of class Dok.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        Integer id = null;
        Dok instance = new Dok();
        instance.setId(id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNazwa method, of class Dok.
     */
    @Test
    public void testGetNazwa() {
        System.out.println("getNazwa");
        Dok instance = new Dok();
        String expResult = "";
        String result = instance.getNazwa();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setNazwa method, of class Dok.
     */
    @Test
    public void testSetNazwa() {
        System.out.println("setNazwa");
        String nazwa = "";
        Dok instance = new Dok();
        instance.setNazwa(nazwa);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
