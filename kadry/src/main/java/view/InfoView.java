/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PasekwynagrodzenFacade;
import dao.UzFacade;
import embeddable.Mce;
import entity.Pasekwynagrodzen;
import entity.Uz;
import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class InfoView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private UzFacade uzFacade;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    private List<Uz> uzytkownicy;
    
    @PostConstruct
    private void init() {
        String rok = wpisView.getRokWpisu();
        List<Pasekwynagrodzen> paski = pasekwynagrodzenFacade.findByRok(rok);
        uzytkownicy = uzFacade.findByUprawnienia("Administrator");
        for (Uz u : uzytkownicy) {
            int suma = 0;
            for (String mc : Mce.getMceListS()) {
                List<Pasekwynagrodzen> paskimc = paski.stream().filter(p->p.getMc().equals(mc)&&(p.getSporzadzil().equals(u.getLogin())||p.getSporzadzil().equals(u.getImieNazwisko()))).collect(Collectors.toList());
                int paskimcilosc = paskimc.size();
                switch (mc){
                    case "01":
                        u.setM1(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                    case "02":
                        u.setM2(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                    case "03":
                        u.setM3(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                    case "04":
                        u.setM4(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                    case "05":
                        u.setM5(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                     case "06":
                        u.setM6(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                     case "07":
                        u.setM7(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                     case "08":
                        u.setM8(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                     case "09":
                        u.setM9(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                     case "10":
                        u.setM10(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                     case "11":
                        u.setM11(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                    case "12":
                        u.setM12(paskimcilosc);
                        suma = suma + paskimcilosc;
                        break;
                }
            }
            u.setM13(suma);
        }
        System.out.println("");
    }

    public List<Uz> getUzytkownicy() {
        return uzytkownicy;
    }

    public void setUzytkownicy(List<Uz> uzytkownicy) {
        this.uzytkownicy = uzytkownicy;
    }
    
    
    
    
}
