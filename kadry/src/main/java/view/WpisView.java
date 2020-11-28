/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import session.SessionFacade;
import dao.UzDAO;
import entity.Firma;
import entity.Pracownik;
import entity.Uz;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
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
    @Inject
    private Uz uzer;
    private String miesiacOd;
    private String miesiacDo;
    private Firma firma;
    private Pracownik pracownik;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private SessionFacade firmaFacade;
    

    public WpisView() {
        }
    

    @PostConstruct
    private void init() { //E.m(this);
        rokWpisu="2020";
        miesiacWpisu="11";
    }
    
    public void zmienrok() {
        rokWpisu=String.valueOf(Integer.parseInt(rokWpisu)+1);
    }

    public String getRokWpisu() {
        return rokWpisu;
    }

    public void setRokWpisu(String rokWpisu) {
        this.rokWpisu = rokWpisu;
    }

    public String getRokUprzedni() {
        return rokUprzedni;
    }

    public void setRokUprzedni(String rokUprzedni) {
        this.rokUprzedni = rokUprzedni;
    }

    public String getRokNastepny() {
        return rokNastepny;
    }

    public void setRokNastepny(String rokNastepny) {
        this.rokNastepny = rokNastepny;
    }

    public String getMiesiacWpisu() {
        return miesiacWpisu;
    }

    public void setMiesiacWpisu(String miesiacWpisu) {
        this.miesiacWpisu = miesiacWpisu;
    }

    public String getMiesiacOd() {
        return miesiacOd;
    }

    public void setMiesiacOd(String miesiacOd) {
        this.miesiacOd = miesiacOd;
    }

    public String getMiesiacDo() {
        return miesiacDo;
    }

    public void setMiesiacDo(String miesiacDo) {
        this.miesiacDo = miesiacDo;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
        this.pracownik = pracownik;
    }
    

  }
