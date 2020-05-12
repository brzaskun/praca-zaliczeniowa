/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Osito
 */
public class JPAProgramatically {
    
    
//    UMIEŚĆIĆ TO W PERISTANCE
//    <!--ABY TO DZIAŁAŁO NALEŻY ZAZNACZYC WE WLASCIWOSCIACH PROJEKTU - COMPILE ON SAVE!!!!!-->
//    <persistence-unit name="fkKonto1">
//    <description>JPA Test</description>
//    <exclude-unlisted-classes>true</exclude-unlisted-classes>
//    <validation-mode>NONE</validation-mode>
//    <properties>
//        <property name="javax.persistence.jdbc.driver" value="com.mysql.jdbc.Driver"/>
//        <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/pkpir?useUnicode=true&amp;useJDBCCompliantTimezoneShift=true&amp;useLegacyDatetimeCode=false&amp;serverTimezone=UTC"/>
//        <property name="javax.persistence.jdbc.user" value="brzaskun"/>
//        <property name="javax.persistence.jdbc.password" value="pufikun"/>
//        <property name="eclipselink.logging.level" value="FINEST"/>
//        <property name="eclipselink.logging.level.sql" value="FINEST"/>
//        <property name="eclipselink.logging.parameters" value="true"/>
//    </properties>
//  </persistence-unit>
                
       public static void main(String[] args)  {
        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("JavaApplication4PU");
        EntityManager emH2 = emfH2.createEntityManager();
        List<Podatnik> podatnicy = emH2.createQuery("SELECT o FROM Podatnik o").getResultList();
        
//        for (Fakturywystokresowe f: faktury) {
//            //String query = "SELECT d FROM Faktura d WHERE d.fakturaPK.numerkolejny='"+f.getDokument().getFakturaPK().getNumerkolejny()+"' AND d.fakturaPK.wystawcanazwa='"+f.getDokument().getFakturaPK().getWystawcanazwa()+"'";
//            //Faktura faktura = (Faktura) emH2.createQuery(query).getSingleResult();
//            //f.setFa_id(faktura.getId());
//            emH2.merge(f);
//        }
        for (Podatnik p :podatnicy) {
            emH2.getTransaction().begin();
            List<Dokfk> dokfk =  emH2.createQuery("SELECT o FROM Dokfk o WHERE o.podatnikObj =:podatnik AND o.rok =:rok").setParameter("podatnik", p).setParameter("rok", "2020").getResultList();
//            List<Rodzajedok> rodzajedok = emH2.createQuery("SELECT o FROM Rodzajedok o WHERE o.podatnikObj =:podatnik AND o.rok =:rok").setParameter("podatnik", p).setParameter("rok", 2019).getResultList();
//            if (dokfk!=null && !dokfk.isEmpty() && rodzajedok!=null && !rodzajedok.isEmpty()) {
//                for (Dokfk s : dokfk) {
//                    naniesrodzaj(s,rodzajedok);
//                    emH2.merge(s);
//                }
//                System.out.println("podatnik "+p.getPrintnazwa());
//            }
        if (dokfk.size()>0) {
            System.out.println("");
        }
            emH2.getTransaction().commit();
        }
        System.out.println("koniec");
    }

    private static void podmienkonta(Rodzajedok s, List<Konto> konta) {
        Konto p1 = s.getKontoRZiS();
        Konto p2 = s.getKontorozrachunkowe();
        Konto p3 = s.getKontovat();
        if (p1!=null && p1.getRok()!=2019) {
            s.setKontoRZiS(nowekonto(p1,konta));
        }
        if (p2!=null && p2.getRok()!=2019) {
            s.setKontorozrachunkowe(nowekonto(p2,konta));
        }
        if (p3!=null && p3.getRok()!=2019) {
            s.setKontovat(nowekonto(p3,konta));
        }
    }

    private static Konto nowekonto(Konto p1, List<Konto> konta) {
        Konto zwrot = null;
        for (Konto k : konta) {
            if (p1.getPelnynumer().equals(k.getPelnynumer())) {
                zwrot = k;
                break;
            }
        }
        return zwrot;
    }

    private static void naniesrodzaj(Dokfk s, List<Rodzajedok> rodzajedok) {
        Rodzajedok rodzaj = s.getRodzajedok();
        for (Rodzajedok t : rodzajedok) {
            if (t.getSkrot().equals(rodzaj.getSkrot())) {
                s.setRodzajedok(t);
                break;
            }
        }
    }
}
