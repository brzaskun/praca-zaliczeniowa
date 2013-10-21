/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import daoFK.KontoZapisyFKDAO;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Kontozapisy;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable{
    
    private Dokfk selected;
    private int liczbawierszy;
    private List<Wiersze> wiersze;
    @Inject private DokDAOfk dokDAOfk;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private Kontozapisy kontozapisy;
    @Inject private Wiersze wiersz;
    private static List<Dokfk> selecteddokfk;
    private List<Dokfk> wykaz;
    private boolean zapisz0edytuj1;

    public DokfkView() {
        liczbawierszy = 1;
        selected = new Dokfk();
        DokfkPK dokfkPK = new DokfkPK();
        dokfkPK.setRok("2013");
        selected.setDokfkPK(dokfkPK);
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1,0));
        selected.setKonta(wiersze);
        List<Kontozapisy> zapisynakoncie = new ArrayList<>();
        selected.setZapisynakoncie(zapisynakoncie);
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
            nanieszapisynakontach();
            dokDAOfk.edit(selected);
            wykaz.clear();
            wykaz = dokDAOfk.findAll();
            Msg.msg("i", "Dokument zmeniony");
            selected = new Dokfk();
            wiersze = new ArrayList<>();
            wiersze.add(new Wiersze(1,0));
            selected.setKonta(wiersze);
         } catch (Exception e){
            System.out.println(e.toString());
            Msg.msg("e", "Nie udało się zmenic dokumentu "+e.toString());
        }
    }
    
    public void dodaj(){
        try {
            uzupelnijwierszeodane();
            //nanosimy zapisy na kontach i dodajemy jako pozycję dokumentu fk
            nanieszapisynakontach();
            //najpierw zobacz czy go nie ma, jak jest to usun i dodaj
            Dokfk poszukiwanydokument = dokDAOfk.findDokfk(selected.getDatawystawienia(), selected.getNumer());
            if (poszukiwanydokument instanceof Dokfk){
                dokDAOfk.destroy(poszukiwanydokument);
                dokDAOfk.dodaj(selected);
            } else {
                dokDAOfk.dodaj(selected);
            }
            wykaz.add(selected);
            selected = new Dokfk();
            wiersze = new ArrayList<>();
            wiersze.add(new Wiersze(1,0));
            selected.setKonta(wiersze);
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
        for(Wiersze p : wierszewdokumencie){
                String opis = p.getOpis();
                if(opis.contains("kontown")){
                    p.setKonto(p.getKontoWn());
                    p.setKontonumer(p.getKontoWn().getPelnynumer());
                    p.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
                    p.setPodatniknazwa("Tymczasowy");
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setKwotaMa(0.0);
                    p.setTypwiersza(1);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else if (opis.contains("kontoma")){
                    p.setKonto(p.getKontoMa());
                    p.setKontonumer(p.getKontoMa().getPelnynumer());
                    p.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
                    p.setPodatniknazwa("Tymczasowy");
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setKwotaWn(0.0);
                    p.setTypwiersza(2);
                    p.setDokfk(selected);
                    p.setZaksiegowane(Boolean.FALSE);
                } else {
                    p.setKonto(p.getKontoWn().getNazwaskrocona().startsWith("2") ? p.getKontoWn() : p. getKontoMa());
                    p.setKontonumer(p.getKontoWn().getNazwaskrocona().startsWith("2") ? p.getKontoWn().getPelnynumer() : p. getKontoMa().getPelnynumer());
                    p.setKontoprzeciwstawne(p.getKontoWn().getNazwaskrocona().startsWith("2") ? p.getKontoMa().getPelnynumer() : p. getKontoWn().getPelnynumer());
                    p.setPodatniknazwa("Tymczasowy");
                    p.setDataksiegowania(selected.getDatawystawienia());
                    p.setTypwiersza(0);
                    p.setDokfk(selected);
                    opisdoprzekazania=p.getOpis();
                    p.setZaksiegowane(Boolean.FALSE);
                }
                  }
    }

    public void usundokument(Dokfk dousuniecia) {
        try {
            wykaz.remove(dousuniecia);
            dokDAOfk.usun(dousuniecia);
            Msg.msg("i", "Dokument usunięty");
        } catch (Exception e){
            Msg.msg("e", "Nie udało się usunąć dokumentu");
        }
    }

     public void nanieszapisynakontach(){
         if (!selected.getZapisynakoncie().isEmpty()){
            usunistniejacezapisy(selected.getZapisynakoncie());
         }
         List<Kontozapisy> zapisynakontach = new ArrayList<>();
         String opis = "";
         Dokfk x = selected;
         List<Wiersze> wierszewdokumencie = x.getKonta();
         for(Wiersze p : wierszewdokumencie){
         if(p.getTypwiersza()==1){
             dodajwn(p, x, opis, zapisynakontach);
         } else if(p.getTypwiersza()==2) {
             dodajma(p, x, opis, zapisynakontach);
         } else {
             opis = p.getOpis();
             dodajwn(p, x, opis, zapisynakontach);
             dodajma(p, x, opis, zapisynakontach);
         }
         }
         x.setNaniesionezapisy(true);
         x.setZapisynakoncie(zapisynakontach);
         RequestContext.getCurrentInstance().update("zestawieniedokumentow:dataList");
         Msg.msg("i", "Zapisy na kontacg wygenerowane "+x.getNumer());
         
     }
     
     private void usunistniejacezapisy(List<Kontozapisy> zachowanezapisy){
         try {
         for(Kontozapisy p : zachowanezapisy){
             kontoZapisyFKDAO.destroy(p);
         }
         } catch (Exception ex) {
             Msg.msg("e", "Błąd przy usuwaniu istniejących zapisó na kontach");
         }
     }
     
     private void dodajwn(Wiersze p,Dokfk x, String opis, List<Kontozapisy> zapisynakontach){
             Kontozapisy kontozapisy = new Kontozapisy();
             kontozapisy.setKonto(p.getKontoWn().getPelnynumer());
             kontozapisy.setKontoob(p.getKontoWn());
             kontozapisy.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
             kontozapisy.setNumer(x.getNumer());
             kontozapisy.setOpis(opis);
             kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
             kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
             kontozapisy.setKwotawn(p.getKwotaWn());
             kontozapisy.setKwotama(0);
             kontozapisy.setDokfk(x);
             kontozapisy.setWartoscpierwotna(p.getKwotaWn());
             kontozapisy.setDorozliczenia(0);
             zapisynakontach.add(kontozapisy);             
     }
     
     private void dodajma(Wiersze p,Dokfk x, String opis,  List<Kontozapisy> zapisynakontach){
             Kontozapisy kontozapisy = new Kontozapisy();
             kontozapisy.setKonto(p.getKontoMa().getPelnynumer());
             kontozapisy.setKontoob(p.getKontoMa());
             kontozapisy.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
             kontozapisy.setNumer(x.getNumer());
             kontozapisy.setOpis(opis);
             kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
             kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
             kontozapisy.setKwotawn(0);
             kontozapisy.setKwotama(p.getKwotaMa());
             kontozapisy.setDokfk(x);
             kontozapisy.setWartoscpierwotna(p.getKwotaMa());
             kontozapisy.setDorozliczenia(0);
             zapisynakontach.add(kontozapisy);
     }
     
     
     public void znajdzdokumentzzapisu(){
        selected = wiersz.getDokfk();
        liczbawierszy = selected.getKonta().size();
     }
     
     public void wybranodokmessage(){
         Msg.msg("i", "Wybrano dokument do edycji");
         List<Wiersze> wierszedowsadzenia = new ArrayList();
         setZapisz0edytuj1(true);
         liczbawierszy = selected.getKonta().size();
         //nie wiem dlaczego to dziala po dodaniu new Wiersze (1,0) - chodzilo o numery rzedu, zaczela dzialac edycja. Wczesniej szwankowal javascript. 
         //Bez tego jednak dostawalem pusty rzad po wpisaniu tej komenty nagle nie dostaje pustego rzedu tylko dzial kopiowanie do selected
         //totalny odlot. poszedl na to jeden wieczor
         //wierszedowsadzenia.add(new Wiersze(1,0));
         //selected.setKonta(wierszedowsadzenia);
     }
     
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

   
    
    
    public Kontozapisy getKontozapisy() {
        return kontozapisy;
    }

    public void setKontozapisy(Kontozapisy kontozapisy) {
        this.kontozapisy = kontozapisy;
    }

   //</editor-fold>

    
    
}
