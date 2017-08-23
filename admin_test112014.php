<?php
error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$_wyniktest = R::getAll('select * from test');
$_testy = R::getAll('select * from testwykaz');
?>

<div style="box-shadow: 10px 10px 5px #888; padding: 30px; margin-top: 10px; background-color: gainsboro;">
    <div id="tbl" style="max-width: 1400px;">
        <table id="tabtest" style="margin: 0px; width: 1400px;">
            <thead>
                <tr >
                    <th style="text-align: center"></th>
                    <th>id</th>
                    <th>nazwa testu</th>
                    <th>treść</th>
                    <th>rodzaj</th>
                    <th>pytanie</th>
                    <th>odp1</th>
                    <th>odp2</th>
                    <th>odp3</th>
                    <th>odp4</th>
                    <th>odp1 wal.</th>
                    <th>odp2 wal.</th>
                    <th>odp3 wal.</th>
                    <th>odp4 wal.</th>
                    <th style="text-align: center">edytuj</th>
                    <th style="text-align: center">usuń</th>
                </tr>
            </thead>
            <tbody>
<?php error_reporting(0);
foreach ($_wyniktest as $value) { ?>
                    <tr id="<?php error_reporting(0);
    echo $value['id'] ?>" class="wierszetest" >
                        <td class="czekboks"><input type="checkbox"/></td>
                        <td><?php error_reporting(0);
    echo $value['id'] ?></td>
                        <td><?php error_reporting(0);
    echo $value['nazwatest'] ?></td>
                        <td><?php error_reporting(0);
    echo $value['tresc'] ?></td>
                        <td><?php error_reporting(0);
    echo $value['rodzaj'] ?></td>
                        <td><?php error_reporting(0);
    echo $value['pytanie'] ?></td>
                        <td><?php error_reporting(0);
    echo $value['odp1'] ?></td>
                        <td><?php error_reporting(0);
                echo $value['odp2'] ?></td>
                        <td><?php error_reporting(0);
                echo $value['odp3'] ?></td>
                        <td><?php error_reporting(0);
                echo $value['odp4'] ?></td>
                        <td><?php error_reporting(0);
                echo $value['odp1walidacja'] == 'true' ? 'pda' : 'npda' ?></td>
                        <td><?php error_reporting(0);
                echo $value['odp2walidacja'] == 'true' ? 'pda' : 'npda' ?></td>
                        <td><?php error_reporting(0);
                echo $value['odp3walidacja'] == 'true' ? 'pda' : 'npda' ?></td>
                        <td><?php error_reporting(0);
                echo $value['odp4walidacja'] == 'true' ? 'pda' : 'npda' ?></td>
                        <td style="width: 6%;">
                    <input title="edytuj" name="edytuj" value="edytuj" type="button"  onclick="edittest(this);" class="buttonedytujuser" style="display: none;"/>
                </td>
                <td style="width: 6%;"><input title="usuń" name="usun" value="usuń" type="button"  onclick="usuntest(this);"  style="display: none;"/></td>
                    </tr>
    <?php error_reporting(0);
} ?>
            </tbody>
        </table>
    </div>
</div>

<div id='edittest' style='margin-top: 10px; display: none;' title="Edycja wybranego pytania testowego" onload="">
    <form id="formedittest">
        <table  style="margin-bottom: 15px;">
            <tbody>
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="idtest" name="idtest" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>nazwa test: </span></td><td>
                        <select id="nazwatest" name="nazwatest" style="width: 250px;">
                            <?php
                            error_reporting(0);
                            foreach ($_testy as $value) {
                                ?>
                                <option value="<?php echo $value['nazwa']; ?>"><?php echo $value['nazwa']; ?></option>
                                <?php error_reporting(0);
                            }
                            ?>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><span>treść: </span></td><td><input id="ttresc" name="ttresc" style="width: 690px;"/>
                </tr>
                <tr>
                    <td><span>rodzaj: </span></td><td><input id="trodzaj" name="trodzaj" style="width: 250px;"  type="number" min="0" max="9" maxlength="1" pattern="\d"></td>
                </tr>
                <tr>
                    <td><span>pytanie: </span></td><td><input id="tpytanie" name="tpytanie" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp1: </span></td><td><input id="todp1" name="todp1" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp2: </span></td><td><input id="todp2" name="todp2" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp3: </span></td><td><input id="todp3" name="todp3" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp4: </span></td><td><input id="todp4" name="todp4" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp1 walidacja: </span></td><td><input id="todp1w" type="checkbox" name="todp1w" ></td>
                </tr>
                <tr>
                    <td><span>odp2 walidacja: </span></td><td><input id="todp2w" type="checkbox" name="todp2w" ></td>
                </tr>
                <tr>
                    <td><span>odp3 walidacja: </span></td><td><input id="todp3w" type="checkbox" name="todp3w" ></td>
                </tr>
                <tr>
                    <td><span>odp4 walidacja: </span></td><td><input id="todp4w" type="checkbox" name="todp4w" ></td>
                </tr>
            </tbody>
        </table>
        <input id="edytujtest" name="edytujtest" value="edytuj" type="button" onclick="edytujtabeletest();" style="padding: 5px; width: 120px; margin-left: 30%;">
        <input id="podgladtest" name="podgladtest" value="podglad" type="button" onclick="edytujtabeletestpodglad();" style="padding: 5px; width: 120px; margin-left: 5%;">
    </form>
</div>
<div id='newtest' style='margin-top: 10px; display: none;' title="Nowe pytanie do testu">
    <form id="forminserttest" >
        <table  style="margin-bottom: 15px;">
           <tbody>
                <tr hidden>
                    <td><span>id: </span></td><td><input type="text" id="Nidtest" name="Nidtest" style="width: 350px;"></td>
                </tr>
                <tr>
                    <td><span>nazwa testu: </span></td><td>
                        <select id="Nnazwatest" name="Nnazwatest" style="width: 250px;">
                            <?php
                            error_reporting(0);
                            foreach ($_testy as $value) {
                                ?>
                                <option value="<?php echo $value['nazwa']; ?>"><?php echo $value['nazwa']; ?></option>
                                <?php error_reporting(0);
                            }
                            ?>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td><span style="width: 200px;">treść: </span></td><td><input id="Nttresc" name="ttresc" style="width: 690px;"/>
                </tr>
                <tr>
                    <td><span>rodzaj: </span></td><td><input id="Ntrodzaj" name="Ntrodzaj" style="width: 250px;" type="number" min="0" max="9" maxlength="1" pattern="\d"></td>
                </tr>
                <tr>
                    <td><span>pytanie: </span></td><td><input id="Ntpytanie" name="Ntpytanie" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp1: </span></td><td><input id="Ntodp1" name="Ntodp1" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp2: </span></td><td><input id="Ntodp2" name="Ntodp2" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp3: </span></td><td><input id="Ntodp3" name="Ntodp3" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp4: </span></td><td><input id="Ntodp4" name="Ntodp4" style="width: 690px;"></td>
                </tr>
                <tr>
                    <td><span>odp1 walidacja: </span></td><td><input id="Ntodp1w" type="checkbox" name="Ntodp1w" ></td>
                </tr>
                <tr>
                    <td><span>odp2 walidacja: </span></td><td><input id="Ntodp2w" type="checkbox" name="Ntodp2w" ></td>
                </tr>
                <tr>
                    <td><span>odp3 walidacja: </span></td><td><input id="Ntodp3w" type="checkbox" name="Ntodp3w" ></td>
                </tr>
                <tr>
                    <td><span>odp4 walidacja: </span></td><td><input id="Ntodp4w" type="checkbox" name="Ntodp4w" ></td>
                </tr>
            </tbody>
        </table>
        <input id="Ndodajtest" name="Ndodajtest" value="dodaj" type="button" onclick="dodajnowytest()" style="padding: 5px; width: 120px; margin-left: 40%;">
    </form>
</div>
