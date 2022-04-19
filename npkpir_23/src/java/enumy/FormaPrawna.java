/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enumy;

/**
 *
 * @author Osito
 */

public enum FormaPrawna {
    OSOBA_FIZYCZNA,
    SPOLKA_CYWILNA,
    SPOLKA_Z_O_O,
    SPOLKA_KOMANDYTOWA,
    PROSTA_SPÓŁKA_AKCYJNA,
    STOWARZYSZENIE,
    FEDERACJA,
    FUNDACJA;

    public static FormaPrawna getOSOBA_FIZYCZNA() {
        return OSOBA_FIZYCZNA;
    }

    
    public static FormaPrawna getSPOLKA_CYWILNA() {
        return SPOLKA_CYWILNA;
    }


    
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

    public static FormaPrawna getFUNDACJA() {
        return FUNDACJA;
    }

    public static FormaPrawna getPROSTA_SPÓŁKA_AKCYJNA() {
        return PROSTA_SPÓŁKA_AKCYJNA;
    }
    
    
    
}
