/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import dao.PodatnikUdzialyDAO;
import dao.PodmiotDAO;
import data.Data;
import entity.Podatnik;
import entity.PodatnikUdzialy;
import entity.Podmiot;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.event.RowEditEvent;
import webservice.GUS;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodmiotView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private PodmiotDAO podmiotDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private PodatnikUdzialyDAO podatnikUdzialyDAO;
    private List<Podmiot> podmioty;
    private List<PodatnikUdzialy> podatnikUdzialy;
    private Podmiot selected;
    @Inject
    private Podmiot nowy;
    private boolean osobafizyczna;
    private boolean tylkoaktywne;
    private boolean tylkoprawne;
    private boolean tylkofizyczne;
    
    
    
    @PostConstruct
    private void init() {
        podmioty = podmiotDAO.findAll();
        podatnikUdzialy = podatnikUdzialyDAO.findAll();
        podatnikUdzialy = podatnikUdzialy.stream().filter(Objects::nonNull).filter(p->p.getPodatnikObj()!=null).filter(p->p.getPodatnikObj().isPodmiotaktywny()).collect(Collectors.toList());
        tylkoaktywne = true;
        nowy.setPin("1234");
        nowy.setKrajrezydencji("PL");
    }
    
    
    
    public void tworzeniePodmiotuzPodatnika() {
        System.out.println("poczatek");
        List<Podmiot> lista = new ArrayList<>();
        List<Podatnik> podatnicy = podatnikDAO.findAllManager();
        if (podatnicy != null) {
            podatnicy.forEach((p -> {
                if (p.isPodmiotaktywny()) {
                    Podmiot podmiot = new Podmiot(p);
                    Map<String, String> dane = pobierzdane(podmiot.getNip());
                    if (!dane.isEmpty() && dane.size() > 1) {
                        podmiot.setOsobafizyczna(czyfizyczna(dane, podmiot.getPesel(), podmiot.getKrs()));
                        podmiot.setPin("1234");
                        podmiot.setEmail(p.getEmail());
                        podmiot.setTelefon(p.getTelefonkontaktowy());
                        podmiot.setKrajrezydencji("PL");
                        podmiot.setAktywnydlazus(true);
                        if (podmiot.isOsobafizyczna() == false) {
                            podmiot.setNazwa(zrobnazwe(dane, p.getNip()));
                            podmiot.setPrintnazwa(p.getPrintnazwa());
                            podmiot.setKrs(zrobKRS(dane, p.getImie()));
                            podmiot.setImie(null);
                            podmiot.setNazwisko(null);
                            podmiot.setAktywnydlazus(false);
                        }
                        lista.add(podmiot);
                        //System.out.println("NIP " + p.getNip());
                        }
                }
            }));
            podmiotDAO.createList(lista);
        }
        System.out.println("koniec moniec");
    }
    
    
    
    public void pitoznacz() {
        System.out.println("poczatek pitoznacz");
        podmioty = podmiotDAO.findAll();
        if (podmioty != null) {
            for (Podmiot p : podmioty) {
                List<PodatnikUdzialy> podatnikudz = p.getPodatnikudzialy();
                if (podatnikudz!=null&&podatnikudz.size()==1) {
                    PodatnikUdzialy ppp = podatnikudz.get(0);
                    ppp.setPit(true);
                    podatnikUdzialyDAO.edit(ppp);
                }
            }
        }
        System.out.println("koniec moniec pitoznacz");
    }
    
    
      public void pobierzpodmioty() {
        podmioty = podmiotDAO.findAll();
        if (podmioty != null) {
            if (tylkofizyczne) {
                tylkoprawne = false;
                podmioty = podmioty.parallelStream().filter(p->p.isOsobafizyczna()).collect(Collectors.toList());
            } else if (tylkoprawne) {
                tylkofizyczne = false;
                podmioty = podmioty.parallelStream().filter(p->!p.isOsobafizyczna()).collect(Collectors.toList());
            }
        }
        System.out.println("koniec moniec pitoznacz");
    }
    
        public void podmiotudzialy() {
        System.out.println("poczatek udzialy");
        podatnikUdzialy = podatnikUdzialyDAO.findAll();
        podmioty = podmiotDAO.findAll();
        List<PodatnikUdzialy> udzialy = new ArrayList<>();
        if (podmioty != null && podatnikUdzialy!=null) {
            for (Podmiot p : podmioty) {
                for (Iterator it = podatnikUdzialy.iterator();it.hasNext();) {
                   PodatnikUdzialy u = (PodatnikUdzialy) it.next();
                   if (u!=null) {
                    if (p.isOsobafizyczna()) {
                        if (u.getPesel()!=null&&p.getPesel()!=null&&p.getPesel().equals(u.getPesel())) {
                            u.setPodmiot(p);
                            udzialy.add(u);
                            it.remove();
                        } else if (u.getNip()!=null&&p.getNip()!=null&&p.getNip().equals(u.getNip())) {
                            u.setPodmiot(p);
                            udzialy.add(u);
                            it.remove();
                        }
                    } else {
                        if (u.getNip()!=null&&p.getNip()!=null&&p.getNip().equals(u.getNip())) {
                            u.setPodmiot(p);
                            udzialy.add(u);
                            it.remove();
                        }
                    }
                   }
                }
            }
        }
        podatnikUdzialyDAO.editList(udzialy);
        System.out.println("koniec moniec udzialy");
    }
    
    public void usunpodmioty() {
        System.out.println("poczatek udzialy");
        podatnikUdzialy = podatnikUdzialyDAO.findAll();
        for (Iterator it = podatnikUdzialy.iterator();it.hasNext();) {
            PodatnikUdzialy u = (PodatnikUdzialy) it.next();
            u.setPodmiot(null);
        }
        podatnikUdzialyDAO.editList(podatnikUdzialy);
        System.out.println("koniec moniec udzialy");
    }

   
    public void pobierzdaneind() {
        if (this.nowy.getNip()!=null) {
            boolean niemanipu = sprawdznip(podmioty, this.nowy.getNip());
            if (niemanipu) {
                Map<String, String> dane = pobierzdane(this.nowy.getNip());
                if (!dane.isEmpty() && dane.size() > 1) {
                        this.nowy.setRegon(dane.get("Regon"));
                        String typ = dane.get("Typ");
                        if (typ.equals("P")) {
                            this.nowy.setNazwa(dane.get("Nazwa"));
                            this.nowy.setPrintnazwa(webservice.GUS.zmniejsznazwe("Nazwa", dane.get("Nazwa")));
                            this.nowy.setKrs(dane.get("praw_numerWrejestrzeEwidencji"));
                            this.nowy.setOsobafizyczna(false);
                        } else {
                            this.nowy.setAktywnydlazus(true);
                            this.nowy.setOsobafizyczna(true);
                        }
                    Msg.msg("Uzupełniono dane");
                } else {
                    this.nowy.setRegon("BŁĘDNY NIP");
                    Msg.msg("e","Błędny NIP");
                }
                
            } else {
                this.nowy.setRegon("TAKI PODMIOT JUŻ ISTNIEJE");
                Msg.msg("e","Taki podmiot już istnieje!");
            }
        }
    }
    
    private boolean sprawdznip(List<Podmiot> podmioty, String nip) {
        Podmiot znaleziony = podmioty.stream().filter(p->p.getNip().equals(nip)).findFirst().orElse(null);
        return znaleziony==null;
    }
    
    public void sprawdzpesel() {
        Podmiot znaleziony = null;
        for (Podmiot p : podmioty) {
            if (p.getPesel()!=null && p.getPesel().equals(nowy.getPesel())) {
                znaleziony = p;
            }
        }
        if (znaleziony!=null) {
             this.nowy.setNazwa("TAKI PODMIOT JUŻ ISTNIEJE");
        } 
    }
    
    public void sprawdzkrs() {
        Podmiot znaleziony = null;
        for (Podmiot p : podmioty) {
            if (p.getKrs()!=null && p.getKrs().equals(nowy.getKrs())) {
                znaleziony = p;
            }
        }
        if (znaleziony!=null) {
             this.nowy.setNazwa("TAKI PODMIOT JUŻ ISTNIEJE");
        }
    }
            
    private String zrobKRS(Map<String, String> dane, String krs) {
        String zwrot = krs;
        if (!dane.isEmpty()) {
            zwrot = dane.get("praw_numerWrejestrzeEwidencji");
        }
        return zwrot;
    }
            
    
    private String zrobnazwe(Map<String, String> dane, String nip) {
        String nazwa = "brak";
        if (!dane.isEmpty() && nip!=null) {
            nazwa = dane.get("NazwaOryginal");
        }
        return nazwa;
    }

    private boolean czyfizyczna(Map<String, String> dane, String pesel, String krs) {
        boolean jestfizyczna = false;
        if (pesel!=null) {
            jestfizyczna = true;
        }
        if (krs!=null) {
            jestfizyczna = false;
        }
        if (dane.get("Typ").equals("F")) {
            jestfizyczna = true;
        }
        return jestfizyczna;
    }

    private Map<String, String> pobierzdane(String nip) {
        Map<String, String> zwrot = new HashMap<>();
        try {
            GUS poc = new GUS();
            zwrot = poc.pobierz(nip);
        } catch (Exception e) {
        }
        return zwrot;
        }
    
    
    public void rowedit(RowEditEvent event) {
        Podmiot podmiot = (Podmiot) event.getObject();
        podmiotDAO.edit(podmiot);
        Msg.msg("Naniesiono zmiany");
    }
    
    public void edytujpodmiot(Podmiot podmiot) {
        if (podmiot!=null) {
            podmiotDAO.edit(podmiot);
            Msg.msg("Naniesiono zmianę daty");
        } else {
            Msg.msg("e","Nie wybrano podmiotu");
        }
    }
    
    public void dodajnowy() {
        if (nowy!=null) {
            if (nowy.getNip()==null&&nowy.getPesel()!=null) {
                Msg.msg("e","Błąd. Należy wprowadzić albo NIP albo Pesel");
            } else {
                nowy.setStart(Data.data_yyyyMMdd(new Date()));
                podmiotDAO.create(nowy);
                podmioty.add(nowy);
                nowy = new Podmiot();
                Msg.msg("Dodano nowy podmiot");
            }
        }
    }
    
    public void rowcancel() {
        Msg.msg("e","Anulowano zmiany");
    }

    private void pokazinfo(String nip) {
        System.out.println("NIP 888 aKALINK sssA " + nip);
    }
    
    public void pobierzliste(ValueChangeEvent event) {
        boolean newValue = (boolean) event.getNewValue();
        if (newValue==true) {
            podatnikUdzialy = podatnikUdzialy.stream().filter(Objects::nonNull).filter(p->p.getPodatnikObj()!=null).filter(p->p.getPodatnikObj().isPodmiotaktywny()).collect(Collectors.toList());
        } else {
            podatnikUdzialy = podatnikUdzialyDAO.findAll();
        }
        System.out.println("");
    }

    public void pobierzliste1(ValueChangeEvent event) {
        boolean newValue = (boolean) event.getNewValue();
        if (newValue==true) {
            podatnikUdzialy = podatnikUdzialy.stream().filter(Objects::nonNull).filter(p->p.getPodatnikObj()!=null).filter(r->r.getPodatnikObj().getPesel().equals("00000000000")).collect(Collectors.toList());
        } else {
            podatnikUdzialy = podatnikUdzialyDAO.findAll();
        }
        System.out.println("");
    }

    
    public void nowypodmiot(PodatnikUdzialy p) {
        
    }
    
    public void editudzial(PodatnikUdzialy p) {
        try {
            podatnikUdzialyDAO.edit(p);
            Msg.dP();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public List<Podmiot> getPodmioty() {
        return podmioty;
    }

    public void setPodmioty(List<Podmiot> podmioty) {
        this.podmioty = podmioty;
    }

    public Podmiot getSelected() {
        return selected;
    }

    public void setSelected(Podmiot selected) {
        this.selected = selected;
    }

    public Podmiot getNowy() {
        return nowy;
    }

    public void setNowy(Podmiot nowy) {
        this.nowy = nowy;
    }

    public boolean isOsobafizyczna() {
        return osobafizyczna;
    }

    public void setOsobafizyczna(boolean osobafizyczna) {
        this.osobafizyczna = osobafizyczna;
    }

    public List<PodatnikUdzialy> getPodatnikUdzialy() {
        return podatnikUdzialy;
    }

    public void setPodatnikUdzialy(List<PodatnikUdzialy> podatnikUdzialy) {
        this.podatnikUdzialy = podatnikUdzialy;
    }

    public boolean isTylkoaktywne() {
        return tylkoaktywne;
    }

    public void setTylkoaktywne(boolean tylkoaktywne) {
        this.tylkoaktywne = tylkoaktywne;
    }

    public boolean isTylkoprawne() {
        return tylkoprawne;
    }

    public void setTylkoprawne(boolean tylkoprawne) {
        this.tylkoprawne = tylkoprawne;
    }

    public boolean isTylkofizyczne() {
        return tylkofizyczne;
    }

    public void setTylkofizyczne(boolean tylkofizyczne) {
        this.tylkofizyczne = tylkofizyczne;
    }

    

    
    
}


