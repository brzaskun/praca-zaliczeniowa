/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDDAO;
import dao.UzDAO;
import dao.WpisDAO;
import data.Data;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Roki;
import entity.ParamCzworkiPiatki;
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
import javax.servlet.http.HttpSession;
import msg.Msg;

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
    private String miesiacWpisuArchiwum;
    private String miesiacNastepny;
    private String miesiacUprzedni;
    @Inject
    private Uz wprowadzil;
    private String miesiacOd;
    private String miesiacDo;
    private boolean srodkTrw;
    private String rodzajopodatkowania;
    private boolean ksiegaryczalt;
    private boolean mc0kw1;
    private boolean ksiegirachunkowe;
    private boolean vatowiec;
    private int vatokres;
    private boolean paramCzworkiPiatki;
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
    private boolean czegosbrakuje;
    private String formaprawna;
    private boolean niegosc;
    private Integer zmianaokresuRok;
    private String zmianaokresuMc;
    private double stawkapodatkuospr;
    private String zrodlo;
    private boolean rokzamkniety;
    private boolean rokpoprzednizamkniety;

    public WpisView() {
        czegosbrakuje = false;
    }
    

    @PostConstruct
    private void init() {
        ustawMceOdDo();
        Wpis wpis = pobierzWpisBD();
        if (wpis != null) {
            podatnikWpisu = wpis.getPodatnikWpisu();
            if (wpis.getPodatnikWpisu() == null) {
                inicjacjaUz(wpis);
            } else {
                miesiacWpisu = wpis.getMiesiacWpisu();
                zmianaokresuMc =  wpis.getMiesiacWpisu();
                miesiacWpisuArchiwum = wpis.getMiesiacWpisu();
                rokWpisu = wpis.getRokWpisu();
                zmianaokresuRok =  wpis.getRokWpisu();
            }
           obsluzMce(wpis);
           uzupelnijdanepodatnika();
           czyniegosc();
        }
     
        }
    
    public void initpublic() {
        init();
    }
    public String menustrona() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        String uri = ctx.getExternalContext().getRequestServletPath();
        zrodlo = uri+"?faces-redirect=true";
        return "BookkeeperFK";
    }
    
    public String wroc() {
        String zwrot = "/ksiegowaFK/ksiegowaFKDokument.xhtml?faces-redirect=true";
        try {
            if (zrodlo != null) {
                zwrot = zrodlo.toString();
                zrodlo = null;
            }
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    //swiezowpisany uzer nie ma ustawionych parametrow
    private void inicjacjaUz(Wpis wpis) {
        miesiacWpisu = Data.aktualnyMc();
        wpis.setMiesiacWpisu(Data.aktualnyMc());
        wpis.setMiesiacOd(Data.aktualnyMc());
        wpis.setMiesiacDo(Data.aktualnyMc());
        miesiacWpisuArchiwum = Data.aktualnyMc();
        wpis.setRokWpisu(Roki.getRokiListS().get(Roki.getRokiListS().size() - 1));
        Uz podatnikwpisu = uzDAO.findUzByLogin(wpis.getWprowadzil());
        String nipfirmy;
        String nazwapodatnika;
        try {
            nipfirmy = podatnikwpisu.getFirma();
            nazwapodatnika = podatnikDAO.findPodatnikByNIP(nipfirmy).getNazwapelna();
        } catch (Exception e) {
            E.e(e);
            //laduje demofirme jak cos pojdzie zle
            nipfirmy = "1111005008";
            nazwapodatnika = podatnikDAO.findPodatnikByNIP(nipfirmy).getNazwapelna();
        }
        podatnikWpisu = nazwapodatnika;
        wpis.setPodatnikWpisu(nazwapodatnika);
        wpis.setMiesiacWpisu(miesiacWpisu);
        wpisDAO.edit(wpis);
    }
    private Wpis pobierzWpisBD() {
        Wpis wpis = null;
        try {
            String wprowadzilX = getPrincipal().getName();
            wprowadzil = uzDAO.findUzByLogin(wprowadzilX);
            wpis = wpisDAO.find(wprowadzilX);
        } catch (Exception e) {
            E.e(e); 
        } 
        return wpis;
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

    public void wpisAktualizujZmianadaty() {
        miesiacWpisu = new String(zmianaokresuMc);
        rokWpisu = zmianaokresuRok;
        naniesDaneDoWpis();
    }
    
    public void naniesDaneDoWpis() {
        czegosbrakuje = false;
        String wprowadzilX = getPrincipal().getName();
        wprowadzil = uzDAO.findUzByLogin(wprowadzilX);
        Wpis wpis = new Wpis();
        wpis = wpisDAO.find(wprowadzilX);
        wpis.setPodatnikWpisu(podatnikWpisu);
        if (!miesiacWpisu.equals("CR")) {
            wpis.setMiesiacWpisu(miesiacWpisu);
            miesiacWpisuArchiwum = miesiacWpisu;
        } else if (miesiacWpisu.equals("CR")){
            wpis.setMiesiacWpisu(Data.aktualnyMc());
        }
        wpis.setRokWpisuSt(String.valueOf(rokWpisu));
        wpis.setRokWpisu(rokWpisu);
        wpis.setMiesiacOd(miesiacOd);
        wpis.setMiesiacDo(miesiacDo);
        wpisDAO.edit(wpis);
        uzupelnijdanepodatnika();
        czyniegosc();
       
    }
    
     public void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(miesiacWpisu);
        wpistmp.setRokWpisu(rokWpisu);
        wpistmp.setRokWpisuSt(String.valueOf(rokWpisu));
        wpistmp.setPodatnikWpisu(podatnikWpisu);
        wpisDAO.edit(wpistmp);
        naniesDaneDoWpis();
    }

    private Principal getPrincipal() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getUserPrincipal();
    }
    
    public String findNazwaPodatnika() {
        String wprowadzilX = getPrincipal().getName();
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
        obsluzMcPrzedPo();
        obsluzRok();
        if (miesiacOd == null) {
            miesiacOd = "01";
        }
        if (miesiacDo == null) {
            miesiacDo = "01";
        }
        obsluzPiatki();
        pobierzformaprawna();
        try {
            pobierzOpodatkowanie();
        } catch (Exception e1) {
            E.e(e1);
        }
    }

    private void obsluzPiatki() {
        List<ParamCzworkiPiatki> piatki = podatnikObiekt.getParamCzworkiPiatki();
        if (piatki != null && (!piatki.isEmpty())) {
            for (ParamCzworkiPiatki p : piatki) {
                if (p.getRokOd().equals(rokWpisuSt)) {
                    paramCzworkiPiatki = (p.getParametr().equals("tak") ? true : false);
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
    
    private String sprawdzjakiokresvat() {
        Integer rok = this.getRokWpisu();
        Integer mc = Integer.parseInt(this.getMiesiacWpisu());
        List<Parametr> parametry = this.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }
    
    private void pobierzOpodatkowanie() {
        try {
            mc0kw1 = zwrocmc0kw1();
            rodzajopodatkowania = zwrocindexparametrzarok();
            rokzamkniety = zwrocrokzamkniety();
            rokpoprzednizamkniety = zwrocrokpoprzednizamkniety();
            if (rodzajopodatkowania != null) {
                if (this.podatnikObiekt.getFormaPrawna() != null && this.podatnikObiekt.getFormaPrawna().toString().equals("SPOLKA_Z_O_O")) {
                    stawkapodatkuospr = stawkapodatkuospr();
                }
                String czyjestvat = sprawdzjakiokresvat();
                if (czyjestvat.equals("blad")) {
                    vatowiec = false;
                    vatokres = 0;
                } else {
                    vatowiec = true;
                    if (czyjestvat.equals("kwartalne")) {
                        vatokres = 2;
                    } else {
                        vatokres = 1;
                    }
                }
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
            } else {
                ksiegaryczalt = false;
                ksiegirachunkowe = false;
                Msg.msg("e", "Brak wyboru opodatkowania w danym roku");
            }
            if (rodzajopodatkowania == null) {
                czegosbrakuje = true;
            }
        } catch (Exception e) {
            czegosbrakuje = true;
            E.e(e);
        }
    }
    
    private double stawkapodatkuospr() {
        return podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(this).getStawkapodatkuospr();
    }
    
    private String zwrocindexparametrzarok() {
         return podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(this).getFormaopodatkowania();
    }
    
    private boolean zwrocrokzamkniety() {
        return podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(this).isZamkniety();
    }
    private boolean zwrocrokpoprzednizamkniety() {
        boolean zwrot = false;
        try {
            zwrot = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRokPoprzedni(this).isZamkniety();
        } catch (Exception e) {
        }
        return zwrot;
    }
    
    
    private boolean zwrocmc0kw1() {
        return podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(this).isMc0kw1();
    }
    public String skierujmultisuera() {
        rodzajopodatkowania = zwrocindexparametrzarok();
        if (rodzajopodatkowania.contains("księgi rachunkowe")) {
            return "/guestFK/guestFKTablica.xhtml?faces-redirect=true";
        } else {
            return "/guest/guestPodatki.xhtml?faces-redirect=true";
        }
    }
    
    private void ustawMceOdDo() {
        if (miesiacDo == null && miesiacWpisu == null) {
            miesiacDo = miesiacWpisu;
            miesiacOd = miesiacWpisu;
        }
    }
    
    private void czyniegosc() {
        niegosc = true;
        if (this.wprowadzil.getUprawnienia().equals("Guest")||this.wprowadzil.getUprawnienia().equals("GuestFK")||this.wprowadzil.getUprawnienia().equals("GuestFaktura")||this.wprowadzil.getUprawnienia().equals("Multiuser")||this.wprowadzil.getUprawnienia().equals("Dedra")) {
            niegosc = false;
        }
    }

    public void kopiujmiesiac() {
        zmianaokresuMc = new String(miesiacWpisu);
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    public Integer getRokNastepny() {
        return rokNastepny;
    }

    public void setRokNastepny(Integer rokNastepny) {
        this.rokNastepny = rokNastepny;
    }

    public boolean isVatowiec() {
        return vatowiec;
    }

    public void setVatowiec(boolean vatowiec) {
        this.vatowiec = vatowiec;
    }

    public String getZrodlo() {
        return zrodlo;
    }

    public void setZrodlo(String zrodlo) {
        this.zrodlo = zrodlo;
    }

    public double getStawkapodatkuospr() {
        return stawkapodatkuospr;
    }

    public void setStawkapodatkuospr(double stawkapodatkuospr) {
        this.stawkapodatkuospr = stawkapodatkuospr;
    }

    public Integer getZmianaokresuRok() {
        return zmianaokresuRok;
    }

    public void setZmianaokresuRok(Integer zmianaokresuRok) {
        this.zmianaokresuRok = zmianaokresuRok;
    }

    public String getZmianaokresuMc() {
        return zmianaokresuMc;
    }

    public void setZmianaokresuMc(String zmianaokresuMc) {
        this.zmianaokresuMc = zmianaokresuMc;
    }

    public String getFormaprawna() {
        return formaprawna;
    }

    public void setFormaprawna(String formaprawna) {
        this.formaprawna = formaprawna;
    }

    public String getMiesiacWpisuArchiwum() {
        return miesiacWpisuArchiwum;
    }

    public void setMiesiacWpisuArchiwum(String miesiacWpisuArchiwum) {
        this.miesiacWpisuArchiwum = miesiacWpisuArchiwum;
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

    public boolean isParamCzworkiPiatki() {
        return paramCzworkiPiatki;
    }

    public void setParamCzworkiPiatki(boolean paramCzworkiPiatki) {
        this.paramCzworkiPiatki = paramCzworkiPiatki;
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

    public boolean isNiegosc() {
        return niegosc;
    }

    public void setNiegosc(boolean niegosc) {
        this.niegosc = niegosc;
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

    public boolean isMc0kw1() {
        return mc0kw1;
    }

    public void setMc0kw1(boolean mc0kw1) {
        this.mc0kw1 = mc0kw1;
    }
    
    public String getRokUprzedniSt() {
        return rokUprzedniSt;
    }
    
    public void setRokUprzedniSt(String rokUprzedniSt) {
        this.rokUprzedniSt = rokUprzedniSt;
    }
    
    public boolean isCzegosbrakuje() {
        return czegosbrakuje;
    }

    public void setCzegosbrakuje(boolean czegosbrakuje) {
        this.czegosbrakuje = czegosbrakuje;
    }

   
//</editor-fold>

    private void pobierzformaprawna() {
        if (podatnikObiekt.getFormaPrawna() != null) {
            formaprawna = podatnikObiekt.getFormaPrawna().toString();
        }
    }

    public boolean isRokzamkniety() {
        return rokzamkniety;
    }

    public void setRokzamkniety(boolean rokzamkniety) {
        this.rokzamkniety = rokzamkniety;
    }

    public boolean isRokpoprzednizamkniety() {
        return rokpoprzednizamkniety;
    }

    public void setRokpoprzednizamkniety(boolean rokpoprzednizamkniety) {
        this.rokpoprzednizamkniety = rokpoprzednizamkniety;
    }

    public String getPrintNazwa() {
        return this.podatnikObiekt.getPrintnazwa();
    }

    public int getVatokres() {
        return vatokres;
    }

    public void setVatokres(int vatokres) {
        this.vatokres = vatokres;
    }

  

  }
