/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import daoFK.KontoZapisyFKDAO;
import embeddablefk.FKWiersz;
import entityfk.Dokfk;
import entityfk.Kontozapisy;
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
    private List<FKWiersz> wiersze;
    @Inject private DokDAOfk dokDAOfk;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private Kontozapisy kontozapisy;
    private static List<Dokfk> selecteddokfk;
    private List<Dokfk> wykaz;

    public DokfkView() {
        liczbawierszy = 1;
        selected = new Dokfk();
        wiersze = new ArrayList<>();
        wiersze.add(new FKWiersz(1,0));
        selected.setKonta(wiersze);
        wykaz = new ArrayList<>();
        selecteddokfk = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        wykaz = dokDAOfk.findAll();
    }
    
    public void liczbaw() {
        Double pierwsze = wiersze.get(liczbawierszy-1).getKwotaWn();
        Double drugie = wiersze.get(liczbawierszy-1).getKwotaMa();
        if(pierwsze!=null||drugie!=null){
            liczbawierszy++;
            wiersze.add(new FKWiersz(liczbawierszy,0));
        }
    }
    
   /**
    * Usuwanie wierszy z dokumenu ksiegowego
    */
    public void liczbawu() {
        if(liczbawierszy>1){
            liczbawierszy--;
            wiersze.remove(liczbawierszy);
        }
    }
   
    public void edycja(){
        try {
            dokDAOfk.destroy(selecteddokfk.get(0));
            dokDAOfk.dodaj(selecteddokfk.get(0));
            Msg.msg("i", "Dokument zmeniony");
         } catch (Exception e){
            System.out.println(e.toString());
            Msg.msg("e", "Nie udało się zmenic dokumentu "+e.toString());
        }
    }
    
    public void dodaj(){
        //ladnie uzupelnia informacje o wierszu pk
        String opisdoprzekazania="";
        try {
            for(FKWiersz p : selected.getKonta()){
                String opis = p.getOpis();
                if(opis.contains("kontown")){
                    p.setKonto(p.getKontoWn());
                    p.setPodatnik("Tymczasowy");
                    p.setDataksiegowania(selected.getData());
                    p.setKwotaMa(0.0);
                    p.setTypwiersza(1);
                    p.setOpis(opisdoprzekazania);
                    p.setZaksiegowane(Boolean.FALSE);
                } else if (opis.contains("kontoma")){
                    p.setKonto(p.getKontoMa());
                    p.setPodatnik("Tymczasowy");
                    p.setDataksiegowania(selected.getData());
                    p.setKwotaWn(0.0);
                    p.setTypwiersza(2);
                    p.setOpis(opisdoprzekazania);
                    p.setZaksiegowane(Boolean.FALSE);
                } else {
                    //no wlasnie i tu jest ta gupota, chyba trzeba to zmienic
                    p.setKonto(p.getKontoWn());
                    p.setPodatnik("Tymczasowy");
                    p.setDataksiegowania(selected.getData());
                    p.setTypwiersza(0);
                    opisdoprzekazania=p.getOpis();
                    p.setZaksiegowane(Boolean.FALSE);
                }
            }
            dokDAOfk.dodaj(selected);
            Msg.msg("i", "Dokument dodany");
        } catch (Exception e){
            Msg.msg("e", "Nie udało się dodac dokumentu");
        }
    }
    
     public void usun(){
        try {
            wykaz.remove(selecteddokfk.get(0));
            dokDAOfk.usun(selecteddokfk.get(0));
            Msg.msg("i", "Dokument usunięty");
            RequestContext.getCurrentInstance().update("form");
        } catch (Exception e){
            Msg.msg("e", "Nie udało się usunąć dokumentu");
        }
    }

     public void zaksieguj(){
         Dokfk x = selecteddokfk.get(0);
         for(FKWiersz p : x.getKonta()){
         if(p.getTypwiersza()==1){
             dodajwn(p, x);
         } else if(p.getTypwiersza()==2) {
             dodajma(p, x);
         } else {
             dodajwn(p, x);
             dodajma(p, x);
         }
         }
     }
     
     private void dodajwn(FKWiersz p,Dokfk x){
             kontozapisy.setPodatnik(p.getPodatnik());
             kontozapisy.setKonto(p.getKontoWn().getPelnynumer());
             kontozapisy.setKontoob(p.getKonto());
             kontozapisy.setDokument(x);
             kontozapisy.setOpis(p.getOpis());
             kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
             kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
             kontozapisy.setKwotawn(p.getKwotaWn());
             kontozapisy.setKwotama(0);
             kontoZapisyFKDAO.dodaj(kontozapisy);
             kontozapisy = new Kontozapisy();
     }
     
     private void dodajma(FKWiersz p,Dokfk x){
             kontozapisy.setPodatnik(p.getPodatnik());
             kontozapisy.setKonto(p.getKontoMa().getPelnynumer());
             kontozapisy.setKontoob(p.getKonto());
             kontozapisy.setDokument(x);
             kontozapisy.setOpis(p.getOpis());
             kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
             kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
             kontozapisy.setKwotawn(0);
             kontozapisy.setKwotama(p.getKwotaMa());
             kontoZapisyFKDAO.dodaj(kontozapisy);
             kontozapisy = new Kontozapisy();
     }
     
    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    public List<FKWiersz> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<FKWiersz> wiersze) {
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
  
    
    
}
