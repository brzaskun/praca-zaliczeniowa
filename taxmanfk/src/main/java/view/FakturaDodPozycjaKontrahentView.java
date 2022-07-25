/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Klienci1comparator;
import dao.FakturaDodPozycjaKontrahentDAO;
import dao.FakturaDodatkowaPozycjaDAO;
import dao.FakturywystokresoweDAO;
import dao.PodatnikDAO;
import data.Data;
import entity.Faktura;
import entity.FakturaDodPozycjaKontrahent;
import entity.FakturaDodatkowaPozycja;
import entity.Fakturywystokresowe;
import entity.Klienci;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturaDodPozycjaKontrahentView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FakturaDodPozycjaKontrahentDAO fakturaDodPozycjaKontrahentDAO;
    @Inject
    private FakturaDodatkowaPozycjaDAO fakturaDodatkowaPozycjaDAO;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Klienci> klienci;
    private List<FakturaDodatkowaPozycja> pozycje;
    private List<FakturaDodPozycjaKontrahent> lista_wzor;
    private List<FakturaDodPozycjaKontrahent> lista_2;
    private List<FakturaDodPozycjaKontrahent> lista_2_filter;
    private List<FakturaDodPozycjaKontrahent> lista_2_selected;
    @Inject
    private FakturaDodPozycjaKontrahent selected;
    private String rok;
    private String mc;
    private double sumawybranych;
    private double sumawybranych2;
    private boolean pokazujtylkopuste;
    private List<Fakturywystokresowe> wykazfaktur;

    
    @PostConstruct
    private void init() {
        pozycje = fakturaDodatkowaPozycjaDAO.findAll();
        lista_wzor = fakturaDodPozycjaKontrahentDAO.findByRok(Data.aktualnyRok());
        lista_2 = new ArrayList<>();
        rok = Data.aktualnyRok();
        klienci = new ArrayList<>();
        wykazfaktur = fakturywystokresoweDAO.findPodatnikBiezace("GRZELCZYK", rok);
        for (Fakturywystokresowe p : wykazfaktur) {
            Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getDokument().getKontrahent().getNip());
            Klienci k = p.getDokument().getKontrahent();
            if (pod != null) {
                k.setJezykwysylki(pod.getJezykmaila());
                k.setNazwapodatnika(pod.getPrintnazwa());
            } else {
                k.setNazwapodatnika(k.getNazwabezCudzy());
            }
            klienci.add(k);
        }
        Collections.sort(klienci, new Klienci1comparator());
    }

    public void pobierzklientow() {
        if (mc != null && rok != null) {
            pozycje = fakturaDodatkowaPozycjaDAO.findAll();
            if (rok != null && mc != null) {
                lista_2_filter = null;
                lista_2_selected = null;
                lista_wzor = fakturaDodPozycjaKontrahentDAO.findByRok(rok);
                List<FakturaDodPozycjaKontrahent> lista_tmp = lista_wzor.stream().filter(p -> p.getRok().equals(rok) && p.getMc().equals(mc)).collect(Collectors.toList());
                for (FakturaDodPozycjaKontrahent p : lista_tmp) {
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getKontrahent().getNip());
                    if (pod != null) {
                        p.getKontrahent().setNazwapodatnika(pod.getPrintnazwa());
                    }
                }
                if (pokazujtylkopuste) {
                    lista_2 = lista_2.stream().filter(p->p.getIlosc()==0).collect(Collectors.toList());
                }
                uzupelnijofakture(lista_tmp);
                lista_2 = new ArrayList<>();
                lista_2.addAll(lista_tmp);
                Msg.msg("Pobrano stałe pozycje");
            }
        }
    }
    
    public void dodaj() {
        if (selected.getKontrahent()!=null && selected.getFakturaDodatkowaPozycja() !=null) {
            try {
                selected.setRok(rok);
                selected.setMc(mc);
                fakturaDodPozycjaKontrahentDAO.create(selected);
                lista_2.add(selected);
                lista_2_filter = null;
                lista_2_selected = null;
                selected = new FakturaDodPozycjaKontrahent();
                Msg.msg("Zapisano nową pozycję");
            } catch (Exception e) {
                Msg.msg("e","Taka nazwa już istnieje");
            }
        } else {
            Msg.msg("e","Nie wprowadzono kontrahenta/pozycji. Nie można zapisać");
        }
    }
    
    public void usun(FakturaDodPozycjaKontrahent sel) {
        if (sel !=null) {
            try {
                fakturaDodPozycjaKontrahentDAO.remove(sel);
                lista_2.remove(sel);
                Msg.msg("Usunięto pozycję");
            } catch (Exception e) {
                Msg.msg("e","Nieudane usunięcie pozycji");
            }
        } else {
            Msg.msg("e","Nie wybrano pozycji. Nie można usunąć");
        }
    }
    
    public void zeruj() {
        if (lista_2_selected!=null && lista_2_selected.size()==1) {
            try {
                FakturaDodPozycjaKontrahent sel = lista_2_selected.get(0);
                sel.setRozliczone(false);
                fakturaDodPozycjaKontrahentDAO.edit(sel);
                Msg.msg("Wyzerowano pozycję");
            } catch (Exception e) {
                Msg.msg("e","Nieudane zerowanie pozycji");
            }
        } else {
            Msg.msg("e","Nie wybrano pozycji. Nie można usunąć");
        }
    }
    
    public void edytuj(FakturaDodPozycjaKontrahent sel, int co) {
        if (sel !=null) {
            try {
                fakturaDodPozycjaKontrahentDAO.edit(sel);
                if (co==1) {
                    Msg.msg("Zmieniono ilość");
                } else if (co==2) {
                    Msg.msg("Zmieniono kwotę");
                } else if (co==3) {
                    Msg.msg("Zmieniono permanentne");
                }
            } catch (Exception e) {
                Msg.msg("e","Nieudana zmiana danych");
            }
        } else {
            Msg.msg("e","Nie wybrano pozycji. Nie można wyedytować");
        }
    }
    
    public void sumujwybrane() {
        List<FakturaDodPozycjaKontrahent> lista = lista_2_filter!=null && lista_2_filter.size()>0 ? lista_2_filter : lista_2;
        sumawybranych = 0.0;
        for (FakturaDodPozycjaKontrahent p : lista) {
            if (p.getKwotaindywid()!=0.0) {
                sumawybranych = Z.z(sumawybranych+p.getKwotaindywid()*p.getIlosc());
            } else {
                sumawybranych = Z.z(sumawybranych+p.getFakturaDodatkowaPozycja().getKwota()*p.getIlosc());
            }
            sumawybranych2 = sumawybranych2+p.getIlosc();
        }
        Msg.msg("Podsumowano");
    }
    
    public void generujpermanentne() {
        if (rok!=null) {
            lista_2_filter = null;
            lista_2_selected = null;
            lista_wzor = fakturaDodPozycjaKontrahentDAO.findByRok(rok);
            if (lista_wzor==null||lista_wzor.isEmpty()) {
                String rokuprzedni = String.valueOf(Integer.parseInt(rok)-1);
                lista_wzor = fakturaDodPozycjaKontrahentDAO.findByRok(rokuprzedni);
            }
            if (rok!=null&&mc!=null) {
               String[] okrespop = Data.poprzedniOkres(mc, rok);
               List<FakturaDodPozycjaKontrahent> lista_tmp = lista_wzor.stream().filter(p->p.getRok().equals(okrespop[1])&&p.getMc().equals(okrespop[0])).collect(Collectors.toList());
               if (lista_tmp.isEmpty()) {
                   Msg.msg("e", "Brak pozycji stałych");
               } else {
                   if (lista_2==null) {
                       lista_2 = new ArrayList<>();
                   }
                   for (FakturaDodPozycjaKontrahent p : lista_tmp) {
                       FakturaDodPozycjaKontrahent r = new FakturaDodPozycjaKontrahent(p, rok, mc);
                       dodajpozycje(r,lista_2);
                       Podatnik pod = podatnikDAO.findPodatnikByNIP(r.getKontrahent().getNip());
                        if (pod != null) {
                            r.getKontrahent().setNazwapodatnika(pod.getPrintnazwa().replace("\"", ""));
                        }
                   }
                   uzupelnijofakture(lista_tmp);
                   if (pokazujtylkopuste) {
                       lista_2 = lista_2.stream().filter(p->p.getIlosc()==0).collect(Collectors.toList());
                   }
                   Msg.msg("Pobrano stałe pozycje");
               }
           }
        } else {
            Msg.msg("e","Nie wybrano roku");
        }
    }
    
    private void dodajpozycje(FakturaDodPozycjaKontrahent r, List<FakturaDodPozycjaKontrahent> lista_2) {
        boolean dodac = true;
        for (FakturaDodPozycjaKontrahent p : lista_2) {
            if (r.getKontrahent().equals(p.getKontrahent())&&r.getFakturaDodatkowaPozycja().equals(p.getFakturaDodatkowaPozycja())) {
                dodac = false;
                break;
            }
        }
        if (dodac) {
            lista_2.add(r);
        }
    }
    
    
    public void zachowajpermanentne() {
        if (lista_2!=null&& !lista_2.isEmpty()) {
            fakturaDodPozycjaKontrahentDAO.createEditList(lista_2);
            Msg.msg("Zachowano pozycje");
        } else {
            Msg.msg("e","Lista pusta");
        }
    }
    
    private void uzupelnijofakture(List<FakturaDodPozycjaKontrahent> lista_tmp) {
        for (FakturaDodPozycjaKontrahent p : lista_tmp) {
            p.setBrakfaktury(true);
            p.setBrakfakturydoedycji(true);
            for (Fakturywystokresowe r : wykazfaktur) {
                Faktura f = r.getDokument();
                if (f.getKontrahent().getNip().equals(p.getKontrahent().getNip())) {
                    p.setBrakfaktury(false);
                    if (r.isRecznaedycja()==true) {
                        p.setBrakfakturydoedycji(false);
                    }
                    break;
                }
            }
        }
    }

    public FakturaDodPozycjaKontrahent getSelected() {
        return selected;
    }

    public void setSelected(FakturaDodPozycjaKontrahent selected) {
        this.selected = selected;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<Klienci> getKlienci() {
        return klienci;
    }

    public void setKlienci(List<Klienci> klienci) {
        this.klienci = klienci;
    }

    public List<FakturaDodatkowaPozycja> getPozycje() {
        return pozycje;
    }

    public void setPozycje(List<FakturaDodatkowaPozycja> pozycje) {
        this.pozycje = pozycje;
    }

    public List<FakturaDodPozycjaKontrahent> getLista_2_selected() {
        return lista_2_selected;
    }

    public void setLista_2_selected(List<FakturaDodPozycjaKontrahent> lista_2_selected) {
        this.lista_2_selected = lista_2_selected;
    }

    public List<FakturaDodPozycjaKontrahent> getLista_2() {
        return lista_2;
    }

    public void setLista_2(List<FakturaDodPozycjaKontrahent> lista_2) {
        this.lista_2 = lista_2;
    }

    public List<FakturaDodPozycjaKontrahent> getLista_2_filter() {
        return lista_2_filter;
    }

    public void setLista_2_filter(List<FakturaDodPozycjaKontrahent> lista_2_filter) {
        this.lista_2_filter = lista_2_filter;
    }

    public double getSumawybranych() {
        return sumawybranych;
    }

    public void setSumawybranych(double sumawybranych) {
        this.sumawybranych = sumawybranych;
    }

    public double getSumawybranych2() {
        return sumawybranych2;
    }

    public void setSumawybranych2(double sumawybranych2) {
        this.sumawybranych2 = sumawybranych2;
    }

    public boolean isPokazujtylkopuste() {
        return pokazujtylkopuste;
    }

    public void setPokazujtylkopuste(boolean pokazujtylkopuste) {
        this.pokazujtylkopuste = pokazujtylkopuste;
    }

    

 
    
    
}
