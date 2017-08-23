<?php

error_reporting(0);
if (session_status() != 2) {
    session_start();
};
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$email = $_POST['Nemail'];
$imienazwisko = $_POST['Nimienazwisko'];
$firma = $_POST['Nfirmauser'];
$uprawnienia = $_POST['Nuprawnieniauser'];
$plec = $_POST['Nplecuser'];
$szkolenieuser = $_POST['Nszkolenieuser'];
date_default_timezone_set('Europe/Warsaw');
$czasbiezacy = date("Y-m-d H:i:s");
try {
    $sql = "INSERT INTO  `uczestnicy` (`id`, `email` ,`imienazwisko` ,`plec` ,`firma` , `nazwaszkolenia`, `uprawnienia` ,`wyslanymailupr` ,`sessionstart` ,
      `sessionend` ,`wyniktestu` ,`wyslanycert`, `utworzony`)
      VALUES (0, '$email',  '$imienazwisko', '$plec', '$firma', '$szkolenieuser', '$uprawnienia' , 0, NULL , NULL , 0 , 0, '$czasbiezacy');";
    R::exec($sql);
    $id = R::getInsertID();
    $sql = "SELECT `managerlimit` FROM `zakladpracy` WHERE `zakladpracy`.`nazwazakladu`='$firma';";
    $_SESSION['managerlimit'] = R::getCell($sql);
    require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Mail.php');
    $instrukcja = R::getCell("SELECT instrukcja FROM szkoleniewykaz WHERE  nazwa = '$szkolenieuser'");
    $poziomzaswiadczenie = Mail::pobierzPoziomZaswiadczenia($szkolenieuser, $plec);
    $linia1 = Mail::pobierzLinia1Zaswiadczenia($szkolenieuser);
    //wysylanie maila dla nowododanego
    require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/swiftmailer/swift_required.php';
    try {
        $transport = Swift_SmtpTransport::newInstance('az0066.srv.az.pl', 465);
        $transport->setEncryption('ssl');
        $transport->setUsername('e-szkolenia@odomg.pl');
        $transport->setPassword('Odo1234*');
        // Create the Mailer using your created Transport
        $mailer = Swift_Mailer::newInstance($transport);
        $logger = Mail::loggerFactory($mailer);
        // Create a message
        $message = null;
        if ($uprawnienia === "manager" && $plec === "m") {
            $message = Swift_Message::newInstance('Rejestracja do e-szkolenia - menadżer '.$linia1)
                    ->setContentType('text/plain')
                    ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                    ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                    ->setTo(array($email => $imienazwisko))
                    ->setBody('
        <h4>Szanowny Panie Menadżerze ' . $imienazwisko . '</h4>
        <div style="width: 550px; text-align: justify;">
        <p>Został Pan właśnie zarejestrowany w systemie e-learningu w celu zarządzania danymi pracowników firmy do odbycia 
        Szkolenia z Ochrony Danych Osobowych przygotowanego przez firmę ODO Management Group.
        Szkolenie kończy się testem wielokrotnego wyboru. 
        Po ukończeniu testu każdy pracownik otrzyma stosowne zaświadczenie. 
        W przypadku niezaliczenia testu pracownik możesz zalogować się jeszcze raz w celu ponownego przeszkolenia</p>
        <p>Wymagany termin odbycia szkolenia i zaliczenia testu przez pracownika: 24 godziny od momentu pierwszego zalogowania do systemu.</p>
        <p>Maksymalna dopuszczalna ilość logowań wynosi 4.</p>
        <p>Należy kliknąć w ten link aby rozpocząć <a href="https://szkolenie.odomg.pl/index.php?mail=' . urlencode($email) . '">SZKOLENIE - MANAGER</a> </p>
        <p>Od pierwszego zalogowania ma Pan ' . $_SESSION['managerlimit'] . ' dni na rejestrowanie, edycję lub usuwanie uczestników testu.</p>
        <p>Zalogowanie oznacza akceptację regulaminu szkolenia. </p>
		<p>Wymagania systemowe umożliwiające prawidłowe funkcjonowanie programu: zainstalowany program Acrobat Reader (od wersji 10.0). <br/>
		 Rekomendowane przeglądarki: Chrome, Mozilla Firefox, Internet Explorer (w ostatnich wersjach)</p>
        <p>Z wyrazami szacunku</p>
        <p>Zespół ODO Management Group</p><br/>
        <p>
        Zarządzanie danymi pracowników biorących udział w szkoleniu i teście wymaga umieszczenia na pana komputerze plików cookie. 
        Kliknięcie na link oznacza wyrażenie zgody na użycie plików cookie.
        </p>
        </div>
    ', 'text/html');
        } else if ($uprawnienia === "manager" && $plec === "k") {
            $message = Swift_Message::newInstance('Rejestracja do e-szkolenia - menadżer '.$linia1)
                    ->setContentType('text/plain')
                    ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                    ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                    ->setTo(array($email => $imienazwisko))
                    ->setBody('
        <h4>Szanowna Pani Menadżer ' . $imienazwisko . '</h4>
        <div style="width: 550px; text-align: justify;">
        <p>Została Pani właśnie zarejestrowana w systemie e-learningu w celu zarządzania danymi pracowników firmy do odbycia 
        Szkolenia z Ochrony Danych Osobowych przygotowanego przez firmę ODO Management Group.
        Szkolenie kończy się testem wielokrotnego wyboru. 
        Po ukończeniu testu każdy pracownik otrzyma stosowne zaświadczenie. 
        W przypadku niezaliczenia testu pracownik możesz zalogować się jeszcze raz w celu ponownego przeszkolenia</p>
        <p>Wymagany termin odbycia szkolenia i zaliczenia testu przez pracownika: 24 godziny od momentu pierwszego zalogowania do systemu.</p>
        <p>Maksymalna dopuszczalna ilość logowań wynosi 4.</p>
        <p>Należy kliknąć w ten link aby rozpocząć <a href="https://szkolenie.odomg.pl/index.php?mail=' . urlencode($email) . '">SZKOLENIE - MANAGER</a> </p>
        <p>Od pierwszego zalogowania ma Pani ' . $_SESSION['managerlimit'] . ' dni na rejestrowanie, edycję lub usuwanie uczestników testu.</p>
		<p>Wymagania systemowe umożliwiające prawidłowe funkcjonowanie programu: zainstalowany program Acrobat Reader (od wersji 10.0). <br/>
		 Rekomendowane przeglądarki: Chrome, Mozilla Firefox, Internet Explorer (w ostatnich wersjach)</p>
        <p>Z wyrazami szacunku</p>
        <p>Zespół ODO Management Group</p><br/>
        <p>
        Zarządzanie danymi pracowników biorących udział w szkoleniu i teście wymaga umieszczenia na pana komputerze plików cookie. 
        Kliknięcie na link oznacza wyrażenie zgody na użycie plików cookie.
        </p>
        </div>
    ', 'text/html');
        } else if ($plec === "k") {
            $message = Swift_Message::newInstance('Rejestracja do e-szkolenia - ' . $linia1)
                    ->setContentType('text/plain')
                    ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                    ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                    ->setTo(array($email => $imienazwisko))
                    ->setBody('
        <h4>Dzień dobry,</h4>
        <div style="width: 550px; text-align: justify;">
        <p>Pani adres email został zarejestrowany w systemie e-learningu. </p>
        <p>Zapraszamy do wzięcia udziału w szkoleniu przygotowanym przez firmę ODO Management Group. 
        Szkolenie kończy się testem wielokrotnego wyboru. Po ukończeniu testu generowane jest stosowne zaświadczenie (plik PDF). 
        W przypadku niezaliczenia testu należy zalogować się jeszcze raz i ponownie przystąpić do szkolenia i do testu.</p>
        <p style="color: rgb(243,112,33);">Poniższy link pozostaje aktywny przez 24 godziny od momentu pierwszego zalogowania do systemu.</p>
        <p>Kliknij w ten link, aby rozpocząć <a href="https://szkolenie.odomg.pl/index.php?mail=' . urlencode($email) . '">SZKOLENIE</a> </p>
        <p><a href="https://szkolenie.odomg.pl/resources/css/pics/'.$instrukcja.'">instrukcja do e-szkolenia,</a></p>
         <p>Wzięcie udziału w szkoleniu wymaga umieszczenia na komputerze użytkownika plików cookie.
         Rozpoczęcie szkolenia oznacza wyrażenie zgody na użycie plików cookie.</p>
          <p>Wymagania systemowe umożliwiające prawidłowe funkcjonowanie programu: zainstalowany program Acrobat Reader (od wersji 10.0). <br/>
		 Rekomendowane przeglądarki: Chrome, Mozilla Firefox, Internet Explorer (w ostatnich wersjach)</p>
        <p>Powodzenia!</p>
        <p>Zespół ODO Management Group</p><br/>
        <p style="font-weight: bold;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
        </div>
    ', 'text/html');
        } else {
            $message = Swift_Message::newInstance('Rejestracja do e-szkolenia - ' . $linia1)
                    ->setContentType('text/plain')
                    ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                    ->setTo(array($email => $imienazwisko))
                    ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
                    ->setBody('
        <h4>Dzień dobry,</h4>
        <div style="width: 550px; text-align: justify;">
        <p>Pana adres email został zarejestrowany w systemie e-learningu. </p>
        <p>Zapraszamy do wzięcia udziału w szkoleniu przygotowanym przez firmę ODO Management Group. 
        Szkolenie kończy się testem wielokrotnego wyboru. Po ukończeniu testu generowane jest stosowne zaświadczenie (plik PDF). 
        W przypadku niezaliczenia testu należy zalogować się jeszcze raz i ponownie przystąpić do szkolenia i do testu.</p>
        <p style="color: rgb(243,112,33);">Poniższy link pozostaje aktywny przez 24 godziny od momentu pierwszego zalogowania do systemu.</p>
        <p>Kliknij w ten link, aby rozpocząć <a href="https://szkolenie.odomg.pl/index.php?mail=' . urlencode($email) . '">SZKOLENIE</a> </p>
        <p><a href="https://szkolenie.odomg.pl/resources/css/pics/'.$instrukcja.'">instrukcja do e-szkolenia,</a></p>
        <p> Wzięcie udziału w szkoleniu wymaga umieszczenia na komputerze użytkownika plików cookie.
         Rozpoczęcie szkolenia oznacza wyrażenie zgody na użycie plików cookie.</p>
		 <p>Wymagania systemowe umożliwiające prawidłowe funkcjonowanie programu: zainstalowany program Acrobat Reader (od wersji 10.0). <br/>
		 Rekomendowane przeglądarki: Chrome, Mozilla Firefox, Internet Explorer (w ostatnich wersjach)</p>
        <p>Powodzenia!</p>
        <p>Zespół ODO Management Group</p><br/>
        <p style="font-weight: bold;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
        </div>
    ', 'text/html');
        }
        //dodaj zalaczniki
        // Send the message
        $failedRecipients = array();
        $numSent = 0;
        $numSent = $mailer->send($message, $failedRecipients);
        if ($numSent == 0) {
            $niewyslano = $failedRecipients[0];
            Mail::mailniewyslano($niewyslano,$logger);
            echo "link";
            exit();
        } else if ($numSent == 1) { 
            $sql = "UPDATE  `uczestnicy` SET  `wyslanymailupr` = '1' WHERE  `uczestnicy`.`id` = $id;";
            $res = R::exec($sql);
            if ($res == 0) {
                echo "update";
                exit();
            }
            echo $id;
            exit();
        }
    } catch (Exception $e) {
        echo "czas";
        Mail::mailerror($e);
        exit();
    }
} catch (Exception $e1) {
    echo "inny";
    Mail::mailerror($e1); 
    exit();
} 
?> 

