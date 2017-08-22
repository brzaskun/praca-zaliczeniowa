<?php
error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$_szkoleniatesty = R::getAll('select * from szkolenietest');
$_szkolenia = R::getAll('select * from szkoleniewykaz');
$_testy = R::getAll('select * from testwykaz');
?>
<div style="box-shadow: 10px 10px 5px #888; padding: 30px; margin-top: 10px; background-color: gainsboro;">
    <div id="tbl" style="max-width: 650px;">
        <table id="tabszkolenietestust"  class="ckeditor"  style="margin: 0px; width: 650px;">
            <thead>
                <tr >
                    <th></th>
                    <th>id</th>
                    <th>nazwa szkolenia</th>
                    <th>nazwa testu</th>
                    <th>uwagi</th>
                    <th style="text-align: center">edytuj</th>
                    <th style="text-align: center">usuń</th>
                </tr>
            </thead>
            <tbody>
            <?php error_reporting(0);
            foreach ($_szkoleniatesty as $value) { ?>
                                <tr id="<?php error_reporting(0);
                echo $value['id'] ?>" class="wierszeszkolenie" >
                                    <td class="czekboks" style="width: 10px;"><input type="checkbox"/></td>
                                    <td><?php error_reporting(0);
                echo $value['id'] ?></td>
                                    <td><?php error_reporting(0);
                echo $value['szkolenie'] ?></td>
                                    <td><?php error_reporting(0);
                echo $value['test'] ?></td>
                                    <td style="text-align: center;"><?php error_reporting(0);
                echo $value['uwagi'] ?></td>
                                     <td style="width: 55px;"> 
                                <input title="edytuj" name="edytuj" value="edytuj" type="button"  onclick="editszkolenietestust(this);" class="buttonedytujuser" style="display: none;"/>
                            </td>
                            <td style="width: 55px;"><input title="usuń" name="usun" value="usuń" type="button"  onclick="usunszkolenietestust(this);"  style="display: none;"/></td>
                                </tr>
                <?php error_reporting(0);
            } ?>
            </tbody>
        </table>
    </div>
</div>

<div id='editszkolenietestust'  style='margin-top: 10px; display: none;' title="Edytuj szkolenie-test" onload="">
    <form id="formeditszkolenieust" >
        <table   style="margin-bottom: 15px;" >
            <tbody>
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="id" name="id" style="width: 350px;"></td>
                </tr>
                <tr>
                <td><span>nazwa szkolenia: </span></td><td><input id="szkolenie" name="szkolenie" style="width: 100px;" readonly>
                </td>
            </tr>
            <tr>
                <td><span>nazwa testu: </span></td><td><input id="test" name="test" style="width: 100px;" readonly>
                </td>
            </tr>
            <tr>
                <tr>
                    <td><span>uwagi: </span></td><td><input id="uwagi" name="uwagi" style="width: 100px;"></td>
                </tr>
            </tbody>
        </table>
        <input id="edytujszkolenietestust" name="edytujszkolenieust" value="edytuj" type="button" onclick="edytujtabeleszkolenietestust();" style="padding: 5px;width: 120px; margin-left: 35%;">
    </form>
</div>
<div id='newszkolenietestust'  style='margin-top: 10px; display: none;'  title="Nowe szkolenie">
    <form id="forminsertszkolenieust">
        <table   style="margin-bottom: 15px;">
            <tr>
                <td><span>nazwa szkolenia: </span></td><td>
                    <select id="Nszkolenie" name="Nszkolenie" style="width: 250px;">
                <?php error_reporting(0);
                foreach ($_szkolenia as $valuens) {
                        ?>
                        <option value="<?php echo $valuens['nazwa'] ?>"><?php echo $valuens['nazwa'] ?></option>
                        <?php
                    }
                ?>
                    </select>
                </td>
            </tr>
            <tr>
                <td><span>nazwa testu: </span></td><td>
                    <select id="Ntest" name="Ntest" style="width: 250px;">
                    <?php error_reporting(0);
                        foreach ($_testy as $valuent) { 
                        ?>
                            <option value="<?php echo $valuent['nazwa'];?>"><?php echo $valuent['nazwa'];?></option>
                            <?php
                        } ?>
                    </select>
                </td>
            </tr>
            <tr>
                <td><span>uwagi: </span></td><td><input id="Nuwagi" name="Nuwagi" style="width: 100px;"></td>
            </tr>
        </table>
        <input id="Ndodajszkolenieust" name="Ndodajszkolenieust" value="dodaj" type="button" onclick="dodajnoweszkolenietestust();"  style="padding: 5px; width: 120px; margin-left: 35%;">
    </form>
</div>

