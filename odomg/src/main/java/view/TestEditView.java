/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.TestFacade;
import dao.TestWykazFacade;
import entity.Test;
import entity.Testwykaz;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.event.ReorderEvent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class TestEditView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private TestFacade testFacade;
    @Inject
    private TestWykazFacade testWykazFacade;
    @Inject
    private Test test;
    private List<Test> testlista;
    private List<Test> testlistaselected;
    private Testwykaz testwykaz;
    private List<Testwykaz> testwykazlista;
    

    @PostConstruct
    private void init() {
        testwykazlista = testWykazFacade.findAll();
    }
    
     public void dodaj() {
        if (test!=null&&test.getTresc()!=null) {
            try {
                testFacade.create(test);
                test=new Test();
                Msg.msg("Dodano nowy slajd testu");
            } catch (Exception e) {
                Msg.msg("e","Wystąpił błąd, nie dodano nowego slajdu testu");
            }
        }
     }
    
     public void pobierzslajdy() {
        if (testwykaz!=null) {
            testlista  = testFacade.findBynazwa(testwykaz.getNazwa());
            System.out.println("");
        }
     }
     
     public void onRowReorder(ReorderEvent event) {
        int lp = 1;
        for (Test p : testlista) {
            p.setId(lp++);
        }
        Msg.msg("Zmieniono kolejność slajdu");
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public List<Test> getTestlista() {
        return testlista;
    }

    public void setTestlista(List<Test> testlista) {
        this.testlista = testlista;
    }

    public List<Test> getTestlistaselected() {
        return testlistaselected;
    }

    public void setTestlistaselected(List<Test> testlistaselected) {
        this.testlistaselected = testlistaselected;
    }

    public Testwykaz getTestwykaz() {
        return testwykaz;
    }

    public void setTestwykaz(Testwykaz testwykaz) {
        this.testwykaz = testwykaz;
    }

    public List<Testwykaz> getTestwykazlista() {
        return testwykazlista;
    }

    public void setTestwykazlista(List<Testwykaz> testwykazlista) {
        this.testwykazlista = testwykazlista;
    }
     
   
    
    
}
