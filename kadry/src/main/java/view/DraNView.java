/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.PasekwynagrodzenBean;
import comparator.Defnicjalistaplaccomparator;
import comparator.PasekwynagrodzenNazwiskacomparator;
import dao.DefinicjalistaplacFacade;
import dao.NieobecnoscFacade;
import dao.PasekwynagrodzenFacade;
import dao.SMTPSettingsFacade;
import data.Data;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.SMTPSettings;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdf.PdfDRA;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DraNView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Pasekwynagrodzen selected;
    @Inject
    private Pasekwynagrodzen selectedlista;
    private List<Definicjalistaplac> listywybrane;
    private List<Pasekwynagrodzen> paskiwynagrodzen;
    private List<Definicjalistaplac> listadefinicjalistaplac;
    @Inject
    private DefinicjalistaplacFacade definicjalistaplacFacade;
     @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
     @Inject
    private WpisView wpisView;
    private double brutto;
    private double bruttopraca;
    private double bruttozlecenia;
    private double netto;
    private double zus51;
    private double zus51pracownik;
    private double zus51pracodawca;
    private double zus52;
    private double zusFP;
    private double zusFGSP;
    private double zus53;
    private double zus;
    private double pit4;
    private double pit8AR;
    private double pit4N;
    private double potraceniaKomornik;
    private double potraceniaZaliczki;
    private double potraceniaPPK;
    private double potraceniaPozostale;
    private String mcdra;
    private List<Nieobecnosc> listanieobecnosci;
     private boolean dialogOtwarty;
    
    public void open() {
        dialogOtwarty = true;
    }
    public void close() {
        dialogOtwarty = false;
    }
    
    public void reloadDialog() {
        boolean zwrot = false;
        if (dialogOtwarty) {
            init();
        }
    }
    
    public void init() {
        mcdra = wpisView.getMiesiacWpisu();
        pobierzlisty();
        Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
    }

    public void pobierzlisty() {
        if (mcdra != null) {
            listadefinicjalistaplac = definicjalistaplacFacade.findByFirmaRokMc(wpisView.getFirma(), wpisView.getRokWpisu(), mcdra);
            if (listadefinicjalistaplac.isEmpty()==false) {
                for (Iterator<Definicjalistaplac> it = listadefinicjalistaplac.iterator(); it.hasNext();) {
                    Definicjalistaplac d = it.next();
                    if (d.getPasekwynagrodzenList() == null ||d.getPasekwynagrodzenList().isEmpty()) {
                        it.remove();
                    }
                }
                Collections.sort(listadefinicjalistaplac, new Defnicjalistaplaccomparator());
                listywybrane = listadefinicjalistaplac;
                Msg.msg("Pobrano listy płac");
                pobierzpaski();
                List<Angaz> angazelista = pobierzangaze(paskiwynagrodzen);
                listanieobecnosci = new ArrayList<>();
                if (angazelista!=null) {
                    for (Angaz angaz : angazelista) {
                        List<Nieobecnosc> nieobecnoscilista = nieobecnoscFacade.findByAngaz(angaz);
                        if (nieobecnoscilista!=null&&nieobecnoscilista.size()>0) {
                            listanieobecnosci.addAll(pobierznieobecnosci(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), nieobecnoscilista));
                        }
                    }
                }
            }
        }
    }
    
    private static List<Nieobecnosc> pobierznieobecnosci(String rok, String mc, List<Nieobecnosc> nieobecnosci) {
        boolean jestod = false;
        boolean jestdo = false;
        List<Nieobecnosc> zwrot = new ArrayList<>();
        for (Nieobecnosc p : nieobecnosci) {
            jestod = Data.czydatajestwmcu(p.getDataod(), rok, mc);
            jestdo = Data.czydatajestwmcu(p.getDatado(), rok, mc);
            if ((jestod || jestdo) && p.isNaniesiona()) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }
    
    private List<Angaz> pobierzangaze(List<Pasekwynagrodzen> paskiwynagrodzen) {
        Set<Angaz> angaze = new HashSet<>();
        if (paskiwynagrodzen!=null) {
            for (Pasekwynagrodzen pas : paskiwynagrodzen) {
               angaze.add(pas.getAngaz());
            }
        }
        return new ArrayList<>(angaze);
    }
    
    public void drukujliste () {
        if (paskiwynagrodzen!=null && paskiwynagrodzen.size()>0) {
            try {
                Map<String,Double> danezus = new HashMap<>();
                danezus.put("zus51", zus51);
                danezus.put("zus51pracownik", zus51pracownik);
                danezus.put("zus51pracodawca", zus51pracodawca);
                danezus.put("zus52", zus52);
                danezus.put("zusFP", zusFP);
                danezus.put("zusFGSP", zusFGSP);
                danezus.put("zus53", zus53);
                danezus.put("zus", zus);
                danezus.put("pit4", pit4);
                danezus.put("pit4N", pit4N);
                danezus.put("pit8AR", pit8AR);
                danezus.put("brutto", brutto);
                danezus.put("bruttopraca", bruttopraca);
                danezus.put("bruttozlecenia", bruttozlecenia);
                danezus.put("netto", netto);
                danezus.put("potraceniaKomornik", potraceniaKomornik);
                danezus.put("potraceniaPPK", potraceniaPPK);
                danezus.put("potraceniaZaliczki", potraceniaZaliczki);
                danezus.put("potraceniaPozostale", potraceniaPozostale);
                ByteArrayOutputStream dra = PdfDRA.drukujListaPodstawowa(paskiwynagrodzen, listywybrane, listanieobecnosci, wpisView.getFirma().getNip(), mcdra, danezus, wpisView.getFirma().getNazwa(), wpisView.getFirma());
                mailListaDRA(dra.toByteArray());
                Msg.msg("Wydrukowano listę płac");
            } catch (Exception e) {
                Msg.msg("e","Błąd drukowania listy");
            }
        } else {
            Msg.msg("e","Błąd drukowania. Brak pasków");
        }
    }
    
    private void mailListaDRA(byte[] dra) {
        if (dra != null && dra.length > 0) {
            SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
            findSprawaByDef.setUseremail(wpisView.getUzer().getEmail());
            findSprawaByDef.setPassword(wpisView.getUzer().getEmailhaslo());
            String nazwa = wpisView.getFirma().getNip() + "_DRA" + wpisView.getRokWpisu()+ wpisView.getMiesiacWpisu() + "_" + ".pdf";
            mail.Mail.mailDRA(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(),  wpisView.getUzer().getEmail(), null, findSprawaByDef, dra, nazwa,"info@taxman.biz.pl");
            Msg.msg("Wysłano listę płac do pracodawcy");
        } else {
            Msg.msg("e", "Błąd dwysyki DRA");
        }
    }
    
    
     public void mailListaDRAFirma() {
         if (paskiwynagrodzen!=null && paskiwynagrodzen.size()>0) {
            try {
                Map<String,Double> danezus = new HashMap<>();
                danezus.put("zus51", zus51);
                danezus.put("zus51pracownik", zus51pracownik);
                danezus.put("zus51pracodawca", zus51pracodawca);
                danezus.put("zus52", zus52);
                danezus.put("zusFP", zusFP);
                danezus.put("zusFGSP", zusFGSP);
                danezus.put("zus53", zus53);
                danezus.put("zus", zus);
                danezus.put("pit4", pit4);
                danezus.put("pit4N", pit4N);
                danezus.put("pit8AR", pit8AR);
                danezus.put("brutto", brutto);
                danezus.put("bruttopraca", bruttopraca);
                danezus.put("bruttozlecenia", bruttozlecenia);
                danezus.put("netto", netto);
                danezus.put("potraceniaKomornik", potraceniaKomornik);
                danezus.put("potraceniaPPK", potraceniaPPK);
                danezus.put("potraceniaZaliczki", potraceniaZaliczki);
                danezus.put("potraceniaPozostale", potraceniaPozostale);
                ByteArrayOutputStream drastream = PdfDRA.drukujListaPodstawowa(paskiwynagrodzen, listywybrane, listanieobecnosci, wpisView.getFirma().getNip(), mcdra, danezus, wpisView.getFirma().getNazwa(), wpisView.getFirma());
                 byte[] dra = drastream.toByteArray();
                if (dra != null && dra.length > 0) {
                    SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                    findSprawaByDef.setUseremail(wpisView.getUzer().getEmail());
                    findSprawaByDef.setPassword(wpisView.getUzer().getEmailhaslo());
                    String nazwa = wpisView.getFirma().getNip() + "_DRA" + wpisView.getRokWpisu()+ wpisView.getMiesiacWpisu() + "_" + ".pdf";
                    mail.Mail.mailDRA(wpisView.getFirma(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), wpisView.getFirma().getEmail(), null, findSprawaByDef, dra, nazwa, wpisView.getUzer().getEmail());
                    Msg.msg("Wysłano listę płac do pracodawcy");
                } else {
                    Msg.msg("e", "Błąd dwysyki DRA");
                }
                Msg.msg("Wydrukowano listę płac");
            } catch (Exception e){}
        } else {
            Msg.msg("e","Błąd drukowania. Brak pasków");
        }
    }

    public void pobierzpaski() {
        if (listywybrane!=null) {
            brutto=0.0;
            bruttopraca=0.0;
            bruttozlecenia=0.0;
            netto=0.0;
            zus51 = 0.0;
            zus51pracodawca = 0.0;
            zus51pracownik = 0.0;
            zus52 = 0.0;
            zusFP = 0.0;
            zusFGSP = 0.0;
            zus53 = 0.0;
            pit4 = 0.0;
            pit4N = 0.0;
            pit8AR = 0.0;
            zus = 0.0;
            potraceniaKomornik = 0.0;
            potraceniaPPK = 0.0;
            potraceniaZaliczki = 0.0;
            potraceniaPozostale = 0.0;
            paskiwynagrodzen = new ArrayList<>();
             double sumapotracen =0.0;
            if (listywybrane.isEmpty()==false) {
                for (Definicjalistaplac d : listywybrane) {
                    List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByDef(d);
                    if (paski!=null) {
                        paskiwynagrodzen.addAll(paski);
                    }
                }
                if (paskiwynagrodzen.isEmpty()==false) {
                    Collections.sort(paskiwynagrodzen, new PasekwynagrodzenNazwiskacomparator());
                   
                    for (Pasekwynagrodzen p : paskiwynagrodzen) {
                        zus51pracownik = Z.z(zus51pracownik+p.getRazemspolecznepracownik());
                        zus51pracodawca = Z.z(zus51pracodawca+p.getRazemspolecznefirma());
                        zus51 = Z.z(zus51+p.getRazemspolecznepracownik()+p.getRazemspolecznefirma());
                        zus52 = Z.z(zus52+p.getPraczdrowotnedopotracenia());
                        zusFP = Z.z(zusFP+p.getFp());
                        zusFGSP = Z.z(zusFGSP+p.getFgsp());
                        zus53 = Z.z(zus53+p.getRazem53());
                        if (p.getAngaz().getPracownik().isNierezydent()) {
                            pit8AR = Z.z(pit8AR+p.getPodatekdochodowy());
                        } else {
                            pit4 = Z.z(pit4+p.getPodatekdochodowy());
                        }
                        pit4N = Z.z(pit4N+p.getPodatekdochodowyzagranicawaluta());
                        brutto = Z.z(brutto+p.getBrutto());
                        if (p.getDefinicjalistaplac().getRodzajlistyplac().getTyp()==1) {
                            bruttopraca = Z.z(bruttopraca+p.getBrutto());
                        } else {
                            bruttozlecenia = Z.z(bruttozlecenia+p.getBrutto());
                        }
                        netto = Z.z(netto+p.getNetto());
                        potraceniaKomornik = Z.z(potraceniaKomornik+p.getPotraceniaKomornik());
                        potraceniaZaliczki = Z.z(potraceniaZaliczki+p.getPotraceniaZaliczki());
                        potraceniaPPK = Z.z(potraceniaPPK+p.getPotraceniaPPK());
                        sumapotracen = Z.z(sumapotracen + p.getPotracenia());
                    }
                    paskiwynagrodzen.add(PasekwynagrodzenBean.sumujpaski(paskiwynagrodzen));
                    zus = Z.z(zus+zus51+zus52+zus53);
                    Msg.msg("Pobrano paski do DRA");
                }
                potraceniaPozostale = Z.z(sumapotracen -potraceniaKomornik-potraceniaZaliczki-potraceniaPPK);
            }
        } else {
            Msg.msg("e","Błąd pobierania pasków");
        }
    }

    
    public Pasekwynagrodzen getSelected() {
        return selected;
    }

    public void setSelected(Pasekwynagrodzen selected) {
        this.selected = selected;
    }

    public List<Pasekwynagrodzen> getPaskiwynagrodzen() {
        return paskiwynagrodzen;
    }

    public void setPaskiwynagrodzen(List<Pasekwynagrodzen> paskiwynagrodzen) {
        this.paskiwynagrodzen = paskiwynagrodzen;
    }

    public List<Definicjalistaplac> getListadefinicjalistaplac() {
        return listadefinicjalistaplac;
    }

    public void setListadefinicjalistaplac(List<Definicjalistaplac> listadefinicjalistaplac) {
        this.listadefinicjalistaplac = listadefinicjalistaplac;
    }

    public Pasekwynagrodzen getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Pasekwynagrodzen selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Definicjalistaplac> getListywybrane() {
        return listywybrane;
    }

    public void setListywybrane(List<Definicjalistaplac> listywybrane) {
        this.listywybrane = listywybrane;
    }

 

    public double getZus51() {
        return zus51;
    }

    public void setZus51(double zus51) {
        this.zus51 = zus51;
    }

    public double getZus52() {
        return zus52;
    }

    public void setZus52(double zus52) {
        this.zus52 = zus52;
    }

    public double getZus53() {
        return zus53;
    }

    public double getZus51pracownik() {
        return zus51pracownik;
    }

    public void setZus51pracownik(double zus51pracownik) {
        this.zus51pracownik = zus51pracownik;
    }

    public double getZus51pracodawca() {
        return zus51pracodawca;
    }

    public void setZus51pracodawca(double zus51pracodawca) {
        this.zus51pracodawca = zus51pracodawca;
    }

    public double getZusFP() {
        return zusFP;
    }

    public void setZusFP(double zusFP) {
        this.zusFP = zusFP;
    }

    public double getZusFGSP() {
        return zusFGSP;
    }

    public void setZusFGSP(double zusFGSP) {
        this.zusFGSP = zusFGSP;
    }

    
    public void setZus53(double zus53) {
        this.zus53 = zus53;
    }

    public double getZus() {
        return zus;
    }

    public void setZus(double zus) {
        this.zus = zus;
    }

    public double getPit4() {
        return pit4;
    }

    public void setPit4(double pit4) {
        this.pit4 = pit4;
    }

    public double getPit8AR() {
        return pit8AR;
    }

    public void setPit8AR(double pit8AR) {
        this.pit8AR = pit8AR;
    }

    public double getPit4N() {
        return pit4N;
    }

    public void setPit4N(double pit4N) {
        this.pit4N = pit4N;
    }

    
    public String getMcdra() {
        return mcdra;
    }

    public void setMcdra(String mcdra) {
        this.mcdra = mcdra;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    

    

   
    
}
