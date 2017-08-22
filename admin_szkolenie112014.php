<?php
error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$_wynikszkolenia = R::getAll('select * from szkolenie');
$_szkolenia = R::getAll('select * from szkoleniewykaz');
?>
<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro; ">
    <div id="tbl" style="max-width: 1165px;">
        <table id="tabszkolenie"  class="ckeditor" style="margin: 0px; width: 1165px;">
            <thead>
                <tr >
                    <th style="text-align: center"></th>
                    <th>id</th>
                    <th>nazwa szkolenia</th>
                    <th>nagłówek</th>
                    <th>treść</th>
                    <th>edytuj</th>
                    <th>wstaw<br/>slide po</th>
                    <th>usuń</th>
                </tr>
            </thead>
            <tbody>
                <?php
                error_reporting(0);
                foreach ($_wynikszkolenia as $value) {
                    ?>
                    <tr id="<?php
                        error_reporting(0);
                        echo $value['id']
                        ?>" class="wierszeszkolenie" >
                        <td class="czekboks"  style="width: 40px;"><input type="checkbox"/></td>
                        <td style="width: 50px;"><?php
                        error_reporting(0);
                        echo $value['id']
                        ?></td>
                        <td style="width: 100px;"><?php
                            error_reporting(0);
                            echo $value['nazwaszkolenia']
                            ?></td>
                        <td style="width: 100px;"><?php
                            error_reporting(0);
                            echo $value['naglowek']
                            ?></td>

                        <td style="width: 600px;"><?php
                            error_reporting(0);
                            echo $value['tresc']
                            ?></td>
                        <td style="width: 65px;text-align: center;">
                            <input title="edytuj" name="edytuj" value="edytuj" type="button"  onclick="editszkolenie(this);" class="buttonedytujuser" style="display: none;"/>
                        </td>
                        <td style="width: 65px;text-align: center;"> 
                            <input title="wstaw" name="wstaw" value="wstaw" type="button"  onclick="wstawszkolenie(this);" class="buttonedytujuser" style="display: none;"/>
                        </td>
                        <td style="width: 65px;text-align: center;"><input title="usuń" name="usun" value="usuń" type="button"  onclick="usunszkolenie(this);"  style="display: none;"/></td>
                    </tr>
    <?php
    error_reporting(0);
}
?>
            </tbody>
        </table>
    </div>
</div>
<div id='editszkolenie'  style='margin-top: 10px; display: none;'  title="Edytuj szkolenie" onload="">
    <form id="formeditszkolenie">
        <table   style="margin-bottom: 15px;" >
            <tbody>
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="idszkolenie" name="idszkolenie" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>nazwa szkolenia: </span></td>
                    <td><input type="text" id="nazwaszkolenia" name="nazwaszkolenia" style="width: 250px;" readonly=""></td>
                </tr>
                <tr>
                    <td><span>nagłówek: </span></td><td><input id="naglowek" name="naglowek" style="width: 800px;"></td>
                </tr>
                <tr>
                    <td><span>treść: </span></td><td> 
                        <textarea  id="trescszkolenia" name="trescszkolenia"  cols="150" rows="30"></textarea>
                    </td>
                </tr>
            </tbody>
        </table>
        <input id="edytujszkolenie" name="edytujszkolenie" value="edytuj" type="button" onclick="edytujtabeleszkolenie();" style="padding: 5px; width: 120px; margin-left: 25%;">
        <input id="podgladszkolenia" name="podgladszkolenia" value="podglad" type="button" onclick="edytujtabeleszkoleniepodglad();" style="padding: 5px; width: 120px; margin-left: 5%;">
        <input id="podgladszkolenia" name="rezygnujszkolenie" value="rezygnuj" type="button" onclick="$('#editszkolenie').puidialog('hide')" style="padding: 5px; width: 120px; margin-left: 5%;">
    </form>
</div>
<div id='newszkolenie' style='margin-top: 10px; display: none;' title="Nowe szkolenie">
    <form id="forminsertszkolenie">
        <table  style="margin-bottom: 15px;">
            <tr>
                <td><span>nazwa szkolenia: </span></td><td>
                    <select id="Nnazwaszkolenia" name="Nnazwaszkolenia" style="width: 250px;">
                        <?php
                        error_reporting(0);
                        foreach ($_szkolenia as $value) {
                            ?>
                            <option value="<?php echo $value['nazwa']; ?>"><?php echo $value['nazwa']; ?></option>
                            <?php
                            error_reporting(0);
                            }
                            ?>
                    </select>
                </td>
            </tr>
            <tr>
                <td><span>nagłówek: </span></td><td><input id="Nnaglowek" name="Nnaglowek" style="width: 800px;" onblur="weryfikujslajd()"></td>
            </tr>

            <tr>
                <td><span>treść: </span></td><td><textarea  id="Ntrescszkolenia" name="Ntrescszkolenia" cols="120" rows="30"></textarea>
                </td>
            </tr>
        </table>
        <input id="dodajszkolenie" name="dodajszkolenie" value="dodaj" type="button" onclick="dodajnoweszkolenie();" style="padding: 5px; width: 120px; margin-left: 30%;">
        <input id="rezygnujszkolenie" name="rezygunjszkolenie" value="rezygnuj" type="button" onclick="rezygnujnoweszkolenie();" style="padding: 5px; width: 120px; margin-left: 15%;">
    </form>
</div>


