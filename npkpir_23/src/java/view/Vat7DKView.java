/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansVAT.VATDeklaracja;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjaVatSchemaWierszSumDAO;
import dao.DeklaracjaVatWierszSumarycznyDAO;
import dao.DeklaracjevatDAO;
import dao.EwidencjeVatDAO;
import dao.PodatnikDAO;
import dao.SchemaEwidencjaDAO;
import deklaracjaVAT7_13.VAT713;
import deklaracjeSchemy.SchemaVAT7;
import embeddable.EVatwpisSuma;
import embeddable.Kwartaly;
import embeddable.Mce;
import embeddable.Parametr;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.TKodUS;
import embeddable.Vatpoz;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.DeklaracjaVatWierszSumaryczny;
import entity.Deklaracjevat;
import entity.Podatnik;
import entity.SchemaEwidencja;
import error.E;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class Vat7DKView implements Serializable {

    private PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT;

    //</editor-fold>
    public static void main(String args[]) {
        List< String> list = new ArrayList< String>();
        list.add("A");
        list.add("B");
        list.add("C");

        for (Iterator< String> it = list.iterator(); it.hasNext();) {
            String str = it.next();
            if (str.equals("B")) {
                it.remove();
            }
        }
    }

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
    PodatnikDAO podatnikDAO;
    @Inject
    EwidencjeVatDAO ewidencjeVatDAO;
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
    private DeklaracjaVatWierszSumarycznyDAO deklaracjaVatWierszSumarycznyDAO;
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
        HashMap<String, EVatwpisSuma> sumaewidencji = ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji();
        ArrayList<EVatwpisSuma> wartosci = new ArrayList<>(sumaewidencji.values());
        //tu zduplikowac ewidencje
        //VATDeklaracja.duplikujZapisyDlaTransakcji(wartosci);
        //sumuj ewidencje 51 i52 pola
        VATDeklaracja.agregacjaEwidencjiZakupowych5152(wartosci);
        //
        pozycjeDeklaracjiVAT.setCelzlozenia("1");
        //tutaj przeklejamy z ewidencji vat do odpowiednich pol deklaracji
        VATDeklaracja.przyporzadkujPozycjeSzczegolowe(wartosci, pozycjeSzczegoloweVAT, null);
        String kwotaautoryzujaca = kwotaautoryzujcaPobierz();
        //czynieczekajuzcosdowyslania(mc); to chyba jest niepotrzebe w zbadajpoprzednie dekalracje jest uwzgledniony wybadek jak jest poprzednia
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
            uzupelnijPozycje(pozycjeDeklaracjiVAT, vatokres, kwotaautoryzujaca);
            nowadeklaracja = stworzdeklaracje(pozycjeDeklaracjiVAT, vatokres);
        }
        //jezeli zachowaj bedzie true dopiero wrzuci deklaracje do kategorii do wyslania
        if (zachowaj == true) {
            if (flaga == 2) {
                deklaracjevatDAO.destroy(deklaracjakorygowana);
                deklaracjevatDAO.edit(nowadeklaracja);
                deklaracjakorygowana = new Deklaracjevat();
                Msg.msg("i", podatnik + " - zachowano korekte niewysłanej deklaracji VAT za " + rok + "-" + mc, "form:msg");
            } else if (flaga == 1) {
                Msg.msg("e", podatnik + " Deklaracja nie zachowana", "form:msg");
            } else {
                deklaracjevatDAO.dodaj(nowadeklaracja);
                Msg.msg("i", podatnik + " - zachowano nową deklaracje VAT za " + rok + "-" + mc, "form:msg");
            }
            //pobieranie potwierdzenia
            RequestContext.getCurrentInstance().update("vat7:");
            zachowaj = false;
        }
    }
    
    public void obliczNowa() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        pozycjeSzczegoloweVAT = new PozycjeSzczegoloweVAT();
        String vatokres = sprawdzjakiokresvat();
        if (!vatokres.equals("miesięczne")) {
            mc = Kwartaly.getMapamcMcwkw().get(wpisView.getMiesiacWpisu());
        }
        HashMap<String, EVatwpisSuma> mapaewidencji = ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji();
        ArrayList<EVatwpisSuma> pobraneewidencje = new ArrayList<>(mapaewidencji.values());
        List<DeklaracjaVatWierszSumaryczny> wierszesumaryczne = wygenerujwierszesumaryczne(pobraneewidencje, deklaracjaVatWierszSumarycznyDAO);
        pozycjeDeklaracjiVAT.setCelzlozenia("1");
        //tutaj przeklejamy z ewidencji vat do odpowiednich pol deklaracji
        List<DeklaracjaVatSchema> pobranaschemaLista = deklaracjaVatSchemaDAO.findAll();
        DeklaracjaVatSchema pasujacaSchema = VATDeklaracja.odnajdzscheme(vatokres, rok, mc, pobranaschemaLista);
        List<SchemaEwidencja> schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(pasujacaSchema);
        VATDeklaracja.przyporzadkujPozycjeSzczegoloweNowe(schemaewidencjalista, pobraneewidencje, pozycjeSzczegoloweVAT, null);
        List<DeklaracjaVatSchemaWierszSum> schemawierszlista = deklaracjaVatSchemaWierszSumDAO.findWierszeSchemy(pasujacaSchema);
        VATDeklaracja.przyporzadkujPozycjeSzczegoloweSumaryczne(schemawierszlista, wierszesumaryczne, pozycjeSzczegoloweVAT, null);
        String kwotaautoryzujaca = kwotaautoryzujcaPobierz();
        System.out.println("Koniec");
        //czynieczekajuzcosdowyslania(mc); to chyba jest niepotrzebe w zbadajpoprzednie dekalracje jest uwzgledniony wybadek jak jest poprzednia
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
            uzupelnijPozycje(pozycjeDeklaracjiVAT, vatokres, kwotaautoryzujaca);
            nowadeklaracja = stworzdeklaracje(pozycjeDeklaracjiVAT, vatokres);
        }
        //jezeli zachowaj bedzie true dopiero wrzuci deklaracje do kategorii do wyslania
        if (zachowaj == true) {
            if (flaga == 2) {
                deklaracjevatDAO.destroy(deklaracjakorygowana);
                deklaracjevatDAO.edit(nowadeklaracja);
                deklaracjakorygowana = new Deklaracjevat();
                Msg.msg("i", podatnik + " - zachowano korekte niewysłanej deklaracji VAT za " + rok + "-" + mc, "form:msg");
            } else if (flaga == 1) {
                Msg.msg("e", podatnik + " Deklaracja nie zachowana", "form:msg");
            } else {
                deklaracjevatDAO.dodaj(nowadeklaracja);
                Msg.msg("i", podatnik + " - zachowano nową deklaracje VAT za " + rok + "-" + mc, "form:msg");
            }
            //pobieranie potwierdzenia
            RequestContext.getCurrentInstance().update("vat7:");
            zachowaj = false;
        }
    }
    
    private List<DeklaracjaVatWierszSumaryczny> wygenerujwierszesumaryczne(ArrayList<EVatwpisSuma> pobraneewidencje, DeklaracjaVatWierszSumarycznyDAO deklaracjaVatWierszSumarycznyDAO) {
        List<DeklaracjaVatWierszSumaryczny> lista = new ArrayList<>();
        lista.add(VATDeklaracja.podsumujewidencje(pobraneewidencje, deklaracjaVatWierszSumarycznyDAO,0));
        lista.add(VATDeklaracja.podsumujewidencje(pobraneewidencje, deklaracjaVatWierszSumarycznyDAO,1));
        lista.add(VATDeklaracja.podsumujewidencje(pobraneewidencje, deklaracjaVatWierszSumarycznyDAO,2));
        return lista;
    }
     private String kodUrzaduSkarbowegoPobierz() {
        String kodaUrzaduSkarbowego = null;
        try {
            kodaUrzaduSkarbowego = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
            boolean equals = kodaUrzaduSkarbowego.isEmpty();
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Brak wpisanego urzędu skarbowego!", "form:msg");
            flaga = 1;
        }
        return kodaUrzaduSkarbowego;
    }
    
    private String kwotaautoryzujcaPobierz() {
        String kwotaautoryzujaca = null;
        try {
            List<Parametr> listakwotaautoryzujaca = wpisView.getPodatnikObiekt().getKwotaautoryzujaca();
            if (listakwotaautoryzujaca.isEmpty()) {
                throw new Exception();
            }
            //bo wazny jet nie rok na deklaracji ale rok z ktorego sie wysyla
            DateTime datawysylki = new DateTime();
            String rokwysylki = String.valueOf(datawysylki.getYear());
            for (Parametr par : listakwotaautoryzujaca) {
                if (par.getRokOd().equals(rokwysylki)) {
                    kwotaautoryzujaca = par.getParametr();
                    break;
                }
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystapil blad, brak kwoty autoryzujacej w ustawieniach!", "form:msg");
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
                        Msg.msg("e", "A po co tworzysz tę deklaracje, jak są już poźniejsze? To błąd, zatrzymuje program, a ty zajrzyj do wysłanych.", "form:msg");
                        break;
                    }
                }
            } catch (Exception e) {
                E.e(e);
                //klient swiezak nie ma zadnej deklaracji
                pozycjeDeklaracjiVAT.setCelzlozenia("1");
                nowadeklaracja.setNrkolejny(1);
                Msg.msg("i", "Utworzenie samejpierwszej za dany okres " + rok + "-" + mc, "form:msg");
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
            Msg.msg("e", "Nie wpisano w ustawieniach klienta wartosci pola 47!  ", "form:msg");
        }
    }

    private Deklaracjevat bylajuzdeklaracjawtymmiesiacu(String rok, String mc) {
        Deklaracjevat pobrana = null;
        //pobiera liste deklaracji poprzednich z danego miesiaca
        List<Deklaracjevat> deklaracjezTegoSamegoMca = deklaracjevatDAO.findDeklaracjewszystkie(rok, mc, wpisView.getPodatnikWpisu());
        //eliminowanie testowych
        ListIterator<Deklaracjevat> it = deklaracjezTegoSamegoMca.listIterator();
        while (it.hasNext()) {
            Deklaracjevat tmp = it.next();
            if (tmp.isTestowa() == true) {
                it.remove();
                Msg.msg("i", "Dobrym zwyczajem jest usuwać deklaracje testowe przed sporządzeniem tej do wysłania.");
            }
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
                flaga = 1;
                Msg.msg("e", "Nie ma wcześniejszej deklaracji do pobrania.", "form:msg");
            } else if (badana.getStatus().isEmpty() && !badana.getMiesiac().equals(mc)) {
                flaga = 1;
                Msg.msg("e", "Wcześniej sporządzona deklaracja nie jest wyslana. Przerywam sporządzanie tej deklaracji!", "form:msg");
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

    private int zbadajpobranadeklarajce(Deklaracjevat badana) {
        int f = 0;
        try {
            if (badana != null) {
                if (badana.getIdentyfikator().isEmpty()) {
                    Msg.msg("e", "Wcześniej sporządzona deklaracja dot. bieżacego miesiaca nie jest wyslana. Edytuje deklaracje!", "form:msg");
                    pozycjeDeklaracjiVAT.setCelzlozenia("1");
                    nowadeklaracja.setNrkolejny(badana.getNrkolejny());
                    f = 2;
                } else {
                    if (badana.getStatus().equals("301") || badana.getStatus().equals("302") || badana.getStatus().isEmpty()) {
                        Msg.msg("e", "Wysłałeś już deklarację ale nie pobrałeś UPO. Nie mozna sporządzić nowej deklaracji za miesiąc następny!", "form:msg");
                        f = 1;
                    } else if (badana.getStatus().startsWith("4")) {
                        pozycjeDeklaracjiVAT.setCelzlozenia("1");
                        Msg.msg("i", "Utworzono nową deklarację. Wysłanie poprzedniej zakończyło się błędem", "form:msg");
                        nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                    } else if (badana.getStatus().equals("200") && pierwotnazamiastkorekty == false) {
                        nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                        pozycjeDeklaracjiVAT.setCelzlozenia("2");
                        Msg.msg("i", "Przygotowano do zachowania korekte poprawnie wyslanej deklaracji za okres  " + rok + "-" + mc, "form:msg");
                        Msg.msg("i", "Prosze wypełnić treść załącznika ORD-ZU zawierającego wyjaśnienie przyczyny korekty", "form:msg");
                    } else if (badana.getStatus().equals("200") && pierwotnazamiastkorekty == true) {
                        nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                        pozycjeDeklaracjiVAT.setCelzlozenia("1");
                        Msg.msg("i", "Wysłano już deklarację za ten okres. Jednakże w opcjach ustawiono wymuszenie deklaracji pierwotnej", "form:msg");
                        Msg.msg("i", "Przygotowano do zachowania drugą wersję poprawnie wyslanej deklaracji za okres  " + rok + "-" + mc, "form:msg");
                    } else {
                        f = 1;
                        Msg.msg("i", "Wystąpił dziwny błąd wołaj szefa", "form:msg");

                    }
                }
            } else {
                Msg.msg("i", "Nie istnieje uprzednio wysłana dekalracja za ten okres rozliczeniowy. Tworzę nową", "form:msg");
            }
        } catch (Exception e) {
            E.e(e);
//            if (badana.getIdentyfikator().isEmpty()) {
//                Msg.msg("e", "Wcześniej sporządzona deklaracja dot. poprzedniego miesiaca nie jest wyslana. Nie można utworzyć nowej!", "form:msg");
//                flaga = 1;
//            } else {
//                if (badana.getStatus().equals("301") || badana.getStatus().equals("302") || badana.getStatus().isEmpty()) {
//                    Msg.msg("e", "Wysłałeś już deklarację ale nie pobrałeś UPO. Nie mozna sporządzić nowej deklaracji za miesiąc następny!", "form:msg");
//                    flaga = 1;
//                } else if (badana.getStatus().startsWith("4")) {
//                    Msg.msg("e", "Wysłanie deklaracji w poprzednim miesiącu zakończyło się błędem. Nie można utworzyć nowej deklaracji", "form:msg");
//                    flaga = 1;
//                } else if (badana.getStatus().equals("200")) {
//                    nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
//                    pozycjeDeklaracjiVAT.setCelzlozenia("1");
//                    Msg.msg("i", "Potwierdzona udana wysyka w miesiącu poprzednim Tworzę nową dekalracje za " + rok + "-" + mc, "form:msg");
//                } else {
//                    flaga = 1;
//                    Msg.msg("i", "Wystąpił dziwny błąd wołaj szefa", "form:msg");
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

    private Vatpoz uzupelnijPozycje(Vatpoz pozycje, String vatokres, String kwotaautoryzujaca) {
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
    
    private Deklaracjevat stworzdeklaracje(Vatpoz pozycje, String vatokres) {
        Deklaracjevat nowadekl = new Deklaracjevat();
        VAT713 vat713 = null;
        try {
            vat713 = new VAT713(pozycje, wpisView);
        } catch (Exception ex) {
            Msg.msg("e", "Błąd podczas generowania deklaracji VAT. Nalezy sprawdzić parametry podatnika.");
            Logger.getLogger(Vat7DKView.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        nowadekl.setWzorschemy(SchemaVAT7.odnajdzscheme(vatokres, rok, mc).getNazwaschemy());
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

    }
