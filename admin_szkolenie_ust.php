<?php error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
$_wynikszkolenia = R::getAll('select * from szkolenieust');
$_wynik_firmaall = R::getAll('SELECT * FROM zakladpracy');
?>
<div style="box-shadow: 10px 10px 5px #888; font-family: Verdana, Arial, Helvetica, sans-serif; background-color: gainsboro; vertical-align: middle;">
<table id="tabszkolenieust"  class="ckeditor" >
    <thead>
        <tr >
            <th>id</th>
            <th>firma</th>
            <th>nazwa szkolenia</th>
            <th>ilość pytań</th>
        </tr>
    </thead>
    <tbody>
        <?php error_reporting(0); foreach ($_wynikszkolenia as $value) { ?>
            <tr id="<?php error_reporting(0); echo $value['id'] ?>" >
                <td><?php error_reporting(0); echo $value['id'] ?></td>
                <td><?php error_reporting(0); echo $value['firma'] ?></td>
                <td><?php error_reporting(0); echo $value['nazwaszkolenia'] ?></td>
                <td><?php error_reporting(0); echo $value['iloscpytan'] ?></td>
            </tr>
        <?php error_reporting(0); } ?>
    </tbody>
</table>
     <div style="height: 50px;"> 
   <div style="margin: 10px">
    <input id="dodajszkolenieust" name="dodajszkolenieust" value="nowy" type="button" onclick="noweszkolenieust();" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
</div>
         </div>
    </div>

<div id='editszkolenieust' style='margin-top: 10px; height: 250px; box-shadow: 10px 10px 5px #888; background-color: gainsboro; padding: 30px;' title="Edytuj szkolenie" onload="">
    <form id="formeditszkolenieust" action="editszkolenieust.php" method="post" >
        <table  style="margin-bottom: 10px; margin-left: 22px;" >
            <tbody>
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="idszkolenieust" name="idszkolenieust" style="width: 350px;"></td>
                </tr>
                 <tr>
                <td><span>nazwa firmy firmy: </span></td><td>
                    <select id="firmaszkoleniaust" name="firmaszkoleniaust" style="width: 250px;">
                            <?php error_reporting(0);
                            foreach ($_wynik_firmaall as $value) {
                                if ($value['firmaaktywna'] == TRUE) {
                                    ?>
                                    <option value="<?php error_reporting(0); echo $value['nazwazakladu'] ?>"><?php error_reporting(0); echo $value['nazwazakladu'] ?></option>

                                <?php error_reporting(0);
                                }
                            }
                            ?>
                        </select>
                </td>
            </tr>
                <tr>
                <td><span>nazwa szkolenia: </span></td><td>
                    <select id="nazwaszkoleniaust" name="nazwaszkoleniaust" style="width: 250px;">
                        <option value="szkolenie1">szkolenie1</option>
                        <option value="szkolenie2">szkolenie2</option>
                        <option value="szkolenie3">szkolenie3</option>
                        <option value="szkoleniedemo">szkoleniedemo</option>
                    </select>
                </td>
            </tr>
                <tr>
                    <td><span>ilość pytań: </span></td><td><input id="iloscpytanust" name="iloscpytanust" style="width: 100px;"></td>
                </tr>
            </tbody>
        </table> 
        <input id="edytujszkolenieust" name="edytujszkolenieust" value="edytuj" type="submit" onclick="edytujtabeleszkolenieust();" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
        <input id="usunszkolenieust" name="usunszkolenieust" value="usuń" type="submit" onclick="usuntabeleszkolenieust();" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
    </form>
</div>
<div id='newszkolenieust' style='margin-top: 10px; display: none; height: 550px; box-shadow: 10px 10px 5px #888; background-color: gainsboro; padding: 30px;' title="Nowe szkolenie">
    <form id="forminsertszkolenieust" action="newszkolenieust.php" method="post">
        <table  style="margin-bottom: 10px; margin-left: 22px;">
             <tr>
                <td><span>nazwa firmy firmy: </span></td><td>
                    <select id="Nfirmaszkoleniaust" name="Nfirmaszkoleniaust" style="width: 250px;">
                            <?php error_reporting(0);
                            foreach ($_wynik_firmaall as $value) {
                                if ($value['firmaaktywna'] == TRUE) {
                                    ?>
                                    <option value="<?php error_reporting(0); echo $value['nazwazakladu'] ?>"><?php error_reporting(0); echo $value['nazwazakladu'] ?></option>

                                <?php error_reporting(0);
                                }
                            }
                            ?>
                        </select>
                </td>
            </tr>
                <tr>
                <td><span>nazwa szkolenia: </span></td><td>
                    <select id="Nnazwaszkolenia" name="Nnazwaszkoleniaust" style="width: 250px;">
                        <option value="szkolenie1">szkolenie1</option>
                        <option value="szkolenie2">szkolenie2</option>
                        <option value="szkolenie3">szkolenie3</option>
                        <option value="szkoleniedemo">szkoleniedemo</option>
                    </select>
                </td>
            </tr>
                <tr>
                    <td><span>ilość pytań: </span></td><td><input id="Niloscpytanust" name="Niloscpytanust" style="width: 100px;"></td>
                </tr>
        </table>
        <input id="Ndodajszkolenieust" name="Ndodajszkolenieust" value="dodaj" type="submit" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
    </form>
</div>


