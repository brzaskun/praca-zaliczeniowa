<?php error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
$_wynikzaklad = R::getAll('SELECT * FROM zakladpracy');
$_szkolenia = R::getAll('select * from szkoleniewykaz');
?>
<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro;">
    <div id="tbl" style="width: 1350px;">
    <table id="tabzaklad"   style="margin: 0px; width: 1350px;">
    <thead>
        <tr >
            <th style="text-align: center"></th>
            <th style="text-align: center">id</th>
            <th style="text-align: center">nazwa zakładu</th>
            <th style="text-align: center">ulica</th>
            <th style="text-align: center">miejscowość</th>
            <th style="text-align: center">kontakt</th>
            <th style="text-align: center">email</th>
            <th style="text-align: center; width: 4%;">próg <br/>zdawal.</th>
            <th style="text-align: center">firma<br/> aktywna</th>
            <th style="text-align: center; width: 4%;">dopusz. <br/> licz. prac.</th>
            <th style="text-align: center">zarejestrowani<br/> pracownicy</th>
            <th style="text-align: center">limit dni<br/> dla manag.</th>
            <th style="text-align: center">edytuj</th>
            <th style="text-align: center">usuń</th>
        </tr>
    </thead>
    <tbody>
        <?php error_reporting(1); 
        foreach ($_wynikzaklad as $value) {
            $_nazwazakladu = $value['nazwazakladu'];
            $_coszukac = "SELECT * FROM `uczestnicy` WHERE `uczestnicy`.`firma` = '$_nazwazakladu'";
            $_iloscpracownikowzakladu = R::getAll($_coszukac);
            $_iloscwszkoleniu = array();
            $int = 1;
            foreach ($_szkolenia as $value1) {
                $_coszukac = "SELECT * FROM `uczestnicy` WHERE `uczestnicy`.`firma` = '$_nazwazakladu' AND `uczestnicy`.`nazwaszkolenia` = '$value1[nazwa]'";
                $_ilosc = count(R::getAll($_coszukac));
                if ($_ilosc > 0) {
                    $_iloscwszkoleniu[$value1[skrot]] = $_ilosc;
                    $int++;
                }
            }
            ?>
            <tr id="<?php error_reporting(0); echo $value['id'] ?>" class="wierszezaklad" >
                <td class="czekboks"><input type="checkbox"/></td>
                <td><?php error_reporting(0); echo $value['id'] ?></td>
                <?php error_reporting(0); 
                if ($value['firmaaktywna'] == "1") {
                    ?>
                    <td style="width: 300px;"><?php error_reporting(0); echo $value['nazwazakladu'] ?></td>
                    <?php
                } else {
                    ?>
                    <td style="width: 300px; font-style: oblique; color: gray;"><?php error_reporting(0); echo $value['nazwazakladu'] ?></td>
                <?php
                }
                ?>
                <td style="width: 200px;"><?php error_reporting(0); echo $value['ulica'] ?></td>
                <td style="width: 100px;"><?php error_reporting(0); echo $value['miejscowosc'] ?></td>
                <td style="width: 150px;"><?php error_reporting(0); echo $value['kontakt'] ?></td>
                <td style="width: 150px;"><?php error_reporting(0); echo $value['email'] ?></td>
                <td style="text-align: center"><?php error_reporting(0); echo $value['progzdawalnosci'] ?></td>
                <td style="text-align: center"><?php error_reporting(0); echo $value['firmaaktywna'] ?></td>
                <td style="text-align: center"><?php error_reporting(0); echo $value['maxpracownikow'] ?></td>
                <td style="text-align: center">
                    <style>
                        table.hovertable td {
                            padding: 1px;
                        }
                    </style>
                    <table  >
                        <thead>
                            <tr >
                                <th style="text-align: center">suma</th>
                                <?php error_reporting(0);
                                foreach (array_keys($_iloscwszkoleniu) as $value2) { 
                                ?><th style="text-align: center"><?php echo $value2;?></th>
                                <?php error_reporting(0);
                                } ?>
                            </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><?php error_reporting(0); echo count($_iloscpracownikowzakladu) ?></td>
                                 <?php error_reporting(0);
                                foreach (array_values($_iloscwszkoleniu) as $value3) { 
                                ?><td style="text-align: center"><?php echo $value3;?></td>
                                <?php error_reporting(0);
                                } ?>
                            </tr>
                        </tbody>
                    </table>
                </td>
                <td style="text-align: center"><?php error_reporting(0); echo $value['managerlimit'] ?></td>
                <td style="width: 10px;">
                    <input title="edyt" name="edytuj" value="edyt" type="button"  onclick="editzaklad(this);" class="buttonedytujuser" style="display: none;"/>
                </td>
                <td style="width: 10px;"><input title="usuń" name="usun" value="usuń" type="button"  onclick="usunzaklad(this);"  style="display: none;"/></td>
            </tr>
        <?php error_reporting(0); } ?>
    </tbody>
</table>
        </div>
    </div>

<div id='editzaklad' style='margin-top: 10px; display: none;' title="Edycja danych wybranego zakładu" onload="">
    <form id="formeditzaklad">
        <table  style="margin-bottom: 15px;">
            <tbody>
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="idzaklad" name="idzaklad" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>nazwa zakładu: </span></td><td><input type="text" id="nazwazakladu" name="nazwazakladu" style="width: 350px;" readonly></td>
                </tr>
                <tr>
                    <td><span>ulica: </span></td><td><input id="ulica" name="ulica" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>miejscowość: </span></td><td><input id="miejscowosc" name="miejscowosc" style="width: 350px;"></td>
                </tr>
                 <tr>
                    <td><span>próg zdawalności: </span></td><td><input type="number" min="1" max="100" id="progzdawalnosci" name="progzdawalnosci" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>dezaktywuj/aktywuj firmę: </span></td><td>
                    <select id="firmaaktywna" name="firmaaktywna" style="width: 250px;">
                    <option value="aktywna">aktywna</option>
                    <option value="nieaktywna">nieaktywna</option>
                 </select>
                  </td>
                </tr>
                <tr>
                    <td><span>kontakt: </span></td><td><input id="kontakt" name="kontakt" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>email: </span></td><td><input id="email" name="email" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>dop. liczba prac: </span></td><td><input type="number" min="1" id="maxpracownikow" name="maxpracownikow" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>limit dni - manager: </span></td><td><input type="number" min="1" id="managerlimit" name="managerlimit" style="width: 250px;"></td>
                </tr>
            </tbody>
        </table>
        <input id="edytujzaklad" name="edytujzaklad" value="edytuj" type="button" onclick="edytujtabelezaklad();" style="padding: 5px;width: 120px; margin-left: 20%;">
        <input value="rezygnuj" type="button" onclick="canceledytujtabelezaklad();" style="padding: 5px;width: 120px; margin-left: 15%;">
    </form>
</div>
<div id='newzaklad' style='margin-top: 10px; display: none;' title="Nowa firma">
    <form id="forminsertzaklad" >
        <table  style="margin-bottom: 15px;">
            <tr>
                <td><span>nazwa zakladu: </span></td><td><input id="Nnazwazakladu" name="Nnazwazakladu" style="width: 200px;" onblur="weryfikujzaklad();"></td>
            </tr>
            <tr>
                <td><span>ulica: </span></td><td><input id="Nulica" name="Nulica" style="width: 200px;"></td>
            </tr>
            <tr>
                <td><span>miejscowość: </span></td><td><input id="Nmiejscowosc" name="Nmiejscowosc" style="width: 200px;"></td>
            </tr>
             <tr>
                <td><span>próg zdawalności: </span></td><td><input id="Nprogzdawalnosci" name="Nprogzdawalnosci" style="width: 200px;"></td>
             </tr>
              <tr>
                    <td><span>kontakt: </span></td><td><input id="Nkontakt" name="Nkontakt" style="width: 250px;"></td>
              </tr>
               <tr>
                    <td><span>email: </span></td><td><input id="Nemail" name="Nemail" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>dop. liczba prac: </span></td><td><input id="Nmaxpracownikow" name="Nmaxpracownikow" style="width: 250px;"></td>
                </tr>
                 <tr>
                    <td><span>limit dni - manager: </span></td><td><input id="Nmanagerlimit" name="Nmanagerlimit" style="width: 250px;"></td>
                </tr>
        </table>
        <input id="dodajzaklad" name="dodajzaklad" value="dodaj" type="button"  onclick="dodajnowyzaklad();" style="padding: 5px; width: 120px; margin-left: 35%;">
    </form>
</div>
<div id="dialog-zaklad" title="Potwierdzenie" style="display: none;">
    <p>
        <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 100px 0;"></span>
        Edycja danych firmy:  </p><p><span id="zakladwiadomosc"></span></p><p>zakończona sukcesem</p>
</div>
