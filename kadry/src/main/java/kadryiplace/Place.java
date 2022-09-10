/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "place", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Place.findAll", query = "SELECT p FROM Place p"),
    @NamedQuery(name = "Place.findByLplSerial", query = "SELECT p FROM Place p WHERE p.lplSerial = :lplSerial"),
    @NamedQuery(name = "Place.findByLplPodst", query = "SELECT p FROM Place p WHERE p.lplPodst = :lplPodst"),
    @NamedQuery(name = "Place.findByLplNiezd", query = "SELECT p FROM Place p WHERE p.lplNiezd = :lplNiezd"),
    @NamedQuery(name = "Place.findByLplKoszty", query = "SELECT p FROM Place p WHERE p.lplKoszty = :lplKoszty"),
    @NamedQuery(name = "Place.findByLplZalDoch", query = "SELECT p FROM Place p WHERE p.lplZalDoch = :lplZalDoch"),
    @NamedQuery(name = "Place.findByLplPotrInne3", query = "SELECT p FROM Place p WHERE p.lplPotrInne3 = :lplPotrInne3"),
    @NamedQuery(name = "Place.findByLplPotrInne1", query = "SELECT p FROM Place p WHERE p.lplPotrInne1 = :lplPotrInne1"),
    @NamedQuery(name = "Place.findByLplStEmer", query = "SELECT p FROM Place p WHERE p.lplStEmer = :lplStEmer"),
    @NamedQuery(name = "Place.findByLplStRent", query = "SELECT p FROM Place p WHERE p.lplStRent = :lplStRent"),
    @NamedQuery(name = "Place.findByLplStChor", query = "SELECT p FROM Place p WHERE p.lplStChor = :lplStChor"),
    @NamedQuery(name = "Place.findByLplStZdro", query = "SELECT p FROM Place p WHERE p.lplStZdro = :lplStZdro"),
    @NamedQuery(name = "Place.findByLplNiezdZus", query = "SELECT p FROM Place p WHERE p.lplNiezdZus = :lplNiezdZus"),
    @NamedQuery(name = "Place.findByLplPotrInne2", query = "SELECT p FROM Place p WHERE p.lplPotrInne2 = :lplPotrInne2"),
    @NamedQuery(name = "Place.findByLplPotrInne4", query = "SELECT p FROM Place p WHERE p.lplPotrInne4 = :lplPotrInne4"),
    @NamedQuery(name = "Place.findByLplZalStawka", query = "SELECT p FROM Place p WHERE p.lplZalStawka = :lplZalStawka"),
    @NamedQuery(name = "Place.findByLplZalWolna", query = "SELECT p FROM Place p WHERE p.lplZalWolna = :lplZalWolna"),
    @NamedQuery(name = "Place.findByLplPdstPodDoch", query = "SELECT p FROM Place p WHERE p.lplPdstPodDoch = :lplPdstPodDoch"),
    @NamedQuery(name = "Place.findByLplPdstZus", query = "SELECT p FROM Place p WHERE p.lplPdstZus = :lplPdstZus"),
    @NamedQuery(name = "Place.findByLplPdstZdrowotne", query = "SELECT p FROM Place p WHERE p.lplPdstZdrowotne = :lplPdstZdrowotne"),
    @NamedQuery(name = "Place.findByLplPotrInne1Opis", query = "SELECT p FROM Place p WHERE p.lplPotrInne1Opis = :lplPotrInne1Opis"),
    @NamedQuery(name = "Place.findByLplPotrInne2Opis", query = "SELECT p FROM Place p WHERE p.lplPotrInne2Opis = :lplPotrInne2Opis"),
    @NamedQuery(name = "Place.findByLplPotrInne3Opis", query = "SELECT p FROM Place p WHERE p.lplPotrInne3Opis = :lplPotrInne3Opis"),
    @NamedQuery(name = "Place.findByLplPotrInne4Opis", query = "SELECT p FROM Place p WHERE p.lplPotrInne4Opis = :lplPotrInne4Opis"),
    @NamedQuery(name = "Place.findByLplZusEmer", query = "SELECT p FROM Place p WHERE p.lplZusEmer = :lplZusEmer"),
    @NamedQuery(name = "Place.findByLplZusRent", query = "SELECT p FROM Place p WHERE p.lplZusRent = :lplZusRent"),
    @NamedQuery(name = "Place.findByLplZusChor", query = "SELECT p FROM Place p WHERE p.lplZusChor = :lplZusChor"),
    @NamedQuery(name = "Place.findByLplZusWyp", query = "SELECT p FROM Place p WHERE p.lplZusWyp = :lplZusWyp"),
    @NamedQuery(name = "Place.findByLplZusZdro", query = "SELECT p FROM Place p WHERE p.lplZusZdro = :lplZusZdro"),
    @NamedQuery(name = "Place.findByLplPodDoch", query = "SELECT p FROM Place p WHERE p.lplPodDoch = :lplPodDoch"),
    @NamedQuery(name = "Place.findByLplZasRodz", query = "SELECT p FROM Place p WHERE p.lplZasRodz = :lplZasRodz"),
    @NamedQuery(name = "Place.findByLplZasPiel", query = "SELECT p FROM Place p WHERE p.lplZasPiel = :lplZasPiel"),
    @NamedQuery(name = "Place.findByLplZasWych", query = "SELECT p FROM Place p WHERE p.lplZasWych = :lplZasWych"),
    @NamedQuery(name = "Place.findByLplStWyp", query = "SELECT p FROM Place p WHERE p.lplStWyp = :lplStWyp"),
    @NamedQuery(name = "Place.findByLplPotrInne1Pdst", query = "SELECT p FROM Place p WHERE p.lplPotrInne1Pdst = :lplPotrInne1Pdst"),
    @NamedQuery(name = "Place.findByLplPotrInne2Pdst", query = "SELECT p FROM Place p WHERE p.lplPotrInne2Pdst = :lplPotrInne2Pdst"),
    @NamedQuery(name = "Place.findByLplPotrInne3Pdst", query = "SELECT p FROM Place p WHERE p.lplPotrInne3Pdst = :lplPotrInne3Pdst"),
    @NamedQuery(name = "Place.findByLplPotrInne4Pdst", query = "SELECT p FROM Place p WHERE p.lplPotrInne4Pdst = :lplPotrInne4Pdst"),
    @NamedQuery(name = "Place.findByLplZasRodzPdst", query = "SELECT p FROM Place p WHERE p.lplZasRodzPdst = :lplZasRodzPdst"),
    @NamedQuery(name = "Place.findByLplZasPielPdst", query = "SELECT p FROM Place p WHERE p.lplZasPielPdst = :lplZasPielPdst"),
    @NamedQuery(name = "Place.findByLplZasWychPdst", query = "SELECT p FROM Place p WHERE p.lplZasWychPdst = :lplZasWychPdst"),
    @NamedQuery(name = "Place.findByLplWynForma", query = "SELECT p FROM Place p WHERE p.lplWynForma = :lplWynForma"),
    @NamedQuery(name = "Place.findByLplKonto", query = "SELECT p FROM Place p WHERE p.lplKonto = :lplKonto"),
    @NamedQuery(name = "Place.findByLplPdstChorWyp", query = "SELECT p FROM Place p WHERE p.lplPdstChorWyp = :lplPdstChorWyp"),
    @NamedQuery(name = "Place.findByLplStFp", query = "SELECT p FROM Place p WHERE p.lplStFp = :lplStFp"),
    @NamedQuery(name = "Place.findByLplStFgsp", query = "SELECT p FROM Place p WHERE p.lplStFgsp = :lplStFgsp"),
    @NamedQuery(name = "Place.findByLplZusFp", query = "SELECT p FROM Place p WHERE p.lplZusFp = :lplZusFp"),
    @NamedQuery(name = "Place.findByLplZusFgsp", query = "SELECT p FROM Place p WHERE p.lplZusFgsp = :lplZusFgsp"),
    @NamedQuery(name = "Place.findByLplPdstZusRed", query = "SELECT p FROM Place p WHERE p.lplPdstZusRed = :lplPdstZusRed"),
    @NamedQuery(name = "Place.findByLplZlec", query = "SELECT p FROM Place p WHERE p.lplZlec = :lplZlec"),
    @NamedQuery(name = "Place.findByLplZasRodzIlosc", query = "SELECT p FROM Place p WHERE p.lplZasRodzIlosc = :lplZasRodzIlosc"),
    @NamedQuery(name = "Place.findByLplZasPielIlosc", query = "SELECT p FROM Place p WHERE p.lplZasPielIlosc = :lplZasPielIlosc"),
    @NamedQuery(name = "Place.findByLplUbEmUbez", query = "SELECT p FROM Place p WHERE p.lplUbEmUbez = :lplUbEmUbez"),
    @NamedQuery(name = "Place.findByLplUbEmPrac", query = "SELECT p FROM Place p WHERE p.lplUbEmPrac = :lplUbEmPrac"),
    @NamedQuery(name = "Place.findByLplUbEmBudz", query = "SELECT p FROM Place p WHERE p.lplUbEmBudz = :lplUbEmBudz"),
    @NamedQuery(name = "Place.findByLplUbEmPfron", query = "SELECT p FROM Place p WHERE p.lplUbEmPfron = :lplUbEmPfron"),
    @NamedQuery(name = "Place.findByLplUbReUbez", query = "SELECT p FROM Place p WHERE p.lplUbReUbez = :lplUbReUbez"),
    @NamedQuery(name = "Place.findByLplUbRePrac", query = "SELECT p FROM Place p WHERE p.lplUbRePrac = :lplUbRePrac"),
    @NamedQuery(name = "Place.findByLplUbReBudz", query = "SELECT p FROM Place p WHERE p.lplUbReBudz = :lplUbReBudz"),
    @NamedQuery(name = "Place.findByLplUbRePfron", query = "SELECT p FROM Place p WHERE p.lplUbRePfron = :lplUbRePfron"),
    @NamedQuery(name = "Place.findByLplUbChUbez", query = "SELECT p FROM Place p WHERE p.lplUbChUbez = :lplUbChUbez"),
    @NamedQuery(name = "Place.findByLplUbChPrac", query = "SELECT p FROM Place p WHERE p.lplUbChPrac = :lplUbChPrac"),
    @NamedQuery(name = "Place.findByLplUbChBudz", query = "SELECT p FROM Place p WHERE p.lplUbChBudz = :lplUbChBudz"),
    @NamedQuery(name = "Place.findByLplUbChPfron", query = "SELECT p FROM Place p WHERE p.lplUbChPfron = :lplUbChPfron"),
    @NamedQuery(name = "Place.findByLplUbWyUbez", query = "SELECT p FROM Place p WHERE p.lplUbWyUbez = :lplUbWyUbez"),
    @NamedQuery(name = "Place.findByLplUbWyPrac", query = "SELECT p FROM Place p WHERE p.lplUbWyPrac = :lplUbWyPrac"),
    @NamedQuery(name = "Place.findByLplUbWyBudz", query = "SELECT p FROM Place p WHERE p.lplUbWyBudz = :lplUbWyBudz"),
    @NamedQuery(name = "Place.findByLplUbWyPfron", query = "SELECT p FROM Place p WHERE p.lplUbWyPfron = :lplUbWyPfron"),
    @NamedQuery(name = "Place.findByLplDniPrzepr", query = "SELECT p FROM Place p WHERE p.lplDniPrzepr = :lplDniPrzepr"),
    @NamedQuery(name = "Place.findByLplDniObow", query = "SELECT p FROM Place p WHERE p.lplDniObow = :lplDniObow"),
    @NamedQuery(name = "Place.findByLplUbZdUbez", query = "SELECT p FROM Place p WHERE p.lplUbZdUbez = :lplUbZdUbez"),
    @NamedQuery(name = "Place.findByLplUbZdPrac", query = "SELECT p FROM Place p WHERE p.lplUbZdPrac = :lplUbZdPrac"),
    @NamedQuery(name = "Place.findByLplUbZdBudz", query = "SELECT p FROM Place p WHERE p.lplUbZdBudz = :lplUbZdBudz"),
    @NamedQuery(name = "Place.findByLplUbZdPfron", query = "SELECT p FROM Place p WHERE p.lplUbZdPfron = :lplUbZdPfron"),
    @NamedQuery(name = "Place.findByLplUbFpUbez", query = "SELECT p FROM Place p WHERE p.lplUbFpUbez = :lplUbFpUbez"),
    @NamedQuery(name = "Place.findByLplUbFpPrac", query = "SELECT p FROM Place p WHERE p.lplUbFpPrac = :lplUbFpPrac"),
    @NamedQuery(name = "Place.findByLplUbFpBudz", query = "SELECT p FROM Place p WHERE p.lplUbFpBudz = :lplUbFpBudz"),
    @NamedQuery(name = "Place.findByLplUbFpPfron", query = "SELECT p FROM Place p WHERE p.lplUbFpPfron = :lplUbFpPfron"),
    @NamedQuery(name = "Place.findByLplUbFgUbez", query = "SELECT p FROM Place p WHERE p.lplUbFgUbez = :lplUbFgUbez"),
    @NamedQuery(name = "Place.findByLplUbFgPrac", query = "SELECT p FROM Place p WHERE p.lplUbFgPrac = :lplUbFgPrac"),
    @NamedQuery(name = "Place.findByLplUbFgBudz", query = "SELECT p FROM Place p WHERE p.lplUbFgBudz = :lplUbFgBudz"),
    @NamedQuery(name = "Place.findByLplUbFgPfron", query = "SELECT p FROM Place p WHERE p.lplUbFgPfron = :lplUbFgPfron"),
    @NamedQuery(name = "Place.findByLplPrzekrRpw", query = "SELECT p FROM Place p WHERE p.lplPrzekrRpw = :lplPrzekrRpw"),
    @NamedQuery(name = "Place.findByLplKodTytU12", query = "SELECT p FROM Place p WHERE p.lplKodTytU12 = :lplKodTytU12"),
    @NamedQuery(name = "Place.findByLplKodTytU3", query = "SELECT p FROM Place p WHERE p.lplKodTytU3 = :lplKodTytU3"),
    @NamedQuery(name = "Place.findByLplKodTytU4", query = "SELECT p FROM Place p WHERE p.lplKodTytU4 = :lplKodTytU4"),
    @NamedQuery(name = "Place.findByLplPdstWyp", query = "SELECT p FROM Place p WHERE p.lplPdstWyp = :lplPdstWyp"),
    @NamedQuery(name = "Place.findByLplDataWplaty", query = "SELECT p FROM Place p WHERE p.lplDataWplaty = :lplDataWplaty"),
    @NamedQuery(name = "Place.findByLplDataWyplaty", query = "SELECT p FROM Place p WHERE p.lplDataWyplaty = :lplDataWyplaty"),
    @NamedQuery(name = "Place.findByLplKwotaDod1", query = "SELECT p FROM Place p WHERE p.lplKwotaDod1 = :lplKwotaDod1"),
    @NamedQuery(name = "Place.findByLplKwotaDod2", query = "SELECT p FROM Place p WHERE p.lplKwotaDod2 = :lplKwotaDod2"),
    @NamedQuery(name = "Place.findByLplKwotaDod3", query = "SELECT p FROM Place p WHERE p.lplKwotaDod3 = :lplKwotaDod3"),
    @NamedQuery(name = "Place.findByLplKwotaDod4", query = "SELECT p FROM Place p WHERE p.lplKwotaDod4 = :lplKwotaDod4"),
    @NamedQuery(name = "Place.findByLplDod1", query = "SELECT p FROM Place p WHERE p.lplDod1 = :lplDod1"),
    @NamedQuery(name = "Place.findByLplDod2", query = "SELECT p FROM Place p WHERE p.lplDod2 = :lplDod2"),
    @NamedQuery(name = "Place.findByLplDod3", query = "SELECT p FROM Place p WHERE p.lplDod3 = :lplDod3"),
    @NamedQuery(name = "Place.findByLplDod4", query = "SELECT p FROM Place p WHERE p.lplDod4 = :lplDod4"),
    @NamedQuery(name = "Place.findByLplDataDod1", query = "SELECT p FROM Place p WHERE p.lplDataDod1 = :lplDataDod1"),
    @NamedQuery(name = "Place.findByLplDataDod2", query = "SELECT p FROM Place p WHERE p.lplDataDod2 = :lplDataDod2"),
    @NamedQuery(name = "Place.findByLplPotracenia", query = "SELECT p FROM Place p WHERE p.lplPotracenia = :lplPotracenia"),
    @NamedQuery(name = "Place.findByLplWynZasadn", query = "SELECT p FROM Place p WHERE p.lplWynZasadn = :lplWynZasadn"),
    @NamedQuery(name = "Place.findByLplKwPrzWynBr", query = "SELECT p FROM Place p WHERE p.lplKwPrzWynBr = :lplKwPrzWynBr"),
    @NamedQuery(name = "Place.findByLplOgrPdstChor", query = "SELECT p FROM Place p WHERE p.lplOgrPdstChor = :lplOgrPdstChor"),
    @NamedQuery(name = "Place.findByLplOgrPdstChorC", query = "SELECT p FROM Place p WHERE p.lplOgrPdstChorC = :lplOgrPdstChorC"),
    @NamedQuery(name = "Place.findByLplZasRodzM", query = "SELECT p FROM Place p WHERE p.lplZasRodzM = :lplZasRodzM"),
    @NamedQuery(name = "Place.findByLplZasWychS", query = "SELECT p FROM Place p WHERE p.lplZasWychS = :lplZasWychS"),
    @NamedQuery(name = "Place.findByLplEtat1", query = "SELECT p FROM Place p WHERE p.lplEtat1 = :lplEtat1"),
    @NamedQuery(name = "Place.findByLplEtat2", query = "SELECT p FROM Place p WHERE p.lplEtat2 = :lplEtat2"),
    @NamedQuery(name = "Place.findByLplEtatDoDra", query = "SELECT p FROM Place p WHERE p.lplEtatDoDra = :lplEtatDoDra"),
    @NamedQuery(name = "Place.findByLplZasWychP3", query = "SELECT p FROM Place p WHERE p.lplZasWychP3 = :lplZasWychP3"),
    @NamedQuery(name = "Place.findByLplPrzekrRpwD", query = "SELECT p FROM Place p WHERE p.lplPrzekrRpwD = :lplPrzekrRpwD"),
    @NamedQuery(name = "Place.findByLplOgrPdstChorW", query = "SELECT p FROM Place p WHERE p.lplOgrPdstChorW = :lplOgrPdstChorW"),
    @NamedQuery(name = "Place.findByLplPrzekrRpwPrz", query = "SELECT p FROM Place p WHERE p.lplPrzekrRpwPrz = :lplPrzekrRpwPrz"),
    @NamedQuery(name = "Place.findByLplPit4", query = "SELECT p FROM Place p WHERE p.lplPit4 = :lplPit4"),
    @NamedQuery(name = "Place.findByLplZasRodzC", query = "SELECT p FROM Place p WHERE p.lplZasRodzC = :lplZasRodzC"),
    @NamedQuery(name = "Place.findByLplZasPielC", query = "SELECT p FROM Place p WHERE p.lplZasPielC = :lplZasPielC"),
    @NamedQuery(name = "Place.findByLplZasWychC", query = "SELECT p FROM Place p WHERE p.lplZasWychC = :lplZasWychC"),
    @NamedQuery(name = "Place.findByLplPrzychOpod", query = "SELECT p FROM Place p WHERE p.lplPrzychOpod = :lplPrzychOpod"),
    @NamedQuery(name = "Place.findByLplOgrZdrowotne", query = "SELECT p FROM Place p WHERE p.lplOgrZdrowotne = :lplOgrZdrowotne"),
    @NamedQuery(name = "Place.findByLplPdstZusDek", query = "SELECT p FROM Place p WHERE p.lplPdstZusDek = :lplPdstZusDek"),
    @NamedQuery(name = "Place.findByLplPdstChWyDek", query = "SELECT p FROM Place p WHERE p.lplPdstChWyDek = :lplPdstChWyDek"),
    @NamedQuery(name = "Place.findByLplPdstZdroDek", query = "SELECT p FROM Place p WHERE p.lplPdstZdroDek = :lplPdstZdroDek"),
    @NamedQuery(name = "Place.findByLplPdstZasChor", query = "SELECT p FROM Place p WHERE p.lplPdstZasChor = :lplPdstZasChor"),
    @NamedQuery(name = "Place.findByLplKwMinWynBr", query = "SELECT p FROM Place p WHERE p.lplKwMinWynBr = :lplKwMinWynBr"),
    @NamedQuery(name = "Place.findByLplPdstZusAR", query = "SELECT p FROM Place p WHERE p.lplPdstZusAR = :lplPdstZusAR"),
    @NamedQuery(name = "Place.findByLplPdstZasChorAR", query = "SELECT p FROM Place p WHERE p.lplPdstZasChorAR = :lplPdstZasChorAR"),
    @NamedQuery(name = "Place.findByLplPdstFp", query = "SELECT p FROM Place p WHERE p.lplPdstFp = :lplPdstFp"),
    @NamedQuery(name = "Place.findByLplPdstFgsp", query = "SELECT p FROM Place p WHERE p.lplPdstFgsp = :lplPdstFgsp"),
    @NamedQuery(name = "Place.findByLplPdstChor", query = "SELECT p FROM Place p WHERE p.lplPdstChor = :lplPdstChor"),
    @NamedQuery(name = "Place.findByLplPdstRent", query = "SELECT p FROM Place p WHERE p.lplPdstRent = :lplPdstRent"),
    @NamedQuery(name = "Place.findByLplPdstRentRed", query = "SELECT p FROM Place p WHERE p.lplPdstRentRed = :lplPdstRentRed"),
    @NamedQuery(name = "Place.findByLplPdstFp0", query = "SELECT p FROM Place p WHERE p.lplPdstFp0 = :lplPdstFp0"),
    @NamedQuery(name = "Place.findByLplOgrZasChor", query = "SELECT p FROM Place p WHERE p.lplOgrZasChor = :lplOgrZasChor"),
    @NamedQuery(name = "Place.findByLplPdstZusDekAR", query = "SELECT p FROM Place p WHERE p.lplPdstZusDekAR = :lplPdstZusDekAR"),
    @NamedQuery(name = "Place.findByLplPdstChorDekAR", query = "SELECT p FROM Place p WHERE p.lplPdstChorDekAR = :lplPdstChorDekAR"),
    @NamedQuery(name = "Place.findByLplPdstZdroDekAR", query = "SELECT p FROM Place p WHERE p.lplPdstZdroDekAR = :lplPdstZdroDekAR"),
    @NamedQuery(name = "Place.findByLplZusEmerDobr", query = "SELECT p FROM Place p WHERE p.lplZusEmerDobr = :lplZusEmerDobr"),
    @NamedQuery(name = "Place.findByLplZusRentDobr", query = "SELECT p FROM Place p WHERE p.lplZusRentDobr = :lplZusRentDobr"),
    @NamedQuery(name = "Place.findByLplZusZdroDobr", query = "SELECT p FROM Place p WHERE p.lplZusZdroDobr = :lplZusZdroDobr"),
    @NamedQuery(name = "Place.findByLplZusWypDobr", query = "SELECT p FROM Place p WHERE p.lplZusWypDobr = :lplZusWypDobr"),
    @NamedQuery(name = "Place.findByLplEmerUbez", query = "SELECT p FROM Place p WHERE p.lplEmerUbez = :lplEmerUbez"),
    @NamedQuery(name = "Place.findByLplEmerPrac", query = "SELECT p FROM Place p WHERE p.lplEmerPrac = :lplEmerPrac"),
    @NamedQuery(name = "Place.findByLplEmerBudz", query = "SELECT p FROM Place p WHERE p.lplEmerBudz = :lplEmerBudz"),
    @NamedQuery(name = "Place.findByLplEmerPfron", query = "SELECT p FROM Place p WHERE p.lplEmerPfron = :lplEmerPfron"),
    @NamedQuery(name = "Place.findByLplRentUbez", query = "SELECT p FROM Place p WHERE p.lplRentUbez = :lplRentUbez"),
    @NamedQuery(name = "Place.findByLplRentPrac", query = "SELECT p FROM Place p WHERE p.lplRentPrac = :lplRentPrac"),
    @NamedQuery(name = "Place.findByLplRentBudz", query = "SELECT p FROM Place p WHERE p.lplRentBudz = :lplRentBudz"),
    @NamedQuery(name = "Place.findByLplRentPfron", query = "SELECT p FROM Place p WHERE p.lplRentPfron = :lplRentPfron"),
    @NamedQuery(name = "Place.findByLplChorUbez", query = "SELECT p FROM Place p WHERE p.lplChorUbez = :lplChorUbez"),
    @NamedQuery(name = "Place.findByLplChorPrac", query = "SELECT p FROM Place p WHERE p.lplChorPrac = :lplChorPrac"),
    @NamedQuery(name = "Place.findByLplChorBudz", query = "SELECT p FROM Place p WHERE p.lplChorBudz = :lplChorBudz"),
    @NamedQuery(name = "Place.findByLplChorPfron", query = "SELECT p FROM Place p WHERE p.lplChorPfron = :lplChorPfron"),
    @NamedQuery(name = "Place.findByLplWypUbez", query = "SELECT p FROM Place p WHERE p.lplWypUbez = :lplWypUbez"),
    @NamedQuery(name = "Place.findByLplWypPrac", query = "SELECT p FROM Place p WHERE p.lplWypPrac = :lplWypPrac"),
    @NamedQuery(name = "Place.findByLplWypBudz", query = "SELECT p FROM Place p WHERE p.lplWypBudz = :lplWypBudz"),
    @NamedQuery(name = "Place.findByLplWypPfron", query = "SELECT p FROM Place p WHERE p.lplWypPfron = :lplWypPfron"),
    @NamedQuery(name = "Place.findByLplZdroUbez", query = "SELECT p FROM Place p WHERE p.lplZdroUbez = :lplZdroUbez"),
    @NamedQuery(name = "Place.findByLplZdroPrac", query = "SELECT p FROM Place p WHERE p.lplZdroPrac = :lplZdroPrac"),
    @NamedQuery(name = "Place.findByLplZdroBudz", query = "SELECT p FROM Place p WHERE p.lplZdroBudz = :lplZdroBudz"),
    @NamedQuery(name = "Place.findByLplZdroPfron", query = "SELECT p FROM Place p WHERE p.lplZdroPfron = :lplZdroPfron"),
    @NamedQuery(name = "Place.findByLplFpUbez", query = "SELECT p FROM Place p WHERE p.lplFpUbez = :lplFpUbez"),
    @NamedQuery(name = "Place.findByLplFpPrac", query = "SELECT p FROM Place p WHERE p.lplFpPrac = :lplFpPrac"),
    @NamedQuery(name = "Place.findByLplFpBudz", query = "SELECT p FROM Place p WHERE p.lplFpBudz = :lplFpBudz"),
    @NamedQuery(name = "Place.findByLplFpPfron", query = "SELECT p FROM Place p WHERE p.lplFpPfron = :lplFpPfron"),
    @NamedQuery(name = "Place.findByLplFgspUbez", query = "SELECT p FROM Place p WHERE p.lplFgspUbez = :lplFgspUbez"),
    @NamedQuery(name = "Place.findByLplFgspPrac", query = "SELECT p FROM Place p WHERE p.lplFgspPrac = :lplFgspPrac"),
    @NamedQuery(name = "Place.findByLplFgspBudz", query = "SELECT p FROM Place p WHERE p.lplFgspBudz = :lplFgspBudz"),
    @NamedQuery(name = "Place.findByLplFgspPfron", query = "SELECT p FROM Place p WHERE p.lplFgspPfron = :lplFgspPfron"),
    @NamedQuery(name = "Place.findByLplStawkiMan", query = "SELECT p FROM Place p WHERE p.lplStawkiMan = :lplStawkiMan"),
    @NamedQuery(name = "Place.findByLplZaliczkaMan", query = "SELECT p FROM Place p WHERE p.lplZaliczkaMan = :lplZaliczkaMan"),
    @NamedQuery(name = "Place.findByLplChar1", query = "SELECT p FROM Place p WHERE p.lplChar1 = :lplChar1"),
    @NamedQuery(name = "Place.findByLplChar2", query = "SELECT p FROM Place p WHERE p.lplChar2 = :lplChar2"),
    @NamedQuery(name = "Place.findByLplChar3", query = "SELECT p FROM Place p WHERE p.lplChar3 = :lplChar3"),
    @NamedQuery(name = "Place.findByLplChar4", query = "SELECT p FROM Place p WHERE p.lplChar4 = :lplChar4"),
    @NamedQuery(name = "Place.findByLplChar5", query = "SELECT p FROM Place p WHERE p.lplChar5 = :lplChar5"),
    @NamedQuery(name = "Place.findByLplChar6", query = "SELECT p FROM Place p WHERE p.lplChar6 = :lplChar6"),
    @NamedQuery(name = "Place.findByLplChar7", query = "SELECT p FROM Place p WHERE p.lplChar7 = :lplChar7"),
    @NamedQuery(name = "Place.findByLplChar8", query = "SELECT p FROM Place p WHERE p.lplChar8 = :lplChar8"),
    @NamedQuery(name = "Place.findByLplKwotaDod5", query = "SELECT p FROM Place p WHERE p.lplKwotaDod5 = :lplKwotaDod5"),
    @NamedQuery(name = "Place.findByLplKwotaDod6", query = "SELECT p FROM Place p WHERE p.lplKwotaDod6 = :lplKwotaDod6"),
    @NamedQuery(name = "Place.findByLplKwotaDod7", query = "SELECT p FROM Place p WHERE p.lplKwotaDod7 = :lplKwotaDod7"),
    @NamedQuery(name = "Place.findByLplKwotaDod8", query = "SELECT p FROM Place p WHERE p.lplKwotaDod8 = :lplKwotaDod8"),
    @NamedQuery(name = "Place.findByLplPodMinZus", query = "SELECT p FROM Place p WHERE p.lplPodMinZus = :lplPodMinZus"),
    @NamedQuery(name = "Place.findByLplZalMinZdro", query = "SELECT p FROM Place p WHERE p.lplZalMinZdro = :lplZalMinZdro"),
    @NamedQuery(name = "Place.findByLplPrzychOpodOdl", query = "SELECT p FROM Place p WHERE p.lplPrzychOpodOdl = :lplPrzychOpodOdl"),
    @NamedQuery(name = "Place.findByLplPodZusKw", query = "SELECT p FROM Place p WHERE p.lplPodZusKw = :lplPodZusKw"),
    @NamedQuery(name = "Place.findByLplTylZusOpod", query = "SELECT p FROM Place p WHERE p.lplTylZusOpod = :lplTylZusOpod"),
    @NamedQuery(name = "Place.findByLplDataDod3", query = "SELECT p FROM Place p WHERE p.lplDataDod3 = :lplDataDod3"),
    @NamedQuery(name = "Place.findByLplDataDod4", query = "SELECT p FROM Place p WHERE p.lplDataDod4 = :lplDataDod4"),
    @NamedQuery(name = "Place.findByLplChar9", query = "SELECT p FROM Place p WHERE p.lplChar9 = :lplChar9"),
    @NamedQuery(name = "Place.findByLplChar10", query = "SELECT p FROM Place p WHERE p.lplChar10 = :lplChar10"),
    @NamedQuery(name = "Place.findByLplChar11", query = "SELECT p FROM Place p WHERE p.lplChar11 = :lplChar11"),
    @NamedQuery(name = "Place.findByLplChar12", query = "SELECT p FROM Place p WHERE p.lplChar12 = :lplChar12"),
    @NamedQuery(name = "Place.findByLplKwotaDod9", query = "SELECT p FROM Place p WHERE p.lplKwotaDod9 = :lplKwotaDod9"),
    @NamedQuery(name = "Place.findByLplKwotaDod10", query = "SELECT p FROM Place p WHERE p.lplKwotaDod10 = :lplKwotaDod10"),
    @NamedQuery(name = "Place.findByLplKwotaDod11", query = "SELECT p FROM Place p WHERE p.lplKwotaDod11 = :lplKwotaDod11"),
    @NamedQuery(name = "Place.findByLplKwotaDod12", query = "SELECT p FROM Place p WHERE p.lplKwotaDod12 = :lplKwotaDod12"),
    @NamedQuery(name = "Place.findByLplPodZdroKw", query = "SELECT p FROM Place p WHERE p.lplPodZdroKw = :lplPodZdroKw"),
    @NamedQuery(name = "Place.findByLplWynForma2", query = "SELECT p FROM Place p WHERE p.lplWynForma2 = :lplWynForma2"),
    @NamedQuery(name = "Place.findByLplWynForma2Kw", query = "SELECT p FROM Place p WHERE p.lplWynForma2Kw = :lplWynForma2Kw"),
    @NamedQuery(name = "Place.findByLplChar13", query = "SELECT p FROM Place p WHERE p.lplChar13 = :lplChar13"),
    @NamedQuery(name = "Place.findByLplChar14", query = "SELECT p FROM Place p WHERE p.lplChar14 = :lplChar14"),
    @NamedQuery(name = "Place.findByLplChar15", query = "SELECT p FROM Place p WHERE p.lplChar15 = :lplChar15"),
    @NamedQuery(name = "Place.findByLplChar16", query = "SELECT p FROM Place p WHERE p.lplChar16 = :lplChar16"),
    @NamedQuery(name = "Place.findByLplWspDod1", query = "SELECT p FROM Place p WHERE p.lplWspDod1 = :lplWspDod1"),
    @NamedQuery(name = "Place.findByLplWspDod2", query = "SELECT p FROM Place p WHERE p.lplWspDod2 = :lplWspDod2"),
    @NamedQuery(name = "Place.findByLplWspDod3", query = "SELECT p FROM Place p WHERE p.lplWspDod3 = :lplWspDod3"),
    @NamedQuery(name = "Place.findByLplWspDod4", query = "SELECT p FROM Place p WHERE p.lplWspDod4 = :lplWspDod4"),
    @NamedQuery(name = "Place.findByLplWspDod5", query = "SELECT p FROM Place p WHERE p.lplWspDod5 = :lplWspDod5"),
    @NamedQuery(name = "Place.findByLplWspDod6", query = "SELECT p FROM Place p WHERE p.lplWspDod6 = :lplWspDod6"),
    @NamedQuery(name = "Place.findByLplWspDod7", query = "SELECT p FROM Place p WHERE p.lplWspDod7 = :lplWspDod7"),
    @NamedQuery(name = "Place.findByLplWspDod8", query = "SELECT p FROM Place p WHERE p.lplWspDod8 = :lplWspDod8"),
    @NamedQuery(name = "Place.findByLplWspDod9", query = "SELECT p FROM Place p WHERE p.lplWspDod9 = :lplWspDod9"),
    @NamedQuery(name = "Place.findByLplWspDod10", query = "SELECT p FROM Place p WHERE p.lplWspDod10 = :lplWspDod10"),
    @NamedQuery(name = "Place.findByLplDataDod5", query = "SELECT p FROM Place p WHERE p.lplDataDod5 = :lplDataDod5"),
    @NamedQuery(name = "Place.findByLplDataDod6", query = "SELECT p FROM Place p WHERE p.lplDataDod6 = :lplDataDod6"),
    @NamedQuery(name = "Place.findByLplIntDod1", query = "SELECT p FROM Place p WHERE p.lplIntDod1 = :lplIntDod1"),
    @NamedQuery(name = "Place.findByLplIntDod2", query = "SELECT p FROM Place p WHERE p.lplIntDod2 = :lplIntDod2"),
    @NamedQuery(name = "Place.findByLplLp", query = "SELECT p FROM Place p WHERE p.lplLp = :lplLp"),
    @NamedQuery(name = "Place.findByLplIntDod3", query = "SELECT p FROM Place p WHERE p.lplIntDod3 = :lplIntDod3"),
    @NamedQuery(name = "Place.findByLplIntDod4", query = "SELECT p FROM Place p WHERE p.lplIntDod4 = :lplIntDod4"),
    @NamedQuery(name = "Place.findByLplKwotaDod13", query = "SELECT p FROM Place p WHERE p.lplKwotaDod13 = :lplKwotaDod13")})
public class Place implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_serial", nullable = false)
    private Integer lplSerial;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_podst", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPodst;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_niezd", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplNiezd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_koszty", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplKoszty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_zal_doch", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplZalDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_potr_inne_3", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPotrInne3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_potr_inne_1", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPotrInne1;
    @Column(name = "lpl_st_emer", precision = 5, scale = 2)
    private BigDecimal lplStEmer;
    @Column(name = "lpl_st_rent", precision = 5, scale = 2)
    private BigDecimal lplStRent;
    @Column(name = "lpl_st_chor", precision = 5, scale = 2)
    private BigDecimal lplStChor;
    @Column(name = "lpl_st_zdro", precision = 5, scale = 2)
    private BigDecimal lplStZdro;
    @Column(name = "lpl_niezd_zus", precision = 13, scale = 2)
    private BigDecimal lplNiezdZus;
    @Column(name = "lpl_potr_inne_2", precision = 13, scale = 2)
    private BigDecimal lplPotrInne2;
    @Column(name = "lpl_potr_inne_4", precision = 13, scale = 2)
    private BigDecimal lplPotrInne4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_zal_stawka", nullable = false, precision = 5, scale = 2)
    private BigDecimal lplZalStawka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_zal_wolna", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplZalWolna;
    @Column(name = "lpl_pdst_pod_doch", precision = 13, scale = 2)
    private BigDecimal lplPdstPodDoch;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_zus", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_zdrowotne", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstZdrowotne;
    @Size(max = 32)
    @Column(name = "lpl_potr_inne_1_opis", length = 32)
    private String lplPotrInne1Opis;
    @Size(max = 32)
    @Column(name = "lpl_potr_inne_2_opis", length = 32)
    private String lplPotrInne2Opis;
    @Size(max = 32)
    @Column(name = "lpl_potr_inne_3_opis", length = 32)
    private String lplPotrInne3Opis;
    @Size(max = 32)
    @Column(name = "lpl_potr_inne_4_opis", length = 32)
    private String lplPotrInne4Opis;
    @Column(name = "lpl_zus_emer")
    private Character lplZusEmer;
    @Column(name = "lpl_zus_rent")
    private Character lplZusRent;
    @Column(name = "lpl_zus_chor")
    private Character lplZusChor;
    @Column(name = "lpl_zus_wyp")
    private Character lplZusWyp;
    @Column(name = "lpl_zus_zdro")
    private Character lplZusZdro;
    @Column(name = "lpl_pod_doch")
    private Character lplPodDoch;
    @Column(name = "lpl_zas_rodz", precision = 13, scale = 2)
    private BigDecimal lplZasRodz;
    @Column(name = "lpl_zas_piel", precision = 13, scale = 2)
    private BigDecimal lplZasPiel;
    @Column(name = "lpl_zas_wych", precision = 13, scale = 2)
    private BigDecimal lplZasWych;
    @Column(name = "lpl_st_wyp", precision = 5, scale = 2)
    private BigDecimal lplStWyp;
    @Size(max = 22)
    @Column(name = "lpl_potr_inne_1_pdst", length = 22)
    private String lplPotrInne1Pdst;
    @Size(max = 22)
    @Column(name = "lpl_potr_inne_2_pdst", length = 22)
    private String lplPotrInne2Pdst;
    @Size(max = 16)
    @Column(name = "lpl_potr_inne_3_pdst", length = 16)
    private String lplPotrInne3Pdst;
    @Size(max = 16)
    @Column(name = "lpl_potr_inne_4_pdst", length = 16)
    private String lplPotrInne4Pdst;
    @Size(max = 16)
    @Column(name = "lpl_zas_rodz_pdst", length = 16)
    private String lplZasRodzPdst;
    @Size(max = 16)
    @Column(name = "lpl_zas_piel_pdst", length = 16)
    private String lplZasPielPdst;
    @Size(max = 16)
    @Column(name = "lpl_zas_wych_pdst", length = 16)
    private String lplZasWychPdst;
    @Column(name = "lpl_wyn_forma")
    private Short lplWynForma;
    @Size(max = 64)
    @Column(name = "lpl_konto", length = 64)
    private String lplKonto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_chor_wyp", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstChorWyp;
    @Column(name = "lpl_st_fp", precision = 5, scale = 2)
    private BigDecimal lplStFp;
    @Column(name = "lpl_st_fgsp", precision = 5, scale = 2)
    private BigDecimal lplStFgsp;
    @Column(name = "lpl_zus_fp")
    private Character lplZusFp;
    @Column(name = "lpl_zus_fgsp")
    private Character lplZusFgsp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_zus_red", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstZusRed;
    @Column(name = "lpl_zlec", precision = 13, scale = 2)
    private BigDecimal lplZlec;
    @Column(name = "lpl_zas_rodz_ilosc")
    private Short lplZasRodzIlosc;
    @Column(name = "lpl_zas_piel_ilosc")
    private Short lplZasPielIlosc;
    @Column(name = "lpl_ub_em_ubez", precision = 5, scale = 2)
    private BigDecimal lplUbEmUbez;
    @Column(name = "lpl_ub_em_prac", precision = 5, scale = 2)
    private BigDecimal lplUbEmPrac;
    @Column(name = "lpl_ub_em_budz", precision = 5, scale = 2)
    private BigDecimal lplUbEmBudz;
    @Column(name = "lpl_ub_em_pfron", precision = 5, scale = 2)
    private BigDecimal lplUbEmPfron;
    @Column(name = "lpl_ub_re_ubez", precision = 5, scale = 2)
    private BigDecimal lplUbReUbez;
    @Column(name = "lpl_ub_re_prac", precision = 5, scale = 2)
    private BigDecimal lplUbRePrac;
    @Column(name = "lpl_ub_re_budz", precision = 5, scale = 2)
    private BigDecimal lplUbReBudz;
    @Column(name = "lpl_ub_re_pfron", precision = 5, scale = 2)
    private BigDecimal lplUbRePfron;
    @Column(name = "lpl_ub_ch_ubez", precision = 5, scale = 2)
    private BigDecimal lplUbChUbez;
    @Column(name = "lpl_ub_ch_prac", precision = 5, scale = 2)
    private BigDecimal lplUbChPrac;
    @Column(name = "lpl_ub_ch_budz", precision = 5, scale = 2)
    private BigDecimal lplUbChBudz;
    @Column(name = "lpl_ub_ch_pfron", precision = 5, scale = 2)
    private BigDecimal lplUbChPfron;
    @Column(name = "lpl_ub_wy_ubez", precision = 5, scale = 2)
    private BigDecimal lplUbWyUbez;
    @Column(name = "lpl_ub_wy_prac", precision = 5, scale = 2)
    private BigDecimal lplUbWyPrac;
    @Column(name = "lpl_ub_wy_budz", precision = 5, scale = 2)
    private BigDecimal lplUbWyBudz;
    @Column(name = "lpl_ub_wy_pfron", precision = 5, scale = 2)
    private BigDecimal lplUbWyPfron;
    @Column(name = "lpl_dni_przepr")
    private Short lplDniPrzepr;
    @Column(name = "lpl_dni_obow")
    private Short lplDniObow;
    @Column(name = "lpl_ub_zd_ubez", precision = 5, scale = 2)
    private BigDecimal lplUbZdUbez;
    @Column(name = "lpl_ub_zd_prac", precision = 5, scale = 2)
    private BigDecimal lplUbZdPrac;
    @Column(name = "lpl_ub_zd_budz", precision = 5, scale = 2)
    private BigDecimal lplUbZdBudz;
    @Column(name = "lpl_ub_zd_pfron", precision = 5, scale = 2)
    private BigDecimal lplUbZdPfron;
    @Column(name = "lpl_ub_fp_ubez", precision = 5, scale = 2)
    private BigDecimal lplUbFpUbez;
    @Column(name = "lpl_ub_fp_prac", precision = 5, scale = 2)
    private BigDecimal lplUbFpPrac;
    @Column(name = "lpl_ub_fp_budz", precision = 5, scale = 2)
    private BigDecimal lplUbFpBudz;
    @Column(name = "lpl_ub_fp_pfron", precision = 5, scale = 2)
    private BigDecimal lplUbFpPfron;
    @Column(name = "lpl_ub_fg_ubez", precision = 5, scale = 2)
    private BigDecimal lplUbFgUbez;
    @Column(name = "lpl_ub_fg_prac", precision = 5, scale = 2)
    private BigDecimal lplUbFgPrac;
    @Column(name = "lpl_ub_fg_budz", precision = 5, scale = 2)
    private BigDecimal lplUbFgBudz;
    @Column(name = "lpl_ub_fg_pfron", precision = 5, scale = 2)
    private BigDecimal lplUbFgPfron;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_przekr_rpw", nullable = false)
    private Character lplPrzekrRpw;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 8)
    @Column(name = "lpl_kod_tyt_u_1_2", nullable = false, length = 8)
    private String lplKodTytU12;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_kod_tyt_u_3", nullable = false)
    private Character lplKodTytU3;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_kod_tyt_u_4", nullable = false)
    private Character lplKodTytU4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_wyp", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstWyp;
    @Column(name = "lpl_data_wplaty")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lplDataWplaty;
    @Column(name = "lpl_data_wyplaty")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lplDataWyplaty;
    @Column(name = "lpl_kwota_dod_1", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod1;
    @Column(name = "lpl_kwota_dod_2", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod2;
    @Column(name = "lpl_kwota_dod_3", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod3;
    @Column(name = "lpl_kwota_dod_4", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod4;
    @Size(max = 32)
    @Column(name = "lpl_dod_1", length = 32)
    private String lplDod1;
    @Size(max = 32)
    @Column(name = "lpl_dod_2", length = 32)
    private String lplDod2;
    @Size(max = 32)
    @Column(name = "lpl_dod_3", length = 32)
    private String lplDod3;
    @Size(max = 32)
    @Column(name = "lpl_dod_4", length = 32)
    private String lplDod4;
    @Column(name = "lpl_data_dod_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lplDataDod1;
    @Column(name = "lpl_data_dod_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lplDataDod2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_potracenia", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPotracenia;
    @Column(name = "lpl_wyn_zasadn", precision = 13, scale = 2)
    private BigDecimal lplWynZasadn;
    @Column(name = "lpl_kw_prz_wyn_br", precision = 13, scale = 2)
    private BigDecimal lplKwPrzWynBr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_ogr_pdst_chor", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplOgrPdstChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_ogr_pdst_chor_c", nullable = false)
    private Character lplOgrPdstChorC;
    @Column(name = "lpl_zas_rodz_m")
    private Character lplZasRodzM;
    @Column(name = "lpl_zas_wych_s")
    private Character lplZasWychS;
    @Column(name = "lpl_etat_1")
    private Short lplEtat1;
    @Column(name = "lpl_etat_2")
    private Short lplEtat2;
    @Column(name = "lpl_etat_do_dra")
    private Character lplEtatDoDra;
    @Column(name = "lpl_zas_wych_p_3")
    private Character lplZasWychP3;
    @Column(name = "lpl_przekr_rpw_d")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lplPrzekrRpwD;
    @Column(name = "lpl_ogr_pdst_chor_w", precision = 7, scale = 2)
    private BigDecimal lplOgrPdstChorW;
    @Column(name = "lpl_przekr_rpw_prz")
    private Character lplPrzekrRpwPrz;
    @Column(name = "lpl_pit_4")
    private Character lplPit4;
    @Column(name = "lpl_zas_rodz_c")
    private Character lplZasRodzC;
    @Column(name = "lpl_zas_piel_c")
    private Character lplZasPielC;
    @Column(name = "lpl_zas_wych_c")
    private Character lplZasWychC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_przych_opod", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPrzychOpod;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_ogr_zdrowotne", nullable = false)
    private Character lplOgrZdrowotne;
    @Column(name = "lpl_pdst_zus_dek", precision = 13, scale = 2)
    private BigDecimal lplPdstZusDek;
    @Column(name = "lpl_pdst_ch_wy_dek", precision = 13, scale = 2)
    private BigDecimal lplPdstChWyDek;
    @Column(name = "lpl_pdst_zdro_dek", precision = 13, scale = 2)
    private BigDecimal lplPdstZdroDek;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_zas_chor", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstZasChor;
    @Column(name = "lpl_kw_min_wyn_br", precision = 13, scale = 2)
    private BigDecimal lplKwMinWynBr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_zus_a_r", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstZusAR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_zas_chor_a_r", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstZasChorAR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_fp", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstFp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_fgsp", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstFgsp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_chor", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_rent", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstRent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_rent_red", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPdstRentRed;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pdst_fp_0", nullable = false)
    private Character lplPdstFp0;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_ogr_zas_chor", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplOgrZasChor;
    @Column(name = "lpl_pdst_zus_dek_a_r", precision = 13, scale = 2)
    private BigDecimal lplPdstZusDekAR;
    @Column(name = "lpl_pdst_chor_dek_a_r", precision = 13, scale = 2)
    private BigDecimal lplPdstChorDekAR;
    @Column(name = "lpl_pdst_zdro_dek_a_r", precision = 13, scale = 2)
    private BigDecimal lplPdstZdroDekAR;
    @Column(name = "lpl_zus_emer_dobr")
    private Character lplZusEmerDobr;
    @Column(name = "lpl_zus_rent_dobr")
    private Character lplZusRentDobr;
    @Column(name = "lpl_zus_zdro_dobr")
    private Character lplZusZdroDobr;
    @Column(name = "lpl_zus_wyp_dobr")
    private Character lplZusWypDobr;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_emer_ubez", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplEmerUbez;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_emer_prac", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplEmerPrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_emer_budz", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplEmerBudz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_emer_pfron", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplEmerPfron;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_rent_ubez", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplRentUbez;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_rent_prac", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplRentPrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_rent_budz", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplRentBudz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_rent_pfron", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplRentPfron;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_chor_ubez", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplChorUbez;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_chor_prac", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplChorPrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_chor_budz", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplChorBudz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_chor_pfron", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplChorPfron;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_wyp_ubez", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplWypUbez;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_wyp_prac", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplWypPrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_wyp_budz", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplWypBudz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_wyp_pfron", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplWypPfron;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_zdro_ubez", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplZdroUbez;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_zdro_prac", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplZdroPrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_zdro_budz", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplZdroBudz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_zdro_pfron", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplZdroPfron;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_fp_ubez", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplFpUbez;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_fp_prac", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplFpPrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_fp_budz", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplFpBudz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_fp_pfron", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplFpPfron;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_fgsp_ubez", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplFgspUbez;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_fgsp_prac", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplFgspPrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_fgsp_budz", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplFgspBudz;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_fgsp_pfron", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplFgspPfron;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_stawki_man", nullable = false)
    private Character lplStawkiMan;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_zaliczka_man", nullable = false)
    private Character lplZaliczkaMan;
    @Column(name = "lpl_char_1")
    private Character lplChar1;
    @Column(name = "lpl_char_2")
    private Character lplChar2;
    @Column(name = "lpl_char_3")
    private Character lplChar3;
    @Column(name = "lpl_char_4")
    private Character lplChar4;
    @Column(name = "lpl_char_5")
    private Character lplChar5;
    @Column(name = "lpl_char_6")
    private Character lplChar6;
    @Column(name = "lpl_char_7")
    private Character lplChar7;
    @Column(name = "lpl_char_8")
    private Character lplChar8;
    @Column(name = "lpl_kwota_dod_5", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod5;
    @Column(name = "lpl_kwota_dod_6", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod6;
    @Column(name = "lpl_kwota_dod_7", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod7;
    @Column(name = "lpl_kwota_dod_8", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod8;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pod_min_zus", nullable = false)
    private Character lplPodMinZus;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_zal_min_zdro", nullable = false)
    private Character lplZalMinZdro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_przych_opod_odl", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPrzychOpodOdl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pod_zus_kw", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPodZusKw;
    @Column(name = "lpl_tyl_zus_opod")
    private Character lplTylZusOpod;
    @Column(name = "lpl_data_dod_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lplDataDod3;
    @Column(name = "lpl_data_dod_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lplDataDod4;
    @Column(name = "lpl_char_9")
    private Character lplChar9;
    @Column(name = "lpl_char_10")
    private Character lplChar10;
    @Column(name = "lpl_char_11")
    private Character lplChar11;
    @Column(name = "lpl_char_12")
    private Character lplChar12;
    @Column(name = "lpl_kwota_dod_9", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod9;
    @Column(name = "lpl_kwota_dod_10", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod10;
    @Column(name = "lpl_kwota_dod_11", precision = 17, scale = 6)
    private BigDecimal lplKwotaDod11;
    @Column(name = "lpl_kwota_dod_12", precision = 17, scale = 6)
    private BigDecimal lplKwotaDod12;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_pod_zdro_kw", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplPodZdroKw;
    @Column(name = "lpl_wyn_forma_2")
    private Short lplWynForma2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "lpl_wyn_forma_2_kw", nullable = false, precision = 13, scale = 2)
    private BigDecimal lplWynForma2Kw;
    @Column(name = "lpl_char_13")
    private Character lplChar13;
    @Column(name = "lpl_char_14")
    private Character lplChar14;
    @Column(name = "lpl_char_15")
    private Character lplChar15;
    @Column(name = "lpl_char_16")
    private Character lplChar16;
    @Column(name = "lpl_wsp_dod_1", precision = 5, scale = 2)
    private BigDecimal lplWspDod1;
    @Column(name = "lpl_wsp_dod_2", precision = 5, scale = 2)
    private BigDecimal lplWspDod2;
    @Column(name = "lpl_wsp_dod_3", precision = 5, scale = 2)
    private BigDecimal lplWspDod3;
    @Column(name = "lpl_wsp_dod_4", precision = 5, scale = 2)
    private BigDecimal lplWspDod4;
    @Column(name = "lpl_wsp_dod_5", precision = 5, scale = 2)
    private BigDecimal lplWspDod5;
    @Column(name = "lpl_wsp_dod_6", precision = 5, scale = 2)
    private BigDecimal lplWspDod6;
    @Column(name = "lpl_wsp_dod_7", precision = 5, scale = 2)
    private BigDecimal lplWspDod7;
    @Column(name = "lpl_wsp_dod_8", precision = 5, scale = 2)
    private BigDecimal lplWspDod8;
    @Column(name = "lpl_wsp_dod_9", precision = 5, scale = 2)
    private BigDecimal lplWspDod9;
    @Column(name = "lpl_wsp_dod_10", precision = 5, scale = 2)
    private BigDecimal lplWspDod10;
    @Column(name = "lpl_data_dod_5")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lplDataDod5;
    @Column(name = "lpl_data_dod_6")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lplDataDod6;
    @Column(name = "lpl_int_dod_1")
    private Integer lplIntDod1;
    @Column(name = "lpl_int_dod_2")
    private Integer lplIntDod2;
    @Column(name = "lpl_lp")
    private Integer lplLp;
    @Column(name = "lpl_int_dod_3")
    private Integer lplIntDod3;
    @Column(name = "lpl_int_dod_4")
    private Integer lplIntDod4;
    @Column(name = "lpl_kwota_dod_13", precision = 13, scale = 2)
    private BigDecimal lplKwotaDod13;
    @OneToMany(mappedBy = "sklLplSerial")
    private List<PlaceSkl> placeSklList;
    @OneToMany(mappedBy = "dslLplSerial")
    private List<DaneStatL> daneStatLList;
    @OneToMany(mappedBy = "pzzLplSerial")
    private List<PlacePrzZus> placePrzZusList;
    @OneToMany(mappedBy = "przLplSerial")
    private List<PlacePrz> placePrzList;
    @JoinColumn(name = "lpl_ban_serial", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank lplBanSerial;
    @JoinColumn(name = "lpl_lis_serial", referencedColumnName = "lis_serial")
    @ManyToOne
    private Listy lplLisSerial;
    @JoinColumn(name = "lpl_okr_serial", referencedColumnName = "okr_serial", nullable = false)
    @ManyToOne(optional = false)
    private Okres lplOkrSerial;
    @JoinColumn(name = "lpl_oso_serial", referencedColumnName = "oso_serial", nullable = false)
    @ManyToOne(optional = false)
    private Osoba lplOsoSerial;
    @OneToMany(mappedBy = "pzlLplSerial")
    private List<PlaceZlec> placeZlecList;
    @OneToMany(mappedBy = "ppoLplSerial")
    private List<PlacePot> placePotList;

    public Place() {
    }

    public Place(Integer lplSerial) {
        this.lplSerial = lplSerial;
    }

    public Place(Integer lplSerial, BigDecimal lplPodst, BigDecimal lplNiezd, BigDecimal lplKoszty, BigDecimal lplZalDoch, BigDecimal lplPotrInne3, BigDecimal lplPotrInne1, BigDecimal lplZalStawka, BigDecimal lplZalWolna, BigDecimal lplPdstZus, BigDecimal lplPdstZdrowotne, BigDecimal lplPdstChorWyp, BigDecimal lplPdstZusRed, Character lplPrzekrRpw, String lplKodTytU12, Character lplKodTytU3, Character lplKodTytU4, BigDecimal lplPdstWyp, BigDecimal lplPotracenia, BigDecimal lplOgrPdstChor, Character lplOgrPdstChorC, BigDecimal lplPrzychOpod, Character lplOgrZdrowotne, BigDecimal lplPdstZasChor, BigDecimal lplPdstZusAR, BigDecimal lplPdstZasChorAR, BigDecimal lplPdstFp, BigDecimal lplPdstFgsp, BigDecimal lplPdstChor, BigDecimal lplPdstRent, BigDecimal lplPdstRentRed, Character lplPdstFp0, BigDecimal lplOgrZasChor, BigDecimal lplEmerUbez, BigDecimal lplEmerPrac, BigDecimal lplEmerBudz, BigDecimal lplEmerPfron, BigDecimal lplRentUbez, BigDecimal lplRentPrac, BigDecimal lplRentBudz, BigDecimal lplRentPfron, BigDecimal lplChorUbez, BigDecimal lplChorPrac, BigDecimal lplChorBudz, BigDecimal lplChorPfron, BigDecimal lplWypUbez, BigDecimal lplWypPrac, BigDecimal lplWypBudz, BigDecimal lplWypPfron, BigDecimal lplZdroUbez, BigDecimal lplZdroPrac, BigDecimal lplZdroBudz, BigDecimal lplZdroPfron, BigDecimal lplFpUbez, BigDecimal lplFpPrac, BigDecimal lplFpBudz, BigDecimal lplFpPfron, BigDecimal lplFgspUbez, BigDecimal lplFgspPrac, BigDecimal lplFgspBudz, BigDecimal lplFgspPfron, Character lplStawkiMan, Character lplZaliczkaMan, Character lplPodMinZus, Character lplZalMinZdro, BigDecimal lplPrzychOpodOdl, BigDecimal lplPodZusKw, BigDecimal lplPodZdroKw, BigDecimal lplWynForma2Kw) {
        this.lplSerial = lplSerial;
        this.lplPodst = lplPodst;
        this.lplNiezd = lplNiezd;
        this.lplKoszty = lplKoszty;
        this.lplZalDoch = lplZalDoch;
        this.lplPotrInne3 = lplPotrInne3;
        this.lplPotrInne1 = lplPotrInne1;
        this.lplZalStawka = lplZalStawka;
        this.lplZalWolna = lplZalWolna;
        this.lplPdstZus = lplPdstZus;
        this.lplPdstZdrowotne = lplPdstZdrowotne;
        this.lplPdstChorWyp = lplPdstChorWyp;
        this.lplPdstZusRed = lplPdstZusRed;
        this.lplPrzekrRpw = lplPrzekrRpw;
        this.lplKodTytU12 = lplKodTytU12;
        this.lplKodTytU3 = lplKodTytU3;
        this.lplKodTytU4 = lplKodTytU4;
        this.lplPdstWyp = lplPdstWyp;
        this.lplPotracenia = lplPotracenia;
        this.lplOgrPdstChor = lplOgrPdstChor;
        this.lplOgrPdstChorC = lplOgrPdstChorC;
        this.lplPrzychOpod = lplPrzychOpod;
        this.lplOgrZdrowotne = lplOgrZdrowotne;
        this.lplPdstZasChor = lplPdstZasChor;
        this.lplPdstZusAR = lplPdstZusAR;
        this.lplPdstZasChorAR = lplPdstZasChorAR;
        this.lplPdstFp = lplPdstFp;
        this.lplPdstFgsp = lplPdstFgsp;
        this.lplPdstChor = lplPdstChor;
        this.lplPdstRent = lplPdstRent;
        this.lplPdstRentRed = lplPdstRentRed;
        this.lplPdstFp0 = lplPdstFp0;
        this.lplOgrZasChor = lplOgrZasChor;
        this.lplEmerUbez = lplEmerUbez;
        this.lplEmerPrac = lplEmerPrac;
        this.lplEmerBudz = lplEmerBudz;
        this.lplEmerPfron = lplEmerPfron;
        this.lplRentUbez = lplRentUbez;
        this.lplRentPrac = lplRentPrac;
        this.lplRentBudz = lplRentBudz;
        this.lplRentPfron = lplRentPfron;
        this.lplChorUbez = lplChorUbez;
        this.lplChorPrac = lplChorPrac;
        this.lplChorBudz = lplChorBudz;
        this.lplChorPfron = lplChorPfron;
        this.lplWypUbez = lplWypUbez;
        this.lplWypPrac = lplWypPrac;
        this.lplWypBudz = lplWypBudz;
        this.lplWypPfron = lplWypPfron;
        this.lplZdroUbez = lplZdroUbez;
        this.lplZdroPrac = lplZdroPrac;
        this.lplZdroBudz = lplZdroBudz;
        this.lplZdroPfron = lplZdroPfron;
        this.lplFpUbez = lplFpUbez;
        this.lplFpPrac = lplFpPrac;
        this.lplFpBudz = lplFpBudz;
        this.lplFpPfron = lplFpPfron;
        this.lplFgspUbez = lplFgspUbez;
        this.lplFgspPrac = lplFgspPrac;
        this.lplFgspBudz = lplFgspBudz;
        this.lplFgspPfron = lplFgspPfron;
        this.lplStawkiMan = lplStawkiMan;
        this.lplZaliczkaMan = lplZaliczkaMan;
        this.lplPodMinZus = lplPodMinZus;
        this.lplZalMinZdro = lplZalMinZdro;
        this.lplPrzychOpodOdl = lplPrzychOpodOdl;
        this.lplPodZusKw = lplPodZusKw;
        this.lplPodZdroKw = lplPodZdroKw;
        this.lplWynForma2Kw = lplWynForma2Kw;
    }

    public Integer getLplSerial() {
        return lplSerial;
    }

    public void setLplSerial(Integer lplSerial) {
        this.lplSerial = lplSerial;
    }

    public BigDecimal getLplPodst() {
        return lplPodst;
    }

    public void setLplPodst(BigDecimal lplPodst) {
        this.lplPodst = lplPodst;
    }

    public BigDecimal getLplNiezd() {
        return lplNiezd;
    }

    public void setLplNiezd(BigDecimal lplNiezd) {
        this.lplNiezd = lplNiezd;
    }

    public BigDecimal getLplKoszty() {
        return lplKoszty;
    }

    public void setLplKoszty(BigDecimal lplKoszty) {
        this.lplKoszty = lplKoszty;
    }

    public BigDecimal getLplZalDoch() {
        return lplZalDoch;
    }

    public void setLplZalDoch(BigDecimal lplZalDoch) {
        this.lplZalDoch = lplZalDoch;
    }

    public BigDecimal getLplPotrInne3() {
        return lplPotrInne3;
    }

    public void setLplPotrInne3(BigDecimal lplPotrInne3) {
        this.lplPotrInne3 = lplPotrInne3;
    }

    public BigDecimal getLplPotrInne1() {
        return lplPotrInne1;
    }

    public void setLplPotrInne1(BigDecimal lplPotrInne1) {
        this.lplPotrInne1 = lplPotrInne1;
    }

    public BigDecimal getLplStEmer() {
        return lplStEmer;
    }

    public void setLplStEmer(BigDecimal lplStEmer) {
        this.lplStEmer = lplStEmer;
    }

    public BigDecimal getLplStRent() {
        return lplStRent;
    }

    public void setLplStRent(BigDecimal lplStRent) {
        this.lplStRent = lplStRent;
    }

    public BigDecimal getLplStChor() {
        return lplStChor;
    }

    public void setLplStChor(BigDecimal lplStChor) {
        this.lplStChor = lplStChor;
    }

    public BigDecimal getLplStZdro() {
        return lplStZdro;
    }

    public void setLplStZdro(BigDecimal lplStZdro) {
        this.lplStZdro = lplStZdro;
    }

    public BigDecimal getLplNiezdZus() {
        return lplNiezdZus;
    }

    public void setLplNiezdZus(BigDecimal lplNiezdZus) {
        this.lplNiezdZus = lplNiezdZus;
    }

    public BigDecimal getLplPotrInne2() {
        return lplPotrInne2;
    }

    public void setLplPotrInne2(BigDecimal lplPotrInne2) {
        this.lplPotrInne2 = lplPotrInne2;
    }

    public BigDecimal getLplPotrInne4() {
        return lplPotrInne4;
    }

    public void setLplPotrInne4(BigDecimal lplPotrInne4) {
        this.lplPotrInne4 = lplPotrInne4;
    }

    public BigDecimal getLplZalStawka() {
        return lplZalStawka;
    }

    public void setLplZalStawka(BigDecimal lplZalStawka) {
        this.lplZalStawka = lplZalStawka;
    }

    public BigDecimal getLplZalWolna() {
        return lplZalWolna;
    }

    public void setLplZalWolna(BigDecimal lplZalWolna) {
        this.lplZalWolna = lplZalWolna;
    }

    public BigDecimal getLplPdstPodDoch() {
        return lplPdstPodDoch;
    }

    public void setLplPdstPodDoch(BigDecimal lplPdstPodDoch) {
        this.lplPdstPodDoch = lplPdstPodDoch;
    }

    public BigDecimal getLplPdstZus() {
        return lplPdstZus;
    }

    public void setLplPdstZus(BigDecimal lplPdstZus) {
        this.lplPdstZus = lplPdstZus;
    }

    public BigDecimal getLplPdstZdrowotne() {
        return lplPdstZdrowotne;
    }

    public void setLplPdstZdrowotne(BigDecimal lplPdstZdrowotne) {
        this.lplPdstZdrowotne = lplPdstZdrowotne;
    }

    public String getLplPotrInne1Opis() {
        return lplPotrInne1Opis;
    }

    public void setLplPotrInne1Opis(String lplPotrInne1Opis) {
        this.lplPotrInne1Opis = lplPotrInne1Opis;
    }

    public String getLplPotrInne2Opis() {
        return lplPotrInne2Opis;
    }

    public void setLplPotrInne2Opis(String lplPotrInne2Opis) {
        this.lplPotrInne2Opis = lplPotrInne2Opis;
    }

    public String getLplPotrInne3Opis() {
        return lplPotrInne3Opis;
    }

    public void setLplPotrInne3Opis(String lplPotrInne3Opis) {
        this.lplPotrInne3Opis = lplPotrInne3Opis;
    }

    public String getLplPotrInne4Opis() {
        return lplPotrInne4Opis;
    }

    public void setLplPotrInne4Opis(String lplPotrInne4Opis) {
        this.lplPotrInne4Opis = lplPotrInne4Opis;
    }

    public Character getLplZusEmer() {
        return lplZusEmer;
    }

    public void setLplZusEmer(Character lplZusEmer) {
        this.lplZusEmer = lplZusEmer;
    }

    public Character getLplZusRent() {
        return lplZusRent;
    }

    public void setLplZusRent(Character lplZusRent) {
        this.lplZusRent = lplZusRent;
    }

    public Character getLplZusChor() {
        return lplZusChor;
    }

    public void setLplZusChor(Character lplZusChor) {
        this.lplZusChor = lplZusChor;
    }

    public Character getLplZusWyp() {
        return lplZusWyp;
    }

    public void setLplZusWyp(Character lplZusWyp) {
        this.lplZusWyp = lplZusWyp;
    }

    public Character getLplZusZdro() {
        return lplZusZdro;
    }

    public void setLplZusZdro(Character lplZusZdro) {
        this.lplZusZdro = lplZusZdro;
    }

    public Character getLplPodDoch() {
        return lplPodDoch;
    }

    public void setLplPodDoch(Character lplPodDoch) {
        this.lplPodDoch = lplPodDoch;
    }

    public BigDecimal getLplZasRodz() {
        return lplZasRodz;
    }

    public void setLplZasRodz(BigDecimal lplZasRodz) {
        this.lplZasRodz = lplZasRodz;
    }

    public BigDecimal getLplZasPiel() {
        return lplZasPiel;
    }

    public void setLplZasPiel(BigDecimal lplZasPiel) {
        this.lplZasPiel = lplZasPiel;
    }

    public BigDecimal getLplZasWych() {
        return lplZasWych;
    }

    public void setLplZasWych(BigDecimal lplZasWych) {
        this.lplZasWych = lplZasWych;
    }

    public BigDecimal getLplStWyp() {
        return lplStWyp;
    }

    public void setLplStWyp(BigDecimal lplStWyp) {
        this.lplStWyp = lplStWyp;
    }

    public String getLplPotrInne1Pdst() {
        return lplPotrInne1Pdst;
    }

    public void setLplPotrInne1Pdst(String lplPotrInne1Pdst) {
        this.lplPotrInne1Pdst = lplPotrInne1Pdst;
    }

    public String getLplPotrInne2Pdst() {
        return lplPotrInne2Pdst;
    }

    public void setLplPotrInne2Pdst(String lplPotrInne2Pdst) {
        this.lplPotrInne2Pdst = lplPotrInne2Pdst;
    }

    public String getLplPotrInne3Pdst() {
        return lplPotrInne3Pdst;
    }

    public void setLplPotrInne3Pdst(String lplPotrInne3Pdst) {
        this.lplPotrInne3Pdst = lplPotrInne3Pdst;
    }

    public String getLplPotrInne4Pdst() {
        return lplPotrInne4Pdst;
    }

    public void setLplPotrInne4Pdst(String lplPotrInne4Pdst) {
        this.lplPotrInne4Pdst = lplPotrInne4Pdst;
    }

    public String getLplZasRodzPdst() {
        return lplZasRodzPdst;
    }

    public void setLplZasRodzPdst(String lplZasRodzPdst) {
        this.lplZasRodzPdst = lplZasRodzPdst;
    }

    public String getLplZasPielPdst() {
        return lplZasPielPdst;
    }

    public void setLplZasPielPdst(String lplZasPielPdst) {
        this.lplZasPielPdst = lplZasPielPdst;
    }

    public String getLplZasWychPdst() {
        return lplZasWychPdst;
    }

    public void setLplZasWychPdst(String lplZasWychPdst) {
        this.lplZasWychPdst = lplZasWychPdst;
    }

    public Short getLplWynForma() {
        return lplWynForma;
    }

    public void setLplWynForma(Short lplWynForma) {
        this.lplWynForma = lplWynForma;
    }

    public String getLplKonto() {
        return lplKonto;
    }

    public void setLplKonto(String lplKonto) {
        this.lplKonto = lplKonto;
    }

    public BigDecimal getLplPdstChorWyp() {
        return lplPdstChorWyp;
    }

    public void setLplPdstChorWyp(BigDecimal lplPdstChorWyp) {
        this.lplPdstChorWyp = lplPdstChorWyp;
    }

    public BigDecimal getLplStFp() {
        return lplStFp;
    }

    public void setLplStFp(BigDecimal lplStFp) {
        this.lplStFp = lplStFp;
    }

    public BigDecimal getLplStFgsp() {
        return lplStFgsp;
    }

    public void setLplStFgsp(BigDecimal lplStFgsp) {
        this.lplStFgsp = lplStFgsp;
    }

    public Character getLplZusFp() {
        return lplZusFp;
    }

    public void setLplZusFp(Character lplZusFp) {
        this.lplZusFp = lplZusFp;
    }

    public Character getLplZusFgsp() {
        return lplZusFgsp;
    }

    public void setLplZusFgsp(Character lplZusFgsp) {
        this.lplZusFgsp = lplZusFgsp;
    }

    public BigDecimal getLplPdstZusRed() {
        return lplPdstZusRed;
    }

    public void setLplPdstZusRed(BigDecimal lplPdstZusRed) {
        this.lplPdstZusRed = lplPdstZusRed;
    }

    public BigDecimal getLplZlec() {
        return lplZlec;
    }

    public void setLplZlec(BigDecimal lplZlec) {
        this.lplZlec = lplZlec;
    }

    public Short getLplZasRodzIlosc() {
        return lplZasRodzIlosc;
    }

    public void setLplZasRodzIlosc(Short lplZasRodzIlosc) {
        this.lplZasRodzIlosc = lplZasRodzIlosc;
    }

    public Short getLplZasPielIlosc() {
        return lplZasPielIlosc;
    }

    public void setLplZasPielIlosc(Short lplZasPielIlosc) {
        this.lplZasPielIlosc = lplZasPielIlosc;
    }

    public BigDecimal getLplUbEmUbez() {
        return lplUbEmUbez;
    }

    public void setLplUbEmUbez(BigDecimal lplUbEmUbez) {
        this.lplUbEmUbez = lplUbEmUbez;
    }

    public BigDecimal getLplUbEmPrac() {
        return lplUbEmPrac;
    }

    public void setLplUbEmPrac(BigDecimal lplUbEmPrac) {
        this.lplUbEmPrac = lplUbEmPrac;
    }

    public BigDecimal getLplUbEmBudz() {
        return lplUbEmBudz;
    }

    public void setLplUbEmBudz(BigDecimal lplUbEmBudz) {
        this.lplUbEmBudz = lplUbEmBudz;
    }

    public BigDecimal getLplUbEmPfron() {
        return lplUbEmPfron;
    }

    public void setLplUbEmPfron(BigDecimal lplUbEmPfron) {
        this.lplUbEmPfron = lplUbEmPfron;
    }

    public BigDecimal getLplUbReUbez() {
        return lplUbReUbez;
    }

    public void setLplUbReUbez(BigDecimal lplUbReUbez) {
        this.lplUbReUbez = lplUbReUbez;
    }

    public BigDecimal getLplUbRePrac() {
        return lplUbRePrac;
    }

    public void setLplUbRePrac(BigDecimal lplUbRePrac) {
        this.lplUbRePrac = lplUbRePrac;
    }

    public BigDecimal getLplUbReBudz() {
        return lplUbReBudz;
    }

    public void setLplUbReBudz(BigDecimal lplUbReBudz) {
        this.lplUbReBudz = lplUbReBudz;
    }

    public BigDecimal getLplUbRePfron() {
        return lplUbRePfron;
    }

    public void setLplUbRePfron(BigDecimal lplUbRePfron) {
        this.lplUbRePfron = lplUbRePfron;
    }

    public BigDecimal getLplUbChUbez() {
        return lplUbChUbez;
    }

    public void setLplUbChUbez(BigDecimal lplUbChUbez) {
        this.lplUbChUbez = lplUbChUbez;
    }

    public BigDecimal getLplUbChPrac() {
        return lplUbChPrac;
    }

    public void setLplUbChPrac(BigDecimal lplUbChPrac) {
        this.lplUbChPrac = lplUbChPrac;
    }

    public BigDecimal getLplUbChBudz() {
        return lplUbChBudz;
    }

    public void setLplUbChBudz(BigDecimal lplUbChBudz) {
        this.lplUbChBudz = lplUbChBudz;
    }

    public BigDecimal getLplUbChPfron() {
        return lplUbChPfron;
    }

    public void setLplUbChPfron(BigDecimal lplUbChPfron) {
        this.lplUbChPfron = lplUbChPfron;
    }

    public BigDecimal getLplUbWyUbez() {
        return lplUbWyUbez;
    }

    public void setLplUbWyUbez(BigDecimal lplUbWyUbez) {
        this.lplUbWyUbez = lplUbWyUbez;
    }

    public BigDecimal getLplUbWyPrac() {
        return lplUbWyPrac;
    }

    public void setLplUbWyPrac(BigDecimal lplUbWyPrac) {
        this.lplUbWyPrac = lplUbWyPrac;
    }

    public BigDecimal getLplUbWyBudz() {
        return lplUbWyBudz;
    }

    public void setLplUbWyBudz(BigDecimal lplUbWyBudz) {
        this.lplUbWyBudz = lplUbWyBudz;
    }

    public BigDecimal getLplUbWyPfron() {
        return lplUbWyPfron;
    }

    public void setLplUbWyPfron(BigDecimal lplUbWyPfron) {
        this.lplUbWyPfron = lplUbWyPfron;
    }

    public Short getLplDniPrzepr() {
        return lplDniPrzepr;
    }

    public void setLplDniPrzepr(Short lplDniPrzepr) {
        this.lplDniPrzepr = lplDniPrzepr;
    }

    public Short getLplDniObow() {
        return lplDniObow;
    }

    public void setLplDniObow(Short lplDniObow) {
        this.lplDniObow = lplDniObow;
    }

    public BigDecimal getLplUbZdUbez() {
        return lplUbZdUbez;
    }

    public void setLplUbZdUbez(BigDecimal lplUbZdUbez) {
        this.lplUbZdUbez = lplUbZdUbez;
    }

    public BigDecimal getLplUbZdPrac() {
        return lplUbZdPrac;
    }

    public void setLplUbZdPrac(BigDecimal lplUbZdPrac) {
        this.lplUbZdPrac = lplUbZdPrac;
    }

    public BigDecimal getLplUbZdBudz() {
        return lplUbZdBudz;
    }

    public void setLplUbZdBudz(BigDecimal lplUbZdBudz) {
        this.lplUbZdBudz = lplUbZdBudz;
    }

    public BigDecimal getLplUbZdPfron() {
        return lplUbZdPfron;
    }

    public void setLplUbZdPfron(BigDecimal lplUbZdPfron) {
        this.lplUbZdPfron = lplUbZdPfron;
    }

    public BigDecimal getLplUbFpUbez() {
        return lplUbFpUbez;
    }

    public void setLplUbFpUbez(BigDecimal lplUbFpUbez) {
        this.lplUbFpUbez = lplUbFpUbez;
    }

    public BigDecimal getLplUbFpPrac() {
        return lplUbFpPrac;
    }

    public void setLplUbFpPrac(BigDecimal lplUbFpPrac) {
        this.lplUbFpPrac = lplUbFpPrac;
    }

    public BigDecimal getLplUbFpBudz() {
        return lplUbFpBudz;
    }

    public void setLplUbFpBudz(BigDecimal lplUbFpBudz) {
        this.lplUbFpBudz = lplUbFpBudz;
    }

    public BigDecimal getLplUbFpPfron() {
        return lplUbFpPfron;
    }

    public void setLplUbFpPfron(BigDecimal lplUbFpPfron) {
        this.lplUbFpPfron = lplUbFpPfron;
    }

    public BigDecimal getLplUbFgUbez() {
        return lplUbFgUbez;
    }

    public void setLplUbFgUbez(BigDecimal lplUbFgUbez) {
        this.lplUbFgUbez = lplUbFgUbez;
    }

    public BigDecimal getLplUbFgPrac() {
        return lplUbFgPrac;
    }

    public void setLplUbFgPrac(BigDecimal lplUbFgPrac) {
        this.lplUbFgPrac = lplUbFgPrac;
    }

    public BigDecimal getLplUbFgBudz() {
        return lplUbFgBudz;
    }

    public void setLplUbFgBudz(BigDecimal lplUbFgBudz) {
        this.lplUbFgBudz = lplUbFgBudz;
    }

    public BigDecimal getLplUbFgPfron() {
        return lplUbFgPfron;
    }

    public void setLplUbFgPfron(BigDecimal lplUbFgPfron) {
        this.lplUbFgPfron = lplUbFgPfron;
    }

    public Character getLplPrzekrRpw() {
        return lplPrzekrRpw;
    }

    public void setLplPrzekrRpw(Character lplPrzekrRpw) {
        this.lplPrzekrRpw = lplPrzekrRpw;
    }

    public String getLplKodTytU12() {
        return lplKodTytU12;
    }

    public void setLplKodTytU12(String lplKodTytU12) {
        this.lplKodTytU12 = lplKodTytU12;
    }

    public Character getLplKodTytU3() {
        return lplKodTytU3;
    }

    public void setLplKodTytU3(Character lplKodTytU3) {
        this.lplKodTytU3 = lplKodTytU3;
    }

    public Character getLplKodTytU4() {
        return lplKodTytU4;
    }

    public void setLplKodTytU4(Character lplKodTytU4) {
        this.lplKodTytU4 = lplKodTytU4;
    }

    public BigDecimal getLplPdstWyp() {
        return lplPdstWyp;
    }

    public void setLplPdstWyp(BigDecimal lplPdstWyp) {
        this.lplPdstWyp = lplPdstWyp;
    }

    public Date getLplDataWplaty() {
        return lplDataWplaty;
    }

    public void setLplDataWplaty(Date lplDataWplaty) {
        this.lplDataWplaty = lplDataWplaty;
    }

    public Date getLplDataWyplaty() {
        return lplDataWyplaty;
    }

    public void setLplDataWyplaty(Date lplDataWyplaty) {
        this.lplDataWyplaty = lplDataWyplaty;
    }

    public BigDecimal getLplKwotaDod1() {
        return lplKwotaDod1;
    }

    public void setLplKwotaDod1(BigDecimal lplKwotaDod1) {
        this.lplKwotaDod1 = lplKwotaDod1;
    }

    public BigDecimal getLplKwotaDod2() {
        return lplKwotaDod2;
    }

    public void setLplKwotaDod2(BigDecimal lplKwotaDod2) {
        this.lplKwotaDod2 = lplKwotaDod2;
    }

    public BigDecimal getLplKwotaDod3() {
        return lplKwotaDod3;
    }

    public void setLplKwotaDod3(BigDecimal lplKwotaDod3) {
        this.lplKwotaDod3 = lplKwotaDod3;
    }

    public BigDecimal getLplKwotaDod4() {
        return lplKwotaDod4;
    }

    public void setLplKwotaDod4(BigDecimal lplKwotaDod4) {
        this.lplKwotaDod4 = lplKwotaDod4;
    }

    public String getLplDod1() {
        return lplDod1;
    }

    public void setLplDod1(String lplDod1) {
        this.lplDod1 = lplDod1;
    }

    public String getLplDod2() {
        return lplDod2;
    }

    public void setLplDod2(String lplDod2) {
        this.lplDod2 = lplDod2;
    }

    public String getLplDod3() {
        return lplDod3;
    }

    public void setLplDod3(String lplDod3) {
        this.lplDod3 = lplDod3;
    }

    public String getLplDod4() {
        return lplDod4;
    }

    public void setLplDod4(String lplDod4) {
        this.lplDod4 = lplDod4;
    }

    public Date getLplDataDod1() {
        return lplDataDod1;
    }

    public void setLplDataDod1(Date lplDataDod1) {
        this.lplDataDod1 = lplDataDod1;
    }

    public Date getLplDataDod2() {
        return lplDataDod2;
    }

    public void setLplDataDod2(Date lplDataDod2) {
        this.lplDataDod2 = lplDataDod2;
    }

    public BigDecimal getLplPotracenia() {
        return lplPotracenia;
    }

    public void setLplPotracenia(BigDecimal lplPotracenia) {
        this.lplPotracenia = lplPotracenia;
    }

    public BigDecimal getLplWynZasadn() {
        return lplWynZasadn;
    }

    public void setLplWynZasadn(BigDecimal lplWynZasadn) {
        this.lplWynZasadn = lplWynZasadn;
    }

    public BigDecimal getLplKwPrzWynBr() {
        return lplKwPrzWynBr;
    }

    public void setLplKwPrzWynBr(BigDecimal lplKwPrzWynBr) {
        this.lplKwPrzWynBr = lplKwPrzWynBr;
    }

    public BigDecimal getLplOgrPdstChor() {
        return lplOgrPdstChor;
    }

    public void setLplOgrPdstChor(BigDecimal lplOgrPdstChor) {
        this.lplOgrPdstChor = lplOgrPdstChor;
    }

    public Character getLplOgrPdstChorC() {
        return lplOgrPdstChorC;
    }

    public void setLplOgrPdstChorC(Character lplOgrPdstChorC) {
        this.lplOgrPdstChorC = lplOgrPdstChorC;
    }

    public Character getLplZasRodzM() {
        return lplZasRodzM;
    }

    public void setLplZasRodzM(Character lplZasRodzM) {
        this.lplZasRodzM = lplZasRodzM;
    }

    public Character getLplZasWychS() {
        return lplZasWychS;
    }

    public void setLplZasWychS(Character lplZasWychS) {
        this.lplZasWychS = lplZasWychS;
    }

    public Short getLplEtat1() {
        return lplEtat1;
    }

    public void setLplEtat1(Short lplEtat1) {
        this.lplEtat1 = lplEtat1;
    }

    public Short getLplEtat2() {
        return lplEtat2;
    }

    public void setLplEtat2(Short lplEtat2) {
        this.lplEtat2 = lplEtat2;
    }

    public Character getLplEtatDoDra() {
        return lplEtatDoDra;
    }

    public void setLplEtatDoDra(Character lplEtatDoDra) {
        this.lplEtatDoDra = lplEtatDoDra;
    }

    public Character getLplZasWychP3() {
        return lplZasWychP3;
    }

    public void setLplZasWychP3(Character lplZasWychP3) {
        this.lplZasWychP3 = lplZasWychP3;
    }

    public Date getLplPrzekrRpwD() {
        return lplPrzekrRpwD;
    }

    public void setLplPrzekrRpwD(Date lplPrzekrRpwD) {
        this.lplPrzekrRpwD = lplPrzekrRpwD;
    }

    public BigDecimal getLplOgrPdstChorW() {
        return lplOgrPdstChorW;
    }

    public void setLplOgrPdstChorW(BigDecimal lplOgrPdstChorW) {
        this.lplOgrPdstChorW = lplOgrPdstChorW;
    }

    public Character getLplPrzekrRpwPrz() {
        return lplPrzekrRpwPrz;
    }

    public void setLplPrzekrRpwPrz(Character lplPrzekrRpwPrz) {
        this.lplPrzekrRpwPrz = lplPrzekrRpwPrz;
    }

    public Character getLplPit4() {
        return lplPit4;
    }

    public void setLplPit4(Character lplPit4) {
        this.lplPit4 = lplPit4;
    }

    public Character getLplZasRodzC() {
        return lplZasRodzC;
    }

    public void setLplZasRodzC(Character lplZasRodzC) {
        this.lplZasRodzC = lplZasRodzC;
    }

    public Character getLplZasPielC() {
        return lplZasPielC;
    }

    public void setLplZasPielC(Character lplZasPielC) {
        this.lplZasPielC = lplZasPielC;
    }

    public Character getLplZasWychC() {
        return lplZasWychC;
    }

    public void setLplZasWychC(Character lplZasWychC) {
        this.lplZasWychC = lplZasWychC;
    }

    public BigDecimal getLplPrzychOpod() {
        return lplPrzychOpod;
    }

    public void setLplPrzychOpod(BigDecimal lplPrzychOpod) {
        this.lplPrzychOpod = lplPrzychOpod;
    }

    public Character getLplOgrZdrowotne() {
        return lplOgrZdrowotne;
    }

    public void setLplOgrZdrowotne(Character lplOgrZdrowotne) {
        this.lplOgrZdrowotne = lplOgrZdrowotne;
    }

    public BigDecimal getLplPdstZusDek() {
        return lplPdstZusDek;
    }

    public void setLplPdstZusDek(BigDecimal lplPdstZusDek) {
        this.lplPdstZusDek = lplPdstZusDek;
    }

    public BigDecimal getLplPdstChWyDek() {
        return lplPdstChWyDek;
    }

    public void setLplPdstChWyDek(BigDecimal lplPdstChWyDek) {
        this.lplPdstChWyDek = lplPdstChWyDek;
    }

    public BigDecimal getLplPdstZdroDek() {
        return lplPdstZdroDek;
    }

    public void setLplPdstZdroDek(BigDecimal lplPdstZdroDek) {
        this.lplPdstZdroDek = lplPdstZdroDek;
    }

    public BigDecimal getLplPdstZasChor() {
        return lplPdstZasChor;
    }

    public void setLplPdstZasChor(BigDecimal lplPdstZasChor) {
        this.lplPdstZasChor = lplPdstZasChor;
    }

    public BigDecimal getLplKwMinWynBr() {
        return lplKwMinWynBr;
    }

    public void setLplKwMinWynBr(BigDecimal lplKwMinWynBr) {
        this.lplKwMinWynBr = lplKwMinWynBr;
    }

    public BigDecimal getLplPdstZusAR() {
        return lplPdstZusAR;
    }

    public void setLplPdstZusAR(BigDecimal lplPdstZusAR) {
        this.lplPdstZusAR = lplPdstZusAR;
    }

    public BigDecimal getLplPdstZasChorAR() {
        return lplPdstZasChorAR;
    }

    public void setLplPdstZasChorAR(BigDecimal lplPdstZasChorAR) {
        this.lplPdstZasChorAR = lplPdstZasChorAR;
    }

    public BigDecimal getLplPdstFp() {
        return lplPdstFp;
    }

    public void setLplPdstFp(BigDecimal lplPdstFp) {
        this.lplPdstFp = lplPdstFp;
    }

    public BigDecimal getLplPdstFgsp() {
        return lplPdstFgsp;
    }

    public void setLplPdstFgsp(BigDecimal lplPdstFgsp) {
        this.lplPdstFgsp = lplPdstFgsp;
    }

    public BigDecimal getLplPdstChor() {
        return lplPdstChor;
    }

    public void setLplPdstChor(BigDecimal lplPdstChor) {
        this.lplPdstChor = lplPdstChor;
    }

    public BigDecimal getLplPdstRent() {
        return lplPdstRent;
    }

    public void setLplPdstRent(BigDecimal lplPdstRent) {
        this.lplPdstRent = lplPdstRent;
    }

    public BigDecimal getLplPdstRentRed() {
        return lplPdstRentRed;
    }

    public void setLplPdstRentRed(BigDecimal lplPdstRentRed) {
        this.lplPdstRentRed = lplPdstRentRed;
    }

    public Character getLplPdstFp0() {
        return lplPdstFp0;
    }

    public void setLplPdstFp0(Character lplPdstFp0) {
        this.lplPdstFp0 = lplPdstFp0;
    }

    public BigDecimal getLplOgrZasChor() {
        return lplOgrZasChor;
    }

    public void setLplOgrZasChor(BigDecimal lplOgrZasChor) {
        this.lplOgrZasChor = lplOgrZasChor;
    }

    public BigDecimal getLplPdstZusDekAR() {
        return lplPdstZusDekAR;
    }

    public void setLplPdstZusDekAR(BigDecimal lplPdstZusDekAR) {
        this.lplPdstZusDekAR = lplPdstZusDekAR;
    }

    public BigDecimal getLplPdstChorDekAR() {
        return lplPdstChorDekAR;
    }

    public void setLplPdstChorDekAR(BigDecimal lplPdstChorDekAR) {
        this.lplPdstChorDekAR = lplPdstChorDekAR;
    }

    public BigDecimal getLplPdstZdroDekAR() {
        return lplPdstZdroDekAR;
    }

    public void setLplPdstZdroDekAR(BigDecimal lplPdstZdroDekAR) {
        this.lplPdstZdroDekAR = lplPdstZdroDekAR;
    }

    public Character getLplZusEmerDobr() {
        return lplZusEmerDobr;
    }

    public void setLplZusEmerDobr(Character lplZusEmerDobr) {
        this.lplZusEmerDobr = lplZusEmerDobr;
    }

    public Character getLplZusRentDobr() {
        return lplZusRentDobr;
    }

    public void setLplZusRentDobr(Character lplZusRentDobr) {
        this.lplZusRentDobr = lplZusRentDobr;
    }

    public Character getLplZusZdroDobr() {
        return lplZusZdroDobr;
    }

    public void setLplZusZdroDobr(Character lplZusZdroDobr) {
        this.lplZusZdroDobr = lplZusZdroDobr;
    }

    public Character getLplZusWypDobr() {
        return lplZusWypDobr;
    }

    public void setLplZusWypDobr(Character lplZusWypDobr) {
        this.lplZusWypDobr = lplZusWypDobr;
    }

    public BigDecimal getLplEmerUbez() {
        return lplEmerUbez;
    }

    public void setLplEmerUbez(BigDecimal lplEmerUbez) {
        this.lplEmerUbez = lplEmerUbez;
    }

    public BigDecimal getLplEmerPrac() {
        return lplEmerPrac;
    }

    public void setLplEmerPrac(BigDecimal lplEmerPrac) {
        this.lplEmerPrac = lplEmerPrac;
    }

    public BigDecimal getLplEmerBudz() {
        return lplEmerBudz;
    }

    public void setLplEmerBudz(BigDecimal lplEmerBudz) {
        this.lplEmerBudz = lplEmerBudz;
    }

    public BigDecimal getLplEmerPfron() {
        return lplEmerPfron;
    }

    public void setLplEmerPfron(BigDecimal lplEmerPfron) {
        this.lplEmerPfron = lplEmerPfron;
    }

    public BigDecimal getLplRentUbez() {
        return lplRentUbez;
    }

    public void setLplRentUbez(BigDecimal lplRentUbez) {
        this.lplRentUbez = lplRentUbez;
    }

    public BigDecimal getLplRentPrac() {
        return lplRentPrac;
    }

    public void setLplRentPrac(BigDecimal lplRentPrac) {
        this.lplRentPrac = lplRentPrac;
    }

    public BigDecimal getLplRentBudz() {
        return lplRentBudz;
    }

    public void setLplRentBudz(BigDecimal lplRentBudz) {
        this.lplRentBudz = lplRentBudz;
    }

    public BigDecimal getLplRentPfron() {
        return lplRentPfron;
    }

    public void setLplRentPfron(BigDecimal lplRentPfron) {
        this.lplRentPfron = lplRentPfron;
    }

    public BigDecimal getLplChorUbez() {
        return lplChorUbez;
    }

    public void setLplChorUbez(BigDecimal lplChorUbez) {
        this.lplChorUbez = lplChorUbez;
    }

    public BigDecimal getLplChorPrac() {
        return lplChorPrac;
    }

    public void setLplChorPrac(BigDecimal lplChorPrac) {
        this.lplChorPrac = lplChorPrac;
    }

    public BigDecimal getLplChorBudz() {
        return lplChorBudz;
    }

    public void setLplChorBudz(BigDecimal lplChorBudz) {
        this.lplChorBudz = lplChorBudz;
    }

    public BigDecimal getLplChorPfron() {
        return lplChorPfron;
    }

    public void setLplChorPfron(BigDecimal lplChorPfron) {
        this.lplChorPfron = lplChorPfron;
    }

    public BigDecimal getLplWypUbez() {
        return lplWypUbez;
    }

    public void setLplWypUbez(BigDecimal lplWypUbez) {
        this.lplWypUbez = lplWypUbez;
    }

    public BigDecimal getLplWypPrac() {
        return lplWypPrac;
    }

    public void setLplWypPrac(BigDecimal lplWypPrac) {
        this.lplWypPrac = lplWypPrac;
    }

    public BigDecimal getLplWypBudz() {
        return lplWypBudz;
    }

    public void setLplWypBudz(BigDecimal lplWypBudz) {
        this.lplWypBudz = lplWypBudz;
    }

    public BigDecimal getLplWypPfron() {
        return lplWypPfron;
    }

    public void setLplWypPfron(BigDecimal lplWypPfron) {
        this.lplWypPfron = lplWypPfron;
    }

    public BigDecimal getLplZdroUbez() {
        return lplZdroUbez;
    }

    public void setLplZdroUbez(BigDecimal lplZdroUbez) {
        this.lplZdroUbez = lplZdroUbez;
    }

    public BigDecimal getLplZdroPrac() {
        return lplZdroPrac;
    }

    public void setLplZdroPrac(BigDecimal lplZdroPrac) {
        this.lplZdroPrac = lplZdroPrac;
    }

    public BigDecimal getLplZdroBudz() {
        return lplZdroBudz;
    }

    public void setLplZdroBudz(BigDecimal lplZdroBudz) {
        this.lplZdroBudz = lplZdroBudz;
    }

    public BigDecimal getLplZdroPfron() {
        return lplZdroPfron;
    }

    public void setLplZdroPfron(BigDecimal lplZdroPfron) {
        this.lplZdroPfron = lplZdroPfron;
    }

    public BigDecimal getLplFpUbez() {
        return lplFpUbez;
    }

    public void setLplFpUbez(BigDecimal lplFpUbez) {
        this.lplFpUbez = lplFpUbez;
    }

    public BigDecimal getLplFpPrac() {
        return lplFpPrac;
    }

    public void setLplFpPrac(BigDecimal lplFpPrac) {
        this.lplFpPrac = lplFpPrac;
    }

    public BigDecimal getLplFpBudz() {
        return lplFpBudz;
    }

    public void setLplFpBudz(BigDecimal lplFpBudz) {
        this.lplFpBudz = lplFpBudz;
    }

    public BigDecimal getLplFpPfron() {
        return lplFpPfron;
    }

    public void setLplFpPfron(BigDecimal lplFpPfron) {
        this.lplFpPfron = lplFpPfron;
    }

    public BigDecimal getLplFgspUbez() {
        return lplFgspUbez;
    }

    public void setLplFgspUbez(BigDecimal lplFgspUbez) {
        this.lplFgspUbez = lplFgspUbez;
    }

    public BigDecimal getLplFgspPrac() {
        return lplFgspPrac;
    }

    public void setLplFgspPrac(BigDecimal lplFgspPrac) {
        this.lplFgspPrac = lplFgspPrac;
    }

    public BigDecimal getLplFgspBudz() {
        return lplFgspBudz;
    }

    public void setLplFgspBudz(BigDecimal lplFgspBudz) {
        this.lplFgspBudz = lplFgspBudz;
    }

    public BigDecimal getLplFgspPfron() {
        return lplFgspPfron;
    }

    public void setLplFgspPfron(BigDecimal lplFgspPfron) {
        this.lplFgspPfron = lplFgspPfron;
    }

    public Character getLplStawkiMan() {
        return lplStawkiMan;
    }

    public void setLplStawkiMan(Character lplStawkiMan) {
        this.lplStawkiMan = lplStawkiMan;
    }

    public Character getLplZaliczkaMan() {
        return lplZaliczkaMan;
    }

    public void setLplZaliczkaMan(Character lplZaliczkaMan) {
        this.lplZaliczkaMan = lplZaliczkaMan;
    }

    public Character getLplChar1() {
        return lplChar1;
    }

    public void setLplChar1(Character lplChar1) {
        this.lplChar1 = lplChar1;
    }

    public Character getLplChar2() {
        return lplChar2;
    }

    public void setLplChar2(Character lplChar2) {
        this.lplChar2 = lplChar2;
    }

    public Character getLplChar3() {
        return lplChar3;
    }

    public void setLplChar3(Character lplChar3) {
        this.lplChar3 = lplChar3;
    }

    public Character getLplChar4() {
        return lplChar4;
    }

    public void setLplChar4(Character lplChar4) {
        this.lplChar4 = lplChar4;
    }

    public Character getLplChar5() {
        return lplChar5;
    }

    public void setLplChar5(Character lplChar5) {
        this.lplChar5 = lplChar5;
    }

    public Character getLplChar6() {
        return lplChar6;
    }

    public void setLplChar6(Character lplChar6) {
        this.lplChar6 = lplChar6;
    }

    public Character getLplChar7() {
        return lplChar7;
    }

    public void setLplChar7(Character lplChar7) {
        this.lplChar7 = lplChar7;
    }

    public Character getLplChar8() {
        return lplChar8;
    }

    public void setLplChar8(Character lplChar8) {
        this.lplChar8 = lplChar8;
    }

    public BigDecimal getLplKwotaDod5() {
        return lplKwotaDod5;
    }

    public void setLplKwotaDod5(BigDecimal lplKwotaDod5) {
        this.lplKwotaDod5 = lplKwotaDod5;
    }

    public BigDecimal getLplKwotaDod6() {
        return lplKwotaDod6;
    }

    public void setLplKwotaDod6(BigDecimal lplKwotaDod6) {
        this.lplKwotaDod6 = lplKwotaDod6;
    }

    public BigDecimal getLplKwotaDod7() {
        return lplKwotaDod7;
    }

    public void setLplKwotaDod7(BigDecimal lplKwotaDod7) {
        this.lplKwotaDod7 = lplKwotaDod7;
    }

    public BigDecimal getLplKwotaDod8() {
        return lplKwotaDod8;
    }

    public void setLplKwotaDod8(BigDecimal lplKwotaDod8) {
        this.lplKwotaDod8 = lplKwotaDod8;
    }

    public Character getLplPodMinZus() {
        return lplPodMinZus;
    }

    public void setLplPodMinZus(Character lplPodMinZus) {
        this.lplPodMinZus = lplPodMinZus;
    }

    public Character getLplZalMinZdro() {
        return lplZalMinZdro;
    }

    public void setLplZalMinZdro(Character lplZalMinZdro) {
        this.lplZalMinZdro = lplZalMinZdro;
    }

    public BigDecimal getLplPrzychOpodOdl() {
        return lplPrzychOpodOdl;
    }

    public void setLplPrzychOpodOdl(BigDecimal lplPrzychOpodOdl) {
        this.lplPrzychOpodOdl = lplPrzychOpodOdl;
    }

    public BigDecimal getLplPodZusKw() {
        return lplPodZusKw;
    }

    public void setLplPodZusKw(BigDecimal lplPodZusKw) {
        this.lplPodZusKw = lplPodZusKw;
    }

    public Character getLplTylZusOpod() {
        return lplTylZusOpod;
    }

    public void setLplTylZusOpod(Character lplTylZusOpod) {
        this.lplTylZusOpod = lplTylZusOpod;
    }

    public Date getLplDataDod3() {
        return lplDataDod3;
    }

    public void setLplDataDod3(Date lplDataDod3) {
        this.lplDataDod3 = lplDataDod3;
    }

    public Date getLplDataDod4() {
        return lplDataDod4;
    }

    public void setLplDataDod4(Date lplDataDod4) {
        this.lplDataDod4 = lplDataDod4;
    }

    public Character getLplChar9() {
        return lplChar9;
    }

    public void setLplChar9(Character lplChar9) {
        this.lplChar9 = lplChar9;
    }

    public Character getLplChar10() {
        return lplChar10;
    }

    public void setLplChar10(Character lplChar10) {
        this.lplChar10 = lplChar10;
    }

    public Character getLplChar11() {
        return lplChar11;
    }

    public void setLplChar11(Character lplChar11) {
        this.lplChar11 = lplChar11;
    }

    public Character getLplChar12() {
        return lplChar12;
    }

    public void setLplChar12(Character lplChar12) {
        this.lplChar12 = lplChar12;
    }

    public BigDecimal getLplKwotaDod9() {
        return lplKwotaDod9;
    }

    public void setLplKwotaDod9(BigDecimal lplKwotaDod9) {
        this.lplKwotaDod9 = lplKwotaDod9;
    }

    public BigDecimal getLplKwotaDod10() {
        return lplKwotaDod10;
    }

    public void setLplKwotaDod10(BigDecimal lplKwotaDod10) {
        this.lplKwotaDod10 = lplKwotaDod10;
    }

    public BigDecimal getLplKwotaDod11() {
        return lplKwotaDod11;
    }

    public void setLplKwotaDod11(BigDecimal lplKwotaDod11) {
        this.lplKwotaDod11 = lplKwotaDod11;
    }

    public BigDecimal getLplKwotaDod12() {
        return lplKwotaDod12;
    }

    public void setLplKwotaDod12(BigDecimal lplKwotaDod12) {
        this.lplKwotaDod12 = lplKwotaDod12;
    }

    public BigDecimal getLplPodZdroKw() {
        return lplPodZdroKw;
    }

    public void setLplPodZdroKw(BigDecimal lplPodZdroKw) {
        this.lplPodZdroKw = lplPodZdroKw;
    }

    public Short getLplWynForma2() {
        return lplWynForma2;
    }

    public void setLplWynForma2(Short lplWynForma2) {
        this.lplWynForma2 = lplWynForma2;
    }

    public BigDecimal getLplWynForma2Kw() {
        return lplWynForma2Kw;
    }

    public void setLplWynForma2Kw(BigDecimal lplWynForma2Kw) {
        this.lplWynForma2Kw = lplWynForma2Kw;
    }

    public Character getLplChar13() {
        return lplChar13;
    }

    public void setLplChar13(Character lplChar13) {
        this.lplChar13 = lplChar13;
    }

    public Character getLplChar14() {
        return lplChar14;
    }

    public void setLplChar14(Character lplChar14) {
        this.lplChar14 = lplChar14;
    }

    public Character getLplChar15() {
        return lplChar15;
    }

    public void setLplChar15(Character lplChar15) {
        this.lplChar15 = lplChar15;
    }

    public Character getLplChar16() {
        return lplChar16;
    }

    public void setLplChar16(Character lplChar16) {
        this.lplChar16 = lplChar16;
    }

    public BigDecimal getLplWspDod1() {
        return lplWspDod1;
    }

    public void setLplWspDod1(BigDecimal lplWspDod1) {
        this.lplWspDod1 = lplWspDod1;
    }

    public BigDecimal getLplWspDod2() {
        return lplWspDod2;
    }

    public void setLplWspDod2(BigDecimal lplWspDod2) {
        this.lplWspDod2 = lplWspDod2;
    }

    public BigDecimal getLplWspDod3() {
        return lplWspDod3;
    }

    public void setLplWspDod3(BigDecimal lplWspDod3) {
        this.lplWspDod3 = lplWspDod3;
    }

    public BigDecimal getLplWspDod4() {
        return lplWspDod4;
    }

    public void setLplWspDod4(BigDecimal lplWspDod4) {
        this.lplWspDod4 = lplWspDod4;
    }

    public BigDecimal getLplWspDod5() {
        return lplWspDod5;
    }

    public void setLplWspDod5(BigDecimal lplWspDod5) {
        this.lplWspDod5 = lplWspDod5;
    }

    public BigDecimal getLplWspDod6() {
        return lplWspDod6;
    }

    public void setLplWspDod6(BigDecimal lplWspDod6) {
        this.lplWspDod6 = lplWspDod6;
    }

    public BigDecimal getLplWspDod7() {
        return lplWspDod7;
    }

    public void setLplWspDod7(BigDecimal lplWspDod7) {
        this.lplWspDod7 = lplWspDod7;
    }

    public BigDecimal getLplWspDod8() {
        return lplWspDod8;
    }

    public void setLplWspDod8(BigDecimal lplWspDod8) {
        this.lplWspDod8 = lplWspDod8;
    }

    public BigDecimal getLplWspDod9() {
        return lplWspDod9;
    }

    public void setLplWspDod9(BigDecimal lplWspDod9) {
        this.lplWspDod9 = lplWspDod9;
    }

    public BigDecimal getLplWspDod10() {
        return lplWspDod10;
    }

    public void setLplWspDod10(BigDecimal lplWspDod10) {
        this.lplWspDod10 = lplWspDod10;
    }

    public Date getLplDataDod5() {
        return lplDataDod5;
    }

    public void setLplDataDod5(Date lplDataDod5) {
        this.lplDataDod5 = lplDataDod5;
    }

    public Date getLplDataDod6() {
        return lplDataDod6;
    }

    public void setLplDataDod6(Date lplDataDod6) {
        this.lplDataDod6 = lplDataDod6;
    }

    public Integer getLplIntDod1() {
        return lplIntDod1;
    }

    public void setLplIntDod1(Integer lplIntDod1) {
        this.lplIntDod1 = lplIntDod1;
    }

    public Integer getLplIntDod2() {
        return lplIntDod2;
    }

    public void setLplIntDod2(Integer lplIntDod2) {
        this.lplIntDod2 = lplIntDod2;
    }

    public Integer getLplLp() {
        return lplLp;
    }

    public void setLplLp(Integer lplLp) {
        this.lplLp = lplLp;
    }

    public Integer getLplIntDod3() {
        return lplIntDod3;
    }

    public void setLplIntDod3(Integer lplIntDod3) {
        this.lplIntDod3 = lplIntDod3;
    }

    public Integer getLplIntDod4() {
        return lplIntDod4;
    }

    public void setLplIntDod4(Integer lplIntDod4) {
        this.lplIntDod4 = lplIntDod4;
    }

    public BigDecimal getLplKwotaDod13() {
        return lplKwotaDod13;
    }

    public void setLplKwotaDod13(BigDecimal lplKwotaDod13) {
        this.lplKwotaDod13 = lplKwotaDod13;
    }

    @XmlTransient
    public List<PlaceSkl> getPlaceSklList() {
        return placeSklList;
    }

    public void setPlaceSklList(List<PlaceSkl> placeSklList) {
        this.placeSklList = placeSklList;
    }

    @XmlTransient
    public List<DaneStatL> getDaneStatLList() {
        return daneStatLList;
    }

    public void setDaneStatLList(List<DaneStatL> daneStatLList) {
        this.daneStatLList = daneStatLList;
    }

    @XmlTransient
    public List<PlacePrzZus> getPlacePrzZusList() {
        return placePrzZusList;
    }

    public void setPlacePrzZusList(List<PlacePrzZus> placePrzZusList) {
        this.placePrzZusList = placePrzZusList;
    }

    @XmlTransient
    public List<PlacePrz> getPlacePrzList() {
        return placePrzList;
    }

    public void setPlacePrzList(List<PlacePrz> placePrzList) {
        this.placePrzList = placePrzList;
    }

    public Bank getLplBanSerial() {
        return lplBanSerial;
    }

    public void setLplBanSerial(Bank lplBanSerial) {
        this.lplBanSerial = lplBanSerial;
    }

    public Listy getLplLisSerial() {
        return lplLisSerial;
    }

    public void setLplLisSerial(Listy lplLisSerial) {
        this.lplLisSerial = lplLisSerial;
    }

    public Okres getLplOkrSerial() {
        return lplOkrSerial;
    }

    public void setLplOkrSerial(Okres lplOkrSerial) {
        this.lplOkrSerial = lplOkrSerial;
    }

    public Osoba getLplOsoSerial() {
        return lplOsoSerial;
    }

    public void setLplOsoSerial(Osoba lplOsoSerial) {
        this.lplOsoSerial = lplOsoSerial;
    }

    @XmlTransient
    public List<PlaceZlec> getPlaceZlecList() {
        return placeZlecList;
    }

    public void setPlaceZlecList(List<PlaceZlec> placeZlecList) {
        this.placeZlecList = placeZlecList;
    }

    @XmlTransient
    public List<PlacePot> getPlacePotList() {
        return placePotList;
    }

    public void setPlacePotList(List<PlacePot> placePotList) {
        this.placePotList = placePotList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lplSerial != null ? lplSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Place)) {
            return false;
        }
        Place other = (Place) object;
        if ((this.lplSerial == null && other.lplSerial != null) || (this.lplSerial != null && !this.lplSerial.equals(other.lplSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Place{" + "lplKoszty=" + lplKoszty + ", lplZalDoch=" + lplZalDoch + ", lplPdstZus=" + lplPdstZus + ", lplPodDoch=" + lplPodDoch + ", lplPrzychOpod=" + lplPrzychOpod + ", lplOkrSerial=" + lplOkrSerial.getOkrRokSerial().getRokNumer() + ", lplOsoSerial=" + lplOsoSerial.getOsoNazwisko() + '}';
    }

  

    
    
}
