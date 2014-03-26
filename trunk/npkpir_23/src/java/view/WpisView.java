/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import dao.UzDAO;
import dao.WpisDAO;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Roki;
import entity.Podatnik;
import entity.Uz;
import entity.Wpis;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "WpisView")
@ViewScoped
public class WpisView implements Serializable {

    private String podatnikWpisu;
    private Integer rokWpisu;
    private String rokWpisuSt;
    private Integer rokUprzedni;
    private String rokUprzedniSt;
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
    @Inject
    private Podatnik podatnikObiekt;

    @Inject
    private Wpis wpis;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private PodatnikDAO podatnikDAO;

   private Integer sumarokmiesiac;

    @PostConstruct
    private void init() {
        if (miesiacDo == null && miesiacWpisu == null) {
            miesiacDo = miesiacWpisu;
            miesiacOd = miesiacWpisu;
        }
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        //Application aplikacja =  FacesContext.getCurrentInstance().getApplication();
        String wprowadzilX = null;
        try {
            wprowadzilX = principal.getName();
        } catch (Exception e) {
        }
        if (wprowadzilX != null) {
            wprowadzil = uzDAO.find(wprowadzilX);
            wpis = wpisDAO.find(wprowadzilX);
            this.podatnikWpisu = wpis.getPodatnikWpisu();
            if (wpis.getPodatnikWpisu() == null) {
                this.miesiacWpisu = "01";
                wpis.setMiesiacWpisu("01");
                wpis.setMiesiacOd("01");
                wpis.setMiesiacDo("01");
                wpis.setRokWpisu(Roki.getRokiListS().get(Roki.getRokiListS().size()-1));
                Uz podatnikwpisu = uzDAO.find(wpis.getWprowadzil());
                String nipfirmy = podatnikwpisu.getFirma();
                String nazwapodatnika = podatnikDAO.findN(nipfirmy).getNazwapelna();
                this.podatnikWpisu = nazwapodatnika;
                wpis.setPodatnikWpisu(nazwapodatnika);
                wpis.setMiesiacWpisu(miesiacWpisu);
                wpisDAO.edit(wpis);
            } else {
                this.miesiacWpisu = wpis.getMiesiacWpisu();
            }
            this.rokWpisu = wpis.getRokWpisu();
            try {
                this.rokUprzedni = wpis.getRokWpisu() - 1;
                this.rokUprzedniSt = String.valueOf(this.rokUprzedni);
            } catch (Exception er) {
            }
            this.rokWpisuSt = String.valueOf(wpis.getRokWpisu());

            try {
                if (miesiacOd == null) {
                    this.miesiacOd = wpis.getMiesiacOd();
                    this.miesiacDo = wpis.getMiesiacDo();
                }
            } catch (Exception e) {
                this.miesiacOd = wpis.getMiesiacOd();
                this.miesiacDo = wpis.getMiesiacDo();
            }
            uzupelnijdanepodatnika();
        }
        try {
            obliczsumarokmiesiac();
        } catch (Exception e) {
        }
    }

    public void wpisAktualizuj() {
        findWpis();
    }

    public void findWpis() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String wprowadzilX = principal.getName();
        wprowadzil = uzDAO.find(wprowadzilX);
        wpis = wpisDAO.find(wprowadzilX);
        wpis.setPodatnikWpisu(podatnikWpisu);
        wpis.setMiesiacWpisu(miesiacWpisu);
        wpis.setRokWpisuSt(String.valueOf(rokWpisu));
        wpis.setRokWpisu(rokWpisu);
        wpis.setMiesiacOd(miesiacOd);
        wpis.setMiesiacDo(miesiacDo);
        uzupelnijdanepodatnika();
        wpisDAO.edit(wpis);
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        sessionX.setAttribute("miesiacWpisu", miesiacWpisu);
        sessionX.setAttribute("podatnikWpisu", podatnikWpisu);
        sessionX.setAttribute("rokWpisu", rokWpisu);
        sessionX.setAttribute("wprowadzil", wprowadzil);
    }

    public String findNazwaPodatnika() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String wprowadzilX = principal.getName();
        wprowadzil = uzDAO.find(wprowadzilX);
        wpis = wpisDAO.find(wprowadzilX);
        if (wpis.getPodatnikWpisu() != null) {
            return wpis.getPodatnikWpisu();
        } else {
            Podatnik podatnik = podatnikDAO.findN(wprowadzil.getFirma());
            String nazwapelna = podatnik.getNazwapelna();
            wpis.setPodatnikWpisu(nazwapelna);
            wpisDAO.edit(wpis);
            return nazwapelna;
        }
    }

    public Wpis findWpisX() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String wprowadzilX = principal.getName();
        wprowadzil = uzDAO.find(wprowadzilX);
        return wpisDAO.find(wprowadzilX);

    }

    private void uzupelnijdanepodatnika() {
        if (podatnikWpisu != null) {
            try {
                podatnikObiekt = podatnikDAO.find(podatnikWpisu);
            } catch (Exception e) {
                podatnikWpisu = "GRZELCZYK";
                podatnikObiekt = podatnikDAO.find(podatnikWpisu);
            }
            try {
                rodzajopodatkowania = podatnikObiekt.getPodatekdochodowy().get(zwrocindexparametrzarok(podatnikObiekt.getPodatekdochodowy())).getParametr();
                if (rodzajopodatkowania.contains("ryczałt")) {
                    ksiegaryczalt = false;
                } else {
                    ksiegaryczalt = true;
                }
            } catch (Exception e) {
            }
        }
        if (miesiacWpisu != null) {
            int miesiacprzed = Mce.getMapamcyX().get(miesiacWpisu);
            if (miesiacprzed == 1) {
                miesiacprzed = 13;
            }
            miesiacUprzedni = Mce.getMapamcy().get(--miesiacprzed);
            int miesiacpo = Mce.getMapamcyX().get(miesiacWpisu);
            if (miesiacpo == 12) {
                miesiacpo = 0;
            }
            miesiacNastepny = Mce.getMapamcy().get(++miesiacpo);
        }
    }

    private void obliczsumarokmiesiac() {
        if ((rokWpisu > 0) && (!miesiacWpisu.isEmpty())) {
            sumarokmiesiac = 2016;
            //jest takie robienie bo jak bylo 01/2014 to bylo mniej niz 2014 i pobieralo stara scheme
            if (rokWpisu > 2013 || (rokWpisu == 2013 && Integer.parseInt(miesiacWpisu) > 4)) {
                sumarokmiesiac = 2018;
            }
        }
    }

    private int zwrocindexparametrzarok(List<Parametr> podatekdochodowy) {
        int i = 0;
        for (Parametr p : podatekdochodowy) {
            String rokod = p.getRokOd();
            String rokdo = p.getRokDo();
            String rokwpisuS = String.valueOf(this.rokWpisu);
            boolean rokzamkniety = rokod.equals(rokwpisuS) && rokod.equals(rokwpisuS);
            boolean rokotwarty = rokod.equals(rokwpisuS) && rokod == (null);
            if (rokzamkniety || rokotwarty) {
                return i;
            }
            i++;
        }
        Msg.msg("e", "Parametr opodatkowania nie wprowadzony za dany rok");
        return -1;
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
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
    
    public Wpis getWpis() {
        return wpis;
    }
    
    public void setWpis(Wpis wpis) {
        this.wpis = wpis;
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

    public Integer getSumarokmiesiac() {
        return sumarokmiesiac;
    }

    public void setSumarokmiesiac(Integer sumarokmiesiac) {
        this.sumarokmiesiac = sumarokmiesiac;
    }
}
