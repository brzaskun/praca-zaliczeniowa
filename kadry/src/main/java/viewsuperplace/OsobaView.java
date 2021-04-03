/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewsuperplace;

import DAOsuperplace.OsobaFacade;
import dao.AngazFacade;
import dao.PracownikFacade;
import dao.SlownikszkolazatrhistoriaFacade;
import dao.SlownikwypowiedzenieumowyFacade;
import dao.UmowaFacade;
import data.Data;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Pracownik;
import entity.Slownikszkolazatrhistoria;
import entity.Slownikwypowiedzenieumowy;
import entity.Umowa;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import kadryiplace.Osoba;
import kadryiplace.OsobaPrz;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class OsobaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private OsobaFacade osobaFacade;
    @Inject
    private WpisView wpisView;
    @Inject
    private SlownikszkolazatrhistoriaFacade  slownikszkolazatrhistoriaFacade;
    @Inject
    private SlownikwypowiedzenieumowyFacade  slownikwypowiedzenieumowyFacade;
    @Inject
    private PracownikFacade pracownikFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    
    
    
    public void rob() {
        //List<Osoba> podatnicy = osobaFacade.findAll();
        Osoba osoba = osobaFacade.findByPesel("83020610048");
        Pracownik pracownik = OsobaBean.pobierzOsobaBasic(osoba);
        pracownikFacade.create(pracownik);
        FirmaKadry firma = wpisView.getFirma();
        Angaz angaz = OsobaBean.nowyangaz(pracownik,firma);
        angazFacade.create(angaz);
        List<Slownikszkolazatrhistoria> rodzajezatr = slownikszkolazatrhistoriaFacade.findAll();
        List<Slownikwypowiedzenieumowy> rodzajewypowiedzenia = slownikwypowiedzenieumowyFacade.findAll();
        List<Umowa> umowy = OsobaBean.pobierzumowy(osoba, angaz, rodzajezatr, rodzajewypowiedzenia);
        umowaFacade.createList(umowy);
//        
//        List<StanHist> stanHist = osoba.getStanHistList();
//        Short formawynagrodzenia = osoba.getOsoWynForma();
//        //Formyzap p = Formyzap.get(formawynagrodzenia);
//        for (StanHist r : stanHist) {
//            try {
//                //typ P - tenpracodawca, I inny prac, E-edukacja
//                String stanowisko = Data.data_yyyyMMdd(r.getSthDataOd())+" "+Data.data_yyyyMMdd(r.getSthDataDo())+" "+r.getSthStaSerial().getStaNazwa();
//                System.out.println(stanowisko);
//            } catch (Exception e){}
//        }
//        List<WymiarHist> wymiarHist = osoba.getWymiarHistList();
//        for (WymiarHist r : wymiarHist) {
//            try {
//                //typ P - tenpracodawca, I inny prac, E-edukacja
//                String etat = Data.data_yyyyMMdd(r.getWehDataOd())+" "+Data.data_yyyyMMdd(r.getWehDataDo())+" "+r.getWehEtat1()+"/"+r.getWehEtat2()+" "+r.getWehStatus();
//                System.out.println(etat);
//            } catch (Exception e){}
//        }
//        //ubezpieczenia u danej osoby
//        List<OsobaDet> osobaDet = osoba.getOsobaDetList();
//        for (OsobaDet r : osobaDet) {
//            try {
//                //typ P - tenpracodawca, I inny prac, E-edukacja
//                String ubezp = r.getOsdEmerProc()+" "+r.getOsdChorProc();
//                System.out.println(ubezp);
//            } catch (Exception e){}
//        }
//        String imienaziwko = osoba.getOsoNazwisko()+" "+osoba.getOsoImie1();
//        System.out.println(imienaziwko);
//        String firmanazwa = osoba.getOsoFirSerial().getFirNazwaPel();
//        String firBiuroNip = osoba.getOsoFirSerial().getFirBiuroNip();
//        System.out.println(firBiuroNip);
//        String osoKonto = osoba.getOsoKonto(); 
//        List<OsobaPrz> osobaPrzList = osoba.getOsobaPrzList();
//        for (OsobaPrz r : osobaPrzList) {
//            try {
//                String przerwa = Data.data_yyyyMMdd(r.getOspDataOd())+" "+Data.data_yyyyMMdd(r.getOspDataDo())+" "+r.getOspAbsSerial().getAbsOpis()+" "+r.getOspAbsSerial().getAbsKod();
//                System.out.println(przerwa);
//            } catch (Exception e){}
//        }
//        List<Place> placeList = osoba.getPlaceList();
//         for (Place r : placeList) {
//            try {
//                String przerwa = Data.data_yyyyMMdd(r.getLplDataWyplaty())+" "+r.getLplPdstZus()+" "+r.getLplPodDoch()+" "+r.getLplPit4();
//                System.out.println(przerwa);
//            } catch (Exception e){}
//        }
//         List<PlacePrz> placeprz = osoba.getPlacePrzList();
//         for (PlacePrz r : placeprz) {
//            try {
//                String przerwa = Data.data_yyyyMMdd(r.getPrzDataOd())+" "+Data.data_yyyyMMdd(r.getPrzDataDo())+" "+r.getPrzAbsencja()+" "+r.getPrzWyp()+" "+r.getPrzChorWyp();
//                System.out.println(przerwa);
//            } catch (Exception e){}
//        }
        Msg.msg("Pracownik pobrany");
        System.out.println("");
    }
    
    
     public static void main(String[] args)  {
        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("WebApplication1PU");
        EntityManager emH2 = emfH2.createEntityManager();
        List<Osoba> podatnicy = emH2.createQuery("SELECT o FROM Osoba o JOIN o.osoFirSerial d WHERE d.firSerial = 51").getResultList();
        int i = 0;
        for (Osoba o :podatnicy) {
            String wynik = o.getOsoNazwisko()+" "+o.getOsoImie1();
            System.out.println(wynik);
            String zapyt = "SELECT o FROM OsobaPrz o JOIN o.ospOsoSerial d WHERE d.osoSerial = "+o.getOsoSerial();
            List<OsobaPrz> przerwy = emH2.createQuery(zapyt).getResultList();
            int j = 0;
            for (OsobaPrz pr :przerwy) {
                String wynik2 = Data.data_yyyyMMdd(pr.getOspDataOd())+" "+Data.data_yyyyMMdd(pr.getOspDataDo())+" kwota "+pr.getOspKwota().toString();
                System.out.println(wynik2);
                j++;
                if (j>20) {
                    break;
                }
            }
//            for (Place r : o.getPlaceList()) {
//                System.out.println(r.getLplDniObow());
//                System.out.println(r.getLplDniPrzepr());
//            }
            i++;
            if (i>20) {
                break;
            }
        }
        List<OsobaPrz> przerwy = emH2.createQuery("SELECT o FROM OsobaPrz o").getResultList();
        i = 0;
        for (OsobaPrz o :przerwy) {
            String wynik = o.getOspDataOd().toString()+" "+o.getOspDataDo().toString()+" kwota "+o.getOspKwota().toString();
            System.out.println(wynik);
            i++;
            if (i>20) {
                break;
            }
        }
     }
        
//        for (Fakturywystokresowe f: faktury) {
//            //String query = "SELECT d FROM Faktura d WHERE d.fakturaPK.numerkolejny='"+f.getDokument().getFakturaPK().getNumerkolejny()+"' AND d.fakturaPK.wystawcanazwa='"+f.getDokument().getFakturaPK().getWystawcanazwa()+"'";
//            //Faktura faktura = (Faktura) emH2.createQuery(query).getSingleResult();
//            //f.setFa_id(faktura.getId());
//            emH2.merge(f);
//        }
//        for (Osoba p :podatnicy) {
//            emH2.getTransaction().begin();
//            List<Dokfk> dokfk =  emH2.createQuery("SELECT o FROM Dokfk o WHERE o.podatnikObj =:podatnik AND o.rok =:rok").setParameter("podatnik", p).setParameter("rok", "2020").getResultList();
////            List<Rodzajedok> rodzajedok = emH2.createQuery("SELECT o FROM Rodzajedok o WHERE o.podatnikObj =:podatnik AND o.rok =:rok").setParameter("podatnik", p).setParameter("rok", 2019).getResultList();
////            if (dokfk!=null && !dokfk.isEmpty() && rodzajedok!=null && !rodzajedok.isEmpty()) {
////                for (Dokfk s : dokfk) {
////                    naniesrodzaj(s,rodzajedok);
////                    emH2.merge(s);
////                }
////                System.out.println("podatnik "+p.getPrintnazwa());
////            }
//        if (dokfk.size()>0) {
//        }
//            emH2.getTransaction().commit();
//        }
//    }

    
}
