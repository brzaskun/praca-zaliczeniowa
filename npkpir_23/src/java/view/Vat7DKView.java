/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import beansPodpis.ObslugaPodpisuBean;
import beansVAT.EwidPoz;
import beansVAT.VATDeklaracja;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjaVatSchemaPozKoncoweDAO;
import dao.DeklaracjaVatSchemaWierszSumDAO;
import dao.DeklaracjevatDAO;
import dao.EvpozycjaDAO;
import dao.PodatnikDAO;
import dao.SchemaEwidencjaDAO;
import dao.WniosekVATZDEntityDAO;
import deklaracjaVAT7_13.VAT713;
import deklaracje.vat18m.Deklaracja;
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
import entity.Evewidencja;
import entity.Podatnik;
import entity.SchemaEwidencja;
import entity.WniosekVATZDEntity;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStoreException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Vat7DKView implements Serializable {

    private PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT;
    @Inject
    private Deklaracjevat deklaracjakorygowana;
    @Inject
    private Deklaracjevat nowadeklaracja;
    @Inject
    protected WpisView wpisView;
    @Inject
    private EwidencjaVatView ewidencjaVatView;
    @Inject
    private Vatpoz pozycjeDeklaracjiVAT;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private EvpozycjaDAO evpozycjaDAO;
    @Inject
    private TKodUS tKodUS;
    @Inject
    protected DeklaracjevatDAO deklaracjevatDAO;
    @Inject
    private DeklaracjevatView deklaracjevatView;
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
    private int korektanaliczonyzmniejszajaca;
    private int korektanaliczonyzwiekszajaca;
    private Integer przeniesieniezpoprzedniejdeklaracji;
    private Integer zwrot25dni;
    private Integer zwrot60dni;
    private Integer zwrot180dni;
    private Integer kwotanakaserej;
    private Integer wntsamochoddoodliczenia;
    private DeklaracjaVatSchema pasujacaSchema;
    private HashMap<String, EVatwpisSuma> mapaewidencji;
    private boolean pokazinfovatzz;
    private boolean flagazt;
    private boolean pokazuproszczona;
    private boolean pokazpelna;
    private boolean pozwolnazapis;
    @Inject
    private WniosekVATZDEntityDAO wniosekVATZDEntityDAO;
    private WniosekVATZDEntity wniosekVATZDEntity;
    private boolean wymusozmnaczeniejakokorekte;
    private boolean niesprawdzajpoprzednichdeklaracji;
    private boolean nowejpk;
    private boolean przelewnarachunekvat;
    private boolean zaliczenienapoczetzobowiazan;
    private Integer zaliczenienapoczetzobowiazankwota;
    private String rodzajzobowiazania;
    private List<EwidPoz> pozycjeSzczegoloweNowe;
    private boolean pokazzwrotnarachunek;
   
    public Vat7DKView() {
        pozycjeSzczegoloweVAT = new PozycjeSzczegoloweVAT();
        flaga = 0;
    }

    @PostConstruct
    private void init() { //E.m(this);
        rok = wpisView.getRokWpisu().toString();
        mc = wpisView.getMiesiacWpisu();
        podatnik = wpisView.getPodatnikWpisu();
        pokazuproszczona = czypokazurproszczona();
        pokazpelna = czypokazpelna();
        List<WniosekVATZDEntity> wniosekVATZDEntityList = wniosekVATZDEntityDAO.findByPodatnikRokMcFK(wpisView);
        if(wniosekVATZDEntityList!=null && wniosekVATZDEntityList.size()>0) {
            wniosekVATZDEntity = wniosekVATZDEntityList.get(0);
        }
        nowejpk = czynowejpk();
        pozwolnazapis = obliczpozwolnanazpis();
    }
    
    private boolean czynowejpk() {
        boolean zwrot = false;
        int rok = wpisView.getRokWpisu();
        int mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        if (rok>2020 || (rok==2020 && mc > 9)) {
            zwrot = true;
        }
        return zwrot;
    }

    //to bylo w starej wersji strony
//    public void oblicz() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//        pozycjeSzczegoloweVAT = new PozycjeSzczegoloweVAT();
//        Podatnik pod = wpisView.getPodatnikObiekt();
//        String vatokres = sprawdzjakiokresvat();
//        if (!vatokres.equals("miesięczne")) {
//            mc = Kwartaly.getMapamcMcwkw().get(wpisView.getMiesiacWpisu());
//        }
//        List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
//        pasujacaSchema = VATDeklaracja.odnajdzscheme(vatokres, rok, mc, schemyLista);
//        HashMap<String, EVatwpisSuma> sumaewidencji = ewidencjeVatDAO.findByNazwaPelna(rok, mc, podatnik).getSumaewidencji();
//        ArrayList<EVatwpisSuma> wartosci = new ArrayList<>(sumaewidencji.values());
//        //tu zduplikowac ewidencje
//        //VATDeklaracja.duplikujZapisyDlaTransakcji(wartosci);
//        //sumuj ewidencje 51 i52 pola
//        Evpozycja evpozycja = evpozycjaDAO.findByNazwaPelna("Nabycie towarów i usług pozostałych");
//        VATDeklaracja.agregacjaEwidencjiZakupowych5152(wartosci,evpozycja);
//        //
//        pozycjeDeklaracjiVAT.setCelzlozenia("1");
//        //tutaj przeklejamy z ewidencji vat do odpowiednich pol deklaracji
//        VATDeklaracja.przyporzadkujPozycjeSzczegolowe(wartosci, pozycjeSzczegoloweVAT, null);
//        String kwotaautoryzujaca = kwotaautoryzujcaPobierz();
//        czynieczekajuzcosdowyslania(mc);
//        if (flaga != 1) {
//            try {
//                deklaracjakorygowana = bylajuzdeklaracjawtymmiesiacu(rok,mc);
//                deklaracjawyslana = bylajuzdeklaracjawpoprzednimmiesiacu(rok,mc);
//                if (flaga != 3) {
//                    flaga = zbadajpobranadeklarajce(deklaracjakorygowana);
//                    //pobiera tylko wtedy jak nie ma z reki
//                    if (pole47zreki == false) {
//                        pobierz47zpoprzedniej(deklaracjawyslana);
//                    }
//                } else {
//                    PrimeFaces.current().executeScript("varzmienkolorpola47deklvat();");
//                    Msg.msg("i", "Pobrałem kwotę do przeniesienia wpisaną ręcznie");
//                }
//            } catch (Exception e) {
//                E.e(e);
//                pobierz47zustawien();
//                najpierwszadeklaracja();
//            }
//        }
//        if (flaga != 1) {
//            podsumujszczegolowe();
//            uzupelnijPozycjeDeklaracji(pozycjeDeklaracjiVAT, vatokres, kwotaautoryzujaca);
//            nowadeklaracja = stworzdeklaracje(pozycjeDeklaracjiVAT, vatokres,pasujacaSchema);
//        }
//        //jezeli zachowaj bedzie true dopiero wrzuci deklaracje do kategorii do wyslania
//        if (zachowaj == true) {
//            if (flaga == 2) {
//                deklaracjevatDAO.remove(deklaracjakorygowana);
//                deklaracjevatDAO.edit(nowadeklaracja);
//                deklaracjakorygowana = new Deklaracjevat();
//                Msg.msg("i", podatnik + " - zachowano korekte niewysłanej deklaracji VAT za " + rok + "-" + mc,"form:messages");
//            } else if (flaga == 1) {
//                Msg.msg("e", podatnik + " Deklaracja nie zachowana","form:messages");
//            } else {
//                deklaracjevatDAO.create(nowadeklaracja);
//                Msg.msg("i", podatnik + " - zachowano nową deklaracje VAT za " + rok + "-" + mc,"form:messages");
//            }
//            //pobieranie potwierdzenia
//            PrimeFaces.current().ajax().update("vat7:");
//            zachowaj = false;
//        }
//    }
    public void obliczNowaDedra() {
        if (wpisView.getUzer().getNrtelefonu()==null) {
            Msg.msg("e","Brak numeru telefonu sporządzającego deklarację. Nie można jej zapisać");
        } else {
            ewidencjaVatView.stworzenieEwidencjiZDokumentowDedra(wpisView.getPodatnikObiekt());
            mapaewidencji =  ewidencjaVatView.getSumaewidencji();
            obliczNowa();
        }
    }
    public void obliczNowaPkpir() {
        if (wpisView.getUzer().getNrtelefonu()==null) {
            Msg.msg("e","Brak numeru telefonu sporządzającego deklarację. Nie można jej zapisać");
        } else {
            //ewidencjaVatView.stworzenieEwidencjiZDokumentow(wpisView.getPodatnikObiekt());
            HashMap<String,EVatwpisSuma>  mapaewidencjitmp =  ewidencjaVatView.getSumaewidencji();
            mapaewidencji = mapaewidencjitmp.entrySet().stream()
                .filter(entry -> entry.getKey().contains("Niemcy")==false)
                .collect(Collectors.toMap(
                entry -> entry.getKey(), // Klucz pozostaje bez zmian
                entry -> entry.getValue(), // Wartość pozostaje bez zmian
                (existing, replacement) -> existing, // Rozwiązanie konfliktów kluczy
                HashMap::new // Wyraźne określenie typu wynikowej mapy
            ));
            obliczNowa();
        }
    }
    public void obliczNowaFK() {
        if (wpisView.getUzer().getNrtelefonu()==null) {
            Msg.msg("e","Brak numeru telefonu sporządzającego deklarację. Nie można jej zapisać");
        } else {
            ewidencjaVatView.stworzenieEwidencjiZDokumentowFK(wpisView.getPodatnikObiekt(), wniosekVATZDEntity);
            mapaewidencji =  ewidencjaVatView.getSumaewidencji();
            obliczNowa();
        }
    }
    
    
    public void obliczNowa() {
        flaga = 0;
        flagazt = false;
        pozycjeSzczegoloweVAT = new PozycjeSzczegoloweVAT();
        String vatokres = sprawdzjakiokresvat();
        if (wpisView.getPodatnikObiekt().getNip().equals("5263158333")) {
            vatokres = "miesięczne";
        }
        if (!vatokres.equals("miesięczne")) {
            mc = Kwartaly.getMapamcMcwkw().get(wpisView.getMiesiacWpisu());
        }
        List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
        pasujacaSchema = VATDeklaracja.odnajdzscheme(vatokres, rok, mc, schemyLista);
        ArrayList<EVatwpisSuma> pobraneewidencje = new ArrayList<>(mapaewidencji.values());
        schemawierszsumarycznylista = deklaracjaVatSchemaWierszSumDAO.findWierszeSchemy(pasujacaSchema);
        for (Iterator<DeklaracjaVatSchemaWierszSum> it = schemawierszsumarycznylista.iterator(); it.hasNext();) {
            DeklaracjaVatSchemaWierszSum p = it.next();
            if (p.getNetto1vat2czek3tekst4()==5) {
                it.remove();
            }
        }
        pozycjeDeklaracjiVAT.setCelzlozenia("1");
        //tutaj przeklejamy z ewidencji vat do odpowiednich pol deklaracji
        List<SchemaEwidencja> schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(pasujacaSchema);
        if (wniosekVATZDEntity!=null) {
            korektanaliczonyzmniejszajaca = (int) wniosekVATZDEntity.getNaliczonyzmniejszenie();
            korektanaliczonyzwiekszajaca = (int) wniosekVATZDEntity.getNaliczonyzwiekszenie();
        }
        int jestschema0niema1 = wygenerujwierszesumaryczne(schemaewidencjalista, pobraneewidencje, schemawierszsumarycznylista);
        double nettouzd = ewidencjaVatView.getNettovatuzd()[0];
        double vatuzd = ewidencjaVatView.getNettovatuzd()[1];
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumarycznylista) {
            if (p.getDeklaracjaVatWierszSumaryczny().getNazwapozycji().equals("Wysokość korekty podstawy opodatkowania, o której mowa w art. 89a ust.1 ustawy")
                    ||p.getDeklaracjaVatWierszSumaryczny().getNazwapozycji().equals("Wysokość korekty podatku należnego, o której mowa w art. 89a ust.1 ustawy")) {
                VATDeklaracja.podsumujewidencjeUlgaZleDlugi(nettouzd, vatuzd, p);
            }
        }
        if (jestschema0niema1 == 0) {
            //tutaj trzeba zrobic nowa deklaracje po nowemu.
            pozycjeSzczegoloweNowe = VATDeklaracja.przyporzadkujPozycjeSzczegoloweNowe(schemaewidencjalista, pobraneewidencje, pozycjeSzczegoloweVAT, null, korektanaliczonyzmniejszajaca, korektanaliczonyzwiekszajaca);
            sumaschemewidencjilista = VATDeklaracja.wyluskajiPrzyporzadkujSprzedaz(schemaewidencjalista, pobraneewidencje);
            deklaracjakorygowana = czynieczekajuzcosdowyslania();
            flaga = zbadajpobranadeklarajce(deklaracjakorygowana);
            if (wymusozmnaczeniejakokorekte) {
                    nowadeklaracja.setNrkolejny(99);
                    pozycjeDeklaracjiVAT.setCelzlozenia("2");
                    Msg.msg("w", "Przygotowano do zachowania ręczną korekte deklaracji za okres  " + rok + "-" + mc);   
            } else {
                Deklaracjevat ostatniawyslana = cozostalowyslane();
                flaga = zbadajpobranadeklarajce(ostatniawyslana);
            }
            if (flaga != 1) {
                DeklaracjaVatSchemaWierszSum przeniesienie = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota nadwyżki z poprzedniej deklaracji");
                DeklaracjaVatSchemaWierszSum należny = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Razem (suma przychodów)");
                DeklaracjaVatSchemaWierszSum naliczony = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Razem kwota podatku naliczonego do odliczenia");
                DeklaracjaVatSchemaWierszSum dowpłaty = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota podatku podlegająca wpłacie");    
                DeklaracjaVatSchemaWierszSum nadwyzkanaliczonego = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Nadwyżka podatku naliczonego nad należnym");
              
                try {
                    //niepotrzebne bo jest wyzej
                    //deklaracjakorygowana = bylajuzdeklaracjawtymmiesiacu(rok,mc);
                    int przeniesieniekwota = przeniesieniezpoprzedniejdeklaracji !=null ? przeniesieniezpoprzedniejdeklaracji:0;
                    if (!niesprawdzajpoprzednichdeklaracji && !wymusozmnaczeniejakokorekte) {
                        Deklaracjevat deklaracjaPopMc = bylajuzdeklaracjawpoprzednimmiesiacu(rok,mc);
                        if (deklaracjaPopMc != null) {
                            //pobiera tylko wtedy jak nie ma z reki
                            if (przeniesieniezpoprzedniejdeklaracji == null) {
                                przeniesieniekwota = pobierz47zpoprzedniejN(deklaracjaPopMc);
                            }
                        } else {
                            if (!wpisView.getPodatnikObiekt().getNip().equals("5263158333")) {
                                if (przeniesieniezpoprzedniejdeklaracji == null) {
                                    przeniesieniekwota = pobierz47zustawienN();
                                    Msg.msg("i", "Pobrałem kwotę do przeniesienia z ustawień");
                                } 
                            }
                        }
                    }
                    przeniesienie.getDeklaracjaVatWierszSumaryczny().setSumavat(przeniesieniekwota);
                    naliczony.getDeklaracjaVatWierszSumaryczny().setSumavat(naliczony.getDeklaracjaVatWierszSumaryczny().getSumavat()+przeniesieniekwota+korektanaliczonyzmniejszajaca+korektanaliczonyzwiekszajaca);
                } catch (Exception ex) {
                   E.e(ex);
                }
                int nż = należny.getDeklaracjaVatWierszSumaryczny().getSumavat();
                if (wntsamochoddoodliczenia != null) {
                    DeklaracjaVatSchemaWierszSum naleznyWNTsamochod = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Wysokość podatku należnego od wewnątrzwspólnotowego nabycia środków transportu");
                    naleznyWNTsamochod.getDeklaracjaVatWierszSumaryczny().setSumavat(wntsamochoddoodliczenia);
                    nż = nż - naleznyWNTsamochod.getDeklaracjaVatWierszSumaryczny().getSumavat();
                    należny.getDeklaracjaVatWierszSumaryczny().setSumavat(nż);
                }
                int nl = naliczony.getDeklaracjaVatWierszSumaryczny().getSumavat();
                if (nż > nl) {
                    dowpłaty.getDeklaracjaVatWierszSumaryczny().setSumavat(nż - nl);
                } else {
                    nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().setSumavat(nl-nż);
                }
                if (kwotanakaserej != null) {
                    kasarejestrujaca(nż, nl, dowpłaty, nadwyzkanaliczonego);
                }
                boolean nowyjpk2020 = wpisView.isJpk2020M()||wpisView.isJpk2020K()||wpisView.isJpk2020M2()||wpisView.isJpk2020K2();
                DeklaracjaVatSchemaWierszSum doprzeniesienia = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota do przeniesienia na następny okres rozliczeniowy");
                doprzeniesienia.getDeklaracjaVatWierszSumaryczny().setSumavat(nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().getSumavat());
                if (doprzeniesienia.getDeklaracjaVatWierszSumaryczny().getSumavat()>0.0) {
                    pokazzwrotnarachunek =true;
                }
                pokazinfovatzz =  false;
                int dozwrotujuznarachunek = 0;
                if (zwrot25dni != null) {
                    flagazt = true;
                    pokazinfovatzz = true;
                    rozliczprzeniesienie(doprzeniesienia,nadwyzkanaliczonego, "Kwota do zwrotu na rachunek bankowy","do zwrotu w terminie 25 dni",zwrot25dni);
                    if (nowyjpk2020) {
                        DeklaracjaVatSchemaWierszSum zmienna = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"do zwrotu w terminie 25 dni");
                        zmienna.getDeklaracjaVatWierszSumaryczny().setCzekpole(true);
                        dozwrotujuznarachunek = dozwrotujuznarachunek+zwrot25dni;
                    }
                }
                if (zwrot60dni != null) {
                    ustawflagazt(nż);
                    rozliczprzeniesienie(doprzeniesienia,nadwyzkanaliczonego, "Kwota do zwrotu na rachunek bankowy","do zwrotu w terminie 60 dni",zwrot60dni);
                    if (nowyjpk2020) {
                        DeklaracjaVatSchemaWierszSum zmienna = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"do zwrotu w terminie 60 dni");
                        zmienna.getDeklaracjaVatWierszSumaryczny().setCzekpole(true);
                        dozwrotujuznarachunek = dozwrotujuznarachunek+zwrot60dni;
                    }
                }
                if (zwrot180dni != null) {
                    ustawflagazt(nż);
                    rozliczprzeniesienie(doprzeniesienia,nadwyzkanaliczonego, "Kwota do zwrotu na rachunek bankowy","do zwrotu w terminie 180 dni",zwrot180dni);
                    if (nowyjpk2020) {
                        DeklaracjaVatSchemaWierszSum zmienna = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"do zwrotu w terminie 180 dni");
                        zmienna.getDeklaracjaVatWierszSumaryczny().setCzekpole(true);
                        dozwrotujuznarachunek = dozwrotujuznarachunek+zwrot180dni;
                    }
                }
                if (zwrot25dni == null && zwrot60dni == null && zwrot180dni == null && nowyjpk2020) {
                    VATDeklaracja.usunschemawiersz(schemawierszsumarycznylista,"Kwota do zwrotu na rachunek bankowy");
                }
                if (przelewnarachunekvat) {
                    DeklaracjaVatSchemaWierszSum zmienna = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Zwrot na rachunek VAT, o którym mowa w art. 87 ust. 6a ustawy");
                    zmienna.getDeklaracjaVatWierszSumaryczny().setCzekpole(true);
                } else {
                    VATDeklaracja.usunschemawiersz(schemawierszsumarycznylista,"Zwrot na rachunek VAT, o którym mowa w art. 87 ust. 6a ustawy");
                }
                if (zaliczenienapoczetzobowiazan) {
                    DeklaracjaVatSchemaWierszSum zmienna = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Zaliczenie zwrotu podatku na poczet przyszłych zobowiązań podatkowych");
                    zmienna.getDeklaracjaVatWierszSumaryczny().setCzekpole(true);
                } else {
                    VATDeklaracja.usunschemawiersz(schemawierszsumarycznylista,"Zaliczenie zwrotu podatku na poczet przyszłych zobowiązań podatkowych");
                }
                if (zaliczenienapoczetzobowiazankwota!=null&&zaliczenienapoczetzobowiazankwota>0) {
                    DeklaracjaVatSchemaWierszSum zmienna = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Wysokość zwrotu do zaliczenia na poczet przyszłych zobowiązań podatkowych");
                    zmienna.getDeklaracjaVatWierszSumaryczny().setSumavat(zaliczenienapoczetzobowiazankwota);
                    doprzeniesienia.getDeklaracjaVatWierszSumaryczny().setSumavat(nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().getSumavat()-dozwrotujuznarachunek);
                } else {
                    VATDeklaracja.usunschemawiersz(schemawierszsumarycznylista,"Wysokość zwrotu do zaliczenia na poczet przyszłych zobowiązań podatkowych");
                }
                if (rodzajzobowiazania!=null && !rodzajzobowiazania.isEmpty()) {
                    DeklaracjaVatSchemaWierszSum zmienna = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Rodzaj przyszłego zobowiązania podatkowego");
                    zmienna.getDeklaracjaVatWierszSumaryczny().setStringpole(rodzajzobowiazania);
                } else {
                    VATDeklaracja.usunschemawiersz(schemawierszsumarycznylista,"Rodzaj przyszłego zobowiązania podatkowego");
                }
                VATDeklaracja.przyporzadkujPozycjeSzczegoloweSumaryczne(schemawierszsumarycznylista, pozycjeSzczegoloweVAT, null);
            }
        }
    }
    
        
    private void rozliczprzeniesienie(DeklaracjaVatSchemaWierszSum doprzeniesienia, DeklaracjaVatSchemaWierszSum nadwyzkanaliczonego, String kwota_do_zwrotu_na_rachunek_bankowy, String do_zwrotu_w_terminie_dni, Integer zwrotdni) {
        DeklaracjaVatSchemaWierszSum narachunek = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,kwota_do_zwrotu_na_rachunek_bankowy);
        DeklaracjaVatSchemaWierszSum narachunek25dni = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,do_zwrotu_w_terminie_dni);
        narachunek.getDeklaracjaVatWierszSumaryczny().setSumavat(zwrotdni);
        narachunek25dni.getDeklaracjaVatWierszSumaryczny().setSumavat(zwrotdni);
        doprzeniesienia.getDeklaracjaVatWierszSumaryczny().setSumavat(nadwyzkanaliczonego.getDeklaracjaVatWierszSumaryczny().getSumavat()-zwrotdni);
    }
    
    private void kasarejestrujaca(int nż, int nl, DeklaracjaVatSchemaWierszSum dowpłaty, DeklaracjaVatSchemaWierszSum nadwyzkanaliczonego) {
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
    
   
    
    private void ustawflagazt(int nż) {
        if (nż == 0) {
            flagazt = true;
        }
    }
    
    public void przelicznaliczony(ValueChangeEvent e) {
        DeklaracjaVatSchemaWierszSum przeniesienie = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota nadwyżki z poprzedniej deklaracji");
        przeniesienie.getDeklaracjaVatWierszSumaryczny().setSumavat(((Integer) e.getNewValue()));
        ArrayList<EVatwpisSuma> pobraneewidencje = new ArrayList<>(mapaewidencji.values());
        for (DeklaracjaVatSchemaWierszSum p : schemawierszsumarycznylista) {
            if (p.getCzescdeklaracji().equals("D") && !p.getDeklaracjaVatWierszSumaryczny().getNazwapozycji().equals("Kwota nadwyżki z poprzedniej deklaracji")) {
                p.getDeklaracjaVatWierszSumaryczny().setSumanetto(0);
                p.getDeklaracjaVatWierszSumaryczny().setSumavat(0);
                VATDeklaracja.podsumujewidencje(null, pobraneewidencje, p);
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
        String kwotaautoryzujaca = "0.0";
        if (!wpisView.getPodatnikObiekt().isPodpiscertyfikowany()) {
            kwotaautoryzujaca = kwotaautoryzujcaPobierz();
        }
        String vatokres = sprawdzjakiokresvat();
        deklaracjakorygowana = czynieczekajuzcosdowyslania();
        if (flaga == 2 && deklaracjakorygowana!=null) {
            deklaracjevatDAO.remove(deklaracjakorygowana);
            deklaracjakorygowana = null;
            flaga = 0;
            Msg.msg("i", "Podatnik: "+wpisView.getPrintNazwa() + " - usunięto poprzednią niewysłaną deklarację VAT za " + rok + "-" + mc);
        }
        boolean vatzd = wniosekVATZDEntity!=null;
        boolean splitpayment = false;
        if (flaga != 1) {
            pozycjeDeklaracjiVAT.setFirma1osobafiz0(wpisView.isKsiegirachunkowe());
            uzupelnijPozycjeDeklaracji(pozycjeDeklaracjiVAT, vatokres, kwotaautoryzujaca);
            nowadeklaracja = stworzdeklaracje(pozycjeDeklaracjiVAT, vatokres, pasujacaSchema, vatzd, splitpayment, pozycjeSzczegoloweNowe);
            nowadeklaracja.setSchemawierszsumarycznylista(schemawierszsumarycznylista);
            nowadeklaracja.setPodsumowanieewidencji(mapaewidencji);
            DeklaracjaVatSchemaWierszSum doprzeniesienia = VATDeklaracja.pobierzschemawiersz(schemawierszsumarycznylista,"Kwota do przeniesienia na następny okres rozliczeniowy");
            nowadeklaracja.setKwotadoprzeniesienia(doprzeniesienia.getDeklaracjaVatWierszSumaryczny().getSumavat());
        }
        //jezeli zachowaj bedzie true dopiero wrzuci deklaracje do kategorii do wyslania
        if (flaga == 1) {
            Msg.msg("e", "Podatnik: "+wpisView.getPrintNazwa() + " Deklaracja nie zachowana");
        } else {
            if (vatzd) {
                dodajzalacznikVATZD(nowadeklaracja);
                wniosekVATZDEntity.getDeklaracjevat().add(nowadeklaracja);
                nowadeklaracja.setWniosekVATZDEntity(wniosekVATZDEntity);
            }
            nowadeklaracja.setDatasporzadzenia(new Date());
            nowadeklaracja.setIdentyfikator("dla jpk");
            nowadeklaracja.setStatus("388");
            nowadeklaracja.setOpis("zachowana dla wysłania z jpk");
            nowadeklaracja.setDatazlozenia(new Date());
            nowadeklaracja.setSporzadzil(wpisView.getUzer().getImie() + " " + wpisView.getUzer().getNazw());
            deklaracjevatDAO.create(nowadeklaracja);
            if (vatzd) {
                wniosekVATZDEntityDAO.edit(wniosekVATZDEntity);
            }
            deklaracjevatView.init();
            Msg.msg("i", "Podatnik: "+wpisView.getPrintNazwa() + " - zachowano nową deklaracje VAT za " + rok + "-" + mc);
            
        }
        //pobieranie potwierdzenia
        nowadeklaracja = null;
        flaga = 1;
        zachowaj = false;
    }
    
    public void dodajzalacznikVATZD(Deklaracjevat temp) {
        String podatnik = wpisView.getPodatnikWpisu();
        String zalacznik;
        String trescdeklaracji = temp.getDeklaracja();
        //pozbywamy sie koncowki </ns:Deklaracja> ale szukamy wpierw czy isteje juz inny zalacznik
        int lastIndexOf = trescdeklaracji.lastIndexOf("</Zalaczniki>");
        if (lastIndexOf == -1) {
            zalacznik = "<Zalaczniki>"+wniosekVATZDEntity.getZalacznik()+"</Zalaczniki>";
            lastIndexOf = trescdeklaracji.lastIndexOf("<podp:DaneAutoryzujace");
            if (lastIndexOf==-1) {
                lastIndexOf = trescdeklaracji.lastIndexOf("</Deklaracja>");
            }
        } else {
            zalacznik = wniosekVATZDEntity.getZalacznik();
        }
        String koncowka = trescdeklaracji.substring(lastIndexOf);
        trescdeklaracji = trescdeklaracji.substring(0, lastIndexOf);
        //zalaczamy zalacznik
        trescdeklaracji = trescdeklaracji+zalacznik;
        //dodajemy usuniete zakonczenie
        trescdeklaracji = trescdeklaracji+koncowka;
        temp.setDeklaracja(trescdeklaracji);
        try{
            Msg.msg("i","Sukces, załączono VAT-ZD.");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Wystapil błąd. Nie udało się załączyć VAT-ZD.");
        }
    }
    
    private int wygenerujwierszesumaryczne(List<SchemaEwidencja> schemaewidencjalista, List<EVatwpisSuma> pobraneewidencje, List<DeklaracjaVatSchemaWierszSum> schemawierszsumzbazy) {
        int zwrot = 0;
        List<EVatwpisSuma> ewidencjenowe = Collections.synchronizedList(new ArrayList<>());
        if (schemaewidencjalista != null) {
            for (EVatwpisSuma r : pobraneewidencje) {
                SchemaEwidencja se = szukaniewieszaSchemy(schemaewidencjalista, r.getEwidencja());
                if (se==null) {
                    Msg.msg("e","Brak zdefiniowanej schemy deklaracji w ustawieniach programu. Nie można wygenerować deklaracji");
                    zwrot = 1;
                } else {
                    SchemaEwidencja sm = se.getSchemamacierzysta();
                    if (sm != null) {
                        Evewidencja ewm = sm.getEvewidencja();
                        r.setNiesumuj(true);
                        boolean nieznaleziono = true;
                        for (EVatwpisSuma r1 : pobraneewidencje) {
                            if (r1.getEwidencja().equals(ewm)) {
                                r1.setNetto(r1.getNetto().add(r.getNetto()));
                                r1.setVat(r1.getVat().add(r.getVat()));
                                nieznaleziono = false;
                            }
                        }
                        if (nieznaleziono) {
                            ewidencjenowe.add(new EVatwpisSuma(ewm, r.getNetto(), r.getVat(), ""));
                        }
                    }
                }
            }
            if(ewidencjenowe.size() > 0) {
                pobraneewidencje.addAll(ewidencjenowe);
            }
            for (DeklaracjaVatSchemaWierszSum p : schemawierszsumzbazy) {
                VATDeklaracja.podsumujewidencje(schemaewidencjalista, pobraneewidencje, p);
            }
        }
        return zwrot;
    }
    
    private static SchemaEwidencja szukaniewieszaSchemy(List<SchemaEwidencja> schemaewidencjalista, Evewidencja evewidencja) {
        SchemaEwidencja s = null;
        for (SchemaEwidencja p : schemaewidencjalista) {
            if (p.getEvewidencja().equals(evewidencja)) {
                s = p;
            }
        }
        return s;
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
        PrimeFaces.current().ajax().update("form:przyciskivat");
    }

    private String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }

//    private void najpierwszadeklaracja() {
//        if (flaga != 1) {
//            try {
//                //pobiera liste deklaracji poprzednich z danego miesiaca, jezeli nie ma wyrzuca blad
//                List<Deklaracjevat> pobranalistadeklaracji = Collections.synchronizedList(new ArrayList<>());
//                pobranalistadeklaracji = deklaracjevatDAO.findDeklaracjewszystkie(rok, mc, podatnik);
//                deklaracjakorygowana = pobranalistadeklaracji.get(pobranalistadeklaracji.size() - 1);
//
//            } catch (Exception er) {
//            }
//            try {
//                //przechwytuje blad i jezeli sa dekalracje pozniejsze wyslane i bezbledne to kaze zajrzec do nich
//                List<Deklaracjevat> pobranalistadeklaracji = Collections.synchronizedList(new ArrayList<>());
//                pobranalistadeklaracji = deklaracjevatDAO.findDeklaracjeWyslane(podatnik, rok);
//                if (pobranalistadeklaracji.isEmpty()) {
//                    throw new Exception();
//                }
//                for (Deklaracjevat p : pobranalistadeklaracji) {
//                    if (p.getStatus().equals("200")) {
//                        flaga = 1;
//                        Msg.msg("e", "A po co tworzysz tę deklaracje, jak są już poźniejsze? To błąd, zatrzymuje program, a ty zajrzyj do wysłanych.","form:messages");
//                        break;
//                    }
//                }
//            } catch (Exception e) {
//                E.e(e);
//                //klient swiezak nie ma zadnej deklaracji
//                pozycjeDeklaracjiVAT.setCelzlozenia("1");
//                nowadeklaracja.setNrkolejny(1);
//                Msg.msg("i", "Utworzenie samejpierwszej za dany okres " + rok + "-" + mc,"form:messages");
//            }
//        }
//    }

    private void pobierz47zustawien() {
        try {
            //jak jest z reki to zeby nie bralo z ustawien
            if (pole47zreki == false) {
                Podatnik pod = podatnikDAO.findByNazwaPelna(podatnik);
                String Pole47 = pod.getPole47();
                Integer PoleI47 = Integer.parseInt(Pole47);
                pozycjeSzczegoloweVAT.setPole47(Pole47);
                pozycjeSzczegoloweVAT.setPoleI47(PoleI47);
            }
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
                String pole47 = wpisView.getPodatnikObiekt().getPole47();
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
//to chyba nie potrzebne juz
//    private Deklaracjevat bylajuzdeklaracjawtymmiesiacu(String rok, String mc) {
//        Deklaracjevat pobrana = null;
//        //pobiera liste deklaracji poprzednich z danego miesiaca
//        List<Deklaracjevat> deklaracjezTegoSamegoMca = deklaracjevatDAO.findDeklaracjewszystkie(rok, mc, wpisView.getPodatnikWpisu());
//        //eliminowanie testowych
//        ListIterator<Deklaracjevat> it = deklaracjezTegoSamegoMca.listIterator();
//        boolean znalazlemtestowe = false;
//        while (it.hasNext()) {
//            Deklaracjevat tmp = it.next();
//            if (tmp.isTestowa() == true) {
//                znalazlemtestowe = true;
//                it.remove();
//            }
//        }
//        if (znalazlemtestowe) {
//            Msg.msg("i", "Dobrym zwyczajem jest usuwać deklaracje testowe przed sporządzeniem tej do wysłania.");
//        }
//        if (deklaracjezTegoSamegoMca != null && deklaracjezTegoSamegoMca.size() > 0) {
//            pobrana = deklaracjezTegoSamegoMca.get(deklaracjezTegoSamegoMca.size() - 1);
//        }
//        return pobrana;
//    }
    
    private Deklaracjevat bylajuzdeklaracjawpoprzednimmiesiacu(String rok, String mc) {
        Deklaracjevat pobrana = null;
        int oilesiecofnac = 1;
        if (ParametrView.zwrocParametr(wpisView.getPodatnikObiekt().getVatokres(), Integer.parseInt(rok), Integer.parseInt(mc)).equals("kwartalne")) {
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
             Msg.msg("w", "Nie mogę odnaleźć deklaracji z poprzedniego okresu rozliczeniowego. Kwotę z przeniesienia trzeba wprowadzić ręcznie.");
        }
        return pobrana;
    }
    
    private Deklaracjevat cozostalowyslane() {
        Deklaracjevat badana = null;
        try {
            List<Deklaracjevat> wyslane = deklaracjevatDAO.findDeklaracjeWyslaneMc(wpisView.getPodatnikWpisu(),wpisView.getRokWpisuSt(), mc);
            if (!wyslane.isEmpty()) {
                badana = wyslane.get(wyslane.size()-1);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return badana;
    }

    private Deklaracjevat czynieczekajuzcosdowyslania() {
        Deklaracjevat badana = null;
        try {
            badana = deklaracjevatDAO.findDeklaracjeDowyslania(wpisView.getPodatnikWpisu());
            if (badana != null) {
                flaga = 2;
            }
        } catch (Exception e) {
            E.e(e);
        }
        return badana;
    }

    //generalnie sluzy do pobierania pola 47
    private void pobierz47zpoprzedniej(Deklaracjevat deklaracja) {
        if (flaga != 1) {
           if (deklaracja != null){
                pozycjeSzczegoloweVAT.setPole47(deklaracja.getSelected().getPozycjeszczegolowe().getPole65());
                pozycjeSzczegoloweVAT.setPoleI47(deklaracja.getSelected().getPozycjeszczegolowe().getPoleI65());
            } else {
                Msg.msg("w", "Nie ma żadnej deklaracji, z której można by pobrać pole VAT do przeniesienia");
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
                Msg.msg("w", "Nie ma żadnej deklaracji, z której można by pobrać pole VAT do przeniesienia");
            }
        }
        return kwotazprzeniesienia;
    }

    
    private int zbadajpobranadeklarajce(Deklaracjevat badana) {
        int f = 0;
        try {
            if (badana != null) {
                String l = " "+badana.getRok()+"-"+badana.getMiesiac()+" wysłana dnia: "+data.Data.data_yyyyMMdd(badana.getDatazlozenia())+" ";
                if (badana.getStatus().startsWith("399")||badana.getStatus().startsWith("120")) {
                    Msg.msg("e", "Istnieje deklaracja za okres " + rok + "-" + mc + " która została już wysłana z plikiem jpk, ale nie pobrano jeszcze upo!");
                    pozycjeDeklaracjiVAT.setCelzlozenia("1");
                    nowadeklaracja.setNrkolejny(badana.getNrkolejny());
                    f = 1;
                } else if (badana.getStatus().startsWith("388")) {
                    Msg.msg("w", "Przygotowano do zachowania drugą wersję niezałączonej do jpk deklaracji za okres  " + rok + "-" + mc);
                    f = 2;
                } else if (badana.getStatus().startsWith("301") || badana.getStatus().startsWith("302") || badana.getStatus().isEmpty()) {
                    Msg.msg("e", "Wysłałeś już deklarację" + l + "ale nie pobrałeś UPO. Nie mozna sporządzić nowej deklaracji za miesiąc następny!");
                    f = 1;
                } else if (badana.getStatus().startsWith("4")) {
                    pozycjeDeklaracjiVAT.setCelzlozenia("1");
                    Msg.msg("w", "Utworzono nową deklarację" + l + ". Wysłanie poprzedniej zakończyło się błędem. Jeśli chcesz sporządzić korektę usuń najpierw błędną deklarację.");
                    nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                } else if (badana.getStatus().startsWith("200") && pierwotnazamiastkorekty == false) {
                    nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                    pozycjeDeklaracjiVAT.setCelzlozenia("2");
                    Msg.msg("w", "Przygotowano do zachowania korekte poprawnie wyslanej deklaracji za okres  " + rok + "-" + mc);
                } else if (badana.getStatus().startsWith("200") && pierwotnazamiastkorekty == true) {
                    nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                    pozycjeDeklaracjiVAT.setCelzlozenia("1");
                    Msg.msg("w", "Wysłano już deklarację za ten okres. Jednakże w opcjach ustawiono wymuszenie deklaracji pierwotnej");
                    Msg.msg("w", "Przygotowano do zachowania drugą wersję poprawnie wyslanej deklaracji za okres  " + rok + "-" + mc);
                } else {
                    f = 1;
                    Msg.msg("e", "Wystąpił dziwny błąd wołaj szefa");

                }
            } else {
               // Msg.msg("i", "Nie istnieje uprzednio wysłana deklaracja za ten okres rozliczeniowy. Tworzę nową","form:messages");
            }
        } catch (Exception e) {
            E.e(e);
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
        pozycje.setPodatnik(wpisView.getPodatnikObiekt().getPrintnazwa());
        pozycje.setRegon(wpisView.getPodatnikObiekt().getRegon());
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
        List<DeklaracjaVatPozycjeKoncowe> uzupelnionewiersze = Collections.synchronizedList(new ArrayList<>());
        if (schemapozycjekoncowe != null) {
            for (DeklaracjaVatSchemaPozKoncowe p : schemapozycjekoncowe) {
                uzupelnionewiersze.add(p.getDeklaracjaVatPozycjeKoncowe());
            }
        } else {
            schemapozycjekoncowe = Collections.synchronizedList(new ArrayList<>());
        }
        return uzupelnionewiersze;
    }
    
    private Deklaracjevat stworzdeklaracje(Vatpoz pozycje, String vatokres, DeklaracjaVatSchema schema, boolean vatzd, boolean splitpayment, List<EwidPoz> pozycjeSzczegoloweNowe) {
        if (schema.getNazwaschemy().equals("M-18") || schema.getNazwaschemy().equals("K-12")) {
            korektaM18K12(pozycje);
        }
        if (schema.getNazwaschemy().equals("M-19") || schema.getNazwaschemy().equals("K-13")) {
            korektaM18K12(pozycje);
        }
        if (schema.getNazwaschemy().equals("M-20") || schema.getNazwaschemy().equals("K-14")) {
            korektaM18K12(pozycje);
        }
        Deklaracjevat nowadekl = new Deklaracjevat();
        String wiersz = null;
        byte[] deklaracjapodpisana = null;
        try {
            String nrtelefonu = wpisView.getUzer().getNrtelefonu() == null ? "605586176" : wpisView.getUzer().getNrtelefonu();
            if (wpisView.getPodatnikObiekt().isPodpiscertyfikowany()) {
                VAT713 vat713 = new VAT713(pozycje, schema, true, vatzd, nrtelefonu, splitpayment);
                wiersz = vat713.getWiersz();
                nowadekl.setJestcertyfikat(true);
            } else {
                VAT713 vat713 = new VAT713(pozycje, schema, false, vatzd, nrtelefonu, splitpayment);
                //to jest wygenerowana deklaracjia w xml
                wiersz = vat713.getWiersz();
                nowadekl.setJestcertyfikat(false);
            }
            if (schema.getNazwaschemy().equals("M-18")) {
                deklaracje.vat18m.Deklaracja dekla = new Deklaracja();
            }
        } catch (Exception ex) {
            Msg.msg("e", "Błąd podczas generowania deklaracji VAT. Nalezy sprawdzić parametry podatnika.");
            // Logger.getLogger(Vat7DKView.class.getName()).log(Level.SEVERE, null, ex);
        }
        nowadekl.setDeklaracja(wiersz);
        nowadekl.setRok(rok);
        if (!vatokres.equals("miesięczne")) {
            nowadekl.setMiesiac(Kwartaly.getMapamcMcwkw().get(wpisView.getMiesiacWpisu()));
            nowadekl.setMiesiackwartal(true);
        } else {
            nowadekl.setMiesiac(mc);
            nowadekl.setMiesiackwartal(false);
        }
//        for (EwidPoz p : pozycjeSzczegoloweNowe) {
//            p.setDeklaracja(nowadeklaracja);
//        }
        for (EwidPoz p : pozycjeSzczegoloweNowe) {
            p.setDeklaracja(nowadekl);
        }
        nowadekl.setEwidpozlista(pozycjeSzczegoloweNowe);
        nowadekl.setDeklaracjapodpisana(deklaracjapodpisana);
        nowadekl.setNrkwartalu(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu()));
        nowadekl.setMiesiac(mc);
        nowadekl.setKodurzedu(pozycjeDeklaracjiVAT.getKodurzedu());
        nowadekl.setPodatnik(podatnik);
        nowadekl.setSelected(pozycjeDeklaracjiVAT);
        nowadekl.setIdentyfikator("");
        nowadekl.setUpo("");
        nowadekl.setStatus("");
        nowadekl.setOpis("");
        nowadekl.setWzorschemy(schema.getNazwaschemy());
        nowadekl.setSchemaobj(schema);
        nowadekl.setKwotaautoryzacja(pozycje.getKwotaautoryzacja());
        return nowadekl;
    }
    
  private void korektaM18K12(Vatpoz pozycje) {
      PozycjeSzczegoloweVAT poz  = pozycje.getPozycjeszczegolowe();
            if (poz.getPoleI57()==0) {
                poz.setPoleI57(0);
                poz.setPole57(null);
            }
            if (poz.getPoleI58()==0) {
                poz.setPoleI58(0);
                poz.setPole58(null);
            }
            if (poz.getPoleI59()==0) {
                poz.setPoleI59(0);
                poz.setPole59(null);
            }
            if (poz.getPoleI60()==0) {
                poz.setPoleI60(0);
                poz.setPole60(null);
            }
            if (poz.getPoleI61()==0) {
                poz.setPoleI61(0);
                poz.setPole61(null);
            }
  }
    
    private boolean czypokazurproszczona() {
        boolean zwrot = true;
        if (wpisView.isKsiegirachunkowe()) {
            zwrot = false;
        }
        return zwrot;
    }
    

    private boolean czypokazpelna() {
        boolean zwrot = false;
        if (wpisView.isKsiegirachunkowe()) {
//        if (wpisView.isKsiegirachunkowe() && wpisView.getPodatnikObiekt().isPodpiscertyfikowany() && sprawdzczymozna()) {
            zwrot = true;
        }
        return zwrot;
    }

     public boolean sprawdzczymozna() {
        boolean zwrot = false;
        try {
            if (wpisView.getPodatnikObiekt().isPodpiscertyfikowany()) {
                zwrot = ObslugaPodpisuBean.moznapodpisacError(wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
            }
        } catch (KeyStoreException ex) {
            Msg.msg("e", "Brak karty w czytniku");
        } catch (IOException ex) {
            Msg.msg("e", "UWAGA! Błędne hasło!");
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
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

    public boolean isPokazuproszczona() {
        return pokazuproszczona;
    }

    public void setPokazuproszczona(boolean pokazuproszczona) {
        this.pokazuproszczona = pokazuproszczona;
    }

    public boolean isPokazpelna() {
        return pokazpelna;
    }

    public void setPokazpelna(boolean pokazpelna) {
        this.pokazpelna = pokazpelna;
    }

    public boolean isFlagazt() {
        return flagazt;
    }

    public void setFlagazt(boolean flagazt) {
        this.flagazt = flagazt;
    }

    public EwidencjaVatView getEwidencjaVatView() {
        return ewidencjaVatView;
    }

    public void setEwidencjaVatView(EwidencjaVatView ewidencjaVatView) {
        this.ewidencjaVatView = ewidencjaVatView;
    }

    public WniosekVATZDEntity getWniosekVATZDEntity() {
        return wniosekVATZDEntity;
    }

    public void setWniosekVATZDEntity(WniosekVATZDEntity wniosekVATZDEntity) {
        this.wniosekVATZDEntity = wniosekVATZDEntity;
    }

    public boolean isWymusozmnaczeniejakokorekte() {
        return wymusozmnaczeniejakokorekte;
    }

    public void setWymusozmnaczeniejakokorekte(boolean wymusozmnaczeniejakokorekte) {
        this.wymusozmnaczeniejakokorekte = wymusozmnaczeniejakokorekte;
    }

    public boolean isNiesprawdzajpoprzednichdeklaracji() {
        return niesprawdzajpoprzednichdeklaracji;
    }

    public void setNiesprawdzajpoprzednichdeklaracji(boolean niesprawdzajpoprzednichdeklaracji) {
        this.niesprawdzajpoprzednichdeklaracji = niesprawdzajpoprzednichdeklaracji;
    }

    public int getKorektanaliczonyzmniejszajaca() {
        return korektanaliczonyzmniejszajaca;
    }

    public void setKorektanaliczonyzmniejszajaca(int korektanaliczonyzmniejszajaca) {
        this.korektanaliczonyzmniejszajaca = korektanaliczonyzmniejszajaca;
    }

    public int getKorektanaliczonyzwiekszajaca() {
        return korektanaliczonyzwiekszajaca;
    }

    public void setKorektanaliczonyzwiekszajaca(int korektanaliczonyzwiekszajaca) {
        this.korektanaliczonyzwiekszajaca = korektanaliczonyzwiekszajaca;
    }

    public boolean isNowejpk() {
        return nowejpk;
    }

    public void setNowejpk(boolean nowejpk) {
        this.nowejpk = nowejpk;
    }

    public boolean isPrzelewnarachunekvat() {
        return przelewnarachunekvat;
    }

    public void setPrzelewnarachunekvat(boolean przelewnarachunekvat) {
        this.przelewnarachunekvat = przelewnarachunekvat;
    }

    public boolean isZaliczenienapoczetzobowiazan() {
        return zaliczenienapoczetzobowiazan;
    }

    public void setZaliczenienapoczetzobowiazan(boolean zaliczenienapoczetzobowiazan) {
        this.zaliczenienapoczetzobowiazan = zaliczenienapoczetzobowiazan;
    }

    public Integer getZaliczenienapoczetzobowiazankwota() {
        return zaliczenienapoczetzobowiazankwota;
    }

    public void setZaliczenienapoczetzobowiazankwota(Integer zaliczenienapoczetzobowiazankwota) {
        this.zaliczenienapoczetzobowiazankwota = zaliczenienapoczetzobowiazankwota;
    }

    public boolean isPozwolnazapis() {
        return pozwolnazapis;
    }

    public void setPozwolnazapis(boolean pozwolnazapis) {
        this.pozwolnazapis = pozwolnazapis;
    }

    public Integer getWntsamochoddoodliczenia() {
        return wntsamochoddoodliczenia;
    }

    public void setWntsamochoddoodliczenia(Integer wntsamochoddoodliczenia) {
        this.wntsamochoddoodliczenia = wntsamochoddoodliczenia;
    }

    public boolean isPokazzwrotnarachunek() {
        return pokazzwrotnarachunek;
    }

    public void setPokazzwrotnarachunek(boolean pokazzwrotnarachunek) {
        this.pokazzwrotnarachunek = pokazzwrotnarachunek;
    }

   
    

    public String getRodzajzobowiazania() {
        return rodzajzobowiazania;
    }

    public void setRodzajzobowiazania(String rodzajzobowiazania) {
        if (rodzajzobowiazania!=null) {
            try {
                String pattern = "[^a-zA-Z0-9 ]";
                rodzajzobowiazania = rodzajzobowiazania.replaceAll(pattern, "");
            } catch (Exception e){}
        }
        this.rodzajzobowiazania = rodzajzobowiazania;
    }

    private boolean obliczpozwolnanazpis() {
        boolean zwrot = true;
        if (wpisView.isJpk2020K()) {
            if (!wpisView.getMiesiacWpisu().equals("03")&&!wpisView.getMiesiacWpisu().equals("06")&&!wpisView.getMiesiacWpisu().equals("09")&&!wpisView.getMiesiacWpisu().equals("12")) {
                zwrot = false;
            }
        }
        return zwrot;
    }

    

    

    
    

   

   
       

}
