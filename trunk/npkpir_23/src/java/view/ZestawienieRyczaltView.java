/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.RyczDAO;
import dao.WpisDAO;
import dao.ZobowiazanieDAO;
import embeddable.Mce;
import embeddable.RyczaltPodatek;
import embeddable.Straty1;
import embeddable.Udzialy;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Pitpoz;
import entity.Podatnik;
import entity.Ryczpoz;
import entity.Wpis;
import entity.Zobowiazanie;
import entity.Zusstawki;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdf.PdfPIT28;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "ZestawienieRyczaltView")
@ViewScoped
public class ZestawienieRyczaltView implements Serializable {
    //dane niezbedne do wyliczania pit
    private String wybranyudzialowiec;

    @Inject
    private DokDAO dokDAO;
    @Inject
    private RyczDAO pitDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    //bieżący pit
    private Ryczpoz pitpoz;
    //sumowanie poprzednich pitów jeżeli są zachowane
    private Ryczpoz narPitpoz;
    //lista pitow
    private List<Ryczpoz> listapit;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    List<Double> styczen;
    List<Double> luty;
    List<Double> marzec;
    List<Double> kwiecien;
    List<Double> maj;
    List<Double> czerwiec;
    List<Double> lipiec;
    List<Double> sierpien;
    List<Double> wrzesien;
    List<Double> pazdziernik;
    List<Double> listopad;
    List<Double> grudzien;
    List<Double> Ipolrocze;
    List<Double> IIpolrocze;
    List<Double> rok;
    private List<Dok> lista;
    private List<Ryczpoz> pobierzPity;
    private List<List> zebranieMcy;
    @Inject private Ryczpoz biezacyPit;
    @Inject private PodStawkiDAO podstawkiDAO;
    @Inject private ZobowiazanieDAO zobowiazanieDAO;
    private String wybranyprocent;
    private List<String> listawybranychudzialowcow;
     //z reki
    private boolean zus51zreki;
    private boolean zus52zreki;
    @Inject private WpisDAO wpisDAO;

    public ZestawienieRyczaltView() {
        styczen = Arrays.asList(new Double[4]);
        styczen = Arrays.asList(new Double[4]);
        luty = Arrays.asList(new Double[4]);
        marzec = Arrays.asList(new Double[4]);
        kwiecien = Arrays.asList(new Double[4]);
        maj = Arrays.asList(new Double[4]);
        czerwiec = Arrays.asList(new Double[4]);
        lipiec = Arrays.asList(new Double[4]);
        sierpien = Arrays.asList(new Double[4]);
        wrzesien = Arrays.asList(new Double[4]);
        pazdziernik = Arrays.asList(new Double[4]);
        listopad = Arrays.asList(new Double[4]);
        grudzien = Arrays.asList(new Double[4]);
        pobierzPity = new ArrayList<>();
        zebranieMcy = new ArrayList<>();
        listapit = new ArrayList<>();
        listawybranychudzialowcow = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Podatnik pod = podatnikDAO.find(wpisView.getPodatnikWpisu());
            try {
                for (Udzialy p : pod.getUdzialy()) {
                    listawybranychudzialowcow.add(p.getNazwiskoimie());

                }
            } catch (Exception e) { E.e(e); 
                Msg.msg("e", "Nie uzupełniony wykaz udziałów", "formpit:messages");
            }
            Collection c = null;
            try {
                c = dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString());
            } catch (Exception e) { E.e(e); 
            }
            if (c != null) {
                for (int i = 0; i < 4; i++) {
                    styczen.set(i, 0.0);
                    luty.set(i, 0.0);
                    marzec.set(i, 0.0);
                    kwiecien.set(i, 0.0);
                    maj.set(i, 0.0);
                    czerwiec.set(i, 0.0);
                    lipiec.set(i, 0.0);
                    sierpien.set(i, 0.0);
                    wrzesien.set(i, 0.0);
                    pazdziernik.set(i, 0.0);
                    listopad.set(i, 0.0);
                    grudzien.set(i, 0.0);
                }
                lista = new ArrayList<>();
                lista.addAll(c);
                for (Dok dokument : lista) {
                    List<KwotaKolumna1> szczegol = dokument.getListakwot1();
                    for (KwotaKolumna1 tmp : szczegol) {
                        String selekcja = dokument.getPkpirM();
                        String selekcja2 = tmp.getNazwakolumny();
                        Double kwota = tmp.getNetto();
                        Double temp = 0.0;
                        switch (selekcja) {
                            case "01":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = styczen.get(0) + kwota;
                                        styczen.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = styczen.get(1) + kwota;
                                        styczen.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = styczen.get(2) + kwota;
                                        styczen.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = styczen.get(3) + kwota;
                                        styczen.set(3, temp);
                                        break;
                                }
                                break;
                            case "02":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = luty.get(0) + kwota;
                                        luty.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = luty.get(1) + kwota;
                                        luty.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = luty.get(2) + kwota;
                                        luty.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = luty.get(3) + kwota;
                                        luty.set(3, temp);
                                        break;
                                }
                                break;
                            case "03":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = marzec.get(0) + kwota;
                                        marzec.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = marzec.get(1) + kwota;
                                        marzec.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = marzec.get(2) + kwota;
                                        marzec.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = marzec.get(3) + kwota;
                                        marzec.set(3, temp);
                                        break;
                                }
                                break;
                            case "04":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = kwiecien.get(0) + kwota;
                                        kwiecien.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = kwiecien.get(1) + kwota;
                                        kwiecien.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = kwiecien.get(2) + kwota;
                                        kwiecien.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = kwiecien.get(3) + kwota;
                                        kwiecien.set(3, temp);
                                        break;
                                }
                                break;
                            case "05":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = maj.get(0) + kwota;
                                        maj.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = maj.get(1) + kwota;
                                        maj.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = maj.get(2) + kwota;
                                        maj.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = maj.get(3) + kwota;
                                        maj.set(3, temp);
                                        break;
                                }
                                break;
                            case "06":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = czerwiec.get(0) + kwota;
                                        czerwiec.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = czerwiec.get(1) + kwota;
                                        czerwiec.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = czerwiec.get(2) + kwota;
                                        czerwiec.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = czerwiec.get(3) + kwota;
                                        czerwiec.set(3, temp);
                                        break;
                                }
                                break;
                            case "07":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = lipiec.get(0) + kwota;
                                        lipiec.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = lipiec.get(1) + kwota;
                                        lipiec.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = lipiec.get(2) + kwota;
                                        lipiec.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = lipiec.get(3) + kwota;
                                        lipiec.set(3, temp);
                                        break;
                                }
                                break;
                            case "08":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = sierpien.get(0) + kwota;
                                        sierpien.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = sierpien.get(1) + kwota;
                                        sierpien.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = sierpien.get(2) + kwota;
                                        sierpien.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = sierpien.get(3) + kwota;
                                        sierpien.set(3, temp);
                                        break;
                                }
                                break;
                            case "09":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = wrzesien.get(0) + kwota;
                                        wrzesien.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = wrzesien.get(1) + kwota;
                                        wrzesien.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = wrzesien.get(2) + kwota;
                                        wrzesien.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = wrzesien.get(3) + kwota;
                                        wrzesien.set(3, temp);
                                        break;
                                }
                                break;
                            case "10":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = pazdziernik.get(0) + kwota;
                                        pazdziernik.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = pazdziernik.get(1) + kwota;
                                        pazdziernik.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = pazdziernik.get(2) + kwota;
                                        pazdziernik.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = pazdziernik.get(3) + kwota;
                                        pazdziernik.set(3, temp);
                                        break;
                                }
                                break;
                            case "11":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = listopad.get(0) + kwota;
                                        listopad.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = listopad.get(1) + kwota;
                                        listopad.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = listopad.get(2) + kwota;
                                        listopad.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = listopad.get(3) + kwota;
                                        listopad.set(3, temp);
                                        break;
                                }
                                break;
                            case "12":
                                switch (selekcja2) {
                                    case "17%":
                                        temp = grudzien.get(0) + kwota;
                                        grudzien.set(0, temp);
                                        break;
                                    case "8.5%":
                                        temp = grudzien.get(1) + kwota;
                                        grudzien.set(1, temp);
                                        break;
                                    case "5.5%":
                                        temp = grudzien.get(2) + kwota;
                                        grudzien.set(2, temp);
                                        break;
                                   case "3%":
                                        temp = grudzien.get(3) + kwota;
                                        grudzien.set(3, temp);
                                        break;
                                }
                                break;
                        }
                    }
                }
                //pobierzPity();
                    zebranieMcy.add(styczen);
                    zebranieMcy.add(luty);
                    zebranieMcy.add(marzec);
                    zebranieMcy.add(kwiecien);
                    zebranieMcy.add(maj);
                    zebranieMcy.add(czerwiec);
                    zebranieMcy.add(lipiec);
                    zebranieMcy.add(sierpien);
                    zebranieMcy.add(wrzesien);
                    zebranieMcy.add(pazdziernik);
                    zebranieMcy.add(listopad);
                    zebranieMcy.add(grudzien);

                Ipolrocze = new ArrayList<>();
                IIpolrocze = new ArrayList<>();
                rok = new ArrayList<>();

                for (int i = 0; i < 4; i++) {
                    Ipolrocze.add(styczen.get(i) + luty.get(i) + marzec.get(i) + kwiecien.get(i) + maj.get(i) + czerwiec.get(i));
                    IIpolrocze.add(lipiec.get(i) + sierpien.get(i) + wrzesien.get(i) + pazdziernik.get(i) + listopad.get(i) + grudzien.get(i));
                    rok.add(Ipolrocze.get(i) + IIpolrocze.get(i));
                }
            }
        }
    }

    //oblicze pit ryczałtowca  i wkleja go do biezacego Pitu w celu wyswietlenia, nie zapisuje
    public void obliczPit() {
        if (!wybranyudzialowiec.equals("wybierz osobe")) {
           
                Podatnik tmpP = podatnikDAO.find(wpisView.getPodatnikWpisu());
                List<Udzialy> lista = tmpP.getUdzialy();
                for (Udzialy p : lista) {
                    if (p.getNazwiskoimie().equals(wybranyudzialowiec)) {
                        wybranyprocent = p.getUdzial();
                        break;
                    }
                }
                biezacyPit.setPodatnik(wpisView.getPodatnikWpisu());
                biezacyPit.setPkpirR(wpisView.getRokWpisu().toString());
                biezacyPit.setPkpirM(wpisView.getMiesiacWpisu());
                obliczprzychod();
                wyciagnijprzychodsuma();
                double procent = Double.parseDouble(wybranyprocent) / 100;
                biezacyPit.setPrzychodyudzial(biezacyPit.getPrzychody().multiply(new BigDecimal(procent)));
                biezacyPit.setWynik(biezacyPit.getPrzychodyudzial());
                biezacyPit.setUdzialowiec(wybranyudzialowiec);
                biezacyPit.setUdzial(wybranyprocent);
                try {
                Podatnik selected = wpisView.getPodatnikObiekt();
                Iterator it;
                it = selected.getZusparametr().iterator();
                if(zus51zreki==false){
                while (it.hasNext()) {
                    Zusstawki tmpX = (Zusstawki) it.next();
                    if (tmpX.getZusstawkiPK().getRok().equals(wpisView.getRokWpisu().toString())
                            && tmpX.getZusstawkiPK().getMiesiac().equals(wpisView.getMiesiacWpisu())) {
                        if (selected.getOdliczaczus51() == true) {
                            if (tmpX.getZus51ch() != null) {
                                biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51ch()));
                            } else {
                                biezacyPit.setZus51(BigDecimal.valueOf(tmpX.getZus51bch()));
                            }
                        } else {
                            biezacyPit.setZus51(new BigDecimal(0));
                        }
                        if(zus52zreki==false){
                        biezacyPit.setZus52(BigDecimal.valueOf(tmpX.getZus52odl()));
                        }
                        break;
                    }
                }
                }
                } catch (Exception e) { E.e(e); 
                    Msg.msg("e", "Brak wpisanych stawek ZUS-51,52 indywidualnych dla danego klienta. Jeżeli ZUS 51 nie ma być odliczany, sprawdź czy odpowiednia opcja jest wybrana w ustwieniach klienta");
                    biezacyPit = new Ryczpoz();
                    wybranyudzialowiec = "wybierz osobe";
                    return;
                }
             try {
                rozliczstrate(tmpP);
                obliczpodatek();
                if(biezacyPit.getZus52() != null) {
                    if (biezacyPit.getPodatek().subtract(biezacyPit.getZus52()).signum()==1){
                        BigDecimal tmpX = biezacyPit.getPodatek().subtract(biezacyPit.getZus52());
                        tmpX = tmpX.setScale(0, RoundingMode.HALF_EVEN);
                        biezacyPit.setNaleznazal(tmpX);
                    } else {
                        biezacyPit.setNaleznazal(BigDecimal.ZERO);
                    }
                } else {
                    biezacyPit.setNaleznazal(biezacyPit.getPodatek());
                }
                if (biezacyPit.getNaleznazal().compareTo(BigDecimal.ZERO) == 1) {
                    biezacyPit.setDozaplaty(biezacyPit.getNaleznazal());
                } else {
                    biezacyPit.setDozaplaty(BigDecimal.ZERO);
                }
            } catch (Exception e) { E.e(e); 
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak wprowadzonych stawek podatkowych na dany rok! Nie można przeliczyć ryczałtu za okres ", biezacyPit.getPkpirM());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                biezacyPit = new Ryczpoz();
                wybranyudzialowiec = "wybierz osobe";
            }
            try {
                Zobowiazanie data = zobowiazanieDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM());
                biezacyPit.setTerminwplaty(data.getZobowiazaniePK().getRok() + "-" + data.getZobowiazaniePK().getMc() + "-" + data.getPitday());
            } catch (Exception e) { E.e(e); 
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak wprowadzonych dat płatności zobowiazan  w danym okresie! Nie można przeliczyć ryczałtu", biezacyPit.getPkpirM());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                biezacyPit = new Ryczpoz();
                wybranyudzialowiec = "wybierz osobe";
                RequestContext.getCurrentInstance().update("formpit1:");
            }
            RequestContext.getCurrentInstance().update("formpit1:");
        }
    }

    private void rozliczstrate(Podatnik tmp) {
        List<Straty1> straty = tmp.getStratyzlatub1();
        double sumastrat = 0.0;
        try {
            Double zostalo = wyliczStrataZostalo(tmp);
            for (Straty1 p : straty) {
                Double wyliczmaks = zostalo - Double.parseDouble(p.getPolowakwoty());
                if (wyliczmaks > 0) {
                    sumastrat += Double.parseDouble(p.getPolowakwoty());
                } else {
                    sumastrat += zostalo;
                }
            }
             BigDecimal wynikpozus = biezacyPit.getWynik().subtract(biezacyPit.getZus51());
            if (wynikpozus.signum() == 1) {
                BigDecimal stratadoujecia = wynikpozus.subtract(new BigDecimal(sumastrat));
                if (stratadoujecia.signum() == -1) {
                    biezacyPit.setStrata(wynikpozus);
                } else {
                    biezacyPit.setStrata(new BigDecimal(sumastrat));
                }
            } else {
                biezacyPit.setStrata(BigDecimal.ZERO);
            }
        } catch (Exception e) { E.e(e); 
            biezacyPit.setStrata(BigDecimal.ZERO);
        }
    }

    //wyliczenie niezbedne przy wracaniu do historycznych pitow
    private double wyliczStrataZostalo(Podatnik tmp) {
        double zostalo = 0.0;
        for (Straty1 r : tmp.getStratyzlatub1()) {
            double sumabiezace = 0.0;
             for (Straty1.Wykorzystanie s : r.getWykorzystanieBiezace()) {
                 if (Integer.parseInt(s.getRokwykorzystania())<wpisView.getRokWpisu()) {
                    sumabiezace += s.getKwotawykorzystania();
                    sumabiezace = Math.round(sumabiezace * 100.0) / 100.0;
                 }
             }
             double kwota = Double.parseDouble(r.getKwota());
             double uprzednio = Double.parseDouble(r.getWykorzystano());
             double biezace = sumabiezace;
             zostalo += Math.round((kwota-uprzednio-biezace) * 100.0) / 100.0;
         }
        return zostalo;
    }
    
    
    public void zachowajPit() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        FacesContext facesCtx = FacesContext.getCurrentInstance();
        if (biezacyPit.getWynik() != null) {
            try {
                Ryczpoz find = pitDAO.find(biezacyPit.getPkpirR(), biezacyPit.getPkpirM(), biezacyPit.getPodatnik(), biezacyPit.getUdzialowiec());
                pitDAO.destroy(find);
                pitDAO.dodaj(biezacyPit);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Edytowano PIT " + biezacyPit.getUdzialowiec() + " za m-c:" + biezacyPit.getPkpirM(), null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e) { E.e(e); 
                pitDAO.dodaj(biezacyPit);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Zachowano PIT " + biezacyPit.getUdzialowiec() + " za m-c:" + biezacyPit.getPkpirM(), null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }

        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nie można zachować. PIT nie wypełniony", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

     public void zachowajPit13() {
        biezacyPit.setPkpirM("13");
        zachowajPit();
    }
     
      public void aktualizujPIT(AjaxBehaviorEvent e) {
        aktualizuj();
        RequestContext.getCurrentInstance().update("formpit1");
        wybranyudzialowiec = "wybierz osobe";
        Msg.msg("i", "Zmieniono miesiąc obrachunkowy.");
    }
    
    private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.findWpis();
    }

    

    private void obliczprzychod() {
        List<RyczaltPodatek> podatkibiezace = new ArrayList<>();
        String selekcja = wpisView.getMiesiacWpisu();
        int miesiacint = Mce.getMiesiacToNumber().get(selekcja)-1  ;
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 17%", 0.17, miesiacint, 0));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 8,5%", 0.085, miesiacint, 1));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 5,5%", 0.055, miesiacint, 2));
        podatkibiezace.add(pobranieprzychodu("Przychody opodatkowane stawką 3%", 0.03, miesiacint, 3));
        biezacyPit.setListapodatkow(podatkibiezace);
    }

    private RyczaltPodatek pobranieprzychodu(String opis, double stawka, int miesiac, int pozycja){
        BigDecimal suma = new BigDecimal(0);
        BigDecimal podatek = new BigDecimal(0);
        suma = suma.add(BigDecimal.valueOf(Double.valueOf(zebranieMcy.get(miesiac).get(pozycja).toString())));
        suma = suma.setScale(2, RoundingMode.HALF_EVEN);
        RyczaltPodatek podtk = new RyczaltPodatek();
        podtk.setOpis(opis);
        podtk.setStawka(stawka);
        podtk.setPrzychod(suma.doubleValue());
        podtk.setUdzialprocentowy(0.0);
        podtk.setPodstawa(0.0);
        podtk.setZmniejszenie(0.0);
        podtk.setPodatek(0.0);
        return podtk;
    }
    
    private void wyciagnijprzychodsuma(){
         List<RyczaltPodatek> podatkibiezace = new ArrayList<>();
         BigDecimal suma = new BigDecimal(0);
         podatkibiezace = biezacyPit.getListapodatkow();
         for(RyczaltPodatek p : podatkibiezace){
             suma = suma.add(new BigDecimal(p.getPrzychod()));
         }
         biezacyPit.setPrzychody(suma);
    }
    
    private void obliczpodatek() {
        wyliczprocent(biezacyPit.getPrzychody());
        wyliczodliczenieZmniejszenia();
        wyliczpodatek();
    }
    
    private void wyliczprocent(BigDecimal suma){
        try{
        for(RyczaltPodatek p : biezacyPit.getListapodatkow()){
            BigDecimal wartosc = new BigDecimal(p.getPrzychod());
            BigDecimal procent = wartosc.divide(suma, 20, RoundingMode.HALF_EVEN);
            procent = procent.setScale(4, RoundingMode.HALF_EVEN);
            p.setUdzialprocentowy(procent.doubleValue());
        }
        } catch (Exception e) { E.e(e); 
           FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak przychodow w miesiacu", "");
           FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    private void wyliczodliczenieZmniejszenia(){
        for(RyczaltPodatek p : biezacyPit.getListapodatkow()){
            BigDecimal wartosc = new BigDecimal(p.getUdzialprocentowy());
            BigDecimal tmp = new BigDecimal(0.0);
            try {
                BigDecimal zus51 = wartosc.multiply(biezacyPit.getZus51());
                zus51 = zus51.setScale(2, RoundingMode.HALF_EVEN);
                tmp = zus51;
            } catch (Exception e1) {
                
            }
            try {
                BigDecimal strata = wartosc.multiply(biezacyPit.getStrata());
                strata = strata.setScale(2, RoundingMode.HALF_EVEN);
                tmp = tmp.add(strata);
                p.setZmniejszenie(tmp.doubleValue());
            } catch (Exception e1) {
                
            }
        }
    }
    
    private void wyliczpodatek(){
        BigDecimal podateksuma = new BigDecimal(BigInteger.ZERO);
        BigDecimal podstawasuma = new BigDecimal(BigInteger.ZERO);
        for(RyczaltPodatek p : biezacyPit.getListapodatkow()){
        BigDecimal wynik = (new BigDecimal(p.getPrzychod())).subtract(new BigDecimal(p.getZmniejszenie()));
        wynik = wynik.setScale(0, RoundingMode.HALF_EVEN);
        podstawasuma = podstawasuma.add(wynik);
        p.setPodstawa(wynik.doubleValue());
        BigDecimal podatek = wynik.multiply(new BigDecimal(p.getStawka()));
        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
        podateksuma = podateksuma.add(podatek);
        p.setPodatek(podatek.doubleValue());
    }
        biezacyPit.setPodstawa(podstawasuma);
        biezacyPit.setPodatek(podateksuma);
    }

    public void pobierzPity() {
        try {
            pobierzPity.addAll(pitDAO.findAll());
        } catch (Exception e) { E.e(e); 
        }
        narPitpoz = new Ryczpoz();
        int index = 0;
        Iterator it;
        it = pobierzPity.iterator();
        while (it.hasNext()) {
            Pitpoz tmpX = (Pitpoz) it.next();
            if (tmpX.getPkpirR().equals(wpisView.getRokWpisu().toString())
                    && tmpX.getPkpirM().equals(wpisView.getMiesiacWpisu())) {
                index = tmpX.getId() - 1;
                break;
            }
        }
        //narPitpoz = pobierzPity.get(Mce.getMapamcyX().get(wpisView.getMiesiacWpisu()));
        narPitpoz = pobierzPity.get(index);
        biezacyPit = narPitpoz;
    }
    
    public void drukujRyczalt() {
        try {
            PdfPIT28.drukuj(biezacyPit, wpisView, podatnikDAO);
        } catch (Exception e) { E.e(e); 
            
        }
    }
   
    
    

    public DokDAO getDokDAO() {
        return dokDAO;
    }

    public void setDokDAO(DokDAO dokDAO) {
        this.dokDAO = dokDAO;
    }

    public RyczDAO getPitDAO() {
        return pitDAO;
    }

    public void setRyczDAO(RyczDAO pitDAO) {
        this.pitDAO = pitDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Dok> getLista() {
        return lista;
    }

    public void setLista(List<Dok> lista) {
        this.lista = lista;
    }

    public List<Double> getStyczen() {
        return styczen;
    }

    public void setStyczen(List<Double> styczen) {
        this.styczen = styczen;
    }

    public List<Double> getLuty() {
        return luty;
    }

    public void setLuty(List<Double> luty) {
        this.luty = luty;
    }

    public List<Double> getMarzec() {
        return marzec;
    }

    public void setMarzec(List<Double> marzec) {
        this.marzec = marzec;
    }

    public List<Double> getKwiecien() {
        return kwiecien;
    }

    public void setKwiecien(List<Double> kwiecien) {
        this.kwiecien = kwiecien;
    }

    public List<Double> getMaj() {
        return maj;
    }

    public void setMaj(List<Double> maj) {
        this.maj = maj;
    }

    public List<Double> getCzerwiec() {
        return czerwiec;
    }

    public void setCzerwiec(List<Double> czerwiec) {
        this.czerwiec = czerwiec;
    }

    public List<Double> getLipiec() {
        return lipiec;
    }

    public void setLipiec(List<Double> lipiec) {
        this.lipiec = lipiec;
    }

    public List<Double> getSierpien() {
        return sierpien;
    }

    public void setSierpien(List<Double> sierpien) {
        this.sierpien = sierpien;
    }

    public List<Double> getWrzesien() {
        return wrzesien;
    }

    public void setWrzesien(List<Double> wrzesien) {
        this.wrzesien = wrzesien;
    }

    public List<Double> getPazdziernik() {
        return pazdziernik;
    }

    public void setPazdziernik(List<Double> pazdziernik) {
        this.pazdziernik = pazdziernik;
    }

    public List<Double> getListopad() {
        return listopad;
    }

    public void setListopad(List<Double> listopad) {
        this.listopad = listopad;
    }

    public List<Double> getGrudzien() {
        return grudzien;
    }

    public void setGrudzien(List<Double> grudzien) {
        this.grudzien = grudzien;
    }

    public List<Double> getIpolrocze() {
        return Ipolrocze;
    }

    public void setIpolrocze(List<Double> Ipolrocze) {
        this.Ipolrocze = Ipolrocze;
    }

    public List<Double> getIIpolrocze() {
        return IIpolrocze;
    }

    public void setIIpolrocze(List<Double> IIpolrocze) {
        this.IIpolrocze = IIpolrocze;
    }

    public List<Double> getRok() {
        return rok;
    }

    public void setRok(List<Double> rok) {
        this.rok = rok;
    }

    public Ryczpoz getRyczpoz() {
        return pitpoz;
    }

    public void setRyczpoz(Ryczpoz pitpoz) {
        this.pitpoz = pitpoz;
    }

    public Ryczpoz getNarRyczpoz() {
        return narPitpoz;
    }

    public void setNarRyczpoz(Ryczpoz narRyczpoz) {
        this.narPitpoz = narRyczpoz;
    }

    public List<Ryczpoz> getPobierzPity() {
        return pobierzPity;
    }

    public void setPobierzPity(List<Ryczpoz> pobierzPity) {
        this.pobierzPity = pobierzPity;
    }

    public List<List> getZebranieMcy() {
        return zebranieMcy;
    }

    public void setZebranieMcy(List<List> zebranieMcy) {
        this.zebranieMcy = zebranieMcy;
    }

    public Ryczpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Ryczpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }

    public PodStawkiDAO getPodstawkiDAO() {
        return podstawkiDAO;
    }

    public void setPodstawkiDAO(PodStawkiDAO podstawkiDAO) {
        this.podstawkiDAO = podstawkiDAO;
    }

    public ZobowiazanieDAO getZobowiazanieDAO() {
        return zobowiazanieDAO;
    }

    public void setZobowiazanieDAO(ZobowiazanieDAO zobowiazanieDAO) {
        this.zobowiazanieDAO = zobowiazanieDAO;
    }

    public String getWybranyudzialowiec() {
        return wybranyudzialowiec;
    }

    public void setWybranyudzialowiec(String wybranyudzialowiec) {
        this.wybranyudzialowiec = wybranyudzialowiec;
    }

   

    public String getWybranyprocent() {
        return wybranyprocent;
    }

    public void setWybranyprocent(String wybranyprocent) {
        this.wybranyprocent = wybranyprocent;
    }

    public List<String> getListawybranychudzialowcow() {
        return listawybranychudzialowcow;
    }

    public void setListawybranychudzialowcow(List<String> listawybranychudzialowcow) {
        this.listawybranychudzialowcow = listawybranychudzialowcow;
    }

    public boolean isZus51zreki() {
        return zus51zreki;
    }

    public void setZus51zreki(boolean zus51zreki) {
        this.zus51zreki = zus51zreki;
    }

    public boolean isZus52zreki() {
        return zus52zreki;
    }

    public void setZus52zreki(boolean zus52zreki) {
        this.zus52zreki = zus52zreki;
    }
    
}
