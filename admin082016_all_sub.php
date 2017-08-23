<?php
error_reporting(0);  
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$_wynik = R::getAll('select * from uczestnicy');
$_wynik_firmaall = R::getAll('SELECT * FROM zakladpracy ORDER BY `zakladpracy`.`nazwazakladu` ASC');
$_szkolenia = R::getAll('select * from szkoleniewykaz ORDER BY `szkoleniewykaz`.`nazwa` ASC');
?>
<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro;">
    <form id="tabelauserow" >
        <div id="tbl" style="max-width: 1465px;">
<!--            nie ma tabeli, jest generowana w całości-->
        </div>
    </form>
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
                    <td><span>firma: </span></td><td>
                        <select id="firmausernazwa" name="firmausernazwa" style="width: 250px;">
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
<div id="ajax_sun" title="trwa przetwarzanie" style="display: none; text-align: center; z-index: -1;" >
    <img src="/images/ajax_loaderc.gif" alt="ajax" height="70" width="70">;
</div>
