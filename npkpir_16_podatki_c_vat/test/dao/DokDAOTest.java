/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import java.util.List;
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
public class DokDAOTest {
    
    public DokDAOTest() {
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
     * Test of getDownloadedDok method, of class DokDAO.
     */
    @Test
    public void testGetDownloadedDok() {
        System.out.println("getDownloadedDok");
        DokDAO instance = new DokDAO();
        List expResult = null;
        List result = instance.getDownloadedDok();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setDownloadedDok method, of class DokDAO.
     */
    @Test
    public void testSetDownloadedDok() {
        System.out.println("setDownloadedDok");
        List<Dok> downloadedDok = null;
        DokDAO instance = new DokDAO();
        instance.setDownloadedDok(downloadedDok);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of init method, of class DokDAO.
     */
    @Test
    public void testInit() {
        System.out.println("init");
        DokDAO instance = new DokDAO();
        instance.init();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refresh method, of class DokDAO.
     */
    @Test
    public void testRefresh() {
        System.out.println("refresh");
        DokDAO instance = new DokDAO();
        instance.refresh();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of dodajNowyWpis method, of class DokDAO.
     */
    @Test
    public void testDodajNowyWpis() {
        System.out.println("dodajNowyWpis");
        Dok selDokument = null;
        DokDAO instance = new DokDAO();
        instance.dodajNowyWpis(selDokument);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of destroy method, of class DokDAO.
     */
    @Test
    public void testDestroy() {
        System.out.println("destroy");
        Dok selDokument = null;
        DokDAO instance = new DokDAO();
        instance.destroy(selDokument);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of edit method, of class DokDAO.
     */
    @Test
    public void testEdit() {
        System.out.println("edit");
        Dok selDokument = null;
        DokDAO instance = new DokDAO();
        instance.edit(selDokument);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
