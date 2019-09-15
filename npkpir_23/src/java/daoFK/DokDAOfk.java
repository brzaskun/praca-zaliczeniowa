/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import embeddable.Kwartaly;
import entity.Klienci;
import entity.Podatnik;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named(value="DokDAOfk")

public class DokDAOfk extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade dokFacade;
    
    public DokDAOfk() {
        super(Dokfk.class);
    }

    public DokDAOfk(Class entityClass) {
        super(entityClass);
    }


    public DokDAOfk(SessionFacade dokFacade, Class entityClass) {
        super(entityClass);
        this.dokFacade = dokFacade;
    }
    
//    public void refresh(Dokfk dokfk) {
//        dokFacade.refresh(dokfk);
//    }
        
    public List<Dokfk> findAll(){
        return dokFacade.findAll(Dokfk.class);
    }
    
  
    public void usun(Dokfk selected) {
        dokFacade.remove(selected);
    }

    public List findDokfkPodatnikRokMc(WpisView wpisView) {
        try {
           return Collections.synchronizedList(dokFacade.findDokfkPodatnikRokMc(wpisView));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List findDokfkPodatnikRokMcVAT(WpisView wpisView) {
        try {
           return Collections.synchronizedList(dokFacade.findDokfkPodatnikRokMcVAT(wpisView));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List findDokfkPodatnikRokKw(WpisView wpisView) {
        List<Dokfk> zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
           String mcwpisu = wpisView.getMiesiacWpisu();
           List<String> mcekw = Kwartaly.mctoMcewKw(mcwpisu);
           zwrot =  Collections.synchronizedList(dokFacade.findDokfkPodatnikRokKw(wpisView, mcekw));
       } catch (Exception e ){
           
       }
        return zwrot;
    }
    
    public List<Dokfk> findDokfkPodatnikRokKw(WpisView wpisView, String mcwpisu) {
        List<Dokfk> zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
           List<String> mcekw = Kwartaly.mctoMcewKw(mcwpisu);
           zwrot =  Collections.synchronizedList(dokFacade.findDokfkPodatnikRokKw(wpisView, mcekw));
       } catch (Exception e ){
           
       }
        return zwrot;
    }
    
    public List<Dokfk> findDokfkPodatnikRok(WpisView w) {
        try {
           return Collections.synchronizedList(dokFacade.findDokfkPodatnikRok(w));
       } catch (Exception e ){
           return null;
       }
    }
    
//    public List<Dokfk> findDokfkPodatnikRokUnique(WpisView w) {
//        List<Dokfk> zwrot = new ArrayList<>();
//        try {
//           List<Dokfk> zwrot1 = Collections.synchronizedList(dokFacade.getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokUnique").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
//           List<Dokfk> zwrot2 = Collections.synchronizedList(dokFacade.getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokUnique2").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
//           if (zwrot1!=null) {
//               zwrot.addAll(zwrot1);
//           }
//           if (zwrot2!=null) {
//               zwrot.addAll(zwrot2);
//           }
//       } catch (Exception e ){
//           
//       }
//        return zwrot;
//    }
    
    
    public List<Dokfk> findDokfkPodatnik(WpisView wpisView) {
        try {
           return Collections.synchronizedList(dokFacade.findDokfkPodatnik(wpisView));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkAllRok(String rok) {
        try {
           return sessionFacade.getEntityManager().createNamedQuery("Dokfk.findByRok").setParameter("rok", rok).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
    
     public List<Dokfk> findDokfkPodatnikRok(Podatnik p, String rok) {
        try {
           return sessionFacade.getEntityManager().createNamedQuery("Dokfk.findByPodatnikRok").setParameter("podatnik", p).setParameter("rok", rok).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokSrodkiTrwale(WpisView wpisView) {
        try {
           return Collections.synchronizedList(dokFacade.findDokfkPodatnikRokSrodkiTrwale(wpisView));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokRMK(WpisView wpisView) {
        try {
           return Collections.synchronizedList(dokFacade.findDokfkPodatnikRokRMK(wpisView));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokMcKategoria(WpisView wpisView, String kategoria) {
        try {
           return dokFacade.findDokfkPodatnikRokMcKategoria(wpisView, kategoria);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokKategoria(WpisView wpisView, String kategoria) {
        try {
           return Collections.synchronizedList(dokFacade.findDokfkPodatnikRokKategoria(wpisView, kategoria));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokKategoria(Podatnik podatnik, String rok, String kategoria) {
        try {
           return Collections.synchronizedList(dokFacade.findDokfkPodatnikRokKategoria(podatnik, rok, kategoria));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokKategoriaOrderByNo(WpisView wpisView, String kategoria) {
        try {
           return Collections.synchronizedList(dokFacade.findDokfkPodatnikRokKategoriaOrderByNo(wpisView, kategoria));
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfk(String data, String numer) {
       try {
           return dokFacade.findDokfk(data, numer);
       } catch (Exception e ){
           return null;
       }
    }
    
  
    public Dokfk findDokfkObj(Dokfk selected) {
       try {
           return dokFacade.findDokfk(selected);
       } catch (Exception e ){
           return null;
       }
    }
    
       
    public Dokfk findDokfkObjKontrahent(Dokfk selected) {
       try {
           return dokFacade.findDokfkKontrahent(selected);
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfkLastofaType(Podatnik podatnik, String seriadokfk, String rok) {
       try {
           return dokFacade.findDokfkLastofaType(podatnik,seriadokfk, rok);
       } catch (Exception e ){
           return null;
       }
    }
    
    public Dokfk findDokfkLastofaTypeMc(Podatnik podatnik, String seriadokfk, String rok, String mc) {
       try {
           return dokFacade.findDokfkLastofaTypeMc(podatnik,seriadokfk, rok, mc);
       } catch (Exception e ){
           return null;
       }
    }
    
    public Dokfk findDokfkLastofaTypeKontrahent(Podatnik podatnik, String seriadokfk, Klienci kontr, String rok, Set<Dokfk> listaostatnich) {
        Dokfk zwrot = null;
        try {
            if (listaostatnich != null) {
                for (Dokfk p : listaostatnich) {
                    if (p.getSeriadokfk().equals(seriadokfk) && p.getKontr().equals(kontr)) {
                        zwrot = p;
                        break;
                    }
                }
            }
            if (zwrot == null) {
                zwrot = dokFacade.findDokfkLastofaTypeKontrahent(podatnik, seriadokfk, kontr, rok);
            }
        } catch (Exception e) {

        }
        return zwrot;
    }

    public Collection<? extends Dokfk> findDokByTypeYear(String BO, String rok) {
        try {
           return dokFacade.findDokByTypeYear(BO,rok);
       } catch (Exception e ){
           return null;
       }
    }

    public List<Dokfk> zwrocBiezacegoKlientaRokVAT(Podatnik podatnik, String rok) {
        return dokFacade.findDokfkBKVAT(podatnik,rok);
    }

    public Dokfk findDokfofaType(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        try {
           return dokFacade.findDokfofaTypeKontrahent(podatnikWpisu,vat, rokWpisuSt, mc);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfofaTypeKilkaLista(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        try {
           List<Dokfk> lista = Collections.synchronizedList(dokFacade.findDokfofaTypeKontrahentKilka(podatnikWpisu,vat, rokWpisuSt, mc));
           return lista;
           } catch (Exception e ){
           return null;
       }
    }
    
    public Dokfk findDokfofaTypeKilka(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        try {
           List<Dokfk> lista = Collections.synchronizedList(dokFacade.findDokfofaTypeKontrahentKilka(podatnikWpisu,vat, rokWpisuSt, mc));
           Dokfk d = null;
           int max = 0;
           if (lista != null && lista.size() > 0) {
               for (Dokfk l: lista) {
                   int nr = Integer.parseInt(l.getNumerwlasnydokfk().split("/")[0]);
                   if (nr > max) {
                       max = nr;
                       d = l;
                   }
               }
           }
           return d;
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfkObjUsun(Dokfk dousuniecia) {
        try {
           return dokFacade.findDokfkObject(dousuniecia);
       } catch (Exception e ){
           return null;
       }
    }

    public List<String> znajdzDokumentPodatnikWprFK(String wpr) {
        return Collections.synchronizedList(dokFacade.znajdzDokumentPodatnikWprFK(wpr));
    }

    public List<String> findZnajdzSeriePodatnik(WpisView wpisView) {
        return Collections.synchronizedList(dokFacade.findZnajdzSeriePodatnik(wpisView));
    }

    public Dokfk findDokfkID(Dokfk wybranyDokfk) {
         try {
           return dokFacade.findDokfId(wybranyDokfk);
       } catch (Exception e ){
           return null;
       }
    }
    
}
