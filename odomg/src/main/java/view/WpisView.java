/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class WpisView implements Serializable {
    private static final long serialVersionUID = 1L;

    private String rokWpisu;
    private String rokUprzedni;
    private String rokNastepny;
    private String miesiacWpisu;
    private String miesiacOd;
    private String miesiacDo;
 
    

    public WpisView() {
        }
    

    @PostConstruct
    public void init() { //E.m(this);
        rokWpisu="2020";
        miesiacWpisu="12";
    }
    

  }
