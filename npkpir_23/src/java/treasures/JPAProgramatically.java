/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import entity.Faktura;
import entity.FakturaDuplikat;
import entity.Fakturywystokresowe;
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
//        <property name="javax.persistence.jdbc.url" value="jdbc:mysql://localhost:3306/pkpir?useUnicode=true&amp;characterEncoding=UTF-8"/>
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
        List<Fakturywystokresowe> faktury = emH2.createQuery("SELECT o FROM Fakturywystokresowe o").getResultList();
        emH2.getTransaction().begin();
        for (Fakturywystokresowe f: faktury) {
            //String query = "SELECT d FROM Faktura d WHERE d.fakturaPK.numerkolejny='"+f.getDokument().getFakturaPK().getNumerkolejny()+"' AND d.fakturaPK.wystawcanazwa='"+f.getDokument().getFakturaPK().getWystawcanazwa()+"'";
            //Faktura faktura = (Faktura) emH2.createQuery(query).getSingleResult();
            //f.setFa_id(faktura.getId());
            emH2.merge(f);
        }
        emH2.getTransaction().commit();
        System.out.println("koniec");
    }
}
