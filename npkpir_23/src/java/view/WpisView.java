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
import error.E;
import java.io.Serializable;
import java.security.Principal;
import java.util.List;
import javax.annotation.PostConstruct;
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
        } catch (Exception e) { E.e(e); 
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
            try {
                this.rokNastepny = wpis.getRokWpisu() + 1;
                this.rokNastepnySt = String.valueOf(this.rokNastepny);
            } catch (Exception er) {
            }
            this.rokWpisuSt = String.valueOf(wpis.getRokWpisu());

            try {
                if (miesiacOd == null) {
                    this.miesiacOd = wpis.getMiesiacOd();
                    this.miesiacDo = wpis.getMiesiacDo();
                }
            } catch (Exception e) { E.e(e); 
                this.miesiacOd = wpis.getMiesiacOd();
                this.miesiacDo = wpis.getMiesiacDo();
            }
            uzupelnijdanepodatnika();
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
            Podatnik podatnik = podatnikDAO.findPodatnikByNIP(wprowadzil.getFirma());
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
            } catch (Exception e) { E.e(e); 
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
                if (rodzajopodatkowania.contains("księgi rachunkowe")) {
                    ksiegirachunkowe = true;
                } else {
                    ksiegirachunkowe = false;
                }
            } catch (Exception e) { 
                System.out.println("Blad " + e.toString()); 
            }
        }
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

    private int zwrocindexparametrzarok(List<Parametr> podatekdochodowy) {
        boolean manager = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Manager");
        boolean admin = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("Administrator");
        boolean guestfk = FacesContext.getCurrentInstance().getExternalContext().isUserInRole("GuestFK");
        if ((manager == false) && (admin == false) && (guestfk == false)) {
            int i = 0;
            for (Parametr p : podatekdochodowy) {
                String rokod = p.getRokOd();
                String rokdo = p.getRokDo();
                String rokwpisuS = String.valueOf(this.rokWpisu);
                boolean rokzamkniety = rokod.equals(rokwpisuS) && rokdo.equals(rokwpisuS);
                boolean rokotwarty = rokod.equals(rokwpisuS) && rokdo == (null);
                if (rokzamkniety || rokotwarty) {
                    return i;
                }
                i++;
            }
            Msg.msg("e", "Parametr opodatkowania nie wprowadzony za dany rok");
            return -1;
        }
        return -1;
    }
    
    public String skierujmultisuera() {
        Podatnik podatnik = podatnikDAO.find(podatnikWpisu);
        List<Parametr> pod = podatnik.getPodatekdochodowy();
        if ( pod != null && (!pod.isEmpty())) {
            for (Parametr p : pod) {
                if (p.getRokOd().equals(rokWpisuSt)) {
                    if (p.getParametr().contains("księgi rachunkowe")) {
                        return "/guestFK/guestFKTablica.xhtml?faces-redirect=true";
                    } else {
                        return "/guest/guestTablica.xhtml?faces-redirect=true";
                    }
                }
            }
        }
        return "";
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

  }
