/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.RozrachunkiDAO;
import dao.WierszeDAO;
import daoFK.DokDAOfk;
import daoFK.KontoZapisyFKDAO;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Kontozapisy;
import entityfk.Rozrachunki;
import entityfk.RozrachunkiPK;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable{
    
    protected Dokfk selected;
    private int liczbawierszy;
    private List<Wiersze> wiersze;
    @Inject private DokDAOfk dokDAOfk;
    @Inject private WierszeDAO wierszeDAO;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private Kontozapisy kontozapisy;
    @Inject private Wiersze wiersz;
    @Inject private Wiersze aktualnywierszdorozrachunkow;
    private static List<Dokfk> selecteddokfk;
    private List<Dokfk> wykaz;
    private boolean zapisz0edytuj1;
    private int numerwierszazapisu;
    private List<RozrachunkiTmp> rozrachunkiwierszewdokumencie;
    @Inject private RozrachunkiDAO rozrachunkiDAO;

    public DokfkView() {
        liczbawierszy = 1;
        selected = new Dokfk();
        DokfkPK dokfkPK = new DokfkPK();
        dokfkPK.setRok("2013");
        selected.setDokfkPK(dokfkPK);
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1,0));
        selected.setKonta(wiersze);
//        List<Kontozapisy> zapisynakoncie = new ArrayList<>();
//        selected.setZapisynakoncie(zapisynakoncie);
        wykaz = new ArrayList<>();
        selecteddokfk = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        wykaz = dokDAOfk.findAll();
    }
    
    public void liczbaw() {
       liczbawierszy = selected.getKonta().size();
        double pierwsze = selected.getKonta().get(liczbawierszy-1).getKwotaWn();
        double drugie = selected.getKonta().get(liczbawierszy-1).getKwotaMa();
        if(pierwsze!=0||drugie!=0){
            liczbawierszy++;
            selected.getKonta().add(new Wiersze(liczbawierszy,0));
        }
    }
    
//   /**
//    * Usuwanie wierszy z dokumenu ksiegowego
//    */
    public void liczbawu() {
        if(liczbawierszy>1){
            liczbawierszy--;
            selected.getKonta().remove(liczbawierszy);
        }
    }
   
    public void edycja(){
        try {
            uzupelnijwierszeodane();
            //nanieszapisynakontach();
            dokDAOfk.edit(selected);
            wykaz.clear();
            wykaz = dokDAOfk.findAll();
            rozlicznaniesionerozrachunki();
            Msg.msg("i", "Dokument zmeniony");
            selected = new Dokfk();
            DokfkPK dokfkPK = new DokfkPK();
            dokfkPK.setRok("2013");
            selected.setDokfkPK(dokfkPK);
            wiersze = new ArrayList<>();
            wiersze.add(new Wiersze(1,0));
            selected.setKonta(wiersze);
            setZapisz0edytuj1(false);
         } catch (Exception e){
            System.out.println(e.toString());
            Msg.msg("e", "Nie udało się zmenic dokumentu "+e.toString());
        }
    }
    
    public void przywrocwpisbutton() {
        setZapisz0edytuj1(false);
        
    }
    
    public void dodaj(){
        try {
            uzupelnijwierszeodane();
            //nanosimy zapisy na kontach i dodajemy jako pozycję dokumentu fk
            //nanieszapisynakontach();
            //najpierw zobacz czy go nie ma, jak jest to usun i dodaj
            Dokfk poszukiwanydokument = dokDAOfk.findDokfk(selected.getDatawystawienia(), selected.getNumer());
            if (poszukiwanydokument instanceof Dokfk){
                dokDAOfk.destroy(poszukiwanydokument);
                dokDAOfk.dodaj(selected);
            } else {
                dokDAOfk.dodaj(selected);
            }
            wykaz.add(selected);
            rozlicznaniesionerozrachunki();
            selected = new Dokfk();
            DokfkPK dokfkPK = new DokfkPK();
            dokfkPK.setRok("2013");
            selected.setDokfkPK(dokfkPK);
            wiersze = new ArrayList<>();
            wiersze.add(new Wiersze(1,0));
            selected.setKonta(wiersze);
            liczbawierszy = 1;
            RequestContext.getCurrentInstance().update("formwpisdokument");
            Msg.msg("i", "Dokument dodany");
        } catch (Exception e){
            Msg.msg("e", "Nie udało się dodac dokumentu "+e.toString());
        }
    }
    
    public void zresetujpoladialogu () {
        selected = new Dokfk();
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1,0));
        selected.setKonta(wiersze);
    }
    
    private void uzupelnijwierszeodane() {
        //ladnie uzupelnia informacje o wierszu pk
        String opisdoprzekazania="";
        List<Wiersze> wierszewdokumencie = selected.getKonta();
        try {
        for(Wiersze p : wierszewdokumencie){
                String opis = p.getOpis();
                if(opis.contains("kontown")){
                    p.setKonto(p.getKontoWn());
                    p.setKontonumer(p.getKontoWn().getPelnynumer());
                    p.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setKwotaMa(0.0);
                    p.setTypwiersza(1);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else if (opis.contains("kontoma")){
                    p.setKonto(p.getKontoMa());
                    p.setKontonumer(p.getKontoMa().getPelnynumer());
                    //p.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setKwotaWn(0.0);
                    p.setTypwiersza(2);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else {
                    p.setKonto(p.getKontoWn().getNazwaskrocona().startsWith("2") ? p.getKontoWn() : p. getKontoMa());
                    p.setKontonumer(p.getKontoWn().getNazwaskrocona().startsWith("2") ? p.getKontoWn().getPelnynumer() : p. getKontoMa().getPelnynumer());
                    p.setKontoprzeciwstawne(p.getKontoWn().getNazwaskrocona().startsWith("2") ? p.getKontoMa().getPelnynumer() : p. getKontoWn().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setTypwiersza(0);
                    p.setDokfk(selected);
                    opisdoprzekazania=p.getOpis();
                    p.setZaksiegowane(Boolean.FALSE);
                }
                  }
        } catch (Exception e) {
            Msg.msg("e", "Błąd w pliku DokfkView w funkcji uzupelnijwierszeodane");
        }
    }

    public void usundokument(Dokfk dousuniecia) {
        try {
            dokDAOfk.usun(dousuniecia);
            wykaz.remove(dousuniecia);
            Msg.msg("i", "Dokument usunięty");
        } catch (Exception e){
            Msg.msg("e", "Nie udało się usunąć dokumentu");
        }
    }
    public void rozrachunki () {
         String wnlubma = (String) Params.params("wpisywaniefooter:wnlubma");
         uzupelnijwierszeodanewtrakcie();
            Dokfk sprawdz = selected;
            String nrwierszaS = (String) Params.params("wpisywaniefooter:wierszid");
            Integer nrwiersza = Integer.parseInt(nrwierszaS);
            nrwiersza--;
            aktualnywierszdorozrachunkow = selected.getKonta().get(nrwiersza);
            uzupelnijaktualnywiersz(wnlubma);
            pobierzwierszezbiezacegodokumentu();
          if (aktualnywierszdorozrachunkow.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
            RequestContext.getCurrentInstance().update("rozrachunki");
            RequestContext.getCurrentInstance().execute("drugionShow();");
         } else {
           Msg.msg("e", "Wybierz konto rozrachunkowe");
         }
     }
    
     private void uzupelnijwierszeodanewtrakcie() {
        //ladnie uzupelnia informacje o wierszu pk
        List<Wiersze> wierszewdokumencie = selected.getKonta();
        try {
        for(Wiersze p : wierszewdokumencie){
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
        }
        } catch (Exception e) {
            Msg.msg("e", "Błąd w pliku DokfkView w funkcji uzupelnijwierszeodane");
        }
    }
     
     private void uzupelnijaktualnywiersz(String wnlubma) {
        //ladnie uzupelnia informacje o wierszu pk
         Wiersze p = aktualnywierszdorozrachunkow;
         try {
                if(wnlubma.equals("Wn")){
                    p.setKonto(p.getKontoWn());
                    p.setKontonumer(p.getKontoWn().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setKwotapierwotna(p.getKwotaWn());
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                    p.setWnlubma("Wn");
                } else {
                    p.setKonto(p.getKontoMa());
                    p.setKontonumer(p.getKontoMa().getPelnynumer());
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setKwotapierwotna(p.getKwotaMa());
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                    p.setWnlubma("Ma");
                }
        } catch (Exception e) {
            Msg.msg("e", "Błąd w pliku DokfkView w funkcji uzupelnijwierszeodane");
        }
    }
     
     private void pobierzwierszezbiezacegodokumentu() {
        rozrachunkiwierszewdokumencie = new ArrayList<>();
        List<Wiersze> wierszezdokumentu = selected.getKonta();
        try {
        for (Wiersze p : wierszezdokumentu) {
            //pobieram tylko te konta ktore nie leza po tej samej stronie dokumetu co konto rozrachunkowe i sa takie same jak konto rozrliczane
            if (aktualnywierszdorozrachunkow.getWnlubma().equals("Wn")) {
                if (p.getKontoMa().equals(aktualnywierszdorozrachunkow.getKontoWn())) {
                    rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(p,aktualnywierszdorozrachunkow));
                    p.setKonto(p.getKontoMa());
                    p.setKontonumer(p.getKonto().getNazwapelna());
                    p.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
                    p.setKwotapierwotna(p.getKwotaMa());
                }
            } else {
                if (p.getKontoWn().equals(aktualnywierszdorozrachunkow.getKontoMa())) {
                    rozrachunkiwierszewdokumencie.add(new RozrachunkiTmp(p,aktualnywierszdorozrachunkow));
                    p.setKonto(p.getKontoWn());
                    p.setKontonumer(p.getKonto().getNazwapelna());
                    p.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
                    p.setKwotapierwotna(p.getKwotaWn());
                }
            }
        }
        } catch (Exception e) {}
    }

    public static class RozrachunkiTmp {

        private double kwotarozrachunku;
        private Wiersze wierszsparowany;
        private Wiersze wierszrozliczany;

        public RozrachunkiTmp() {
        }

        private RozrachunkiTmp(Wiersze wierszsparowany, Wiersze wierszrozliczany) {
            this.wierszsparowany = wierszsparowany;
            this.wierszrozliczany = wierszrozliczany;
        }

        public double getKwotarozrachunku() {
            return kwotarozrachunku;
        }

        public void setKwotarozrachunku(double kwotarozrachunku) {
            this.kwotarozrachunku = kwotarozrachunku;
        }

        public Wiersze getWierszsparowany() {
            return wierszsparowany;
        }

        public void setWierszsparowany(Wiersze wierszsparowany) {
            this.wierszsparowany = wierszsparowany;
        }

        public Wiersze getWierszrozliczany() {
            return wierszrozliczany;
        }

        public void setWierszrozliczany(Wiersze wierszrozliczany) {
            this.wierszrozliczany = wierszrozliczany;
        }

      
    }
    
   public void rozlicznaniesionerozrachunki() {
       try {
       for (RozrachunkiTmp p : rozrachunkiwierszewdokumencie) {
           RozrachunkiPK rPK = new RozrachunkiPK();
           rPK.setZapisrozliczany(p.getWierszrozliczany().getIdwiersza());
           rPK.setZapissparowany(p.getWierszsparowany().getIdwiersza());
           Rozrachunki r = new Rozrachunki(rPK);
           r.setKwotarozrachunku(p.getKwotarozrachunku());
           r.setWierszrozliczany(p.getWierszrozliczany());
           r.setWierszsparowany(p.getWierszsparowany());
           for (Wiersze s : selected.getKonta()) {
               if (s.getIdwiersza().equals(p.getWierszrozliczany().getIdwiersza())){
                   s.getRozrachunkijakorozliczany().add(r);
               }
           }
           for (Wiersze s : selected.getKonta()) {
               if (s.getIdwiersza().equals(p.getWierszsparowany().getIdwiersza())){
                   s.getRozrachunkijakosparowany().add(r);
               }
           }
           dokDAOfk.edit(selected);
           Msg.msg("i", "Rozrachunki naniesione");
       }
       } catch (Exception ex) {
           Msg.msg("w", "Nie naniesiono rozrachunkow");
       }
   }
   
   public void zapisanorozrachunek(){
       Msg.msg("i", "Zapisano rozrachunki");
   }

//     public void nanieszapisynakontach(){
//         if (!selected.getZapisynakoncie().isEmpty()){
//            usunistniejacezapisy(selected.getZapisynakoncie());
//         }
//         List<Kontozapisy> zapisynakontach = new ArrayList<>();
//         String opis = "";
//         Dokfk x = selected;
//         List<Wiersze> wierszewdokumencie = x.getKonta();
//         for(Wiersze p : wierszewdokumencie){
//         if(p.getTypwiersza()==1){
//             dodajwn(p, x, opis, zapisynakontach);
//         } else if(p.getTypwiersza()==2) {
//             dodajma(p, x, opis, zapisynakontach);
//         } else {
//             opis = p.getOpis();
//             dodajwn(p, x, opis, zapisynakontach);
//             dodajma(p, x, opis, zapisynakontach);
//         }
//         }
//         x.setNaniesionezapisy(true);
//         x.setZapisynakoncie(zapisynakontach);
//         RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
//         Msg.msg("i", "Zapisy na kontacg wygenerowane "+x.getNumer());
//         
//     }
     
//     private void usunistniejacezapisy(List<Kontozapisy> zachowanezapisy){
//         try {
//         for(Kontozapisy p : zachowanezapisy){
//             kontoZapisyFKDAO.destroy(p);
//         }
//         } catch (Exception ex) {
//             Msg.msg("e", "Błąd przy usuwaniu istniejących zapisó na kontach");
//         }
//     }
     
//     private void dodajwn(Wiersze p,Dokfk x, String opis, List<Kontozapisy> zapisynakontach){
//             Kontozapisy kontozapisy = new Kontozapisy();
//             kontozapisy.setKonto(p.getKontoWn().getPelnynumer());
//             kontozapisy.setKontoob(p.getKontoWn());
//             kontozapisy.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
//             kontozapisy.setNumer(x.getNumer());
//             kontozapisy.setOpis(opis);
//             kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
//             kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
//             kontozapisy.setKwotawn(p.getKwotaWn());
//             kontozapisy.setKwotama(0);
//             kontozapisy.setDokfk(x);
//             kontozapisy.setWartoscpierwotna(p.getKwotaWn());
//             kontozapisy.setDorozliczenia(0);
//             zapisynakontach.add(kontozapisy);             
//     }
//     
//     private void dodajma(Wiersze p,Dokfk x, String opis,  List<Kontozapisy> zapisynakontach){
//             Kontozapisy kontozapisy = new Kontozapisy();
//             kontozapisy.setKonto(p.getKontoMa().getPelnynumer());
//             kontozapisy.setKontoob(p.getKontoMa());
//             kontozapisy.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
//             kontozapisy.setNumer(x.getNumer());
//             kontozapisy.setOpis(opis);
//             kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
//             kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
//             kontozapisy.setKwotawn(0);
//             kontozapisy.setKwotama(p.getKwotaMa());
//             kontozapisy.setDokfk(x);
//             kontozapisy.setWartoscpierwotna(p.getKwotaMa());
//             kontozapisy.setDorozliczenia(0);
//             zapisynakontach.add(kontozapisy);
//     }
     
     
     public void znajdzdokumentzzapisu(){
        selected = wiersz.getDokfk();
        String szukanafrazazzapisu = wiersz.getOpis();
        liczbawierszy = selected.getKonta().size();
        List<Wiersze> zawartoscselected = new ArrayList<>();
        zawartoscselected = selected.getKonta();
        for (Wiersze p : zawartoscselected) {
            if (szukanafrazazzapisu.equals(p.getOpis())) {
                numerwierszazapisu = p.getIdporzadkowy();
                numerwierszazapisu--;
            }
        }
        setZapisz0edytuj1(true);
        String makrozaznaczajacepole = "#formwpisdokument\\:dataList\\:"+String.valueOf(numerwierszazapisu)+"\\:opis";
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
        //RequestContext.getCurrentInstance().execute("$(#formwpisdokument\\\\:dataList\\\\:5\\\\:opis).select()");
     }
     
     public void wybranodokmessage(){
         Msg.msg("i", "Wybrano dokument do edycji");
         setZapisz0edytuj1(true);
         liczbawierszy = selected.getKonta().size();
         //nie wiem dlaczego to dziala po dodaniu new Wiersze (1,0) - chodzilo o numery rzedu, zaczela dzialac edycja. Wczesniej szwankowal javascript. 
         //Bez tego jednak dostawalem pusty rzad po wpisaniu tej komenty nagle nie dostaje pustego rzedu tylko dzial kopiowanie do selected
         //totalny odlot. poszedl na to jeden wieczor
         //wierszedowsadzenia.add(new Wiersze(1,0));
         //selected.setKonta(wierszedowsadzenia);
     }
     
    
     
     
     
//     public void rozrachunki_stare() {
//         Dokfk sprawdz = selected;
//         String nrwiersza = (String) Params.params("wpisywaniefooter:wierszid");
//         aktualnywierszdorozrachunkow.setIdwiersza(Integer.parseInt(nrwiersza));
//         //uzupelnijwierszeodane();
//         String wnlubma= (String) Params.params("wpisywaniefooter:wnlubma");
//         if (wnlubma.equals("kontoWn")) {
//             aktualnywierszdorozrachunkow.setKwotaWn(Double.parseDouble((String) Params.params("wpisywaniefooter:kwota")));
//             aktualnywierszdorozrachunkow.setKwotaMa(0.0);
//             aktualnywierszdorozrachunkow.setKontonumer((String) Params.params("wpisywaniefooter:kontonr"));
//         } else {
//             aktualnywierszdorozrachunkow.setKwotaMa(Double.parseDouble((String) Params.params("wpisywaniefooter:kwota")));
//             aktualnywierszdorozrachunkow.setKwotaWn(0.0);
//             aktualnywierszdorozrachunkow.setKontonumer((String) Params.params("wpisywaniefooter:kontonr"));
//             
//         }
//         aktualnywierszdorozrachunkow.setDataksiegowania((String) Params.params("wpisywaniefooter:datka"));
//         aktualnywierszdorozrachunkow.setOpis((String) Params.params("wpisywaniefooter:opis"));
//         Dokfk newdokfk = new Dokfk();
//         DokfkPK newdokfkPK = new DokfkPK();
//         newdokfk.setDatawystawienia((String) Params.params("wpisywaniefooter:datka"));
//         newdokfkPK.setSeriadokfk((String) Params.params("wpisywaniefooter:symbol"));
//         String footernumer = (String) Params.params("wpisywaniefooter:numer");
//         newdokfkPK.setNrkolejny(Integer.parseInt(footernumer));
//         newdokfkPK.setPodatnik((String) Params.params("wpisywaniefooter:podatnik"));
//         newdokfkPK.setRok("2013");
//         newdokfk.setDokfkPK(newdokfkPK);
//         aktualnywierszdorozrachunkow.setDokfk(newdokfk);
//         RequestContext.getCurrentInstance().update("rozrachunki");
//         RequestContext.getCurrentInstance().execute("drugionShow();");
//     }
    //<editor-fold defaultstate="collapsed" desc="comment">
    public int getLiczbawierszy() {
        return liczbawierszy;
    }
    
    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    public List<Wiersze> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<Wiersze> wiersze) {
        this.wiersze = wiersze;
    }

    public int getNumerwierszazapisu() {
        return numerwierszazapisu;
    }

    public void setNumerwierszazapisu(int numerwierszazapisu) {
        this.numerwierszazapisu = numerwierszazapisu;
    }

    public Dokfk getSelected() {
        return selected;
    }
    
    public void setSelected(Dokfk selected) {
        this.selected = selected;
    }
    
    public List<Dokfk> getWykaz() {
        return wykaz;
    }
    
    public void setWykaz(List<Dokfk> wykaz) {
        this.wykaz = wykaz;
    }
    
    public List<Dokfk> getSelecteddokfk() {
        return selecteddokfk;
    }
    
    public void setSelecteddokfk(List<Dokfk> selecteddokfk) {
        DokfkView.selecteddokfk = selecteddokfk;
    }

    public Wiersze getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersze wiersz) {
        this.wiersz = wiersz;
    }

    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }

    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }

    public Wiersze getAktualnywierszdorozrachunkow() {
        return aktualnywierszdorozrachunkow;
    }

    public void setAktualnywierszdorozrachunkow(Wiersze aktualnywierszdorozrachunkow) {
        this.aktualnywierszdorozrachunkow = aktualnywierszdorozrachunkow;
    }

    public List<RozrachunkiTmp> getRozrachunkiwierszewdokumencie() {
        return rozrachunkiwierszewdokumencie;
    }

    public void setRozrachunkiwierszewdokumencie(List<RozrachunkiTmp> rozrachunkiwierszewdokumencie) {
        this.rozrachunkiwierszewdokumencie = rozrachunkiwierszewdokumencie;
    }

   
    
    
    public Kontozapisy getKontozapisy() {
        return kontozapisy;
    }

    public void setKontozapisy(Kontozapisy kontozapisy) {
        this.kontozapisy = kontozapisy;
    }

   //</editor-fold>

    
    
}
