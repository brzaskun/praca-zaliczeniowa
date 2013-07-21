/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.EwidencjeVatDAO;
import dao.PodatnikDAO;
import deklaracjaVAT7_13.VAT713;
import embeddable.Daneteleadresowe;
import embeddable.EVatwpisSuma;
import embeddable.Kwartaly;
import embeddable.Parametr;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.TKodUS;
import embeddable.Vatpoz;
import entity.Deklaracjevat;
import entity.Evewidencja;
import entity.Podatnik;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
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
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class Vat7DKView implements Serializable {

    @Inject
    private Deklaracjevat deklaracjakorygowana;
    @Inject
    private Deklaracjevat deklaracjawyslana;
    @Inject
    private Deklaracjevat nowadeklaracja;
    @ManagedProperty(value = "#{WpisView}")
    protected WpisView wpisView;
    @Inject
    private Vatpoz selected;
    static private PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT;
    @Inject
    private Daneteleadresowe adres;
    @Inject
    PodatnikDAO podatnikDAO;
    @Inject
    EwidencjeVatDAO ewidencjeVatDAO;
    @Inject
    private TKodUS tKodUS;
    @Inject
    protected DeklaracjevatDAO deklaracjevatDAO;
    private int flaga;
    private String rok;
    private String mc;
    private String podatnik;
    private boolean pole56zreki;
    private boolean pole59zreki;
    private boolean zachowaj;

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
        Podatnik pod = podatnikDAO.find(podatnik);
        String vatokres = sprawdzjakiokresvat();
         if(!vatokres.equals("miesięczne")){
                Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                mc = miesiacewkwartale.get(2);
            }
        HashMap<String, EVatwpisSuma> sumaewidencji = ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji();
        ArrayList<EVatwpisSuma> wyciagnieteewidencje = new ArrayList<>(sumaewidencji.values());
        //tu zduplikowac ewidencje
        ArrayList<EVatwpisSuma> ewidencjetmp = new ArrayList<>(sumaewidencji.values());
        for (EVatwpisSuma ew : ewidencjetmp) {
            if (ew.getEwidencja().getNazwa().equals("import usług") || ew.getEwidencja().getNazwa().equals("rejestr WNT")) {
                EVatwpisSuma suma = new EVatwpisSuma(ew.getEwidencja(), ew.getNetto(), ew.getVat(), ew.getEstawka());
                //pobieram i kopiuje stara ewidencje
                Evewidencja tmp = new Evewidencja(ew.getEwidencja().getNazwa(), ew.getEwidencja().getPole(), ew.getEwidencja().getNrpolanetto(), ew.getEwidencja().getNrpolavat(), ew.getEwidencja().getRodzajzakupu(), ew.getEwidencja().getTransakcja(), ew.getEwidencja().isTylkoNetto());
                //wpisuje pola zakupu
                tmp.setNrpolanetto("51");
                tmp.setNrpolavat("52");
                //zachowuje ewidecje do tymczasowej sumy
                suma.setEwidencja(tmp);
                //dodaje tymczasowa sume do calosci 
                wyciagnieteewidencje.add(suma);
            }
            if (ew.getEwidencja().getNazwa().equals("import usług")) {
                EVatwpisSuma suma = new EVatwpisSuma(ew.getEwidencja(), ew.getNetto(), ew.getVat(), ew.getEstawka());
                //pobieram i kopiuje stara ewidencje
                Evewidencja tmp = new Evewidencja(ew.getEwidencja().getNazwa(), ew.getEwidencja().getPole(), ew.getEwidencja().getNrpolanetto(), ew.getEwidencja().getNrpolavat(), ew.getEwidencja().getRodzajzakupu(), ew.getEwidencja().getTransakcja(), ew.getEwidencja().isTylkoNetto());
                //wpisuje pola zakupu
                tmp.setNrpolanetto("39");
                tmp.setNrpolavat("40");
                //zachowuje ewidecje do tymczasowej sumy
                suma.setEwidencja(tmp);
                //dodaje tymczasowa sume do calosci 
                wyciagnieteewidencje.add(suma);
            }
        }
        //sumuj ewidencje 51 i52 pola
        Evewidencja pojewid = new Evewidencja("sumaryczna", "Nabycie towarów i usług pozostałych", "51", "52", "opodatkowane", "zakup suma", false);
        EVatwpisSuma sumawew = new EVatwpisSuma(pojewid, BigDecimal.ZERO, BigDecimal.ZERO, "");
        for (Iterator<EVatwpisSuma> it = wyciagnieteewidencje.iterator(); it.hasNext();) {
            EVatwpisSuma ew = it.next();
            if (ew.getEwidencja().getNrpolanetto().equals("51")) {
                sumawew.setNetto(sumawew.getNetto().add(ew.getNetto()));
                sumawew.setVat(sumawew.getVat().add(ew.getVat()));
                it.remove();
            }

        }
        wyciagnieteewidencje.add(sumawew);

        //
        for (EVatwpisSuma ew : wyciagnieteewidencje) {
            System.out.println("Ewidencja :" + ew.toString());
            String nrpolanetto = ew.getEwidencja().getNrpolanetto();
            String nrpolavat = ew.getEwidencja().getNrpolavat();
            String netto = String.valueOf(ew.getNetto());
            int nettoI = Integer.parseInt(ew.getNetto().toString());
            String vat = String.valueOf(ew.getVat().toString());
            int vatI = Integer.parseInt(ew.getVat().toString());
            Class[] paramString = new Class[1];
            paramString[0] = String.class;
            Method met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole" + nrpolanetto, paramString);
            met.invoke(pozycjeSzczegoloweVAT, new String(netto));
            paramString = new Class[1];
            paramString[0] = Integer.class;
            try {
                met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPoleI" + nrpolanetto, paramString);
                met.invoke(pozycjeSzczegoloweVAT, new Integer(nettoI));
            } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            }
            if ((nrpolavat != null) && (!nrpolavat.equals(""))) {
                paramString = new Class[1];
                paramString[0] = String.class;
                met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole" + nrpolavat, paramString);
                met.invoke(pozycjeSzczegoloweVAT, new String(vat));
                paramString = new Class[1];
                paramString[0] = Integer.class;
                try {
                    met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPoleI" + nrpolavat, paramString);
                    met.invoke(pozycjeSzczegoloweVAT, new Integer(vatI));
                } catch (Exception e) {
                }
            }
        }
         String kwotaautoryzujaca = null;
        String kodus = tKodUS.getLista().get(pod.getUrzadskarbowy());
        try {
            boolean equals = kodus.equals("");
        } catch (Exception e) {
            Msg.msg("e", "Brak wpisanego urzędu skarbowego!", "form:msg");
            setFlaga(1);
        }
        try {
            List<Parametr> listakwotaautoryzujaca = pod.getKwotaautoryzujaca();
            if(listakwotaautoryzujaca.size()==0){
                throw new Exception();
            }
            for (Parametr par : listakwotaautoryzujaca) {
                if (par.getRokOd().equals(rok)) {
                    kwotaautoryzujaca = par.getParametr();
                    break;
                }
            }
        } catch (Exception e) {
            Msg.msg("e", "Wystapil blad, brak kwoty autoryzujacej w ustawieniach!", "form:msg");
            setFlaga(1);
        }
        czynieczekajuzcosdowyslania();
        if (flaga != 1) {
            try {
                bylajuzdeklaracjawtymmiesiacu();
                zbadajpobranadeklarajce();
                pobierz47zpoprzedniej();
            } catch (Exception e) {
                    pobierz47zustawien();
                    najpierwszadeklaracja();
            }
        }

        if (flaga != 1) {
            podsumujszczegolowe();
            selected.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
            selected.setPodatnik(podatnik);
            selected.setRok(rok);
            if(vatokres.equals("miesięczne")){
                selected.setRodzajdeklaracji("VAT-7");
            } else {
                selected.setRodzajdeklaracji("VAT-7K");
            }
            String mcx = String.valueOf(Integer.parseInt(mc));
            selected.setMiesiac(mcx);
            selected.setKodurzedu(tKodUS.getLista().get(pod.getUrzadskarbowy()));
            selected.setNazwaurzedu(pod.getUrzadskarbowy());
            adres.setNIP(pod.getNip());
            adres.setImiePierwsze(pod.getImie().toUpperCase());
            adres.setNazwisko(pod.getNazwisko().toUpperCase());
            adres.setDataUrodzenia(pod.getDataurodzenia());
            adres.setWojewodztwo(pod.getWojewodztwo().toUpperCase());
            adres.setPowiat(pod.getPowiat().toUpperCase());
            adres.setGmina(pod.getGmina().toUpperCase());
            adres.setUlica(pod.getUlica().toUpperCase());
            adres.setNrDomu(pod.getNrdomu());
            adres.setNrLokalu(pod.getNrlokalu());
            adres.setMiejscowosc(pod.getMiejscowosc().toUpperCase());
            adres.setKodPocztowy(pod.getKodpocztowy());
            adres.setPoczta(pod.getPoczta().toUpperCase());
            selected.setAdres(adres);
            selected.setKwotaautoryzacja(kwotaautoryzujaca);
            stworzdeklaracje();
            nowadeklaracja.setEwidencje(ewidencjeVatDAO.find(rok, mc, podatnik).getEwidencje());
            nowadeklaracja.setPodsumowanieewidencji(ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji());
            nowadeklaracja.setRok(rok);
            if(!vatokres.equals("miesięczne")){
                Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                nowadeklaracja.setMiesiac(miesiacewkwartale.get(2));
                nowadeklaracja.setMiesiackwartal(true);
                nowadeklaracja.setNrkwartalu(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
            } else {
                nowadeklaracja.setMiesiac(mc);
                nowadeklaracja.setMiesiackwartal(false);
                nowadeklaracja.setNrkwartalu(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
            }
            nowadeklaracja.setMiesiac(mc);
            nowadeklaracja.setKodurzedu(selected.getKodurzedu());
            nowadeklaracja.setPodatnik(podatnik);
            nowadeklaracja.setSelected(selected);
            nowadeklaracja.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
            nowadeklaracja.setIdentyfikator("");
            nowadeklaracja.setUpo("");
            nowadeklaracja.setStatus("");
            nowadeklaracja.setOpis("");
            if(nowadeklaracja.isMiesiackwartal()){
                 if(wpisView.getSumarokmiesiac()>2017){
                    nowadeklaracja.setWzorschemy("K-8");
                } else {
                    nowadeklaracja.setWzorschemy("K-7");
                }
            } else {
                if(wpisView.getSumarokmiesiac()>2017){
                    nowadeklaracja.setWzorschemy("M-14");
                } else {
                    nowadeklaracja.setWzorschemy("M-13");
                }
            }
        }
        //jezeli zachowaj bedzie true dopiero wrzuci deklaracje do kategorii do wyslania
        if(zachowaj==true){
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
        zachowaj=false;
        }
    }

    public void odswiezprzycisk(ValueChangeEvent e){
        RequestContext.getCurrentInstance().update("form:przycisk1");
    }
    
     private String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        Integer sumaszukana = rok+mc;
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        //odszukaj date w parametrze - kandydat na metode statyczna
        for(Parametr p : parametry){
            try{
            Integer dolnagranica = Integer.parseInt(p.getRokOd()) + Integer.parseInt(p.getMcOd());
            Integer gornagranica = Integer.parseInt(p.getRokDo()) + Integer.parseInt(p.getMcDo());
            if(sumaszukana>=dolnagranica&&sumaszukana<=gornagranica){
                return p.getParametr();
            }
            } catch (Exception e){
            Integer dolnagranica = Integer.parseInt(p.getRokOd()) + Integer.parseInt(p.getMcOd());
            if(sumaszukana>=dolnagranica){
                return p.getParametr();
            }
            }
        }
        return "blad";
    }
    
    private void najpierwszadeklaracja() {
        if(flaga!=1){
        try {
            //pobiera liste deklaracji poprzednich z danego miesiaca, jezeli nie ma wyrzuca blad
            List<Deklaracjevat> pobranalistadeklaracji = new ArrayList<>();
            pobranalistadeklaracji = deklaracjevatDAO.findDeklaracjewszystkie(rok.toString(), mc, podatnik);
            deklaracjakorygowana = pobranalistadeklaracji.get(pobranalistadeklaracji.size() - 1);

        } catch (Exception er) {}
        try  {
            //przechwytuje blad i jezeli sa dekalracje pozniejsze wyslane i bezbledne to kaze zajrzec do nich
            List<Deklaracjevat> pobranalistadeklaracji = new ArrayList<>();
            pobranalistadeklaracji = deklaracjevatDAO.findDeklaracjeWyslane(podatnik, rok);
            for(Deklaracjevat p : pobranalistadeklaracji){
                if(p.getStatus().equals("200")){
                    flaga = 1;
                    Msg.msg("e", "A po co tworzysz tę deklaracje, jak są już poźniejsze? Wywalam błąd a ty zajrzyj do wysłanych.", "form:msg");
                    break;
                }
            }
        } catch (Exception e){
            //klient swiezak nie ma zadnej deklaracji
            selected.setCelzlozenia("1");
            nowadeklaracja.setNrkolejny(1);
            Msg.msg("i", "Utworzenie samejpierwszej za dany okres " + rok + "-" + mc, "form:msg");
        }
        }
    }

    private void pobierz47zustawien() {
        try {
            Podatnik pod = podatnikDAO.find(podatnik);
            String Pole47 = pod.getPole47();
            Integer PoleI47 = Integer.parseInt(Pole47);
            pozycjeSzczegoloweVAT.setPole47(Pole47);
            pozycjeSzczegoloweVAT.setPoleI47(PoleI47);
            deklaracjawyslana.setIdentyfikator("lolo");
            deklaracjawyslana.setPodatnik("manolo");
        } catch (Exception e) {
            setFlaga(1);
            Msg.msg("e", "Nie wpisano w ustawieniach klienta wartosci pola 47!  ","form:msg");
        }
    }

    private void bylajuzdeklaracjawtymmiesiacu() {
        try {
            //pobiera liste deklaracji poprzednich z danego miesiaca
            List<Deklaracjevat> pobranalistadeklaracji = new ArrayList<>();
            pobranalistadeklaracji = deklaracjevatDAO.findDeklaracjewszystkie(rok.toString(), mc, podatnik);
            //eliminowanie testowych
            ListIterator it;
            it = pobranalistadeklaracji.listIterator();
            while(it.hasNext()){
                Deklaracjevat tmp = (Deklaracjevat) it.next();
                if(tmp.isTestowa()==true){
                        it.remove();
                }
            }
            deklaracjakorygowana = pobranalistadeklaracji.get(pobranalistadeklaracji.size() - 1);
            //nie bylo takiej wiec cofa sie o miesiac
        } catch (Exception e) {
            String mcX = "";
            String rokX = "";
            if (mc.equals("01")) {
                mcX = "12";
                rokX = String.valueOf(Integer.parseInt(rok) - 1);
            } else {
                Integer tmp = Integer.parseInt(mc);
                tmp--;
                mcX = tmp.toString();
                if (!mcX.equals("10") || !mcX.equals("11") || !mcX.equals("12")) {
                    mcX = "0".concat(mcX);
                }
                rokX = rok;
            }
            //dlatego jest inna (deklaracja wyslana) bo ona musi z poprzedniego miesiaca byc. sluzy tylko tutaj
            List<Deklaracjevat> pobranalistadeklaracji2 = new ArrayList<>();
            pobranalistadeklaracji2 = deklaracjevatDAO.findDeklaracjewszystkie(rokX, mcX, podatnik);
            deklaracjawyslana = pobranalistadeklaracji2.get(pobranalistadeklaracji2.size() - 1);
            deklaracjakorygowana = null;
        }
    }
    private void czynieczekajuzcosdowyslania(){
        try{
            Deklaracjevat badana = deklaracjevatDAO.findDeklaracjeDowyslania(podatnik);
            if(badana.getStatus().equals("")&&!badana.getMiesiac().equals(mc)){
                flaga = 1;
                Msg.msg("e", "Wcześniej sporządzona deklaracja nie jest wyslana. Przerywam sporządzanie tej deklaracji!", "form:msg");
            }
        } catch (Exception e){
            
        }
    }
    
    //generalnie sluzy do pobierania pola 47
    private void pobierz47zpoprzedniej() {
        if (flaga != 1) {
            try {
                pozycjeSzczegoloweVAT.setPole47(deklaracjakorygowana.getPozycjeszczegolowe().getPole47());
                pozycjeSzczegoloweVAT.setPoleI47(deklaracjakorygowana.getPozycjeszczegolowe().getPoleI47());
            } catch (Exception e) {
                pozycjeSzczegoloweVAT.setPole47(deklaracjawyslana.getPozycjeszczegolowe().getPole65());
                pozycjeSzczegoloweVAT.setPoleI47(deklaracjawyslana.getPozycjeszczegolowe().getPoleI65());
            }
        }
    }

    private void zbadajpobranadeklarajce() {
        Deklaracjevat badana;
        try {
            badana = deklaracjakorygowana;
            if (badana.getIdentyfikator().equals("")) {
                Msg.msg("e", "Wcześniej sporządzona deklaracja dot. bieżacego miesiaca nie jest wyslana. Edytuje deklaracje!", "form:msg");
                selected.setCelzlozenia("1");
                nowadeklaracja.setNrkolejny(badana.getNrkolejny());
                setFlaga(2);
            } else {
               if (badana.getStatus().equals("301") || badana.getStatus().equals("302") || badana.getStatus().equals("")) {
                    Msg.msg("e", "Wysłałeś już deklarację ale nie pobrałeś UPO. Nie mozna sporządzić nowej deklaracji za miesiąc następny!", "form:msg");
                    setFlaga(1);
                } else if (badana.getStatus().startsWith("4")) {
                    selected.setCelzlozenia("1");
                    Msg.msg("i", "Utworzono nową deklarację. Wysłanie poprzedniej zakończyło się błędem", "form:msg");
                    nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                } else if (badana.getStatus().equals("200")) {
                    nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                    selected.setCelzlozenia("2");
                    Msg.msg("i", "Utworzono korekte poprawnie wyslanej deklaracji za okres  " + rok + "-" + mc, "form:msg");
                    Msg.msg("i", "Prosze wypełnić treść załącznika ORD-ZU zawierającego wyjaśnienie przyczyny korekty", "form:msg");
                    
                } else {
                    setFlaga(1);
                    Msg.msg("i", "Wystąpił dziwny błąd wołaj szefa", "form:msg");

                }
            }
        } catch (Exception e) {
            badana = deklaracjawyslana;
            if (badana.getIdentyfikator().equals("")) {
                Msg.msg("e", "Wcześniej sporządzona deklaracja dot. poprzedniego miesiaca nie jest wyslana. Nie można utworzyć nowej!", "form:msg");
                setFlaga(1);
            } else {
               if (badana.getStatus().equals("301") || badana.getStatus().equals("302") || badana.getStatus().equals("")) {
                    Msg.msg("e", "Wysłałeś już deklarację ale nie pobrałeś UPO. Nie mozna sporządzić nowej deklaracji za miesiąc następny!", "form:msg");
                    setFlaga(1);
                } else if (badana.getStatus().startsWith("4")) {
                    Msg.msg("e", "Wysłanie deklaracji w poprzednim miesiącu zakończyło się błędem. Nie można utworzyć nowej deklaracji", "form:msg");
                    setFlaga(1);
                } else if (badana.getStatus().equals("200")) {
                    nowadeklaracja.setNrkolejny(badana.getNrkolejny() + 1);
                    selected.setCelzlozenia("1");
                    Msg.msg("i", "Potwierdzona udana wysyka w miesiącu poprzednim Tworzę nową dekalracje za " + rok + "-" + mc, "form:msg");
                } else {
                    setFlaga(1);
                    Msg.msg("i", "Wystąpił dziwny błąd wołaj szefa", "form:msg");

                }
            }
        }
    }

    private void podsumujszczegolowe() {
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        selected.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
        PozycjeSzczegoloweVAT p = pozycjeSzczegoloweVAT;//podsumowanie pol szsczegolowych z pobranych czastkowych
        p.setPoleI45(p.getPoleI20() + p.getPoleI21() + p.getPoleI23() + p.getPoleI25() + p.getPoleI27() + p.getPoleI29() + p.getPoleI31() + p.getPoleI33() + p.getPoleI35() + p.getPoleI37() + p.getPoleI41());
        p.setPole45(String.valueOf(p.getPoleI45()));
        p.setPoleI46(p.getPoleI26() + p.getPoleI28() + p.getPoleI30() + p.getPoleI34() + p.getPoleI36() + p.getPoleI38() + p.getPoleI42() + p.getPoleI43() + p.getPoleI44());
        p.setPole46(String.valueOf(p.getPoleI46()));
        p.setPoleI55(p.getPoleI47() + p.getPoleI48() + p.getPoleI50() + p.getPoleI52() + p.getPoleI53() + p.getPoleI54());
        p.setPole55(String.valueOf(p.getPoleI55()));
        Integer dozaplaty = p.getPoleI46() - p.getPoleI55();
        //to jets gupie bo kwota na kasy powinna byc jakos inaczej wstawiana to jest caly temat do zrobienia
        if(pole56zreki==true){
            if(dozaplaty>p.getPoleI56()){
                p.setPoleI56(Integer.parseInt(p.getPole56()));
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
        if(!p.getPole59().equals("")){
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
        if (p.getPole61() != "") {
            p.setPoleI61(Integer.parseInt(p.getPole61()));
            if (p.getPoleI61() > p.getPoleI60()) {
                p.setPoleI61(p.getPoleI60());
                p.setPole61(p.getPoleI61().toString());
            }
        } else {
            p.setPole61("0");
            p.setPoleI61(0);
        }
        if (p.getPole62() != "") {
            p.setPoleI62(Integer.parseInt(p.getPole62()));
            if (p.getPoleI62() > p.getPoleI61()) {
                p.setPoleI62(p.getPoleI61());
                p.setPole62(p.getPoleI62().toString());
            }
        }
        if (p.getPole63() != "") {
            p.setPoleI63(Integer.parseInt(p.getPole63()));
            if (p.getPoleI63() > p.getPoleI61()) {
                p.setPoleI63(p.getPoleI61());
                p.setPole63(p.getPoleI63().toString());
            }
        }
        if (p.getPole64() != "") {
            p.setPoleI64(Integer.parseInt(p.getPole64()));
            if (p.getPoleI64() > p.getPoleI61()) {
                p.setPoleI64(p.getPoleI61());
                p.setPole64(p.getPoleI64().toString());
            }
        }
        roznica = p.getPoleI60() - p.getPoleI61();
        p.setPoleI65(roznica);
        p.setPole65(roznica.toString());
        pozycjeSzczegoloweVAT = p;
    }

    private void stworzdeklaracje() {
        VAT713 vat713 = null;
        try {
            vat713 = new VAT713(selected, wpisView);
        } catch (Exception ex) {
            Logger.getLogger(Vat7DKView.class.getName()).log(Level.SEVERE, null, ex);
        }
        String wiersz = vat713.getWiersz();
        System.out.println(wiersz);
        nowadeklaracja.setDeklaracja(wiersz);
    }

    
   
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Vatpoz getSelected() {
        return selected;
    }

    public void setSelected(Vatpoz selected) {
        this.selected = selected;
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
}
