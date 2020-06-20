/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.UzDAO;
import data.Data;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Roki;
import entity.ParamCzworkiPiatki;
import entity.ParamVatUE;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.Uz;
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
import msg.Msg;
/**
 *
 * @author Osito
 */
@ManagedBean(name = "WpisView")
@SessionScoped
public class WpisView implements Serializable {
    private static final long serialVersionUID = 1L;

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
    private Uz uzer;
    private String miesiacOd;
    private String miesiacDo;
    private boolean srodkTrw;
    private String rodzajopodatkowania;
    private boolean ksiegaryczalt;
    private boolean mc0kw1;
    private boolean ksiegirachunkowe;
    private boolean vatowiec;
    private boolean vatowiecue;
    private int vatokres;
    private boolean paramCzworkiPiatki;
    @Inject
    private Podatnik podatnikObiekt;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    private boolean czegosbrakuje;
    private String formaprawna;
    private boolean niegosc;
    private Integer zmianaokresuRok;
    private String zmianaokresuMc;
    private double stawkapodatkuospr;
    private String zrodlo;
    private boolean rokzamkniety;
    private boolean rokpoprzednizamkniety;
    private Podatnik podatnikwzorcowy;
    private String odjakiegomcdok;
    private boolean biuroiszef;

    public WpisView() {
        czegosbrakuje = false;
    }
    

    @PostConstruct
    private void init() { //E.m(this);
        ustawMceOdDo();
        uzer = pobierzWpisBD();
        odjakiegomcdok = "01";
        formaprawna = null;
        if (uzer != null) {
            podatnikObiekt = uzer.getPodatnik();
            if (podatnikObiekt == null) {
                inicjacjaUz();
            } else if (uzer.getMiesiacWpisu() == null || uzer.getRokWpisu() == null) {
                miesiacWpisu = Data.aktualnyMc();
                zmianaokresuMc = Data.aktualnyMc();
                miesiacWpisuArchiwum = Data.aktualnyMc();
                rokWpisu = Integer.parseInt(Data.aktualnyRok());
                zmianaokresuRok = Integer.parseInt(Data.aktualnyRok());
                inicjacjaUzDaty();
            } else {
                miesiacWpisu = uzer.getMiesiacWpisu();
                zmianaokresuMc = uzer.getMiesiacWpisu();
                miesiacWpisuArchiwum = uzer.getMiesiacWpisu();
                rokWpisu = uzer.getRokWpisu();
                zmianaokresuRok = uzer.getRokWpisu();
            }
            obsluzMce();
            uzupelnijdanepodatnika();
            czyniegosc();
            podatnikwzorcowy = podatnikDAO.find("Wzorcowy");
            czytojetsbiuroiszef();
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
    
    public String wrocupr() {
        String zwrot = "/ksiegowa/ksiegowaZmianapodatnika.xhtml?faces-redirect=true";
        return zwrot;
    }
    
    public String wrocGuestfk() {
        String zwrot = "/guestFaktura/guestFakturaTablica.xhtml?faces-redirect=true";
        return zwrot;
    }
    //swiezowpisany uzer nie ma ustawionych parametrow
    private void inicjacjaUz() {
        miesiacWpisu = Data.aktualnyMc();
        uzer.setMiesiacWpisu(Data.aktualnyMc());
        uzer.setMiesiacOd(Data.aktualnyMc());
        uzer.setMiesiacDo(Data.aktualnyMc());
        uzer.setRokWpisu(Integer.parseInt(Data.aktualnyRok()));
        miesiacWpisuArchiwum = Data.aktualnyMc();
        uzer.setRokWpisu(Roki.getRokiListS().get(Roki.getRokiListS().size() - 1));
        String nipfirmy;
        Podatnik podatnik;
        try {
            nipfirmy = uzer.getFirma();
            podatnik = podatnikDAO.findPodatnikByNIP(nipfirmy);
        } catch (Exception e) {
            E.e(e);
            //laduje demofirme jak cos pojdzie zle
            nipfirmy = "1111005008";
            podatnik = podatnikDAO.findPodatnikByNIP(nipfirmy);
        }
        uzer.setPodatnik(podatnik);
        uzDAO.edit(uzer);
        podatnikObiekt = podatnik;
    }
    
     private void inicjacjaUzDaty() {
        miesiacWpisu = Data.aktualnyMc();
        uzer.setMiesiacWpisu(Data.aktualnyMc());
        uzer.setMiesiacOd(Data.aktualnyMc());
        uzer.setMiesiacDo(Data.aktualnyMc());
        miesiacWpisuArchiwum = Data.aktualnyMc();
        uzer.setRokWpisu(Integer.parseInt(Data.aktualnyRok()));
        uzDAO.edit(uzer);
    }
    private Uz pobierzWpisBD() {
        Uz uz = null;
        try {
            String wprowadzilX = getPrincipal().getName();
            uz = uzDAO.findUzByLogin(wprowadzilX);
        } catch (Exception e) {
            E.e(e); 
        } 
        return uz;
    }
    
    private void obsluzMce() {
        try {
            if (miesiacOd == null) {
                miesiacOd = miesiacWpisu;
                miesiacDo = miesiacWpisu;
            }
        } catch (Exception e) {
            E.e(e);
            miesiacOd = uzer.getMiesiacOd();
            miesiacDo = uzer.getMiesiacDo();
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
        if (uzer.getPodatnik()==null || !uzer.getPodatnik().equals(podatnikObiekt)) {
            uzer.setPodatnik(podatnikObiekt);
            //error.E.s("zmiana podatnika na "+podatnikObiekt.getPrintnazwa());
        }
        if (!miesiacWpisu.equals("CR")) {
            uzer.setMiesiacWpisu(miesiacWpisu);
            miesiacWpisuArchiwum = miesiacWpisu;
        } else if (miesiacWpisu.equals("CR")){
            uzer.setMiesiacWpisu(Data.aktualnyMc());
        }
        uzer.setRokWpisu(rokWpisu);
        uzer.setMiesiacOd(miesiacOd);
        uzer.setMiesiacDo(miesiacDo);
        uzDAO.edit(uzer);
        uzupelnijdanepodatnika();
        czyniegosc();
       czytojetsbiuroiszef();
    }
    
    private void czytojetsbiuroiszef() {
        biuroiszef = true;
        boolean czybiuro = getPodatnikObiekt().getNip().equals("8511005008");
        if (czybiuro) {
            biuroiszef = false;
            boolean czyszef = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser().equals("szef");
            boolean czyrenata = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser().equals("renata");
            if (czyszef || czyrenata) {
                biuroiszef = true;
            }
        }
    }
    
     public void aktualizuj(){
        uzer.setMiesiacWpisu(miesiacWpisu);
        uzer.setRokWpisu(rokWpisu);
        uzer.setPodatnik(podatnikObiekt);
        uzDAO.edit(uzer);
        naniesDaneDoWpis();
        init();
    }

    private Principal getPrincipal() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        return request.getUserPrincipal();
    }
    
    
//    public String findNazwaPodatnika() {
//        String wprowadzilX = getPrincipal().getName();
//        uzer = uzDAO.findUzByLogin(wprowadzilX);
//        Wpis wpis = wpisDAO.find(wprowadzilX);
//        if (wpis.getPodatnikWpisu() != null) {
//            return wpis.getPodatnikWpisu();
//        } else {
//            Podatnik podatnik = podatnikDAO.findPodatnikByNIP(uzer.getFirma());
//            String nazwapelna = podatnik.getNazwapelna();
//            wpis.setPodatnikWpisu(nazwapelna);
//            wpisDAO.edit(wpis);
//            return nazwapelna;
//        }
//    }

  
    private void uzupelnijdanepodatnika() {
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
           // E.e(e1);
        }
    }

    private void obsluzPiatki() {
        paramCzworkiPiatki = false;
        if (podatnikObiekt!=null && podatnikObiekt.getParamCzworkiPiatki()!=null) {
            List<ParamCzworkiPiatki> piatki = podatnikObiekt.getParamCzworkiPiatki();
            if (piatki != null && (!piatki.isEmpty())) {
                for (ParamCzworkiPiatki p : piatki) {
                    if (p.getRokOd().equals(rokWpisuSt)) {
                        paramCzworkiPiatki = (p.getParametr().equals("tak") ? true : false);
                    }
                }
            }
        }
    }
    
       
    private String sprawdzjakiokresvat() {
        Integer rok = this.getRokWpisu();
        Integer mc = Integer.parseInt(this.getMiesiacWpisu());
        List<Parametr> parametry = this.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }
    
    public boolean sprawdzczyue() {
        Integer rok = this.getRokWpisu();
        Integer mc = Integer.parseInt(this.getMiesiacWpisu());
        List<ParamVatUE> parametry = this.getPodatnikObiekt().getParamVatUE();
        return !ParametrView.zwrocParametr(parametry, rok, mc).equals("blad");
    }
    
    private void pobierzOpodatkowanie() {
        try {
            PodatnikOpodatkowanieD opodatkowanie = zwrocFormaOpodatkowania(rokWpisuSt);
            mc0kw1 = opodatkowanie.isMc0kw1();
            rokzamkniety = opodatkowanie.isZamkniety();
            rokpoprzednizamkniety = zwrocrokpoprzednizamkniety();
            rodzajopodatkowania = opodatkowanie.getFormaopodatkowania();
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
                vatowiecue = sprawdzczyue();
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
            if (podatnikObiekt.getFormaPrawna() == null && ksiegirachunkowe) {
                czegosbrakuje = true;
                Msg.msg("e", "Brak wyboru formy prawnej");
            }
        } catch (Exception e) {
            czegosbrakuje = true;
            //E.e(e);
        }
    }
    
    private boolean ilerodzajowopodatkowania() {
        boolean jedno = true;
        List lista = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRokWiele(podatnikObiekt, rokWpisuSt);
        if (lista!=null && lista.size()>1) {
            jedno = false;
        }
        return jedno;
    }
    
    private double stawkapodatkuospr() {
        return podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(podatnikObiekt, rokWpisuSt).getStawkapodatkuospr();
    }
    
    public PodatnikOpodatkowanieD zwrocFormaOpodatkowania(String rok) {
        PodatnikOpodatkowanieD zwrot = null;
        boolean jedno = ilerodzajowopodatkowania();
        if (jedno) {
            zwrot = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRok(podatnikObiekt, rok);
            odjakiegomcdok = "01";
        } else {
            List<PodatnikOpodatkowanieD> lista = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnikRokWiele(podatnikObiekt, rok);
            for (PodatnikOpodatkowanieD p : lista) {
                boolean jestmiedzy = Data.czyjestpomiedzy(p.getDatarozpoczecia(), p.getDatazakonczenia(), rok, miesiacWpisu);
                if (jestmiedzy) {
                    if (p.getSymbolroku().equals(rokWpisuSt)) {
                        odjakiegomcdok = p.getMcOd();
                    }
                    zwrot=p;
                    break;
                }
            }
        }
        return zwrot;
    }
    

    private boolean zwrocrokpoprzednizamkniety() {
        boolean zwrot = false;
        try {
            zwrot = zwrocFormaOpodatkowania(rokUprzedniSt).isZamkniety();
        } catch (Exception e) {
        }
        return zwrot;
    }
    

    public String skierujmultisuera() {
        rodzajopodatkowania = zwrocFormaOpodatkowania(rokWpisuSt).getFormaopodatkowania();
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
        if (this.uzer.getUprawnienia().equals("Guest")||this.uzer.getUprawnienia().equals("GuestFK")||this.uzer.getUprawnienia().equals("GuestFaktura")||this.uzer.getUprawnienia().equals("Multiuser")||this.uzer.getUprawnienia().equals("Dedra")) {
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

    public boolean isVatowiecue() {
        return vatowiecue;
    }

    public void setVatowiecue(boolean vatowiecue) {
        this.vatowiecue = vatowiecue;
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
        return podatnikObiekt.getNazwapelna();
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
    
    public Uz getUzer() {
        return uzer;
    }
    
    public void setUzer(Uz uzer) {
        this.uzer = uzer;
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

    private void pobierzformaprawna() {
        try {
            if (podatnikObiekt.getFormaPrawna() != null) {
                formaprawna = podatnikObiekt.getFormaPrawna().toString();
            } else {
                formaprawna = null;
            }
        } catch (Exception e){
            error.E.s("blad WpisView pobierzformaprawna");
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

    public Podatnik getPodatnikwzorcowy() {
        return podatnikwzorcowy;
    }

    public void setPodatnikwzorcowy(Podatnik podatnikwzorcowy) {
        this.podatnikwzorcowy = podatnikwzorcowy;
    }
    
    
//</editor-fold>

    public String getOdjakiegomcdok() {
        return odjakiegomcdok;
    }

    public void setOdjakiegomcdok(String odjakiegomcdok) {
        this.odjakiegomcdok = odjakiegomcdok;
    }

    public boolean isBiuroiszef() {
        return biuroiszef;
    }

    public void setBiuroiszef(boolean biuroiszef) {
        this.biuroiszef = biuroiszef;
    }
  

  }
