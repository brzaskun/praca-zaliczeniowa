/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.PitDAO;
import dao.PodatnikDAO;
import dao.RodzajedokDAO;
import dao.WpisDAO;
import dao.ZUSDAO;
import daoFK.KontoDAOfk;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.Straty1;
import embeddable.Udzialy;
import entity.Pitpoz;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PodatnikView implements Serializable {
    private String nazwaWybranegoPodatnika;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private Podatnik selected;
    @Inject
    private Podatnik selectedStrata;
    @Inject
    private Podatnik selectedDod;
    @Inject
    private Rodzajedok selectedDokKsi;
    @Inject
    private Rodzajedok wybranyRodzajDokumentu;
    @Inject KontoDAOfk kontoDAOfk;
    private List<Rodzajedok> rodzajeDokumentowLista;
    @ManagedProperty(value = "#{rodzajedokView}")
    private RodzajedokView rodzajedokView;
    private List<String> pojList;
    private PanelGrid grid;
    private String[] listka;
    private List<String> listkakopia;
    private List<String> miesiacepoweryfikacji;

    @Inject
    private Zusstawki zusstawki;
    @Inject
    private Zusstawki zusstawkiWybierz;
    @Inject
    private Parametr parametr;
    @Inject
    private Parametr ostatniparametr;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private ZUSDAO zusDAO;
    @Inject
    private WpisView wpisView;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private Udzialy udzialy;
    //straty z lat ubieglych
    private List<Straty1> stratyzlatub;
    private String stratarok;
    private String stratakwota;
    private String strata50;
    private String stratawykorzystano;
    private String stratazostalo;
    @Inject
    private PitDAO pitDAO;
    private String biezacadata;
    private List<Konto> listaKontKasaBank;
    private List<Konto> listaKontRozrachunkowych;
    private List<Konto> listaKontVAT;
    private List<Konto> listakontoRZiS;
    

    public PodatnikView() {
        miesiacepoweryfikacji = new ArrayList<>();
        listka = new String[3];
        listka[0] = "zero";
        listka[1] = "jeden";
        listka[2] = "dwa";
        rodzajeDokumentowLista = new ArrayList<>();
        listaKontRozrachunkowych = new ArrayList<>();
        listaKontVAT = new ArrayList<>();
        listakontoRZiS  = new ArrayList<>();
        listaKontKasaBank  = new ArrayList<>();
        
    }

   
    @PostConstruct
    public void init() {
        nazwaWybranegoPodatnika = wpisView.getPodatnikWpisu();
        try {
            selected = wpisView.getPodatnikObiekt();
            pobierzogolneDokKsi();
            zweryfikujBazeBiezacegoPodatnika();
            uzupelnijListyKont();
            selectedStrata = podatnikDAO.find(wpisView.getPodatnikWpisu());
        } catch (Exception e) { E.e(e); 
        }
        rodzajeDokumentowLista = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
        biezacadata = String.valueOf(new DateTime().getYear());
    }

    public void dodaj() {
        if (selectedDod.getPesel() == null) {
            selectedDod.setPesel("00000000000");
        }
        try {
            sformatuj(selectedDod);
            podatnikDAO.dodaj(selectedDod);
            selectedDod = null;
            Msg.msg("i", "Dodano nowego podatnika-firmę: " + selectedDod.getNazwapelna());
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Niedodano nowego podatnika-firmę: " + selectedDod.getNazwapelna());
        }
    }

    public void dodajfk() {
        if (selectedDod.getPesel() == null) {
            selectedDod.setPesel("00000000000");
        }
        try {
            selectedDod.setFirmafk(true);
            sformatuj(selectedDod);
            podatnikDAO.dodaj(selectedDod);
            Msg.msg("i", "Dodano nowego podatnika-firmę FK: " + selectedDod.getNazwapelna());
            selectedDod = null;
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Niedodano nowego podatnika-firmę FK: " + selectedDod.getNazwapelna());
        }
        RequestContext.getCurrentInstance().reset("formwprowadzaniefirmy:panelwpisywanianowejfirmy");
        RequestContext.getCurrentInstance().update("formwprowadzaniefirmy:panelwpisywanianowejfirmy");
    }

    public void edytuj() {
        try {
            sformatuj(selected);
            podatnikDAO.edit(selected);
            Msg.msg("i", "Edytowano dane podatnika-klienta " + selected.getNazwapelna(), "akordeon:form:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd - dane niezedytowane", "akordeon:form:msg");
        }
    }
    
    
    public void edytujfk() {
        try {
            sformatuj(selected);
            selected.setFirmafk(true);
            podatnikDAO.edit(selected);
            Msg.msg("i", "Edytowano dane podatnika-klienta " + selected.getNazwapelna(), "akordeon:form:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd - dane niezedytowane", "akordeon:form:msg");
        }
    }
   
    public void zmianaWysylkaZus(ValueChangeEvent el) {
        try {
            selected.setWysylkazusmail((Boolean) el.getNewValue());
            podatnikDAO.edit(selected);
            Msg.msg("i", "Zmieniono", "akordeon:form:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd - dane niezmienione", "akordeon:form:msg");
        }
    }

    public void sformatuj(Podatnik s) throws Exception {
        try {
            String formatka = null;
            s.setNazwapelna(s.getNazwapelna().toUpperCase());
            s.setWojewodztwo(s.getWojewodztwo().substring(0, 1).toUpperCase() + s.getWojewodztwo().substring(1).toLowerCase());
            s.setGmina(s.getGmina().substring(0, 1).toUpperCase() + s.getGmina().substring(1).toLowerCase());
            if (!s.getUlica().isEmpty()) {
                s.setUlica(s.getUlica().substring(0, 1).toUpperCase() + s.getUlica().substring(1));
            }
            s.setPowiat(s.getPowiat().substring(0, 1).toUpperCase() + s.getPowiat().substring(1).toLowerCase());
            s.setMiejscowosc(s.getMiejscowosc().substring(0, 1).toUpperCase() + s.getMiejscowosc().substring(1).toLowerCase());
            s.setPoczta(s.getPoczta().substring(0, 1).toUpperCase() + s.getPoczta().substring(1).toLowerCase());
            s.setImie(s.getImie().substring(0, 1).toUpperCase() + s.getImie().substring(1).toLowerCase());
            s.setNazwisko(s.getNazwisko().substring(0, 1).toUpperCase() + s.getNazwisko().substring(1).toLowerCase());
        } catch (Exception r) {
            Msg.msg("e", "Wystąpił błąd podczas formatowania wprowadzonych danych");
            throw new Exception();
        }

    }
//archeo??
//    public void dodajrzadwzor(ActionEvent e) {
//        UIComponent wywolaneprzez = getGrid();
//
//        //wywolaneprzez.setRendered(false);
//        System.out.println("Form: "
//                + wywolaneprzez.getNamingContainer().getClientId());
//        System.out.println("Rodzic: "
//                + (wywolaneprzez = wywolaneprzez.getParent()));
//        System.out.println("Klientid: " + wywolaneprzez.getClientId());
//        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
//        UIComponent output = new HtmlOutputText();
//        UIComponent nowyinput = new HtmlInputText();
//        UIComponent nowyinput1 = new HtmlInputText();
//        HtmlCommandButton button = new HtmlCommandButton();
//
//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//        ExpressionFactory ef = ExpressionFactory.newInstance();
//        int rozmiar = 0;
//        for (int i = 0; i < listka.length; i++) {
//            if (listka[i] != null) {
//                rozmiar++;
//            }
//        }
//        int rozmiarS = rozmiar + 1;
//        final String bindingO = "parametr w okresie";
//        final String binding = "#{podatnikView.listka[" + rozmiar + "]}";
//        final String bindingS = "#{podatnikView.listka[" + rozmiarS + "]}";
//        ValueExpression veO = ef.createValueExpression(elContext, bindingO, String.class);
//        ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
//        ValueExpression ve1 = ef.createValueExpression(elContext, bindingS, String.class);
//        button.setValue("dodaj");
//        FacesContext context = FacesContext.getCurrentInstance();
//        MethodExpression actionListener = context.getApplication().getExpressionFactory()
//                .createMethodExpression(context.getELContext(), "#{podatnikView.dodajrzad}", null, new Class[]{ActionEvent.class});
//        button.addActionListener(new MethodExpressionActionListener(actionListener));
//
//        final String bindingB3 = "@form";
//        button.getAttributes().put("update", bindingB3);
//        output.setValueExpression("value", veO);
//        nowyinput.setValueExpression("value", ve);
//        nowyinput1.setValueExpression("value", ve1);
//        grid = getGrid();
//        grid.getChildren().add(output);
//        grid.getChildren().add(nowyinput);
//        grid.getChildren().add(nowyinput1);
//        grid.getChildren().add(button);
//
//        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
//        listkakopia = Arrays.asList(listka);
//        List<String> nowalista = new ArrayList();
//        for (String c : listkakopia) {
//            if (c != null) {
//                nowalista.add(c);
//            }
//        }
//        System.out.println("To jest listka: " + listkakopia.toString());
//    }
//
//    public void dodajrzad(ActionEvent e) {
//        UIComponent wywolaneprzez = getGrid();
//
//        //wywolaneprzez.setRendered(false);
//        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
//        HtmlOutputText output = new HtmlOutputText();
//        UIComponent nowyinput = new HtmlInputText();
//        UIComponent nowyinput1 = new HtmlInputText();
//        HtmlCommandButton button = new HtmlCommandButton();
//        ELContext elContext = FacesContext.getCurrentInstance().getELContext();
//        ExpressionFactory ef = ExpressionFactory.newInstance();
//        int rozmiar = 0;
//        for (int i = 0; i < listka.length; i++) {
//            if (listka[i] != null) {
//                rozmiar++;
//            }
//        }
//        int rozmiarS = rozmiar + 1;
//        final String binding = "#{podatnikView.listka[" + rozmiar + "]}";
//        final String bindingS = "#{podatnikView.listka[" + rozmiarS + "]}";
//        ValueExpression ve = ef.createValueExpression(elContext, binding, String.class);
//        ValueExpression ve1 = ef.createValueExpression(elContext, bindingS, String.class);
//        button.setValue("dodaj");
//        FacesContext context = FacesContext.getCurrentInstance();
//        MethodExpression actionListener = context.getApplication().getExpressionFactory()
//                .createMethodExpression(context.getELContext(), "#{podatnikView.dodajrzad}", null, new Class[]{ActionEvent.class});
//        button.addActionListener(new MethodExpressionActionListener(actionListener));
//
//        final String bindingB3 = "@form";
//        button.getAttributes().put("update", bindingB3);
//        output.setValue("parametr w okresie");
//        nowyinput.setValueExpression("value", ve);
//        nowyinput1.setValueExpression("value", ve1);
//        grid = getGrid();
//        grid.getChildren().add(output);
//        grid.getChildren().add(nowyinput);
//        grid.getChildren().add(nowyinput1);
//        grid.getChildren().add(button);
//
//        RequestContext.getCurrentInstance().update(wywolaneprzez.getClientId());
//        listkakopia = Arrays.asList(listka);
//        List<String> nowalista = new ArrayList();
//        for (String c : listkakopia) {
//            if (c != null) {
//                nowalista.add(c);
//            }
//        }
//
//    }
//
    public void dodajdoch() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = new ArrayList<>();
        try {
            lista.addAll(selected.getPodatekdochodowy());
        } catch (Exception e) { E.e(e); 
        }
        if (sprawdzrok(parametr, lista) == 0) {
            lista.add(parametr);
            selected.setPodatekdochodowy(lista);
            podatnikDAO.edit(selected);
            parametr = new Parametr();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr pod.dochodowy do podatnika.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru pod.doch. Niedopasowane okresy.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void dodajzawieszenie() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = new ArrayList<>();
        try {
            lista.addAll(selected.getZawieszeniedzialalnosci());
        } catch (Exception e) { E.e(e); 
        }
        parametr.setRokDo(parametr.getRokOd());
        lista.add(parametr);
        selected.setZawieszeniedzialalnosci(lista);
        podatnikDAO.edit(selected);
        parametr = new Parametr();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr zawieszenie działalności do podatnika.", selected.getNazwapelna());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void usunzawieszenie() {
        List<Parametr> tmp = new ArrayList<>();
        tmp.addAll(selected.getZawieszeniedzialalnosci());
        tmp.remove(tmp.size() - 1);
        selected.setZawieszeniedzialalnosci(tmp);
        podatnikDAO.edit(selected);
    }

    private int sprawdzrok(Parametr nowe, List<Parametr> stare) {
        if (stare.isEmpty()) {
            Integer new_rokOd = Integer.parseInt(nowe.getRokOd());
            parametr.setMcOd("01");
            parametr.setMcDo("12");
            parametr.setRokDo(new_rokOd.toString());
            return 0;
        } else {
            Parametr ostatniparametr = stare.get(stare.size() - 1);
            Integer old_rokDo = Integer.parseInt(ostatniparametr.getRokDo());
            Integer new_rokOd = Integer.parseInt(nowe.getRokOd());
            if (old_rokDo == new_rokOd - 1) {
                parametr.setMcOd("01");
                parametr.setMcDo("12");
                parametr.setRokDo(new_rokOd.toString());
                return 0;
            } else {
                return 1;
            }
        }
    }

    public void usundoch() {
        List<Parametr> tmp = new ArrayList<>();
        tmp.addAll(selected.getPodatekdochodowy());
        tmp.remove(tmp.size() - 1);
        selected.setPodatekdochodowy(tmp);
        podatnikDAO.edit(selected);
    }

    public void dodajvat() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = new ArrayList<>();
        try {
            lista.addAll(selected.getVatokres());
        } catch (Exception e) { E.e(e); 
        }
        if (sprawdzvat(parametr, lista) == 0) {
            lista.add(parametr);
            selected.setVatokres(lista);
            podatnikDAO.edit(selected);
            parametr = new Parametr();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr VAT metoda do podatnika.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru VAT metoda. Niedopasowane okresy.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    private int sprawdzvat(Parametr nowe, List<Parametr> stare) {
        if (stare.isEmpty()) {
            parametr.setMcDo("");
            parametr.setRokDo("");
            return 0;
        } else {
            ostatniparametr = stare.get(stare.size() - 1);
            Integer old_rokDo = Integer.parseInt(nowe.getRokOd());
            Integer old_mcOd = Integer.parseInt(nowe.getMcOd());
            Integer sumOld = Integer.parseInt(ostatniparametr.getMcOd()) + Integer.parseInt(ostatniparametr.getRokOd());
            Integer sumNew = Integer.parseInt(nowe.getMcOd()) + Integer.parseInt(nowe.getRokOd());
            if (sumOld >= sumNew) {
                return 1;
            }
            if (old_mcOd == 1) {
                old_mcOd = 12;
                old_rokDo--;
            } else {
                old_mcOd--;
            }
            ostatniparametr.setRokDo(old_rokDo.toString());
            ostatniparametr.setMcDo(Mce.getNumberToMiesiac().get(old_mcOd));
            return 0;
        }
    }

    public void usunvat() {
        List<Parametr> tmp = new ArrayList<>();
        tmp.addAll(selected.getVatokres());
        tmp.remove(tmp.size() - 1);
        selected.setVatokres(tmp);
        podatnikDAO.edit(selected);
    }

    public void dodajzus() {
        try {
            selected = wpisView.getPodatnikObiekt();
            List<Zusstawki> tmp = new ArrayList<>();
            try {
                tmp.addAll(selected.getZusparametr());
            } catch (Exception e) { E.e(e); 
            }
            sprawdzzus(tmp);
            tmp.add(zusstawki);
            selected.setZusparametr(tmp);
            podatnikDAO.edit(selected);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr ZUS do podatnika.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru ZUS. Niedopasowane okresy.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

  
    private void sprawdzzus(List tmp) throws Exception {
        Iterator it;
        it = tmp.iterator();
        while (it.hasNext()) {
            Zusstawki tmpx = (Zusstawki) it.next();
            if (tmpx.getZusstawkiPK().equals(zusstawki.getZusstawkiPK())) {
                throw new Exception("Blad");
            }
        }
    }
    
     public void edytujzus() {
        try {
            selected = wpisView.getPodatnikObiekt();
            List<Zusstawki> tmp = new ArrayList<>();
            try {
                tmp.addAll(selected.getZusparametr());
            } catch (Exception e) { E.e(e); 
            }
            if (tmp.contains(zusstawki)) {
                // to niby gupawe ale jest madre bo on rozpoznaje zus stawki po roku i miesiacu tylko
                tmp.remove(zusstawki);
                tmp.add(serialclone.SerialClone.clone(zusstawki));
                selected.setZusparametr(tmp);
                podatnikDAO.edit(selected);
                zusstawki =  new Zusstawki();
                Msg.msg("Udana edycja stawek ZUS");
            } else {
                Msg.msg("w", "Nie ma czego edytowac. Cos dziwnego sie stalo.Wolaj szefa (PodatnikView - edytujzus");
            }
        } catch (Exception e) { E.e(e); 
        }
    }

    public void usunzus(Zusstawki loop) {
        selected = wpisView.getPodatnikObiekt();
        List<Zusstawki> tmp = new ArrayList<>();
        tmp.addAll(selected.getZusparametr());
        tmp.remove(loop);
        selected.setZusparametr(tmp);
        podatnikDAO.edit(selected);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usunięto parametr ZUS do podatnika.", selected.getNazwapelna());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void wybranowiadomosc() {
        zusstawki = serialclone.SerialClone.clone(zusstawkiWybierz);
        Msg.msg("Wybrano stawki ZUS.");
    }
    

    public void pobierzzus() {
        String rokzus = (String) params.Params.paramsContains("rokzus");
        String mczus = (String) params.Params.paramsContains("miesiaczus");
        if (rokzus == null || mczus == null) {
            Msg.msg("e", "Problem z pobieraniem okresu rozliczeniowego.");
        }
        List<Zusstawki> tmp = new ArrayList<>();
        tmp.addAll(zusDAO.findAll());
        ZusstawkiPK key = new ZusstawkiPK();
        key.setRok(rokzus);
        key.setMiesiac(mczus);
        Iterator it;
        it = tmp.iterator();
        while (it.hasNext()) {
            Zusstawki tmpX = (Zusstawki) it.next();
            if (tmpX.getZusstawkiPK().equals(key)) {
                zusstawki = tmpX;
                break;
            }
        }
    }

   

    public String przejdzdoStrony() {
        selected = wpisView.getPodatnikObiekt();
        //sprawdazic
        RequestContext.getCurrentInstance().execute("openwindow()");
        return "/manager/managerPodUstaw.xhtml?faces-redirect=true";
    }

    public void dodajremanent() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = new ArrayList<>();
        try {
            lista.addAll(selected.getRemanent());
        } catch (Exception e) { E.e(e); 
        }
        if (sprawdzrok(parametr, lista) == 0) {
            lista.add(parametr);
            selected.setRemanent(lista);
            podatnikDAO.edit(selected);
            parametr = new Parametr();
            Msg.msg("Dodatno parametr remanent do podatnika: "+selected.getNazwapelna());
        } else {
            Msg.msg("e","Niedodatno parametru remanent. Niedopasowane okresy: "+selected.getNazwapelna());
        }
    }

    public void usunremanent() {
        List<Parametr> tmp = new ArrayList<>();
        tmp.addAll(selected.getRemanent());
        tmp.remove(tmp.size() - 1);
        selected.setRemanent(tmp);
        podatnikDAO.edit(selected);
    }

    public void dodajkwoteautoryzujaca() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = new ArrayList<>();
        try {
            lista.addAll(selected.getKwotaautoryzujaca());
        } catch (Exception e) { E.e(e); 
        }
        if (sprawdzrok(parametr, lista) == 0) {
            String tmp = parametr.getParametr();
            tmp = tmp.replace(",", ".");
            parametr.setParametr(tmp);
            assert tmp.contains(",") : "Nie usuwa dobrze przecinka z kwota autoryzujaca!";
            lista.add(parametr);
            selected.setKwotaautoryzujaca(lista);
            podatnikDAO.edit(selected);
            parametr = new Parametr();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr kwota autoryzująca do podatnika.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru kwota autoryzujaca. Niedopasowane okresy.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usunkwoteautoryzujaca() {
        List<Parametr> tmp = new ArrayList<>();
        tmp.addAll(selected.getKwotaautoryzujaca());
        tmp.remove(tmp.size() - 1);
        selected.setKwotaautoryzujaca(tmp);
        podatnikDAO.edit(selected);
    }

    public void dodajnrpkpir() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = new ArrayList<>();
        try {
            lista.addAll(selected.getNumerpkpir());
        } catch (Exception e) { E.e(e); 
        }
        if (sprawdzrok(parametr, lista) == 0) {
            String tmp = parametr.getParametr();
            tmp = tmp.replace(",", ".");
            parametr.setParametr(tmp);
            assert tmp.contains(",") : "Nie usuwa dobrze przecinka z npkpir!";
            lista.add(parametr);
            selected.setNumerpkpir(lista);
            podatnikDAO.edit(selected);
            parametr = new Parametr();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno numer początkowy w pkpir dla podatnika.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno numeru początkowego w pkpir dla podatnika. Niedopasowane okresy.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usunnrpkpir() {
        List<Parametr> tmp = new ArrayList<>();
        tmp.addAll(selected.getNumerpkpir());
        tmp.remove(tmp.size() - 1);
        selected.setNumerpkpir(tmp);
        podatnikDAO.edit(selected);
    }
    
     public void dodajFKpiatki() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = new ArrayList<>();
        try {
            lista.addAll(selected.getFKpiatki());
        } catch (Exception e) { E.e(e); 
        }
        if (sprawdzrok(parametr, lista) == 0) {
            lista.add(parametr);
            selected.setFKpiatki(lista);
            podatnikDAO.edit(selected);
            parametr = new Parametr();
            Msg.msg("Dodano ustawienie piątek");
        } else {
            Msg.msg("e", "Nie udało się zmienić ustawienie piątek");
        }
    }

    public void usunFKpiatki() {
        List<Parametr> tmp = new ArrayList<>();
        tmp.addAll(selected.getFKpiatki());
        tmp.remove(tmp.size() - 1);
        selected.setFKpiatki(tmp);
        podatnikDAO.edit(selected);
    }

    public void dodajpole47() {
        try {
            podatnikDAO.edit(selected);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno udziały.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno udziału.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usunpole47() {
        selected.setPole47(null);
        podatnikDAO.edit(selected);
    }

    public void dodajUdzialy() {
        selected = wpisView.getPodatnikObiekt();
        List<Udzialy> lista = new ArrayList<>();
        try {
            lista.addAll(selected.getUdzialy());
        } catch (Exception e) { E.e(e); 
        }
        try {
            Integer sumaudzialow = 0;
            for (Udzialy p : lista) {
                try {
                    if (!p.getRokDo().isEmpty()) {
                    }
                } catch (Exception ef) {
                    sumaudzialow += Integer.parseInt(p.getUdzial());
                }
                if (udzialy.getNazwiskoimie().equals(p.getNazwiskoimie())) {
                    throw new Exception();
                }
                if (udzialy.getNip().equals(p.getNip())) {
                    throw new Exception();
                }
            }
            sumaudzialow += Integer.parseInt(udzialy.getUdzial());
            if (sumaudzialow > 100) {
                throw new Exception();
            }
            lista.add(udzialy);
            selected.setUdzialy(lista);
            podatnikDAO.edit(selected);
            Msg.msg("i", "Dodano udziały", "akordeon:form6:messages");
        } catch (Exception ex) {
            Msg.msg("e", "Niedodano udziału, wystąpił błąd. Sprawdz dane:nazwisko, procenty", "akordeon:form6:messages");
        }

    }

    public void editUdzialy(RowEditEvent ex) {
        try {
            Podatnik selected2 = podatnikDAO.find(nazwaWybranegoPodatnika);
            podatnikDAO.destroy(selected2);
            podatnikDAO.dodaj(selected);
            Msg.msg("i", "Wyedytowano udziały", "akordeon:form6:messages");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Nie zmieniono udziałów", "akordeon:form6:messages");
        }
    }

    public void usunUdzialy(Udzialy udzialy) {
        selected = wpisView.getPodatnikObiekt();
        List<Udzialy> tmp = new ArrayList<>();
        tmp.addAll(selected.getUdzialy());
        tmp.remove(udzialy);
        selected.setUdzialy(tmp);
        podatnikDAO.edit(selected);
        Msg.msg("i", "Usunięto wskazany udział: " + udzialy.getNazwiskoimie(), "akordeon:form6:messages");
    }

    public void dodajDokKsi() {
        if (selectedDokKsi.getNazwa() != null && selectedDokKsi.getRodzajedokPK().getSkrotNazwyDok() != null) {
            try {
                selectedDokKsi.setPodatnikObj(wpisView.getPodatnikObiekt());
                selectedDokKsi.setSkrot(selectedDokKsi.getRodzajedokPK().getSkrotNazwyDok().toUpperCase(new Locale("pl")));
                selectedDokKsi.getRodzajedokPK().setSkrotNazwyDok(selectedDokKsi.getSkrot().toUpperCase(new Locale("pl")));
                selectedDokKsi.setNazwa(selectedDokKsi.getNazwa().toLowerCase(new Locale("pl")));
                rodzajedokDAO.dodaj(selectedDokKsi);
                rodzajeDokumentowLista.add(selectedDokKsi);
                selectedDokKsi = new Rodzajedok();
                Msg.msg("i", "Dodano nowy wzór dokumentu", "akordeon:form6");
            } catch (Exception ex) {
                Msg.msg("e", "Niedodano nowego wzoru dokumentu, wystąpił błąd", "akordeon:form6");
            }
        } else {
             Msg.msg("e", "Brak nazwy i skrótu dokumentu", "akordeon:form6");
        }
    }

    public void przygotujdoedycjiDokKsi() {
        selectedDokKsi = wybranyRodzajDokumentu;
        Msg.msg("i", "Wybrano wzorzec do edycji", "akordeon:form7");
    }
    
    public void editdok() {
        try {
            rodzajedokDAO.edit(selectedDokKsi);
            selectedDokKsi = new Rodzajedok();
            Msg.msg("i", "Wyedytowano wzorce dokumentów");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Nie zmieniono dokumentów");
        }
    }

    public void usunDokKsi(Rodzajedok rodzajDokKsi) {
        rodzajedokDAO.destroy(rodzajDokKsi);
        rodzajeDokumentowLista.remove(rodzajDokKsi);
        Msg.msg("i", "Usunięto wzor dokumentu", "akordeon:form6");
    }

    public void pobierzogolneDokKsi() {
        selected = wpisView.getPodatnikObiekt();
        List<Rodzajedok> dokumentyBiezacegoPodatnika = rodzajedokDAO.findListaPodatnik(selected);
        List<Rodzajedok> ogolnaListaDokumentow = rodzajedokView.getListaWspolnych();
        try {
            for (Rodzajedok tmp : ogolnaListaDokumentow) {
                boolean odnaleziono = false;
                for (Rodzajedok r: dokumentyBiezacegoPodatnika) {
                    if (r.getSkrot().equals(tmp.getSkrot())) {
                        odnaleziono = true;
                    }
                }
                if (odnaleziono == false) {
                    Rodzajedok nowy  = serialclone.SerialClone.clone(tmp);
                    nowy.setPodatnikObj(selected);
                    nowy.getRodzajedokPK().setPodatnik(selected.getNip());
                    nowy.setKontoRZiS(null);
                    nowy.setKontorozrachunkowe(null);
                    nowy.setKontovat(null);
                    rodzajedokDAO.dodaj(nowy);
                    dokumentyBiezacegoPodatnika.add(nowy);
                }
            }
        } catch (Exception ex) {
        }
    }

    public void peseldataurodzenia() {
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String skrot = params.get("form:pesel");
        String tmp = "19" + skrot.substring(0, 2) + "-" + skrot.substring(2, 4) + "-" + skrot.substring(4, 6);
        selectedDod.setDataurodzenia(tmp);
    }

    public void wypelnijfax() {
        selected.setFax("000000000");
    }

    public void zmienzbiorowoZUSPIT() {
        try {
            List<Podatnik> lista = new ArrayList<>();
            lista.addAll(podatnikDAO.findAll());
        } catch (Exception e) { E.e(e); 
        }
    }

    public void updateDokKsi(ValueChangeListener ex) {
        RequestContext.getCurrentInstance().update("akordeon:form6:parametryDokKsi");
    }

    public void dodajstrate() {
        BigDecimal zostalo = new BigDecimal(stratakwota);
        zostalo = zostalo.subtract(new BigDecimal(stratawykorzystano)).setScale(2, RoundingMode.HALF_EVEN);
        stratazostalo = zostalo.toString();
        BigDecimal polowa = new BigDecimal(stratakwota);
        polowa = polowa.divide(new BigDecimal(2)).setScale(2, RoundingMode.HALF_EVEN);
        strata50 = polowa.toString();
        Straty1 tmps = new Straty1(stratarok, stratakwota, strata50, stratawykorzystano, stratazostalo);
        Straty1 ostatnirok = new Straty1();
        try {
            stratyzlatub = selected.getStratyzlatub1();
            ostatnirok = stratyzlatub.get(stratyzlatub.size() - 1);

        } catch (Exception e) { E.e(e); 
            stratyzlatub = new ArrayList<>();
            ostatnirok.setRok("1900");
        }
        try {
            if (zostalo.signum() == -1) {
                Msg.msg("e", "Kwota wykorzystana większa od straty", "akordeon:form2:messages");
                throw new Exception();
            }
            if (Integer.parseInt(ostatnirok.getRok()) > Integer.parseInt(stratarok)) {
                Msg.msg("e", "Wpisywany rok nie pasuje do listy", "akordeon:form2:messages");
                throw new Exception();
            }
            stratyzlatub.add(tmps);
            selectedStrata.setStratyzlatub1(stratyzlatub);
            podatnikDAO.edit(selectedStrata);
            stratarok = "";
            stratakwota = "";
            strata50 = "";
            stratawykorzystano = "";
            stratazostalo = "";
            Msg.msg("i", "Dodano stratę za rok " + stratarok, "akordeon:form2:messages");
            RequestContext.getCurrentInstance().update("akordeon:form1");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd poczdas dodawania straty " + stratarok, "akordeon:form2:messages");
        }
    }

    public void usunstrate(Straty1 loop) {
        try {
            stratyzlatub = selectedStrata.getStratyzlatub1();
            stratyzlatub.size();
            stratyzlatub.remove(loop);
            podatnikDAO.edit(selectedStrata);
            Msg.msg("i", "Usunieto stratę za rok " + loop.getRok(), "akordeon:form2:messages");
            RequestContext.getCurrentInstance().update("akordeon:form1");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Wołaj szefa " + loop, "akordeon:form2:messages");
        }
    }

    public void naniesRozliczenieStrat() {
        Msg.msg("i", "Rozpoczynam rozliczanie strat");
        List<Pitpoz> pitpoz = pitDAO.findList(wpisView.getRokUprzedniSt(), "13", wpisView.getPodatnikWpisu());
        if (pitpoz.isEmpty()) {
            Msg.msg("e", "Nie sporządzono pitu za 13-mc poprzedniego roku. Przerywam nanoszenie strat");
            return;
        }
        Double strataRozliczonaWDanymRoku = 0.0;
        Double wynikzarok = 0.0;
        for (Pitpoz p : pitpoz) {
            strataRozliczonaWDanymRoku = Math.round(p.getStrata().doubleValue() * 100.0) / 100.0;
            wynikzarok = Math.round(p.getWynik().doubleValue() * 100.0) / 100.0;
        }
        try {
            //zerowanie strat w przypadku codniecia sie niezbedne
            for (Straty1 r : selectedStrata.getStratyzlatub1()) {
                List<Straty1.Wykorzystanie> wykorzystanie = r.getWykorzystanieBiezace();
                Iterator it = wykorzystanie.iterator();
                while (it.hasNext()) {
                    Straty1.Wykorzystanie w = (Straty1.Wykorzystanie) it.next();
                    if (Integer.parseInt(w.getRokwykorzystania()) >= wpisView.getRokUprzedni()) {
                        it.remove();
                    }
                }
            }
        } catch (Exception e) { E.e(e); 
        }
        //dodawanie straty jak nie bylo zysku
        if (wynikzarok < 0) {
            Msg.msg("i", "W roku poprzednim była strata. Dopisuję stratę do listy");
            Iterator it = selectedStrata.getStratyzlatub1().iterator();
            while (it.hasNext()) {
                Straty1 y = (Straty1) it.next();
                if (y.getRok().equals(wpisView.getRokUprzedniSt())) {
                    it.remove();
                }
            }
            Straty1 nowastrata = new Straty1();
            nowastrata.setRok(wpisView.getRokUprzedniSt());
            nowastrata.setKwota(String.valueOf(wynikzarok));
            nowastrata.setPolowakwoty(String.valueOf(Math.round((wynikzarok / 2) * 100.0) / 100.0));
            nowastrata.setWykorzystano("0.0");
            nowastrata.setSumabiezace("0.0");
            nowastrata.setZostalo(String.valueOf(wynikzarok));
            selectedStrata.getStratyzlatub1().add(nowastrata);
            //wczesniej usunieto zapisy z roku ale nie zaktualizowano podsumowan
            for (Straty1 r : selectedStrata.getStratyzlatub1()) {
                double sumabiezace = 0.0;
                for (Straty1.Wykorzystanie s : r.getWykorzystanieBiezace()) {
                    sumabiezace += s.getKwotawykorzystania();
                    sumabiezace = Math.round(sumabiezace * 100.0) / 100.0;
                }
                r.setSumabiezace(String.valueOf(sumabiezace));
                double kwota = Double.parseDouble(r.getKwota());
                double uprzednio = Double.parseDouble(r.getWykorzystano());
                double biezace = Double.parseDouble(r.getSumabiezace());
                r.setZostalo(String.valueOf(Math.round((kwota - uprzednio - biezace) * 100.0) / 100.0));
            }
            podatnikDAO.edit(selectedStrata);
            return;
        } else {
            Msg.msg("i", "Poprzedni rok zakończył się zyskiem. Nie ma czego dopisać do listy.");
        }
        for (Straty1 r : selectedStrata.getStratyzlatub1()) {
            Double zostalo = wyliczStrataZostalo(r);
            List<Straty1.Wykorzystanie> wykorzystanie = r.getWykorzystanieBiezace();
            if (zostalo <= 0 || strataRozliczonaWDanymRoku <= 0) {
                break;
            }
            if (wykorzystanie == null) {
                wykorzystanie = new ArrayList<>();
                Straty1.Wykorzystanie w = new Straty1.Wykorzystanie();
                w.setRokwykorzystania(wpisView.getRokUprzedniSt());
                w.setKwotawykorzystania(zostalo >= strataRozliczonaWDanymRoku ? strataRozliczonaWDanymRoku : zostalo);
                wykorzystanie.add(w);
                strataRozliczonaWDanymRoku -= w.getKwotawykorzystania();
                r.setWykorzystanieBiezace(wykorzystanie);
            } else if (wykorzystanie.isEmpty()) {
                wykorzystanie = new ArrayList<>();
                Straty1.Wykorzystanie w = new Straty1.Wykorzystanie();
                w.setRokwykorzystania(wpisView.getRokUprzedniSt());
                w.setKwotawykorzystania(zostalo >= strataRozliczonaWDanymRoku ? strataRozliczonaWDanymRoku : zostalo);
                wykorzystanie.add(w);
                strataRozliczonaWDanymRoku -= w.getKwotawykorzystania();
                r.setWykorzystanieBiezace(wykorzystanie);
            } else {
                List<Straty1.Wykorzystanie> nowewykorzystania = new ArrayList<>();
                for (Iterator<Straty1.Wykorzystanie> it =  wykorzystanie.iterator(); it.hasNext(); ) {
                    Straty1.Wykorzystanie s = (Straty1.Wykorzystanie) it.next();
                    if (s.getRokwykorzystania().equals(wpisView.getRokUprzedniSt())) {
                        s.setKwotawykorzystania(zostalo >= strataRozliczonaWDanymRoku ? strataRozliczonaWDanymRoku : zostalo);
                        strataRozliczonaWDanymRoku -= s.getKwotawykorzystania();
                        strataRozliczonaWDanymRoku = Math.round(strataRozliczonaWDanymRoku * 100.0) / 100.0;
                        break;
                    } else {
                        Straty1.Wykorzystanie w = new Straty1.Wykorzystanie();
                        w.setRokwykorzystania(wpisView.getRokUprzedniSt());
                        w.setKwotawykorzystania(zostalo >= strataRozliczonaWDanymRoku ? strataRozliczonaWDanymRoku : zostalo);
                        strataRozliczonaWDanymRoku -= w.getKwotawykorzystania();
                        strataRozliczonaWDanymRoku = Z.z(strataRozliczonaWDanymRoku);
                        nowewykorzystania.add(w);
                    }
                }
                wykorzystanie.addAll(nowewykorzystania);
                r.setWykorzystanieBiezace(wykorzystanie);
            }
        }
        for (Straty1 r : selectedStrata.getStratyzlatub1()) {
            double sumabiezace = 0.0;
            try {
                for (Straty1.Wykorzystanie s : r.getWykorzystanieBiezace()) {
                    sumabiezace += s.getKwotawykorzystania();
                    sumabiezace = Math.round(sumabiezace * 100.0) / 100.0;
                }
            } catch (Exception e) { E.e(e); 
            }
            r.setSumabiezace(String.valueOf(sumabiezace));
            double kwota = Double.parseDouble(r.getKwota());
            double uprzednio = Double.parseDouble(r.getWykorzystano());
            double biezace = Double.parseDouble(r.getSumabiezace());
            r.setZostalo(String.valueOf(Math.round((kwota - uprzednio - biezace) * 100.0) / 100.0));
        }
        podatnikDAO.edit(selectedStrata);
        Msg.msg("i", "Ukończyłem rozliczanie strat");
    }

    //wyliczenie niezbedne przy wracaniu do historycznych pitow
    private double wyliczStrataZostalo(Straty1 tmp) {
        double zostalo = 0.0;
        double sumabiezace = 0.0;
        try {
            for (Straty1.Wykorzystanie s : tmp.getWykorzystanieBiezace()) {
                if (Integer.parseInt(s.getRokwykorzystania()) < wpisView.getRokUprzedni()) {
                    sumabiezace += s.getKwotawykorzystania();
                    sumabiezace = Math.round(sumabiezace * 100.0) / 100.0;
                }
            }
        } catch (Exception e) { E.e(e); 
        }
        double kwota = Double.parseDouble(tmp.getKwota());
        double uprzednio = Double.parseDouble(tmp.getWykorzystano());
        double biezace = sumabiezace;
        zostalo += Math.round((kwota - uprzednio - biezace) * 100.0) / 100.0;
        return Math.round(zostalo * 100.0) / 100.0;
    }
    
    private void zweryfikujBazeBiezacegoPodatnika() {
        // to bylo nam potrzebne do transformacji teraz jest juz zbedne bo klineci maja przeniesione dokumenty
//        List<Rodzajedok> listaRodzajeDokPodatnika = rodzajedokDAO.findListaPodatnik(selected);
//        if (listaRodzajeDokPodatnika == null || listaRodzajeDokPodatnika.size() == 0) {
//            for (Rodzajedok p : selected.getDokumentyksiegowe()) {
//                RodzajedokPK rodzajedokPK = new RodzajedokPK(p.getSkrot());
//                p.setRodzajedokPK(rodzajedokPK);
//                p.setPodatnikObj(selected);
//                rodzajedokDAO.dodaj(p);
//            }
//            rodzajeDokumentowLista.addAll(rodzajedokDAO.findListaPodatnik(selected));
//            RequestContext.getCurrentInstance().update("akordeon:form6:parametryDokKsi");
//        } else {
//            rodzajeDokumentowLista.addAll(listaRodzajeDokPodatnika);
//            RequestContext.getCurrentInstance().update("akordeon:form6:parametryDokKsi");
//        }
    }
    
    private void uzupelnijListyKont() {
        String p = wpisView.getPodatnikWpisu();
        listaKontRozrachunkowych = kontoDAOfk.findKontaRozrachunkowe(wpisView);
        listaKontVAT = kontoDAOfk.findKontaVAT(p, wpisView.getRokWpisu());
        listakontoRZiS = kontoDAOfk.findKontaRZiS(wpisView);
        listakontoRZiS.addAll(kontoDAOfk.findKontaGrupa3(wpisView));
        listaKontKasaBank = kontoDAOfk.findlistaKontKasaBank(wpisView);
    }
    
    public void naniesKontaNaDokumentRozrachunki(ValueChangeEvent e) {
        if (selectedDokKsi.getRodzajedokPK() != null) {
            Konto wybraneKonto = (Konto) e.getNewValue();
            selectedDokKsi.setKontorozrachunkowe(wybraneKonto);
            rodzajedokDAO.edit(selectedDokKsi);
        } else {
            Msg.msg("e", "Nie wybrano dokumentu");
        }
    }
    
    public void naniesKontaNaDokumentVat(ValueChangeEvent e) {
        try {
            if (selectedDokKsi.getRodzajedokPK() != null) {
                Konto wybraneKonto = (Konto) e.getNewValue();
                selectedDokKsi.setKontovat(wybraneKonto);
                rodzajedokDAO.edit(selectedDokKsi);
            } else {
                Msg.msg("e", "Nie wybrano dokumentu");
            }
        } catch (Exception e1) {
            Msg.msg("e", "Błąd przy przyporzadkowaniu konta do dokumentu");
        }
    }
    
    public void naniesKontaNaDokumentRZiS(ValueChangeEvent e) {
        if (selectedDokKsi.getRodzajedokPK() != null) {
            Konto wybraneKonto = (Konto) e.getNewValue();
            selectedDokKsi.setKontoRZiS(wybraneKonto);
            rodzajedokDAO.edit(selectedDokKsi);
        } else {
            Msg.msg("e", "Nie wybrano dokumentu");
        }

    }

    
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    
    //     public void skopiujstraty() {
//         List<Podatnik> podatnicy = podatnikDAO.findAll();
//         for (Podatnik p : podatnicy) {
//             if (p.getStratyzlatub1() != null) {
//                List<Straty> straty = new ArrayList<>();
//                for (Straty1 r : p.getStratyzlatub1()) {
//                    Straty s = new Straty();
//                    s.setRok(r.getRok());
//                    s.setKwota(r.getKwota());
//                    s.setPolowakwoty(r.getPolowakwoty());
//                    s.setWykorzystano(r.getWykorzystano());
//                    s.setZostalo(r.getZostalo());
//                    straty.add(s);
//                }
//                p.setStratyzlatub(straty);
//                podatnikDAO.edit(p);
//                Msg.msg("i", "Przeniesiono straty podatnika "+p.getNazwapelna());
//             }
//         }
//     }
    
    
    public List<Konto> getListaKontKasaBank() {
        return listaKontKasaBank;
    }

    public void setListaKontKasaBank(List<Konto> listaKontKasaBank) {
        this.listaKontKasaBank = listaKontKasaBank;
    }

    public List<Konto> getListaKontRozrachunkowych() {
        return listaKontRozrachunkowych;
    }

    public void setListaKontRozrachunkowych(List<Konto> listaKontRozrachunkowych) {
        this.listaKontRozrachunkowych = listaKontRozrachunkowych;
    }

    public List<Konto> getListaKontVAT() {
        return listaKontVAT;
    }

    public void setListaKontVAT(List<Konto> listaKontVAT) {
        this.listaKontVAT = listaKontVAT;
    }

    public List<Konto> getListakontoRZiS() {
        return listakontoRZiS;
    }

    public void setListakontoRZiS(List<Konto> listakontoRZiS) {
        this.listakontoRZiS = listakontoRZiS;
    }

    public String getBiezacadata() {
        return biezacadata;
    }
    
    public void setBiezacadata(String biezacadata) {
        this.biezacadata = biezacadata;
    }
    
  
    
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public ZUSDAO getZusDAO() {
        return zusDAO;
    }
    
    public void setZusDAO(ZUSDAO zusDAO) {
        this.zusDAO = zusDAO;
    }
    
    public Zusstawki getZusstawki() {
        return zusstawki;
    }
    
    public void setZusstawki(Zusstawki zusstawki) {
        this.zusstawki = zusstawki;
    }

   
    public Parametr getParametr() {
        return parametr;
    }
    
    public void setParametr(Parametr parametr) {
        this.parametr = parametr;
    }
    
    public List<String> getListkakopia() {
        return listkakopia;
    }
    
    public void setListkakopia(List<String> listkakopia) {
        this.listkakopia = listkakopia;
    }
    
    public String[] getListka() {
        return listka;
    }
    
    public void setListka(String[] listka) {
        this.listka = listka;
    }
    
    public List<String> getPojList() {
        return pojList;
    }
    
    public void setPojList(List<String> pojList) {
        this.pojList = pojList;
    }
    
    public PanelGrid getGrid() {
        return grid;
    }
    
    public void setGrid(PanelGrid grid) {
        this.grid = grid;
    }
    
    public String getNazwaWybranegoPodatnika() {
        return nazwaWybranegoPodatnika;
    }
    
    public void setNazwaWybranegoPodatnika(String nazwaWybranegoPodatnika) {
        nazwaWybranegoPodatnika = nazwaWybranegoPodatnika;
    }
    
    public Podatnik getSelected() {
        return selected;
    }
    
    public void setSelected(Podatnik selected) {
        this.selected = selected;
    }
    
    public Rodzajedok getSelectedDokKsi() {
        return selectedDokKsi;
    }
    
    public void setSelectedDokKsi(Rodzajedok selectedDokKsi) {
        this.selectedDokKsi = selectedDokKsi;
    }
    
    public RodzajedokView getRodzajedokView() {
        return rodzajedokView;
    }
    
    public void setRodzajedokView(RodzajedokView rodzajedokView) {
        this.rodzajedokView = rodzajedokView;
    }
    
    public Podatnik getSelectedDod() {
        return selectedDod;
    }
    
    public void setSelectedDod(Podatnik selectedDod) {
        this.selectedDod = selectedDod;
    }
    
    public Udzialy getUdzialy() {
        return udzialy;
    }
    
    public void setUdzialy(Udzialy udzialy) {
        this.udzialy = udzialy;
    }
    
    public List<Straty1> getStratyzlatub() {
        return stratyzlatub;
    }
    
    public void setStratyzlatub(List<Straty1> stratyzlatub) {
        this.stratyzlatub = stratyzlatub;
    }
    
    public String getStratarok() {
        return stratarok;
    }
    
    public void setStratarok(String stratarok) {
        this.stratarok = stratarok;
    }
    
    public String getStratakwota() {
        return stratakwota;
    }
    
    public void setStratakwota(String stratakwota) {
        this.stratakwota = stratakwota;
    }
    
    public String getStrata50() {
        return strata50;
    }
    
    public void setStrata50(String strata50) {
        this.strata50 = strata50;
    }
    
    public String getStratawykorzystano() {
        return stratawykorzystano;
    }
    
    public void setStratawykorzystano(String stratawykorzystano) {
        this.stratawykorzystano = stratawykorzystano;
    }
    
    public String getStratazostalo() {
        return stratazostalo;
    }
    
    public void setStratazostalo(String stratazostalo) {
        this.stratazostalo = stratazostalo;
    }
    
    public Podatnik getSelectedStrata() {
        return selectedStrata;
    }
    
    public void setSelectedStrata(Podatnik selectedStrata) {
        this.selectedStrata = selectedStrata;
    }
    
    public PitDAO getPitDAO() {
        return pitDAO;
    }
    
    public void setPitDAO(PitDAO pitDAO) {
        this.pitDAO = pitDAO;
    }
    
    public Zusstawki getZusstawkiWybierz() {
        return zusstawkiWybierz;
    }
    
    public void setZusstawkiWybierz(Zusstawki zusstawkiWybierz) {
        this.zusstawkiWybierz = zusstawkiWybierz;
    }
    
    public Rodzajedok getWybranyRodzajDokumentu() {
        return wybranyRodzajDokumentu;
    }
    
    public void setWybranyRodzajDokumentu(Rodzajedok wybranyRodzajDokumentu) {
        this.wybranyRodzajDokumentu = wybranyRodzajDokumentu;
    }
    
    public List<Rodzajedok> getRodzajeDokumentowLista() {
        return rodzajeDokumentowLista;
    }
    
    public void setRodzajeDokumentowLista(List<Rodzajedok> rodzajeDokumentowLista) {
        this.rodzajeDokumentowLista = rodzajeDokumentowLista;
    }
//</editor-fold>


   

    
 

}
