/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import beansPodpis.ObslugaPodpisuBean;
import beansVAT.VATDeklaracja;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjaVatSchemaPozKoncoweDAO;
import dao.DeklaracjaVatSchemaWierszSumDAO;
import dao.DeklaracjevatDAO;
import dao.EvpozycjaDAO;
import dao.EwidencjeVatDAO;
import dao.PodatnikDAO;
import dao.SchemaEwidencjaDAO;
import deklaracjaVAT7_13.VAT713;
import embeddable.EVatwpisSuma;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.SchemaEwidencjaSuma;
import embeddable.TKodUS;
import embeddable.Vatpoz;
import entity.DeklaracjaVatPozycjeKoncowe;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaPozKoncowe;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.Deklaracjevat;
import entity.Evpozycja;
import entity.Podatnik;
import entity.SchemaEwidencja;
import error.E;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class Vat7DKView implements Serializable {

    private PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT;
    @Inject
    private Deklaracjevat deklaracjakorygowana;
    @Inject
    private Deklaracjevat deklaracjawyslana;
    @Inject
    private Deklaracjevat nowadeklaracja;
    @ManagedProperty(value = "#{WpisView}")
    protected WpisView wpisView;
    @Inject
    private Vatpoz pozycjeDeklaracjiVAT;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private EwidencjeVatDAO ewidencjeVatDAO;
    @Inject
    private EvpozycjaDAO evpozycjaDAO;
    @Inject
    private TKodUS tKodUS;
    @Inject
    protected DeklaracjevatDAO deklaracjevatDAO;
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    @Inject
    private SchemaEwidencjaDAO schemaEwidencjaDAO;
    @Inject
    private DeklaracjaVatSchemaWierszSumDAO deklaracjaVatSchemaWierszSumDAO;
    @Inject
    private DeklaracjaVatSchemaPozKoncoweDAO deklaracjaVatSchemaPozKoncoweDAO;
    private int flaga;
    private String rok;
    private String mc;
    private String podatnik;
    private boolean pole43zreki;
    private boolean pole56zreki;
    private boolean pole59zreki;
    private boolean pole47zreki;
    private boolean pole53zreki;
    private boolean pole70zreki;
    private boolean pokaz56lub59;
    private boolean zachowaj;
    private boolean pierwotnazamiastkorekty;
    private List<SchemaEwidencjaSuma> sumaschemewidencjilista;
    private List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista;
    private Integer przeniesieniezpoprzedniejdeklaracji;
    private Integer zwrot25dni;
    private Integer zwrot60dni;
    private Integer zwrot180dni;
    private Integer kwotanakaserej;
    private DeklaracjaVatSchema pasujacaSchema;
    private HashMap<String, EVatwpisSuma> mapaewidencji;
    private boolean pokazinfovatzz;
    private boolean flagazt;
   
    public Vat7DKView() {
        pozycjeSzczegoloweVAT = new PozycjeSzczegoloweVAT();
        flaga = 0;
    }

    @PostConstruct
    private void init() {
        rok = wpisView.getRokWpisu().toString();
        mc = wpisView.getMiesiacWpisu();
        podatnik = wpisView.getPodatnikWpisu();
    }

    public void oblicz() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        pozycjeSzczegoloweVAT = new PozycjeSzczegoloweVAT();
        Podatnik pod = wpisView.getPodatnikObiekt();
        String vatokres = sprawdzjakiokresvat();
        if (!vatokres.equals("miesięczne")) {
            mc = Kwartaly.getMapamcMcwkw().get(wpisView.getMiesiacWpisu());
        }
        List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
        pasujacaSchema = VATDeklaracja.odnajdzscheme(vatokres, rok, mc, schemyLista);
        HashMap<String, EVatwpisSuma> sumaewidencji = ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji();
        ArrayList<EVatwpisSuma> wartosci = new ArrayList<>(sumaewidencji.values());
        //tu zduplikowac ewidencje
        //VATDeklaracja.duplikujZapisyDlaTransakcji(wartosci);
        //sumuj ewidencje 51 i52 pola
        Evpozycja evpozycja = evpozycjaDAO.find("Nabycie towarów i usług pozostałych");
        VATDeklaracja.agregacjaEwidencjiZakupowych5152(wartosci,evpozycja);
        //
        pozycjeDeklaracjiVAT.setCelzlozenia("1");
        //tutaj przeklejamy z ewidencji vat do odpowiednich pol deklaracji
        VATDeklaracja.przyporzadkujPozycjeSzczegolowe(wartosci, pozycjeSzczegoloweVAT, null);
        String kwotaautoryzujaca = kwotaautoryzujcaPobierz();
        czynieczekajuzcosdowyslania(mc);
        if (flaga != 1) {
            try {
                deklaracjakorygowana = bylajuzdeklaracjawtymmiesiacu(rok,mc);
                deklaracjawyslana = bylajuzdeklaracjawpoprzednimmiesiacu(rok,mc);
                if (flaga != 3) {
                    flaga = zbadajpobranadeklarajce(deklaracjakorygowana);
                    //pobiera tylko wtedy jak nie ma z reki
                    if (pole47zreki == false) {
                        pobierz47zpoprzedniej(deklaracjawyslana);
                    }
                } else {
                    RequestContext.getCurrentInstance().execute("varzmienkolorpola47deklvat();");
                    Msg.msg("i", "Pobrałem kwotę do przeniesienia wpisaną ręcznie");
                }
            } catch (Exception e) {
                E.e(e);
                pobierz47zustawien();
                najpierwszadeklaracja();
            }
        }
        if (flaga != 1) {
            podsumujszczegolowe();
            uzupelnijPozycjeDeklaracji(pozycjeDeklaracjiVAT, vatokres, kwotaautoryzujaca);
            nowadeklaracja = stworzdeklaracje(pozycjeDeklaracjiVAT, vatokres,pasujacaSchema);
        }
        //jezeli zachowaj bedzie true dopiero wrzuci deklaracje do kategorii do wyslania
        if (zachowaj == true) {
            if (flaga == 2) {
                deklaracjevatDAO.destroy(deklaracjakorygowana);
                deklaracjevatDAO.edit(nowadeklaracja);
                deklaracjakorygowana = new Deklaracjevat();
                Msg.msg("i", podatnik + " - zachowano korekte niewysłanej deklaracji VAT za " + rok + "-" + mc,"form:messages");
            } else if (flaga == 1) {
                Msg.msg("e", podatnik + " Deklaracja nie zachowana","form:messages");
            } else {
                deklaracjevatDAO.dodaj(nowadeklaracja);
                Msg.msg("i", podatnik + " - zachowano nową deklaracje VAT za " + rok + "-" + mc,"form:messages");
            }
            //pobieranie potwierdzenia
            RequestContext.getCurrentInstance().update("vat7:");
            zachowaj = false;
        }
    }
    
    public void obliczNowa() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        flaga = 0;
        flagazt = false;
        pozycjeSzczegoloweVAT = new PozycjeSzczegoloweVAT();
        String vatokres = sprawdzjakiokresvat();
        if (!vatokres.equals("miesięczne")) {
            mc = Kwartaly.getMapamcMcwkw().get(wpisView.getMiesiacWpisu());
        }
        List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
        pasujacaSchema = VATDeklaracja.odnajdzscheme(vatokres, rok, mc, schemyLista);
        mapaewidencji = ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji();
        ArrayList<EVatwpisSuma> pobraneewidencje = new ArrayList<>(mapaewidencji.values());
        schemawierszsumarycznylista = deklaracjaVatSchemaWierszSumDAO.findWierszeSchemy(pasujacaSchema);
        wygenerujwierszesumaryczne(pobraneewidencje, schemawierszsumarycznylista);
        pozycjeDeklaracjiVAT.setCelzlozenia("1");
        //tutaj przeklejamy z ewidencji vat do odpowiednich pol deklaracji
        List<SchemaEwidencja> schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(pasujacaSchema);
        VATDeklaracja.przyporzadkujPozycjeSzczegoloweNowe(schemaewidencjalista, pobraneewidencje, pozycjeSzczegoloweVAT, null);
        sumaschemewidencjilista = VATDeklaracja.wyluskajiPrzyporzadkujSprzedaz(schemaewidencjalista, pobraneewidencje);
        System.out.println("Koniec");
        czynieczekajuzcosdowyslania(mc);
        if (flaga != 1) {
            DeklaracjaVatSchemaWierszSum przeniesienie = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota nadwyżki z poprzedniej deklaracji");
            DeklaracjaVatSchemaWierszSum należny = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Razem (suma przychodów)");    
            DeklaracjaVatSchemaWierszSum naliczony = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Razem kwota podatku naliczonego do odliczenia");
            DeklaracjaVatSchemaWierszSum dowpłaty = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota podatku podlegająca wpłacie");    
            DeklaracjaVatSchemaWierszSum nadwyzkanaliczonego = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Nadwyżka podatku naliczonego nad należnym");    
            try {
                deklaracjakorygowana = bylajuzdeklaracjawtymmiesiacu(rok,mc);
                deklaracjawyslana = bylajuzdeklaracjawpoprzednimmiesiacu(rok,mc);
                if (flaga != 3) {
                    flaga = zbadajpobranadeklarajce(deklaracjakorygowana);
                    if (flaga == 1) {
                        return;
                    }
                    //pobiera tylko wtedy jak nie ma z reki
                    if (przeniesieniezpoprzedniejdeklaracji == null) {
                        Integer kwotazprzeniesienia = pobierz47zpoprzedniejN(deklaracjawyslana);
                        przeniesienie.getDeklaracjaVatWierszSumaryczny().setSumavat(kwotazprzeniesienia);
                        naliczony.getDeklaracjaVatWierszSumaryczny().setSumavat(naliczony.getDeklaracjaVatWierszSumaryczny().getSumavat()+kwotazprzeniesienia);
                    } else {
                        przeniesienie.getDeklaracjaVatWierszSumaryczny().setSumavat(przeniesieniezpoprzedniejdeklaracji);
                        naliczony.getDeklaracjaVatWierszSumaryczny().setSumavat(naliczony.getDeklaracjaVatWierszSumaryczny().getSumavat()+przeniesieniezpoprzedniejdeklaracji);
                    }
                } else {
                    if (przeniesieniezpoprzedniejdeklaracji == null) {
                        Integer kwotazprzeniesienia = pobierz47zustawienN();
                        przeniesienie.getDeklaracjaVatWierszSumaryczny().setSumavat(kwotazprzeniesienia);
                        naliczony.getDeklaracjaVatWierszSumaryczny().setSumavat(naliczony.getDeklaracjaVatWierszSumaryczny().getSumavat()+kwotazprzeniesienia);
                        najpierwszadeklaracja();
                        Msg.msg("i", "Pobrałem kwotę do przeniesienia z ustawień");
                    } else {
                        przeniesienie.getDeklaracjaVatWierszSumaryczny().setSumavat(przeniesieniezpoprzedniejdeklaracji);
                        naliczony.getDeklaracjaVatWierszSumaryczny().setSumavat(naliczony.getDeklaracjaVatWierszSumaryczny().getSumavat()+przeniesieniezpoprzedniejdeklaracji);
                        RequestContext.getCurrentInstance().execute("varzmienkolorpola47deklvat();");
                        Msg.msg("i", "Pobrałem kwotę do przeniesienia wpisaną ręcznie");
                    }
                    flaga = 0;
                }
            } catch (Exception ex) {
               E.e(ex);
            }
            int nż = należny.getDeklaracjaVatWierszSumaryczny().getSumavat();
            int nl = naliczony.getDeklaracjaVatWierszSumaryczny().getSumavat();
            if (nż > nl) {
                dowpłaty.getDeklaracjaVatWierszSumaryczny().setSumavat(nż - nl);
            } else {
                nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().setSumavat(nl-nż);
            }
            if (kwotanakaserej != null) {
                if (nż > nl) {
                    DeklaracjaVatSchemaWierszSum kasadoodliczenia = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota wydatkowana na kasy rej. do odliczenia");
                    kasadoodliczenia.getDeklaracjaVatWierszSumaryczny().setSumavat(kwotanakaserej);
                    dowpłaty.getDeklaracjaVatWierszSumaryczny().setSumavat((dowpłaty.getDeklaracjaVatWierszSumaryczny().getSumavat() - kwotanakaserej) > 0 ? (dowpłaty.getDeklaracjaVatWierszSumaryczny().getSumavat() - kwotanakaserej) : 0);
                } else {
                    DeklaracjaVatSchemaWierszSum kasadozwrotu = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota wydatkowana na kasy rej. do zwrotu");
                    kasadozwrotu.getDeklaracjaVatWierszSumaryczny().setSumavat(kwotanakaserej);
                    nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().setSumavat(nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().getSumavat() + kwotanakaserej);
                }
            }
            DeklaracjaVatSchemaWierszSum doprzeniesienia = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota do przeniesienia na następny okres rozliczeniowy");
            doprzeniesienia.getDeklaracjaVatWierszSumaryczny().setSumavat(nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().getSumavat());
            pokazinfovatzz =  false;
            if (zwrot25dni != null) {
                flagazt = true;
                pokazinfovatzz = true;
                DeklaracjaVatSchemaWierszSum narachunek = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota do zwrotu na rachunek bankowy");
                DeklaracjaVatSchemaWierszSum narachunek25dni = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"do zwrotu w terminie 25 dni");
                narachunek.getDeklaracjaVatWierszSumaryczny().setSumavat(zwrot25dni);
                narachunek25dni.getDeklaracjaVatWierszSumaryczny().setSumavat(zwrot25dni);
                doprzeniesienia.getDeklaracjaVatWierszSumaryczny().setSumavat(nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().getSumavat()-zwrot25dni);
            }
            if (zwrot60dni != null) {
                ustawflagazt(nż);
                DeklaracjaVatSchemaWierszSum narachunek = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota do zwrotu na rachunek bankowy");
                DeklaracjaVatSchemaWierszSum narachunek60dni = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"do zwrotu w terminie 60 dni");
                narachunek.getDeklaracjaVatWierszSumaryczny().setSumavat(zwrot60dni);
                narachunek60dni.getDeklaracjaVatWierszSumaryczny().setSumavat(zwrot60dni);
                doprzeniesienia.getDeklaracjaVatWierszSumaryczny().setSumavat(nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().getSumavat()-zwrot60dni);
            }
            if (zwrot180dni != null) {
                ustawflagazt(nż);
                DeklaracjaVatSchemaWierszSum narachunek = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota do zwrotu na rachunek bankowy");
                DeklaracjaVatSchemaWierszSum narachunek180dni = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"do zwrotu w terminie 180 dni");
                narachunek.getDeklaracjaVatWierszSumaryczny().setSumavat(zwrot180dni);
                narachunek180dni.getDeklaracjaVatWierszSumaryczny().setSumavat(zwrot180dni);
                doprzeniesienia.getDeklaracjaVatWierszSumaryczny().setSumavat(nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().getSumavat()-zwrot180dni);
            }
           VATDeklaracja.przyporzadkujPozycjeSzczegoloweSumaryczne(schemawierszsumarycznylista, pozycjeSzczegoloweVAT, null);
           System.out.println("sporzadzono deklaracje");
        }
    }
    
    private void ustawflagazt(int nż) {
        if (nż == 0) {
            flagazt = true;
        }
    }
    
    public void przelicznaliczony(ValueChangeEvent e) {
        DeklaracjaVatSchemaWierszSum przeniesienie = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota nadwyżki z poprzedniej deklaracji");
        przeniesienie.getDeklaracjaVatWierszSumaryczny().setSumavat(((Integer) e.getNewValue()));
        HashMap<String, EVatwpisSuma> mapaewidencji = ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji();
        ArrayList<EVatwpisSuma> pobraneewidencje = new ArrayList<>(mapaewidencji.values());
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumarycznylista) {
            if (p.getCzescdeklaracji().equals("D") && !p.getDeklaracjaVatWierszSumaryczny().getNazwapozycji().equals("Kwota nadwyżki z poprzedniej deklaracji")) {
                p.getDeklaracjaVatWierszSumaryczny().setSumanetto(0);
                p.getDeklaracjaVatWierszSumaryczny().setSumavat(0);
                VATDeklaracja.podsumujewidencje(pobraneewidencje, p);
            }
        }
        DeklaracjaVatSchemaWierszSum naliczony = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Razem kwota podatku naliczonego do odliczenia");
        naliczony.getDeklaracjaVatWierszSumaryczny().setSumavat(naliczony.getDeklaracjaVatWierszSumaryczny().getSumavat()+((Integer) e.getNewValue()));
        DeklaracjaVatSchemaWierszSum należny = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Razem (suma przychodów)");
        DeklaracjaVatSchemaWierszSum dowpłaty = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota podatku podlegająca wpłacie");    
        DeklaracjaVatSchemaWierszSum doprzeniesienia = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Nadwyżka podatku naliczonego nad należnym");    
        int nż = należny.getDeklaracjaVatWierszSumaryczny().getSumavat();
        int nl = naliczony.getDeklaracjaVatWierszSumaryczny().getSumavat();
        if (nż > nl) {
            dowpłaty.getDeklaracjaVatWierszSumaryczny().setSumavat(nż - nl);
        } else {
            doprzeniesienia.getDeklaracjaVatWierszSumaryczny().setSumavat(nl-nż);
        }
        VATDeklaracja.przyporzadkujPozycjeSzczegoloweSumaryczne(schemawierszsumarycznylista, pozycjeSzczegoloweVAT, null);
    }
    
    public void zapiszdeklaracje() {
        String kwotaautoryzujaca = kwotaautoryzujcaPobierz();
        String vatokres = sprawdzjakiokresvat();
        if (flaga != 1) {
            //podsumujszczegolowe();
            uzupelnijPozycjeDeklaracji(pozycjeDeklaracjiVAT, vatokres, kwotaautoryzujaca);
            nowadeklaracja = stworzdeklaracje(pozycjeDeklaracjiVAT, vatokres, pasujacaSchema);
            nowadeklaracja.setSchemawierszsumarycznylista(schemawierszsumarycznylista);
            nowadeklaracja.setPodsumowanieewidencji(mapaewidencji);
            DeklaracjaVatSchemaWierszSum doprzeniesienia = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota do przeniesienia na następny okres rozliczeniowy");
            nowadeklaracja.setKwotadoprzeniesienia(doprzeniesienia.getDeklaracjaVatWierszSumaryczny().getSumavat());
        }
        //jezeli zachowaj bedzie true dopiero wrzuci deklaracje do kategorii do wyslania
        if (flaga == 2) {
            deklaracjevatDAO.destroy(deklaracjakorygowana);
            nowadeklaracja.setSchemawierszsumarycznylista(schemawierszsumarycznylista);
            deklaracjevatDAO.edit(nowadeklaracja);
            deklaracjakorygowana = new Deklaracjevat();
            Msg.msg("i", podatnik + " - zachowano korekte niewysłanej deklaracji VAT za " + rok + "-" + mc,"form:messages");
        } else if (flaga == 1) {
            Msg.msg("e", podatnik + " Deklaracja nie zachowana","form:messages");
        } else {
            deklaracjevatDAO.dodaj(nowadeklaracja);
            Msg.msg("i", podatnik + " - zachowano nową deklaracje VAT za " + rok + "-" + mc,"form:messages");
        }
        //pobieranie potwierdzenia
        RequestContext.getCurrentInstance().update("vat7:");
        zachowaj = false;
    }
    
    private void wygenerujwierszesumaryczne(ArrayList<EVatwpisSuma> pobraneewidencje, List<DeklaracjaVatSchemaWierszSum> schemawierszsumzbazy) {
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumzbazy) {
            VATDeklaracja.podsumujewidencje(pobraneewidencje, p);
        }
    }
        
     private String kodUrzaduSkarbowegoPobierz() {
        String kodaUrzaduSkarbowego = null;
        try {
            kodaUrzaduSkarbowego = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
            boolean equals = kodaUrzaduSkarbowego.isEmpty();
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Brak wpisanego urzędu skarbowego!","form:messages");
            flaga = 1;
        }
        return kodaUrzaduSkarbowego;
    }
    
    private String kwotaautoryzujcaPobierz() {
        String kwotaautoryzujaca = null;
        String kwotaautoryzujacarokpop = null;
        try {
            Podatnik p = wpisView.getPodatnikObiekt();
            List<Parametr> listakwotaautoryzujaca = p.getKwotaautoryzujaca();
            if (listakwotaautoryzujaca.isEmpty()) {
                throw new Exception();
            }
            //bo wazny jet nie rok na deklaracji ale rok z ktorego sie wysyla
            DateTime datawysylki = new DateTime();
            String rokwysylki = String.valueOf(datawysylki.getYear());
            String rokpoprzedni = String.valueOf(datawysylki.getYear()-1);
            for (Parametr par : listakwotaautoryzujaca) {
                if (par.getRokOd().equals(rokpoprzedni)) {
                    kwotaautoryzujacarokpop = par.getParametr();
                }
                if (par.getRokOd().equals(rokwysylki)) {
                    kwotaautoryzujaca = par.getParametr();
                }
            }
            if (kwotaautoryzujaca == null) {
                kwotaautoryzujaca = kwotaautoryzujacarokpop;
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystapil blad, brak kwoty autoryzujacej w ustawieniach!","form:messages");
            flaga = 1;
        }
        return kwotaautoryzujaca;
    }

    public void odswiezprzycisk(ValueChangeEvent e) {
        RequestContext.getCurrentInstance().update("form:przyciskivat");
    }

    private String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }

    private void najpierwszadeklaracja() {
        if (flaga != 1) {
            try {
                //pobiera liste deklaracji poprzednich z danego miesiaca, jezeli nie ma wyrzuca blad
                List<Deklaracjevat> pobranalistadeklaracji = new ArrayList<>();
                pobranalistadeklaracji = deklaracjevatDAO.findDeklaracjewszystkie(rok, mc, podatnik);
                deklaracjakorygowana = pobranalistadeklaracji.get(pobranalistadeklaracji.size() - 1);

            } catch (Exception er) {
            }
            try {
                //przechwytuje blad i jezeli sa dekalracje pozniejsze wyslane i bezbledne to kaze zajrzec do nich
                List<Deklaracjevat> pobranalistadeklaracji = new ArrayList<>();
                pobranalistadeklaracji = deklaracjevatDAO.findDeklaracjeWyslane(podatnik, rok);
                if (pobranalistadeklaracji.isEmpty()) {
                    throw new Exception();
                }
                for (Deklaracjevat p : pobranalistadeklaracji) {
                    if (p.getStatus().equals("200")) {
                        flaga = 1;
                        Msg.msg("e", "A po co tworzysz tę deklaracje, jak są już poźniejsze? To błąd, zatrzymuje program, a ty zajrzyj do wysłanych.","form:messages");
                        break;
                    }
                }
            } catch (Exception e) {
                E.e(e);
                //klient swiezak nie ma zadnej deklaracji
                pozycjeDeklaracjiVAT.setCelzlozenia("1");
                nowadeklaracja.setNrkolejny(1);
                Msg.msg("i", "Utworzenie samejpierwszej za dany okres " + rok + "-" + mc,"form:messages");
            }
        }
    }

    private void pobierz47zustawien() {
        try {
            //jak jest z reki to zeby nie bralo z ustawien
            if (pole47zreki == false) {
                Podatnik pod = podatnikDAO.find(podatnik);
                String Pole47 = pod.getPole47();
                Integer PoleI47 = Integer.parseInt(Pole47);
                pozycjeSzczegoloweVAT.setPole47(Pole47);
                pozycjeSzczegoloweVAT.setPoleI47(PoleI47);
            }
            deklaracjawyslana.setIdentyfikator("lolo");
            deklaracjawyslana.setPodatnik("manolo");
        } catch (Exception e) {
            E.e(e);
            flaga = 1;
            Msg.msg("e", "Nie wpisano w ustawieniach klienta wartosci pola 47!  ","form:messages");
        }
    }
    
    private Integer pobierz47zustawienN() {
        int kwotazprzeniesienia = 0;
        try {
            //jak jest z reki to zeby nie bralo z ustawien
            if (pole47zreki == false) {
                Podatnik pod = podatnikDAO.find(podatnik);
                String pole47 = pod.getPole47();
                Integer poleI47 = Integer.parseInt(pole47);
                kwotazprzeniesienia = poleI47;
            }
        } catch (Exception e) {
            E.e(e);
            flaga = 1;
            Msg.msg("e", "Nie wpisano w ustawieniach klienta wartosci pola 47!  ","form:messages");
        }
        return kwotazprzeniesienia;
    }

    private Deklaracjevat bylajuzdeklaracjawtymmiesiacu(String rok, String mc) {
        Deklaracjevat pobrana = null;
        //pobiera liste deklaracji poprzednich z danego miesiaca
        List<Deklaracjevat> deklaracjezTegoSamegoMca = deklaracjevatDAO.findDeklaracjewszystkie(rok, mc, wpisView.getPodatnikWpisu());
        //eliminowanie testowych
        ListIterator<Deklaracjevat> it = deklaracjezTegoSamegoMca.listIterator();
        boolean znalazlemtestowe = false;
        while (it.hasNext()) {
            Deklaracjevat tmp = it.next();
            if (tmp.isTestowa() == true) {
                znalazlemtestowe = true;
                it.remove();
            }
        }
        if (znalazlemtestowe) {
            Msg.msg("i", "Dobrym zwyczajem jest usuwać deklaracje testowe przed sporządzeniem tej do wysłania.");
        }
        if (deklaracjezTegoSamegoMca != null && deklaracjezTegoSamegoMca.size() > 0) {
            pobrana = deklaracjezTegoSamegoMca.get(deklaracjezTegoSamegoMca.size() - 1);
        }
        return pobrana;
    }
    
    private Deklaracjevat bylajuzdeklaracjawpoprzednimmiesiacu(String rok, String mc) {
        Deklaracjevat pobrana = null;
        int oilesiecofnac = 1;
        if (Parametr.wyluskajParametr(wpisView.getPodatnikObiekt().getVatokres(), mc, rok).getParametr().equals("kwartalne")) {
            oilesiecofnac = 3;
        }
        String[] rokmc = Mce.zmniejszmiesiac(rok, mc, oilesiecofnac);
        String rokX = rokmc[0];
        String mcX = rokmc[1];
        //dlatego jest inna (deklaracja wyslana) bo ona musi z poprzedniego miesiaca byc. sluzy tylko tutaj
        List<Deklaracjevat> pobranalistadeklaracji2 = deklaracjevatDAO.findDeklaracjewszystkie(rokX, mcX, wpisView.getPodatnikWpisu());
        if (pobranalistadeklaracji2 != null && pobranalistadeklaracji2.size() > 0) {
            for (Deklaracjevat p : pobranalistadeklaracji2) {
                if (p.getIdentyfikator() != null && p.getStatus() != null) {
                    if (!p.getIdentyfikator().isEmpty() && !p.getStatus().isEmpty()) {
                        pobrana = p;
                    }
                }
            }
        } else {
            //jak nie ma w poprzednim miesiacu to jest luka i trzeba zrobic inaczej
            flaga = 3;
            Msg.msg("w", "Nie mogę odnaleźć deklaracji z poprzedniego okresu rozliczeniowego. Kwotę z przeniesienia trzeba wprowadzić ręcznie.");
        }
        return pobrana;
    }

    private void czynieczekajuzcosdowyslania(String mc) {
        try {
            Deklaracjevat badana = deklaracjevatDAO.findDeklaracjeDowyslania(wpisView.getPodatnikWpisu());
            if (badana == null) {

            } else {
                deklaracjakorygowana = badana;
                flaga = 2;
            }
        } catch (Exception e) {
            E.e(e);

        }
    }

    //generalnie sluzy do pobierania pola 47
    private void pobierz47zpoprzedniej(Deklaracjevat deklaracja) {
        if (flaga != 1) {
           if (deklaracja != null){
                pozycjeSzczegoloweVAT.setPole47(deklaracja.getPozycjeszczegolowe().getPole65());
                pozycjeSzczegoloweVAT.setPoleI47(deklaracja.getPozycjeszczegolowe().getPoleI65());
            } else {
                Msg.msg("w", "Nie ma żadnej dekalracji, zktórej można by pobrać pole VAT do przeniesienia");
            }
        }
    }
    //generalnie sluzy do pobierania pola 47
    private int pobierz47zpoprzedniejN(Deklaracjevat deklaracja) {
        int kwotazprzeniesienia = 0;
        if (flaga != 1) {
           if (deklaracja != null){
                kwotazprzeniesienia = deklaracja.getKwotadoprzeniesienia();
            } else {
                Msg.msg("w", "Nie ma żadnej dekalracji, zktórej można by pobrać pole VAT do przeniesienia");
            }
        }
        return kwotazprzeniesienia;
    }

    private int zbadajpobranadeklarajce(Deklaracjevat badana) {
        int f = 0;
        try {
            if (badana != null) {
                String l = " "+badana.getRok()+"-"+badana.getMiesiac()+" wysłana dnia: "+data.Data.data_yyyyMMdd(badana.getDatazlozenia())+" ";
                if (badana.getIdentyfikator().isEmpty()) {
                    Msg.msg("e", "Wcześniej sporządzona deklaracja dot. bieżacego miesiaca nie jest wyslana. Edytuje deklaracje!","form:messages");
                    pozycjeDeklaracjiVAT.setCelzlozenia("1");
                    nowadeklaracja.setNrkolejny(badana.getNrkolejny());
                    f = 2;
                } else {
                    if (badana.getStatus().startsWith("301") || badana.getStatus().startsWith("302") || badana.getStatus().isEmpty()) {
                        Msg.msg("e", "Wysłałeś już deklarację"+l+"ale nie pobrałeś UPO. Nie mozna sporządzić nowej deklaracji za miesiąc następny!","form:messages");
                        f = 1;
                    } else if (badana.getStatus().startsWith("4")) {
                        pozycjeDeklaracjiVAT.setCelzlozenia("1");
                        Msg.msg("w", "Utworzono nową deklarację"+l+". Wysłanie poprzedniej zakończyło się błędem. Jeśli chcesz sporządzić korektę usuń najpierw błędną deklarację.","form:messages");
                        nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                    } else if (badana.getStatus().startsWith("200") && pierwotnazamiastkorekty == false) {
                        nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                        pozycjeDeklaracjiVAT.setCelzlozenia("2");
                        Msg.msg("w", "Przygotowano do zachowania korekte poprawnie wyslanej deklaracji za okres  " + rok + "-" + mc,"form:messages");                    } else if (badana.getStatus().startsWith("200") && pierwotnazamiastkorekty == true) {
                        nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                        pozycjeDeklaracjiVAT.setCelzlozenia("1");
                        Msg.msg("w", "Wysłano już deklarację za ten okres. Jednakże w opcjach ustawiono wymuszenie deklaracji pierwotnej","form:messages");
                        Msg.msg("w", "Przygotowano do zachowania drugą wersję poprawnie wyslanej deklaracji za okres  " + rok + "-" + mc,"form:messages");
                    } else {
                        f = 1;
                        Msg.msg("e", "Wystąpił dziwny błąd wołaj szefa","form:messages");

                    }
                }
            } else {
                Msg.msg("i", "Nie istnieje uprzednio wysłana dekalracja za ten okres rozliczeniowy. Tworzę nową","form:messages");
            }
        } catch (Exception e) {
            E.e(e);
//            if (badana.getIdentyfikator().isEmpty()) {
//                Msg.msg("e", "Wcześniej sporządzona deklaracja dot. poprzedniego miesiaca nie jest wyslana. Nie można utworzyć nowej!","form:messages");
//                flaga = 1;
//            } else {
//                if (badana.getStatus().equals("301") || badana.getStatus().equals("302") || badana.getStatus().isEmpty()) {
//                    Msg.msg("e", "Wysłałeś już deklarację ale nie pobrałeś UPO. Nie mozna sporządzić nowej deklaracji za miesiąc następny!","form:messages");
//                    flaga = 1;
//                } else if (badana.getStatus().startsWith("4")) {
//                    Msg.msg("e", "Wysłanie deklaracji w poprzednim miesiącu zakończyło się błędem. Nie można utworzyć nowej deklaracji","form:messages");
//                    flaga = 1;
//                } else if (badana.getStatus().equals("200")) {
//                    nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
//                    pozycjeDeklaracjiVAT.setCelzlozenia("1");
//                    Msg.msg("i", "Potwierdzona udana wysyka w miesiącu poprzednim Tworzę nową dekalracje za " + rok + "-" + mc,"form:messages");
//                } else {
//                    flaga = 1;
//                    Msg.msg("i", "Wystąpił dziwny błąd wołaj szefa","form:messages");
//
//                }
//            }
        }
        return f;
    }

    private void podsumujszczegolowe() {
        if (pole43zreki == true) {
            pozycjeSzczegoloweVAT.setPoleI43(Integer.parseInt(pozycjeSzczegoloweVAT.getPole43()));
        }
        if (pole53zreki == true) {
            pozycjeSzczegoloweVAT.setPoleI53(Integer.parseInt(pozycjeSzczegoloweVAT.getPole53()));
        }
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        pozycjeDeklaracjiVAT.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
        PozycjeSzczegoloweVAT p = pozycjeSzczegoloweVAT;//podsumowanie pol szsczegolowych z pobranych czastkowych
        p.setPoleI45(p.getPoleI20() + p.getPoleI21() + p.getPoleI23() + p.getPoleI25() + p.getPoleI27() + p.getPoleI29() + p.getPoleI31() + p.getPoleI32() + p.getPoleI33() + p.getPoleI35() + p.getPoleI37() + p.getPoleI41());
        p.setPole45(String.valueOf(p.getPoleI45()));
        p.setPoleI46(p.getPoleI26() + p.getPoleI28() + p.getPoleI30() + p.getPoleI34() + p.getPoleI36() + p.getPoleI38() + p.getPoleI42() + p.getPoleI43() + p.getPoleI44());
        p.setPole46(String.valueOf(p.getPoleI46()));
        if (pole47zreki == true) {
            p.setPoleI47(Integer.parseInt(p.getPole47()));
        }
        p.setPoleI55(p.getPoleI47() + p.getPoleI48() + p.getPoleI50() + p.getPoleI52() + p.getPoleI53() + p.getPoleI54());
        p.setPole55(String.valueOf(p.getPoleI55()));
        Integer dozaplaty = p.getPoleI46() - p.getPoleI55();
        if (dozaplaty < 0) {
            pokaz56lub59 = true;
        }
        //to jets gupie bo kwota na kasy powinna byc jakos inaczej wstawiana to jest caly temat do zrobienia
        if (pole56zreki == true) {
            Integer kwota = Integer.parseInt(p.getPole56());
            if (dozaplaty > kwota) {
                p.setPoleI56(kwota);
            } else {
                p.setPoleI56(dozaplaty);
                p.setPole56(dozaplaty.toString());
            }
        }
        p.setPole57("0");
        p.setPoleI57(0);
        Integer roznica = p.getPoleI46() - p.getPoleI55() - p.getPoleI56() - p.getPoleI57();
        if (roznica > 0) {
            p.setPoleI58(roznica);
            p.setPole58(roznica.toString());
        } else {
            p.setPole58("0");
            p.setPoleI58(0);
        }
        Integer dozwrotu = p.getPoleI55() - p.getPoleI46();
        if (pole59zreki == true) {
            p.setPoleI59(Integer.parseInt(p.getPole59()));
        }

        roznica = p.getPoleI55() - p.getPoleI46() + p.getPoleI59();
        if (dozwrotu > 0) {
            p.setPoleI60(roznica);
            p.setPole60(roznica.toString());
        } else {
            p.setPole60("0");
            p.setPoleI60(0);
        }
        if (p.getPole61() != null) {
            p.setPoleI61(Integer.parseInt(p.getPole61()));
            if (p.getPoleI61() > p.getPoleI60()) {
                p.setPoleI61(p.getPoleI60());
                p.setPole61(p.getPoleI61().toString());
            }
        } else {
            p.setPole61("0");
            p.setPoleI61(0);
        }
        if (p.getPole62() != null) {
            p.setPoleI62(Integer.parseInt(p.getPole62()));
            if (p.getPoleI62() > p.getPoleI61()) {
                p.setPoleI62(p.getPoleI61());
                p.setPole62(p.getPoleI62().toString());
            }
        }
        if (p.getPole63() != null) {
            p.setPoleI63(Integer.parseInt(p.getPole63()));
            if (p.getPoleI63() > p.getPoleI61()) {
                p.setPoleI63(p.getPoleI61());
                p.setPole63(p.getPoleI63().toString());
            }
        }
        if (p.getPole64() != null) {
            p.setPoleI64(Integer.parseInt(p.getPole64()));
            if (p.getPoleI64() > p.getPoleI61()) {
                p.setPoleI64(p.getPoleI61());
                p.setPole64(p.getPoleI64().toString());
            }
        }
        roznica = p.getPoleI60() - p.getPoleI61();
        p.setPoleI65(roznica);
        p.setPole65(roznica.toString());
        if (pole70zreki == true) {
            p.setPole70("1");
            p.setPoleI70(1);
        }
    }

    private Vatpoz uzupelnijPozycjeDeklaracji(Vatpoz pozycje, String vatokres, String kwotaautoryzujaca) {
        pozycje.setPozycjekoncowe(pobierzpozycjekoncowe(pasujacaSchema));
        pozycje.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
        pozycje.setPodatnik(podatnik);
        pozycje.setRok(rok);
        if (vatokres.equals("miesięczne")) {
            pozycje.setRodzajdeklaracji("VAT-7");
        } else {
            pozycje.setRodzajdeklaracji("VAT-7K");
        }
        //String mcx = String.valueOf(Integer.parseInt(mc));
        pozycje.setMiesiac(mc);
        pozycje.setKodurzedu(tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy()));
        pozycje.setNazwaurzedu(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        pozycje.setAdres(VATDeklaracja.uzupelnijAdres(wpisView.getPodatnikObiekt()));
        pozycje.setKwotaautoryzacja(kwotaautoryzujaca);
        return pozycje;
    }
    
    private List<DeklaracjaVatPozycjeKoncowe> pobierzpozycjekoncowe(DeklaracjaVatSchema pasujacaSchema) {
        List<DeklaracjaVatSchemaPozKoncowe> schemapozycjekoncowe = deklaracjaVatSchemaPozKoncoweDAO.findWierszeSchemy(pasujacaSchema);
        List<DeklaracjaVatPozycjeKoncowe> uzupelnionewiersze = new ArrayList<>();
        if (schemapozycjekoncowe != null) {
            for (DeklaracjaVatSchemaPozKoncowe p : schemapozycjekoncowe) {
                uzupelnionewiersze.add(p.getDeklaracjaVatPozycjeKoncowe());
            }
        } else {
            schemapozycjekoncowe = new ArrayList<>();
        }
        return uzupelnionewiersze;
    }
    
    private Deklaracjevat stworzdeklaracje(Vatpoz pozycje, String vatokres, DeklaracjaVatSchema schema) {
        Deklaracjevat nowadekl = new Deklaracjevat();
        VAT713 vat713 = null;
        try {
            if (ObslugaPodpisuBean.moznaPodpisac()) {
                vat713 = new VAT713(pozycje, schema, true);
            } else {
                vat713 = new VAT713(pozycje, schema, false);
            }
        } catch (Exception ex) {
            Msg.msg("e", "Błąd podczas generowania deklaracji VAT. Nalezy sprawdzić parametry podatnika.");
            Logger.getLogger(Vat7DKView.class.getName()).log(Level.SEVERE, null, ex);
        }
        //to jest wygenerowana dekalracjia w xml
        String wiersz = vat713.getWiersz();
        nowadekl.setDeklaracja(wiersz);
        nowadekl.setRok(rok);
        if (!vatokres.equals("miesięczne")) {
            nowadekl.setMiesiac(Kwartaly.getMapamcMcwkw().get(wpisView.getMiesiacWpisu()));
            nowadekl.setMiesiackwartal(true);
        } else {
            nowadekl.setMiesiac(mc);
            nowadekl.setMiesiackwartal(false);
        }
        nowadekl.setNrkwartalu(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu()));
        nowadekl.setMiesiac(mc);
        nowadekl.setKodurzedu(pozycjeDeklaracjiVAT.getKodurzedu());
        nowadekl.setPodatnik(podatnik);
        nowadekl.setSelected(pozycjeDeklaracjiVAT);
        nowadekl.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
        nowadekl.setIdentyfikator("");
        nowadekl.setUpo("");
        nowadekl.setStatus("");
        nowadekl.setOpis("");
        nowadekl.setWzorschemy(schema.getNazwaschemy());
        nowadekl.setSchemaobj(schema);
        return nowadekl;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Vatpoz getPozycjeDeklaracjiVAT() {
        return pozycjeDeklaracjiVAT;
    }

    public void setPozycjeDeklaracjiVAT(Vatpoz pozycjeDeklaracjiVAT) {
        this.pozycjeDeklaracjiVAT = pozycjeDeklaracjiVAT;
    }

    public PozycjeSzczegoloweVAT getPozycjeSzczegoloweVAT() {
        return pozycjeSzczegoloweVAT;
    }

    public void setPozycjeSzczegoloweVAT(PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT) {
        this.pozycjeSzczegoloweVAT = pozycjeSzczegoloweVAT;
    }

    public Deklaracjevat getNowadeklaracja() {
        return nowadeklaracja;
    }

    public void setNowadeklaracja(Deklaracjevat nowadeklaracja) {
        this.nowadeklaracja = nowadeklaracja;
    }

    public int getFlaga() {
        return flaga;
    }

    public void setFlaga(int flaga) {
        this.flaga = flaga;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public boolean isPole56zreki() {
        return pole56zreki;
    }

    public void setPole56zreki(boolean pole56zreki) {
        this.pole56zreki = pole56zreki;
    }

    public boolean isPole59zreki() {
        return pole59zreki;
    }

    public void setPole59zreki(boolean pole59zreki) {
        this.pole59zreki = pole59zreki;
    }

    public boolean isZachowaj() {
        return zachowaj;
    }

    public void setZachowaj(boolean zachowaj) {
        this.zachowaj = zachowaj;
    }

    public boolean isPokaz56lub59() {
        return pokaz56lub59;
    }

    public void setPokaz56lub59(boolean pokaz56lub59) {
        this.pokaz56lub59 = pokaz56lub59;
    }

    public boolean isPole43zreki() {
        return pole43zreki;
    }

    public void setPole43zreki(boolean pole43zreki) {
        this.pole43zreki = pole43zreki;
    }

    public boolean isPole53zreki() {
        return pole53zreki;
    }

    public void setPole53zreki(boolean pole53zreki) {
        this.pole53zreki = pole53zreki;
    }

    public boolean isPole47zreki() {
        return pole47zreki;
    }

    public void setPole47zreki(boolean pole47zreki) {
        this.pole47zreki = pole47zreki;
    }

    public boolean isPierwotnazamiastkorekty() {
        return pierwotnazamiastkorekty;
    }

    public void setPierwotnazamiastkorekty(boolean pierwotnazamiastkorekty) {
        this.pierwotnazamiastkorekty = pierwotnazamiastkorekty;
    }

    public boolean isPole70zreki() {
        return pole70zreki;
    }

    public void setPole70zreki(boolean pole70zreki) {
        this.pole70zreki = pole70zreki;
    }

    public List<SchemaEwidencjaSuma> getSumaschemewidencjilista() {
        return sumaschemewidencjilista;
    }

    public void setSumaschemewidencjilista(List<SchemaEwidencjaSuma> sumaschemewidencjilista) {
        this.sumaschemewidencjilista = sumaschemewidencjilista;
    }

    public List<DeklaracjaVatSchemaWierszSum> getSchemawierszsumarycznylista() {
        return schemawierszsumarycznylista;
    }

    public void setSchemawierszsumarycznylista(List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista) {
        this.schemawierszsumarycznylista = schemawierszsumarycznylista;
    }

    public Integer getPrzeniesieniezpoprzedniejdeklaracji() {
        return przeniesieniezpoprzedniejdeklaracji;
    }

    public void setPrzeniesieniezpoprzedniejdeklaracji(Integer przeniesieniezpoprzedniejdeklaracji) {
        this.przeniesieniezpoprzedniejdeklaracji = przeniesieniezpoprzedniejdeklaracji;
    }

    public Integer getZwrot25dni() {
        return zwrot25dni;
    }

    public void setZwrot25dni(Integer zwrot25dni) {
        this.zwrot25dni = zwrot25dni;
    }

    public Integer getZwrot60dni() {
        return zwrot60dni;
    }

    public void setZwrot60dni(Integer zwrot60dni) {
        this.zwrot60dni = zwrot60dni;
    }

    public Integer getZwrot180dni() {
        return zwrot180dni;
    }

    public void setZwrot180dni(Integer zwrot180dni) {
        this.zwrot180dni = zwrot180dni;
    }

    public Integer getKwotanakaserej() {
        return kwotanakaserej;
    }

    public void setKwotanakaserej(Integer kwotanakaserej) {
        this.kwotanakaserej = kwotanakaserej;
    }

    public DeklaracjaVatSchema getPasujacaSchema() {
        return pasujacaSchema;
    }

    public void setPasujacaSchema(DeklaracjaVatSchema pasujacaSchema) {
        this.pasujacaSchema = pasujacaSchema;
    }

    public boolean isPokazinfovatzz() {
        return pokazinfovatzz;
    }

    public void setPokazinfovatzz(boolean pokazinfovatzz) {
        this.pokazinfovatzz = pokazinfovatzz;
    }

    public boolean isFlagazt() {
        return flagazt;
    }

    public void setFlagazt(boolean flagazt) {
        this.flagazt = flagazt;
    }

    

   

   
       

}
