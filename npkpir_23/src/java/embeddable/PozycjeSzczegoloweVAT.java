/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Embeddable;

    

/**
 *
 * @author Osito
 */
@Embeddable
public class PozycjeSzczegoloweVAT implements Serializable {
    private static final long serialVersionUID = -4763993669394275545L;
    
    private String pole10;
    private String pole11;
    private String pole12;
    private String pole13;
    private String pole14;
    private String pole15;
    private String pole16;
    private String pole17;
    private String pole18;
    private String pole19;
    private String pole20;
    private String pole21;
    private String pole22;
    private String pole23;
    private String pole24;
    private String pole25;
    private String pole26;
    private String pole27;
    private String pole28;
    private String pole29;
    private String pole30;
    private String pole31;
    private String pole32;
    private String pole33;
    private String pole34;
    private String pole35;
    private String pole36;
    private String pole37;
    private String pole38;
    private String pole39;
    private String pole40;
    private String pole41;
    private String pole42;
    private String pole43;
    private String pole44;
    private String pole45;
    private String pole46;
    private String pole47;
    private String pole48;
    private String pole49;
    private String pole50;
    private String pole51;
    private String pole52;
    private String pole53;
    private String pole54;
    private String pole55;
    private String pole56;
    private String pole57;
    private String pole58;
    private String pole59;
    private String pole60;
    private String pole61;
    private String pole62;
    private String pole63;
    private String pole64;
    private String pole65;
    private String pole66;
    private String pole67;
    private String pole68;
    private String pole69;
    private String pole70;
    
    private Integer poleI10;
    private Integer poleI11;
    private Integer poleI12;
    private Integer poleI13;
    private Integer poleI14;
    private Integer poleI15;
    private Integer poleI16;
    private Integer poleI17;
    private Integer poleI18;
    private Integer poleI19;
    private Integer poleI20;
    private Integer poleI21;
    private Integer poleI22;
    private Integer poleI23;
    private Integer poleI24;
    private Integer poleI25;
    private Integer poleI26;
    private Integer poleI27;
    private Integer poleI28;
    private Integer poleI29;
    private Integer poleI30;
    private Integer poleI31;
    private Integer poleI32;
    private Integer poleI33;
    private Integer poleI34;
    private Integer poleI35;
    private Integer poleI36;
    private Integer poleI37;
    private Integer poleI38;
    private Integer poleI39;
    private Integer poleI40;
    private Integer poleI41;
    private Integer poleI42;
    private Integer poleI43;
    private Integer poleI44;
    private Integer poleI45;
    private Integer poleI46;
    private Integer poleI47;
    private Integer poleI48;
    private Integer poleI49;
    private Integer poleI50;
    private Integer poleI51;
    private Integer poleI52;
    private Integer poleI53;
    private Integer poleI54;
    private Integer poleI55;
    private Integer poleI56;
    private Integer poleI57;
    private Integer poleI58;
    private Integer poleI59;
    private Integer poleI60;
    private Integer poleI61;
    private Integer poleI62;
    private Integer poleI63;
    private Integer poleI64;
    private Integer poleI65;
    private Integer poleI66;
    private Integer poleI67;
    private Integer poleI68;
    private Integer poleI69;
    private Integer poleI70;

    public PozycjeSzczegoloweVAT() {
        poleI10=0;
        poleI11=0;
        poleI12=0;
        poleI13=0;
        poleI14=0;
        poleI15=0;
        poleI16=0;
        poleI17=0;
        poleI18=0;
        poleI19=0;
        poleI20=0;
        poleI21=0;
        poleI22=0;
        poleI23=0;
        poleI24=0;
        poleI25=0;
        poleI26=0;
        poleI27=0;
        poleI28=0;
        poleI29=0;
        poleI30=0;
        poleI31=0;
        poleI32=0;
        poleI33=0;
        poleI34=0;
        poleI35=0;
        poleI36=0;
        poleI37=0;
        poleI38=0;
        poleI39=0;
        poleI40=0;
        poleI41=0;
        poleI42=0;
        poleI43=0;
        poleI44=0;
        poleI45=0;
        poleI46=0;
        poleI47=0;
        poleI48=0;
        poleI49=0;
        poleI50=0;
        poleI51=0;
        poleI52=0;
        poleI53=0;
        poleI54=0;
        poleI55=0;
        poleI56=0;
        poleI57=0;
        poleI58=0;
        poleI59=0;
        poleI60=0;
        poleI61=0;
        poleI62=0;
        poleI63=0;
        poleI64=0;
        poleI65=0;
        poleI66=0;
        poleI67=0;
        poleI68=0;
        poleI69=0;
        poleI70=0;
    }
    
    public List<PP> getPozycje() {
        List<PP> lista = new ArrayList<>();
        for(int i = 10;i<42;i++){
            try {
                Class[] noparams = {};
                Method met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("getPoleI"+i, noparams);
                Integer wynik = (Integer) met.invoke(this, (Object[]) null);
                if (wynik != 0.0) {
                    lista.add(new PP("pole "+i,wynik));
                }
                
            } catch (Exception ex) {
                Logger.getLogger(PozycjeSzczegoloweVAT.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return lista;
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">    
    public String getPole10() {
        return pole10;
    }

    public void setPole10(String pole10) {
        this.pole10 = pole10;
    }

    public String getPole11() {
        return pole11;
    }

    public void setPole11(String pole11) {
        this.pole11 = pole11;
    }

    public String getPole12() {
        return pole12;
    }

    public void setPole12(String pole12) {
        this.pole12 = pole12;
    }

    public String getPole13() {
        return pole13;
    }

    public void setPole13(String pole13) {
        this.pole13 = pole13;
    }

    public String getPole14() {
        return pole14;
    }

    public void setPole14(String pole14) {
        this.pole14 = pole14;
    }

    public String getPole15() {
        return pole15;
    }

    public void setPole15(String pole15) {
        this.pole15 = pole15;
    }

    public String getPole16() {
        return pole16;
    }

    public void setPole16(String pole16) {
        this.pole16 = pole16;
    }

    public String getPole17() {
        return pole17;
    }

    public void setPole17(String pole17) {
        this.pole17 = pole17;
    }

    public String getPole18() {
        return pole18;
    }

    public void setPole18(String pole18) {
        this.pole18 = pole18;
    }

    public String getPole19() {
        return pole19;
    }

    public void setPole19(String pole19) {
        this.pole19 = pole19;
    }

    public Integer getPoleI10() {
        return poleI10;
    }

    public void setPoleI10(Integer poleI10) {
        this.poleI10 = poleI10;
    }

    public Integer getPoleI11() {
        return poleI11;
    }

    public void setPoleI11(Integer poleI11) {
        this.poleI11 = poleI11;
    }

    public Integer getPoleI12() {
        return poleI12;
    }

    public void setPoleI12(Integer poleI12) {
        this.poleI12 = poleI12;
    }

    public Integer getPoleI13() {
        return poleI13;
    }

    public void setPoleI13(Integer poleI13) {
        this.poleI13 = poleI13;
    }

    public Integer getPoleI14() {
        return poleI14;
    }

    public void setPoleI14(Integer poleI14) {
        this.poleI14 = poleI14;
    }

    public Integer getPoleI15() {
        return poleI15;
    }

    public void setPoleI15(Integer poleI15) {
        this.poleI15 = poleI15;
    }

    public Integer getPoleI16() {
        return poleI16;
    }

    public void setPoleI16(Integer poleI16) {
        this.poleI16 = poleI16;
    }

    public Integer getPoleI17() {
        return poleI17;
    }

    public void setPoleI17(Integer poleI17) {
        this.poleI17 = poleI17;
    }

    public Integer getPoleI18() {
        return poleI18;
    }

    public void setPoleI18(Integer poleI18) {
        this.poleI18 = poleI18;
    }

    public Integer getPoleI19() {
        return poleI19;
    }

    
    public void setPoleI19(Integer poleI19) {    
        this.poleI19 = poleI19;
    }

    public String getPole20() {
        return pole20;
    }
    
    public void setPole20(String pole20) {
        this.pole20 = pole20;
    }
    
    public String getPole21() {
        return pole21;
    }
    
    public void setPole21(String pole21) {
        this.pole21 = pole21;
    }
    
    public String getPole22() {
        return pole22;
    }
    
    public void setPole22(String pole22) {
        this.pole22 = pole22;
    }
    
    public String getPole23() {
        return pole23;
    }
    
    public void setPole23(String pole23) {
        this.pole23 = pole23;
    }
    
    public String getPole24() {
        return pole24;
    }
    
    public void setPole24(String pole24) {
        this.pole24 = pole24;
    }
    
    public String getPole25() {
        return pole25;
    }
    
    public void setPole25(String pole25) {
        this.pole25 = pole25;
    }
    
    public String getPole26() {
        return pole26;
    }
    
    public void setPole26(String pole26) {
        this.pole26 = pole26;
    }
    
    public String getPole27() {
        return pole27;
    }
    
    public void setPole27(String pole27) {
        this.pole27 = pole27;
    }
    
    public String getPole28() {
        return pole28;
    }
    
    public void setPole28(String pole28) {
        this.pole28 = pole28;
    }
    
    public String getPole29() {
        return pole29;
    }
    
    public void setPole29(String pole29) {
        this.pole29 = pole29;
    }
    
    public String getPole30() {
        return pole30;
    }
    
    public void setPole30(String pole30) {
        this.pole30 = pole30;
    }
    
    public String getPole31() {
        return pole31;
    }
    
    public void setPole31(String pole31) {
        this.pole31 = pole31;
    }
    
    public String getPole32() {
        return pole32;
    }
    
    public void setPole32(String pole32) {
        this.pole32 = pole32;
    }
    
    public String getPole33() {
        return pole33;
    }
    
    public void setPole33(String pole33) {
        this.pole33 = pole33;
    }
    
    public String getPole34() {
        return pole34;
    }
    
    public void setPole34(String pole34) {
        this.pole34 = pole34;
    }
    
    public String getPole35() {
        return pole35;
    }
    
    public void setPole35(String pole35) {
        this.pole35 = pole35;
    }
    
    public String getPole36() {
        return pole36;
    }
    
    public void setPole36(String pole36) {
        this.pole36 = pole36;
    }
    
    public String getPole37() {
        return pole37;
    }
    
    public void setPole37(String pole37) {
        this.pole37 = pole37;
    }
    
    public String getPole38() {
        return pole38;
    }
    
    public void setPole38(String pole38) {
        this.pole38 = pole38;
    }
    
    public String getPole39() {
        return pole39;
    }
    
    public void setPole39(String pole39) {
        this.pole39 = pole39;
    }
    
    public String getPole40() {
        return pole40;
    }
    
    public void setPole40(String pole40) {
        this.pole40 = pole40;
    }
    
    public String getPole41() {
        return pole41;
    }
    
    public void setPole41(String pole41) {
        this.pole41 = pole41;
    }
    
    public String getPole42() {
        return pole42;
    }
    
    public void setPole42(String pole42) {
        this.pole42 = pole42;
    }
    
    public String getPole43() {
        return pole43;
    }
    
    public void setPole43(String pole43) {
        this.pole43 = pole43;
    }
    
    public String getPole44() {
        return pole44;
    }
    
    public void setPole44(String pole44) {
        this.pole44 = pole44;
    }
    
    public String getPole45() {
        return pole45;
    }
    
    public void setPole45(String pole45) {
        this.pole45 = pole45;
    }
    
    public String getPole46() {
        return pole46;
    }
    
    public void setPole46(String pole46) {
        this.pole46 = pole46;
    }
    
    public String getPole47() {
        return pole47;
    }
    
    public void setPole47(String pole47) {
        this.pole47 = pole47;
    }
    
    public String getPole48() {
        return pole48;
    }
    
    public void setPole48(String pole48) {
        this.pole48 = pole48;
    }
    
    public String getPole49() {
        return pole49;
    }
    
    public void setPole49(String pole49) {
        this.pole49 = pole49;
    }
    
    public String getPole50() {
        return pole50;
    }
    
    public void setPole50(String pole50) {
        this.pole50 = pole50;
    }
    
    public String getPole51() {
        return pole51;
    }
    
    public void setPole51(String pole51) {
        this.pole51 = pole51;
    }
    
    public String getPole52() {
        return pole52;
    }
    
    public void setPole52(String pole52) {
        this.pole52 = pole52;
    }
    
    public String getPole53() {
        return pole53;
    }
    
    public void setPole53(String pole53) {
        this.pole53 = pole53;
    }
    
    public String getPole54() {
        return pole54;
    }
    
    public void setPole54(String pole54) {
        this.pole54 = pole54;
    }
    
    public String getPole55() {
        return pole55;
    }
    
    public void setPole55(String pole55) {
        this.pole55 = pole55;
    }
    
    public String getPole56() {
        return pole56;
    }
    
    public void setPole56(String pole56) {
        this.pole56 = pole56;
    }
    
    public String getPole57() {
        return pole57;
    }
    
    public void setPole57(String pole57) {
        this.pole57 = pole57;
    }
    
    public String getPole58() {
        return pole58;
    }
    
    public void setPole58(String pole58) {
        this.pole58 = pole58;
    }
    
    public String getPole59() {
        return pole59;
    }
    
    public void setPole59(String pole59) {
        this.pole59 = pole59;
    }
    
    public String getPole60() {
        return pole60;
    }
    
    public void setPole60(String pole60) {
        this.pole60 = pole60;
    }
    
    public String getPole61() {
        return pole61;
    }
    
    public void setPole61(String pole61) {
        this.pole61 = pole61;
    }
    
    public String getPole62() {
        return pole62;
    }
    
    public void setPole62(String pole62) {
        this.pole62 = pole62;
    }
    
    public String getPole63() {
        return pole63;
    }
    
    public void setPole63(String pole63) {
        this.pole63 = pole63;
    }
    
    public String getPole64() {
        return pole64;
    }
    
    public void setPole64(String pole64) {
        this.pole64 = pole64;
    }
    
    public String getPole65() {
        return pole65;
    }
    
    public void setPole65(String pole65) {
        this.pole65 = pole65;
    }
    
    public Integer getPoleI20() {
        return poleI20;
    }
    
    public void setPoleI20(Integer poleI20) {
        this.poleI20 = poleI20;
    }
    
    public Integer getPoleI21() {
        return poleI21;
    }
    
    public void setPoleI21(Integer poleI21) {
        this.poleI21 = poleI21;
    }
    
    public Integer getPoleI22() {
        return poleI22;
    }
    
    public void setPoleI22(Integer poleI22) {
        this.poleI22 = poleI22;
    }
    
    public Integer getPoleI23() {
        return poleI23;
    }
    
    public void setPoleI23(Integer poleI23) {
        this.poleI23 = poleI23;
    }
    
    public Integer getPoleI24() {
        return poleI24;
    }
    
    public void setPoleI24(Integer poleI24) {
        this.poleI24 = poleI24;
    }
    
    public Integer getPoleI25() {
        return poleI25;
    }
    
    public void setPoleI25(Integer poleI25) {
        this.poleI25 = poleI25;
    }
    
    public Integer getPoleI26() {
        return poleI26;
    }
    
    public void setPoleI26(Integer poleI26) {
        this.poleI26 = poleI26;
    }
    
    public Integer getPoleI27() {
        return poleI27;
    }
    
    public void setPoleI27(Integer poleI27) {
        this.poleI27 = poleI27;
    }
    
    public Integer getPoleI28() {
        return poleI28;
    }
    
    public void setPoleI28(Integer poleI28) {
        this.poleI28 = poleI28;
    }
    
    public Integer getPoleI29() {
        return poleI29;
    }
    
    public void setPoleI29(Integer poleI29) {
        this.poleI29 = poleI29;
    }
    
    public Integer getPoleI30() {
        return poleI30;
    }
    
    public void setPoleI30(Integer poleI30) {
        this.poleI30 = poleI30;
    }
    
    public Integer getPoleI31() {
        return poleI31;
    }
    
    public void setPoleI31(Integer poleI31) {
        this.poleI31 = poleI31;
    }
    
    public Integer getPoleI32() {
        return poleI32;
    }
    
    public void setPoleI32(Integer poleI32) {
        this.poleI32 = poleI32;
    }
    
    public Integer getPoleI33() {
        return poleI33;
    }
    
    public void setPoleI33(Integer poleI33) {
        this.poleI33 = poleI33;
    }
    
    public Integer getPoleI34() {
        return poleI34;
    }
    
    public void setPoleI34(Integer poleI34) {
        this.poleI34 = poleI34;
    }
    
    public Integer getPoleI35() {
        return poleI35;
    }
    
    public void setPoleI35(Integer poleI35) {
        this.poleI35 = poleI35;
    }
    
    public Integer getPoleI36() {
        return poleI36;
    }
    
    public void setPoleI36(Integer poleI36) {
        this.poleI36 = poleI36;
    }
    
    public Integer getPoleI37() {
        return poleI37;
    }
    
    public void setPoleI37(Integer poleI37) {
        this.poleI37 = poleI37;
    }
    
    public Integer getPoleI38() {
        return poleI38;
    }
    
    public void setPoleI38(Integer poleI38) {
        this.poleI38 = poleI38;
    }
    
    public Integer getPoleI39() {
        return poleI39;
    }
    
    public void setPoleI39(Integer poleI39) {
        this.poleI39 = poleI39;
    }
    
    public Integer getPoleI40() {
        return poleI40;
    }
    
    public void setPoleI40(Integer poleI40) {
        this.poleI40 = poleI40;
    }
    
    public Integer getPoleI41() {
        return poleI41;
    }
    
    public void setPoleI41(Integer poleI41) {
        this.poleI41 = poleI41;
    }
    
    public Integer getPoleI42() {
        return poleI42;
    }
    
    public void setPoleI42(Integer poleI42) {
        this.poleI42 = poleI42;
    }
    
    public Integer getPoleI43() {
        return poleI43;
    }
    
    public void setPoleI43(Integer poleI43) {
        this.poleI43 = poleI43;
    }
    
    public Integer getPoleI44() {
        return poleI44;
    }
    
    public void setPoleI44(Integer poleI44) {
        this.poleI44 = poleI44;
    }
    
    public Integer getPoleI45() {
        return poleI45;
    }
    
    public void setPoleI45(Integer poleI45) {
        this.poleI45 = poleI45;
    }
    
    public Integer getPoleI46() {
        return poleI46;
    }
    
    public void setPoleI46(Integer poleI46) {
        this.poleI46 = poleI46;
    }
    
    public Integer getPoleI47() {
        return poleI47;
    }
    
    public void setPoleI47(Integer poleI47) {
        this.poleI47 = poleI47;
    }
    
    public Integer getPoleI48() {
        return poleI48;
    }
    
    public void setPoleI48(Integer poleI48) {
        this.poleI48 = poleI48;
    }
    
    public Integer getPoleI49() {
        return poleI49;
    }
    
    public void setPoleI49(Integer poleI49) {
        this.poleI49 = poleI49;
    }
    
    public Integer getPoleI50() {
        return poleI50;
    }
    
    public void setPoleI50(Integer poleI50) {
        this.poleI50 = poleI50;
    }
    
    public Integer getPoleI51() {
        return poleI51;
    }
    
    public void setPoleI51(Integer poleI51) {
        this.poleI51 = poleI51;
    }
    
    public Integer getPoleI52() {
        return poleI52;
    }
    
    public void setPoleI52(Integer poleI52) {
        this.poleI52 = poleI52;
    }
    
    public Integer getPoleI53() {
        return poleI53;
    }
    
    public void setPoleI53(Integer poleI53) {
        this.poleI53 = poleI53;
    }
    
    public Integer getPoleI54() {
        return poleI54;
    }
    
    public void setPoleI54(Integer poleI54) {
        this.poleI54 = poleI54;
    }
    
    public Integer getPoleI55() {
        return poleI55;
    }
    
    public void setPoleI55(Integer poleI55) {
        this.poleI55 = poleI55;
    }
    
    public Integer getPoleI56() {
        return poleI56;
    }
    
    public void setPoleI56(Integer poleI56) {
        this.poleI56 = poleI56;
    }
    
    public Integer getPoleI57() {
        return poleI57;
    }
    
    public void setPoleI57(Integer poleI57) {
        this.poleI57 = poleI57;
    }
    
    public Integer getPoleI58() {
        return poleI58;
    }
    
    public void setPoleI58(Integer poleI58) {
        this.poleI58 = poleI58;
    }
    
    public Integer getPoleI59() {
        return poleI59;
    }
    
    public void setPoleI59(Integer poleI59) {
        this.poleI59 = poleI59;
    }
    
    public Integer getPoleI60() {
        return poleI60;
    }
    
    public void setPoleI60(Integer poleI60) {
        this.poleI60 = poleI60;
    }
    
    public Integer getPoleI61() {
        return poleI61;
    }
    
    public void setPoleI61(Integer poleI61) {
        this.poleI61 = poleI61;
    }
    
    public Integer getPoleI62() {
        return poleI62;
    }
    
    public void setPoleI62(Integer poleI62) {
        this.poleI62 = poleI62;
    }
    
    public Integer getPoleI63() {
        return poleI63;
    }
    
    public void setPoleI63(Integer poleI63) {
        this.poleI63 = poleI63;
    }
    
    public Integer getPoleI64() {
        return poleI64;
    }
    
    public void setPoleI64(Integer poleI64) {
        this.poleI64 = poleI64;
    }
    
    public Integer getPoleI65() {
        return poleI65;
    }
    
    public void setPoleI65(Integer poleI65) {
        this.poleI65 = poleI65;
    }
    
    public String getPole66() {
        return pole66;
    }
    
    public void setPole66(String pole66) {
        this.pole66 = pole66;
    }
    
    public String getPole67() {
        return pole67;
    }
    
    public void setPole67(String pole67) {
        this.pole67 = pole67;
    }
    
    public String getPole68() {
        return pole68;
    }
    
    public void setPole68(String pole68) {
        this.pole68 = pole68;
    }
    
    public String getPole69() {
        return pole69;
    }
    
    public void setPole69(String pole69) {
        this.pole69 = pole69;
    }
    
    public String getPole70() {
        return pole70;
    }
    
    public void setPole70(String pole70) {
        this.pole70 = pole70;
    }
    
    public Integer getPoleI66() {
        return poleI66;
    }
    
    public void setPoleI66(Integer poleI66) {
        this.poleI66 = poleI66;
    }
    
    public Integer getPoleI67() {
        return poleI67;
    }
    
    public void setPoleI67(Integer poleI67) {
        this.poleI67 = poleI67;
    }
    
    public Integer getPoleI68() {
        return poleI68;
    }
    
    public void setPoleI68(Integer poleI68) {
        this.poleI68 = poleI68;
    }
    
    public Integer getPoleI69() {
        return poleI69;
    }
    
    public void setPoleI69(Integer poleI69) {
        this.poleI69 = poleI69;
    }
    
    public Integer getPoleI70() {
        return poleI70;
    }
    
    public void setPoleI70(Integer poleI70) {
        this.poleI70 = poleI70;
    }
//</editor-fold>

    public static class PP {
        String nazwapola;
        int kwota;
        public PP() {
        }

        private PP(String s, Integer wynik) {
            this.nazwapola = s;
            this.kwota = wynik;
        }

        public String getNazwapola() {
            return nazwapola;
        }

        public void setNazwapola(String nazwapola) {
            this.nazwapola = nazwapola;
        }

        public int getKwota() {
            return kwota;
        }

        public void setKwota(int kwota) {
            this.kwota = kwota;
        }
        
    }
    
   
    
}
