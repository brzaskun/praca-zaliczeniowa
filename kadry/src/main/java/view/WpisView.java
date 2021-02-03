/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FirmaFacade;
import dao.MemoryFacade;
import dao.PracownikFacade;
import data.Data;
import entity.Angaz;
import entity.Firma;
import entity.Memory;
import entity.Pracownik;
import entity.Umowa;
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

    @Inject
    private MemoryFacade memoryFacade;
    @Inject
    private FirmaFacade firmaFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    private Memory memory;
    private String rokWpisu;
    private String rokUprzedni;
    private String rokNastepny;
    private String miesiacWpisu;
    private Uz uzer;
    private String miesiacOd;
    private String miesiacDo;
    private Firma firma;
    private Pracownik pracownik;
    private Angaz angaz;
    private Umowa umowa;
 
    

    public WpisView() {
        }
    

    @PostConstruct
    public void init() { //E.m(this);
        rokWpisu="2020";
        miesiacWpisu="12";
        if (uzer!=null) {
            memory = pobierzMemory();
            if (memory!=null && memory.getId()!=null) {
                this.firma = memory.getFirma();
                this.angaz = memory.getAngaz();
                this.umowa = memory.getUmowa();
                this.pracownik = memory.getPracownik();
                this.rokWpisu = memory.getRok();
                this.miesiacWpisu = memory.getMc();
            }
        }
    }
    
    private Memory pobierzMemory() {
        Memory zwrot = memoryFacade.findByUzer(uzer);
        if (zwrot==null&&uzer.getPesel()!=null&&uzer.getPesel().length()==11) {
            Pracownik pracownik = this.pracownik;
            if (uzer.getPesel()!=null) {
                pracownik = pracownikFacade.findByPesel(uzer.getPesel());
            }
            zwrot = new Memory(this.uzer, uzer.getFirma(),pracownik, Data.aktualnyRok(), Data.aktualnyMc());
            memoryFacade.create(zwrot);
        }
        return zwrot;
    }
    
    private Memory createMemory() {
            Memory zwrot = new Memory(this.uzer, uzer.getFirma(),null, Data.aktualnyRok(), Data.aktualnyMc());
            memoryFacade.create(zwrot);
            return zwrot;
    }
    
    public void zmienrok() {
        rokWpisu=String.valueOf(Integer.parseInt(rokWpisu)+1);
    }

    public String getRokWpisu() {
        return rokWpisu;
    }
    
    public int getRokWpisuInt() {
        return Integer.parseInt(rokWpisu);
    }

    public void setRokWpisu(String rokWpisu) {
        if (memory!=null) {
            memory.setRok(rokWpisu);
            memoryFacade.edit(memory);
        } else {
            memory = createMemory();
            memory.setRok(rokWpisu);
            memoryFacade.edit(memory);
        }
                
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
        if (memory!=null) {
            memory.setMc(miesiacWpisu);
            memoryFacade.edit(memory);
        } else {
            memory = createMemory();
            memory.setMc(rokWpisu);
            memoryFacade.edit(memory);
        }
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
        if (memory!=null) {
            memory.setFirma(firma);
            memoryFacade.edit(memory);
        }
        this.firma = firma;
    }

    public Pracownik getPracownik() {
        return pracownik;
    }

    public void setPracownik(Pracownik pracownik) {
         if (memory!=null) {
            memory.setPracownik(pracownik);
            memoryFacade.edit(memory);
        } else {
            memory = createMemory();
            memory.setPracownik(pracownik);
            memoryFacade.edit(memory);
        }
        this.pracownik = pracownik;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        if (memory!=null) {
            memory.setAngaz(angaz);
            memoryFacade.edit(memory);
        }
        this.angaz = angaz;
    }

    public Umowa getUmowa() {
        return umowa;
    }

    public void setUmowa(Umowa umowa) {
        if (memory!=null) {
            memory.setUmowa(umowa);
            memoryFacade.edit(memory);
        }
        this.umowa = umowa;
    }

    void memorize() {
        if (memory!=null&&memory.getId()!=null) {
            memoryFacade.edit(memory);
        }
    }

    public Uz getUzer() {
        return uzer;
    }

    public void setUzer(Uz uzer) {
        this.uzer = uzer;
    }
    
    

  }
