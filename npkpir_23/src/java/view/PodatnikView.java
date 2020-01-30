/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFK.KontaFKBean;
import beansRegon.SzukajDaneBean;
import comparator.Kontocomparator;
import comparator.Podatnikcomparator;
import dao.DokDAO;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.PodatnikUdzialyDAO;
import dao.RodzajedokDAO;
import dao.UzDAO;
import dao.ZUSDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import data.Data;
import embeddable.Parametr;
import embeddable.Udzialy;
import entity.Dok;
import entity.Klienci;
import entity.ParamCzworkiPiatki;
import entity.ParamDeklVatNadwyzka;
import entity.ParamProcentVat;
import entity.ParamSuper;
import entity.ParamVatUE;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
import entity.Rodzajedok;
import entity.Uz;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import entityfk.Dokfk;
import entityfk.Konto;
import enumy.FormaPrawna;
import error.E;
import gus.GUSView;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.faces.event.ValueChangeListener;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;import org.joda.time.DateTime;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;

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
    private Podatnik selectedDodedycja;
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
    @ManagedProperty(value = "#{podatnikWyborView}")
    private PodatnikWyborView podatnikWyborView;
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
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
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
    private PodatnikOpodatkowanieD podatnikOpodatkowanieSelected;
    private List<FormaPrawna> formyprawne;
    private boolean wszystkiekonta;
    @Inject
    private ParamVatUE paramVatUE;
    @Inject
    private ParamProcentVat paramProcentVat;
    @Inject
    private ParamDeklVatNadwyzka paramDeklVatNadwyzka;
    @Inject
    private DokDAOfk dokDAOfk;
    private double sumaudzialow;
    @Inject
    private KlienciDAO klDAO;
    private CommandButton but1;
    private CommandButton but2;
    
    

    public PodatnikView() {
        miesiacepoweryfikacji = Collections.synchronizedList(new ArrayList<>());
        listka = new String[3];
        listka[0] = "zero";
        listka[1] = "jeden";
        listka[2] = "dwa";
        rodzajeDokumentowLista = Collections.synchronizedList(new ArrayList<>());
        listaKontRozrachunkowych = Collections.synchronizedList(new ArrayList<>());
        listaKontVAT = Collections.synchronizedList(new ArrayList<>());
        listakontoRZiS  = Collections.synchronizedList(new ArrayList<>());
        listaKontKasaBank  = Collections.synchronizedList(new ArrayList<>());
        podatnikUdzialy = Collections.synchronizedList(new ArrayList<>());
        podatnikOpodatkowanie = Collections.synchronizedList(new ArrayList<>());
        formyprawne = Collections.synchronizedList(new ArrayList<>());
        
    }
 @Inject
    private UzDAO uzDAO;
   
    @PostConstruct
    public void init() { //E.m(this);
        Uz uz = null;
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            String wprowadzilX = request.getUserPrincipal().getName();
            uz = uzDAO.findUzByLogin(wprowadzilX);
        } catch (Exception e) {
            E.e(e); 
        } 
        try {
            selectedDod.setFax("000000000");
            nazwaWybranegoPodatnika = uz.getPodatnik().getPrintnazwa();
            selected = uz.getPodatnik();
            //zrobdokumenty();
            weryfikujlisteDokumentowPodatnika();
            zweryfikujBazeBiezacegoPodatnika();
            uzupelnijListyKont();
            selectedStrata = podatnikDAO.find(wpisView.getPodatnikWpisu());
        } catch (Exception e) { E.e(e); 
        }
        rodzajeDokumentowLista = rodzajedokDAO.findListaPodatnikEdycja(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        podatnikUdzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
        podatnikOpodatkowanie = podatnikOpodatkowanieDDAO.findOpodatkowaniePodatnik(wpisView);
        biezacadata = String.valueOf(new DateTime().getYear());
        formyprawne.add(FormaPrawna.SPOLKA_CYWILNA);
        formyprawne.add(FormaPrawna.SPOLKA_Z_O_O);
        formyprawne.add(FormaPrawna.SPOLKA_KOMANDYTOWA);
        formyprawne.add(FormaPrawna.STOWARZYSZENIE);
        formyprawne.add(FormaPrawna.FEDERACJA);
        formyprawne.add(FormaPrawna.FUNDACJA);
        wybranyPodatnikOpodatkowanie.setDatarozpoczecia(wpisView.getRokWpisuSt()+"-01-01");
        wybranyPodatnikOpodatkowanie.setDatazakonczenia(wpisView.getRokWpisuSt()+"-12-31");
        wybranyPodatnikOpodatkowanie.setSymbolroku(wpisView.getRokWpisuSt());
        udzialy.setDatarozpoczecia(wpisView.getRokWpisuSt()+"-01-01");
        wybranyPodatnikOpodatkowanie.setStawkapodatkuospr(0.19);
        sumaudzialow = sumujudzialy(podatnikUdzialy);
    }
    
@Inject
private DokDAO dokDAO;
    
    public void zrobdokumenty() {
        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
        Collections.sort(podatnicy, new Podatnikcomparator());
        for (Podatnik pod : podatnicy) {
            //List<Dokfk> dokfk =  dokDAOfk.findDokfkPodatnikRok(pod, wpisView.getRokWpisuSt());
            List<Dok> dokfk =  dokDAO.zwrocBiezacegoKlientaRok(pod, wpisView.getRokWpisuSt());
            List<Rodzajedok> rodzajedok = rodzajedokDAO.findListaPodatnik(pod, wpisView.getRokWpisuSt());
            if (dokfk!=null && dokfk.size()>0 && rodzajedok!=null && rodzajedok.size()>0) {
                System.out.println("Liczba dok "+dokfk.size());
                for (Dok s : dokfk) {
                    naniesrodzaj(s,rodzajedok);
                }
                System.out.println("podatnik "+pod.getPrintnazwa());
                dokDAOfk.editList(dokfk);
            }
        }
        System.out.println("Koniec ");
    }
    
    private void naniesrodzaj(Dok s, List<Rodzajedok> rodzajedok) {
        Rodzajedok rodzaj = s.getRodzajedok();
        if (rodzaj!=null) {
            for (Rodzajedok t : rodzajedok) {
                if (t.getSkrot().equals(rodzaj.getSkrot())) {
                    s.setRodzajedok(t);
                    break;
                }
            }
        }
    }

    public void skopiujdoedycji() {
        selectedDod = selectedDodedycja;
        Msg.msg("Wybrano firmę "+selectedDod.getNazwapelnaPDF()+"do edycji");
    }
    
    public void dodaj() {
        if (selectedDod.getPesel() == null) {
            selectedDod.setPesel("00000000000");
        }
        try {
            generujIndex(selectedDod);
            sformatuj(selectedDod);
            podatnikDAO.dodaj(selectedDod);
            podatnikWyborView.init();
            Msg.msg("i", "Dodano nowego podatnika: " + selectedDod.getPrintnazwa());
            znajdzdaneregonAutomat(selectedDod.getNip(), selectedDod.getEmail());
            selectedDod = new Podatnik();
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Nie dodano nowego podatnika-firmę: " + selectedDod.getNazwapelna());
        }
    }
    
    public void znajdzdaneregonAutomat(String nip, String email) {
        try {
            Klienci aktualizuj = SzukajDaneBean.znajdzdaneregonAutomat(nip, gUSView);
            aktualizuj.setKrajnazwa("polska");
            aktualizuj.setKrajkod("PL");
            aktualizuj.setEmail(email);
            klDAO.dodaj(aktualizuj);
            Msg.msg("Dodano podatnika jako klienta ");
        } catch (Exception e) {
            Msg.msg("e","Błąd, nie dodano firmy jako klienta, lub taki klient już istnieje");
            E.e(e);
        }
    }
    
    
  
    
    public void edytujfiz() {
        if (selectedDod.getPesel() == null) {
            selectedDod.setPesel("00000000000");
        }
        try {
            sformatuj(selectedDod);
            podatnikDAO.edit(selectedDod);
            Msg.msg("i", "Wyedytowano podatnika: " + selectedDod.getPrintnazwa());
            podatnikWyborView.init();
            selectedDod = new Podatnik();
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Nie wyedytowano podatnika-firmę: " + selectedDod.getNazwapelna());
        }
    }
    
    public void resetuj() {
        try {
            selectedDodedycja = null;
            selectedDod = new Podatnik();
            Msg.msg("i", "Zresetowano formularz");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Wystąpił błąd podczas resetowania formularza");
        }
    }
    
//    public void edycjaopodatkowanie(PodatnikOpodatkowanieD item) {
//        try {
//            if (item.getDolaczonydoroku().equals("")) {
//                item.setDolaczonydoroku(null);
//            }
//            podatnikOpodatkowanieDDAO.edit(item);
//            Msg.msg("i", "Edycja roku obrotowego udana.");
//        } catch (Exception e) { 
//            E.e(e); 
//            Msg.msg("e", "Wystąpił błąd podczas edycji roku obrotowego");
//        }
//    }

    public void dodajfk() {
        if (selectedDod.getPesel() == null) {
            selectedDod.setPesel("00000000000");
        }
        try {
            selectedDod.setFirmafk(1);
            selectedDod.setPodpiscertyfikowany(true);
            generujIndex(selectedDod);
            sformatuj(selectedDod);
            podatnikDAO.dodaj(selectedDod);
            Msg.msg("i", "Dodano nowego podatnika-firmę FK: " + selectedDod.getNazwapelna());
            selectedDod =  new Podatnik();
            znajdzdaneregonAutomat(selectedDod.getNip(), selectedDod.getEmail());
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Niedodano nowego podatnika-firmę FK: " + selectedDod.getNazwapelna());
        }
        PrimeFaces.current().resetInputs("formwprowadzaniefirmy:panelwpisywanianowejfirmy");
        PrimeFaces.current().ajax().update("formwprowadzaniefirmy:panelwpisywanianowejfirmy");
    }

    public void edytuj() {
        try {
            sformatuj(selected);
            edytujhaslo(selected);
            zachowajZmiany(selected);
            Msg.msg("i", "Edytowano dane podatnika-klienta " + selected.getPrintnazwa(), "akordeon:form:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd - dane niezedytowane", "akordeon:form:msg");
        }
    }
    
    private void edytujhaslo(Podatnik selected) {
        if (selected.getKartacert()!=null && selected.getKartacert().equals("")) {
            selected.setKartacert(null);
        }
    }
    
    public void kopiujnazwe() {
        if (selectedDod.getNazwapelna() != null) {
            selectedDod.setNazwisko(selectedDod.getPrintnazwa());
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
            Msg.msg("i", "Edytowano dane podatnika-klienta " + selected.getPrintnazwa(), "akordeon:form:msg");
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
            if (el.getNewValue()!=null && !el.getNewValue().equals("")) {
                String regex = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher((String) el.getNewValue());
                if (m.matches()) {
                    selected.setDatamalyzus((String) el.getNewValue());
                    zachowajZmiany(selected);
                    Msg.msg("i", "Zmieniono parametry rozliczania ZUS", "akordeon:form:msg");
                } else {
                    Msg.msg("e", "Nieprawidłowa data!", "akordeon:form:msg");
                }
            }
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Wystąpił błąd nie zmieniono parametrów rozliczania ZUS", "akordeon:form:msg");
        }
    }
    
    public static void main(String[] args) {
//        String pricesString = "2015-04-30";
//        String regex = "^((19|20)\\d\\d)-(0?[1-9]|1[012])-(0?[1-9]|[12][0-9]|3[01])$";
//        Pattern p = Pattern.compile(regex);
//        Matcher m = p.matcher(pricesString);
//        while (m.find()) {
//        }
        String s = "WB1".toUpperCase(new Locale("pl"));
        System.out.println("nazwa "+s);
    }

    public void sformatuj(Podatnik s) throws Exception {
        try {
            String formatka = null;
            s.setWojewodztwo(s.getWojewodztwo().substring(0, 1).toUpperCase() + s.getWojewodztwo().substring(1).toLowerCase());
            if (s.getKodPKD()!=null) {
                s.setKodPKD(s.getKodPKD().toUpperCase());
            }
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
//        PrimeFaces.current().ajax().update(wywolaneprzez.getClientId());
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
//        PrimeFaces.current().ajax().update(wywolaneprzez.getClientId());
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
//        PrimeFaces.current().ajax().update(wywolaneprzez.getClientId());
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
//        PrimeFaces.current().ajax().update(wywolaneprzez.getClientId());
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
        if(wybranyPodatnikOpodatkowanie.getDatarozpoczecia()!=null && wybranyPodatnikOpodatkowanie.getDatazakonczenia()!=null) {
                wybranyPodatnikOpodatkowanie.setMcOd(Data.getMc(wybranyPodatnikOpodatkowanie.getDatarozpoczecia()));
                wybranyPodatnikOpodatkowanie.setMcDo(Data.getMc(wybranyPodatnikOpodatkowanie.getDatazakonczenia()));
                wybranyPodatnikOpodatkowanie.setPodatnikObj(wpisView.getPodatnikObiekt());
                wybranyPodatnikOpodatkowanie.setRokOd(Data.getRok(wybranyPodatnikOpodatkowanie.getDatarozpoczecia()));
                wybranyPodatnikOpodatkowanie.setRokDo(Data.getRok(wybranyPodatnikOpodatkowanie.getDatazakonczenia()));
                wybranyPodatnikOpodatkowanie.setDatawprowadzenia(new Date());
                wybranyPodatnikOpodatkowanie.setKsiegowa(wpisView.getUzer());
            if (sprawdzrok(wybranyPodatnikOpodatkowanie, podatnikOpodatkowanie) == 0) {
                podatnikOpodatkowanie.add(wybranyPodatnikOpodatkowanie);
                podatnikOpodatkowanieDDAO.dodaj(wybranyPodatnikOpodatkowanie);
                wybranyPodatnikOpodatkowanie = new PodatnikOpodatkowanieD();
                Msg.msg("Dodatno parametr pod.dochodowy do podatnika "+selected.getNazwapelna());
            } else {
                Msg.msg("e", "Niedodatno parametru pod.doch. Niedopasowane okresy. Podatnik "+selected.getPrintnazwa());
            }
        }
    }
    
    public void edytujparametrdoch() {
         if (1==1) {
            wybranyPodatnikOpodatkowanie.setMcOd(Data.getMc(wybranyPodatnikOpodatkowanie.getDatarozpoczecia()));
            wybranyPodatnikOpodatkowanie.setMcDo(Data.getMc(wybranyPodatnikOpodatkowanie.getDatazakonczenia()));
            wybranyPodatnikOpodatkowanie.setRokOd(Data.getRok(wybranyPodatnikOpodatkowanie.getDatarozpoczecia()));
            wybranyPodatnikOpodatkowanie.setRokDo(Data.getRok(wybranyPodatnikOpodatkowanie.getDatazakonczenia()));
            wybranyPodatnikOpodatkowanie.setDatawprowadzenia(new Date());
             podatnikOpodatkowanieDDAO.edit(wybranyPodatnikOpodatkowanie);
             Msg.msg("Zmieniono parametr pod.dochodowy do podatnika "+selected.getPrintnazwa());
         } else {
            Msg.msg("e", "Nie zmieniono parametru pod.doch. Niedopasowane okresy. Podatnik "+selected.getPrintnazwa());
        }
    }
    
    public void resetujparamdoch() {
        wybranyPodatnikOpodatkowanie=new PodatnikOpodatkowanieD();
        podatnikOpodatkowanieSelected = null;
        but1.setRendered(true);
        but2.setRendered(false);
         Msg.msg("Reset parametr pod.dochodowy do podatnika "+selected.getPrintnazwa());
    }
    
    
    
    public void dodajzawieszenie() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = Collections.synchronizedList(new ArrayList<>());
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
        List<Parametr> tmp = Collections.synchronizedList(new ArrayList<>());
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
            int wynik = Data.compare(nowe.getRokOd(), nowe.getMcOd(),ostatniparametr.getRokDo(), ostatniparametr.getMcDo());
            if (ostatniparametr.getId()==nowe.getId() || wynik==1) {
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
        List<Parametr> lista = Collections.synchronizedList(new ArrayList<>());
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
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru VAT metoda. Niedopasowane okresy.", selected.getPrintnazwa());
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
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru VAT metoda. Niedopasowane okresy.", selected.getPrintnazwa());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
     public void dodajparamProcentVat() {
        int zwrot = 0;
        zwrot = sprawdzParam(paramProcentVat, selected.getParamProcentVat());
        if (zwrot == 1) {
            selected.getParamProcentVat().add(paramProcentVat);
            zachowajZmianyParam(selected);
            podatnikDAO.edit(selected);
            paramProcentVat = new ParamProcentVat();
            Msg.msg("Dodano procent VAT");
        } else {
            Msg.msg("e","Wystąpił błąd. Nie dodano procent VAT");
        }
    }
     
     public void dodajparamDeklVatNadwyzka() {
        int zwrot = 0;
        zwrot = sprawdzParam(paramDeklVatNadwyzka, selected.getParamDeklVatNadwyzka());
        if (zwrot == 1) {
            selected.getParamDeklVatNadwyzka().add(paramDeklVatNadwyzka);
            zachowajZmianyParam(selected);
            podatnikDAO.edit(selected);
            paramDeklVatNadwyzka = new ParamDeklVatNadwyzka();
            Msg.msg("Dodano algorytm rozliczania zwrot VAT");
        } else {
            Msg.msg("e","Wystąpił błąd. Nie dodano algorytmu rozliczania zwrot VAT");
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
        List<Parametr> tmp = Collections.synchronizedList(new ArrayList<>());
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
    
     public void usunparamProcentVat() {
        if (selected.getParamProcentVat().size() > 0) {
            List<ParamProcentVat> l = selected.getParamProcentVat();
            ParamProcentVat p = l.get(l.size()-1);
            l.remove(p);
            if (l.size() > 0) {
                ParamProcentVat o = l.get(l.size()-1);
                o.setMcDo("");
                o.setRokDo("");
            }
            zachowajZmianyParam(selected);
            podatnikDAO.edit(selected);
        }
    }
     
      public void usunparamDeklVatNadwyzka() {
        if (selected.getParamDeklVatNadwyzka().size() > 0) {
            List<ParamDeklVatNadwyzka> l = selected.getParamDeklVatNadwyzka();
            ParamDeklVatNadwyzka p = l.get(l.size()-1);
            l.remove(p);
            if (l.size() > 0) {
                ParamDeklVatNadwyzka o = l.get(l.size()-1);
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
            List<Zusstawki> tmp = Collections.synchronizedList(new ArrayList<>());
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
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru ZUS. Niedopasowane okresy.", selected.getPrintnazwa());
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
            List<Zusstawki> tmp = Collections.synchronizedList(new ArrayList<>());
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
        List<Zusstawki> tmp = Collections.synchronizedList(new ArrayList<>());
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
        List<Zusstawki> tmp = Collections.synchronizedList(new ArrayList<>());
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
        PrimeFaces.current().executeScript("openwindow()");
        return "/manager/managerPodUstaw.xhtml?faces-redirect=true";
    }

    public void dodajremanent() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = Collections.synchronizedList(new ArrayList<>());
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
            Msg.msg("e","Niedodatno parametru remanent. Niedopasowane okresy: "+selected.getPrintnazwa());
        }
    }

    public void usunremanent() {
        List<Parametr> tmp = Collections.synchronizedList(new ArrayList<>());
        tmp.addAll(selected.getRemanent());
        tmp.remove(tmp.size() - 1);
        selected.setRemanent(tmp);
        zachowajZmiany(selected);
    }

    public void dodajkwoteautoryzujaca() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = Collections.synchronizedList(new ArrayList<>());
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
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru kwota autoryzujaca. Niedopasowane okresy.", selected.getPrintnazwa());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usunkwoteautoryzujaca() {
        List<Parametr> tmp = Collections.synchronizedList(new ArrayList<>());
        tmp.addAll(selected.getKwotaautoryzujaca());
        tmp.remove(tmp.size() - 1);
        selected.setKwotaautoryzujaca(tmp);
        zachowajZmiany(selected);
        wpisView.aktualizuj();
    }

    public void dodajnrpkpir() {
        selected = wpisView.getPodatnikObiekt();
        List<Parametr> lista = Collections.synchronizedList(new ArrayList<>());
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
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno numeru początkowego w pkpir dla podatnika. Niedopasowane okresy.", selected.getPrintnazwa());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usunnrpkpir() {
        List<Parametr> tmp = Collections.synchronizedList(new ArrayList<>());
        tmp.addAll(selected.getNumerpkpir());
        tmp.remove(tmp.size() - 1);
        selected.setNumerpkpir(tmp);
        zachowajZmiany(selected);
    }
    
     public void dodajParamCzworkiPiatki() {
        selected = wpisView.getPodatnikObiekt();
        List<ParamCzworkiPiatki> lista = Collections.synchronizedList(new ArrayList<>());
        if (selected.getParamCzworkiPiatki() == null) {
            selected.setParamCzworkiPiatki(new ArrayList<ParamCzworkiPiatki>());
        }
        if (ParametrView.sprawdzrok(paramCzworkiPiatki, lista) == 0) {
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
            if (selected.getPole47().contains(",")||selected.getPole47().contains(".")) {
                Msg.msg("e", "Podaj kwotę w pełych złotych, bez groszy po przecinku");
            } else {
                zachowajZmiany(selected);
                Msg.msg("Zachowano kwotę do przeniesienia");
            } 
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Wystąpił błąd, nie zachowano kwoty do przeniesienia");
        }
    }

    public void usunpole47() {
        selected.setPole47(null);
        zachowajZmiany(selected);
    }

    public void dodajUdzialy() {
        selected = wpisView.getPodatnikObiekt();
        try {
            for (PodatnikUdzialy p : podatnikUdzialy) {
                if (udzialy.getDatarozpoczecia().equals("")){
                    throw new Exception();
                }
                if (udzialy.getNazwiskoimie().equals("") || udzialy.getNazwiskoimie().equals(p.getNazwiskoimie())) {
                    throw new Exception();
                }
                if (!udzialy.getNip().equals("") && udzialy.getNip().equals(p.getNip())) {
                    throw new Exception();
                }
                if (!udzialy.getPesel().equals("") && udzialy.getPesel().equals(p.getPesel())) {
                    throw new Exception();
                }
            }
            if (udzialy.getDatarozpoczecia()!=null && !udzialy.getDatarozpoczecia().equals("")) {
                udzialy.setMcOd(Data.getMc(udzialy.getDatarozpoczecia()));
                udzialy.setRokOd(Data.getRok(udzialy.getDatarozpoczecia()));
            }
            if (udzialy.getDatarozpoczecia()!=null && !udzialy.getDatazakonczenia().equals("")) {
                udzialy.setRokDo(Data.getRok(udzialy.getDatazakonczenia()));
                udzialy.setMcDo(Data.getMc(udzialy.getDatazakonczenia()));
            }
            udzialy.setPodatnikObj(selected);
            podatnikUdzialy.add(udzialy);
            sumaudzialow = sumujudzialy(podatnikUdzialy);
            if (sumaudzialow > 100.0) {
                udzialy.setUdzial(null);
                throw new Exception();
            }
            podatnikUdzialyDAO.dodaj(udzialy);
            udzialy = new PodatnikUdzialy();
            Msg.msg("i", "Dodano udziały");
        } catch (Exception ex) {
            Msg.msg("e", "Niedodano udziału, wystąpił błąd. Sprawdz dane:nazwisko, procenty");
        }

    }

    public void editUdzialy() {
        try {
            Integer sumaudzialow = 0;
            if (sumujudzialy(podatnikUdzialy) > 100.0) {
                throw new Exception();
            }
            if (udzialy.getDatarozpoczecia()!=null && !udzialy.getDatarozpoczecia().equals("")) {
                udzialy.setMcOd(Data.getMc(udzialy.getDatarozpoczecia()));
                udzialy.setRokOd(Data.getRok(udzialy.getDatarozpoczecia()));
            }
            if (udzialy.getDatarozpoczecia()!=null && !udzialy.getDatazakonczenia().equals("")) {
                udzialy.setRokDo(Data.getRok(udzialy.getDatazakonczenia()));
                udzialy.setMcDo(Data.getMc(udzialy.getDatazakonczenia()));
            }
            podatnikUdzialyDAO.edit(udzialy);
            udzialy = new PodatnikUdzialy();
            Msg.msg("i", "Wyedytowano udziały", "akordeon:form6:messages");
        } catch (Exception e) {
            E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Nie zmieniono udziałów", "akordeon:form6:messages");
        }
    }

    private double sumujudzialy(List<PodatnikUdzialy> podatnikUdzialy) {
        double zwrot = 0.0;
        for (PodatnikUdzialy p : podatnikUdzialy) {
            if (p.getDatazakonczenia()==null || p.getDatazakonczenia().equals("")) {
                String udzial = p.getUdzial();
                udzial = udzial.replace(",", ".");
                zwrot += Double.parseDouble(udzial);
            }
        }
        return zwrot;
    }
    
    public void usunUdzialy(PodatnikUdzialy udzialy) {
        selected = wpisView.getPodatnikObiekt();
        podatnikUdzialyDAO.destroy(udzialy);
        podatnikUdzialy.remove(udzialy);
        Msg.msg("i", "Usunięto wskazany udział: " + udzialy.getNazwiskoimie(), "akordeon:form6:messages");
    }

    public void dodajDokKsi() {
        if (selectedDokKsi.getNazwa() != null && !selectedDokKsi.getNazwa().equals("") && selectedDokKsi.getSkrotNazwyDok() != null && !selectedDokKsi.getSkrotNazwyDok().equals("")) {
            try {
                selectedDokKsi.setPodatnikObj(wpisView.getPodatnikObiekt());
                selectedDokKsi.setSkrot(selectedDokKsi.getSkrotNazwyDok().toUpperCase(new Locale("pl")));
                selectedDokKsi.setSkrotNazwyDok(selectedDokKsi.getSkrot().toUpperCase(new Locale("pl")));
                selectedDokKsi.setNazwa(selectedDokKsi.getNazwa().toLowerCase(new Locale("pl")));
                selectedDokKsi.setRok(wpisView.getRokWpisuSt());
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
            selectedDokKsi.setSkrot(selectedDokKsi.getSkrotNazwyDok().toUpperCase(new Locale("pl")));
            selectedDokKsi.setSkrotNazwyDok(selectedDokKsi.getSkrotNazwyDok().toUpperCase(new Locale("pl")));
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

    public void weryfikujlisteDokumentowPodatnika() {
        selected = wpisView.getPodatnikObiekt();
        List<Rodzajedok> dokumentyBiezacegoPodatnika = rodzajedokDAO.findListaPodatnik(selected, wpisView.getRokWpisuSt());
        List<Rodzajedok> dokumentyBiezacegoPodatnikaRokPoprzedni = rodzajedokDAO.findListaPodatnik(selected, wpisView.getRokUprzedniSt());
        List<Rodzajedok> dokumentyBiezacegoPodatnikaOgolnie = rodzajedokDAO.findListaPodatnikNull(selected);
        if ((dokumentyBiezacegoPodatnikaRokPoprzedni==null || dokumentyBiezacegoPodatnika.isEmpty()) && (dokumentyBiezacegoPodatnikaOgolnie!=null && !dokumentyBiezacegoPodatnikaOgolnie.isEmpty())) {
            dokumentyBiezacegoPodatnikaRokPoprzedni = dokumentyBiezacegoPodatnikaOgolnie;
        }
        List<Rodzajedok> ogolnaListaDokumentow = rodzajedokView.getListaWspolnych();
        List<Konto> konta = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        try {
            if (konta!=null && konta.size()>0) {
                if (dokumentyBiezacegoPodatnikaRokPoprzedni!=null && !dokumentyBiezacegoPodatnikaRokPoprzedni.isEmpty()) {
                    for (Rodzajedok tmp : dokumentyBiezacegoPodatnikaRokPoprzedni) {
                        boolean odnaleziono = false;
                        for (Rodzajedok r: dokumentyBiezacegoPodatnika) {
                            if (r.getSkrot().equals(tmp.getSkrot())) {
                                odnaleziono = true;
                                r.setKontoRZiS(tmp.getKontoRZiS());
                                r.setKontorozrachunkowe(tmp.getKontorozrachunkowe());
                                r.setKontovat(tmp.getKontovat());
                                KontaFKBean.nanieskonta(r, kontoDAOfk);
                                rodzajedokDAO.edit(r);
                            }
                        }
                        if (odnaleziono == false) {
                            Rodzajedok nowy  = serialclone.SerialClone.clone(tmp);
                            nowy.setRok(wpisView.getRokWpisuSt());
                            nowy.setPodatnikObj(selected);
                            KontaFKBean.nanieskonta(nowy, kontoDAOfk);
                            rodzajedokDAO.dodaj(nowy);
                            dokumentyBiezacegoPodatnika.add(nowy);
                        }
                    }
                }
            } else {
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
                            nowy.setRok(wpisView.getRokWpisuSt());
                            nowy.setKontoRZiS(null);
                            nowy.setKontorozrachunkowe(null);
                            nowy.setKontovat(null);
                            rodzajedokDAO.dodaj(nowy);
                            dokumentyBiezacegoPodatnika.add(nowy);
                        }
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
            List<Podatnik> lista = Collections.synchronizedList(new ArrayList<>());
            lista.addAll(podatnikDAO.findAll());
        } catch (Exception e) { E.e(e); 
        }
    }

    public void updateDokKsi(ValueChangeListener ex) {
        PrimeFaces.current().ajax().update("akordeon:form6:parametryDokKsi");
    }

    public void znajdzdaneregon(String formularz) {
        try {
            if (selectedDod.getId() == 0) {
                SzukajDaneBean.znajdzdaneregon(formularz, selectedDod, gUSView);
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private void zweryfikujBazeBiezacegoPodatnika() {
        //dodalem to bo byly konta ze starego roku
        List<Rodzajedok> dokumentyBiezacegoPodatnika = rodzajedokDAO.findListaPodatnik(selected, wpisView.getRokWpisuSt());
        for (Rodzajedok nowy : dokumentyBiezacegoPodatnika) {
            KontaFKBean.nanieskonta(nowy, kontoDAOfk);
            rodzajedokDAO.edit(nowy);
        }
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
//            PrimeFaces.current().ajax().update("akordeon:form6:parametryDokKsi");
//        } else {
//            rodzajeDokumentowLista.addAll(listaRodzajeDokPodatnika);
//            PrimeFaces.current().ajax().update("akordeon:form6:parametryDokKsi");
//        }
    }
    
    private void uzupelnijListyKont() {
        listaKontRozrachunkowych = kontoDAOfk.findKontaRozrachunkoweZpotomkami(wpisView);
        listaKontVAT = kontoDAOfk.findKontaVAT(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        listakontoRZiS = kontoDAOfk.findKontaRZiS(wpisView);
        listakontoRZiS.addAll(kontoDAOfk.findKontaGrupa(wpisView,"3%"));
        listaKontKasaBank = kontoDAOfk.findlistaKontKasaBank(wpisView);
        listaKontKasaBank.addAll(kontoDAOfk.findKontaRozrachunkoweZpotomkami(wpisView));
        Collections.sort(listaKontRozrachunkowych, new Kontocomparator());
        Collections.sort(listaKontVAT, new Kontocomparator());
        Collections.sort(listakontoRZiS, new Kontocomparator());
        Collections.sort(listaKontKasaBank, new Kontocomparator());
    }
    
    public void naniesKontaNaDokumentRozrachunki(ValueChangeEvent e) {
        if (selectedDokKsi != null) {
            Konto wybraneKonto = (Konto) e.getNewValue();
            selectedDokKsi.setKontorozrachunkowe(wybraneKonto);
            rodzajedokDAO.edit(selectedDokKsi);
        } else {
            Msg.msg("e", "Nie wybrano dokumentu");
        }
    }
    
    public void naniesKontaNaDokumentVat(ValueChangeEvent e) {
        try {
            if (selectedDokKsi != null) {
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
        if (selectedDokKsi != null) {
            Konto wybraneKonto = (Konto) e.getNewValue();
            selectedDokKsi.setKontoRZiS(wybraneKonto);
            rodzajedokDAO.edit(selectedDokKsi);
        } else {
            Msg.msg("e", "Nie wybrano dokumentu");
        }

    }
    
    private void zachowajZmianyParam(Podatnik p) {
        p.setWprowadzil(wpisView.getUzer());
        p.setDatawprowadzenia(new Date());
    }
    
    private void zachowajZmiany(Podatnik p) {
        p.setWprowadzil(wpisView.getUzer());
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
            //odksiegujdokumenty();
            Msg.msg("Otworzono rok");
        } else {
            p.setZamkniety(true);
            zaksiegujdokumenty();
            Msg.msg("Zamknięto rok i zaksięgowano dokumenty");
        }
        p.setDatawprowadzenia(new Date());
        p.setKsiegowa(wpisView.getUzer());
        podatnikOpodatkowanieDDAO.edit(p);
        wpisView.initpublic();
    }
    
    public void zamknijrokSF(PodatnikOpodatkowanieD p) {
        if (p.isZamkniety()) {

        } else {
            p.setZamkniety(true);
            zaksiegujdokumenty();
            Msg.msg("Zamknięto rok i zaksięgowano dokumenty");
        }
        p.setDatawprowadzenia(new Date());
        p.setKsiegowa(wpisView.getUzer());
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
    
    public void odksiegujdokumenty() {
        List<Dokfk> selectedlist = dokDAOfk.findDokfkPodatnikRok(wpisView);
            try {
                for (Dokfk p : selectedlist) {
                    p.setDataksiegowania(null);
                }
                dokDAOfk.editList(selectedlist);
                Msg.msg("Odksięgowano dokumenty w liczbie: "+selectedlist.size());
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd podczas odksięgowania dokumentów.");
            }
        }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    //     public void skopiujstraty() {
//         List<Podatnik> podatnicy = podatnikDAO.findAll();
//         for (Podatnik p : podatnicy) {
//             if (p.getStratyzlatub1() != null) {
//                List<Straty> straty = Collections.synchronizedList(new ArrayList<>());
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

    public ParamProcentVat getParamProcentVat() {
        return paramProcentVat;
    }

    public void setParamProcentVat(ParamProcentVat paramProcentVat) {
        this.paramProcentVat = paramProcentVat;
    }

    public ParamDeklVatNadwyzka getParamDeklVatNadwyzka() {
        return paramDeklVatNadwyzka;
    }

    public void setParamDeklVatNadwyzka(ParamDeklVatNadwyzka paramDeklVatNadwyzka) {
        this.paramDeklVatNadwyzka = paramDeklVatNadwyzka;
    }

    public PodatnikWyborView getPodatnikWyborView() {
        return podatnikWyborView;
    }

    public void setPodatnikWyborView(PodatnikWyborView podatnikWyborView) {
        this.podatnikWyborView = podatnikWyborView;
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

    public Podatnik getSelectedDodedycja() {
        return selectedDodedycja;
    }

    public void setSelectedDodedycja(Podatnik selectedDodedycja) {
        this.selectedDodedycja = selectedDodedycja;
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

    public PodatnikOpodatkowanieD getPodatnikOpodatkowanieSelected() {
        return podatnikOpodatkowanieSelected;
    }

    public void setPodatnikOpodatkowanieSelected(PodatnikOpodatkowanieD podatnikOpodatkowanieSelected) {
        this.podatnikOpodatkowanieSelected = podatnikOpodatkowanieSelected;
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
    
    public void kopiujnazweprintnazwa() {
        List<Podatnik> l = podatnikDAO.findAllManager();
        l.forEach((Podatnik p) -> {
            p.setPrintnazwa(p.getNazwapelna());
        });
        podatnikDAO.editList(l);
        Msg.dP();
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

    private void generujIndex(Podatnik selectedDod) throws Exception {
        try {
            if (selectedDod.getNazwapelna() == null) {
                List<Podatnik> l = podatnikDAO.findAllManager();
                int rozmiar = l.size();
                String index  = nowyindex(rozmiar);
                boolean istnieje = true;
                while (istnieje) {
                    istnieje = false;
                    for (Podatnik pod : l) {
                        if (pod.getNazwapelna().equals(index)) {
                            rozmiar = rozmiar +1;
                            index = nowyindex(rozmiar);
                            istnieje = true;
                            break;
                        }
                    }
                }
                selectedDod.setNazwapelna(index);
                Msg.msg("Wygenerowano index dla firmy");
            }
        } catch (Exception e) {
            Msg.dPe();
            E.e(e);
            throw new Exception();
        }
        
    }
    
    private String nowyindex(int rozmiar) {
        String fp = selectedDod.getFormaPrawna() == null ? "f":"p";
        StringBuilder sb = new StringBuilder();
        sb.append(Data.aktualnyRokShort());
        sb.append(fp);
        sb.append(rozmiar);
        return sb.toString();
    }

    public void uzupelnijpozycje() {
        List<PodatnikOpodatkowanieD> lista = podatnikOpodatkowanieDDAO.findAll();
        for (PodatnikOpodatkowanieD p : lista) {
            p.setDatarozpoczecia(p.getRokOd()+"-01-01");
            p.setDatazakonczenia(p.getRokDo()+"-12-31");
            p.setSymbolroku(p.getRokOd());
        }
        podatnikOpodatkowanieDDAO.editList(lista);
        Msg.dP();
    }
 
    public void aktualizujopodatkowanie() {
        List<PodatnikUdzialy> udzialy = podatnikUdzialyDAO.findAll();
        for (PodatnikUdzialy p : udzialy) {
            p.setDatarozpoczecia(p.getRokOd()+"-"+p.getMcOd()+"-01");
        }
        podatnikUdzialyDAO.editList(udzialy);
        Msg.dP();
    }

    public double getSumaudzialow() {
        return sumaudzialow;
    }

    public void setSumaudzialow(double sumaudzialow) {
        this.sumaudzialow = sumaudzialow;
    }

public void naniesjezykmaila() {
    try {
        podatnikDAO.edit(selected);
        Msg.msg("Zmieniono język maila");
    } catch (Exception E) {
        Msg.msg("e","Wystąpił błąd nie zmieniono języka maila");
    }
}

public void przygotujedycjeopodatkowanie() {
    if (podatnikOpodatkowanieSelected==null) {
        Msg.msg("e","Nie wybrano roku");
    } else {
        wybranyPodatnikOpodatkowanie = podatnikOpodatkowanieSelected;
        but1.setRendered(false);
        but2.setRendered(true);
    }
}

    public CommandButton getBut1() {
        return but1;
    }

    public void setBut1(CommandButton but1) {
        this.but1 = but1;
    }

    public CommandButton getBut2() {
        return but2;
    }

    public void setBut2(CommandButton but2) {
        this.but2 = but2;
    }

}
