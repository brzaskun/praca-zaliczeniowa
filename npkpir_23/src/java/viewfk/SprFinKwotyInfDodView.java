/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.BOFKBean;
import beansFK.SaldoAnalitykaBean;
import beansFK.SprFinInfDodBean;
import dao.DokDAOfk;
import dao.KontoDAOfk;
import dao.PodatnikUdzialyDAO;
import dao.SprFinKwotyInfDodDAO;
import dao.StronaWierszaDAO;
import data.Data;
import embeddable.Mce;
import embeddablefk.SaldoKonto;
import entity.PodatnikUdzialy;
import entityfk.Konto;
import entityfk.SprFinKwotyInfDod;
import entityfk.StronaWiersza;
import error.E;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
 import org.apache.commons.io.IOUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import plik.Plik;
import view.WpisView;
import wydajnosc.ConstructorInterceptor;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class SprFinKwotyInfDodView  implements Serializable{
    private static final long serialVersionUID = 1L;
    private SprFinKwotyInfDod sprFinKwotyInfDod;
    @Inject
    private SprFinKwotyInfDodDAO sprFinKwotyInfDodDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    private List<PodatnikUdzialy> podatnikUdzialy;
    @Inject
    private WpisView wpisView;
    
    
    public void init() { //E.m(this);
        sprFinKwotyInfDod = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (sprFinKwotyInfDod==null) {
            sprFinKwotyInfDod = new SprFinKwotyInfDod(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            sprFinKwotyInfDod.setDatasporzadzenia(Data.aktualnaData());
            sprFinKwotyInfDod.setDatauchwal(Data.aktualnaData());
            SprFinKwotyInfDod rokpop = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt());
            if (rokpop!=null) {
                skopiujdane(rokpop);
            }
        } else {
            
        }
        pobierzudzialy();
    }
    
    private void skopiujdane(SprFinKwotyInfDod rokpop) {
        sprFinKwotyInfDod.setSad(rokpop.getSad());
        sprFinKwotyInfDod.setPpdzialalnosci(rokpop.getPpdzialalnosci());
        sprFinKwotyInfDod.setPozpdzialalnosci(rokpop.getPozpdzialalnosci());
        sprFinKwotyInfDod.setPid1B(rokpop.getPid1A());
        sprFinKwotyInfDod.setPid2B(rokpop.getPid2A());
        sprFinKwotyInfDod.setPid3B(rokpop.getPid3A());
        sprFinKwotyInfDod.setPid4B(rokpop.getPid4A());
        sprFinKwotyInfDod.setPid5B(rokpop.getPid5A());
        sprFinKwotyInfDod.setPid6B(rokpop.getPid6A());
        sprFinKwotyInfDod.setPid7B(rokpop.getPid7A());
        sprFinKwotyInfDod.setPid8B(rokpop.getPid8A());
        sprFinKwotyInfDod.setPid9B(rokpop.getPid9A());
        sprFinKwotyInfDod.setPid10B(rokpop.getPid10A());
        sprFinKwotyInfDod.setPid11B(rokpop.getPid11A());
    }
    
    public void kopiujsad() {
        SprFinKwotyInfDod rokpop = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt());
        if (rokpop!=null) {
            sprFinKwotyInfDod.setSad(rokpop.getSad());
            sprFinKwotyInfDod.setPpdzialalnosci(rokpop.getPpdzialalnosci());
            sprFinKwotyInfDod.setPozpdzialalnosci(rokpop.getPozpdzialalnosci());
            Msg.msg("Pobrano dane");
        } else {
            Msg.msg("Brak danych w roku poprzednim");
        }
        
    }
    
    public void zapisz() {
        try {
            ustawscheme();
            sprFinKwotyInfDodDAO.edit(sprFinKwotyInfDod);
            init();
            Msg.msg("Zachowano zmiany");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd, niezachowano zmian");
        }
    }
    
    private void ustawscheme() {
        String datasporzadzenia = sprFinKwotyInfDod.getDatauchwal();
        //error.E.s(""+datasporzadzenia);
        sprFinKwotyInfDod.setNrschemy("1-0");
        if (data.Data.czyjestpo("2019-09-01",datasporzadzenia)) {
            sprFinKwotyInfDod.setNrschemy("1-2");
        }
        //error.E.s(sprFinKwotyInfDod.getNrschemy());
    }
    
    //NIE ROBIC BAZE64 BO NIE BEDZIE DZIALAC
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            //String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            //String dt = String.valueOf((new Date()).getTime());
            //String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
            String rozszerzenie = filename.substring(filename.length()-3, filename.length());
            if (rozszerzenie.equals("pdf")) {
                sprFinKwotyInfDod.setPlik(uploadedFile.getContents());
                sprFinKwotyInfDod.setNazwapliku(filename);
                sprFinKwotyInfDodDAO.edit(sprFinKwotyInfDod);
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
                zapisz();
            } else {
                Msg.msg("e", "Nie właściwy typ pliku. Musi być pdf");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    public void zachowajplikOpcja(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            //String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            //String dt = String.valueOf((new Date()).getTime());
            //String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
            String rozszerzenie = filename.substring(filename.length()-3, filename.length());
            if (rozszerzenie.equals("pdf")) {
                sprFinKwotyInfDod.setPlikOpcja(uploadedFile.getContents());
                sprFinKwotyInfDod.setNazwaplikuOpcja(filename);
                sprFinKwotyInfDodDAO.edit(sprFinKwotyInfDod);
                Msg.msg("Sukces. Opcjonalny plik " + filename + " został skutecznie załadowany");
                zapisz();
            } else {
                Msg.msg("e", "Nie właściwy typ pliku. Musi być pdf");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku opcjonalnego");
        }
    }
    
    
    public void zachowajplikxml(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            //String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            //String dt = String.valueOf((new Date()).getTime());
            //String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
            sprFinKwotyInfDod.setPlikxml(IOUtils.toByteArray(uploadedFile.getInputstream()));
            sprFinKwotyInfDod.setNazwaplikuxml(filename);
            sprFinKwotyInfDodDAO.edit(sprFinKwotyInfDod);
            Msg.msg("Sukces. Plik xml " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku xml");
        }
    }
    
    public void pokazplik() {
        OutputStream outStream = null;
        try {
            String nazwa = "informacjadodatkowa"+wpisView.getPodatnikObiekt().getNip();
            File targetFile = Plik.plik(nazwa+".pdf", true);
            outStream = new FileOutputStream(targetFile);
            outStream.write(sprFinKwotyInfDod.getPlik());
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } catch (FileNotFoundException ex) {
            // Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                // Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void pokazplikOpcja() {
        OutputStream outStream = null;
        try {
            String nazwa = "informacjawstepna"+wpisView.getPodatnikObiekt().getNip();
            File targetFile = Plik.plik(nazwa+".pdf", true);
            outStream = new FileOutputStream(targetFile);
            outStream.write(sprFinKwotyInfDod.getPlikOpcja());
            String f = "pokazwydruk('"+nazwa+"');";
            PrimeFaces.current().executeScript(f);
        } catch (FileNotFoundException ex) {
            // Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                // Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void pokazplikxml() {
        OutputStream outStream = null;
        try {
            String nazwa = "sprawozdaniefin"+wpisView.getPodatnikObiekt().getNip();
            File targetFile = Plik.plik(nazwa+".xml", true);
            outStream = new FileOutputStream(targetFile);
            outStream.write(sprFinKwotyInfDod.getPlikxml());
            nazwa = nazwa+".xml";
            String f = "pokazwydrukpdf('"+nazwa+"');";//jest pdf ale to pokazuje bez dodawanai rozszerzenia
            PrimeFaces.current().executeScript(f);
        } catch (FileNotFoundException ex) {
            // Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            // Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                // Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void generujInfdod() {
        if (sprFinKwotyInfDod.getSad()==null) {
            Msg.msg("e","Nie wpisano sądu. Nie można wygenerować informacji");
            return;
        } else if (sprFinKwotyInfDod.getPpdzialalnosci()==null) {
            Msg.msg("e","Nie wpisano głównego rodzaju działalności. Nie można wygenerować informacji");
            return;
        } else if (sprFinKwotyInfDod.getPozpdzialalnosci()==null) {
            Msg.msg("e","Nie wpisano rodzaju działalności. Nie można wygenerować informacji");
            return;
        } else {
            List<Konto> kontaklienta = kontoDAOfk.findWszystkieKontaPodatnikaRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<StronaWiersza> zapisyObrotyRozp = BOFKBean.pobierzZapisyObrotyRozp(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<StronaWiersza> zapisyRok = stronaWierszaDAO.findStronaByPodatnikRokRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            List<Konto> kontaklientarokpop = kontoDAOfk.findKontaOstAlityka( wpisView.getPodatnikObiekt(), wpisView.getRokUprzedni());
            boolean czyzaksiegowanoporok = true;
//            if (!kontaklienta.isEmpty()&&!kontaklientarokpop.isEmpty()) {
//                czyzaksiegowanoporok = SaldoAnalitykaBean.sprawdzzaksiegowanie(kontaklienta, kontaklientarokpop);
//            }
            if (czyzaksiegowanoporok) {
                List<SaldoKonto> listaSaldoKonto = SaldoAnalitykaBean.przygotowanalistasaldbo(kontaklienta, kontaklientarokpop, zapisyBO, zapisyObrotyRozp, zapisyRok, wpisView.getPodatnikObiekt());
                try {
                    SprFinInfDodBean.drukujInformacjeDodatkowa(wpisView, sprFinKwotyInfDod, listaSaldoKonto);
                } catch (Exception e) {
                    Msg.msg("e","Nie udało się wygenerować informacji w pdf");
                }
            } else {
                Msg.msg("e","Są różnice między zaksięgowanymi saldami z roku pop. a kwotami w BO.");
            }
        }
    }
    
    public void generujSprawozdanieZarzadu() {
        SprFinInfDodBean.drukujSprawozdanieZarzadu(wpisView, sprFinKwotyInfDod);
    }
    
    public void generujUchwaly() {
        try {
            SprFinInfDodBean.drukujUchwaly1(wpisView, sprFinKwotyInfDod, podatnikUdzialy);
            SprFinInfDodBean.drukujUchwaly2(wpisView, sprFinKwotyInfDod, podatnikUdzialy);
        } catch (Exception e) {
            Msg.msg("e","Nie udało się wygenerować uchwał w pdf");
        }
    }

    public SprFinKwotyInfDod getSprFinKwotyInfDod() {
        return sprFinKwotyInfDod;
    }

    public void setSprFinKwotyInfDod(SprFinKwotyInfDod sprFinKwotyInfDod) {
        this.sprFinKwotyInfDod = sprFinKwotyInfDod;
    }
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<PodatnikUdzialy> getPodatnikUdzialy() {
        return podatnikUdzialy;
    }

    public void setPodatnikUdzialy(List<PodatnikUdzialy> podatnikUdzialy) {
        this.podatnikUdzialy = podatnikUdzialy;
    }

    private void pobierzudzialy() {
        podatnikUdzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView.getPodatnikObiekt());
        for (Iterator<PodatnikUdzialy> it = podatnikUdzialy.iterator();it.hasNext();) {
            PodatnikUdzialy p = it.next();
            if (p.getDatazakonczenia()!=null && !p.getDatazakonczenia().equals("")) {
                String[] okres = Mce.zwiekszmiesiac(data.Data.getRok(sprFinKwotyInfDod.getDatasporzadzenia()), data.Data.getMc(sprFinKwotyInfDod.getDatasporzadzenia()));
                if (data.Data.czyokresjestprzed(p.getDatazakonczenia(), okres[0], okres[1])) {
                    it.remove();
                }
            }
            
        }
    }

    
    public static void main(String[] args) {
        boolean czyjestprzed = data.Data.czydatajestprzed("2019-05-01", "2020", "06");
        System.out.println(czyjestprzed);
    }
       
    
}
