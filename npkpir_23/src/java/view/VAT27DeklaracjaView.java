/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.Xad;
import dao.Deklaracjavat27DAO;
import deklaracje.vat272.Deklaracja;
import deklaracje.vat272.VAT27Bean;
import embeddable.Kwartaly;
import embeddable.TKodUS;
import entity.Deklaracjavat27;
import entity.Vat27;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VAT27DeklaracjaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private TKodUS tKodUS;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value="#{vat27View}")
    private Vat27View vat27View;
    @ManagedProperty(value="#{vat27FKView}")
    private Vat27FKView vat27FKView;
    @Inject
    private Deklaracjavat27DAO deklaracjavat27DAO;
    
    public void tworzdeklaracjekorekta(List<Vat27> lista) {
        Deklaracjavat27 stara = deklaracjavat27DAO.findbyPodatnikRokMc(wpisView);
        if (stara == null || stara.getDatazlozenia() == null) {
            Msg.msg("e", "Pierwotna deklaracja nie została wysłana, nie można zrobić korekty");
        } else {
            List<Vat27> staralista = stara.getPozycje();
            boolean robickorekte = false;
            int nrkolejny = 0;
            for (Vat27 s : staralista) {
                for (Vat27 t : lista) {
                    if (t.getKontrahent() != null && s.getKontrahent() != null && t.getKontrahent().equals(s.getKontrahent())) {
                        if (Z.z(t.getNetto()) != Z.z(s.getNetto())) {
                            t.setKorekta(true);
                            robickorekte = true;
                            nrkolejny = stara.getNrkolejny()+1;
                            break;
                        }
                    }
                }
            }
            if (robickorekte) {
                deklaracjavat27DAO.usundeklaracje27(wpisView);
                for (Iterator<Deklaracjavat27> it = vat27View.getDeklaracjevat27().iterator(); it.hasNext();) {
                    Deklaracjavat27 d = it.next();
                    if (d.getMiesiac().equals(wpisView.getMiesiacWpisu()) && d.getRok().equals(wpisView.getRokWpisuSt())) {
                        vat27View.getDeklaracjevat27().remove(d);
                        break;
                    }
                }
                robdeklaracje(lista, true, nrkolejny);
            } else {
                Msg.msg("w","Nie ma różnic w pozycjach deklaracji. Nie ma sensu robic korekty");
            }
        }
    }
    
     public void tworzdeklaracjekorektaFK(List<Vat27> lista) {
        Deklaracjavat27 stara = deklaracjavat27DAO.findbyPodatnikRokMc(wpisView);
        if (stara == null || stara.getDatazlozenia() == null) {
            Msg.msg("e", "Pierwotna deklaracja nie została wysłana, nie można zrobić korekty");
        } else {
            List<Vat27> staralista = stara.getPozycje();
            boolean robickorekte = false;
            int nrkolejny = 0;
            for (Vat27 s : staralista) {
                for (Vat27 t : lista) {
                    if (t.getKontrahent() != null && s.getKontrahent() != null && t.getKontrahent().equals(s.getKontrahent())) {
                        if (Z.z(t.getNetto()) != Z.z(s.getNetto())) {
                            t.setKorekta(true);
                            robickorekte = true;
                            nrkolejny = stara.getNrkolejny()+1;
                            break;
                        }
                    }
                }
            }
            if (robickorekte) {
                deklaracjavat27DAO.usundeklaracje27(wpisView);
                for (Iterator<Deklaracjavat27> it = vat27View.getDeklaracjevat27().iterator(); it.hasNext();) {
                    Deklaracjavat27 d = it.next();
                    if (d.getMiesiac().equals(wpisView.getMiesiacWpisu()) && d.getRok().equals(wpisView.getRokWpisuSt())) {
                        vat27View.getDeklaracjevat27().remove(d);
                        break;
                    }
                }
                robdeklaracjeFK(lista, true, nrkolejny);
            } else {
                Msg.msg("w","Nie ma różnic w pozycjach deklaracji. Nie ma sensu robic korekty");
            }
        }
    }
    
    public void tworzdeklaracje(List<Vat27> lista) {
        if (!lista.isEmpty() && lista.get(0).getKontrahent() != null) {
            robdeklaracje(lista, false, 0);
            vat27View.init3();
        } else {
            Msg.msg("w", "Lista dokumentów jest pusta");
        }
    }
    
    public void tworzdeklaracjeFK(List<Vat27> lista) {
        if (!lista.isEmpty() && lista.get(0).getKontrahent() != null) {
            robdeklaracjeFK(lista, false, 0);
            vat27FKView.init3();
        } else {
            Msg.msg("w", "Lista dokumentów jest pusta");
        }
    }
    
    public void robdeklaracje(List<Vat27> lista, boolean korekta, int nrkolejny) {
        try {
            String deklaracja = sporzadz(lista, korekta);
            Object[] podpisanadeklaracja = podpiszDeklaracje(deklaracja);
            if (podpisanadeklaracja != null) {
                Deklaracjavat27 deklaracjavat27 = generujdeklaracje(podpisanadeklaracja);
                deklaracjavat27.setNrkolejny(nrkolejny);
                for (Vat27 p : lista) {
                    p.setDeklaracjavat27(deklaracjavat27);
                }
                deklaracjavat27.setPozycje(lista);
                vat27View.getDeklaracjevat27().add(deklaracjavat27);
                Msg.msg("Sporządzono deklarację VAT-27");
            } else {
                Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-27. Sprawdź czy włożono kartę z podpisem! Sprawdź oznaczenia krajów i NIP-y");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-27");
        }
    }
    
    public void robdeklaracjeFK(List<Vat27> lista, boolean korekta, int nrkolejny) {
        try {
            String deklaracja = sporzadz(lista, korekta);
            Object[] podpisanadeklaracja = podpiszDeklaracje(deklaracja);
            if (podpisanadeklaracja != null) {
                Deklaracjavat27 deklaracjavat27 = generujdeklaracje(podpisanadeklaracja);
                deklaracjavat27.setNrkolejny(nrkolejny);
                for (Vat27 p : lista) {
                    p.setDeklaracjavat27(deklaracjavat27);
                }
                deklaracjavat27.setPozycje(lista);
                vat27FKView.getDeklaracjevat27().add(deklaracjavat27);
                Msg.msg("Sporządzono deklarację VAT-27");
            } else {
                Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-27. Sprawdź czy włożono kartę z podpisem! Sprawdź oznaczenia krajów i NIP-y");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-27");
        }
    }
    
    private String sporzadz(List<Vat27> lista, boolean korekta) {
        deklaracje.vat272.Deklaracja deklaracja = new Deklaracja();
        String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        if (korekta) {
            deklaracja.setNaglowek(VAT27Bean.tworznaglowekkorekta(wpisView.getMiesiacWpisu(),wpisView.getRokWpisuSt(),kodurzedu));
        } else {
            deklaracja.setNaglowek(VAT27Bean.tworznaglowek(wpisView.getMiesiacWpisu(),wpisView.getRokWpisuSt(),kodurzedu));
        }
        deklaracja.setPodmiot1(VAT27Bean.podmiot1(wpisView));
        deklaracja.setPozycjeSzczegolowe(VAT27Bean.pozycjeszczegolowe27(lista));
        deklaracja.setPouczenie(BigDecimal.ONE);
        //VAT27Bean.marszajuldoplikuxml(deklaracja, wpisView);
        return VAT27Bean.marszajuldoStringu(deklaracja, wpisView);
    }

    private Object[] podpiszDeklaracje(String xml) {
        Object[] deklaracjapodpisana = null;
        try {
            deklaracjapodpisana = Xad.podpisz(xml);
        } catch (Exception e) {
            E.e(e);
        }
        return deklaracjapodpisana;
    }
    
    private Deklaracjavat27 generujdeklaracje(Object[] podpisanadeklaracja) {
        Deklaracjavat27 deklaracjavat27 = new Deklaracjavat27();
        deklaracjavat27.setPodatnik(wpisView.getPodatnikWpisu());
        deklaracjavat27.setMiesiac(wpisView.getMiesiacWpisu());
        deklaracjavat27.setRok(wpisView.getRokWpisuSt());
        deklaracjavat27.setDeklaracja((String) podpisanadeklaracja[1]);
        deklaracjavat27.setDeklaracjapodpisana((byte[]) podpisanadeklaracja[0]);
        deklaracjavat27.setNrkwartalu(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu()));
        deklaracjavat27.setJestcertyfikat(true);
        deklaracjavat27.setKodurzedu(tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy()));
        deklaracjavat27.setSporzadzil(wpisView.getWprowadzil().getLogin());
        deklaracjavat27.setWzorschemy("http://crd.gov.pl/wzor/2017/01/11/3844/");
        return deklaracjavat27;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Vat27View getVat27View() {
        return vat27View;
    }

    public void setVat27View(Vat27View vat27View) {
        this.vat27View = vat27View;
    }

    public Vat27FKView getVat27FKView() {
        return vat27FKView;
    }

    public void setVat27FKView(Vat27FKView vat27FKView) {
        this.vat27FKView = vat27FKView;
    }

   
    
    
    
}
