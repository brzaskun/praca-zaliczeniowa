<?php
error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
require_once $_SERVER['DOCUMENT_ROOT'].'/resources/php/ConvertZaswiadczenie.php';
$_szk = R::getAll('select * from szkoleniewykaz'); 
$sql = "SELECT * FROM szkoleniewykaz";
$szkoleniawykaz = R::getAll($sql);
$output = array();
foreach ($szkoleniawykaz as $val) {
     $id_zaswiadczenie = $val['id_zaswiadczenie'];
     if (isset($id_zaswiadczenie)) {
       $val['id_zaswiadczenie'] = ConvertZaswiadczenie::toName($id_zaswiadczenie);
     } else {
         $val['id_zaswiadczenie'] = null;
     }
     array_push($output,$val);
}
$_szk = $output; 
$sql = "SELECT nazwa FROM zaswiadczenia";
$zaswiadczenia = R::getCol($sql);
?>

<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro; ">
    <div id="tbl" style="max-width: 12000px;">
        <table id="tabszkoleniewykaz" class="compact" class="ckeditor" style="margin: 0px; width: 1200px;">
            <thead>
                <tr >
                    <th></th>
                    <th style="width: 20px;">id</th>
                    <th>nazwa</th>
                    <th>skrót</th>
                    <th>opis</th>
                    <th>id_zaswiadczenie</th>
                    <th>instrukcja</th>
                    <th>edytuj</th>
                    <th>usuń</th>
                </tr>
            </thead>
            <tbody>
                <?php
                error_reporting(0);
                foreach ($_szk as $value) {
                    ?>
                    <tr id="<?php
                        error_reporting(0);
                        echo $value['id']
                        ?>" class="wierszeszkoleniewykaz" >
                        <td class="czekboks" style="width: 10px;"><input type="checkbox" class="czekbox"/></td>
                        <td><span class="doedycji"><?php
                        error_reporting(0);
                        echo $value['id']
                        ?></span></td>
                        <td><span class="doedycji"><?php
                            error_reporting(0);
                            echo $value['nazwa']
                            ?></span></td>
                        <td><span class="doedycji"><?php
                            error_reporting(0);
                            echo $value['skrot']
                            ?></span></td>
                        <td><span class="doedycji"><?php
                        error_reporting(0);
                        echo $value['opis']
                        ?></span></td>
                        <td><span class="doedycji"><?php
                        error_reporting(0);
                        echo $value['id_zaswiadczenie']
                            ?></span></td>
                        <td><a href="/resources/css/pics/<?php error_reporting(0);echo $value['instrukcja']?>" target="_blank"><span class="doedycji"><?php error_reporting(0);echo $value['instrukcja']?></span></a></td>
                        <td style="width: 55px;">
                            <input title="edytuj" name="edytuj" value="edytuj" type="checkbox"  onclick="editszkoleniewykaz(event);" class="czekedycja" style="display: none;"/>
                        </td>
                        <td style="width: 55px;"><input title="usuń" name="usun" value="usuń" type="checkbox"  onclick="usunszkoleniewykaz(this);" class="czekedycja" style="display: none;"/></td>
                    </tr>
    <?php
    error_reporting(0);
}?>
            </tbody>
        </table>
    </div>
    <div id='editszkoleniewykaz'  style='margin-top: 10px; display: none;'  title="Edytuj szkolenie">
        <form id="formeditszkoleniewykaz">
            <table id="tabelaeditszkoleniewykaz"  style="margin-bottom: 15px;">
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="idszkolenie" name="idszkolenie" style="width: 150px;"></td>
                </tr>
                <tr>
                    <td><span>nazwa szkolenia: </span></td><td><input id="szkolenia" name="szkolenia" style="width: 250px;" readonly></td>
                </tr>
                <tr>
                    <td><span>skrót nazwy: </span></td><td><input id="skrot" name="skrot" style="width: 250px;" ></td>
                </tr>
                <tr>
                    <td><span>opis szkolenia: </span></td><td><input id="opis" name="opis" style="width: 250px;"></td>
                </tr>
                 <tr>
                     <td><span>zaświadczenie: </span></td>
                     <td>
                      <select id="zaswiadczenie" name="zaswiadczenie" style="width: 260px;">
                            <?php
                            error_reporting(0);
                            foreach ($zaswiadczenia as $value) {
                                ?>
                                <option value="<?php echo $value; ?>"><?php echo $value; ?></option>
                                <?php error_reporting(0);
                            }
                            ?>
                        </select>
                     </td>
                </tr>
                <tr>
                    <td><span>plik instukcja: </span></td><td><input id="instrukcja" name="instrukcja" style="width: 250px;"></td>
                </tr>
            </table>
            <input value="edytuj" type="button" onclick="edytujtabeleszkoleniewykaz();"  style="padding: 5px; width: 120px; margin-left: 17%;">
            <input value="rezygnuj" type="button" onclick="canceledytujszkolenie()"  style="padding: 5px; width: 120px; margin-left: 5%;">
        </form>
    </div>
    <div id='newszkoleniewykaz'  style='margin-top: 10px; display: none;'  title="Nowe szkolenie">
        <form id="forminsertszkoleniewykaz">
            <table   style="margin-bottom: 15px;"> 
                <tr>
                    <td><span>nazwa szkolenia: </span></td><td><input id="Nszkolenia" name="Nszkolenia" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>skrót nazwy: </span></td><td><input id="Nskrot" name="Nskrot" style="width: 250px;"></td>
                </tr>
                <tr>
                    <td><span>opis szkolenia: </span></td><td><input id="Nopis" name="Nopis" style="width: 250px;"></td>
                </tr>
                 <tr>
                     <td><span>zaświadczenie: </span></td>
                     <td>
                      <select id="Nzaswiadczenie" name="Nzaswiadczenie" style="width: 260px;">
                            <?php
                            error_reporting(0);
                            foreach ($zaswiadczenia as $value) {
                                ?>
                                <option value="<?php echo $value; ?>"><?php echo $value; ?></option>
                                <?php error_reporting(0);
                            }
                            ?>
                        </select>
                     </td>
                </tr>
                <tr>
                    <td><span>plik instukcja: </span></td><td><input id="Ninstrukcja" name="instrukcja" style="width: 250px;"></td>
                </tr>
            </table>
            <input value="dodaj" type="button" onclick="dodajnoweszkoleniewykaz();"  style="padding: 5px; width: 120px; margin-left: 17%;">
            <input value="rezygnuj" type="button" onclick="cancelnoweszkolenie()"  style="padding: 5px; width: 120px; margin-left: 5%;">
        </form>
    </div>
</div>

