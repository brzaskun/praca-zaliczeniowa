/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanParametr;

import embeddable.Parametr;
import entity.ParamSuper;
import java.util.List;

/**
 *
 * @author Osito
 */
public class BeanParamSuper {
    public static int sprawdzrok(ParamSuper parametr, List stare) {
        if (stare.isEmpty()) {
            parametr.setMcOd("01");
            parametr.setMcDo("12");
            parametr.setRokDo(parametr.getRokOd());
            return 0;
        } else {
            ParamSuper ostatniparametr = (ParamSuper) stare.get(stare.size() - 1);
            Integer old_rokDo = Integer.parseInt(ostatniparametr.getRokDo());
            Integer new_rokOd = Integer.parseInt(parametr.getRokOd());
            if (old_rokDo == new_rokOd - 1) {
                parametr.setMcOd("01");
                parametr.setMcDo("12");
                parametr.setRokDo(parametr.getRokOd());
                return 0;
            } else {
                return 1;
            }
        }
    }
}
