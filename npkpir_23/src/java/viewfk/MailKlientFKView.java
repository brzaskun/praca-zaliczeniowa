/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewfk;

import dao.DeklaracjaVatSchemaDAO;
import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import dao.SchemaEwidencjaDAO;
import entity.DeklaracjaVatSchema;
import entity.Deklaracjevat;
import interceptor.ConstructorInterceptor;
import jakarta.faces.view.ViewScoped;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import jpkview.Jpk_VAT2NView;
import mail.MaiManager;
import pdf.PdfVAT7new;
import view.DeklaracjevatView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class MailKlientFKView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private SymulacjaWynikuView symulacjaWynikuView;
    @Inject
    private SymulacjaWynikuNarastajacoView symulacjaWynikuNarastajacoView;
    @Inject
    private DeklaracjevatView deklaracjevatView;
    @Inject
    private Jpk_VAT2NView jpk_VAT2NView;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    @Inject
    private SchemaEwidencjaDAO schemaEwidencjaDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
 
    
    private static final String trescmaila = "<p> Dzień dobry</p> <p> Przesyłamy zestawienia księgowe</p> "
            + "<p> dla firmy <span style=\"color:#008000;\">%s</span> NIP %s</p> "
            + "<p> za okres <span style=\"color:#008000;\">%s/%s</span></p> "
            + " <p> &nbsp;</p>";
    
    public void wyslijPit() {
        try {
            String tytuł = String.format("Taxman - zestawienie wydruków księgowych za %s/%s", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            String tresc = String.format(new Locale("pl_PL"), trescmaila, wpisView.getPodatnikObiekt().getPrintnazwa(), wpisView.getPodatnikObiekt().getNip(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            ByteArrayOutputStream przychodykosztymc = null;
            symulacjaWynikuView.init();
            przychodykosztymc = symulacjaWynikuView.drukuj(1);
            ByteArrayOutputStream przychodykosztymcszczegoly = null;
            przychodykosztymcszczegoly = symulacjaWynikuView.drukuj(2);
            symulacjaWynikuNarastajacoView.init();
            ByteArrayOutputStream wyniknarastajaco = null;
            wyniknarastajaco = symulacjaWynikuNarastajacoView.drukuj();
            ByteArrayOutputStream deklaracjavat = null;
            deklaracjevatView.init();
            // Filtruj listę za pomocą streama
            Optional<Deklaracjevat> result = deklaracjevatView.getWyslanenormalne().stream()
            .filter(entity -> wpisView.getRokWpisuSt().equals(entity.getRok()) && wpisView.getMiesiacWpisu().equals(entity.getMiesiac()))
            .findFirst(); // zwraca opcjonalną pierwszą znalezioną encję

            // Jeśli chcesz zwrócić wynik (jeśli jest obecny) lub null, możesz to zrobić w ten sposób:
            Deklaracjevat dkl = result.orElse(null);
             DeklaracjaVatSchema pasujacaSchema = null;
             List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
               for (DeklaracjaVatSchema p : schemyLista) {
                  if (p.getNazwaschemy().equals(dkl.getWzorschemy())) {
                      pasujacaSchema = p;
                      break;
                  }
             }
            deklaracjavat = PdfVAT7new.drukujNowaVAT7(podatnikDAO, dkl, pasujacaSchema, schemaEwidencjaDAO, wpisView);
            ByteArrayOutputStream plikjpk = null;
            jpk_VAT2NView.init();
            plikjpk = jpk_VAT2NView.przygotujXMLFKPodglad();
            MaiManager.mailManagerZUSPITZalaczniki(wpisView.getPodatnikObiekt().getEmail(), tytuł, tresc, null, wpisView.getUzer().getEmail(), null, sMTPSettingsDAO.findSprawaByDef(),
                    przychodykosztymc, przychodykosztymcszczegoly, deklaracjavat, plikjpk);
            msg.Msg.msg("Wysłano do klienta informacje o podatku");
        } catch (Exception e){
            
        }
    }
}
