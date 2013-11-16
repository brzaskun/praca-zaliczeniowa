/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import embeddablefk.WierszStronafk;
import embeddablefk.WierszStronafkPK;
import entityfk.Dokfk;
import entityfk.DokfkPK;
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
import params.Params;
import viewfk.subroutines.NaniesZapisynaKontaFK;
import viewfk.subroutines.UzupelnijWierszeoDane;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable {
    private Dokfk selected;
    @Inject private DokDAOfk dokDAOfk;
    private boolean zapisz0edytuj1;
    private String wierszid;
    private String wnlubma;
    private int liczbawierszy;
    private List<Dokfk> wykazZaksiegowanychDokumentow;
    //a to jest w dialog_zapisywdokumentach
    @Inject private Wiersze wiersz;
    private int numerwierszazapisu;

    public DokfkView() {
        resetujDokument();
        wykazZaksiegowanychDokumentow = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        try {
             wykazZaksiegowanychDokumentow = dokDAOfk.findAll();
        } catch (Exception e) {
        }
        zapisz0edytuj1 = false;
    }
    
    //********************************************funkcje dla ksiegowania dokumentow
    //RESETUJ DOKUMNETFK
    private void resetujDokument() {
        selected = new Dokfk();
        DokfkPK dokfkPK = new DokfkPK();
        selected.setDokfkPK(dokfkPK);
        List<Wiersze> wiersze = new ArrayList<>();
        Wiersze nowywiersz = new Wiersze(1, 0);
        WierszStronafk wierszStronafkWn = new WierszStronafk();
        nowywiersz.setWierszStronaWn(wierszStronafkWn);
        nowywiersz.getWierszStronaWn().getWierszStronafkPK().setNrPorzadkowyWiersza(1);
        WierszStronafk wierszStronafkMa = new WierszStronafk();
        nowywiersz.setWierszStronaMa(wierszStronafkMa);
        nowywiersz.getWierszStronaMa().getWierszStronafkPK().setNrPorzadkowyWiersza(1);
        wiersze.add(nowywiersz);
        selected.setKonta(wiersze);
        liczbawierszy = 1;
        zapisz0edytuj1 = false;
        
    }
    
    //dodaje wiersze do dokumentu
    public void liczbaw() {
        double pierwsze = 0.0;
        double drugie = 0.0;
        try {
            liczbawierszy = selected.getKonta().size();
            pierwsze = selected.getKonta().get(liczbawierszy - 1).getWierszStronaWn().getKwota();
            drugie = selected.getKonta().get(liczbawierszy - 1).getWierszStronaMa().getKwota();
        } catch (Exception e) {
            Msg.msg("w", "Uzupełnij dane przed dodaniem nowego wiersza");
        }
        if (pierwsze != 0 || drugie != 0) {
            liczbawierszy++;    
            selected.getKonta().add(utworzNowyWiersz());
            RequestContext.getCurrentInstance().execute("załadujmodelzachowywaniawybranegopola();");
        } else {
            Msg.msg("w", "Uzuwpełnij dane przed dodaniem nowego wiersza");
        }
    }
    
    private Wiersze utworzNowyWiersz() {
        Wiersze nowywiersz = new Wiersze(liczbawierszy, 0);
        WierszStronafk wierszStronafkWn = new WierszStronafk();
        WierszStronafkPK wierszStronafkPKWn = new WierszStronafkPK();
        dodajdanedowiersza(wierszStronafkPKWn);
        nowywiersz.setWierszStronaWn(wierszStronafkWn);
        WierszStronafk wierszStronafkMa = new WierszStronafk();
        WierszStronafkPK wierszStronafkPKMa = new WierszStronafkPK();
        dodajdanedowiersza(wierszStronafkPKMa);
        nowywiersz.setWierszStronaMa(wierszStronafkMa);
        return nowywiersz;
    }
    
    private void dodajdanedowiersza(WierszStronafkPK w){
        w.setNrPorzadkowyWiersza(liczbawierszy);
        w.setTypdokumentu(selected.getDokfkPK().getSeriadokfk());
        w.setNrkolejnydokumentu(selected.getDokfkPK().getNrkolejny());
        w.setStronaWnlubMa("Wn");
    }
    //wersja dla pierwszegor zedu
    public void dodajdanedowierszaPW(){
        WierszStronafkPK w = selected.getKonta().get(0).getWierszStronaWn().getWierszStronafkPK();
        dodajdanedowiersza(w);
        w = selected.getKonta().get(0).getWierszStronaMa().getWierszStronafkPK();
        dodajdanedowiersza(w);
    }
    
    //usuwa wiersze z dokumentu

    public void liczbawu() {
        if (liczbawierszy > 1) {
            liczbawierszy--;
            selected.getKonta().remove(liczbawierszy);
        }
    }

    public void dodaj() {
        try {
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            //nanosimy zapisy na kontach
            NaniesZapisynaKontaFK.nanieszapisynakontach(selected.getKonta());
            //najpierw zobacz czy go nie ma, jak jest to usun i dodaj
            dokDAOfk.dodaj(selected);
            wykazZaksiegowanychDokumentow.add(selected);
            resetujDokument();
            RequestContext.getCurrentInstance().update("formwpisdokument");
            Msg.msg("i", "Dokument dodany");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się dodac dokumentu " + e.toString());
        }
    }
    
      public void edycja() {
        try {
            UzupelnijWierszeoDane.uzupelnijwierszeodane(selected);
            NaniesZapisynaKontaFK.nanieszapisynakontach(selected.getKonta());
            dokDAOfk.edit(selected);
            wykazZaksiegowanychDokumentow.clear();
            wykazZaksiegowanychDokumentow = dokDAOfk.findAll();
            Msg.msg("i", "Pomyślnie zaktualizowano dokument");
        } catch (Exception e) {
            System.out.println(e.toString());
            Msg.msg("e", "Nie udało się zmenic dokumentu " + e.toString());
        }
    }
    
    public void usundokument(Dokfk dousuniecia) {
        try {
            dokDAOfk.usun(dousuniecia);
            wykazZaksiegowanychDokumentow.remove(dousuniecia);
            resetujDokument();
            RequestContext.getCurrentInstance().update("formwpisdokument");
            Msg.msg("i", "Dokument usunięty");
        } catch (Exception e) {
            Msg.msg("e", "Nie udało się usunąć dokumentu");
        }
    }
    
    //***************************************
    public void znajdzduplicatdokumentu() {
        //uruchamiaj tylko jak jest wpisywanie a nie edycja
        if(zapisz0edytuj1==false) {
        Dokfk dokument = dokDAOfk.findDokfkObj(selected);
        if (dokument != null) {
            RequestContext.getCurrentInstance().execute("document.getElementById('formwpisdokument:numer').select();");
            Msg.msg("e", "Blad dokument o takim numerze juz istnieje");
        } else {
            Msg.msg("i", "Go on Master");
        }
        }
    }

    public void wygenerujokreswpisudokumentu() {
        String data = (String) Params.params("formwpisdokument:datka");
        String mc = data.split("-")[1];
        selected.setMiesiac(mc);
        RequestContext.getCurrentInstance().update("formwpisdokument:miesiac");
        Msg.msg("i", "Wygenerowano okres dokumentu");
    }

    public void pobierzostatninumerdok() {
        try {
            Dokfk ostatnidokumentdanegorodzaju = dokDAOfk.findDokfkLastofaType("Kowalski", selected.getDokfkPK().getSeriadokfk());
            selected.getDokfkPK().setNrkolejny(ostatnidokumentdanegorodzaju.getDokfkPK().getNrkolejny()+1);
            RequestContext.getCurrentInstance().update("formwpisdokument:numer");
        } catch (Exception e) {}
    }
    
    public void wybranodokmessage() {
        Msg.msg("i", "Wybrano dokument do edycji " + selected.getDokfkPK().toString());
        setZapisz0edytuj1(true);
        liczbawierszy = selected.getKonta().size();
    }
    
    public void znajdzdokumentzzapisu() {
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
        String makrozaznaczajacepole = "#formwpisdokument\\:dataList\\:" + String.valueOf(numerwierszazapisu) + "\\:opis";
        RequestContext.getCurrentInstance().update("formwpisdokument");
        RequestContext.getCurrentInstance().update("zestawieniezapisownakontach");
        //RequestContext.getCurrentInstance().execute("$(#formwpisdokument\\\\:dataList\\\\:5\\\\:opis).select()");
    }

     //on robi nie tylko to ze przywraca button, on jeszcze resetuje selected
    //dodalem to tutaj a nie przy funkcji edytuj bo wtedy nie wyswietlalo wiadomosci o edycji
    public void przywrocwpisbutton() {
        setZapisz0edytuj1(false);
        resetujDokument();
        RequestContext.getCurrentInstance().execute("pierwszy.hide();");
    }
    
    //********************
    
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    
    public Dokfk getSelected() {
        return selected;
    }

    public int getNumerwierszazapisu() {
        return numerwierszazapisu;
    }

    public void setNumerwierszazapisu(int numerwierszazapisu) {
        this.numerwierszazapisu = numerwierszazapisu;
    }
    
    public void setSelected(Dokfk selected) {
        this.selected = selected;
    }

    public List<Dokfk> getWykazZaksiegowanychDokumentow() {
        return wykazZaksiegowanychDokumentow;
    }

    public void setWykazZaksiegowanychDokumentow(List<Dokfk> wykazZaksiegowanychDokumentow) {
        this.wykazZaksiegowanychDokumentow = wykazZaksiegowanychDokumentow;
    }

    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }
    
    public String getWierszid() {
        return wierszid;
    }

    public void setWierszid(String wierszid) {
        this.wierszid = wierszid;
    }

    public String getWnlubma() {
        return wnlubma;
    }

    public void setWnlubma(String wnlubma) {
        this.wnlubma = wnlubma;
    }
    
    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }

    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }
    
    public Wiersze getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersze wiersz) {
        this.wiersz = wiersz;
    }
     
    //</editor-fold>

}
