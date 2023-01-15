/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EtatPracFacade;
import dao.RodzajwynagrodzeniaFacade;
import dao.SMTPSettingsFacade;
import dao.SkladnikWynagrodzeniaFacade;
import dao.StanowiskopracFacade;
import dao.UmowaFacade;
import dao.UmowakodzusFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import data.Data;
import entity.Angaz;
import entity.FirmaKadry;
import entity.SMTPSettings;
import entity.Umowa;
import entity.Umowakodzus;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.model.DualListModel;
import pdf.PdfUmowaoZlecenia;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class UmowaGrupowoView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private org.primefaces.model.DualListModel<Angaz> listaumowy;
    @Inject
    private Umowa selected;
    @Inject
    private UmowakodzusFacade rodzajumowyFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    @Inject
    private EtatPracFacade etatFacade;
    @Inject
    private StanowiskopracFacade stanowiskopracFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private UmowaFacade umowaFacade;
    private List<Umowakodzus> listaumowakodzus;
    private double wynagrodzeniemiesieczne;
    private double wynagrodzeniegodzinowe;


     @PostConstruct
    private void init() {
        if (wpisView.getFirma()!=null) {
            listaumowy = new DualListModel<>();
            List<Angaz> angaze = wpisView.getFirma().getAngazList();
            listaumowy.setSource(angaze!=null?angaze:new ArrayList<>());
            listaumowy.setTarget(new ArrayList<>());
            listaumowakodzus = rodzajumowyFacade.findUmowakodzusAktywneZlecenie();
        }
    }
    
     public void ustawumowe() {
        int numerkolejny = 1;
        List<Umowa> umowaList = umowaFacade.findByAngaz(wpisView.getAngaz());
        if (umowaList != null) {
            numerkolejny = numerkolejny + umowaList.size();
        }
        if (selected.getId() == null) {
            if (selected.getUmowakodzus().isPraca()) {
                selected.setNrkolejny("UP/" + numerkolejny + "/" + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu());
                selected.setChorobowe(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setNfz("16R");
            } else if (selected.getUmowakodzus().isZlecenie()) {
                selected.setNrkolejny("UC/" + numerkolejny + "/" + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu());
                selected.setChorobowe(false);
                selected.setChorobowedobrowolne(true);
                selected.setRentowe(true);
                selected.setEmerytalne(true);
                selected.setWypadkowe(true);
                selected.setZdrowotne(true);
                selected.setNfz("16R");
            }
        }
    }
     
     public void drukujumoweselectedLista() {
        if (selected != null) {
             if (listaumowy.getTarget()==null||listaumowy.getTarget().size()==0) {
                Msg.msg("w", "Nie ma wybranych pracowników");
            } else {
                PdfUmowaoZlecenia.drukujwszystkie(selected, wpisView.getFirma(), listaumowy.getTarget(), wynagrodzeniegodzinowe);
             }
        } else {
            Msg.msg("e", "Nie wybrano umowy");
        }
    }
     
     public void mailUmowyZlecenia() {
         if (listaumowy.getTarget()==null||listaumowy.getTarget().size()==0) {
                Msg.msg("w", "Nie ma wybranych zleceniobiorców");
            } else {
                FirmaKadry firmaKadry = wpisView.getFirma();
                ByteArrayOutputStream drukujmail = PdfUmowaoZlecenia.drukujwszystkie(selected, wpisView.getFirma(), listaumowy.getTarget(), wynagrodzeniegodzinowe);
                SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                String nazwa = firmaKadry.getNip()+"umowyzlecenia.pdf";
                mail.Mail.mailUmowyZlecenia(wpisView.getFirma(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, wpisView.getUzer().getEmail());
                Msg.msg("Wysłano umowy zlecenia do klienta");
         }
    }
     
     public void naniesdoBazy() {
         if (listaumowy.getTarget()==null||listaumowy.getTarget().size()==0) {
                Msg.msg("w", "Nie ma wybranych zleceniobiorców");
            } else {
             List<Angaz> target = listaumowy.getTarget();
                for (Angaz angaz : target) {
                    selected.setAngaz(angaz);
                    beanstesty.UmowaBean.createpierwsza(selected, umowaFacade, etatFacade, stanowiskopracFacade, rodzajwynagrodzeniaFacade, skladnikWynagrodzeniaFacade, zmiennaWynagrodzeniaFacade);
                }
                Msg.msg("Zapisano umowy zlecenia do bazy");
         }
     }
         
     
     public void sprawdzczyumowajestnaczas() {
        if (selected.getDatado() != null) {
            if (selected.getSlownikszkolazatrhistoria() != null && selected.getSlownikszkolazatrhistoria().getSymbol().equals("P")) {
                Msg.msg("e", "Wybrano umowę na czas nieokreślony a wprowadzono datę do!");
            }
        }
    }
     
     
     public void naniesdatynaumowe() {
        if (selected != null && selected.getDatazawarcia() != null) {
            String rok = selected.getDatazawarcia().substring(0,4);
            int rokI = Integer.parseInt(rok);
            if (rokI<2022) {
                selected.setDatazawarcia(null);
                Msg.msg("e","Umowa nie może być z wcześniejszego roku niż 2022");
            } else {
                selected.setDataspoleczne(selected.getDatazawarcia());
                selected.setDatazdrowotne(selected.getDatazawarcia());
                selected.setDataod(selected.getDatazawarcia());
                selected.setTerminrozpoczeciapracy(selected.getDatazawarcia());
                obliczwiek();
            }
        }
    }
     
     public void obliczwiek() {
        String zwrot = "";
        String dataurodzenia = wpisView.getAngaz().getPracownik().getDataurodzenia();
        if (dataurodzenia == null) {
            Msg.msg("e", "Brak daty urodzenia pracownika");
        } else if (selected != null && selected.getDataod() != null) {
            LocalDate dataur = LocalDate.parse(dataurodzenia);
            LocalDate dataumowy = LocalDate.parse(selected.getDataod());
            String rok = Data.getRok(selected.getDataod());
            String pierwszydzienroku = rok + "-01-01";
            LocalDate dataroku = LocalDate.parse(pierwszydzienroku);
            long lata = ChronoUnit.YEARS.between(dataur, dataumowy);
            long dni = ChronoUnit.DAYS.between(dataroku, dataumowy);
            selected.setLata((int) lata);
            selected.setDni((int) dni);
        }
    }

    public DualListModel<Angaz> getListaumowy() {
        return listaumowy;
    }

    public void setListaumowy(DualListModel<Angaz> listaumowy) {
        this.listaumowy = listaumowy;
    }

    public Umowa getSelected() {
        return selected;
    }

    public void setSelected(Umowa selected) {
        this.selected = selected;
    }

    public List<Umowakodzus> getListaumowakodzus() {
        return listaumowakodzus;
    }

    public void setListaumowakodzus(List<Umowakodzus> listaumowakodzus) {
        this.listaumowakodzus = listaumowakodzus;
    }

    public double getWynagrodzeniemiesieczne() {
        return wynagrodzeniemiesieczne;
    }

    public void setWynagrodzeniemiesieczne(double wynagrodzeniemiesieczne) {
        this.wynagrodzeniemiesieczne = wynagrodzeniemiesieczne;
    }

    public double getWynagrodzeniegodzinowe() {
        return wynagrodzeniegodzinowe;
    }

    public void setWynagrodzeniegodzinowe(double wynagrodzeniegodzinowe) {
        this.wynagrodzeniegodzinowe = wynagrodzeniegodzinowe;
    }
    
    
    
    
}
