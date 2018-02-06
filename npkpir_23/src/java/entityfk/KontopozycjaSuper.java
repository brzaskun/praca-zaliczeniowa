/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToOne;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class KontopozycjaSuper implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idKP", nullable = false)
    protected Integer idKP;
    @Size(max = 255)
    @Column(length = 255, name = "pozycjaWn")
    protected String pozycjaWn;
    //0 aktywa
    //1 pasywa
    //99 rzis
    @Column(length = 10, name = "stronaWn")
    protected String stronaWn;
    @Size(max = 255)
    @Column(length = 255, name = "pozycjaMa")
    protected String pozycjaMa;
    //0 aktywa
    //1 pasywa
    //99 rzis
    @Column(length = 10, name = "stronaMa")
    protected String stronaMa;
    @Column(name = "syntetykaanalityka")
    protected String syntetykaanalityka;
    @JoinColumn(name = "ukladBR", referencedColumnName = "lp")
    protected UkladBR ukladBR;
    @OneToOne
    @JoinColumn(name = "kontoID", referencedColumnName = "id")
    protected Konto kontoID;
    @Column(name = "wynik0bilans1")
    protected boolean wynik0bilans1;

    public KontopozycjaSuper() {
    }

       
    
 
}
