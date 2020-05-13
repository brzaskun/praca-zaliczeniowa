/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.BOFKBean;
import beansFK.SaldoAnalitykaBean;
import beansFK.SprFinInfDodBean;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.SprFinKwotyInfDodDAO;
import embeddablefk.SaldoKonto;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import org.apache.commons.io.IOUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import plik.Plik;
import view.WpisView; import org.primefaces.PrimeFaces;
import static beansFK.SprFinInfDodBean.drukujInformacjeDodatkowa;
import dao.PodatnikUdzialyDAO;
import embeddable.Mce;
import entity.PodatnikUdzialy;
import java.util.Base64;
import java.util.Iterator;

/**
 *
 * @author Osito
 */
@ManagedBean
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
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    
    public void init() { //E.m(this);
        sprFinKwotyInfDod = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (sprFinKwotyInfDod==null) {
            sprFinKwotyInfDod = new SprFinKwotyInfDod(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            SprFinKwotyInfDod rokpop = sprFinKwotyInfDodDAO.findsprfinkwoty(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt());
            if (rokpop!=null) {
                skopiujdane(rokpop);
            }
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
        String datasporzadzenia = sprFinKwotyInfDod.getDatasporzadzenia();
        //System.out.println(""+datasporzadzenia);
        sprFinKwotyInfDod.setNrschemy("1-0");
        if (data.Data.czyjestpo("2019-09-01",datasporzadzenia)) {
            sprFinKwotyInfDod.setNrschemy("1-2");
        }
        //System.out.println(sprFinKwotyInfDod.getNrschemy());
    }
    
    //NIE ROBIC BAZE64 BO NIE BEDZIE DZIALAC
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            //String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            //String dt = String.valueOf((new Date()).getTime());
            //String nazwakrotka = wpisView.getPodatnikObiekt().getNip()+"_"+dt+"_"+"logo."+extension;
            sprFinKwotyInfDod.setPlik(uploadedFile.getContents());
            sprFinKwotyInfDod.setNazwapliku(filename);
            sprFinKwotyInfDodDAO.edit(sprFinKwotyInfDod);
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
            zapisz();
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
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
            Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                outStream.close();
            } catch (IOException ex) {
                Logger.getLogger(SprFinKwotyInfDodView.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void generujInfdod() {
        List<Konto> kontaklienta = kontoDAOfk.findWszystkieKontaPodatnikaRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<StronaWiersza> zapisyObrotyRozp = BOFKBean.pobierzZapisyObrotyRozp(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<StronaWiersza> zapisyRok = stronaWierszaDAO.findStronaByPodatnikRokRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<Konto> kontaklientarokpop = kontoDAOfk.findKontaOstAlitykaRokPop(wpisView);
        List<SaldoKonto> listaSaldoKonto = SaldoAnalitykaBean.przygotowanalistasaldbo(kontaklienta, kontaklientarokpop, zapisyBO, zapisyObrotyRozp, zapisyRok, wpisView.getPodatnikObiekt());
        SprFinInfDodBean.drukujInformacjeDodatkowa(wpisView, sprFinKwotyInfDod, listaSaldoKonto);
    }
    
    public void generujSprawozdanieZarzadu() {
        SprFinInfDodBean.drukujSprawozdanieZarzadu(wpisView, sprFinKwotyInfDod);
    }
    
    public void generujUchwaly() {
        SprFinInfDodBean.drukujUchwaly1(wpisView, sprFinKwotyInfDod, podatnikUdzialy);
        SprFinInfDodBean.drukujUchwaly2(wpisView, sprFinKwotyInfDod, podatnikUdzialy);
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
        podatnikUdzialy = podatnikUdzialyDAO.findUdzialyPodatnik(wpisView);
        for (Iterator<PodatnikUdzialy> it = podatnikUdzialy.iterator();it.hasNext();) {
            PodatnikUdzialy p = it.next();
            if (p.getDatazakonczenia()!=null && !p.getDatazakonczenia().equals("")) {
                String[] mc = Mce.zwiekszmiesiac(data.Data.getRok(sprFinKwotyInfDod.getDatasporzadzenia()), data.Data.getMc(sprFinKwotyInfDod.getDatasporzadzenia()));
                if (data.Data.czyjestprzed(p.getDatazakonczenia(), mc[0], mc[1])) {
                    it.remove();
                }
            }
            
        }
    }

    

       
    
}
