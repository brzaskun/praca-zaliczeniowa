<?php
error_reporting(0);  
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$_wynik_firmaall = R::getAll('SELECT * FROM zakladpracy ORDER BY `zakladpracy`.`nazwazakladu` ASC');
$_szkolenia = R::getAll('select * from szkoleniewykaz ORDER BY `szkoleniewykaz`.`nazwa` ASC');
?>
<style>
    .selectbar1 .ui-dropdown {
        width: 150px;
    }
     .selectbar .ui-dropdown {
        width: 250px;
    }
</style>
<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro;">
    <form id="tabelauserow" >
        <div class="ui-grid-col-5 selectbar">
            <select id="aktywnafirma" name="aktywnafirma"></select>
        </div>
        <div class="ui-grid-col-3 selectbar1" id="warunek1div">
            <select id="warunek1" name="warunek1" >
                        <option selected="wszyscy" value="wszyscy">wszyscy</option>
                        <option value="aktywni">aktywni</option>
                        <option value="archiwalni">archiwalni</option>
                    </select>

        </div>
               <div id="tbl" style="max-width: 1465px;">
<!--            nie ma tabeli, jest generowana w całości-->
        </div>
    </form>
    <div style="height: 50px;"> 
        <!--    jest normalnie ukryty, wyswietlany przez pozycje w menu-->
        <div id="infouserdiv" style="float: left; color: red; display: none;" >
            <form >
                <div>
                    <input id="tabelaeksport" name="tabela" value="XLS" type="button" onclick="generujtabelexls();" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
                </div>
            </form>
        </div>
    </div>
</div> 
<!--formularz do edycji danych użytkownika-->
<div id='edituser' style='margin-top: 10px; display: none;' title="Edycja danych wybranego użytkownika" >
    <form id="formedituser" action="" method="post">
        <table id="tabelaedituser" style="margin-bottom: 15px;">
            <tbody>
                <tr hidden> 
                    <td><span>id: </span></td><td><input type="text" id="iduser" name="iduser" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>adres mail: </span></td><td><input type="text" id="email" name="email" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>imie nazwisko: </span></td><td><input id="imienazwisko" name="imienazwisko" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>płeć: </span></td><td>
                        <select id="plecuser" name="plecuser" style="width: 250px;">
                            <option value="m">mężczyzna</option>
                            <option value="k">kobieta</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><span>szkolenie: </span></td><td>
                        <select id="szkolenieuser" name="szkolenieuser" style="width: 250px;">
                            <?php
                            error_reporting(0);
                            foreach ($_szkolenia as $value) {
                                ?>
                                <option value="<?php echo $value['nazwa']; ?>"><?php echo $value['nazwa']; ?></option>
                                <?php error_reporting(0);
                            }
                            ?>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><span>uprawnienia: </span></td><td>
                        <select id="uprawnieniauser" name="uprawnieniauser" style="width: 250px;">
                            <option value="uzytkownik">uzytkownik</option>
                            <option value="manager">manager</option>
                            <option value="admin">admin</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><span>data zalogowania: </span></td><td><input id="datazalogowania" name="datazalogowania" style="width: 250px;"></td>
                </tr>
            </tbody>
        </table> 
<!--        <input id="editbutton" name="edytujuser" value="edytuj" type="submit"  style="padding: 5px;width: 120px; margin-left: 35%;">-->
        <input value="edytuj" type="button" onclick="edytujtabeleuser();" style="padding: 5px;width: 120px; margin-left: 15%;">
        <input value="rezygnuj" type="button" onclick="canceledytujtabeleuser();" style="padding: 5px;width: 120px; margin-left: 5%;">
<!--        <input id="usunbutton" name="usunuser" value="usuń" type="submit" onclick="usuntabeleuser();" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">-->
    </form>
</div>
<div id='newuser' style='margin-top: 10px; display: none;' title="Dane nowego użytkownika">
    <form id="forminsertuser" >
        <table  style="margin-bottom: 15px;">
            <tr>
                <td><span>adres mail: </span></td>
                <td><input type="email" id="Nemail" name="Nemail" title="wpisz poprawny adres email" 
                                style="width: 250px;" onblur="weryfikujmaila()"></td>
            </tr>
            <tr>
                <td><span>imie nazwisko: </span></td><td><input type="text" id="Nimienazwisko" name="Nimienazwisko" style="width: 250px;" ></td>
            </tr>
            <tr>
                <td><span>płeć: </span></td><td>
                    <select id="Nplecuser" name="Nplecuser" title="wybierz płeć" style="width: 250px;">
                        <option value="m">mężczyzna</option>
                        <option value="k">kobieta</option>
                    </select>
                </td>
            </tr>
            <tr>
                <td><span>firma: </span></td>
                <td>
                    <input id="Nfirmauser" name="Nfirmauser" style="width: 250px;" disabled="true">
                </td>
            </tr>
            <tr>
                <td><span>szkolenie: </span></td><td>
                    <select id="Nszkolenieuser" name="Nszkolenieuser" style="width: 250px;">
                        <?php
                        error_reporting(0);
                        foreach ($_szkolenia as $value) {
                            ?>
                            <option value="<?php echo $value['nazwa']; ?>"><?php echo $value['nazwa']; ?></option>
                        <?php error_reporting(0);
                    }
                    ?>
                    </select>
                </td>
            </tr>
            <tr>
                <td><span>uprawnienia: </span></td><td>
                    <select id="Nuprawnieniauser" name="Nuprawnieniauser" style="width: 250px;">
                        <option value="uzytkownik">uzytkownik</option>
                        <option value="manager">manager</option>
                        <option value="admin">admin</option>
                    </select>
                </td>
            </tr>
        </table>
        <input id="Ndodajuser" name="Ndodajuser" value="dodaj" type="button"  onclick="dodajnowyuser();" style="padding: 5px; width: 120px; margin-left: 15%;">
        <input value="rezygnuj" type="button" onclick="canceluser()"  style="padding: 5px; width: 120px; margin-left: 5%;">
    </form>
</div>
<!--<div id="dialog-user" title="Potwierdzenie" style="display: none;">
    <p>
        <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 100px 0;"></span>
        Edycja użytkownika: </p><p><span id="uzytkownikwiadomosc"></span></p><p>zakończona sukcesem</p>
</div>-->
<div id="dialog_user_usun" title="" style="display: none;">
    <p>Czy napewno usunąć?</p> 
    <input value="tak" style="float: left; margin-left: 4%; width: 120px;" type="button"  onclick="tak_usunwiersz();"/> <input value="nie" type="button" onclick="nie_usunwiersz();" style="float: right; margin-right: 4%; width: 120px;"/>
</div>
<div id="ajax_sun" title="trwa przetwarzanie" style="display: none; text-align: center; z-index: -1;" >
    <img src="/images/ajax_loaderc.gif" alt="ajax" height="70" width="70">;
</div>
<!--genialna rzecz sluzaca do otwierania plikow generowancyh przez php przez ajax--> 
<iframe id="iframe" style="display: none;"></iframe> 
