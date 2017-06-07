/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanParametr.BeanParamSuper;
import beansRegon.SzukajDaneBean;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDDAO;
import dao.PodatnikUdzialyDAO;
import dao.RodzajedokDAO;
import dao.ZUSDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import data.Data;
import embeddable.Parametr;
import embeddable.Udzialy;
import entity.ParamCzworkiPiatki;
import entity.ParamSuper;
import entity.ParamVatUE;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
import entity.Rodzajedok;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import entityfk.Dokfk;
import entityfk.Konto;
import enumy.FormaPrawna;
import error.E;
import gus.GUSView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PodatnikView implements Serializable {
    private static final long serialVersionUID = 1L;
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
    @ManagedProperty(value = "#{gUSView}")
    private GUSView gUSView;
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
    private ParamCzworkiPiatki paramCzworkiPiatki;
    @Inject
    private Parametr ostatniparametr;
    @Inject
    private ZUSDAO zusDAO;
    @Inject
    private WpisView wpisView;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private PodatnikUdzialy udzialy;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    @Inject
    private PodatnikOpodatkowanieDDAO podatnikOpodatkowanieDDAO;
    private String biezacadata;
    private List<Konto> listaKontKasaBank;
    private List<Konto> listaKontRozrachunkowych;
    private List<Konto> listaKontVAT;
    private List<Konto> listakontoRZiS;
    private List<PodatnikUdzialy> podatnikUdzialy;
    private PodatnikUdzialy wybranyPodatnikUdzialy;
    private List<PodatnikOpodatkowanieD> podatnikOpodatkowanie;
    @Inject
    private PodatnikOpodatkowanieD wybranyPodatnikOpodatkowanie;
    private List<FormaPrawna> formyprawne;
    private boolean wszystkiekonta;
    @Inject
    private ParamVatUE paramVatUE;
    @Inject
    private DokDAOfk dokDAOfk;
    

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
        podatnikUdzialy = new ArrayList<>();
        podatnikOpodatkowanie = new ArrayList<>();
        formyprawne = new ArrayList<>();
        
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
        podatnikUdzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
        podatnikOpodatkowanie = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnik(wpisView);
        biezacadata = String.valueOf(new DateTime().getYear());
        formyprawne.add(FormaPrawna.SPOLKA_Z_O_O);
        formyprawne.add(FormaPrawna.SPOLKA_KOMANDYTOWA);
        formyprawne.add(FormaPrawna.STOWARZYSZENIE);
        formyprawne.add(FormaPrawna.FEDERACJA);
        formyprawne.add(FormaPrawna.FUNDACJA);
    }

    public void dodaj() {
        if (selectedDod.getPesel() == null) {
            selectedDod.setPesel("00000000000");
        }
        try {
            sformatuj(selectedDod);
            podatnikDAO.dodaj(selectedDod);
            Msg.msg("i", "Dodano nowego podatnika-firmę: " + selectedDod.getNazwapelna());
            selectedDod = new Podatnik();
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Niedodano nowego podatnika-firmę: " + selectedDod.getNazwapelna());
        }
    }

    public void dodajfk() {
        if (selectedDod.getPesel() == null) {
            selectedDod.setPesel("00000000000");
        }
        try {
            selectedDod.setFirmafk(1);
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
            zachowajZmiany(selected);
            Msg.msg("i", "Edytowano dane podatnika-klienta " + selected.getNazwapelna(), "akordeon:form:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd - dane niezedytowane", "akordeon:form:msg");
        }
    }
    
    public void kopiujnazwe() {
        if (selectedDod.getNazwapelna() != null) {
            selectedDod.setNazwisko(selectedDod.getNazwapelna());
        }
    }
    
    public void kopiujmiasto() {
        if (selectedDod.getPowiat() != null) {
            selectedDod.setGmina(selectedDod.getPowiat());
            selectedDod.setPoczta(selectedDod.getPowiat());
            selectedDod.setMiejscowosc(selectedDod.getPowiat());
        }
    }
    
    public void skopiujudzialy() {
        udzialy = wybranyPodatnikUdzialy;
    }
    public void edytujfk() {
        try {
            sformatuj(selected);
            zachowajZmiany(selected);
            Msg.msg("i", "Edytowano dane podatnika-klienta " + selected.getNazwapelna(), "akordeon:form:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd - dane niezedytowane", "akordeon:form:msg");
        }
    }
   
    public void zmianaWysylkaZus(ValueChangeEvent el) {
        try {
            selected.setWysylkazusmail((Boolean) el.getNewValue());
            zachowajZmiany(selected);
            Msg.msg("i", "Zmieniono parametry rozliczania ZUS", "akordeon:form:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd nie zmieniono parametrów rozliczania ZUS", "akordeon:form:msg");
        }
    }
    
     public void zmianaZus51(ValueChangeEvent el) {
        try {
            selected.setOdliczeniezus51((Boolean) el.getNewValue());
            zachowajZmiany(selected);
            Msg.msg("i", "Zmieniono parametry rozliczania ZUS", "akordeon:form:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd nie zmieniono parametrów rozliczania ZUS", "akordeon:form:msg");
        }
    }
     
     public void zmianaZus52(ValueChangeEvent el) {
        try {
            selected.setOdliczeniezus52((Boolean) el.getNewValue());
            zachowajZmiany(selected);
            Msg.msg("i", "Zmieniono parametry rozliczania ZUS", "akordeon:form:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd nie zmieniono parametrów rozliczania ZUS", "akordeon:form:msg");
        }
    }
     
    public void zmianaDatamalyzus(ValueChangeEvent el) {
        try {
            String regex = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher((String) el.getNewValue());
            if (m.matches()) {
                selected.setDatamalyzus((String) el.getNewValue());
                zachowajZmiany(selected);
                Msg.msg("i", "Zmieniono parametry rozliczania ZUS", "akordeon:form:msg");
            } else {
                Msg.msg("e", "Niprawidłowa data!", "akordeon:form:msg");
            }
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd nie zmieniono parametrów rozliczania ZUS", "akordeon:form:msg");
        }
    }
    
    public static void main(String[] args) {
        String pricesString = "2015-04-30";
        String regex = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(pricesString);
        while (m.find()) {
            System.out.println("lolo");
        }
    }

    public void sformatuj(Podatnik s) throws Exception {
        try {
            String formatka = null;
            s.setNazwapelna(s.getNazwapelna().toUpperCase());
            s.setWojewodztwo(s.getWojewodztwo().substring(0, 1).toUpperCase() + s.getWojewodztwo().substring(1).toLowerCase());
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
        if (sprawdzrok(wybranyPodatnikOpodatkowanie, podatnikOpodatkowanie) == 0) {
            wybranyPodatnikOpodatkowanie.setMcOd("01");
            wybranyPodatnikOpodatkowanie.setMcDo("12");
            wybranyPodatnikOpodatkowanie.setPodatnikObj(wpisView.getPodatnikObiekt());
            wybranyPodatnikOpodatkowanie.setRokDo(wybranyPodatnikOpodatkowanie.getRokOd());
            wybranyPodatnikOpodatkowanie.setDatawprowadzenia(new Date());
            wybranyPodatnikOpodatkowanie.setKsiegowa(wpisView.getWprowadzil());
            podatnikOpodatkowanie.add(wybranyPodatnikOpodatkowanie);
            podatnikOpodatkowanieDDAO.dodaj(wybranyPodatnikOpodatkowanie);
            wybranyPodatnikOpodatkowanie = new PodatnikOpodatkowanieD();
            Msg.msg("Dodatno parametr pod.dochodowy do podatnika "+selected.getNazwapelna());
        } else {
            Msg.msg("e", "Niedodatno parametru pod.doch. Niedopasowane okresy. Podatnik "+selected.getNazwapelna());
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
        zachowajZmiany(selected);
        parametr = new Parametr();
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr zawieszenie działalności do podatnika.", selected.getNazwapelna());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }
    
    public void usunzawieszenie() {
        List<Parametr> tmp = new ArrayList<>();
        tmp.addAll(selected.getZawieszeniedzialalnosci());
        tmp.remove(tmp.size() - 1);
        selected.setZawieszeniedzialalnosci(tmp);
        zachowajZmiany(selected);
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
    
    private int sprawdzrok(PodatnikOpodatkowanieD nowe, List<PodatnikOpodatkowanieD> stare) {
        if (stare.isEmpty()) {
            return 0;
        } else {
            PodatnikOpodatkowanieD ostatniparametr = stare.get(stare.size() - 1);
            Integer old_rokDo = Integer.parseInt(ostatniparametr.getRokDo());
            Integer new_rokOd = Integer.parseInt(nowe.getRokOd());
            if (old_rokDo == new_rokOd - 1) {
                return 0;
            } else {
                return 1;
            }
        }
    }
    

    public void usundoch() {
        PodatnikOpodatkowanieD p = podatnikOpodatkowanie.get(podatnikOpodatkowanie.size()-1);
        podatnikOpodatkowanie.remove(p);
        podatnikOpodatkowanieDDAO.destroy(p);
    }

    public void dodajvat() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = new ArrayList<>();
        try {
            lista.addAll(selected.getVatokres());
        } catch (Exception e) { 
            E.e(e); 
        }
        if (sprawdzvat(parametr, lista) == 1) {
            lista.add(parametr);
            selected.setVatokres(lista);
            zachowajZmiany(selected);
            parametr = new Parametr();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr VAT metoda do podatnika.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru VAT metoda. Niedopasowane okresy.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void dodajvatUE() {
        int zwrot = 0;
        zwrot = sprawdzParam(paramVatUE, selected.getParamVatUE());
        if (zwrot == 1) {
            selected.getParamVatUE().add(paramVatUE);
            zachowajZmianyParam(selected);
            podatnikDAO.edit(selected);
            paramVatUE = new ParamVatUE();
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr VAT metoda do podatnika.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } else {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru VAT metoda. Niedopasowane okresy.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public int sortparamsuper(Object o1, Object o2) {
        return Data.compare(((ParamSuper) o1).getRokOd(), ((ParamSuper) o1).getMcOd(), ((ParamSuper) o2).getRokOd(), ((ParamSuper) o2).getMcOd());
    }
    
    private int sprawdzParam(ParamSuper nowe, List stare) {
        int zwrot = 0;
        if (stare.isEmpty()) {
            nowe.setMcDo("");
            nowe.setRokDo("");
            zwrot = 1;
        } else {
            ParamSuper ostatniparametr = (ParamSuper) stare.get(stare.size() - 1);
            zwrot = Data.compare(nowe.getRokOd(), nowe.getMcOd(), ostatniparametr.getRokOd(), ostatniparametr.getMcOd());
            String[] poprzedniokres = Data.poprzedniOkres(nowe.getMcOd(), nowe.getRokOd());
            ostatniparametr.setMcDo(poprzedniokres[0]);
            ostatniparametr.setRokDo(poprzedniokres[1]);
        }
        return zwrot;
    }

    private int sprawdzvat(Parametr nowe, List<Parametr> stare) {
        int zwrot = 0;
        if (stare.isEmpty()) {
            parametr.setMcDo("");
            parametr.setRokDo("");
            zwrot = 1;
        } else {
            ostatniparametr = stare.get(stare.size() - 1);
            zwrot = Data.compare(parametr.getRokOd(), parametr.getMcOd(), ostatniparametr.getRokOd(), ostatniparametr.getMcOd());
            String[] poprzedniokres = Data.poprzedniOkres(parametr.getMcOd(), parametr.getRokOd());
            ostatniparametr.setMcDo(poprzedniokres[0]);
            ostatniparametr.setRokDo(poprzedniokres[1]);
        }
        return zwrot;
    }

    public void usunvat() {
        List<Parametr> tmp = new ArrayList<>();
        tmp.addAll(selected.getVatokres());
        tmp.remove(tmp.size() - 1);
        selected.setVatokres(tmp);
        zachowajZmiany(selected);
    }
    
    public void usunvatUE() {
        if (selected.getParamVatUE().size() > 0) {
            List<ParamVatUE> l = selected.getParamVatUE();
            ParamVatUE p = l.get(l.size()-1);
            l.remove(p);
            if (l.size() > 0) {
                ParamVatUE o = l.get(l.size()-1);
                o.setMcDo("");
                o.setRokDo("");
            }
            zachowajZmianyParam(selected);
            podatnikDAO.edit(selected);
        }
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
            zachowajZmiany(selected);
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
                zachowajZmiany(selected);
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
        zachowajZmiany(selected);
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
            zachowajZmiany(selected);
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
        zachowajZmiany(selected);
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
            if (tmp.contains(".") == false) {
                tmp  = tmp + ".0";
            }
            parametr.setParametr(tmp);
            assert tmp.contains(",") : "Nie usuwa dobrze przecinka z kwota autoryzujaca!";
            lista.add(parametr);
            selected.setKwotaautoryzujaca(lista);
            zachowajZmiany(selected);
            parametr = new Parametr();
            wpisView.aktualizuj();
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
        zachowajZmiany(selected);
        wpisView.aktualizuj();
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
            zachowajZmiany(selected);
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
        zachowajZmiany(selected);
    }
    
     public void dodajParamCzworkiPiatki() {
        selected = wpisView.getPodatnikObiekt();
        List<ParamCzworkiPiatki> lista = new ArrayList<>();
        if (selected.getParamCzworkiPiatki() == null) {
            selected.setParamCzworkiPiatki(new ArrayList<ParamCzworkiPiatki>());
        }
        if (BeanParamSuper.sprawdzrok(paramCzworkiPiatki, lista) == 0) {
            selected.getParamCzworkiPiatki().add((ParamCzworkiPiatki) paramCzworkiPiatki);
            zachowajZmiany(selected);
            paramCzworkiPiatki = new ParamCzworkiPiatki();
            Msg.msg("Dodano ustawienie piątek");
        } else {
            Msg.msg("e", "Nie udało się zmienić ustawienie piątek");
        }
    }

    public void usunParamCzworkiPiatki() {
        selected.getParamCzworkiPiatki().remove(selected.getParamCzworkiPiatki().size()-1);
        zachowajZmiany(selected);
    }

    public void dodajpole47() {
        try {
            zachowajZmiany(selected);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno udziały.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno udziału.", selected.getNazwapelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usunpole47() {
        selected.setPole47(null);
        zachowajZmiany(selected);
    }

    public void dodajUdzialy() {
        selected = wpisView.getPodatnikObiekt();
        try {
            double sumaudzialow = 0;
            for (PodatnikUdzialy p : podatnikUdzialy) {
                String udzial = p.getUdzial();
                udzial = udzial.replace(",", ".");
                double udzialkwota = Double.parseDouble(udzial);
                try {
                    if (!p.getRokDo().isEmpty()) {
                    }
                } catch (Exception ef) {
                    sumaudzialow += udzialkwota;
                }
                if (udzialy.getNazwiskoimie().equals(p.getNazwiskoimie())) {
                    throw new Exception();
                }
                if (udzialy.getNip().equals(p.getNip())) {
                    throw new Exception();
                }
            }
            String udzial = udzialy.getUdzial();
            udzial = udzial.replace(",", ".");
            double udzialkwota = Double.parseDouble(udzial);
            sumaudzialow += udzialkwota;
            if (sumaudzialow > 100.0) {
                throw new Exception();
            }
            udzialy.setPodatnikObj(selected);
            podatnikUdzialy.add(udzialy);
            podatnikUdzialyDAO.dodaj(udzialy);
            udzialy = new PodatnikUdzialy();
            Msg.msg("i", "Dodano udziały", "akordeon:form6:messages");
        } catch (Exception ex) {
            Msg.msg("e", "Niedodano udziału, wystąpił błąd. Sprawdz dane:nazwisko, procenty", "akordeon:form6:messages");
        }

    }

    public void editUdzialy() {
        try {
            Integer sumaudzialow = 0;
            for (PodatnikUdzialy p : podatnikUdzialy) {
                try {
                    if (!p.getRokDo().isEmpty()) {
                    }
                } catch (Exception ef) {
                    sumaudzialow += Integer.parseInt(p.getUdzial());
                }
            }
            if (sumaudzialow > 100) {
                throw new Exception();
            }
            podatnikUdzialyDAO.edit(udzialy);
            udzialy = new PodatnikUdzialy();
            Msg.msg("i", "Wyedytowano udziały", "akordeon:form6:messages");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Nie zmieniono udziałów", "akordeon:form6:messages");
        }
    }

    public void usunUdzialy(PodatnikUdzialy udzialy) {
        selected = wpisView.getPodatnikObiekt();
        podatnikUdzialyDAO.destroy(udzialy);
        podatnikUdzialy.remove(udzialy);
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
        } catch (Exception e) {
            E.e(e); 
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

    public void znajdzdaneregon(String formularz) {
        try {
            SzukajDaneBean.znajdzdaneregon(formularz, selectedDod, gUSView);
        } catch (Exception e) {
            E.e(e);
        }
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
        listaKontRozrachunkowych = kontoDAOfk.findKontaRozrachunkoweZpotomkami(wpisView);
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
    
    private void zachowajZmianyParam(Podatnik p) {
        p.setWprowadzil(wpisView.getWprowadzil());
        p.setDatawprowadzenia(new Date());
    }
    
    private void zachowajZmiany(Podatnik p) {
        p.setWprowadzil(wpisView.getWprowadzil());
        p.setDatawprowadzenia(new Date());
        podatnikDAO.edit(selected);
    }
    
    
    public List<FormaPrawna> getFormyprawne() {
        return formyprawne;
    }

    public void pokazWszystkieKontadoWyboru(ValueChangeEvent e) {
        wszystkiekonta = (Boolean) e.getNewValue();
        if (wszystkiekonta == true) {
            listaKontRozrachunkowych = kontoDAOfk.findKontaRozrachunkowe(wpisView);
        }
    }
    
    public void zamknijrok(PodatnikOpodatkowanieD p) {
        if (p.isZamkniety()) {
            p.setZamkniety(false);
            odksiegujdokumenty();
            Msg.msg("Otworzono rok");
        } else {
            p.setZamkniety(true);
            zaksiegujdokumenty();
            Msg.msg("Zamknięto rok");
        }
        p.setDatawprowadzenia(new Date());
        p.setKsiegowa(wpisView.getWprowadzil());
        podatnikOpodatkowanieDDAO.edit(p);
        wpisView.initpublic();
    }
    
    private void zaksiegujdokumenty() {
        List<Dokfk> selectedlist = dokDAOfk.findDokfkPodatnikRok(wpisView);
            try {
                for (Dokfk p : selectedlist) {
                    if (p.getDataksiegowania() == null) {
                        p.setDataksiegowania(new Date());
                    }
                }
                dokDAOfk.editList(selectedlist);
                Msg.msg("Zaksięgowano dokumenty w liczbie: "+selectedlist.size());
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas księgowania dokumentów.");
            }
        }
    
    private void odksiegujdokumenty() {
        List<Dokfk> selectedlist = dokDAOfk.findDokfkPodatnikRok(wpisView);
            try {
                for (Dokfk p : selectedlist) {
                    p.setDataksiegowania(null);
                }
                dokDAOfk.editList(selectedlist);
                Msg.msg("Zaksięgowano dokumenty w liczbie: "+selectedlist.size());
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas księgowania dokumentów.");
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
    public void setFormyprawne(List<FormaPrawna> formyprawne) {
        this.formyprawne = formyprawne;
    }

    public List<Konto> getListaKontKasaBank() {
        return listaKontKasaBank;
    }

    public ParamVatUE getParamVatUE() {
        return paramVatUE;
    }

    public GUSView getgUSView() {
        return gUSView;
    }

    public void setgUSView(GUSView gUSView) {
        this.gUSView = gUSView;
    }

    public void setParamVatUE(ParamVatUE paramVatUE) {
        this.paramVatUE = paramVatUE;
    }

    public void setListaKontKasaBank(List<Konto> listaKontKasaBank) {
        this.listaKontKasaBank = listaKontKasaBank;
    }

    public ParamCzworkiPiatki getParamCzworkiPiatki() {
        return paramCzworkiPiatki;
    }

    public void setParamCzworkiPiatki(ParamCzworkiPiatki paramCzworkiPiatki) {
        this.paramCzworkiPiatki = paramCzworkiPiatki;
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

    public PodatnikUdzialy getUdzialy() {
        return udzialy;
    }

    public void setUdzialy(PodatnikUdzialy udzialy) {
        this.udzialy = udzialy;
    }
   
    
            
    public Podatnik getSelectedStrata() {
        return selectedStrata;
    }
    
    public void setSelectedStrata(Podatnik selectedStrata) {
        this.selectedStrata = selectedStrata;
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

    public List<PodatnikUdzialy> getPodatnikUdzialy() {
        return podatnikUdzialy;
    }

    public PodatnikUdzialy getWybranyPodatnikUdzialy() {
        return wybranyPodatnikUdzialy;
    }

    public void setWybranyPodatnikUdzialy(PodatnikUdzialy wybranyPodatnikUdzialy) {
        this.wybranyPodatnikUdzialy = wybranyPodatnikUdzialy;
    }

    public List<PodatnikOpodatkowanieD> getPodatnikOpodatkowanie() {
        return podatnikOpodatkowanie;
    }

    public void setPodatnikOpodatkowanie(List<PodatnikOpodatkowanieD> podatnikOpodatkowanie) {
        this.podatnikOpodatkowanie = podatnikOpodatkowanie;
    }

    public PodatnikOpodatkowanieD getWybranyPodatnikOpodatkowanie() {
        return wybranyPodatnikOpodatkowanie;
    }

    public void setWybranyPodatnikOpodatkowanie(PodatnikOpodatkowanieD wybranyPodatnikOpodatkowanie) {
        this.wybranyPodatnikOpodatkowanie = wybranyPodatnikOpodatkowanie;
    }

    public boolean isWszystkiekonta() {
        return wszystkiekonta;
    }

    public void setWszystkiekonta(boolean wszystkiekonta) {
        this.wszystkiekonta = wszystkiekonta;
    }

    public void setPodatnikUdzialy(List<PodatnikUdzialy> podatnikUdzialy) {
        this.podatnikUdzialy = podatnikUdzialy;
    }
    
    public void setRodzajeDokumentowLista(List<Rodzajedok> rodzajeDokumentowLista) {
        this.rodzajeDokumentowLista = rodzajeDokumentowLista;
    }
//</editor-fold>

   
    public void zamienudzialy() {
        List<Podatnik> podatnicy = podatnikDAO.findAll();
        for (Podatnik p : podatnicy) {
            List<Udzialy> udzialy = p.getUdzialy();
            if (udzialy != null) {
                for (Udzialy r : udzialy) {
                    PodatnikUdzialy s = new PodatnikUdzialy(r,p);
                    try {
                        podatnikUdzialyDAO.dodaj(s);
                    } catch (Exception e) {
                        E.e(e);
                    }
                }
            p.setUdzialy(null);
            zachowajZmiany(p);
            }
        }
    }
    
//    public void zamienOpodatkowanieDochodowym() {
//        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
//        for (Podatnik p : podatnicy) {
//            List<Parametr> parametr = p.getPodatekdochodowy();
//            if (parametr != null) {
//                for (Parametr r : parametr) {
//                    PodatnikOpodatkowanieD s = new PodatnikOpodatkowanieD(r,p);
//                    podatnikOpodatkowanieDDAO.dodaj(s);
//                }
//            p.setPodatekdochodowy(null);
//            podatnikDAO.edit(p);
//            }
//        }
//    }

    
 

}
