/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFK.BOFKBean;
import beansPodpis.ObslugaPodpisuBean;
import beansVAT.VATDeklaracja;
import comparator.Vatcomparator;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjevatDAO;
import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import dao.SchemaEwidencjaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.Deklaracjevat;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import mail.MailOther;
import msg.Msg; import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import pdf.PdfVAT7;
import pdf.PdfVAT7new;
import viewfk.SaldoAnalitykaView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DeklaracjevatView implements Serializable {
    private Deklaracjevat selected;
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    private List<Deklaracjevat> wyslane;
    private List<Deklaracjevat> wyslanenormalne;
    private List<Deklaracjevat> wyslaneniepotwierdzone;
    private List<Deklaracjevat> wyslanetestowe;
    private List<Deklaracjevat> wyslanezbledem;
    private List<Deklaracjevat> oczekujace;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value="#{saldoAnalitykaView}")
    private SaldoAnalitykaView saldoAnalitykaView;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    @Inject
    private SchemaEwidencjaDAO schemaEwidencjaDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    private boolean pokazZT;
    private boolean pokazZZ;
    private boolean pokazprzyciskpodpis;
    private double saldo222;
    private boolean wyslijtezjpk;

    public DeklaracjevatView() {
        wyslane = Collections.synchronizedList(new ArrayList<>());
        oczekujace = Collections.synchronizedList(new ArrayList<>());
        wyslanenormalne = Collections.synchronizedList(new ArrayList<>());
        wyslanetestowe = Collections.synchronizedList(new ArrayList<>());
        wyslanezbledem = Collections.synchronizedList(new ArrayList<>());
        wyslaneniepotwierdzone = Collections.synchronizedList(new ArrayList<>());
    }
    
    
    @PostConstruct
    public void init() {
        pokazZT = false;
        pokazZZ = false;
        wyslane = Collections.synchronizedList(new ArrayList<>());
        oczekujace = Collections.synchronizedList(new ArrayList<>());
        wyslanenormalne = Collections.synchronizedList(new ArrayList<>());
        wyslanetestowe = Collections.synchronizedList(new ArrayList<>());
        wyslanezbledem = Collections.synchronizedList(new ArrayList<>());
        wyslaneniepotwierdzone = Collections.synchronizedList(new ArrayList<>());
        List<StronaWiersza> zapisyBO = BOFKBean.pobierzZapisyBO(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        List<StronaWiersza> zapisyObrotyRozp = BOFKBean.pobierzZapisyObrotyRozp(dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        try {
            oczekujace = deklaracjevatDAO.findDeklaracjeDowyslaniaList(wpisView.getPodatnikWpisu());
            if (oczekujace != null && oczekujace.size() == 1) {
                Konto kontoRozrachunkizUS = kontoDAOfk.findKonto("222", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
            if (kontoRozrachunkizUS!=null) {
                List<Konto> konta = new ArrayList<>();
                konta.add(kontoRozrachunkizUS);
                saldoAnalitykaView.przygotowanalistasald(konta, zapisyBO, zapisyObrotyRozp, "wszystkie");
                double saldown = saldoAnalitykaView.getSumaSaldoKonto().get(0).getSaldoWn();
                double saldoma = saldoAnalitykaView.getSumaSaldoKonto().get(0).getSaldoMa();
                saldo222 = saldown != 0.0 ? saldown : -saldoma;
            }
                Deklaracjevat p = oczekujace.get(0);
                DeklaracjaVatSchemaWierszSum narachunek25dni = VATDeklaracja.pobierzschemawiersz(p.getSchemawierszsumarycznylista(),"do zwrotu w terminie 25 dni");
                int kwota25 = narachunek25dni.getDeklaracjaVatWierszSumaryczny().getSumavat();
                if (kwota25 > 0 && p.getVatzt() == null) {
                    pokazZT = true;
                } 
                if (kwota25 > 0 && p.getVatzz() == null) {
                    pokazZZ = true;
                }
                DeklaracjaVatSchemaWierszSum narachunek60dni = VATDeklaracja.pobierzschemawiersz(p.getSchemawierszsumarycznylista(),"do zwrotu w terminie 60 dni");
                DeklaracjaVatSchemaWierszSum narachunek180dni = VATDeklaracja.pobierzschemawiersz(p.getSchemawierszsumarycznylista(),"do zwrotu w terminie 180 dni");
                int kwota60 = narachunek60dni.getDeklaracjaVatWierszSumaryczny().getSumavat();
                int kwota180 = narachunek180dni.getDeklaracjaVatWierszSumaryczny().getSumavat();
                DeklaracjaVatSchemaWierszSum sumasprzedazy = VATDeklaracja.pobierzschemawiersz(p.getSchemawierszsumarycznylista(),"Razem (suma przychodów)");
                int vatkwota = sumasprzedazy.getDeklaracjaVatWierszSumaryczny().getSumavat();
//                if ((kwota60 > 0 || kwota180 > 0) && vatkwota == 0) {
                if ((kwota60 > 0 || kwota180 > 0)  && vatkwota ==0) {
                    pokazZZ = true;
                }
                if (kwota25 > 0 && p.getVatzt() == null) {
                    pokazZT = true;
                }
                if (p.getSchemaobj().getNazwaschemy().equals("M-19") || p.getSchemaobj().getNazwaschemy().equals("K-13")) {
                    pokazZZ = false;
                    pokazZT = false;
                }
            }
        } catch (Exception e) {
            E.e(e);
        }
        try {
            wyslane = deklaracjevatDAO.findDeklaracjeWyslane(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            for (Deklaracjevat p : wyslane) {
                try {
                    if (p.isTestowa()) {
                        wyslanetestowe.add(p);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            for (Deklaracjevat p : wyslane) {
                if (!wyslanetestowe.contains(p)) {
                    if (p.getStatus().startsWith("4")) {
                        wyslanezbledem.add(p);
                    } else if (p.getStatus().startsWith("3") || p.getStatus().startsWith("1")) {
                        wyslaneniepotwierdzone.add(p);
                    } else {
                        wyslanenormalne.add(p);
                    }
                }

            }
        } catch (Exception e) {
            E.e(e);
        }
        pokazprzyciskpodpisfunkcja();
        Collections.sort(wyslanenormalne, new Vatcomparator());
    }
    
    public void podpiszdeklaracje(List<Deklaracjevat> oczekujace) {
        if (!oczekujace.isEmpty()) {
            try {
                Deklaracjevat biezaca = oczekujace.get(0);
                FacesContext context = FacesContext.getCurrentInstance();
                PodpisView podpisView = (PodpisView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"podpisView");
                Object[] deklaracje = podpisView.podpiszDeklaracje(biezaca.getDeklaracja(),wpisView);
                biezaca.setDeklaracjapodpisana((byte[]) deklaracje[0]);
                biezaca.setDeklaracja((String) deklaracje[1]);
                deklaracjevatDAO.edit(biezaca);
                pokazprzyciskpodpisfunkcja();
                Msg.msg("Udało się podpisać deklarację podpisem certyfikowanym");
            } catch (Exception e) {
                Msg.msg("e", "Nie udało się podpisać deklaracji");
            }
        }
    }

     public void edit(RowEditEvent ex) {
        try {
            //sformatuj();
            deklaracjevatDAO.edit(selected);
            FacesMessage msg = new FacesMessage("Nowy dokytkownik edytowany " + ex.getObject().toString(), selected.getPodatnik());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage("Dokytkownik nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
     
   public void destroy(Deklaracjevat selDok) {
        selected = selDok;
        try {
               oczekujace.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Deklaracja nie usunięta");
            }
    }
   
   public void destroywyslane(Deklaracjevat selDok) {
        selected = selDok;
        try {
               wyslanenormalne.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Deklaracja nie usunięta");
            }
    }
   
    public void destroy2() {
         try {
               oczekujace.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Deklaracja nie usunięta");
            }
           
        }
    
    public void destroy2a() {
         try {
               wyslanezbledem.remove(selected);
               wyslanetestowe.remove(selected);
               deklaracjevatDAO.destroy(selected);
                Msg.msg("i","Deklaracja usunięta");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Deklaracja nie usunięta");
            }
           
        }

    public void mailvat7(int row) {
        try {
            MailOther.vat7(row, wpisView, 0, sMTPSettingsDAO.findSprawaByDef());
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    public void mailvat7N(int row) {
        try {
            MailOther.vat7(row, wpisView, 1, sMTPSettingsDAO.findSprawaByDef());
        } catch (Exception e) { E.e(e); 
            
        }
    }
    
    public boolean pokazprzyciskpodpisfunkcja() {
        boolean zwrot = false;
        pokazprzyciskpodpis = false;
        if (oczekujace != null && wpisView.getPodatnikObiekt().isPodpiscertyfikowany()) {
            for (Deklaracjevat d : oczekujace) {
                if (d.getDeklaracjapodpisana()==null) {
                    pokazprzyciskpodpis = ObslugaPodpisuBean.moznaPodpisac(wpisView.getPodatnikObiekt().getKartacert());
                }
            }
        }
        zwrot = pokazprzyciskpodpis;
        return zwrot;
    }
    
    public void drukujprzygotowanedowysylki(Deklaracjevat dkl) {
        try {
            DeklaracjaVatSchema pasujacaSchema = null;
            List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
            for (DeklaracjaVatSchema p : schemyLista) {
                if (p.getNazwaschemy().equals(dkl.getWzorschemy())) {
                    pasujacaSchema = p;
                    break;
                }
            }
            Integer rok = Integer.parseInt(pasujacaSchema.getRokOd());
            Integer mc = Integer.parseInt(pasujacaSchema.getMcOd());
            if (rok <= 2015 && mc <= 7) {
                PdfVAT7.drukujwys(podatnikDAO, dkl);
                String f = "wydrukvat7wysylka('"+dkl.getPodatnik()+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                PdfVAT7new.drukujNowaVAT7(podatnikDAO, dkl, pasujacaSchema, schemaEwidencjaDAO, wpisView);
                String f = "wydrukvat7wysylkaN('"+dkl.getPodatnik()+"');";
                PrimeFaces.current().executeScript(f);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    public void drukujdeklaracje(Deklaracjevat dkl, int wiersz) {
        try {
            DeklaracjaVatSchema pasujacaSchema = null;
            List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
            for (DeklaracjaVatSchema p : schemyLista) {
                if (p.getNazwaschemy().equals(dkl.getWzorschemy())) {
                    pasujacaSchema = p;
                    break;
                }
            }
            Integer rok = Integer.parseInt(pasujacaSchema.getRokOd());
            Integer mc = Integer.parseInt(pasujacaSchema.getMcOd());
            if (rok <= 2015 && mc <= 7) {
                PdfVAT7.drukuj(dkl, wiersz, podatnikDAO);
                String f = "document.getElementById('formwyslane:akordeon:dataList:"+wiersz+":mailbutton').style.display='inline';";
                PrimeFaces.current().executeScript(f);
            } else {
                PdfVAT7new.drukujNowaVAT7(podatnikDAO, dkl, pasujacaSchema, schemaEwidencjaDAO, wpisView);
                String f = "document.getElementById('formwyslane:akordeon:dataList:"+wiersz+":mailbuttonN').style.display='inline';";
                PrimeFaces.current().executeScript(f);
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    public void drukujdeklaracje(Deklaracjevat dkl) {
        try {
            DeklaracjaVatSchema pasujacaSchema = null;
            List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
            for (DeklaracjaVatSchema p : schemyLista) {
                if (p.getNazwaschemy().equals(dkl.getWzorschemy())) {
                    pasujacaSchema = p;
                    break;
                }
            }
            Integer rok = Integer.parseInt(pasujacaSchema.getRokOd());
            Integer mc = Integer.parseInt(pasujacaSchema.getMcOd());
            PdfVAT7new.drukujNowaVAT7(podatnikDAO, dkl, pasujacaSchema, schemaEwidencjaDAO, wpisView);
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    private void aktualizujGuest(){
        wpisView.naniesDaneDoWpis();
    }
     private void aktualizuj(){
        wpisView.naniesDaneDoWpis();
    }
    
     public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }
    
    public void handleSave(ValueChangeEvent e) {
        oczekujace.get(0).setDeklaracja((String)e.getNewValue());
        deklaracjevatDAO.edit(oczekujace.get(0));
        Msg.msg("Zmieniono treść deklaracji");
    }
    
    
    public List<Deklaracjevat> getWyslane() {
        return wyslane;
    }

    public void setWyslane(List<Deklaracjevat> wyslane) {
        this.wyslane = wyslane;
    }

    public List<Deklaracjevat> getOczekujace() {
        return oczekujace;
    }

    public void setOczekujace(List<Deklaracjevat> oczekujace) {
        this.oczekujace = oczekujace;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Deklaracjevat getSelected() {
        return selected;
    }

    public void setSelected(Deklaracjevat selected) {
        this.selected = selected;
    }

    public boolean isPokazZZ() {
        return pokazZZ;
    }

    public void setPokazZZ(boolean pokazZZ) {
        this.pokazZZ = pokazZZ;
    }

    public boolean isPokazprzyciskpodpis() {
        return pokazprzyciskpodpis;
    }

    public void setPokazprzyciskpodpis(boolean pokazprzyciskpodpis) {
        this.pokazprzyciskpodpis = pokazprzyciskpodpis;
    }

   

    public List<Deklaracjevat> getWyslanenormalne() {
        return wyslanenormalne;
    }

    public void setWyslanenormalne(List<Deklaracjevat> wyslanenormalne) {
        this.wyslanenormalne = wyslanenormalne;
    }

    public List<Deklaracjevat> getWyslanetestowe() {
        return wyslanetestowe;
    }

    public void setWyslanetestowe(List<Deklaracjevat> wyslanetestowe) {
        this.wyslanetestowe = wyslanetestowe;
    }

    public List<Deklaracjevat> getWyslanezbledem() {
        return wyslanezbledem;
    }

    public void setWyslanezbledem(List<Deklaracjevat> wyslanezbledem) {
        this.wyslanezbledem = wyslanezbledem;
    }

    public List<Deklaracjevat> getWyslaneniepotwierdzone() {
        return wyslaneniepotwierdzone;
    }

    public void setWyslaneniepotwierdzone(List<Deklaracjevat> wyslaneniepotwierdzone) {
        this.wyslaneniepotwierdzone = wyslaneniepotwierdzone;
    }

    public boolean isPokazZT() {
        return pokazZT;
    }

    public void setPokazZT(boolean pokazZT) {
        this.pokazZT = pokazZT;
    }

    public double getSaldo222() {
        return saldo222;
    }

    public void setSaldo222(double saldo222) {
        this.saldo222 = saldo222;
    }

    public SaldoAnalitykaView getSaldoAnalitykaView() {
        return saldoAnalitykaView;
    }

    public void setSaldoAnalitykaView(SaldoAnalitykaView saldoAnalitykaView) {
        this.saldoAnalitykaView = saldoAnalitykaView;
    }

    public boolean isWyslijtezjpk() {
        return wyslijtezjpk;
    }

    public void setWyslijtezjpk(boolean wyslijtezjpk) {
        this.wyslijtezjpk = wyslijtezjpk;
    }

   
    
    
    
}
