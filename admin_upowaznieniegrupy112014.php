<?php
error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$_wynik = R::getAll('select * from grupyupowaznien');
$_wynik_firmaall = R::getAll('SELECT * FROM zakladpracy ORDER BY nazwazakladu');
?>

<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro;">
    <form id="tabelauserow" >
        <div id="tbl" style="width: 1000px;">
            <table id="tabuser" class="context-menu-one box menu-1"  style="margin: 0px; width: 1000px;">
                <thead >
                    <tr >
                        <th>id</th>
                        <th>firma</th>
                        <th>nazwa grupy</th>
                        <th></th>
                        <th>edytuj</th>
                        <th>usuń</th>
                    </tr>
                </thead> 
                <tbody>
                    <?php
                    error_reporting(0);
                    foreach ($_wynik as $value) {
                        $nazwazakladuszukanego = $value['firma'];
                        $sql = "select * FROM `zakladpracy` WHERE `nazwazakladu` LIKE '$nazwazakladuszukanego' ";
                        $_wynik_selectfirma = R::getRow($sql);
                        if ($_wynik_selectfirma['firmaaktywna'] == '1') {
                            ?>
                            <tr id="<?php
                            error_reporting(0);
                            echo $value['id']
                            ?>" class="wiersze">
                                <td class="id" id="iduserreset"><?php
                                    error_reporting(0);
                                    echo $value['id']
                                    ?></td>
                                <td style="width: 40%;"><?php
                                    error_reporting(0);
                                    echo $value['firma']
                                    ?></td>
                                <td style="width: 50%;"><?php
                                    error_reporting(0);
                                    echo $value['nazwagrupy']
                                    ?></td>
                                <td class="czekboks" style="text-align: center;"><input type="checkbox"/></td>
                                <td style="width: 5%; text-align: center;">
                                    <input title="edytuj" name="edytuj" value="edytuj" type="button"  onclick="editnazwagrupy(this);" class="buttonedytujuser" style="display: none;"/>
                                </td>
                                <td style="width: 5%; text-align: center;">
                                    <input title="usuń" name="usun" value="usuń"type="button"   onclick="usunwiersz(this);"  style="display: none;"/>
                                </td>
                            </tr>
                            <?php
                            error_reporting(0);
                        }
                    }
                    ?>
                            
                </tbody>
            </table>
        </div>
    </form>
    
</div>

<!--formularz do edycji danych użytkownika-->
<div id='editnazwagrupy' style='margin-top: 10px; display: none;' title="Edycja wybranej grupy" >
    <form id="formedituser" action="" method="post">
        <table  style="margin-bottom: 15px;">
            <tbody>
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="idgrupa" name="idgrupa" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>firma: </span></td><td>
                        <select id="firmanazwa" name="firmanazwa" style="width: 250px;">
                            <?php
                            error_reporting(0);
                            foreach ($_wynik_firmaall as $value) {
                                if ($value['firmaaktywna'] == TRUE) {
                                    ?>
                                    <option value="<?php
                                    error_reporting(0);
                                    echo $value['nazwazakladu']
                                    ?>"><?php
                                                error_reporting(0);
                                                echo $value['nazwazakladu']
                                                ?></option>

                                    <?php
                                    error_reporting(0);
                                }
                            }
                            ?>
                        </select>
                    </td>
                </tr>
                <tr>
                     <tr>
                        <td><span>nazwa grupy: </span></td><td><input type="text" id="nazwagrupy" name="nazwagrupy" style="width: 250px;"></td>
                    </tr>
                </tr>
            </tbody>
        </table>
<!--        <input id="editbutton" name="edytujuser" value="edytuj" type="submit"  style="padding: 5px;width: 120px; margin-left: 35%;">-->
        <input id="editbutton" name="edytujuser" value="edytuj" type="button" onclick="edytujgrupaupowaznien();" style="padding: 5px;width: 120px; margin-left: 35%;">
<!--        <input id="usunbutton" name="usunuser" value="usuń" type="submit" onclick="usuntabeleuser();" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">-->
    </form>
</div>
<div id='nowanazwagrupy' style='margin-top: 10px; display: none;' title="Nazwa nowej grupy">
    <!--    <form id="forminsertuser" action="newuser.php?plik=admin112014_uzytkownicy.php" method="post" >-->
    <form id="forminsertuser" >
        <table  style="margin-bottom: 15px;">
            <tr>
                <td><span>firma: </span></td><td>
                    <select id="Nfirmauser" name="Nfirmauser" style="width: 250px;">
                        <?php
                        error_reporting(0);
                        foreach ($_wynik_firmaall as $value) {
                            if ($value['firmaaktywna'] == TRUE) {
                                ?>
                                <option value="<?php
                                        error_reporting(0);
                                        echo $value['nazwazakladu']
                                        ?>"><?php
                                    error_reporting(0);
                                    echo $value['nazwazakladu']
                                    ?></option>

                                <?php
                                error_reporting(0);
                            }
                        }
                        ?>
                    </select>
                </td>
            </tr>
            <tr>
                <td><span>nazwa grupy: </span></td><td><input type="text" id="Nnazwagrupy" name="Nnazwagrupy" style="width: 250px;"></td>
            </tr>
        </table>
        <input id="Ndodajnazwagrupy" name="Ndodajnazwagrupy" value="dodaj" type="button"  onclick="dodajnazwagrupy();" style="padding: 5px; width: 120px; margin-left: 35%;">
    </form>
</div>
<div id="dialog-user" title="Potwierdzenie" style="display: none;">
    <p>
        <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 100px 0;"></span>
        Edycja użytkownika: </p><p><span id="uzytkownikwiadomosc"></span></p><p>zakończona sukcesem</p>
</div>
<div id="dialog_user_usun" title="" style="display: none;">
    <p>Czy napewno usunąć?</p> 
    <input value="tak" style="float: left; margin-left: 4%; width: 120px;" type="button"  onclick="tak_usunwiersz();"/> <input value="nie" type="button" onclick="nie_usunwiersz();" style="float: right; margin-right: 4%; width: 120px;"/>
</div>
<div id="ajax_sun" title="trwa dodawanie" style="display: none; text-align: center;">
    <img src="/images/ajax_loaderc.gif" alt="ajax" height="70" width="70">;
</div>
