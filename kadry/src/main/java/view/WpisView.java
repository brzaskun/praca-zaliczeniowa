/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FirmaKadryFacade;
import dao.MemoryFacade;
import dao.PracownikFacade;
import dao.UzFacade;
import data.Data;
import embeddable.Okres;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Memory;
import entity.Pracownik;
import entity.Umowa;
import entity.Uz;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class WpisView implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private MemoryFacade memoryFacade;
    @Inject
    private FirmaKadryFacade firmaFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private UzFacade uzFacade;
    private Memory memory;
    private String rokWpisu;
    private String rokUprzedni;
    private String rokNastepny;
    private String miesiacWpisu;
    private Okres okreswpisu;
    private Okres okreswpisupoprzedni;
    private Uz uzer;
    private String miesiacOd;
    private String miesiacDo;
    private FirmaKadry firma;
    private Pracownik pracownik;
    private Angaz angaz;
    private Umowa umowa;
 
    

    public WpisView() {
        }
    

    @PostConstruct
    public void init() { //E.m(this);
        rokWpisu = "2025";
        miesiacWpisu = "01";
        okreswpisu = new Okres(rokWpisu, miesiacWpisu);
        if (uzer == null) {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            uzer = (Uz) request.getAttribute("uzer");
            String wprowadzilX = request.getUserPrincipal().getName();
            uzer = uzFacade.findUzByLogin(wprowadzilX);
        }
        if (uzer != null && uzer.getUprawnienia().getNazwa().equals("Pracodawca")) {
            this.firma = uzer.getFirma();
        } else if (uzer != null) {
            memory = pobierzMemory();
            if (memory != null && memory.getId() != null) {
                this.firma = memory.getFirma();
                this.angaz = memory.getAngaz();
                this.umowa = memory.getUmowa();
                this.pracownik = memory.getPracownik();
                this.rokWpisu = memory.getRok();
                this.rokNastepny = String.valueOf(this.getRokWpisuInt()+1);
                this.rokUprzedni = String.valueOf(this.getRokWpisuInt()-1);
                this.miesiacWpisu = memory.getMc();
                this.okreswpisu = new Okres(memory.getRok(), memory.getMc());
                if (memory.getRok()!=null&& memory.getMc()!=null) {
                    String[] poprzedniOkres = Data.poprzedniOkres(memory.getMc(), memory.getRok());
                    this.okreswpisupoprzedni = new Okres(poprzedniOkres[1], poprzedniOkres[0]);
                }
            } else if (uzer.getUprawnienia().getNazwa().equals("Administrator")) {
                this.firma = uzer.getFirma();
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
            if (zwrot.getFirma()!=null) {
                memoryFacade.create(zwrot);
            }
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
        String zwrot = this.rokWpisu;
        if (zwrot != null) {
            zwrot = String.valueOf(Integer.parseInt(zwrot)-1);
        }
        return zwrot;
    }

    public void setRokUprzedni(String rokUprzedni) {
        this.rokUprzedni = rokUprzedni;
    }

    public String getRokNastepny() {
        String zwrot = this.rokWpisu;
        if (zwrot != null) {
            zwrot = String.valueOf(Integer.parseInt(zwrot)+1);
        }
        return zwrot;
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
            memory.setMc(miesiacWpisu);
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

    public FirmaKadry getFirma() {
        return firma;
    }

    public void setFirma(FirmaKadry firma) {
        if (memory!=null) {
            memory.setFirma(firma);
            memoryFacade.edit(memory);
        } else {
            if (firma!=null) {
                memory = createMemory();
                memory.setFirma(firma);
                memoryFacade.edit(memory);
            }
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
        }else {
            if (pracownik!=null) {
                memory = createMemory();
                memory.setPracownik(pracownik);
                memoryFacade.edit(memory);
            }
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
        } else {
            if (angaz!=null) {
                memory = createMemory();
                memory.setAngaz(angaz);
                memoryFacade.edit(memory);
            }
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
            pobierzMemory();
        } else {
            if (umowa!=null) {
                memory = createMemory();
                memory.setUmowa(umowa);
                memoryFacade.edit(memory);
            }
        }
        this.umowa = umowa;
    }

    void usunMemory() {
        if (memory!=null&&memory.getId()!=null) {
            if (memory.getFirma()==null&&memory.getAngaz()==null&&memory.getUmowa()==null) {
                memoryFacade.remove(memory);
                memory=null;
            }
        }
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

    public Okres getOkreswpisu() {
        return okreswpisu;
    }

    public Okres getOkreswpisupoprzedni() {
        String[] poprzedniOkres = Data.poprzedniOkres(this.miesiacWpisu, this.rokWpisu);
        this.okreswpisupoprzedni = new Okres(poprzedniOkres[1], poprzedniOkres[0]);
        return okreswpisupoprzedni;
    }

    

    
    public void setOkreswpisu(Okres okreswpisu) {
        rokWpisu = okreswpisu.getRok();
        miesiacWpisu = okreswpisu.getMc();
        if (memory!=null) {
            memory.setRok(rokWpisu);
            memory.setMc(miesiacWpisu);
            memoryFacade.edit(memory);
        } else {
            memory = createMemory();
            memory.setRok(rokWpisu);
            memory.setMc(miesiacWpisu);
            memoryFacade.edit(memory);
        }
        this.okreswpisu = okreswpisu;
    }

    public Memory getMemory() {
        return memory;
    }

    public void setMemory(Memory memory) {
        this.memory = memory;
    }
    
    

  }
