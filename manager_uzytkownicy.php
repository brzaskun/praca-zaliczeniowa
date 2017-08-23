<?php error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
$firmamanagera = $_SESSION['uczestnik']['firma'];
$mailmanager = $_SESSION['uczestnik']['email'];
$sql = "SELECT * FROM uczestnicy WHERE firma = '$firmamanagera' AND NOT email = '$mailmanager'";
$_wynik = R::getAll($sql);
?>

<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro;">
    <?php error_reporting(0);
    $sql = "select * FROM `zakladpracy` WHERE `nazwazakladu` LIKE '$firmamanagera'";
    $_wynik_selectfirma = R::getRow($sql);
    if ($_wynik_selectfirma['firmaaktywna'] == '1') {
        ?>
        <table id="tabuser" class="hovertable context-menu-one box menu-1" >
            <thead >
                <tr >
                    <th>lp</th>
                    <th>adres mail</th>
                    <th>imie i nazwisko</th>
                    <th>płeć</th>
                    <th>firma</th>
                    <th>szkolenie</th>
                    <th>uprawnienia</th>
                    <th>ilość logowań</th>
                    <th>wysłano link</th>
                    <th>rozpoczecie sesji</th>
                    <th>zakończenie sesji</th>
                    <th>wyniktestu</th>
                    <th>wysłano cert.</th>
                    <th hidden>id</th>
                </tr>
            </thead>
            <tbody>

                <?php error_reporting(0);
                $i = 1;
                foreach ($_wynik as $value) {
                    ?>
                    <tr id="<?php error_reporting(0); echo $value['id'] ?>" class="wiersze" >
                        <td><?php error_reporting(0); echo $i ?></td>
                        <td><?php error_reporting(0); echo $value['email'] ?></td>
                        <td><?php error_reporting(0); echo $value['imienazwisko'] ?></td>
                        <td><?php error_reporting(0); echo $value['plec'] ?></td>
                        <td><?php error_reporting(0); echo $value['firma'] ?></td>
                        <td><?php error_reporting(0); echo $value['nazwaszkolenia'] ?></td>
                        <td style="text-align: center"><?php error_reporting(0); echo $value['uprawnienia'] ?></td>
                        <td style="text-align: center; color: <?php error_reporting(0); echo ($value['ilosclogowan'] >= 4 ? 'red' : 'black'); ?>;"><?php error_reporting(0); echo $value['ilosclogowan'] ?></td>
                        <td style="text-align: center; color: <?php error_reporting(0); echo ($value['wyslanymailupr'] >= 1 ? 'blue' : 'black'); ?>;"><?php error_reporting(0); echo $value['wyslanymailupr'] ?></td>
                        <td><?php error_reporting(0); echo $value['sessionstart'] ?></td>
                        <td><?php error_reporting(0); echo $value['sessionend'] ?></td>
                        <td style="text-align: center"><?php error_reporting(0); echo $value['wyniktestu'] ?></td>
                        <td style="text-align: center"><?php error_reporting(0); echo $value['wyslanycert'] ?></td>
                        <td hidden><?php error_reporting(0); echo $value['id'] ?></td>
                    </tr>
                    <?php error_reporting(0);
                    $i++;
                    $_SESSION['liczbapracownikow'] = $i;
                }
                ?>
            </tbody>
        </table>
        <script>
                        $(document).ready(function() {
                            var maxpracownikow = <?php error_reporting(0); echo $_SESSION['maxpracownikow']; ?>;
                            var liczbapracownikow = <?php error_reporting(0); echo $_SESSION['liczbapracownikow']; ?>;
                            liczbapracownikow = liczbapracownikow - 1;
                            if (liczbapracownikow >= maxpracownikow) {
                                $('#dodajuserdiv').hide();
                                $('#infouserdiv').show();
                                $('#przyciskladowanie').hide();
                                $('#plikwzorcowy').hide();
                                $('#komunikatmax').show();
                            } else {
                                $('#dodajuserdiv').show();
                                $('#infouserdiv').hide();
                                $('#przyciskladowanie').show();
                                $('#plikwzorcowy').show();
                                $('#komunikatmax').hide();
                            }
                        });
        </script>
        <div style="height: 50px;"> 
        <div id="dodajuserdiv" style="margin: 10px; display: none">
            <input id="back" name="dodajuser" value="nowy" type="button" onclick="nowyuser();" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
        </div>
        <div id="infouserdiv" style="float: left; color: red;">
            <form action="manager.php">
                <div>
                    <input id="odswiez" name="odswiez" value="odśwież" type="submit" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
                </div>
            </form>
        </div>
        <div id="infouserdiv" style="float: left; color: red;">
            <form action="managertabela.php">
                <div>
                    <input id="tabela" name="tabela" value="XLS" type="submit" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
                </div>
            </form>
        </div>

            <div style="float: right;">
                <p >Maksymalna dopuszczalna liczba pracowników wynosi: <?php error_reporting(0); echo $_SESSION['maxpracownikow'] ?> </p>
            </div>
        </div>
        </div>

        <!--formularz do edycji danych użytkownika-->
        <div id='edituser' style='margin-top: 10px; height: 330px; box-shadow: 10px 10px 5px #888; background-color: gainsboro; padding: 30px;' title="Wybrany użytkownik" >
            <form id="formedituser" action="" method="post">
                <table  style="margin-bottom: 10px; margin-left: 22px;">
                    <tbody>
                        <tr hidden>
                            <td><span>id: </span></td><td><input type="text" id="iduser" name="iduser" style="width: 250px;"></td>
                        </tr>
                        <tr>
                            <td><span>adres mail: </span></td><td><input type="email" id="email" name="email" style="width: 250px;" readonly=""></td>
                        </tr>
                        <tr>
                            <td><span>imie nazwisko: </span></td><td><input type="text" id="imienazwisko" name="imienazwisko" style="width: 250px;"></td>
                        </tr>
                        <tr>
                            <td><span>płeć: </span></td><td>
                                <select id="plecuser" name="plecuser" style="width: 250px;">
                                    <option value="m">mężczyzna</option>
                                    <option value="k">kobieta</option>
                                </select>
                            </td>
                        </tr>
                        <tr hidden>
                            <td><span>firma: </span></td><td>
                                <input type="text" id="firmausernazwa" name="firmausernazwa" style="width: 250px;" value="<?php error_reporting(0); echo $firmamanagera; ?>" readonly>
                            </td>
                        </tr>
                        <tr>
                            <td><span>szkolenie: </span></td><td>
                                <select id="szkolenieuser" name="szkolenieuser" style="width: 250px;">
                                    <option value="szkolenie1">szkolenie1</option>
                                    <option value="szkolenie2">szkolenie2</option>
                                    <option value="szkolenie3">szkolenie3</option>
                                    <option value="szkoleniedemo">szkoleniedemo</option>
                                </select>
                            </td>
                        </tr>
                        <tr hidden>
                            <td><span>uprawnienia: </span></td><td>
                                <input type="text" id="uprawnieniauser" name="uprawnieniauser" style="width: 250px;" value="uzytkownik" readonly>
                            </td>użytkownik
                        </tr>
                        <tr hidden>
                            <td><span>data zalogowania: </span></td><td><input id="datazalogowania" name="datazalogowania" style="width: 250px;" readonly></td>
                        </tr>
                    </tbody>
                </table>
                <input id="editbutton" name="edytujuser" value="edytuj" type="submit" onclick="edytujtabeleuser();" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
                <input id="usunbutton" name="usunuser" value="usuń" type="submit" onclick="usuntabeleusermanager();" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
            </form>
        </div>
        <div id='newuser' style='margin-top: 10px; display: none;' title="Nowy użytkownik">
            <form id="forminsertuser" action="newuser_manager.php" method="post">
                <table>
                    <tr>
                        <td><span>adres mail: </span></td><td><input type="email" id="Nemail" name="Nemail" style="width: 250px;"></td>
                    </tr>
                    <tr>
                        <td><span>imie nazwisko: </span></td><td><input type="text" id="Nimienazwisko" name="Nimienazwisko" style="width: 250px;"></td>
                    </tr>
                    <tr>
                        <td><span>płeć: </span></td><td>
                            <select id="Nplecuser" name="Nplecuser" style="width: 250px;">
                                <option value="m">mężczyzna</option>
                                <option value="k">kobieta</option>
                            </select>
                        </td>
                    </tr>
                    <tr hidden>
                        <td><span>firma: </span></td><td>
                            <input type="text" id="Nfirmauser" name="Nfirmauser" style="width: 250px;" value="<?php error_reporting(0); echo $firmamanagera; ?>" readonly>
                        </td>
                    </tr>
                    <tr>
                        <td><span>szkolenie: </span></td><td>
                            <select id="Nszkolenieuser" name="Nszkolenieuser" style="width: 250px;">
                                <option value="szkolenie1">szkolenie1</option>
                                <option value="szkolenie2">szkolenie2</option>
                                <option value="szkolenie3">szkolenie3</option>
                                <option value="szkoleniedemo">szkoleniedemo</option>
                            </select>
                        </td>
                    </tr>
                    <tr hidden>
                        <td><span>uprawnienia: </span></td><td>
                            <input type="text" id="Nuprawnieniauser" name="Nuprawnieniauser" style="width: 250px;" value="uzytkownik" readonly>
                        </td>
                    </tr>
                </table>
                <input id="Nback" name="dodajuser" value="dodaj" type="submit" style="padding: 5px; padding: 10px; width: 120px; margin-left: 2%; float: left;">
            </form>
        </div>
        <div id="dialog-user" title="Potwierdzenie" style="display: none;">
            <p>
                <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 100px 0;"></span>
                Edycja użytkownika: </p><p><span id="uzytkownikwiadomosc"></span></p><p>zakończona sukcesem</p>
        </div>
        <div id="dialog-user-usun" title="Potwierdzenie" style="display: none;">
            <p>
                <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 100px 0;"></span>
                Użytkownik: </p><p><span id="uzytkownikwiadomoscusun"></span></p><p>usunięty</p>
        </div>
         <div id="dialog-user-nousun" title="Błąd" style="display: none;">
            <p>
                <span class="ui-icon ui-icon-circle-check" style="float: left; margin: 0 7px 100px 0;"></span>
                Użytkownik: </p><p><span id="uzytkownikwiadomoscnousun"></span></p><p>już się zalogował. Nie może zostać usuniety.</p>
        </div>
        <?php error_reporting(0);
    } else {
        ?>
        <p style="color: red; font-size: x-large;">Firma została deazktywowana przez administratora</p>
        <?php error_reporting(0);
    }
    ?>

