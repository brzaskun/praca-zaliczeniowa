/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import daoFK.KontoZapisyFKDAO;
import embeddablefk.FKWiersz;
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

    public DokfkView() {
        liczbawierszy = 1;
        selected = new Dokfk();
        DokfkPK dokfkPK = new DokfkPK();
        dokfkPK.setRok("2013");
        selected.setDokfkPK(dokfkPK);
        wiersze = new ArrayList<>();
        wiersze.add(new Wiersze(1,0));
        selected.setKonta(wiersze);
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
//   
//    public void edycja(){
//        try {
//            dokDAOfk.destroy(selecteddokfk.get(0));
//            dokDAOfk.dodaj(selecteddokfk.get(0));
//            Msg.msg("i", "Dokument zmeniony");
//         } catch (Exception e){
//            System.out.println(e.toString());
//            Msg.msg("e", "Nie udało się zmenic dokumentu "+e.toString());
//        }
//    }
//    
    public void dodaj(){
        //ladnie uzupelnia informacje o wierszu pk
        String opisdoprzekazania="";
        try {
            for(Wiersze p : selected.getKonta()){
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
            selected = new Dokfk();
            wiersze = new ArrayList<>();
            wiersze.add(new Wiersze());
            selected.setKonta(wiersze);
            Msg.msg("i", "Dokument dodany");
        } catch (Exception e){
            Msg.msg("e", "Nie udało się dodac dokumentu");
        }
    }

//        try {
//            wykaz.remove(selecteddokfk.get(0));
//            dokDAOfk.usun(selecteddokfk.get(0));
//            Msg.msg("i", "Dokument usunięty");
//            RequestContext.getCurrentInstance().update("form");
//        } catch (Exception e){
//            Msg.msg("e", "Nie udało się usunąć dokumentu");
//        }
//    }

     public void nanieszapisynakontach(){
         List<Kontozapisy> zapisynakontach = new ArrayList<>();
         String opis = "";
         Dokfk x = selected;
         for(Wiersze p : x.getKonta()){
         if(p.getTypwiersza()==1){
             dodajwn(p, x, opis, zapisynakontach);
         } else if(p.getTypwiersza()==2) {
             dodajma(p, x, opis, zapisynakontach);
         } else {
             opis = p.getOpis();
             dodajwn(p, x, opis, zapisynakontach);
             dodajma(p, x, opis, zapisynakontach);
         }
         x.setNaniesionezapisy(true);
         x.setZapisynakoncie(zapisynakontach);
         }
         RequestContext.getCurrentInstance().update("form:dataList");
         Msg.msg("i", "Zapisy zaksięgowane "+x.getNumer());
         
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
        RequestContext.getCurrentInstance().update("dialogEdycja");
        RequestContext.getCurrentInstance().update("form");
        RequestContext.getCurrentInstance().update("dialogrozrachunki");
        
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
    
    
    public Kontozapisy getKontozapisy() {
        return kontozapisy;
    }

    public void setKontozapisy(Kontozapisy kontozapisy) {
        this.kontozapisy = kontozapisy;
    }

   //</editor-fold>

    
    
}
