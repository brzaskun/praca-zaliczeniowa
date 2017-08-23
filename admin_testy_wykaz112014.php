<?php
error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$_szk = R::getAll('SELECT * FROM testwykaz ORDER BY nazwa');
?>

<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro; ">
    <div id="tbl" style="max-width: 700px;">

    </div>
    
     <div id="edittestwykaz"  style='margin-top: 10px; display: none;'  title="Edycja testu">
    <form id="formedittestwykaz">
        <table id='tabelaedituser'    style="margin-bottom: 15px;">
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="idszkolenie" name="idszkolenie" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>nazwa szkolenia: </span></td><td><input id="szkolenia" name="szkolenia" style="width: 260px;" readonly></td>
            </tr>
              <tr>
                    <td><span>skrót nazwy: </span></td><td><input id="skrot" name="skrot" style="width: 260px;" ></td>
            </tr>
                <tr>
                    <td><span>opis szkolenia: </span></td><td><input id="opis" name="opis" style="width: 260px;"></td>
                </tr>
        </table>
        <input value="edytuj" type="button" onclick="edytujtabeletestwykaz();"  style="padding: 5px; width: 120px; margin-left: 15%;">
        <input id="rezygnuj" name="rezygnuj" value="rezygnuj" type="button" onclick="$('#edittestwykaz').puidialog('hide');" style="padding: 5px; width: 120px; margin-left: 15%;">
    </form>
    </div>
    <div id='newtestwykaz'  style='margin-top: 10px; display: none;'  title="Nowy test">
    <form id="forminserttestwykaz">
        <table   style="margin-bottom: 15px;">
                <tr>
                    <td><span>nazwa testu: </span></td><td><input id="Ntestu" name="Ntestu" style="width: 260px;"></td>
            </tr>
            <tr>
                    <td><span>skrót nazwy: </span></td><td><input id="Nskrot" name="Nskrot" style="width: 260px;"></td>
            </tr>
                <tr>
                    <td><span>opis testu: </span></td><td><input id="Nopis" name="Nopis" style="width: 260px;"></td>
                </tr>
        </table>
        <input value="dodaj" type="button" onclick="dodajnowytestwykaz();"  style="padding: 5px; width: 120px; margin-left: 15%;">
        <input id="rezygnuj" name="rezygnuj" value="rezygnuj" type="button" onclick="$('#newtestwykaz').puidialog('hide');" style="padding: 5px; width: 120px; margin-left: 15%;">
    </form>
    </div>
</div>

