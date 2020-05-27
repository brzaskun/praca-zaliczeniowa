/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sprawozdania.v12;

import entityfk.SprFinKwotyInfDod;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.apache.xml.security.utils.Base64;

/**
 *
 * @author Osito
 */
public class SprawozdanieFin2018DodInfoBean {

    public static sprawozdania.v12.JednostkaInna.DodatkoweInformacjeIObjasnieniaJednostkaInna generuj(SprFinKwotyInfDod sprFinKwotyInfDod) {
        JednostkaInna.DodatkoweInformacjeIObjasnieniaJednostkaInna p = new JednostkaInna.DodatkoweInformacjeIObjasnieniaJednostkaInna();
        p.dodatkoweInformacjeIObjasnienia = zrobdodatkoweinfo(sprFinKwotyInfDod);
        p.informacjaDodatkowaDotyczacaPodatkuDochodowego = zrobinfopodatekdochodowy(sprFinKwotyInfDod);
        return p;
    }

    private static List<TInformacjaDodatkowa> zrobdodatkoweinfo(SprFinKwotyInfDod sprFinKwotyInfDod) {
        List<TInformacjaDodatkowa> zwrot = new ArrayList<>();
        TInformacjaDodatkowa p = new TInformacjaDodatkowa();
        p.opis = "Załącznik 1 - polityka rachunkowości przedsiębiorstwa";
        p.plik = zrobstream(sprFinKwotyInfDod);
        zwrot.add(p);
        return zwrot;
    }

    
    private static TZalacznik zrobstream(SprFinKwotyInfDod sprFinKwotyInfDod) {
        TZalacznik p = new TZalacznik();
        try {
            p.nazwa = sprFinKwotyInfDod.getNazwapliku();
            p.zawartosc = sprFinKwotyInfDod.getPlik();
        } catch (Exception ex) {}
        return p;
    }
    
//    private static TZalacznik zrobplik(String filename) {
//        TZalacznik p = new TZalacznik();
//        try {
//            Path path = Paths.get(filename);
//            byte[] data = Files.readAllBytes(path);
//            p.nazwa = "Polityka_rachunkowosci.pdf";
//            p.zawartosc = data;
//        } catch (IOException ex) {}
//        return p;
//    }

    private static TInformacjaDodatkowaDotyczacaPodatkuDochodowego zrobinfopodatekdochodowy(SprFinKwotyInfDod s) {
        TInformacjaDodatkowaDotyczacaPodatkuDochodowego p = new TInformacjaDodatkowaDotyczacaPodatkuDochodowego();
        //A. Zysk (strata) brutto za dany rok
        p.pid1 = zrobd(s.getPid1A(), s.getPid1B());
        //B. Przychody zwolnione z opodatkowania (trwałe różnice pomiędzy zyskiem/stratą dla celów rachunkowych a dochodem/stratą dla celów podatkowych), w tym:
        p.pid2 = zrobTPozycjaInformacjiDodatkowej(s.getPid2A(), s.getPid2B());
        //C. Przychody niepodlegające opodatkowania w roku bieżącym, w tym:
        p.pid3 = zrobTPozycjaInformacjiDodatkowej(s.getPid3A(), s.getPid3B());
        //D. Przychody podlegające opodatkowania w roku bieżącym, ujęte w księgach rachunkowych lat ubiegłych w tym:
        p.pid4 = zrobTPozycjaInformacjiDodatkowej(s.getPid4A(), s.getPid4B());
        //E. Koszty niestanowiące kosztów uzyskania przychodów (trwałe różnice pomiędzy zyskiem/stratą dla celów rachunkowych a dochodem/stratą dla celów podatkowych), w tym:
        p.pid5 = zrobTPozycjaInformacjiDodatkowej(s.getPid5A(), s.getPid5B());
        //F. Koszty nieuznawane za koszty uzyskania przychodów w bieżącym roku, w tym:
        p.pid6 = zrobTPozycjaInformacjiDodatkowej(s.getPid6A(), s.getPid6B());
        //G. Koszty uznawane za koszty uzyskania przychodów w roku bieżącym ujęte w księgach lat ubiegłych, w tym:
        p.pid7 = zrobTPozycjaInformacjiDodatkowej(s.getPid7A(), s.getPid7B());
        //H. Strata z lat ubiegłych, w tym:
        p.pid8 = zrobTPozycjaInformacjiDodatkowej2(s.getPid8A(), s.getPid8B());
        //I. Inne zmiany podstawy opodatkowania, w tym:
        p.pid9 = zrobTPozycjaInformacjiDodatkowej(s.getPid9A(), s.getPid9B());
        //J. Podstawa opodatkowania podatkiem dochodowym
        p.pid10 = zrobd(s.getPid10A(), s.getPid10B());
        //K. Podatek dochodowy
        p.pid11 = zrobd(s.getPid11A(), s.getPid11B());
        return p;
    }
    
   
    private static TKwotaOkres zrobd(BigDecimal a, BigDecimal b) {
        TKwotaOkres p = new TKwotaOkres();
        p.rb = a;
        p.rp = b;
        return p;
    }

    private static TPozycjaInformacjiDodatkowej zrobTPozycjaInformacjiDodatkowej(BigDecimal a, BigDecimal b) {
        TPozycjaInformacjiDodatkowej p = new TPozycjaInformacjiDodatkowej();
        TKwotaOkresInformacjiDodatkowej r = new TKwotaOkresInformacjiDodatkowej();
        r.rb = zrobkwoteA(a);
        r.rp = zrobkwoteA(b);
        p.kwota = r;
        return p;
    }

    private static TPozycjaInformacjiDodatkowej2 zrobTPozycjaInformacjiDodatkowej2(BigDecimal a, BigDecimal b) {
        TPozycjaInformacjiDodatkowej2 p = new TPozycjaInformacjiDodatkowej2();
        TKwotaOkresInformacjiDodatkowej r = new TKwotaOkresInformacjiDodatkowej();
        r.rb = zrobkwoteA(a);
        r.rp = zrobkwoteA(b);
        p.kwota = r;
        return p;
    }
    
    private static TKwotaInformacjiDodatkowej zrobkwoteA(BigDecimal a) {
        TKwotaInformacjiDodatkowej p = new TKwotaInformacjiDodatkowej();
        p.kwotaA = a;
        return p;
    }
    
     public static void main(String[] args) {
        //try {
//            TZalacznik p = zrobplik("d:\\aplik.pdf");
//            InputStream targetStream = new ByteArrayInputStream(p.zawartosc);
//            File targetFile = new File("d:\\apliknowy.pdf");
//            java.nio.file.Files.copy(targetStream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
//            IOUtils.closeQuietly(targetStream);
//        } catch (IOException ex) {
//            Logger.getLogger(SprawozdanieFin2018DodInfoBean.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    

     
}
