/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDDAO;
import dao.UzDAO;
import dao.WpisDAO;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Roki;
import entity.Podatnik;
import entity.Uz;
import entity.Wpis;
import error.E;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "WpisView")
@SessionScoped
public class WpisView implements Serializable {
    private static final long serialVersionUID = 1L;

    private String podatnikWpisu;
    private Integer rokWpisu;
    private String rokWpisuSt;
    private Integer rokUprzedni;
    private String rokUprzedniSt;
    private Integer rokNastepny;
    private String rokNastepnySt;
    private String miesiacWpisu;
    private String miesiacNastepny;
    private String miesiacUprzedni;
    @Inject
    private Uz wprowadzil;
    private String miesiacOd;
    private String miesiacDo;
    private boolean srodkTrw;
    private String rodzajopodatkowania;
    private boolean ksiegaryczalt;
    private boolean ksiegirachunkowe;
    private boolean FKpiatki;
    @Inject
    private Podatnik podatnikObiekt;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private PodatnikOpodatkowanieDDAO podatnikOpodatkowanieDDAO;


    @PostConstruct
    private void init() {
        if (miesiacDo == null && miesiacWpisu == null) {
            miesiacDo = miesiacWpisu;
            miesiacOd = miesiacWpisu;
        }
        Wpis wpis = pobierzWpis();
        if (wpis != null) {
            podatnikWpisu = wpis.getPodatnikWpisu();
            if (wpis.getPodatnikWpisu() == null) {
                miesiacWpisu = "01";
                wpis.setMiesiacWpisu("01");
                wpis.setMiesiacOd("01");
                wpis.setMiesiacDo("01");
                wpis.setRokWpisu(Roki.getRokiListS().get(Roki.getRokiListS().size()-1));
                Uz podatnikwpisu = uzDAO.findUzByLogin(wpis.getWprowadzil());
                String nipfirmy;
                String nazwapodatnika;
                try {
                    nipfirmy = podatnikwpisu.getFirma();
                    nazwapodatnika = podatnikDAO.findPodatnikByNIP(nipfirmy).getNazwapelna();
                } catch (Exception e) { E.e(e); 
                    //laduje demofirme jak cos pojdzie zle
                    nipfirmy = "1111005008";
                    nazwapodatnika = podatnikDAO.findPodatnikByNIP(nipfirmy).getNazwapelna();
                }
                podatnikWpisu = nazwapodatnika;
                wpis.setPodatnikWpisu(nazwapodatnika);
                wpis.setMiesiacWpisu(miesiacWpisu);
                wpisDAO.edit(wpis);
            } else {
                miesiacWpisu = wpis.getMiesiacWpisu();
            }
            rokWpisu = wpis.getRokWpisu();
           obsluzRok();
           obsluzMce(wpis);
           uzupelnijdanepodatnika();
        }
     
    }
    
    private final Wpis pobierzWpis() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        try {
            String wprowadzilX = principal.getName();
            wprowadzil = uzDAO.findUzByLogin(wprowadzilX);
            Wpis wpis = wpisDAO.find(wprowadzilX);
            return wpis;
        } catch (Exception e) {
            E.e(e); 
            return null;
        } 
    }
    
    private void obsluzMce(Wpis wpis) {
         try {
            if (miesiacOd == null) {
                    miesiacOd = wpis.getMiesiacOd();
                    miesiacDo = wpis.getMiesiacDo();
                }
            } catch (Exception e) { E.e(e); 
                miesiacOd = wpis.getMiesiacOd();
                miesiacDo = wpis.getMiesiacDo();
            }
    }

    private void obsluzRok() {
         try {
                rokUprzedni = rokWpisu - 1;
                rokUprzedniSt = String.valueOf(rokUprzedni);
            } catch (Exception e) {
                E.e(e);
            }
            try {
                rokNastepny = rokWpisu + 1;
                rokNastepnySt = String.valueOf(rokNastepny);
            } catch (Exception e) {
                E.e(e);
            }
            rokWpisuSt = String.valueOf(rokWpisu);
    }
    
    private void obsluzMcPrzedPo() {
        if (miesiacWpisu != null) {
            int miesiacprzed = Mce.getMiesiacToNumber().get(miesiacWpisu);
            if (miesiacprzed == 1) {
                miesiacprzed = 13;
            }
            miesiacUprzedni = Mce.getNumberToMiesiac().get(--miesiacprzed);
            int miesiacpo = Mce.getMiesiacToNumber().get(miesiacWpisu);
            if (miesiacpo == 12) {
                miesiacpo = 0;
            }
            miesiacNastepny = Mce.getNumberToMiesiac().get(++miesiacpo);
        }
    }
    
    public void wpisAktualizuj() {
        naniesDaneDoWpis();
    }

    public final void naniesDaneDoWpis() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String wprowadzilX = principal.getName();
        wprowadzil = uzDAO.findUzByLogin(wprowadzilX);
        Wpis wpis = new Wpis();
        wpis = wpisDAO.find(wprowadzilX);
        wpis.setPodatnikWpisu(podatnikWpisu);
        wpis.setMiesiacWpisu(miesiacWpisu);
        wpis.setRokWpisuSt(String.valueOf(rokWpisu));
        wpis.setRokWpisu(rokWpisu);
        wpis.setMiesiacOd(miesiacOd);
        wpis.setMiesiacDo(miesiacDo);
        uzupelnijdanepodatnika();
        wpisDAO.edit(wpis);
        obsluzRok();
        obsluzMcPrzedPo();
    }

    public String findNazwaPodatnika() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        String wprowadzilX = request.getUserPrincipal().getName();
        wprowadzil = uzDAO.findUzByLogin(wprowadzilX);
        Wpis wpis = wpisDAO.find(wprowadzilX);
        if (wpis.getPodatnikWpisu() != null) {
            return wpis.getPodatnikWpisu();
        } else {
            Podatnik podatnik = podatnikDAO.findPodatnikByNIP(wprowadzil.getFirma());
            String nazwapelna = podatnik.getNazwapelna();
            wpis.setPodatnikWpisu(nazwapelna);
            wpisDAO.edit(wpis);
            return nazwapelna;
        }
    }

  
    private void uzupelnijdanepodatnika() {
        obsluzPodatnikObiekt();
        pobierzOpodatkowanie();
        obsluzMcPrzedPo();
        if (miesiacOd == null) {
            miesiacOd = "01";
        }
        if (miesiacDo == null) {
            miesiacDo = "01";
        }
        List<Parametr> piatki = podatnikObiekt.getFKpiatki();
        if ( piatki != null && (!piatki.isEmpty())) {
            for (Parametr p : piatki) {
                if (p.getRokOd().equals(rokWpisuSt)) {
                    FKpiatki = (p.getParametr().equals("tak") ? true : false);
                }
            }
        }
    }

    private void obsluzPodatnikObiekt() {
        if (podatnikWpisu != null) {
            try {
                podatnikObiekt = podatnikDAO.find(podatnikWpisu);
            } catch (Exception e) { 
                E.e(e); 
                podatnikWpisu = "GRZELCZYK";
                podatnikObiekt = podatnikDAO.find(podatnikWpisu);
            }
        }
    }
    
    private void pobierzOpodatkowanie() {
        try {
                rodzajopodatkowania = zwrocindexparametrzarok();
                if (rodzajopodatkowania.contains("ryczałt")) {
                    ksiegaryczalt = false;
                } else {
                    ksiegaryczalt = true;
                }
                if (rodzajopodatkowania.contains("księgi rachunkowe")) {
                    ksiegirachunkowe = true;
                } else {
                    ksiegirachunkowe = false;
                }
            } catch (Exception e) { 
                E.e(e);
            }
    }
    
    private String zwrocindexparametrzarok() {
         return podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(this).getFormaopodatkowania();
    }
    
    public String skierujmultisuera() {
        rodzajopodatkowania = zwrocindexparametrzarok();
        if (rodzajopodatkowania.contains("księgi rachunkowe")) {
            return "/guestFK/guestFKTablica.xhtml?faces-redirect=true";
        } else {
            return "/guest/guestPodatki.xhtml?faces-redirect=true";
        }
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    public Integer getRokNastepny() {
        return rokNastepny;
    }

    public void setRokNastepny(Integer rokNastepny) {
        this.rokNastepny = rokNastepny;
    }

    public String getRokNastepnySt() {
        return rokNastepnySt;
    }

    public void setRokNastepnySt(String rokNastepnySt) {
        this.rokNastepnySt = rokNastepnySt;
    }
    
    
    public boolean isKsiegirachunkowe() {
        return ksiegirachunkowe;
    }

    public void setKsiegirachunkowe(boolean ksiegirachunkowe) {
        this.ksiegirachunkowe = ksiegirachunkowe;
    }

    public boolean isFKpiatki() {
        return FKpiatki;
    }

    public void setFKpiatki(boolean FKpiatki) {
        this.FKpiatki = FKpiatki;
    }
    
    public String getPodatnikWpisu() {
        return podatnikWpisu;
    }
    
    public void setPodatnikWpisu(String podatnikWpisu) {
        this.podatnikWpisu = podatnikWpisu;
    }
    
    public Integer getRokWpisu() {
        return rokWpisu;
    }
    
    public void setRokWpisu(Integer rokWpisu) {
        this.rokWpisu = rokWpisu;
    }
    
    public String getMiesiacWpisu() {
        return miesiacWpisu;
    }
    
    public void setMiesiacWpisu(String miesiacWpisu) {
        this.miesiacWpisu = miesiacWpisu;
    }
    
    public Uz getWprowadzil() {
        return wprowadzil;
    }
    
    public void setWprowadzil(Uz wprowadzil) {
        this.wprowadzil = wprowadzil;
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
    
    public boolean isSrodkTrw() {
        return srodkTrw;
    }
    
    public void setSrodkTrw(boolean srodkTrw) {
        this.srodkTrw = srodkTrw;
    }
    
      
    public WpisDAO getWpisDAO() {
        return wpisDAO;
    }
    
    public void setWpisDAO(WpisDAO wpisDAO) {
        this.wpisDAO = wpisDAO;
    }
    
    public String getRodzajopodatkowania() {
        return rodzajopodatkowania;
    }
    
    public void setRodzajopodatkowania(String rodzajopodatkowania) {
        this.rodzajopodatkowania = rodzajopodatkowania;
    }
    
    public Podatnik getPodatnikObiekt() {
        return podatnikObiekt;
    }
    
    public void setPodatnikObiekt(Podatnik podatnikObiekt) {
        this.podatnikObiekt = podatnikObiekt;
    }
    
    public boolean isKsiegaryczalt() {
        return ksiegaryczalt;
    }
    
    public void setKsiegaryczalt(boolean ksiegaryczalt) {
        this.ksiegaryczalt = ksiegaryczalt;
    }
    
    public String getMiesiacNastepny() {
        return miesiacNastepny;
    }
    
    public void setMiesiacNastepny(String miesiacNastepny) {
        this.miesiacNastepny = miesiacNastepny;
    }
    
    public String getMiesiacUprzedni() {
        return miesiacUprzedni;
    }
    
    public void setMiesiacUprzedni(String miesiacUprzedni) {
        this.miesiacUprzedni = miesiacUprzedni;
    }
    
    public String getRokWpisuSt() {
        return rokWpisuSt;
    }
    
    public void setRokWpisuSt(String rokWpisuSt) {
        this.rokWpisuSt = rokWpisuSt;
    }
    
    public Integer getRokUprzedni() {
        return rokUprzedni;
    }
    
    public void setRokUprzedni(Integer rokUprzedni) {
        this.rokUprzedni = rokUprzedni;
    }
    
    public String getRokUprzedniSt() {
        return rokUprzedniSt;
    }
    
    public void setRokUprzedniSt(String rokUprzedniSt) {
        this.rokUprzedniSt = rokUprzedniSt;
    }
    
    
//</editor-fold>

  }
