/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.OdsetkiDAO;
import dao.PitDAO;
import dao.PlatnosciDAO;
import dao.PodatnikDAO;
import dao.WpisDAO;
import dao.ZobowiazanieDAO;
import entity.Deklaracjevat;
import entity.Odsetki;
import entity.Pitpoz;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
import entity.Wpis;
import entity.Zobowiazanie;
import entity.Zusstawki;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.security.Principal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "PlatnosciView")
@ViewScoped
public class PlatnosciView implements Serializable {
    private static final long serialVersionUID = 1L;
    private Platnosci selectedZob;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private PlatnosciDAO platnosciDAO;
    @Inject
    private OdsetkiDAO odsetkiDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private Podatnik biezacyPodanik;
    @Inject
    private ZobowiazanieDAO zv;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WpisDAO wpisDAO;

    private boolean edytujplatnosc;

    public PlatnosciView() {
    }

    @PostConstruct
    private void init() {
        HttpServletRequest request;
        request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        String nazwapodatnika = principal.getName();
        try {
            biezacyPodanik = podatnikDAO.find(wpisView.findNazwaPodatnika());
        } catch (Exception e) { E.e(e); 
        }
        pokazzobowiazania();
    }

    public void pokazzobowiazania() {
        selectedZob = new Platnosci();
        PlatnosciPK platnosciPK = new PlatnosciPK();
        platnosciPK.setRok(wpisView.getRokWpisu().toString());
        platnosciPK.setMiesiac(wpisView.getMiesiacWpisu());
        platnosciPK.setPodatnik(biezacyPodanik.getNazwapelna());
        selectedZob.setPlatnosciPK(platnosciPK);
        try {
            selectedZob = platnosciDAO.findPK(platnosciPK);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Platnosci były już raz zachowane. Pobrano je z archiwum", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            edytujplatnosc = true;
            RequestContext.getCurrentInstance().update("form:formZob");
        } catch (Exception e) { E.e(e); 
            nowezobowiazanie();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Wprowadź nowe daty przelewów.", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("form:formZob");
        }
    }

    public void nowezobowiazanie() {
        String rok = selectedZob.getPlatnosciPK().getRok();
        String mc = selectedZob.getPlatnosciPK().getMiesiac();
        String podatnik = biezacyPodanik.getNazwapelna();
        List<Zusstawki> listapobrana = biezacyPodanik.getZusparametr();
        if (listapobrana != null) {
            Zusstawki zusstawki = new Zusstawki();
            Iterator it;
            it = listapobrana.iterator();
            while (it.hasNext()) {
                Zusstawki tmp = (Zusstawki) it.next();
                if (tmp.getZusstawkiPK().getRok().equals(rok) && tmp.getZusstawkiPK().getMiesiac().equals(mc)) {
                    zusstawki = tmp;
                }
            }
            selectedZob.setZus51(zusstawki.getZus51ch());
            selectedZob.setZus52(zusstawki.getZus52());
            selectedZob.setZus53(zusstawki.getZus53());
            selectedZob.setPit4(zusstawki.getPit4());
        }
        Pitpoz pitpoz = new Pitpoz();
        //pobierz PIT-5
        try {
            pitpoz = pitDAO.find(rok, mc, podatnik);
            selectedZob.setPit5(pitpoz.getNaleznazal().doubleValue());
        } catch (Exception e) { E.e(e); 
            selectedZob.setPit5(0.0);
        }
        //pobierz VAT-7
        try {
            Deklaracjevat dekl = new Deklaracjevat();
            try {
                List<Deklaracjevat> deklaracje = deklaracjevatDAO.findDeklaracjewszystkie(rok, mc, podatnik);
                dekl = deklaracje.get(deklaracje.size() - 1);
                if (dekl.getPozycjeszczegolowe().getPoleI58() != 0) {
                    selectedZob.setVat(Double.parseDouble(dekl.getPozycjeszczegolowe().getPole58()));
                } else {
                    selectedZob.setVat(0 - Double.parseDouble(dekl.getPozycjeszczegolowe().getPole60()));
                }
            } catch (Exception e) { E.e(e); 
                selectedZob.setVat(0 - Double.parseDouble(dekl.getPozycjeszczegolowe().getPole60()));
            }
        } catch (Exception e) { E.e(e); 
            selectedZob.setVat(0.0);
        }
        List<Zobowiazanie> terminy = new ArrayList<>();
        terminy.addAll(zv.findAll());
        Zobowiazanie termin = new Zobowiazanie();
        Iterator itx;
        itx = terminy.iterator();
        while (itx.hasNext()) {
            Zobowiazanie tmp = (Zobowiazanie) itx.next();
            if (tmp.getZobowiazaniePK().getRok().equals(selectedZob.getPlatnosciPK().getRok())
                    && tmp.getZobowiazaniePK().getMc().equals(selectedZob.getPlatnosciPK().getMiesiac())) {
                termin = tmp;
            }
        }
        selectedZob.setTerminzuz(termin.getZusday1());
        selectedZob.setTerminzpit4(termin.getPitday());
        selectedZob.setTerminzpit5(termin.getPitday());
        selectedZob.setTerminzvat(termin.getVatday());
        selectedZob.setZus51ods(0.0);
        selectedZob.setZus52ods(0.0);
        selectedZob.setZus53ods(0.0);
        selectedZob.setPit4ods(0.0);
        selectedZob.setPit5ods(0.0);
        selectedZob.setVatods(0.0);
        try {
            selectedZob.setZus51suma(selectedZob.getZus51() + selectedZob.getZus51ods());
            selectedZob.setZus52suma(selectedZob.getZus52() + selectedZob.getZus51ods());
            selectedZob.setZus53suma(selectedZob.getZus53() + selectedZob.getZus51ods());
            selectedZob.setPit4suma(selectedZob.getPit4() + selectedZob.getPit4ods());
            selectedZob.setPit5suma(selectedZob.getPit5() + selectedZob.getPit5ods());
            Platnosci platnosci = new Platnosci();
            platnosci.setPit4(selectedZob.getPit4());
            platnosci.setPit5(selectedZob.getPit5());
            platnosci.setVat(selectedZob.getVat());
            platnosci.setZus51(selectedZob.getZus51());
            platnosci.setZus52(selectedZob.getZus52());
            platnosci.setZus53(selectedZob.getZus53());
            PlatnosciPK platnosciPK = new PlatnosciPK();
            platnosciPK.setMiesiac(mc);
            platnosciPK.setRok(rok);
            platnosciPK.setPodatnik(podatnik);
            platnosci.setPlatnosciPK(platnosciPK);
            platnosciDAO.edit(platnosci);
        } catch (Exception e) { E.e(e); 
        }
        //selectedZob.setVatsuma(selectedZob.getVat()+selectedZob.getVatods());
    }

    public void przeliczodsetki(int opcja) {
        String datatmp = selectedZob.getPlatnosciPK().getRok() + "-" + selectedZob.getPlatnosciPK().getMiesiac() + "-" + selectedZob.getTerminzuz();
        Date datatmp51 = selectedZob.getZus51zapl();
        Date datatmp52 = selectedZob.getZus52zapl();
        Date datatmp53 = selectedZob.getZus53zapl();
        Date dataod;
        Date datado;
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            dataod = formatter.parse(datatmp);
            selectedZob.setZus51ods(odsetki(dataod, datatmp51, selectedZob.getZus51().toString()));
            selectedZob.setZus52ods(odsetki(dataod, datatmp52, selectedZob.getZus52().toString()));
            selectedZob.setZus53ods(odsetki(dataod, datatmp53, selectedZob.getZus53().toString()));
            selectedZob.setZus51suma(selectedZob.getZus51() + selectedZob.getZus51ods());
            selectedZob.setZus52suma(selectedZob.getZus52() + selectedZob.getZus52ods());
            selectedZob.setZus53suma(selectedZob.getZus53() + selectedZob.getZus53ods());
        } catch (ParseException e) {
        }
        try {
            if (opcja == 1) {
                platnosciDAO.dodaj(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("akordeon:formZob:wiad");
            } else {
                platnosciDAO.edit(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci ponownie zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("akordeon:formZob:wiad");
            }
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Platnosci nie zachowane - PodatekView", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("akordeon:formZob:wiad");
        }
    }

    public void przeliczodsetkiPIT4(int opcja) {
        String datatmp = selectedZob.getPlatnosciPK().getRok() + "-" + selectedZob.getPlatnosciPK().getMiesiac() + "-" + selectedZob.getTerminzpit4();
        Date datatmpPIT4 = selectedZob.getPit4zapl();
        Date dataod;
        Date datado;
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            dataod = formatter.parse(datatmp);
            selectedZob.setPit4ods(odsetki(dataod, datatmpPIT4, selectedZob.getPit4().toString()));
            selectedZob.setPit4suma(selectedZob.getPit4() + selectedZob.getPit4ods());
        } catch (ParseException e) {
        }
        try {
            if (opcja == 1) {
                platnosciDAO.dodaj(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("akordeon:formZob1:wiad1");
            } else {
                platnosciDAO.edit(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci ponownie zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("akordeon:formZob1:wiad1");
            }
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Platnosci nie zachowane - PodatekView", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("akordeon:formZob1:wiad1");
        }
    }

    public void przeliczodsetkiPIT5(int opcja) {
        String datatmp = selectedZob.getPlatnosciPK().getRok() + "-" + selectedZob.getPlatnosciPK().getMiesiac() + "-" + selectedZob.getTerminzpit4();
        Date datatmpPIT5 = selectedZob.getPit5zapl();
        Date dataod;
        Date datado;
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            dataod = formatter.parse(datatmp);
            selectedZob.setPit5ods(odsetki(dataod, datatmpPIT5, selectedZob.getPit5().toString()));
            selectedZob.setPit5suma(selectedZob.getPit5() + selectedZob.getPit5ods());
        } catch (ParseException e) {
        }
        try {
            if (opcja == 1) {
                platnosciDAO.dodaj(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("akordeon:formZob1:wiad1");
            } else {
                platnosciDAO.edit(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci ponownie zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("akordeon:formZob1:wiad1");
            }
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Platnosci nie zachowane - PodatekView", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("akordeon:formZob1:wiad1");
        }
    }
    
    public void przeliczodsetkiVAT(int opcja) {
        String datatmp = selectedZob.getPlatnosciPK().getRok() + "-" + selectedZob.getPlatnosciPK().getMiesiac() + "-" + selectedZob.getTerminzpit4();
        Date datatmpVAT = selectedZob.getVatzapl();
        Date dataod;
        Date datado;
        try {
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            dataod = formatter.parse(datatmp);
            selectedZob.setVatods(odsetki(dataod, datatmpVAT, selectedZob.getVat().toString()));
            selectedZob.setVatsuma(selectedZob.getVat()+ selectedZob.getVatods());
        } catch (ParseException e) {
        }
        try {
            if (opcja == 1) {
                platnosciDAO.dodaj(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("akordeon:formZob1:wiad1");
            } else {
                platnosciDAO.edit(selectedZob);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Platnosci ponownie zachowane - PodatekView", "");
                FacesContext.getCurrentInstance().addMessage(null, msg);
                RequestContext.getCurrentInstance().update("akordeon:formZob1:wiad1");
            }
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Platnosci nie zachowane - PodatekView", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            RequestContext.getCurrentInstance().update("akordeon:formZob1:wiad1");
        }
    }
    

    private Double odsetki(Date dataod, Date datadotmp, String podstawa) {
        Boolean wymuszonykoniec = false;
        Date dataDozwiersza = new Date();
        BigDecimal kwota = new BigDecimal(BigInteger.ZERO);
        BigDecimal odsetki = new BigDecimal(BigInteger.ZERO);
        Date datado = new Date();
        String stawka;
        while (dataod.getTime() < datadotmp.getTime()) {
            Odsetki odsetkiwiersz = zwrocokres(dataod);
            dataDozwiersza = odsetkiwiersz.getDatadoD();
            if (dataDozwiersza == null) {
                dataDozwiersza = datadotmp;
                wymuszonykoniec = true;
            }
            //obliczanie nie konczy sie na jednymwierszu wiec trzeba przestawic daty od i do
            if (dataDozwiersza.getTime() < datadotmp.getTime()) {
                datado = dataDozwiersza;
            } else {
                datado = datadotmp;
            }
            odsetki = new BigDecimal(odsetkiwiersz.getStopaodsetek().replace(",", "."));
            //te 20 to zaokraglenie
            odsetki = odsetki.divide(new BigDecimal("36500"), 20, RoundingMode.HALF_EVEN);
            long x = datado.getTime();
            long y = dataod.getTime();
            Long wynik = Math.abs(x - y);
            if (datado.equals(datadotmp)) {
                wynik = (wynik / (1000 * 60 * 60 * 24)) + 1;
            } else {
                wynik = (wynik / (1000 * 60 * 60 * 24)) + 2;
            }
            kwota = kwota.add(odsetki.multiply(new BigDecimal(podstawa)).multiply(new BigDecimal(wynik.toString())).setScale(2, RoundingMode.HALF_EVEN));
            if (wymuszonykoniec == false) {
                dataod = new Date(dataDozwiersza.getTime() + (1000 * 60 * 60 * 24));
            } else {
                dataod = datadotmp;
            }
        }
        kwota = kwota.setScale(0, RoundingMode.HALF_EVEN);
        return kwota.doubleValue();
    }

    private Odsetki zwrocokres(Date dataod) {
        List<Odsetki> lista = new ArrayList<>();
        lista.addAll(odsetkiDAO.findAll());
        Iterator it;
        it = lista.iterator();
        while (it.hasNext()) {
            Odsetki tmp = (Odsetki) it.next();
            try {
                if ((dataod.getTime() >= tmp.getDataodD().getTime()) && (dataod.getTime() <= tmp.getDatadoD().getTime())) {
                    return tmp;
                }
            } catch (Exception e) { E.e(e); 
                break;
            }
        }
        return lista.get(lista.size() - 1);
    }

    private long roznicaDni(Date date_od, Date date_do) {
        long x = date_do.getTime();
        long y = date_od.getTime();
        long wynik = Math.abs(x - y);
        wynik = wynik / (1000 * 60 * 60 * 24);
        return wynik;
    }
    
     private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpisDAO.edit(wpistmp);
    }
     private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.naniesDaneDoWpis();
    }
    
     public void aktualizujTablice() throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }

    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Podatnik getBiezacyPodanik() {
        return biezacyPodanik;
    }

    public void setBiezacyPodanik(Podatnik biezacyPodanik) {
        this.biezacyPodanik = biezacyPodanik;
    }

    public ZobowiazanieDAO getZv() {
        return zv;
    }

    public void setZv(ZobowiazanieDAO zv) {
        this.zv = zv;
    }

    public Platnosci getSelectedZob() {
        return selectedZob;
    }

    public void setSelectedZob(Platnosci selectedZob) {
        this.selectedZob = selectedZob;
    }

    

    public PlatnosciDAO getPlatnosciDAO() {
        return platnosciDAO;
    }

    public void setPlatnosciDAO(PlatnosciDAO platnosciDAO) {
        this.platnosciDAO = platnosciDAO;
    }

    public boolean isEdytujplatnosc() {
        return edytujplatnosc;
    }

    public void setEdytujplatnosc(boolean edytujplatnosc) {
        this.edytujplatnosc = edytujplatnosc;
    }

    public OdsetkiDAO getOdsetkiDAO() {
        return odsetkiDAO;
    }

    public void setOdsetkiDAO(OdsetkiDAO odsetkiDAO) {
        this.odsetkiDAO = odsetkiDAO;
    }

    public PitDAO getPitDAO() {
        return pitDAO;
    }

    public void setPitDAO(PitDAO pitDAO) {
        this.pitDAO = pitDAO;
    }

}
