/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumy;

import javax.inject.Named;

/**
 *
 * @author Osito
 */

public enum FormaPrawna {

    SPOLKA_Z_O_O,
    SPOLKA_KOMANDYTOWA,
    STOWARZYSZENIE,
    FEDERACJA;

    public static FormaPrawna getSPOLKA_Z_O_O() {
        return SPOLKA_Z_O_O;
    }

    public static FormaPrawna getSPOLKA_KOMANDYTOWA() {
        return SPOLKA_KOMANDYTOWA;
    }

    public static FormaPrawna getSTOWARZYSZENIE() {
        return STOWARZYSZENIE;
    }

    public static FormaPrawna getFEDERACJA() {
        return FEDERACJA;
    }
    
    
}
